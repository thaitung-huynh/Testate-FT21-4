package maze;

public class Demo {
    public static final String LS = System.lineSeparator();
    public static void main(String[] args) {
        Maze m = Maze.parse("###########" + LS +
                "#+ + + + +#" + LS +
                "###########" + LS);
        System.out.println(m);
    }
}
