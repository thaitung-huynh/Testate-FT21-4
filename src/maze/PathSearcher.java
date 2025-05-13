package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PathSearcher {

    private class Pair {
        int x, y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final Stack<Cell> stack = new Stack<>();
    private boolean[][] passed;

    public List<Cell> shortestPath(Maze maze, Cell start, Cell target) {

        int m = maze.getHeight();
        int n = maze.getWidth();

        Pair[][] dad = new Pair[m][n];
        passed = new boolean[m][n];


        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) passed[i][j] = false;


        passed[start.getRow()][start.getColumn()] = true;
        dad[start.getRow()][start.getColumn()] = null;

        stack.push(start);

        while (!stack.isEmpty()) {
            Cell curCell = stack.pop();

            if (curCell.equals(target)) break;

            if (maze.canWalk(curCell, Direction.NORTH)) {
                Cell next = curCell.neighbor(Direction.NORTH);
                if (passed[next.getRow()][next.getColumn()]) continue;
                stack.push(next);
                dad[next.getRow()][next.getColumn()] = new Pair(curCell.getRow(), curCell.getColumn());
            }

            if (maze.canWalk(curCell, Direction.SOUTH)) {
                Cell next = curCell.neighbor(Direction.SOUTH);
                if (passed[next.getRow()][next.getColumn()]) continue;
                stack.push(next);
                dad[next.getRow()][next.getColumn()] = new Pair(curCell.getRow(), curCell.getColumn());
            }

            if (maze.canWalk(curCell, Direction.WEST)) {
                Cell next = curCell.neighbor(Direction.WEST);
                if (passed[next.getRow()][next.getColumn()]) continue;
                stack.push(next);
                dad[next.getRow()][next.getColumn()] = new Pair(curCell.getRow(), curCell.getColumn());
            }

            if (maze.canWalk(curCell, Direction.EAST)) {
                Cell next = curCell.neighbor(Direction.EAST);
                if (passed[next.getRow()][next.getColumn()]) continue;
                stack.push(next);
                dad[next.getRow()][next.getColumn()] = new Pair(curCell.getRow(), curCell.getColumn());
            }
        }

        List<Cell> path = new ArrayList<>();

        int traceX = target.getRow();
        int traceY = target.getColumn();

        while (dad[traceX][traceY] != null) {
            path.add(new Cell(traceX, traceY));
            int newX = dad[traceX][traceY].x;
            int newY = dad[traceX][traceY].y;
            traceX = newX;
            traceY = newY;
        }

        return path.reversed();
    }
}
