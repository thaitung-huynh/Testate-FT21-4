package maze;

import org.junit.Test;

import static maze.MazeTest.path;
import static org.junit.Assert.assertEquals;

public class PathSearchTest {
    @Test
    public void solve1() {
        Maze maze = MazeParser.parse(ExampleMazes.PATH_1);

        assertEquals(path(0, 0, 0, 1, 0, 2, 0, 3, 0, 4),
                     new PathSearcher().shortestPath(maze,
                                                     new Cell(0, 0),
                                                     new Cell(0, 4)));
    }

    @Test
    public void solve2() {
        Maze maze = MazeParser.parse(ExampleMazes.MAZE1);
        assertEquals(path(2, 2, 1, 2, 0, 2, 0, 3, 0, 4, 1, 4, 2, 4, 3, 4, 3, 5, 4, 5, 4, 4),
                     new PathSearcher().shortestPath(maze,
                                                     new Cell(2, 2),
                                                     new Cell(4, 4)));
    }

    @Test
    public void solve3() {
        Maze maze = MazeParser.parse(ExampleMazes.MAZE1_OPEN_ENDS);
        assertEquals(11,
                     new PathSearcher().shortestPath(maze,
                                                     new Cell(2, 2),
                                                     new Cell(4, 4))
                                       .size());
    }
}