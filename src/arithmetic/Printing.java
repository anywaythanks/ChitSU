package arithmetic;

/**
 * Класс для вывода массивов в консоль.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Printing {
    /**
     * Вывести массив чисел.
     *
     * @param array массив.
     */
    public static void massive(int[][][][][] array) {
        for (int[][][][] ints : array) {
            massive(ints);
        }
    }

    /**
     * Вывести массив чисел.
     *
     * @param array массив.
     */
    public static void massive(int[][][][] array) {
        for (int[][][] ints : array) {
            massive(ints);
        }
    }

    /**
     * Вывести массив чисел.
     *
     * @param array массив.
     */
    public static void massive(int[][][] array) {
        for (int[][] ints : array) {
            massive(ints);
        }
    }

    /**
     * Вывести массив чисел.
     *
     * @param array массив.
     */
    public static void massive(int[][] array) {
        for (int[] ints : array) {
            massive(ints);
        }
    }

    /**
     * Вывести массив чисел.
     *
     * @param array массив.
     */
    public static void massive(int[] array) {
        for (int i : array) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }


    /**
     * Вывести массив строк.
     *
     * @param array массив.
     */
    public static void massive(Object[][][][][] array) {
        for (Object[][][][] objects : array) {
            massive(objects);
        }
    }

    /**
     * Вывести массив строк.
     *
     * @param array массив.
     */
    public static void massive(Object[][][][] array) {
        for (Object[][][] objects : array) {
            massive(objects);
        }
    }

    /**
     * Вывести массив строк.
     *
     * @param array массив.
     */
    public static void massive(Object[][][] array) {
        for (Object[][] objects : array) {
            massive(objects);
        }
    }

    /**
     * Вывести массив строк.
     *
     * @param array массив.
     */
    public static void massive(Object[][] array) {
        for (Object[] objects : array) {
            massive(objects);
        }
    }

    /**
     * Вывести массив строк.
     *
     * @param array массив.
     */
    public static void massive(Object[] array) {
        for (Object o : array) {
            System.out.print(o.toString() + "\t");
        }
        System.out.println();
    }


    /**
     * Вывести массив bool.
     *
     * @param array массив.
     */
    public static void massive(boolean[][][][][] array) {
        for (boolean[][][][] booleans : array) {
            massive(booleans);
        }
    }

    /**
     * Вывести массив bool.
     *
     * @param array массив.
     */
    public static void massive(boolean[][][][] array) {
        for (boolean[][][] booleans : array) {
            massive(booleans);
        }
    }

    /**
     * Вывести массив bool.
     *
     * @param array массив.
     */
    public static void massive(boolean[][][] array) {
        for (boolean[][] booleans : array) {
            massive(booleans);
        }
    }

    /**
     * Вывести массив bool.
     *
     * @param array массив.
     */
    public static void massive(boolean[][] array) {
        for (boolean[] booleans : array) {
            massive(booleans);
        }
    }

    /**
     * Вывести массив bool.
     *
     * @param array массив.
     */
    public static void massive(boolean[] array) {
        for (boolean b : array) {
            System.out.print(b + "\t");
        }
        System.out.println();
    }
}
