package school.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import school.models.class_entity.ClassService;
import school.models.student.StudentService;
import school.models.teacher.TeacherService;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Text teacher_count;

    @FXML
    private Text student_count;

    @FXML
    private Text class_count;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TeacherService teacherService = new TeacherService();
        StudentService studentService = new StudentService();
        ClassService classService = new ClassService();

        teacher_count.setText(String.valueOf(teacherService.getAllTeachers().size()));
        student_count.setText(String.valueOf(studentService.getAllStudents().size()));
        class_count.setText(String.valueOf(classService.getAllClass().size()));
    }

    
}
