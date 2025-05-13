package maze;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpenDeadEndsTest {
    @Test
    public void openDeadEnds1() {
        Maze maze = Maze.generate(ExampleMazes.PATH_1_HEIGHT, ExampleMazes.PATH_1_WIDTH, 0, 0);
        maze.openDeadEnds();
        assertEquals(ExampleMazes.PATH_1, maze.toString());
    }

    @Test
    public void openDeadEnds2() {
        Maze maze = Maze.generate(ExampleMazes.MAZE1_HEIGHT, ExampleMazes.MAZE1_WIDTH, ExampleMazes.MAZE1_DET);
        maze.openDeadEnds();
        assertEquals(ExampleMazes.MAZE1_OPEN_ENDS, maze.toString());
    }

    @Test
    public void openDeadEnds3() {
        Maze maze = Maze.generate(ExampleMazes.MAZE2_HEIGHT, ExampleMazes.MAZE2_WIDTH, ExampleMazes.MAZE2_DET);
        maze.openDeadEnds();
        assertEquals(ExampleMazes.MAZE2_OPEN_ENDS, maze.toString());
    }
}