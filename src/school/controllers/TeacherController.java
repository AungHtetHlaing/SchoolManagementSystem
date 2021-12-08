package school.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import school.helper.AlertDialog;
import school.models.class_entity.ClassEntity;
import school.models.class_entity.ClassService;
import school.models.section.Section;
import school.models.section.SectionService;
import school.models.student.Student;
import school.models.student.StudentService;
import school.models.subject.Subject;
import school.models.subject.SubjectService;
import school.models.teacher.Teacher;
import school.models.teacher.TeacherService;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {
    @FXML
    private TextField t_name;

    @FXML
    private TextField t_age;

    @FXML
    private TextField t_phone;

    @FXML
    private ChoiceBox<String> t_gender;

    @FXML
    private ChoiceBox<Subject> t_subject;

    @FXML
    private ChoiceBox<ClassEntity> t_class;

    @FXML
    private ChoiceBox<Section> t_section;


    @FXML
    private TextArea t_address;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Label count;

    @FXML
    private TextField search_box;

    @FXML
    private TableView<Teacher> t_table;
    @FXML
    private TableColumn<Teacher, Integer> col_t_id;

    @FXML
    private TableColumn<Teacher, String> col_t_name;

    @FXML
    private TableColumn<Teacher, Integer> col_t_age;

    @FXML
    private TableColumn<Teacher, String> col_t_phone;

    @FXML
    private TableColumn<Teacher, String> col_t_gender;

    @FXML
    private TableColumn<Teacher, Subject> col_t_subject;

    @FXML
    private TableColumn<Teacher, ClassEntity> col_t_class;

    @FXML
    private TableColumn<Teacher, Section> col_t_section;

    @FXML
    private TableColumn<Teacher, String> col_t_address;

    @FXML
    private TableColumn<Teacher, String> col_t_entered_date;

    @FXML
    private TableColumn<Teacher, Void> actionColumn;


    @FXML
    void addTeacher(ActionEvent event) {


        try {
            String name = t_name.getText();
            int age = Integer.parseInt(t_age.getText());
            String phone = t_phone.getText();
            String address = t_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || t_gender.getSelectionModel().isEmpty() || t_class.getSelectionModel().isEmpty() || t_section.getSelectionModel().isEmpty() || t_subject.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = t_gender.getSelectionModel().getSelectedItem();
                int class_id = t_class.getSelectionModel().getSelectedItem().getId();
                int section_id = t_section.getSelectionModel().getSelectedItem().getId();
                int subject_id = t_subject.getSelectionModel().getSelectedItem().getId();

                Teacher teacher = new Teacher(0,age,phone,gender,new ClassEntity(class_id,0,null),new Section(section_id,null),new Subject(subject_id,null),name,address,null);
                TeacherService studentService = new TeacherService();
                if (studentService.insertTeacher(teacher)) {

                    t_name.setText("");
                    t_age.setText("");
                    t_phone.setText("");
                    t_address.setText("");
                    t_gender.getSelectionModel().clearSelection();
                    t_class.getSelectionModel().clearSelection();
                    t_section.getSelectionModel().clearSelection();
                    t_subject.getSelectionModel().clearSelection();

                    addDataToTable();

                }
            }

        } catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        } catch (Exception e) {
            AlertDialog.show("Error", e.getMessage());
        }


    }

    private ObservableList<Teacher> teachers;
    public void addDataToTable() {
        TeacherService teacherService = new TeacherService();
        teachers = FXCollections.observableArrayList(teacherService.getAllTeachers());
        count.setText(String.valueOf(teachers.size()));
        t_table.setItems(teachers);
    }

    @FXML
    void cancelTeacher(ActionEvent event) {
        t_name.setText("");
        t_age.setText("");
        t_phone.setText("");
        t_address.setText("");
        t_class.getSelectionModel().clearSelection();
        t_section.getSelectionModel().clearSelection();
        t_subject.getSelectionModel().clearSelection();

        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);
    }

    private int teacher_id;
    @FXML
    void editTeacher(ActionEvent event) {

        try {
            String name = t_name.getText();
            int age = Integer.parseInt(t_age.getText());
            String phone = t_phone.getText();
            String address = t_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || t_gender.getSelectionModel().isEmpty() || t_class.getSelectionModel().isEmpty() || t_section.getSelectionModel().isEmpty() || t_subject.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = t_gender.getSelectionModel().getSelectedItem();
                int class_id = t_class.getSelectionModel().getSelectedItem().getId();
                int section_id = t_section.getSelectionModel().getSelectedItem().getId();
                int subject_id = t_subject.getSelectionModel().getSelectedItem().getId();

                Teacher teacher = new Teacher(teacher_id,age,phone,gender,new ClassEntity(class_id,0,null),new Section(section_id,null),new Subject(subject_id,null),name,address,null);
                TeacherService studentService = new TeacherService();
                if (studentService.updateTeacher(teacher)) {

                    t_name.setText("");
                    t_age.setText("");
                    t_phone.setText("");
                    t_address.setText("");
                    t_gender.getSelectionModel().clearSelection();
                    t_class.getSelectionModel().clearSelection();
                    t_section.getSelectionModel().clearSelection();
                    t_subject.getSelectionModel().clearSelection();

                    addDataToTable();

                }
            }

        } catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        } catch (Exception e) {
            AlertDialog.show("Error", e.getMessage());
        }
    }

    @FXML
    void search(ActionEvent event) {
        String searchTxt = search_box.getText();

        TeacherService studentService = new TeacherService();
        teachers = FXCollections.observableArrayList(studentService.getAllTeachersBySearch(searchTxt));
        t_table.setItems(teachers);
    }

    private ObservableList<Section> sections;
    private ObservableList<ClassEntity> classEntities;
    private ObservableList<String> genders;
    private ObservableList<Subject> subjects;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView searchImg = new ImageView("/school/assets/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);

        SectionService sectionService = new SectionService();
        sections = FXCollections.observableArrayList(sectionService.getAllSections());
        t_section.setItems(sections);

        ClassService classService = new ClassService();
        classEntities = FXCollections.observableArrayList(classService.getAllClassName());
        t_class.setItems(classEntities);

        SubjectService subjectService = new SubjectService();
        subjects = FXCollections.observableArrayList(subjectService.getAllSubjects());
        t_subject.setItems(subjects);

        genders = FXCollections.observableArrayList("male","female");
        t_gender.setItems(genders);

        col_t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_t_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_t_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_t_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_t_class.setCellValueFactory(new PropertyValueFactory<>("classEntity"));
        col_t_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_t_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_t_section.setCellValueFactory(new PropertyValueFactory<>("section"));
        col_t_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_t_entered_date.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        addDataToTable();
        customizeActionTableColumn();
    }

    public void customizeActionTableColumn() {
        Callback<TableColumn<Teacher, Void>, TableCell<Teacher, Void>> factory = new Callback<TableColumn<Teacher, Void>, TableCell<Teacher, Void>>() {
            @Override
            public TableCell<Teacher, Void> call(TableColumn<Teacher, Void> classSectionVoidTableColumn) {
                final TableCell<Teacher, Void> cell = new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");


                    {
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0,10,0,10));
                        hBox.getChildren().addAll(editButton,deleteButton);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = t_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                Teacher teacher = teachers.get(ind);
                                t_name.setText(teacher.getName());
                                t_age.setText(String.valueOf(teacher.getAge()));
                                t_phone.setText(teacher.getPhone());
                                t_address.setText(teacher.getAddress());
                                t_gender.getSelectionModel().select(teacher.getGender());

                                for (ClassEntity ce : classEntities) {
                                    if(ce.getId() == teacher.getClassEntity().getId()){
                                        t_class.getSelectionModel().select(ce);
                                        break;
                                    }
                                }

                                for (Section s : sections) {
                                    if(s.getId() == teacher.getSection().getId()){
                                        t_section.getSelectionModel().select(s);
                                        break;
                                    }
                                }

                                for (Subject s : subjects) {
                                    if(s.getId() == teacher.getSubject().getId()){
                                        t_subject.getSelectionModel().select(s);
                                        break;
                                    }
                                }
                                teacher_id = teacher.getId();

                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction( e -> {
                            int ind = t_table.getSelectionModel().getFocusedIndex();
                            TeacherService teacherService = new TeacherService();
                            if (ind != -1) {
                                Teacher teacher = teachers.get(ind);
                                if (teacherService.deleteTeacherById(teacher.getId())) {
                                    addDataToTable();
                                }
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void unused, boolean b) {
                        super.updateItem(unused, b);

                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(hBox);
                        }
                    }
                };
                return cell;
            }

        };
        actionColumn.setCellFactory(factory);

    }
}
