package maze;

public class Border {
    private final Cell cell;
    private final Direction dir;

    public Border(Cell cell, Direction dir) {
        this.cell = cell;
        this.dir = dir;
    }

    public Cell getCell() {
        return cell;
    }

    public Direction getDir() {
        return dir;
    }

    public Cell getLeft() {
        return switch (dir) {
            case NORTH -> cell.neighbor(Direction.NORTH);
            case SOUTH, EAST -> cell;
            case WEST -> cell.neighbor(Direction.WEST);
        };
    }

    public Cell getRight() {
        return switch (dir) {
            case NORTH, WEST -> cell;
            case SOUTH -> cell.neighbor(Direction.SOUTH);
            case EAST -> cell.neighbor(Direction.EAST);
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Border) {
            Border b = (Border) obj;
            return cell.equals(b.cell.neighbor(Direction.EAST)) ||
                    cell.equals(b.cell.neighbor(Direction.SOUTH)) ||
                    cell.equals(b.cell.neighbor(Direction.WEST)) ||
                    cell.equals(b.cell.neighbor(Direction.NORTH));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return cell.hashCode() + (dir == Direction.SOUTH || dir == Direction.EAST? 1 : 0);
    }

    @Override
    public String toString() {
        return "border at" + cell.toString() + "  " + dir.toString();
    }
}
