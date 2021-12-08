package school;

import school.helper.ConfirmBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartMain extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/school/views/login.fxml"));
        root.getStylesheets().add("/school/assets/resources/style.css");
        stage.setTitle("School Management System");
        stage.getIcons().add(new Image("/school/assets/img/school.png"));
        stage.setResizable(false);
        stage.setScene(new Scene(root, 600, 346));
        stage.setOnCloseRequest(e -> {
            e.consume();
            boolean flag = ConfirmBox.show("Exit", "Are you sure to exit!");
            if (flag) {
                Platform.exit();
            }
        });
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
