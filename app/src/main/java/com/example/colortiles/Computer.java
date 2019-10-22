package com.example.colortiles;


import java.util.Arrays;

public class Computer {

    private boolean[][] map;
    private boolean[][] result; // какие плитки необходимо нажать, чтобы выиграть

    public Computer(boolean[][] map) {
        this.map = new boolean[4][4];
        this.result = new boolean[4][4];

        copyArr(map, this.map);
    }

    private void copyArr(boolean[][] from, boolean[][] to) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                to[i][j] = from[i][j];
    }

    private int count(boolean[][] steps) {
        int count = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (steps[i][j]) count++;
        return count;
    }

    private void mapChangeColor(int i, int j) {
        for (int q = 0; q < 4; q++) {
            map[i][q] = !map[i][q];
            map[q][j] = !map[q][j];
        }
        map[i][j] = !map[i][j];
    }

    private boolean hasWon() {
        int count = count(map);
        if (count == 16 || count == 0)
            return true;
        return false;
    }

    /*
     * Перебираются все возможные комбинации так, чтобы предыдущая отличалась от следующей только одним битом
     * Например для 4 бит:
     * 0000     1100
     * 0001     1101
     * 0011     1111
     * 0010     1110
     * 0110     1010
     * 0111     1011
     * 0101     1001
     * 0100     1000
     * Этот способ позволяет изменять только одну плитку каждую итерацию и при этом перебрать все возможные комбинации
     */
    public boolean[][] compute() {
        int max = 65535;
        if (hasWon())
            return result;

        for (int i = 0; i < max; i++) {
            // выбирается плитка которую нужно будет изменить следующей
            int pos = 0;
            for (int p = 1, j = 0; ; p *= 2, j++)
                // если i % 2^(j+1) == 2^j - 1
                if (i % (p*2) == p - 1) {
                    pos = j;
                    break;
                }
            result[pos / 4][pos % 4] = !result[pos / 4][pos % 4];
            mapChangeColor(pos /4, pos % 4);

            if (hasWon())
                break;
        }
        // Всегда есть только 2 решения, причем они противоположные =>
        // => самое кототкое решение состоит максимум из 16/2 шагов
        // если мы нашли решение больше чем за 8 шагов, то его достаточно инвертировать,
        // чтобы получить второе, более короткое решение
        if (count(result) > 8) {
            System.out.println("YA");
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                    result[i][j] = !result[i][j];
        }
        return result;
    }
}
