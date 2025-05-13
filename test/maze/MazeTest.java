package maze;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MazeTest {
    private Maze maze;
    private final Cell cell00 = new Cell(0, 0);
    private final Cell cell01 = new Cell(0, 1);

    @Before
    public void init() {
        maze = new Maze(2, 2);
        maze.tearDownThisWall(cell00.getBorder(Direction.EAST));
        maze.tearDownThisWall(cell00.getBorder(Direction.SOUTH));
        maze.tearDownThisWall(cell01.getBorder(Direction.SOUTH));
    }

    @Test
    public void testInRange() {
        assertFalse(maze.contains(new Cell(0, -1)));
        assertFalse(maze.contains(new Cell(-1, 0)));
        assertFalse(maze.contains(new Cell(5, 5)));
        assertFalse(maze.contains(new Cell(6, 4)));
        assertTrue(maze.contains(new Cell(0, 0)));
        assertTrue(maze.contains(new Cell(maze.getHeight() - 1,
                                          maze.getWidth() - 1)));
    }

    @Test
    public void testGetWidth() {
        assertEquals(2, maze.getWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(2, maze.getHeight());
    }

    @Test
    public void testToString() {
        assertEquals(ExampleMazes.MAZE0, maze.toString());
    }

    @Test
    public void testModified() {
        Maze check = new Maze(6, 5);
        assertEquals(ExampleMazes.EMPTY, check.toString());
        for (int r = 0; r < check.getHeight(); ++r)
            for (int c = 0; c < check.getWidth(); ++c) {
                Cell cell = new Cell(r, c);
                for (Direction dir : Direction.values()) {
                    Cell targetCell = cell.neighbor(dir);
                    if (check.contains(targetCell))
                        check.tearDownThisWall(cell.getBorder(dir));
                }
            }
        assertEquals(ExampleMazes.FULL, check.toString());
    }

    @Test
    public void testOuterWall() {
        assertTrue(maze.isOuterWall(cell00.getBorder(Direction.NORTH)));
        assertFalse(maze.isOuterWall(cell00.getBorder(Direction.EAST)));
        assertFalse(maze.isOuterWall(cell00.getBorder(Direction.SOUTH)));
        assertTrue(maze.isOuterWall(cell00.getBorder(Direction.WEST)));
    }

    @Test
    public void testWalkableDirections() {
        assertEquals(List.of(Direction.EAST, Direction.SOUTH),
                     maze.walkableDirections(cell00));
        assertEquals(List.of(Direction.SOUTH, Direction.WEST),
                     maze.walkableDirections(cell01));
    }

    @Test
    public void testShortestPath() {
        assertEquals(path(1, 0, 0, 0, 0, 1, 1, 1),
                     maze.shortestPath(new Cell(1, 0), new Cell(1, 1)));
    }

    @Test
    public void testOpenDeadEnds() {
        maze.openDeadEnds();
        assertEquals(ExampleMazes.MAZE0, maze.toString());
    }

    public static List<Cell> path(int... pos) {
        if (pos.length % 2 != 0)
            throw new IllegalArgumentException("Need an even number of coordinates");
        List<Cell> result = new ArrayList<>(pos.length / 2);
        for (int i = 0; i < pos.length; i += 2)
            result.add(new Cell(pos[i], pos[i + 1]));
        return result;
    }
}