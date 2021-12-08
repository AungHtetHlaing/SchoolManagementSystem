package school.helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {
    static boolean flag;
    public static boolean show(String title, String message) {
        flag = false;
        Stage stage = new Stage();
        VBox root = new VBox();

        Label label = new Label(message);
        label.setStyle("-fx-font-family: Cambria; -fx-font-size: 16;");
        Button okBtn = new Button("OK");
        okBtn.setId("okBtn");
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancelBtn");


        okBtn.setOnAction(e -> {
            flag = true;
            stage.close();

        });


        cancelBtn.setOnAction(e -> {
            flag = false;
            stage.close();

        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(okBtn, cancelBtn);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);

        root.getChildren().addAll(label, hBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getStylesheets().add("/school/assets/resources/style.css");
        root.setSpacing(10);

        if (title.equalsIgnoreCase("exit")){
            stage.getIcons().add(new Image("/school/assets/img/exit.png"));
        }
        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setMinWidth(300);
        stage.setMinHeight(70);
        stage.setResizable(false);
        stage.setScene(new Scene(root,300,70));
        stage.setTitle(title);
        stage.showAndWait(); // waiting return value; show is not waiting return value;

        return flag;
    }
}
