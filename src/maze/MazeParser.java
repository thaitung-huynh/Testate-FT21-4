package maze;

import java.util.ArrayList;
import java.util.List;

public class MazeParser {
    public static Maze parse(String text) {
        String[] allLines = text.split("\\R");

        List<String> nonEmptyLines = new ArrayList<>();
        if (text.equals(""))
            throw new IllegalArgumentException("Empty Maze");
        for (String line : allLines)
            if (!line.trim().isEmpty()) nonEmptyLines.add(line);

        for (String nonEmptyLine : nonEmptyLines)
            if (nonEmptyLine.length() != nonEmptyLines.getFirst().length())
                throw new IllegalArgumentException("Maze contains different lines");

        for (String line : nonEmptyLines) {
            for (char c : line.toCharArray()) {
                if (c != '#' && c != ' ' && c != '+')
                    throw new IllegalArgumentException("Maze contains invalid characters");
            }
            if (line.charAt(0) != '#' || line.charAt(line.length()-1) != '#')
                throw new IllegalArgumentException("Maze contains invalid characters");
        }

        for (char c: nonEmptyLines.getFirst().toCharArray())
            if (c != '#')
                throw new IllegalArgumentException("Maze contains invalid border");
        for (char c: nonEmptyLines.getLast().toCharArray())
            if (c != '#')
                throw new IllegalArgumentException("Maze contains invalid border");

        Maze result = new Maze(nonEmptyLines.size() / 2, nonEmptyLines.getFirst().length() / 2);



        for (int r = 1; r < nonEmptyLines.size() - 1; r += 2) {
            String row = nonEmptyLines.get(r);
            String row1 = nonEmptyLines.get(r + 1);
            for (int c = 1; c < row.length() - 1; c += 2) {
                if (row.charAt(c) != '+') continue;
                if (row.charAt(c + 1) == ' ')
                    result.tearDownThisWall(new Border(new Cell(r / 2, c / 2), Direction.EAST));

                if (row1.charAt(c) == ' ') result.tearDownThisWall(new Border(new Cell(r / 2, c / 2), Direction.SOUTH));
                if (row1.charAt(c) == '+')
                    throw new IllegalArgumentException("Maze contains invalid border");
            }
        }

        return result;
    }
}
