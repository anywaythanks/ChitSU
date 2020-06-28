package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ControlHelp {
    @FXML
    public AnchorPane listScroll;
    private ScrollPane[] abc;
    @FXML
    public TreeView<String> tree;
    @FXML
    public Hyperlink
            load_1, save_1, save_2, variants_1, variants_2, variants_3, tradition_1, tradition_2, classic_1, classic_2, clear_1, regulations_1,
            variants_4, variants_5, variants_6, linkCandidates_1, linkCandidates_2, opt_1, linkCandidates_3, creative_1, generation_1, variants_7,
            classic_3, tradition_3, res_1, mainInstruction_1, save_3, load_2, prompt_1, prompt_2, teaching_1;

    private void blocker
            (int num) {
        int q = 0;
        for (ScrollPane text : abc)
            text.setVisible(q++ == num);
    }

    private TreeItem<String>
            mainInstruction = new TreeItem<>("Как играть"),
            sud = new TreeItem<>("Судоку"),
            regulations = new TreeItem<>("Правила"),
            variants = new TreeItem<>("Задания"),
            tradition = new TreeItem<>("Традиционная судоку"),
            candidates = new TreeItem<>("Кандидаты"),
            functional = new TreeItem<>("Функционал программы"),
            exit = new TreeItem<>("Выход"),
            sud2 = new TreeItem<>("Манипуляции с судоку"),
            rez = new TreeItem<>("Режимы"),
            options = new TreeItem<>("Опции"),
            save = new TreeItem<>("Сохранение"),
            load = new TreeItem<>("Загрузка"),
            clear = new TreeItem<>("Очистка"),
            res = new TreeItem<>("Решение"),
            prompt = new TreeItem<>("Подсказка"),
            generation = new TreeItem<>("Генерация"),
            classic = new TreeItem<>("Классический режим игры"),
            creative = new TreeItem<>("Творческий режим игры"),
            teaching = new TreeItem<>("Обучающий режим игры"),
            coiCell = new TreeItem<>("Подсветка неправильных клеток"),
            numCell = new TreeItem<>("Подсветка совпадающих цифр"),
            candidate = new TreeItem<>("Подсветка кандидатов"),
            opt = new TreeItem<>("Настройки"),
            root = new TreeItem<>("root");

    private TreeItem<String> addRoot
            () {
        sud.getChildren().add(regulations);
        sud.getChildren().add(variants);
        sud.getChildren().add(tradition);
        sud.getChildren().add(candidates);

        sud2.getChildren().add(save);
        sud2.getChildren().add(load);
        sud2.getChildren().add(clear);
        res.getChildren().add(prompt);
        sud2.getChildren().add(res);
        sud2.getChildren().add(generation);
        sud2.getChildren().add(exit);

        rez.getChildren().add(classic);
        rez.getChildren().add(creative);
        rez.getChildren().add(teaching);

        options.getChildren().add(coiCell);
        options.getChildren().add(numCell);
        options.getChildren().add(candidate);
        options.getChildren().add(opt);

        functional.getChildren().add(sud2);
        functional.getChildren().add(rez);
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
        load_1.setOnAction(event -> selectedIt(load));
        load_2.setOnAction(event -> selectedIt(load));

        save_1.setOnAction(event -> selectedIt(save));
        save_2.setOnAction(event -> selectedIt(save));
        save_3.setOnAction(event -> selectedIt(save));

        variants_1.setOnAction(event -> selectedIt(variants));
        variants_2.setOnAction(event -> selectedIt(variants));
        variants_3.setOnAction(event -> selectedIt(variants));
        variants_4.setOnAction(event -> selectedIt(variants));
        variants_5.setOnAction(event -> selectedIt(variants));
        variants_6.setOnAction(event -> selectedIt(variants));
        variants_7.setOnAction(event -> selectedIt(variants));

        tradition_1.setOnAction(event -> selectedIt(tradition));
        tradition_2.setOnAction(event -> selectedIt(tradition));
        tradition_3.setOnAction(event -> selectedIt(tradition));

        classic_1.setOnAction(event -> selectedIt(classic));
        classic_2.setOnAction(event -> selectedIt(classic));
        classic_3.setOnAction(event -> selectedIt(classic));

        clear_1.setOnAction(event -> selectedIt(clear));

        regulations_1.setOnAction(event -> selectedIt(regulations));

        linkCandidates_1.setOnAction(event -> selectedIt(candidates));
        linkCandidates_2.setOnAction(event -> selectedIt(candidates));
        linkCandidates_3.setOnAction(event -> selectedIt(candidate));

        opt_1.setOnAction(event -> selectedIt(opt));

        creative_1.setOnAction(event -> selectedIt(creative));

        generation_1.setOnAction(event -> selectedIt(generation));

        res_1.setOnAction(event -> selectedIt(res));

        mainInstruction_1.setOnAction(event -> selectedIt(mainInstruction));

        prompt_1.setOnAction(event -> selectedIt(prompt));
        prompt_2.setOnAction(event -> selectedIt(prompt));

        teaching_1.setOnAction(event -> selectedIt(teaching));

        tree.setRoot(addRoot());
        tree.setShowRoot(false);

        MultipleSelectionModel<TreeItem<String>> selectionModel = tree.getSelectionModel();

        // устанавливаем слушатель для отслеживания изменений
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
