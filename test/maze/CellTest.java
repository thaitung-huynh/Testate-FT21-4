package maze;

import org.junit.Test;

import static maze.Direction.EAST;
import static maze.Direction.NORTH;
import static maze.Direction.SOUTH;
import static maze.Direction.WEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CellTest {
    private final Cell cell00 = new Cell(0, 0);
    private final Cell cell01 = new Cell(0, 1);
    private final Cell cell32 = new Cell(3, 2);
    private final Cell cell33 = new Cell(3, 3);
    private final Cell cell34 = new Cell(3, 4);
    private final Cell cell23 = new Cell(2, 3);
    private final Cell cell43 = new Cell(4, 3);

    @Test
    public void getRowTest() {
        assertEquals(0, cell00.getRow());
    }

    @Test
    public void getColumnTest() {
        assertEquals(0, cell00.getColumn());
    }

    @Test
    public void neighborTest() {
        assertEquals(0, cell00.neighbor(SOUTH).getColumn());
        assertEquals(-1, cell00.neighbor(WEST).getColumn());
        assertEquals(0, cell00.neighbor(NORTH).getColumn());
        assertEquals(1, cell00.neighbor(EAST).getColumn());
        assertEquals(1, cell00.neighbor(SOUTH).getRow());
        assertEquals(0, cell00.neighbor(WEST).getRow());
        assertEquals(-1, cell00.neighbor(NORTH).getRow());
        assertEquals(0, cell00.neighbor(EAST).getRow());

        assertEquals(cell23, cell33.neighbor(NORTH));
        assertEquals(cell34, cell33.neighbor(EAST));
        assertEquals(cell43, cell33.neighbor(SOUTH));
        assertEquals(cell32, cell33.neighbor(WEST));
    }

    @Test
    public void equalsTest() {
        assertNotEquals(cell00, cell01);
        assertNotEquals(null, cell00);
        assertEquals(cell00, new Cell(0, 0));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(cell01.hashCode(), cell01.hashCode());
        assertEquals(cell01.hashCode(), new Cell(0, 1).hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals("(0, 0)", cell00.toString());
        assertEquals("(0, 1)", cell01.toString());
    }

    @Test
    public void testGetBorder() {
        assertEquals(new Border(cell32, EAST), cell33.getBorder(WEST));
        assertEquals(new Border(cell34, WEST), cell33.getBorder(EAST));
        assertEquals(new Border(cell23, SOUTH), cell33.getBorder(NORTH));
        assertEquals(new Border(cell43, NORTH), cell33.getBorder(SOUTH));
    }
}