import java.util.*;

public class ProblemSolver
{
    public static void main(String[] args)
    {
        //timer when run starts
        long startTime = System.nanoTime();
        int[][] startingStateBoard = initState();

        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++)
            {
                System.out.print (startingStateBoard[r][c]);
            }
            System.out.println();
        }

        //DFSearch.solvePuzzle(startingStateBoard);
        //UCSearch.solvePuzzle(startingStateBoard);
        //BFSearch.solvePuzzle(startingStateBoard);
        //AStarSearch.solvePuzzle(startingStateBoard);

        //timer when run ends
        long endTime = System.nanoTime();

        //calculates the runtime using start and end times
        long total = endTime - startTime;
        System.out.println ("runtime in seconds: " + ((double)total)/1000000000);
    }

    private static int[][] initState( )
    {
        List<Integer> choice = new ArrayList<Integer>();

        //list of ints from 0-8
        for (int i = 0; i <= 8; i++)
        {
            choice.add(i);
        }

        Random rand = new Random();
        int[][] startingStateBoard = new int[3][3];
        int i = 0;
        int row = 0;
        //populates board from choice array and removes the random selection from choice
        while (!choice.isEmpty())
        {
            for (int j = 0; j < 3; j++)
            {
                int tile = rand.nextInt(9 - i);
                startingStateBoard[row][j] = choice.get(tile);
                choice.remove(tile);
                i++;
            }
            row++;
        }

        return startingStateBoard;  
    }

}
