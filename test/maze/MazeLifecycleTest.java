package maze;

import org.junit.Test;

import java.util.List;

import static maze.ExampleMazes.LS;
import static maze.MazeTest.path;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MazeLifecycleTest {
    @Test
    public void horizontalMaze() {
        Maze maze = Maze.generate(ExampleMazes.PATH_1_HEIGHT, ExampleMazes.PATH_1_WIDTH, 0, 0);
        assertEquals(ExampleMazes.PATH_1, maze.toString());

        Maze parsedMaze = Maze.parse(maze.toString());
        assertEquals(ExampleMazes.PATH_1, parsedMaze.toString());

        assertEquals(path(0, 0, 0, 1, 0, 2, 0, 3, 0, 4),
                     parsedMaze.shortestPath(new Cell(0, 0),
                                             new Cell(0, 4)));
    }

    @Test
    public void verticalMaze() {
        Maze maze = Maze.generate(ExampleMazes.PATH_2_HEIGHT, ExampleMazes.PATH_2_WIDTH, 0, 0);
        assertEquals(ExampleMazes.PATH_2, maze.toString());

        Maze parsedMaze = Maze.parse(maze.toString());
        assertEquals(ExampleMazes.PATH_2, parsedMaze.toString());

        assertEquals(path(0, 0, 1, 0, 2, 0, 3, 0, 4, 0),
                     parsedMaze.shortestPath(new Cell(0, 0),
                                             new Cell(4, 0)));
    }

    @Test
    public void testMaze1() {
        Maze maze = Maze.generate(ExampleMazes.MAZE1_HEIGHT, ExampleMazes.MAZE1_WIDTH, ExampleMazes.MAZE1_DET);
        assertEquals(ExampleMazes.MAZE1, maze.toString());
        Maze parsedMaze = Maze.parse(maze.toString());
        assertEquals(ExampleMazes.MAZE1, parsedMaze.toString());

        assertEquals(path(4, 4, 4, 5, 3, 5, 3, 4, 2, 4, 1, 4, 0, 4, 0, 3, 0, 2, 1, 2, 2, 2),
                     parsedMaze.shortestPath(new Cell(4, 4),
                                             new Cell(2, 2)));
    }

    @Test
    public void testMaze1WithOpenEnds() {
        Maze maze = Maze.generate(ExampleMazes.MAZE1_HEIGHT, ExampleMazes.MAZE1_WIDTH, ExampleMazes.MAZE1_DET);
        assertEquals(ExampleMazes.MAZE1, maze.toString());

        maze.openDeadEnds();
        assertEquals(ExampleMazes.MAZE1_OPEN_ENDS, maze.toString());

        Maze parsedMaze = Maze.parse(maze.toString());
        assertEquals(ExampleMazes.MAZE1_OPEN_ENDS, parsedMaze.toString());

        assertEquals(11,
                     parsedMaze.shortestPath(new Cell(4, 4),
                                             new Cell(2, 2))
                               .size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void generatorHeightZero() {
        Maze.generate(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generatorWidthZero() {
        Maze.generate(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserEmptyMaze() {
        Maze.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserSymbol() {
        Maze.parse("###" + LS +
                   "#a#" + LS +
                   "###");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserFormatShort() {
        Maze.parse("###" + LS +
                   "##" + LS +
                   "###");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserFormatLong() {
        Maze.parse("###" + LS +
                   "#+ #" + LS +
                   "###");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserFenceBottom() {
        Maze.parse("###" + LS +
                   "#+#" + LS +
                   "#+#");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserFenceLeft() {
        Maze.parse("###" + LS +
                   "++#" + LS +
                   "###");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserFenceRight() {
        Maze.parse("###" + LS +
                   "#++" + LS +
                   "###");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserFenceTop() {
        Maze.parse("#+#" + LS +
                   "#+#" + LS +
                   "###");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserFenceTopRight() {
        Maze.parse("##+" + LS +
                   "#+#" + LS +
                   "###");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserWrongSymbol() {
        Maze.parse("###" + LS +
                   "#+#" + LS +
                   "#+#" + LS +
                   "#+#" + LS +
                   "###");
    }

    @Test
    public void parserEmptyRows() {
        // no exception should be thrown in the following statement
        Maze.parse("###" + LS + LS + LS +
                   "#+#" + LS +
                   "###" + LS + LS + LS);
        assertTrue(true);
    }

    @Test(timeout = 1000)
    public void randomMazePathLength() {
        Maze maze = Maze.generate(10, 10);
        final int len1 = maze.shortestPath(new Cell(0, 0),
                                           new Cell(9, 9))
                             .size();
        maze.openDeadEnds();
        final int len2 = maze.shortestPath(new Cell(0, 0),
                                           new Cell(9, 9))
                             .size();
        assertTrue(len1 >= len2);
    }

    public void unsolvableMaze() {
        Maze maze = Maze.parse("#####" + LS +
                               "#+#+#" + LS +
                               "#####" + LS);
        assertTrue(maze.shortestPath(new Cell(0, 0),
                                     new Cell(0, 1)).isEmpty());
    }

    @Test(timeout = 10_000)
    public void bigMaze() {
        int height = 100;
        int width = 100;
        final Maze maze = Maze.generate(height, width);
        final List<Cell> path1 = maze.shortestPath(new Cell(0, 0),
                                                   new Cell(height - 1, width - 1));
        assertFalse(path1.isEmpty());
        maze.openDeadEnds();
        final List<Cell> path2 = maze.shortestPath(new Cell(0, 0),
                                                   new Cell(height - 1, width - 1));
        assertFalse(path1.isEmpty());
        assertTrue(path1.size() >= path2.size());
    }

}
