import java.util.*;

public class AStarSearch
{
    //initialize the goal to compare the current state to later
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};

    static class PuzzleNode
    {
        int[][] state;
        PuzzleNode parent;
        String move;
        int cost;
        int heuristic;

        PuzzleNode(int[][] state, PuzzleNode parent, String move, int cost)
        {
            this.state = state;
            this.parent = parent;
            this.move = move;
            this.cost = cost;
            this.heuristic = calculateHeuristic(state);
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
        //priority queue stores PuzzleNodes based on their cost and heuristic value
        PriorityQueue<PuzzleNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost + node.heuristic));
        Set<PuzzleNode> visited = new HashSet<>();
        priorityQueue.add(new PuzzleNode(initialState, null, null, 0));
        int count = 0;


        while (!priorityQueue.isEmpty())
        {

            PuzzleNode currentNode = priorityQueue.poll();
            visited.add(currentNode);

            //if current is the goal then print solution
            if (Arrays.deepEquals(currentNode.state, GOAL_STATE))
            {
                printSolution(currentNode);
                System.out.println("Nodes visited: " + count);
                return;
            }

            //adds new children to our queue
            List<PuzzleNode> children = getChildren(currentNode);
            for (PuzzleNode child : children)
            {
                if (!visited.contains(child))
                {
                    priorityQueue.add(child);
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

        // Possible moves: left, right, up, down, denoted by -1 or 1
        int[] row = {-1, 1, 0, 0};
        int[] col = {0, 0, -1, 1};
        String[] moves = {"UP", "DOWN", "LEFT", "RIGHT"};

        for (int i = 0; i < 4; i++)
        {
            int newRow = zeroRow + row[i];
            int newCol = zeroCol + col[i];

            //moves the piece to the zero space and creates the new zero space
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3)
            {
                int[][] newState = new int[3][3];
                for (int j = 0; j < 3; j++)
                {
                    newState[j] = Arrays.copyOf(state[j], 3);
                }
                newState[zeroRow][zeroCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                int newCost = node.cost + 1;
                PuzzleNode child = new PuzzleNode(newState, node, moves[i], newCost);
                children.add(child);
            }
        }
        return children;
    }

    private static int calculateHeuristic(int[][] state)
    {
        int count = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (state[i][j] != GOAL_STATE[i][j])
                {
                    count++;
                }
            }
        }
        return count;
    }

    //prints out the list of moves
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