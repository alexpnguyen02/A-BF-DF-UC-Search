import java.util.*;

public class BFSearch
{
    //initialize the goal to compare the current state to later
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};

    static class PuzzleNode
    {
        int[][] state;
        PuzzleNode parent;
        String move;

        PuzzleNode(int[][] state, PuzzleNode parent, String move)
        {
            this.state = state;
            this.parent = parent;
            this.move = move;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PuzzleNode that = (PuzzleNode) o;
            return Arrays.deepEquals(state, that.state);
        }

        @Override
        public int hashCode()
        {
            return Arrays.deepHashCode(state);
        }
    }

    public static void solvePuzzle(int[][] initialState)
    {
        //creates queue because the state we add first is the first we come back to
        Queue<PuzzleNode> queue = new LinkedList<>();
        Set<PuzzleNode> visited = new HashSet<>();
        queue.add(new PuzzleNode(initialState, null, null));
        int count = 0;

        while (!queue.isEmpty())
        {
            PuzzleNode currentNode = queue.poll();
            visited.add(currentNode);

            //if the current is the goal we print then leave the block
            if (Arrays.deepEquals(currentNode.state, GOAL_STATE))
            {
                printSolution(currentNode);
                System.out.println("Nodes visited: " + count);
                return;
            }

            List<PuzzleNode> children = getChildren(currentNode);
            //add children to queue if it's a newly created child
            for (PuzzleNode child : children)
            {
                if (!visited.contains(child))
                {
                    queue.add(child);
                    visited.add(child);
                }
                count++;
            }
        }
        System.out.println("Nodes visited: " + count);
        System.out.println("No solution found.");
    }

    //returns new puzzle state or a child after a move
    private static List<PuzzleNode> getChildren(PuzzleNode node)
    {
        List<PuzzleNode> children = new ArrayList<>();
        int[][] state = node.state;
        int zeroRow = 0, zeroCol = 0;

        //finds where 0 is in the puzzle
        outerloop:
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (state[i][j] == 0)
                {
                    zeroRow = i;
                    zeroCol = j;
                    break outerloop;
                }
            }
        }

        // Possible moves: left, right, up, down denoted by -1 or 1
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};
        String[] moves = {"UP", "DOWN", "LEFT", "RIGHT"};

        for (int i = 0; i < 4; i++) {
            int newRow = zeroRow + dRow[i];
            int newCol = zeroCol + dCol[i];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3)
            {
                int[][] newState = new int[3][3];
                for (int j = 0; j < 3; j++)
                {
                    newState[j] = Arrays.copyOf(state[j], 3);
                }
                newState[zeroRow][zeroCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                PuzzleNode child = new PuzzleNode(newState, node, moves[i]);
                children.add(child);
            }
        }

        return children;
    }

    //prints the list of moves
    private static void printSolution(PuzzleNode node)
    {
        List<String> moves = new ArrayList<>();
        while (node.parent != null)
        {
            moves.add(0, node.move);
            node = node.parent;
        }
        System.out.println("Solution:");
        for (String move : moves)
        {
            System.out.println(move);
        }
    }
}