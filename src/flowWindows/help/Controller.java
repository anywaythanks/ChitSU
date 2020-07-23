package flowWindows.help;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * Controller класс, для обработки кнопок.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Controller {
    /**
     * Массив {@link ScrollPane}. необходим, для работы {@link Controller#blocker(int)}.
     */
    private ScrollPane[] abc;

    /**
     * {@link AnchorPane}, в котором находятся все {@link ScrollPane}.
     */
    @FXML
    public AnchorPane listScroll;
    /**
     * Дерево помощи. Именно тут находятся все основные пункты.
     */
    @FXML
    public TreeView<String> tree;


    /**
     * Ссылка на {@link Controller#load}.
     */
    @FXML
    public Hyperlink load_1, load_2;
    /**
     * Ссылка на {@link Controller#save}.
     */
    @FXML
    public Hyperlink save_1, save_2, save_3;
    /**
     * Ссылка на {@link Controller#variant}.
     */
    @FXML
    public Hyperlink variants_1, variants_2, variants_3, variants_4, variants_5, variants_6, variants_7;
    /**
     * Ссылка на {@link Controller#tradition}.
     */
    @FXML
    public Hyperlink tradition_1, tradition_2, tradition_3;
    /**
     * Ссылка на {@link Controller#classic}.
     */
    @FXML
    public Hyperlink classic_1, classic_2, classic_3;
    /**
     * Ссылка на {@link Controller#creative}.
     */
    @FXML
    public Hyperlink creative_1;
    /**
     * Ссылка на {@link Controller#teaching}.
     */
    @FXML
    public Hyperlink teaching_1;
    /**
     * Ссылка на {@link Controller#clear}.
     */
    @FXML
    public Hyperlink clear_1;
    /**
     * Ссылка на {@link Controller#regulation}.
     */
    @FXML
    public Hyperlink regulation_1;
    /**
     * Ссылка на {@link Controller#candidate}.
     */
    @FXML
    public Hyperlink candidates_1, candidates_2;
    /**
     * Ссылка на {@link Controller#candidateBacklight}.
     */
    @FXML
    public Hyperlink candidateBacklight_3;
    /**
     * Ссылка на {@link Controller#opt}.
     */
    @FXML
    public Hyperlink opt_1;
    /**
     * Ссылка на {@link Controller#generation}.
     */
    @FXML
    public Hyperlink generation_1;
    /**
     * Ссылка на {@link Controller#decision}.
     */
    @FXML
    public Hyperlink decision_1;
    /**
     * Ссылка на {@link Controller#mainInstruction}.
     */
    @FXML
    public Hyperlink mainInstruction_1;
    /**
     * Ссылка на {@link Controller#prompt}.
     */
    @FXML
    public Hyperlink prompt_1, prompt_2;


    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о том, как пользоваться программой.
     */
    private final TreeItem<String> mainInstruction = new TreeItem<>("Как играть");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположены основные пункты, поясняющие, что такое судоку.
     */
    private final TreeItem<String> sud = new TreeItem<>("Судоку");
    /**
     * Пункт помощи.
     * <p>
     * В нем изложены основные правила судоку.
     */
    private final TreeItem<String> regulation = new TreeItem<>("Правила");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о том, что такое задания.
     */
    private final TreeItem<String> variant = new TreeItem<>("Задания");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о том, что такое традиционная судоку.
     */
    private final TreeItem<String> tradition = new TreeItem<>("Традиционная судоку");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о том, что такое кандидаты.
     */
    private final TreeItem<String> candidate = new TreeItem<>("Кандидаты");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположены остальные пункты, разъясняющие функционал программы.
     */
    private final TreeItem<String> functional = new TreeItem<>("Функционал программы");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о том, как выйти из программы.
     */
    private final TreeItem<String> exit = new TreeItem<>("Выход");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположены пункты, рассказывающие о манипуляциях с судоку.
     */
    private final TreeItem<String> sud2 = new TreeItem<>("Манипуляции с судоку");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположены пункты с информацией о режимах игры.
     */
    private final TreeItem<String> mode = new TreeItem<>("Режимы");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположены пункты с информацией об опциях программы.
     */
    private final TreeItem<String> options = new TreeItem<>("Опции");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о сохранениях.
     */
    private final TreeItem<String> save = new TreeItem<>("Сохранение");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о загрузке.
     */
    private final TreeItem<String> load = new TreeItem<>("Загрузка");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация об очистке поля.
     */
    private final TreeItem<String> clear = new TreeItem<>("Очистка");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о том, как решить судоку.
     */
    private final TreeItem<String> decision = new TreeItem<>("Решение");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о подсказках.
     */
    private final TreeItem<String> prompt = new TreeItem<>("Подсказка");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о том, как сгенерировать судоку.
     */
    private final TreeItem<String> generation = new TreeItem<>("Генерация");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о классическом режиме игры.
     */
    private final TreeItem<String> classic = new TreeItem<>("Классический режим игры");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о творческом режиме игры.
     */
    private final TreeItem<String> creative = new TreeItem<>("Творческий режим игры");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация об обучающем режиме игры.
     */
    private final TreeItem<String> teaching = new TreeItem<>("Обучающий режим игры");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о подсветке неправильных клеток.
     */
    private final TreeItem<String> coiCell = new TreeItem<>("Подсветка неправильных клеток");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о подсветке совпадающих цифр.
     */
    private final TreeItem<String> numCell = new TreeItem<>("Подсветка совпадающих цифр");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о подсветке кандидатов.
     */
    private final TreeItem<String> candidateBacklight = new TreeItem<>("Подсветка кандидатов");
    /**
     * Пункт помощи.
     * <p>
     * В нем расположена информация о настройках игры.
     */
    private final TreeItem<String> opt = new TreeItem<>("Настройки");
    /**
     * Корневой пункт.
     * <p>
     * Обычно невидим
     */
    private final TreeItem<String> root = new TreeItem<>("root");


    /**
     * Метод создает корень {@link TreeItem}.
     */
    private TreeItem<String> setRoot() {
        sud.getChildren().add(regulation);
        sud.getChildren().add(variant);
        sud.getChildren().add(tradition);
        sud.getChildren().add(candidate);

        sud2.getChildren().add(save);
        sud2.getChildren().add(load);
        sud2.getChildren().add(clear);
        decision.getChildren().add(prompt);
        sud2.getChildren().add(decision);
        sud2.getChildren().add(generation);
        sud2.getChildren().add(exit);

        mode.getChildren().add(classic);
        mode.getChildren().add(creative);
        mode.getChildren().add(teaching);

        options.getChildren().add(coiCell);
        options.getChildren().add(numCell);
        options.getChildren().add(candidateBacklight);
        options.getChildren().add(opt);

        functional.getChildren().add(sud2);
        functional.getChildren().add(mode);
        functional.getChildren().add(options);

        root.getChildren().add(mainInstruction);
        root.getChildren().add(sud);
        root.getChildren().add(functional);
        return root;
    }

    /**
     * метод изменяет выбранный пункт, на новый.
     *
     * @param a новый пункт меню.
     */
    private void selectedIt(TreeItem<String> a) {
        tree.getSelectionModel().select(a);
    }

    /**
     * Метод блокирует все {@link ScrollPane}, кроме одной, под определённым номером.
     *
     * @param num номер {@link ScrollPane}, который не нужно блокировать.
     */
    private void blocker(int num) {
        int q = 0;
        for (ScrollPane text : abc)
            text.setVisible(q++ == num);
    }

    /**
     * Метод создает {@link java.beans.EventHandler} для ссылок.
     *
     * @param item  пункт меню, на который ссылаются ссылки.
     * @param links ссылки.
     */
    private void setLinkEvent(TreeItem<String> item, Hyperlink... links) {
        for (Hyperlink link : links)
            link.setOnAction(event -> selectedIt(item));
    }


    @FXML
    public void initialize() {
        abc = new ScrollPane[listScroll.getChildren().size()];
        int q = 0;
        for (Node ch : listScroll.getChildren()) {
            ScrollPane text = (ScrollPane) ch;
            abc[q++] = text;
        }
        setLinkEvent(load, load_1, load_2);
        setLinkEvent(save, save_1, save_2, save_3);
        setLinkEvent(variant, variants_1, variants_2, variants_3, variants_4, variants_5, variants_6, variants_7);
        setLinkEvent(tradition, tradition_1, tradition_2, tradition_3);
        setLinkEvent(classic, classic_1, classic_2, classic_3);
        setLinkEvent(clear, creative_1);
        setLinkEvent(teaching, teaching_1);
        setLinkEvent(regulation, regulation_1);
        setLinkEvent(candidate, candidates_1, candidates_2);
        setLinkEvent(candidateBacklight, candidateBacklight_3);
        setLinkEvent(opt, opt_1);
        setLinkEvent(creative, creative_1);
        setLinkEvent(generation, generation_1);
        setLinkEvent(decision, decision_1);
        setLinkEvent(mainInstruction, mainInstruction_1);
        setLinkEvent(prompt, prompt_1, prompt_2);


        tree.setRoot(setRoot());
        tree.setShowRoot(false);

        MultipleSelectionModel<TreeItem<String>> selectionModel = tree.getSelectionModel();

        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                switch (newValue.getValue()) {
                    case "Правила":
                        blocker(0);
                        break;
                    case "Сохранение":
                        blocker(1);
                        break;
                    case "Загрузка":
                        blocker(2);
                        break;
                    case "Очистка":
                        blocker(3);
                        break;
                    case "Решение":
                        blocker(4);
                        break;
                    case "Генерация":
                        blocker(5);
                        break;
                    case "Классический режим игры":
                        blocker(6);
                        break;
                    case "Творческий режим игры":
                        blocker(7);
                        break;
                    case "Обучающий режим игры":
                        blocker(8);
                        break;
                    case "Подсветка неправильных клеток":
                        blocker(9);
                        break;
                    case "Подсветка совпадающих цифр":
                        blocker(10);
                        break;
                    case "Подсветка кандидатов":
                        blocker(11);
                        break;
                    case "Настройки":
                        blocker(12);
                        break;
                    case "Манипуляции с судоку":
                        blocker(13);
                        break;
                    case "Режимы":
                        blocker(14);
                        break;
                    case "Опции":
                        blocker(15);
                        break;
                    case "Задания":
                        blocker(16);
                        break;
                    case "Традиционная судоку":
                        blocker(17);
                        break;
                    case "Кандидаты":
                        blocker(18);
                        break;
                    case "Как играть":
                        blocker(19);
                        break;
                    case "Выход":
                        blocker(20);
                        break;
                    case "Подсказка":
                        blocker(21);
                        break;
                }
        });
        selectedIt(mainInstruction);
    }
}
