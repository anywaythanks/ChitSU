package arithmetic;

/**
 * Класс для сравнения объектов.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Equals {
    /**
     * Функция для обнаружения объекта.
     *
     * @param objectEquals объект, который будут искать в массиве.
     * @param objects      массив, в котором будут искать объект.
     * @return если в массиве есть совпадающий объет, то true, иначе false.
     */
    public static boolean objectEq(Object objectEquals, Object... objects) {
        for (Object object : objects)
            if (object.equals(objectEquals))
                return true;
        return false;
    }
}
