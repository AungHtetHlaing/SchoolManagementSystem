package school.controllers;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Transform;
import javafx.util.Callback;
import javafx.util.Duration;
import school.helper.ConfirmBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button nav_home;

    @FXML
    private Button nav_teachers;

    @FXML
    private Button nav_students;

    @FXML
    private Button nav_classes;

    @FXML
    private Button nav_subjects;

    @FXML
    private Button nav_pay_amount;

    @FXML
    private Button nav_exit;

    private Parent home,teacher, student, classLayout, subject, pay_amount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nav_home.setGraphic(getImage("/school/assets/img/home.png"));
        nav_teachers.setGraphic(getImage("/school/assets/img/home.png"));
        nav_students.setGraphic(getImage("/school/assets/img/student.png"));
        nav_classes.setGraphic(getImage("/school/assets/img/home.png"));
        nav_subjects.setGraphic(getImage("/school/assets/img/subject.png"));
        nav_pay_amount.setGraphic(getImage("/school/assets/img/pay.png"));
        nav_exit.setGraphic(getImage("/school/assets/img/sad.png"));



        try {
            home = FXMLLoader.load(getClass().getResource("/school/views/home.fxml"));
             teacher = FXMLLoader.load(getClass().getResource("/school/views/teacher.fxml"));
             student = FXMLLoader.load(getClass().getResource("/school/views/student.fxml"));
             classLayout = FXMLLoader.load(getClass().getResource("/school/views/class.fxml"));
            subject = FXMLLoader.load(getClass().getResource("/school/views/subject.fxml"));
            pay_amount = FXMLLoader.load(getClass().getResource("/school/views/pay_amount.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private ImageView getImage(String path) {
        ImageView img = new ImageView(path);
        img.setFitWidth(20);
        img.setFitHeight(20);
        return img;
    }

    @FXML
    void showTeachers(ActionEvent event) throws IOException {
        
        HBox hBox = LoginController.gethBox();
        if (hBox.getChildren().size() > 1) {
            hBox.getChildren().remove( 1);
        }
        LoginController.gethBox().getChildren().add(teacher);


    }


    @FXML
    void showClasses(ActionEvent event) {
        HBox hBox = LoginController.gethBox();
        if (hBox.getChildren().size() > 1) {
            hBox.getChildren().remove(1);
        }
        LoginController.gethBox().getChildren().add(classLayout);
    }

    @FXML
    void showHome(ActionEvent event) {
        HBox hBox = LoginController.gethBox();
        if (hBox.getChildren().size() > 1) {
            hBox.getChildren().remove( 1);
        }
        LoginController.gethBox().getChildren().add(home);
    }

    @FXML
    void exit(ActionEvent event) {
        boolean flag = ConfirmBox.show("Exit", "Are you sure to exit!");
        if (flag) {
            Platform.exit();
        }
    }

    @FXML
    void showPayAmount(ActionEvent event) {
        HBox hBox = LoginController.gethBox();
        if (hBox.getChildren().size() > 1) {
            hBox.getChildren().remove( 1);
        }
        LoginController.gethBox().getChildren().add(pay_amount);

    }

    @FXML
    void showStudents(ActionEvent event) throws IOException {

        HBox hBox = LoginController.gethBox();
        if (hBox.getChildren().size() > 1) {
            hBox.getChildren().remove(1);
        }
        LoginController.gethBox().getChildren().add(student);

    }

    @FXML
    void showSubjects(ActionEvent event) {
        HBox hBox = LoginController.gethBox();
        if (hBox.getChildren().size() > 1) {
            hBox.getChildren().remove(1);
        }
        LoginController.gethBox().getChildren().add(subject);
    }





}
