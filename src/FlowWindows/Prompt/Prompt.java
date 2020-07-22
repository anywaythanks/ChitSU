package FlowWindows.Prompt;

import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс окна помощи.
 * <p>
 * Показывает пользователю помощь по игре.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Prompt extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Prompt#Prompt(String)}.
     */
    public final Controller controller;

    /**
     * Создание объекта {@link Prompt}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Help.fxml или же в нем содержатся ошибки.
     */
    public Prompt(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Prompt.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Prompt.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
    }

    @Override
    public void setText(String text) {
        controller.text.setText(text);
    }
    public void setTextVBox(String... textVBox) {
        for (String s : textVBox) {
            Label label = new Label(s);
            label.setWrapText(true);
            label.setStyle("-fx-font-style: italic");
            label.setFont(Font.font(14));
            label.setMaxSize(1000, 1000);
            controller.cont.getChildren().addAll(label);
        }
    }
}
