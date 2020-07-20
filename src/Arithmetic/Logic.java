package Arithmetic;

/**
 * Класс функций, для работы с bool-переменными.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Logic {
    /**
     * Перевод bool в string.
     *
     * @param bool bool-переменная.
     * @return "TRUE" or "FALSE".
     */
    public static String booleanToString(boolean bool) {
        return bool ? "1" : "0";
    }

    /**
     * Перевод string в bool.
     *
     * @param string string-переменная.
     * @return true or false.
     */
    public static boolean stringToBoolean(String string) {
        return string.equals("1");
    }


    /**
     * Перевод bool в int.
     *
     * @param bool bool-переменная.
     * @return 1 or 0.
     */
    public static int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /**
     * Перевод int в bool.
     *
     * @param i int-переменная.
     * @return true or false.
     */
    public static boolean intToBoolean(int i) {
        return i >= 1;
    }
}
