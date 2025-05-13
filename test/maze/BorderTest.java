package maze;

import org.junit.Test;

import java.util.List;

import static maze.Direction.EAST;
import static maze.Direction.NORTH;
import static maze.Direction.SOUTH;
import static maze.Direction.WEST;
import static org.junit.Assert.assertEquals;

public class BorderTest {
    private final Cell cell02 = new Cell(0, 2);
    private final Cell cell11 = new Cell(1, 1);
    private final Cell cell12 = new Cell(1, 2);
    private final Cell cell13 = new Cell(1, 3);
    private final Cell cell22 = new Cell(2, 2);
    private final Cell cell21 = new Cell(2, 1);

    @Test
    public void testEquals() {
        assertEquals(new Border(cell12, SOUTH),
                     new Border(cell22, NORTH));
        assertEquals(new Border(cell12, EAST),
                     new Border(new Cell(1, 3), WEST));
    }

    @Test
    public void testAllBorders() {
        assertEquals(List.of(new Border(cell12, SOUTH),
                             new Border(cell22, EAST),
                             new Border(cell22, SOUTH),
                             new Border(cell21, EAST)),
                     cell22.allBorders());
    }

    @Test
    public void testGetLeft() {
        assertEquals(cell12, cell12.getBorder(EAST).getLeft());
        assertEquals(cell11, cell12.getBorder(WEST).getLeft());
        assertEquals(cell02, cell12.getBorder(NORTH).getLeft());
        assertEquals(cell12, cell12.getBorder(SOUTH).getLeft());
    }

    @Test
    public void testGetRight() {
        assertEquals(cell13, cell12.getBorder(EAST).getRight());
        assertEquals(cell12, cell12.getBorder(WEST).getRight());
        assertEquals(cell12, cell12.getBorder(NORTH).getRight());
        assertEquals(cell22, cell12.getBorder(SOUTH).getRight());
    }

    @Test
    public void testHashCode() {
        assertEquals(new Border(cell12, SOUTH).hashCode(),
                     new Border(cell22, NORTH).hashCode());
        assertEquals(new Border(cell12, EAST).hashCode(),
                     new Border(new Cell(1, 3), WEST).hashCode());
    }
}
