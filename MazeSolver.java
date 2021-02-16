import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeSolver {
    public final static int ROWS = 5;
    public final static int COLUMNS = 7;

    public static void main(String[] args) {
        int[][] maze = new int[ROWS][COLUMNS];
        Random random = new Random();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                maze[i][j] = random.nextInt(2);
            }
        }
        maze[0][0] = 0;
        maze[ROWS - 1][COLUMNS - 1] = 0;
        printMaze(maze);
        boolean solve = isSolvable(maze);
        System.out.println(solve);
        if (solve) {
            printMaze(maze);
        }
    }

    public static boolean isSolvable(int[][] maze) {
        if (maze[0][0] == 1 || maze[ROWS - 1][COLUMNS - 1] == 1) {
            return false;
        }
        Stack<Integer> places = new Stack<>();
        int row = 0;
        int column = 0;
        int gScore = 0;
        boolean solveAble = false;
        while (true) {
            maze[row][column] = 1;
            if (row == ROWS - 1 && column == COLUMNS - 1) {
                maze[row][column] = 3;
                solveAble = true;
                break;
            }
            List<Integer> scores = new ArrayList<>();
            List<Integer> options = new ArrayList<>();
            if (row - 1 >= 0 && maze[row - 1][column] == 0) {
                scores.add(gScore + h(COLUMNS - 1, ROWS - 1, column, row - 1));
                options.add((row - 1) * COLUMNS + column);
            }
            if (row + 1 < ROWS && maze[row + 1][column] == 0) {
                scores.add(gScore + h(COLUMNS - 1, ROWS - 1, column, row + 1));
                options.add((row + 1) * COLUMNS + column);
            }
            if (column - 1 >= 0 && maze[row][column - 1] == 0) {
                scores.add(gScore + h(COLUMNS - 1, ROWS - 1, column - 1, row));
                options.add((row) * COLUMNS + column - 1);
            }
            if (column + 1 < COLUMNS && maze[row][column + 1] == 0) {
                scores.add(gScore + h(COLUMNS - 1, ROWS - 1, column + 1, row));
                options.add((row) * COLUMNS + column + 1);
            }
            if (options.size() == 0) {
                if (places.isEmpty()) {
                    solveAble = false;
                    break;
                }
                int place = places.pop();
                row = place / COLUMNS;
                column = place % COLUMNS;
                continue;
            }
            int miniScore = scores.get(0);
            int option = 0;
            for (int i = 1; i < scores.size(); i++) {
                if (scores.get(i) < miniScore) {
                    option = i;
                    miniScore = scores.get(i);
                }
            }
            places.push((row) * COLUMNS + column);
            row = options.get(option) / COLUMNS;
            column = options.get(option) % COLUMNS;
            gScore++;
        }
        for (int place : places) {
            int r = place / COLUMNS;
            int c = place % COLUMNS;
            maze[r][c] = 3;
        }
        return solveAble;
    }

    public static int h(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static void printMaze(int[][] maze) {
        for (int i = 0; i < ROWS; i++) {
            String row = "[ ";
            for (int j = 0; j < COLUMNS; j++) {
                row += maze[i][j];
                if (j != COLUMNS - 1) {
                    row += ", ";
                }
            }
            row += "]";
            System.out.println(row);
        }
    }

}
