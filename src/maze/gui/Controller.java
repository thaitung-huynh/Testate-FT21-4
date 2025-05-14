package maze.gui;

import maze.Cell;
import maze.Maze;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

public class Controller extends MouseAdapter {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private final View view;
    private Cell startCell;

    public Controller(View view) {
        this.view = view;
    }

    void createMaze(String height, String width) {
        try {
            view.setMaze(Maze.generate(Integer.parseInt(height),
                                       Integer.parseInt(width)));
        }
        catch (IllegalArgumentException ex) {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    void openDeadEnds() {
        if (view.getMaze() != null) {
            view.getMaze().openDeadEnds();
            view.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (view.getMaze() == null) return;
        final Point2D p = view.toModel(e.getX(), e.getY());
        LOGGER.info(() -> "clicked " + p);
        if (startCell == null)
            startCell = cell(p);
        else {
            final Cell targetCell = cell(p);
            view.setPath(view.getMaze().shortestPath(startCell, targetCell));
            System.out.println(view.getMaze());
            startCell = null;
        }
    }

    private static Cell cell(Point2D p) {
        return new Cell((int) Math.floor(p.getY()), (int) Math.floor(p.getX()));
    }
}
