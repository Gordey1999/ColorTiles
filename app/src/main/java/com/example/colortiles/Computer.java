package com.example.colortiles;



public class Computer {

    private boolean[][] map;
    private boolean[][] result1, result2;

    public Computer(boolean[][] map) {
        this.map = new boolean[4][4];
        this.result1 = new boolean[4][4];
        this.result2 = new boolean[4][4];

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                this.map[i][j] = map[i][j];
    }

    private void mapChangeColor(int i, int j) {
        for (int q = 0; q < 4; q++) {
            map[i][q] = !map[i][q];
            map[q][j] = !map[q][j];
        }
        map[i][j] = !map[i][j];
    }

    private boolean hasWon() {
        int count = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (map[i][j])
                    count++;
        if (count == 16 || count == 0)
            return true;
        return false;
    }

    private int stepsCount(boolean[][] steps) {
        int count = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (steps[i][j]) count++;
        return count;
    }

    private void copyArr(boolean[][] from, boolean[][] to) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                to[i][j] = from[i][j];
    }

    public boolean[][] compute() {
        int max = 65535;
        if (hasWon())
            return result1;

        boolean[][] result = result1;
        boolean flag = false;
        for (int i = 0; i < max; i++) {
            int pos = 0;
            for (int p = 1, j = 0; ; p *= 2, j++)
                if (i % (p*2) == p - 1) {
                    pos = j;
                    break;
                }
            result[pos / 4][pos % 4] = !result[pos / 4][pos % 4];
            mapChangeColor(pos /4, pos % 4);
            if (hasWon()) {
                if (flag) break;
                copyArr(result1, result2);
                result = result2;
                flag = true;
            }
        }
        if (stepsCount(result1) < stepsCount(result2))
            return result1;
        else
            return result2;
    }
}
