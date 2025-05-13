package maze;

import java.util.ArrayList;
import java.util.List;

public class PrimGenerator {

    private final int height;
    private final int width;
    private final int[] determined;
    private final boolean[][] besucht;

    public PrimGenerator(int height,int width, int... determined) {
        this.height = height;
        this.width = width;
        this.determined = determined;
        besucht = new boolean[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) besucht[i][j] = false;
    }

    public Maze generate() {
        Maze m = new Maze(height, width);
        List<Border> L = new ArrayList<>();
        RandomInts rand = new RandomInts(determined);

        int val = rand.nextInt(height * width) ;
        int x = val / width;
        int y = val % width;

        besucht[x][y] = true;

        L.addAll(new Cell(x, y).allBorders());

        while (!L.isEmpty()) {
            int k = rand.nextInt(L.size());
            Border b = L.get(k);

            L.remove(k);

            Cell c1 = b.getLeft();
            Cell c2 = b.getRight();

            if (!m.contains(c1) || !m.contains(c2) ) continue;
            if (!m.isOuterWall(b) && (!besucht[c1.getRow()][c1.getColumn()] || !besucht[c2.getRow()][c2.getColumn()])) {

                if (!besucht[c1.getRow()][c1.getColumn()]){
                    besucht[c1.getRow()][c1.getColumn()] = true;
                    L.addAll(c1.allBorders());
                }

                if (!besucht[c2.getRow()][c2.getColumn()]) {
                    besucht[c2.getRow()][c2.getColumn()] = true;
                    L.addAll(c2.allBorders());
                }
                m.tearDownThisWall(b);
            }
        }
        return m;
    }


}
