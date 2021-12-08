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
import school.models.section.Section;
import school.models.class_entity.ClassService;
import school.models.section.SectionService;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassController implements Initializable {


    @FXML
    private TextField c_name;

    @FXML
    private ChoiceBox<Section> c_section;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Label c_count;

    @FXML
    private TextField c_search_box;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<ClassSection> classTable;

    @FXML
    private TableColumn<ClassSection, Integer> col_class_id;

    @FXML
    private TableColumn<ClassSection, String> col_class_name;

    @FXML
    private TableColumn<ClassSection, String> col_class_section;

    @FXML
    private TableColumn<ClassSection, Void> actionColumn;


    private ObservableList<Section> sections;
    private ObservableList<ClassSection> classSections;
    private ClassService classService;
    private SectionService sectionService;
    private int class_id = 0;

    @FXML
    void search(ActionEvent event) {
        String searchTxt = c_search_box.getText();

        classService = new ClassService();
        classSections = FXCollections.observableArrayList(classService.getAllClassBySearch(searchTxt));
        classTable.setItems(classSections);
    }

    @FXML
    void addClass(ActionEvent event) {
        String className = c_name.getText();

        if (className.isEmpty() || c_section.getSelectionModel().isEmpty()) {
            AlertDialog.show("Error", "Something is empty");
        } else {
            int section_id = c_section.getSelectionModel().getSelectedItem().getId();

            ClassEntity classEntity = new ClassEntity(0,section_id,className);
            classService = new ClassService();
            boolean result = classService.insertClass(classEntity);

            if (result) {
                c_name.setText("");
                c_section.getSelectionModel().clearSelection();

                addDataToTable();
            }
        }

    }


    @FXML
    void editClass(ActionEvent event) {
        String className = c_name.getText();
        int section_id = c_section.getSelectionModel().getSelectedItem().getId();


        if (className.isEmpty()) {
            AlertDialog.show("Error", "ClassName is empty");
        } else {
            ClassEntity classEntity = new ClassEntity(class_id,section_id,className);
            classService = new ClassService();
            boolean result = classService.updateClass(classEntity);

            if (result) {
                c_name.setText("");
                c_section.getSelectionModel().clearSelection();

                addBtn.setDisable(false);
                addBtn.setVisible(true);

                editBtn.setDisable(true);
                editBtn.setVisible(false);

                addDataToTable();
            }
        }


    }

    @FXML
    void cancelClass(ActionEvent event) {
        c_name.setText("");
        c_section.getSelectionModel().clearSelection();
        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ImageView searchImg = new ImageView("/school/assets/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);

        sectionService = new SectionService();
        sections = FXCollections.observableArrayList(sectionService.getAllSections());
        c_section.setItems(sections);



        col_class_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_class_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_class_section.setCellValueFactory(new PropertyValueFactory<>("section"));

        addDataToTable();
        customizeActionTableColumn();

    }

    public void addDataToTable() {
        classService = new ClassService();
        classSections = FXCollections.observableArrayList(classService.getAllClass());
        c_count.setText(String.valueOf(classSections.size()));
        classTable.setItems(classSections);
    }

    public void customizeActionTableColumn() {
        Callback<TableColumn<ClassSection, Void>, TableCell<ClassSection, Void>> factory = new Callback<TableColumn<ClassSection, Void>, TableCell<ClassSection, Void>>() {
            @Override
            public TableCell<ClassSection, Void> call(TableColumn<ClassSection, Void> classSectionVoidTableColumn) {
                final TableCell<ClassSection, Void> cell = new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");


                    {
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0,10,0,10));
                        hBox.getChildren().addAll(editButton,deleteButton);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = classTable.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                ClassSection classSection = classSections.get(ind);
                                c_name.setText(classSection.getName());
                                c_section.getSelectionModel().select(classSection.getSection().getId()-1);
                                class_id = classSection.getId();

                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction( e -> {
                            int ind = classTable.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                ClassSection classSection = classSections.get(ind);
                                if (classService.deleteClassById(classSection.getId())) {
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
