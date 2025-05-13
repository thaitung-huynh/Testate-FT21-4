package maze.gui;

import maze.Border;
import maze.Cell;
import maze.Maze;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY;

public class View extends JPanel {
    private static final Point2D NULL = new Point2D.Double(0., 0.);
    private static final Color BACKGROUND_COLOR = Color.gray;
    private static final Color WALL_COLOR = Color.black;
    private static final Color CELL_COLOR = Color.white;
    private static final Color PATH_COLOR = Color.red;
    private transient Maze maze;
    private transient List<Cell> path;

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        path = null;
        repaint();
    }

    public void setPath(List<Cell> path) {
        this.path = path;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
        g2d.setStroke(new BasicStroke(1f));
        drawBackground(g2d);
        if (maze != null) {
            drawMaze(g2d);
            drawPath(g2d);
        }
    }

    private void drawPath(Graphics2D g2d) {
        if (path == null || path.isEmpty()) return;
        Path2D draw = new Path2D.Double();
        final Iterator<Cell> it = path.iterator();
        final Cell startCell = it.next();
        final Point2D start = toScreen(startCell.getColumn() + 0.5, startCell.getRow() + 0.5);
        draw.moveTo(start.getX(), start.getY());
        while (it.hasNext()) {
            final Cell nextCell = it.next();
            final Point2D nextPoint = toScreen(nextCell.getColumn() + 0.5, nextCell.getRow() + 0.5);
            draw.lineTo(nextPoint.getX(), nextPoint.getY());
        }
        g2d.setColor(PATH_COLOR);
        g2d.draw(draw);
    }

    private void drawMaze(Graphics2D g2d) {
        final Point2D c1 = toScreen(0, 0);
        final Point2D c2 = toScreen(maze.getWidth(), maze.getHeight());
        g2d.setColor(CELL_COLOR);
        g2d.fill(new Rectangle2D.Double(c1.getX(), c1.getY(), c2.getX() - c1.getX(), c2.getY() - c1.getY()));
        g2d.setColor(WALL_COLOR);
        for (Border wall : maze.getWalls()) {
            final int x = wall.getRight().getColumn();
            final int y = wall.getRight().getRow();
            final Point2D p1 = toScreen(x, y);
            final Point2D p2 = wall.getLeft().getColumn() == x ? toScreen(x + 1., y) : toScreen(x, y + 1.);
            g2d.draw(new Double(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
        }
    }

    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    Point2D toModel(int x, int y) {
        if (maze == null) return NULL;
        final Dimension size = getSize();
        final double scale = getScale();
        final double xs = (x - 0.5 * size.getWidth()) / scale + 0.5 * maze.getWidth();
        final double ys = (y - 0.5 * size.getHeight()) / scale + 0.5 * maze.getHeight();
        return new Point2D.Double(xs, ys);
    }

    Point2D toScreen(double x, double y) {
        if (maze == null) return NULL;
        final Dimension size = getSize();
        final double scale = getScale();
        final double xm = (x - 0.5 * maze.getWidth()) * scale + 0.5 * size.getWidth();
        final double ym = (y - 0.5 * maze.getHeight()) * scale + 0.5 * size.getHeight();
        return new Point2D.Double(xm, ym);
    }

    private double getScale() {
        if (maze == null)
            return 1.;
        final Dimension size = getSize();
        return 0.9 * Math.min(size.width / (double) maze.getWidth(),
                              size.height / (double) maze.getHeight());
    }
}
