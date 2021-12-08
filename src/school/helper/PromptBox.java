package school.helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import school.models.user.UserService;

public class PromptBox {

    public static void show(String title, String txt1, String txt2) {

        Stage stage = new Stage();
        VBox root = new VBox();

        Label label1 = new Label(txt1);
        Label label2 = new Label(txt2);
        label1.setStyle("-fx-font-family: Cambria; -fx-font-size: 16;");
        label2.setStyle("-fx-font-family: Cambria; -fx-font-size: 16;");

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();

        HBox hBox1 = new HBox();
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(label1, textField1);

        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(label2, textField2);


        HBox hBox3 = new HBox();
        hBox3.setSpacing(5);
        Button okBtn = new Button("OK");
        okBtn.setId("okBtn");
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancelBtn");
        hBox3.getChildren().addAll(okBtn, cancelBtn);

        okBtn.setOnAction(e -> {

            String currentPassword = textField1.getText();
            String newPassword = textField2.getText();

            if (currentPassword.isEmpty() || newPassword.isEmpty()) {
                AlertDialog.show("Error", "Something is empty");
            } else {
                UserService userService = new UserService();
                boolean result = userService.changePassword(currentPassword, newPassword);

                if (result) {
                    AlertDialog.show("Success", "Changing Password is successful");
                    stage.close();
                } else {
                    AlertDialog.show("Error", "CurrentPassword is incorrect");
                    textField1.setText("");
                    textField2.setText("");
                }
            }



        });

        cancelBtn.setOnAction( e -> {
            stage.close();
        });

        root.getChildren().addAll(hBox1, hBox2, hBox3);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getStylesheets().add("/school/assets/resources/style.css");
        root.setSpacing(10);

        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setMinWidth(350);
        stage.setMinHeight(150);
        stage.setResizable(false);
        stage.setScene(new Scene(root,350,150));
        stage.setTitle(title);
        stage.showAndWait(); // waiting return value; show is not waiting return value;

    }
}
