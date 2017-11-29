
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public class BoardState {

    private static final ConcurrentMap<BoardState,BoardState> states;
    private static final int DEPTH = 4;
    int turn;
    Set<BoardState> nextStates;
    static int cacheHits = 0;
    static{
        states = new ConcurrentHashMap<>();
    }

    Board board;

    public BoardState(Board board, int turn){

        this.board = board;
        this.turn = turn;
    }

    public int getTurn(){
        return turn;
    }

    public int nextTurn(){
        return (turn == Piece.B)? Piece.W:Piece.B;
    }
    public synchronized void computeNextStates(){
        if(nextStates != null) {
            return;
        }
        nextStates = Collections.synchronizedSet(new HashSet<>());
        board.pieceLocations(turn).parallel().flatMap(location -> {
                    Stream.Builder<Move> moves = Stream.builder();
                    board.get(location).nextMoves(location,board).forEach(newLocation -> moves.add(new Move(location,newLocation)));
                    return moves.build();
        }).forEach(move -> {
            Board next = new Board(board);
            next.move(move.from,move.to);
            nextStates.add(state(next,nextTurn()));
        });
    }
    public BoardState nextBestState(){
        computeNextStates();
        ConcurrentHashMap<BoardState,Double> nextBoardStates = new ConcurrentHashMap<>();
        nextStates.parallelStream().forEach(boardState -> nextBoardStates.put(boardState,getHeuristicSumDepth(nextTurn(),DEPTH)));
        double min = Double.MAX_VALUE;
        ArrayList<BoardState> bestMoves = new ArrayList<>();
        for (BoardState state :
                nextBoardStates.keySet()) {
            if(nextBoardStates.get(state) == min){
                bestMoves.add(state);
            }
            else if(nextBoardStates.get(state) < min){
                bestMoves.clear();
                bestMoves.add(state);
                min = nextBoardStates.get(state);
            }
        }
        int index = ((int)(Math.random() * bestMoves.size()));
        return bestMoves.get(index);
    }

    public double getHeuristicSumDepth(int color, int depth){
        if(depth == 0){
            return board.getHeuristicSum(color);
        }
        computeNextStates();
        return nextStates.parallelStream().mapToDouble(boardState -> boardState.getHeuristicSumDepth(color,depth-1)).average().orElse(0);
    }

    public static BoardState state(Board board, int turn){
        BoardState temp = new BoardState(board,turn);
        if(states.containsKey(temp))
            cacheHits++;
        return states.computeIfAbsent(temp, bs -> bs );
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return (toString()).hashCode();
    }

    @Override
    public String toString() {
        return "Turn: " + turn + "\n" + board.toString();
    }
}
