package school.helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertDialog {

    public static void show(String title, String message) {
        Stage stage = new Stage();
        VBox root = new VBox();

        Label label = new Label(message);
        label.setStyle("-fx-font-family: Cambria; -fx-font-size: 16;");
        Button okBtn = new Button("OK");
        okBtn.setId("okBtn");


        okBtn.setOnAction(e -> {
            stage.close();
        });

        root.getChildren().addAll(label, okBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getStylesheets().add("/school/assets/resources/style.css");
        root.setSpacing(10);

        if (title.equalsIgnoreCase("Error")){
            stage.getIcons().add(new Image("/school/assets/img/sad.png"));
        }

        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setMinWidth(300);
        stage.setMinHeight(70);
        stage.setResizable(false);
        stage.setScene(new Scene(root,300,70));
        stage.setTitle(title);
        stage.showAndWait(); // waiting return value; show is not waiting return value;

    }
}
