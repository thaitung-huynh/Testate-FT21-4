package maze;

import java.util.*;

public class PathSearcher {

    private class NodeInStack {
        int value, x, y;
        public NodeInStack(int x, int y, int value) {
            this.value = value;
            this.x = x;
            this.y = y;
        }
    }
    // BFS
    private final Queue<NodeInStack> queue = new LinkedList<>();

    public List<Cell> shortestPath(Maze maze, Cell start, Cell target) {

        int m = maze.getHeight();
        int n = maze.getWidth();

        Cell[][] dad = new Cell[m][n];
        int[][] D = new int[m][n];


        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) D[i][j] = 1000000; // Init Max Value


        if (maze.contains(start) && maze.contains(target)){
            queue.add(new NodeInStack(start.getRow(), start.getColumn(), 0));
            D[start.getRow()][start.getColumn()] = 0;
        }

        while (!queue.isEmpty()) {
            NodeInStack curNode = queue.poll();

            if (D[curNode.x][curNode.y] != curNode.value) continue;

            Cell curCell = new Cell(curNode.x, curNode.y);


            if (curCell.equals(target)) break;


            if (maze.canWalk(curCell, Direction.NORTH)) {
                Cell next = curCell.neighbor(Direction.NORTH);
                config(maze, dad, D, curCell, next);
            }

            if (maze.canWalk(curCell, Direction.SOUTH)) {
                Cell next = curCell.neighbor(Direction.SOUTH);
                config(maze, dad, D, curCell, next);
            }

            if (maze.canWalk(curCell, Direction.WEST)) {
                Cell next = curCell.neighbor(Direction.WEST);
                config(maze, dad, D, curCell, next);

            }

            if (maze.canWalk(curCell, Direction.EAST)) {
                Cell next = curCell.neighbor(Direction.EAST);
                config(maze, dad, D, curCell, next);
            }
        }

        List<Cell> path = new ArrayList<>();

        while (target != null && maze.contains(target) && (D[target.getRow()][target.getColumn()] != 1000000)) {
            path.add(target);
            target = dad[target.getRow()][target.getColumn()];
        }

        return path.reversed();
    }

    private void config(Maze maze, Cell[][] dad, int[][] d, Cell curCell, Cell next) {
        if (maze.contains(next) && (d[next.getRow()][next.getColumn()] > d[curCell.getRow()][curCell.getColumn()] + 1)) {

            d[next.getRow()][next.getColumn()] = d[curCell.getRow()][curCell.getColumn()] + 1;

            queue.add(new NodeInStack(next.getRow(), next.getColumn(), d[next.getRow()][next.getColumn()]));

            dad[next.getRow()][next.getColumn()] = curCell;

        }
    }
}
