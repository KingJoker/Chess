import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Board {
    private static final int SIZE = 8;
    Piece[][] board;
    public Board(){
        board = new Piece[SIZE][SIZE];
        setUp();
    }
    public Board(Board other){
        board = new Piece[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c]=other.board[r][c];
            }
        }
    }
    public void setUp(){
        
        add(Rook.BLACK,Location.get(0,0));
        add(Knight.BLACK,Location.get(0,1));
        add(Bishop.BLACK,Location.get(0,2));
        add(Queen.BLACK,Location.get(0,3));
        add(King.BLACK,Location.get(0,4));
        add(Bishop.BLACK,Location.get(0,5));
        add(Knight.BLACK,Location.get(0,6));
        add(Rook.BLACK,Location.get(0,7));
        for (int c = 0; c < 8; c++) {
            add(Pawn.BLACK,Location.get(1,c));
        }

        add(Rook.WHITE,Location.get(7,0));
        add(Knight.WHITE,Location.get(7,1));
        add(Bishop.WHITE,Location.get(7,2));
        add(Queen.WHITE,Location.get(7,3));
        add(King.WHITE,Location.get(7,4));
        add(Bishop.WHITE,Location.get(7,5));
        add(Knight.WHITE,Location.get(7,6));
        add(Rook.WHITE,Location.get(7,7));
        for (int c = 0; c < 8; c++) {
            add(Pawn.WHITE,Location.get(6,c));
        }
    }
    public Piece get(Location loc){
        return board[loc.r][loc.c];
    }
    public void add(Piece piece, Location loc){
        board[loc.r][loc.c] = piece;
    }

    public static Stream<Location> allLocations(){
        Stream.Builder<Location> ret = Stream.builder();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                ret.add(Location.get(r,c));
            }
        }
        return ret.build();
    }

    public Stream<Location> pieceLocations(){
        return allLocations().filter(loc -> get(loc) != null);
    }

    public Stream<Location> pieceLocations(int color){
        return pieceLocations().filter(loc -> get(loc).getColor() == color);
    }

    public Stream<Piece> getPieces() {
        return Arrays.stream(board).flatMap(x -> Arrays.stream(x)).filter(Objects::nonNull);
    }
    public Stream<Piece> getPieces(int color){
        return getPieces().filter(x -> x.getColor() == color);
    }

    public double getHeuristicSum(int color){
        return getPieces(color).mapToDouble(piece -> piece.heuristicWeight()).sum();
    }

    public void move(Location previousLocation, Location newLocation){
        //if(!canMove(board[previousLocation.r][previousLocation.c],newLocation))
        //    return;
        board[newLocation.r][newLocation.c] = board[previousLocation.r][previousLocation.c];
        board[previousLocation.r][previousLocation.c] = null;
    }
    public boolean isEmpty(Location loc){
        return board[loc.r][loc.c] == null;
    }

    public boolean canMove(Piece piece, Location loc){
        if(!Board.isValid(loc))
            return false;
        if(isEmpty(loc))
            return true;
        if(board[loc.r][loc.c].getColor() != piece.getColor())
            return true;
        return false;
    }

    public static boolean isValid(Location loc){
        return loc.r >=0 && loc.c >= 0 && loc.r < SIZE && loc.c < SIZE;
    }

    public static int size(){
        return SIZE;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(board).replaceAll("],","]\n").replaceAll("null","_");
    }
}
