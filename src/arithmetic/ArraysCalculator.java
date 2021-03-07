package arithmetic;

import java.util.Arrays;

/**
 * Класс для проведения математических действий с массивами.
 *
 * @author anywaythanks
 * @version 1.1
 */
public class ArraysCalculator {
    /**
     * Перевод координат массива в индекс клетки.
     *
     * @param coordinate1 первая координата массива.
     * @param coordinate2 вторая координата массива.
     * @param maxSize1    максимально возможный размер строки массива.
     * @return индекс массива.
     */
    public static int indexCells(int coordinate1, int coordinate2, int maxSize1) {
        return maxSize1 * coordinate1 + coordinate2;
    }

    /**
     * Перевод индекса клетки в координаты массива.
     *
     * @param index    индекс клетки.
     * @param maxSize1 максимально возможный размер строки массива.
     * @return координаты массива в int[2] = {i, j}.
     */
    public static int[] coordinateMassive(int index, int maxSize1) {
        return new int[]{index / maxSize1, index % maxSize1};
    }

    /**
     * @param array массив, глубину которого нужно подсчитать.
     * @return глубина массива.
     */
    public static int calculateDeepArray(Object array) {
        return StringManipulate.calculateQuantitySymbol(array.getClass().getTypeName(), "[");
    }

    /**
     * @param array массив который нужно проверить на простоту(все вложенные массивы на одной глубине имеют одинаковый размер)
     * @return простой ли массив
     */
    public static boolean isEvenArray(Object array) {
        int[] result = new int[calculateDeepArray(array)];
        Arrays.fill(result, -1);
        return isEvenArray(array, 0, result);
    }

    private static boolean isEvenArray(Object array, int deep, int[] lengths) {
        if (array == null) {
            return false;
        } else if (array instanceof boolean[]) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((boolean[]) array).length;
            } else return lengths[deep] == ((boolean[]) array).length;
        } else if (array instanceof char[]) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((char[]) array).length;
            } else return lengths[deep] == ((char[]) array).length;
        } else if (array instanceof short[]) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((short[]) array).length;
            } else return lengths[deep] == ((short[]) array).length;
        } else if (array instanceof int[]) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((int[]) array).length;
            } else return lengths[deep] == ((int[]) array).length;
        } else if (array instanceof long[]) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((long[]) array).length;
            } else return lengths[deep] == ((long[]) array).length;
        } else if (array instanceof float[]) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((float[]) array).length;
            } else return lengths[deep] == ((float[]) array).length;
        } else if (array instanceof double[]) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((double[]) array).length;
            } else return lengths[deep] == ((double[]) array).length;
        } else if (array.getClass().isArray()) {
            if (lengths[deep] == -1) {
                lengths[deep] = ((Object[]) array).length;
            } else if (lengths[deep] != ((Object[]) array).length) {
                return false;
            }
            if (lengths[deep] > 0) {
                if (lengths[deep] == -1) {
                    lengths[deep] = ((Object[]) array).length;
                } else if (lengths[deep] != ((Object[]) array).length) {
                    return false;
                }
                ++deep;
                if (deep != 1) {
                    for (Object array1 : ((Object[]) array)) {
                        if (array1.getClass().isArray()) {
                            isEvenArray(array1, deep, lengths);
                        }
                    }
                } else {
                    boolean res = true;
                    for (Object array1 : ((Object[]) array)) {
                        if (!isEvenArray(array1, deep, lengths)) {
                            res = false;
                        }
                    }
                    return res;
                }
            }
        }
        return true;
    }

    /**
     * @param array простой(все вложенные массивы на одной глубине имеют одинаковый размер) массив.
     * @return размеры массива.
     */
    public static int[] calculateLengthsEvenArray(Object array) {
        return calculateLengthsEvenArray(array, 0, new int[calculateDeepArray(array)]);
    }

    private static int[] calculateLengthsEvenArray(Object array, int deep, int[] lengths) {
        if (array instanceof boolean[]) {
            lengths[deep] = ((boolean[]) array).length;
            return lengths;
        } else if (array instanceof char[]) {
            lengths[deep] = ((char[]) array).length;
            return lengths;
        } else if (array instanceof short[]) {
            lengths[deep] = ((short[]) array).length;
            return lengths;
        } else if (array instanceof int[]) {
            lengths[deep] = ((int[]) array).length;
            return lengths;
        } else if (array instanceof long[]) {
            lengths[deep] = ((long[]) array).length;
            return lengths;
        } else if (array instanceof float[]) {
            lengths[deep] = ((float[]) array).length;
            return lengths;
        } else if (array instanceof double[]) {
            lengths[deep] = ((double[]) array).length;
            return lengths;
        } else {
            if (array.getClass().isArray()) {
                lengths[deep] = ((Object[]) array).length;
                if (lengths[deep] > 0) {
                    return calculateLengthsEvenArray(((Object[]) array)[0], deep + 1, lengths);
                }
            }
            return lengths;
        }
    }

    /**
     * @param array                    массив для проверки на ошибку несоответствия размеров.
     * @param referenceDimensionsArray правильные размеры массива
     * @throws IllegalArgumentException разные глубины массивов
     * @throws IllegalArgumentException array не является простым
     * @throws IllegalArgumentException размеры массивов разные
     */
    public static void checkEvenArrayException(Object array, int... referenceDimensionsArray) throws IllegalArgumentException {
        if (calculateDeepArray(array) != referenceDimensionsArray.length) {
            throw new IllegalArgumentException("The array has the wrong depth.");
        } else if (!ArraysCalculator.isEvenArray(array)) {
            throw new IllegalArgumentException("The array is not even.");
        } else if (!Arrays.equals(calculateLengthsEvenArray(array), referenceDimensionsArray)) {
            throw new IllegalArgumentException("The array is not sized correctly.");
        }
    }
}
