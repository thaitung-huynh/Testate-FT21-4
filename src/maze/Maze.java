package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Maze {
    private static final int N = 0;
    private static final int E = 1;
    private static final int S = 2;
    private static final int W = 3;

    private final int height;
    private final int width;
    private final boolean[][][] stoneInDir;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        stoneInDir = new boolean[height + 1][width + 1][4];
        for (int  x = 0; x < height; ++x)
            for(int y = 0; y < width; ++y){
                stoneInDir[x][y][W] = true;
                stoneInDir[x][y][E] = true;
                stoneInDir[x][y][N] = true;
                stoneInDir[x][y][S] = true;
            }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean contains(Cell cell) {
        int x = cell.getRow();
        int y = cell.getColumn();
        return 0 <= x && x < height && 0 <= y && y < width;
    }

    public void tearDownThisWall(Border border) {
        Cell hier = border.getCell();
        Direction dir = border.getDir();

        if (!contains(hier) || isOuterWall(border)) return;

        switch (dir) {
            case NORTH -> {
                stoneInDir[hier.getRow()][hier.getColumn()][N] = false;
                stoneInDir[hier.getRow() - 1][hier.getColumn()][S] = false;
            }
            case SOUTH -> {
                stoneInDir[hier.getRow()][hier.getColumn()][S] = false;
                stoneInDir[hier.getRow() + 1][hier.getColumn()][N] = false;
            }
            case EAST -> {
                stoneInDir[hier.getRow()][hier.getColumn()][E] = false;
                stoneInDir[hier.getRow()][hier.getColumn() + 1][W] = false;
            }
            case WEST -> {
                stoneInDir[hier.getRow()][hier.getColumn()][W] = false;
                stoneInDir[hier.getRow()][hier.getColumn() - 1][E] = false;
            }
        }
    }

    public boolean canWalk(Cell cell, Direction dir) {
        return (switch (dir) {
            case NORTH -> !stoneInDir[cell.getRow()][cell.getColumn()][N];
            case SOUTH -> !stoneInDir[cell.getRow()][cell.getColumn()][S];
            case EAST -> !stoneInDir[cell.getRow()][cell.getColumn()][E];
            case WEST -> !stoneInDir[cell.getRow()][cell.getColumn()][W];
        });
    }

    public List<Direction> walkableDirections(Cell cell) {
        List<Direction> dirs = new ArrayList<>();
        if (canWalk(cell, Direction.NORTH)) dirs.add(Direction.NORTH);
        if (canWalk(cell, Direction.EAST)) dirs.add(Direction.EAST);
        if (canWalk(cell, Direction.SOUTH)) dirs.add(Direction.SOUTH);
        if (canWalk(cell, Direction.WEST)) dirs.add(Direction.WEST);
        return dirs;
    }

    public boolean isOuterWall(Border border) {
        Cell cell = border.getCell();
        if (!contains(cell)) return false;
        Direction dirBor = border.getDir();
        if (cell.getRow() == 0 && dirBor == Direction.NORTH) return true;

        if (cell.getRow() == height - 1 && dirBor == Direction.SOUTH) return true;

        if (cell.getColumn() == width - 1 && dirBor == Direction.EAST) return true;
        return cell.getColumn() == 0 && dirBor == Direction.WEST;
    }

    public Set<Border> getWalls() {
        Set<Border> walls = new HashSet<>();
        for (int x = 0; x < height; ++x)
            for (int y = 0; y < width; ++y) {
                Cell cell = new Cell(x, y);

                if (stoneInDir[x][y][N]) walls.add(new Border(cell, Direction.NORTH));

                if (stoneInDir[x][y][E]) walls.add(new Border(cell, Direction.EAST));

                if (stoneInDir[x][y][S]) walls.add(new Border(cell, Direction.SOUTH));

                if (stoneInDir[x][y][W]) walls.add(new Border(cell, Direction.WEST));

            }
        return walls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < 2 * width + 1; ++k) sb.append("#");
        sb.append("\r\n");

        for (int x = 0; x < height; ++x) {
            sb.append("#");
            for (int y = 0; y < width; ++y) {
                sb.append("+");
                if (stoneInDir[x][y][E]) sb.append("#");
                else sb.append(" ");
            }
            sb.append("\r\n");
            sb.append("#");
            for (int y = 0; y < width; ++y) {
                if (stoneInDir[x][y][S]) sb.append("##");
                else sb.append(" #");
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }

    public static Maze generate(int height, int width, int... determined) {
        return new PrimGenerator(height, width, determined).generate();
    }

    public static Maze parse(String text) {
        return MazeParser.parse(text);
    }

    public List<Cell> shortestPath(Cell start, Cell target) {
        return new PathSearcher().shortestPath(this, start, target);
    }

    public void openDeadEnds() {
        for (int x = 0; x < height; ++x)
            for(int y = 0; y < width; ++y) {
                int cnt = 0;
                for (int k = 0; k < 4; ++k) cnt += (stoneInDir[x][y][k] ? 1 : 0);
                if (cnt < 3) continue;
                if (stoneInDir[x][y][S] && stoneInDir[x][y][N]) {

                    if (stoneInDir[x][y][E] && !isOuterWall(new Border(new Cell(x, y), Direction.EAST)))
                        tearDownThisWall(new Border(new Cell(x, y), Direction.EAST));
                    else
                        if (stoneInDir[x][y][W] && !isOuterWall(new Border(new Cell(x, y), Direction.WEST)))
                            tearDownThisWall(new Border(new Cell(x, y), Direction.WEST));

                }
                if (stoneInDir[x][y][W] && stoneInDir[x][y][E]) {
                    if (stoneInDir[x][y][N] && !isOuterWall(new Border(new Cell(x, y), Direction.NORTH)))
                        tearDownThisWall(new Border(new Cell(x, y), Direction.NORTH));
                    else
                        if (stoneInDir[x][y][S] && !isOuterWall(new Border(new Cell(x, y), Direction.SOUTH)))
                            tearDownThisWall(new Border(new Cell(x, y), Direction.SOUTH));
                }

            }
    }
}
