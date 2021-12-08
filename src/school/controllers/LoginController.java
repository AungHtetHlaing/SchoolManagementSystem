package school.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import school.StartMain;
import school.helper.AlertDialog;
import school.helper.ConfirmBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import school.helper.PromptBox;
import school.models.user.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {



    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Hyperlink changePassword;


    private static HBox hBox;

    @FXML
    void cancel(ActionEvent event) {
        if (ConfirmBox.show("Exit", "Are you sure to exit without login!")){
            Platform.exit();
        }
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        UserService userService = new UserService();
        if (username.isEmpty() || password.isEmpty()) {
            AlertDialog.show("Error", "Email or Password is empty");
        } else {
            boolean result = userService.loginUser(username, password);

            if (result) {
                hBox = FXMLLoader.load(getClass().getResource("/school/views/main.fxml"));
                Parent home =  FXMLLoader.load(getClass().getResource("/school/views/home.fxml"));
                hBox.getChildren().add(home);
                Stage stage = StartMain.getStage();
                stage.setMaximized(true);
                stage.setResizable(true);

                stage.setScene(new Scene(hBox,1600, 1080));

            } else {
                AlertDialog.show("Error", "Email or Password is incorrect");
                this.username.setText("");
                this.password.setText("");
            }
        }


    }

    public static HBox gethBox() {
        return hBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void changePassword(ActionEvent event) {
        PromptBox.show("Change Your Password", "Current Password", "New Password");
    }
}
