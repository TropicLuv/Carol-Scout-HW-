package pgdp.carol;
import static pgdp.MiniJava.*;
import static pgdp.carol.CarolPfadfinder.*;

public class Main {

    public static void main(String[] args) {
//        int a = getMinimalStepsAndTurns(3,4,0,5,5);
//        System.out.println(a);

//        boolean t = true;
//        boolean f = false;
//        System.out.println(t|f);
        char [] arr = new char [11];
        System.out.println(findInstructionsHelper(new int[][] {{2,4,4,5},{9,9,9,7},{9,9,9,9},{9,9,9,6}},0,0,1,5,3,3,arr,1,'s'));

//        System.out.println(findInstructionsHelper(new int[][] {{0,8,9},{0,9,0},{0,9,0},{0,9,0}},0,0,0,10,0,3,arr,1,'s'));

//        System.out.println(findInstructionsHelper(new int[][] {{0,2,0},{0,2,0},{0,2,0}},0,0,1,2,0,2,arr,1,'s'));
//        System.out.println(findOptimalSolution(new int [][]{{0,0,0,0},{3,3,3,0},{3,0,3,0},{3,3,3,0}},2,1,1,3,0,3,20));



    }
}
