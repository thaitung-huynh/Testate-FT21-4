package maze;

import java.util.Random;

public class RandomInts {
    private final int[] values;
    private int index;

    public RandomInts(int... determined) {
        values = determined;
        index = 0;
    }

    public int nextInt(int limit) {
        if (limit < 0)
            throw new IllegalArgumentException("Invalid limit");

        if (index >= values.length) return new Random().nextInt(limit);
        else return values[index++] % limit;
    }

    public void reset() {
        index = 0;
    }
}
