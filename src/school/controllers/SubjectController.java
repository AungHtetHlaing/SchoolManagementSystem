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
import school.models.class_entity.ClassSection;
import school.models.class_entity.ClassService;
import school.models.subject.Subject;
import school.models.subject.SubjectClass;
import school.models.subject.SubjectService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SubjectController implements Initializable {

    @FXML
    private TextField sub_name;

    @FXML
    private ChoiceBox<ClassEntity> sub_class;

    @FXML
    private Label sub_count;

    @FXML
    private TextField sub_search_box;

    @FXML
    private Button searchBtn;


    @FXML
    private TableView<SubjectClass> sub_table;

    @FXML
    private TableColumn<SubjectClass, String> col_sub_name;

    @FXML
    private TableColumn<SubjectClass, String> col_sub_class;

    @FXML
    private TableColumn<SubjectClass, Void> actionColumn;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    private ObservableList<ClassEntity> classEntities;
    private ObservableList<SubjectClass> subjectClasses;

    @FXML
    void addSubject(ActionEvent event) {

        String subjectName = sub_name.getText();

        if (subjectName.isEmpty() || sub_class.getSelectionModel().isEmpty()) {
            AlertDialog.show("Error", "Something is empty");
        } else {
            int class_id = sub_class.getSelectionModel().getSelectedItem().getId();

            Subject subject = new Subject(0,subjectName);
            SubjectService subjectService = new SubjectService();
            boolean subResult = subjectService.insertSubject(subject);

            int subject_id;

            if (subResult) {
                subject_id = subjectService.getSubjectByName(subject.getName()).getId();
            } else {
                subject_id = subjectService.getSubjectByName(subjectName).getId();
            }
            boolean idInsertResult = subjectService.insertSubjectIdAndClassId(class_id, subject_id);

            if (idInsertResult) {
                sub_name.setText("");
                sub_class.getSelectionModel().clearSelection();

                addDataToTable();
            } else {
                sub_name.setText("");
                sub_class.getSelectionModel().clearSelection();

                AlertDialog.show("Error", "They are already exit!");
            }
        }

    }

    @FXML
    void cancelSubject(ActionEvent event) {
        sub_name.setText("");
        sub_class.getSelectionModel().clearSelection();
        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);
    }


    private int old_class_id;
    @FXML
    void editSubject(ActionEvent event) {
        String subjectName = sub_name.getText();
        if (subjectName.isEmpty() || sub_class.getSelectionModel().isEmpty()) {
            AlertDialog.show("Error", "Something is empty");
        } else {
            int class_id = sub_class.getSelectionModel().getSelectedItem().getId();

            Subject subject = new Subject(0,subjectName);
            SubjectService subjectService = new SubjectService();
            boolean subResult = subjectService.insertSubject(subject);

            int subject_id;

            if (subResult) {
                subject_id = subjectService.getSubjectByName(subject.getName()).getId();
            } else {
                subject_id = subjectService.getSubjectByName(subjectName).getId();
            }
            boolean updateResult = subjectService.updateSubjectIdAndClassId(class_id, subject_id, old_class_id);

            if (updateResult) {
                sub_name.setText("");
                sub_class.getSelectionModel().clearSelection();

                addBtn.setDisable(false);
                addBtn.setVisible(true);

                editBtn.setDisable(true);
                editBtn.setVisible(false);

                addDataToTable();
            }
        }


    }


    @FXML
    void search(ActionEvent event) {
        String searchTxt = sub_search_box.getText();

        SubjectService subjectService = new SubjectService();
        subjectClasses = FXCollections.observableArrayList(subjectService.getSubjectNameAndClassNameBySearch(searchTxt));
        sub_table.setItems(subjectClasses);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView searchImg = new ImageView("/school/assets/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);

        ClassService classService = new ClassService();
        classEntities = FXCollections.observableArrayList(classService.getAllClassName());
        sub_class.setItems(classEntities);

        col_sub_name.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_sub_class.setCellValueFactory(new PropertyValueFactory<>("classEntity"));

        addDataToTable();
        customizeActionTableColumn();
    }

    public void addDataToTable() {
        SubjectService subjectService = new SubjectService();
        subjectClasses = FXCollections.observableArrayList(subjectService.getSubjectNameAndClassName());
        sub_count.setText(String.valueOf(subjectClasses.size()));
        sub_table.setItems(subjectClasses);
    }


    public void customizeActionTableColumn() {
        Callback<TableColumn<SubjectClass, Void>, TableCell<SubjectClass, Void>> factory = new Callback<TableColumn<SubjectClass, Void>, TableCell<SubjectClass, Void>>() {
            @Override
            public TableCell<SubjectClass, Void> call(TableColumn<SubjectClass, Void> classSectionVoidTableColumn) {
                final TableCell<SubjectClass, Void> cell = new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");


                    {
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0,10,0,10));
                        hBox.getChildren().addAll(editButton,deleteButton);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = sub_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                SubjectClass subjectClass = subjectClasses.get(ind);
                                sub_name.setText(subjectClass.getSubject().getName());

                                for (ClassEntity ce : classEntities) {
                                    if(ce.getId() == subjectClass.getClassEntity().getId()){
                                        sub_class.getSelectionModel().select(ce);
                                        break;
                                    }
                                }



                                old_class_id = subjectClass.getClassEntity().getId();


                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction( e -> {
                            int ind = sub_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                SubjectClass classSection = subjectClasses.get(ind);
                                SubjectService subjectService = new SubjectService();
                                if (subjectService.deleteSubjectAndClassId(classSection.getClassEntity().getId(), classSection.getSubject().getId())) {
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

