import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        Board board = new Board();
        BoardState state = new BoardState(board,Piece.W);

        System.out.println(state);
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "500");
        while(true){
            long start = System.currentTimeMillis();
            state = state.nextBestState();
            System.out.println(state);
            System.out.printf("Time to calculate move: %.3f s\n",(System.currentTimeMillis()-start)/1000.0);
        }
    }
}
