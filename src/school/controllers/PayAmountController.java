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
import school.models.pay_amount.PayAmount;
import school.models.pay_amount.PayAmountService;
import school.models.section.SectionService;

import java.net.URL;
import java.util.ResourceBundle;

public class PayAmountController implements Initializable {

    @FXML
    private ChoiceBox<ClassEntity> pay_class;

    @FXML
    private ChoiceBox<Section> pay_section;

    @FXML
    private TextField pay_student;

    @FXML
    private TextField pay_teacher;

    @FXML
    private Label pay_count;

    @FXML
    private TextField pay_search_box;

    @FXML
    private Button searchBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button addBtn;

    @FXML
    private TableView<PayAmount> pay_table;

    @FXML
    private TableColumn<PayAmount, Integer> col_pay_id;

    @FXML
    private TableColumn<PayAmount, String> col_pay_class;

    @FXML
    private TableColumn<PayAmount, String> col_pay_section;

    @FXML
    private TableColumn<PayAmount, Integer> col_pay_student;

    @FXML
    private TableColumn<PayAmount, Integer> col_pay_teacher;

    @FXML
    private TableColumn<PayAmount, Void> col_pay_action;





    @FXML
    void addPay(ActionEvent event) {


        try {
            int studentAmount = Integer.parseInt(pay_student.getText());
            int teacherAmount = Integer.parseInt(pay_teacher.getText());

            if (studentAmount < 0 || teacherAmount < 0 || pay_class.getSelectionModel().isEmpty() || pay_section.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                int class_id = pay_class.getSelectionModel().getSelectedItem().getId();
                int section_id = pay_section.getSelectionModel().getSelectedItem().getId();

                PayAmount payAmount = new PayAmount(0, new ClassEntity(class_id,0,null), new Section(section_id, null), teacherAmount,studentAmount);
                PayAmountService payAmountService = new PayAmountService();
                boolean result = payAmountService.insertPayAmount(payAmount);
                if (result) {
                    pay_teacher.setText("");
                    pay_student.setText("");
                    pay_class.getSelectionModel().clearSelection();
                    pay_section.getSelectionModel().clearSelection();

                    addDataToTable();
                }
            }
        } catch (NumberFormatException e) {
            AlertDialog.show("Error", "Write Number");
        }
    }
    ObservableList<PayAmount> payAmounts;

    public void addDataToTable() {
        PayAmountService payAmountService = new PayAmountService();
        payAmounts = FXCollections.observableArrayList(payAmountService.getAllPayAmount());
        pay_count.setText(String.valueOf(payAmounts.size()));
        pay_table.setItems(payAmounts);

    }

    @FXML
    void cancelPay(ActionEvent event) {
        pay_student.setText("");
        pay_teacher.setText("");
        pay_class.getSelectionModel().clearSelection();
        pay_section.getSelectionModel().clearSelection();

        addBtn.setDisable(false);
        addBtn.setVisible(true);

        editBtn.setDisable(true);
        editBtn.setVisible(false);

    }

    @FXML
    void search(ActionEvent event) {
        String searchTxt = pay_search_box.getText();

        PayAmountService payAmountService = new PayAmountService();
        payAmounts = FXCollections.observableArrayList(payAmountService.getAllPayAmountBySearch(searchTxt));
        pay_table.setItems(payAmounts);
    }

    private int payAmount_id;
    @FXML
    void editPay(ActionEvent event) {
        try {
            int studentAmount = Integer.parseInt(pay_student.getText());
            int teacherAmount = Integer.parseInt(pay_teacher.getText());

            if (studentAmount < 0 || teacherAmount < 0 || pay_class.getSelectionModel().isEmpty() || pay_section.getSelectionModel().isEmpty()) {
                AlertDialog.show("Error", "Something is Wrong");
            } else {
                int class_id = pay_class.getSelectionModel().getSelectedItem().getId();
                int section_id = pay_section.getSelectionModel().getSelectedItem().getId();

                PayAmount payAmount = new PayAmount(0, new ClassEntity(class_id, 0, null), new Section(section_id, null), teacherAmount, studentAmount);
                PayAmountService payAmountService = new PayAmountService();
                boolean result = payAmountService.updatePayAmount(payAmount, payAmount_id);
                if (result) {
                    pay_teacher.setText("");
                    pay_student.setText("");
                    pay_class.getSelectionModel().clearSelection();
                    pay_section.getSelectionModel().clearSelection();

                    addBtn.setDisable(false);
                    addBtn.setVisible(true);

                    editBtn.setDisable(true);
                    editBtn.setVisible(false);

                    addDataToTable();
                }
            }
        }catch (NumberFormatException e) {
                AlertDialog.show("Error", "Write Number");
            }
    }

    private ObservableList<Section> sections;
    private ObservableList<ClassEntity> classEntities;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView searchImg = new ImageView("/school/assets/img/search.png");
        searchImg.setFitWidth(20);
        searchImg.setFitHeight(20);
        searchBtn.setGraphic(searchImg);

        SectionService sectionService = new SectionService();
        sections = FXCollections.observableArrayList(sectionService.getAllSections());
        pay_section.setItems(sections);

        ClassService classService = new ClassService();
        classEntities = FXCollections.observableArrayList(classService.getAllClassName());
        pay_class.setItems(classEntities);

        col_pay_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_pay_class.setCellValueFactory(new PropertyValueFactory<>("classEntity"));
        col_pay_section.setCellValueFactory(new PropertyValueFactory<>("section"));
        col_pay_student.setCellValueFactory(new PropertyValueFactory<>("student_amount"));
        col_pay_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher_amount"));

        addDataToTable();
        customizeActionTableColumn();
    }

    public void customizeActionTableColumn() {
        Callback<TableColumn<PayAmount, Void>, TableCell<PayAmount, Void>> factory = new Callback<TableColumn<PayAmount, Void>, TableCell<PayAmount, Void>>() {
            @Override
            public TableCell<PayAmount, Void> call(TableColumn<PayAmount, Void> classSectionVoidTableColumn) {
                final TableCell<PayAmount, Void> cell = new TableCell<>() {
                    final HBox hBox = new HBox();
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");


                    {
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0,10,0,10));
                        hBox.getChildren().addAll(editButton,deleteButton);
                        editButton.setId("editBtn");
                        editButton.setOnAction(e -> {
                            int ind = pay_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                PayAmount payAmount = payAmounts.get(ind);
                                pay_teacher.setText(String.valueOf(payAmount.getTeacher_amount()));
                                pay_student.setText(String.valueOf(payAmount.getStudent_amount()));

                                for (ClassEntity ce : classEntities) {
                                    if(ce.getId() == payAmount.getClassEntity().getId()){
                                        pay_class.getSelectionModel().select(ce);
                                        break;
                                    }
                                }
                                for (Section s : sections) {
                                    if(s.getId() == payAmount.getSection().getId()){
                                        pay_section.getSelectionModel().select(s);
                                        break;
                                    }
                                }

                                payAmount_id = payAmount.getId();

                                addBtn.setDisable(true);
                                addBtn.setVisible(false);

                                editBtn.setDisable(false);
                                editBtn.setVisible(true);

                            }
                        });

                        deleteButton.setId("cancelBtn");
                        deleteButton.setOnAction( e -> {
                            PayAmountService payAmountService = new PayAmountService();
                            int ind = pay_table.getSelectionModel().getFocusedIndex();
                            if (ind != -1) {
                                PayAmount payAmount = payAmounts.get(ind);
                                if (payAmountService.deletePayAmountById(payAmount.getId())) {
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
        col_pay_action.setCellFactory(factory);

    }
}