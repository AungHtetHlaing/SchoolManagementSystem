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

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TextField s_name;

    @FXML
    private TextField s_age;

    @FXML
    private TextField s_phone;

    @FXML
    private ChoiceBox<String> s_gender;

    @FXML
    private ChoiceBox<ClassEntity> s_class;

    @FXML
    private ChoiceBox<Section> s_section;

    @FXML
    private TextArea s_address;

    @FXML
    private Label s_count;

    @FXML
    private TextField s_search_box;

    @FXML
    private Button searchBtn;

    @FXML
    private Button editBtn;


    @FXML
    private Button addBtn;

    @FXML
    private TableView<Student> stu_table;

    @FXML
    private TableColumn<Student, Integer> col_stu_id;

    @FXML
    private TableColumn<Student, String> col_stu_name;

    @FXML
    private TableColumn<Student, Integer> col_stu_age;

    @FXML
    private TableColumn<Student, String> col_stu_phone;

    @FXML
    private TableColumn<Student, String> col_stu_gender;

    @FXML
    private TableColumn<Student, ClassEntity> col_stu_class;

    @FXML
    private TableColumn<Student, Section> col_stu_section;

    @FXML
    private TableColumn<Student, String> col_stu_address;

    @FXML
    private TableColumn<Student, String> col_stu_entered_date;

    @FXML
    private TableColumn<Student, Void> actionColumn;

    @FXML
    void search(ActionEvent event) {
        String searchTxt = s_search_box.getText();

        StudentService studentService = new StudentService();
        students = FXCollections.observableArrayList(studentService.getAllStudentsBySearch(searchTxt));
        stu_table.setItems(students);
    }

    @FXML
    void addStudent(ActionEvent event) {

        try {
            String name = s_name.getText();
            int age = Integer.parseInt(s_age.getText());
            String phone = s_phone.getText();
            String address = s_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || s_gender.getSelectionModel().isEmpty() || s_class.getSelectionModel().isEmpty() || s_section.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = s_gender.getSelectionModel().getSelectedItem();
                int class_id = s_class.getSelectionModel().getSelectedItem().getId();
                int section_id = s_section.getSelectionModel().getSelectedItem().getId();

                Student student = new Student(0, age, phone, gender, new ClassEntity(class_id,0,null), new Section(section_id, null), name, address, null);
                StudentService studentService = new StudentService();
                if (studentService.insertStudent(student)) {

                        s_name.setText("");
                        s_age.setText("");
                        s_phone.setText("");
                        s_address.setText("");
                        s_gender.getSelectionModel().clearSelection();
                        s_class.getSelectionModel().clearSelection();
                        s_section.getSelectionModel().clearSelection();

                        addDataToTable();

                }
            }

        } catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        } catch (Exception e) {
            AlertDialog.show("Error", e.getMessage());
        }



    }

    private ObservableList<Student> students;
    public void addDataToTable() {
        StudentService studentService = new StudentService();
        students = FXCollections.observableArrayList(studentService.getAllStudents());
        s_count.setText(String.valueOf(students.size()));
        stu_table.setItems(students);
    }

    private int student_id;
    @FXML
    void editStudent(ActionEvent event) {


        try {
            String name = s_name.getText();
            int age = Integer.parseInt(s_age.getText());
            String phone = s_phone.getText();
            String address = s_address.getText();

            if (age < 0  || phone.isEmpty() || name.isEmpty() || address.isEmpty() || s_gender.getSelectionModel().isEmpty() || s_class.getSelectionModel().isEmpty() || s_section.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                if (phone.length() > 11) {
                    throw new Exception("Phone No must be 11 length");
                }
                String gender = s_gender.getSelectionModel().getSelectedItem();
                int class_id = s_class.getSelectionModel().getSelectedItem().getId();
                int section_id = s_section.getSelectionModel().getSelectedItem().getId();

                Student student = new Student(student_id, age, phone, gender, new ClassEntity(class_id,0,null), new Section(section_id, null), name, address, null);
                StudentService studentService = new StudentService();
                if (studentService.updateStudent(student)) {
                    s_name.setText("");
                    s_age.setText("");
                    s_phone.setText("");
                    s_address.setText("");
                    s_gender.getSelectionModel().clearSelection();
                    s_class.getSelectionModel().clearSelection();
                    s_section.getSelectionModel().clearSelection();

                    addBtn.setDisable(false);
                    addBtn.setVisible(true);

                    editBtn.setDisable(true);
                    editBtn.setVisible(false);

                    addDataToTable();
                }
            }

        }catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        } catch (Exception e) {
            AlertDialog.show("Error", e.getMessage());
        }
    }

    @FXML
    void cancelStudent(ActionEvent event) {
        s_name.setText("");
        s_age.setText("");
        s_phone.setText("");
        s_address.setText("");
        s_class.getSelectionModel().clearSelection();
        s_section.getSelectionModel().clearSelection();

        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);
    }

    private ObservableList<Section> sections;
    private ObservableList<ClassEntity> classEntities;
    private ObservableList<String> genders;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ImageView searchImg = new ImageView("/school/assets/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);

        SectionService sectionService = new SectionService();
        sections = FXCollections.observableArrayList(sectionService.getAllSections());
        s_section.setItems(sections);

        ClassService classService = new ClassService();
        classEntities = FXCollections.observableArrayList(classService.getAllClassName());
        s_class.setItems(classEntities);

        genders = FXCollections.observableArrayList("male","female");
        s_gender.setItems(genders);

        col_stu_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_stu_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_stu_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_stu_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_stu_class.setCellValueFactory(new PropertyValueFactory<>("classEntity"));
        col_stu_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_stu_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_stu_section.setCellValueFactory(new PropertyValueFactory<>("section"));
        col_stu_entered_date.setCellValueFactory(new PropertyValueFactory<>("created_at"));


        addDataToTable();
        customizeActionTableColumn();
    }

    public void customizeActionTableColumn() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> factory = new Callback<TableColumn<Student, Void>, TableCell<Student, Void>>() {
            @Override
            public TableCell<Student, Void> call(TableColumn<Student, Void> classSectionVoidTableColumn) {
                final TableCell<Student, Void> cell = new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");


                    {
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0,10,0,10));
                        hBox.getChildren().addAll(editButton,deleteButton);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = stu_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                Student student = students.get(ind);
                                s_name.setText(student.getName());
                                s_age.setText(String.valueOf(student.getAge()));
                                s_phone.setText(student.getPhone());
                                s_address.setText(student.getAddress());
                                s_gender.getSelectionModel().select(student.getGender());

                                for (ClassEntity ce : classEntities) {
                                    if(ce.getId() == student.getClassEntity().getId()){
                                        s_class.getSelectionModel().select(ce);
                                        break;
                                    }
                                }

                                for (Section s : sections) {
                                    if(s.getId() == student.getSection().getId()){
                                        s_section.getSelectionModel().select(s);
                                        break;
                                    }
                                }
                                student_id = student.getId();

                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction( e -> {
                            int ind = stu_table.getSelectionModel().getFocusedIndex();
                            StudentService studentService = new StudentService();
                            if (ind != -1) {
                                Student student = students.get(ind);
                                if (studentService.deleteStudentById(student.getId())) {
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
