import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class Piece {
    public static int W = 0;
    public static int B = 1;
    public static Piece WHITE;
    public static Piece BLACK;
    private int color;

    public Piece(int color){
        this.color = color;
    }
    public abstract double heuristicWeight();
    
    public abstract Stream<Location> nextMoves(Location current, Board board);
    public int getColor(){
        return color;
    }
    public abstract String getChar();

    @Override
    public String toString() {
        return color == W ?getChar():getChar().toUpperCase();
    }
}

class King extends Piece{

    public static Piece WHITE;
    public static Piece BLACK;

    static{
        WHITE = new King(W);
        BLACK = new King(B);
    }

    public King(int color){
        super(color);
    }

    @Override
    public double heuristicWeight() {
        return 10;
    }

    @Override
    public Stream<Location> nextMoves(Location current, Board board) {
        ArrayList<Location> nextMoves = new ArrayList<>();
        nextMoves.add(current.add(1,0));
        nextMoves.add(current.add(0,1));
        nextMoves.add(current.add(-1,0));
        nextMoves.add(current.add(0,-1));
        return nextMoves.parallelStream().filter(Board::isValid).filter(loc -> board.canMove(this,loc));
    }

    @Override
    public String getChar() {
        return "k";
    }
}

class Queen extends Piece{

    public static Piece WHITE;
    public static Piece BLACK;

    static{
        WHITE = new Queen(W);
        BLACK = new Queen(B);
    }
    private Queen(int color){
        super(color);
    }

    @Override
    public double heuristicWeight() {
        return 12;
    }

    @Override
    public Stream<Location> nextMoves(Location current, Board board) {
        ArrayList<Location> nextMoves = new ArrayList<>();
        // up
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(i,0);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // right
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(0,i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }
        // left
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(0,-i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // down
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(-i,0);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }


        // up right
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(i,i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // up left
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(i,-i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }
        // down left
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(-i,-i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // down right
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(-i,i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }
        return nextMoves.stream();
    }
    @Override
    public String getChar() {
        return "q";
    }
}

class Rook extends Piece{

    public static Piece WHITE;
    public static Piece BLACK;

    static{
        WHITE = new Rook(W);
        BLACK = new Rook(B);
    }
    private Rook(int color){
        super(color);
    }

    @Override
    public double heuristicWeight() {
        return 8;
    }

    @Override
    public Stream<Location> nextMoves(Location current, Board board) {
        ArrayList<Location> nextMoves = new ArrayList<>();
        // up
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(i,0);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // right
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(0,i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }
        // left
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(0,-i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // down
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(-i,0);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        return nextMoves.stream();
    }
    @Override
    public String getChar() {
        return "r";
    }
}

class Bishop extends Piece{

    public static Piece WHITE;
    public static Piece BLACK;

    static{
        WHITE = new Bishop(W);
        BLACK = new Bishop(B);
    }
    private Bishop(int color){
        super(color);
    }

    @Override
    public double heuristicWeight() {
        return 8;
    }

    @Override
    public Stream<Location> nextMoves(Location current, Board board) {

        ArrayList<Location> nextMoves = new ArrayList<>();
        // up right
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(i,i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // up left
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(i,-i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }
        // down left
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(-i,-i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }

        // down right
        for(int i = 1; i < board.size(); i++){
            Location temp = current.add(-i,i);
            if((!board.canMove(this,temp)) || (!board.isValid(temp)))
                break;
            nextMoves.add(temp);
        }
        return nextMoves.stream();
    }
    @Override
    public String getChar() {
        return "b";
    }
}

class Knight extends Piece{

    public static Piece WHITE;
    public static Piece BLACK;

    static{
        WHITE = new Knight(W);
        BLACK = new Knight(B);
    }
    private Knight(int color){
        super(color);
    }

    @Override
    public double heuristicWeight() {
        return 8;
    }

    @Override
    public Stream<Location> nextMoves(Location current, Board board) {
        ArrayList<Location> nextMoves = new ArrayList<>();
        nextMoves.add(current.add(2,1));
        nextMoves.add(current.add(2,-1));
        nextMoves.add(current.add(-2,1));
        nextMoves.add(current.add(-2,-1));
        return nextMoves.parallelStream().filter(Board::isValid).filter(loc -> board.canMove(this,loc));
    }
    @Override
    public String getChar() {
        return "n";
    }
}

class Pawn extends Piece{

    public static Piece WHITE;
    public static Piece BLACK;

    static{
        WHITE = new Pawn(W);
        BLACK = new Pawn(B);
    }
    private Pawn(int color){
        super(color);
    }

    @Override
    public double heuristicWeight() {
        return 5;
    }

    @Override
    public Stream<Location> nextMoves(Location current, Board board) {
        int direction = 0;
        if(getColor() == B){
            direction = 1;
        }
        else if(getColor() == W){
            direction = -1;
        }
        ArrayList<Location> nextMoves = new ArrayList<>();
        nextMoves.add(current.add(direction,0));
        if((getColor() == B && current.r == 1) ||
           (getColor() == W && current.r == 6)) {
                nextMoves.add(current.add(direction * 2, 1));
        }
        Location left = current.add(direction, -1);
        Location right = current.add(direction, 1);
        if(Board.isValid(left) && !board.isEmpty(left))
            nextMoves.add(left);
        if(Board.isValid(right) && !board.isEmpty(right))
            nextMoves.add(right);
        return nextMoves.parallelStream().filter(Board::isValid).filter(loc -> board.canMove(this,loc));
    }

    @Override
    public String getChar() {
        return "p";
    }
}
