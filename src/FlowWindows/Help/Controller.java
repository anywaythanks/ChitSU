package FlowWindows.Help;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class Controller {
    @FXML
    public AnchorPane listScroll;
    private ScrollPane[] abc;
    @FXML
    public TreeView<String> tree;
    @FXML
    public Hyperlink
            load_1, load_2,
            save_1, save_2, save_3,
            variants_1, variants_2, variants_3, variants_4, variants_5, variants_6, variants_7,
            tradition_1, tradition_2, tradition_3,
            classic_1, classic_2, classic_3,
            creative_1,
            teaching_1,
            clear_1,
            regulation_1,
            candidates_1, candidates_2,
            candidateBacklight_3,
            opt_1,
            generation_1,
            decision_1,
            mainInstruction_1,
            prompt_1, prompt_2;

    private void blocker(int num) {
        int q = 0;
        for (ScrollPane text : abc)
            text.setVisible(q++ == num);
    }

    private void setLinkEvent(TreeItem<String> item, Hyperlink... links) {
        for (Hyperlink link : links)
            link.setOnAction(event -> selectedIt(item));
    }

    private final TreeItem<String> mainInstruction = new TreeItem<>("Как играть");
    private final TreeItem<String> sud = new TreeItem<>("Судоку");
    private final TreeItem<String> regulation = new TreeItem<>("Правила");
    private final TreeItem<String> variant = new TreeItem<>("Задания");
    private final TreeItem<String> tradition = new TreeItem<>("Традиционная судоку");
    private final TreeItem<String> candidate = new TreeItem<>("Кандидаты");
    private final TreeItem<String> functional = new TreeItem<>("Функционал программы");
    private final TreeItem<String> exit = new TreeItem<>("Выход");
    private final TreeItem<String> sud2 = new TreeItem<>("Манипуляции с судоку");
    private final TreeItem<String> mode = new TreeItem<>("Режимы");
    private final TreeItem<String> options = new TreeItem<>("Опции");
    private final TreeItem<String> save = new TreeItem<>("Сохранение");
    private final TreeItem<String> load = new TreeItem<>("Загрузка");
    private final TreeItem<String> clear = new TreeItem<>("Очистка");
    private final TreeItem<String> decision = new TreeItem<>("Решение");
    private final TreeItem<String> prompt = new TreeItem<>("Подсказка");
    private final TreeItem<String> generation = new TreeItem<>("Генерация");
    private final TreeItem<String> classic = new TreeItem<>("Классический режим игры");
    private final TreeItem<String> creative = new TreeItem<>("Творческий режим игры");
    private final TreeItem<String> teaching = new TreeItem<>("Обучающий режим игры");
    private final TreeItem<String> coiCell = new TreeItem<>("Подсветка неправильных клеток");
    private final TreeItem<String> numCell = new TreeItem<>("Подсветка совпадающих цифр");
    private final TreeItem<String> candidateBacklight = new TreeItem<>("Подсветка кандидатов");
    private final TreeItem<String> opt = new TreeItem<>("Настройки");
    private final TreeItem<String> root = new TreeItem<>("root");

    private TreeItem<String> addRoot() {
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

    private void selectedIt(TreeItem<String> a) {
        tree.getSelectionModel().select(a);
    }

    @FXML
    public void initialize
            () {
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


        tree.setRoot(addRoot());
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
