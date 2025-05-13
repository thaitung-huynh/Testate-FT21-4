package maze;

public class Border {
    private final Cell cell;
    private final Direction dir;

    public Border(Cell cell, Direction dir) {
        this.cell = cell;
        this.dir = dir;
    }

    public Cell getLeft() {
        switch (dir) {
            case NORTH:
                return new Cell(cell.getRow(), cell.getColumn() - 1);
            case SOUTH:
                return new Cell(cell.getRow(),cell.getColumn() + 1);
            case EAST:
                return new Cell(cell.getRow() - 1, cell.getColumn() + 1);
            case WEST:
                return new Cell(cell.getRow() + 1, cell.getColumn() - 1);
        }
        return null;
    }

    public Cell getRight() {

    }

    @Override
    public boolean equals(Object obj) {

    }

    @Override
    public int hashCode() {

    }

    @Override
    public String toString() {

    }
}
