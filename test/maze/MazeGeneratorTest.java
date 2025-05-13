package maze;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MazeGeneratorTest {
    @Test
    public void generateMaze1() {
        final PrimGenerator generator = new PrimGenerator(ExampleMazes.MAZE0_HEIGHT,
                                                          ExampleMazes.MAZE0_WIDTH,
                                                          ExampleMazes.MAZE0_DET);
        Maze maze1 = generator.generate();
        assertEquals(ExampleMazes.MAZE0, maze1.toString());
        Maze maze2 = generator.generate();
        assertEquals(ExampleMazes.MAZE0, maze2.toString());
    }

    @Test
    public void generateMaze2() {
        final PrimGenerator generator = new PrimGenerator(ExampleMazes.MAZE1_HEIGHT,
                                                          ExampleMazes.MAZE1_WIDTH,
                                                          ExampleMazes.MAZE1_DET);
        Maze maze1 = generator.generate();
        assertEquals(ExampleMazes.MAZE1, maze1.toString());
        Maze maze2 = generator.generate();
        assertEquals(ExampleMazes.MAZE1, maze2.toString());
    }

    @Test
    public void generateMaze3() {
        final PrimGenerator generator = new PrimGenerator(ExampleMazes.MAZE2_HEIGHT,
                                                          ExampleMazes.MAZE2_WIDTH,
                                                          ExampleMazes.MAZE2_DET);
        Maze maze1 = generator.generate();
        assertEquals(ExampleMazes.MAZE2, maze1.toString());
        Maze maze2 = generator.generate();
        assertEquals(ExampleMazes.MAZE2, maze2.toString());
    }
}