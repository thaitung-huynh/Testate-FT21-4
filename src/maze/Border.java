package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                    cell.equals(b.cell.neighbor(Direction.NORTH)) || (cell.equals(b.cell) && dir == b.dir);
        }
        return false;
    }

    @Override
    public int hashCode() {
        Cell left = getLeft();
        Cell right = getRight();
        List<Integer> a = new ArrayList<>(List.of(left.getRow(), left.getColumn(), right.getRow(), right.getColumn()));
        a.sort(Integer::compare);

        return ((a.get(0) * 10 + a.get(1)) * 10 + a.get(2)) * 10 + a.get(3);
    }

    @Override
    public String toString() {
        return "border at" + cell.toString() + "  " + dir.toString();
    }
}
