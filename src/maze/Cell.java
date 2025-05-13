package maze;

import java.util.List;

public class Cell {
    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Cell) {
            Cell cell = (Cell) obj;
            return row == cell.row && col == cell.col;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return row + col;
    }


    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    public Cell neighbor(Direction dir) {
        switch (dir) {
            case NORTH:
                return new Cell(row - 1, col);
            case SOUTH:
                return new Cell(row + 1, col);
            case EAST:
                return new Cell(row, col + 1);
            case WEST:
                return new Cell(row, col - 1);
            default:
                return null;
        }
    }

    public Border getBorder(Direction dir) {
        return new Border(this, dir);
    }

    public List<Border> allBorders() {
        return List.of(getBorder(Direction.WEST), getBorder(Direction.EAST), getBorder(Direction.NORTH), getBorder(Direction.SOUTH));
    }
}
