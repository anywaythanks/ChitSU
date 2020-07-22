package Arithmetic;

/**
 * Класс для переводов в разные системы счисления.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class NumberSystem {
    /**
     * Перевод числа в новую систему.
     *
     * @param num число, которое нужно перевести.
     * @return Итог перевода:
     * <p>
     * От 0 до 9 обычные числа;
     * <p>
     * От 10 - это A, B, C и т.д.
     */
    public static String getCharNum(int num) {
        if (num == 0)
            return "";
        if (num < 10)
            return ((char) ('0' + num)) + "";
        return (char) ('A' + (num - 10)) + "";
    }

    /**
     * Обратный перевод {@link NumberSystem#getCharNum}
     *
     * @param s - Строка, которую нужно перевести в новую систему
     * @return От 0 до 9 обычные числа;
     * <p>
     * A, B, C и т.д. - 10, 11, 12 и т.д.
     */
    public static int getNumChar(String s) {
        if (s.charAt(0) == ' ')
            return 0;
        if (s.charAt(0) - '0' >= 0 && s.charAt(0) - '0' < 10)
            return s.charAt(0) - '0';
        return s.charAt(0) - 'A' + 10;
    }
}
