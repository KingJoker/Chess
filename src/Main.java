import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        Board board = new Board();
        BoardState state = new BoardState(board,Piece.W);

        System.out.println(state);
        while(true){
            state = state.nextBestState();
            System.out.println(state);
        }
    }
}
