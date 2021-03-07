package arithmetic;

/**
 * Класс для манипуляций со строками.
 *
 * @author anywaythanks
 * @version 0.0
 */
public class StringManipulate {

    /**
     * @param s      строка для подсчета
     * @param symbol встречающийся символ
     * @return количевство встречающихся символов в строке.
     */
    public static int calculateQuantitySymbol(String s, String symbol) {
        return s.length() - s.replace(symbol, "").length();
    }
}
