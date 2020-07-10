package Arithmetic;

/**
 * Класс для индексации масивов.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Indexing {
    /**
     * Перевод координат массива в индекс клетки.
     * @param coordinate1 первая координата массива.
     * @param coordinate2 вторая координата массива.
     * @param maxSize1 максимально возможный размер строки массива.
     * @return индекс массива.
     */
    public static int indexCells(int coordinate1, int coordinate2, int maxSize1) {
        return maxSize1 * coordinate1 + coordinate2;
    }

    /**
     * Перевод индекса клетки в координаты массива.
     * @param index индекс клетки.
     * @param maxSize1  максимально возможный размер строки массива.
     * @return координаты массива в int[2] = {i, j}.
     */
    public static int[] coordinateMassive(int index, int maxSize1) {
        return new int[]{index / maxSize1, index % maxSize1};
    }
}
