package fxapp;

import fxapp.containers.*;
import fxapp.editWindows.AddElement;
import fxapp.editWindows.DeleteElement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.BooksTable;
import sql.tables.CheckOutsTable;
import sql.tables.CopiesTable;


public class CheckOutsController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<CheckOutsTable> mainTable;
    public TableColumn<CheckOutsTable, Integer> checkout_id;
    public TableColumn<CheckOutsTable, String> reader_first_name;
    public TableColumn<CheckOutsTable, String> reader_last_name;
    public TableColumn<CheckOutsTable, String> book_title;
    public TableColumn<CheckOutsTable, Integer> copy_id;
    public TableColumn<CheckOutsTable, Date> check_out_date;
    public TableColumn<CheckOutsTable, Integer> check_out_period;
    public TableColumn<CheckOutsTable, Date> return_date;

    public TextField filter_text_box;

    private static ObservableList<CheckOutsTable> data;
    public Button returnCopyButton;
    private ArrayList<CheckOutsTable> filtered_data = new ArrayList<CheckOutsTable>();

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadCheckOuts();
        data = FXCollections.observableArrayList(DatabaseConnection.getCheckOutsTableArrayList());
    }

    @Override
    public void reload() {
        try {
            loadToArray();
            CopiesController.loadToArray();
            ReadersController.loadToArray();
            checkout_id.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, Integer>("checkout_id"));
            reader_first_name.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, String>("reader_first_name"));
            reader_last_name.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, String>("reader_last_name"));
            book_title.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, String>("book_title"));
            copy_id.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, Integer>("copy_id"));
            check_out_date.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, Date>("check_out_date"));
            check_out_period.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, Integer>("check_out_period"));
            return_date.setCellValueFactory(new PropertyValueFactory<CheckOutsTable, Date>("return_date"));
            mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
    }

    @FXML
    void modify(ActionEvent event) {
        TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        CheckOutsTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldWithChoiceParameter("Copy of book", true, CopiesController::getMatchingRecords ,item.getCopy_id() + " " + item.getBook_title(), item.getCopy_id()));
        parameters.add(new TextFieldWithChoiceParameter("Reader", true, ReadersController::getMatchingRecords, item.getReader_id() + " " + item.getReader_first_name() + " " + item.getReader_last_name() ,item.getReader_id()));
        parameters.add(new DateParameter("Date of checkout", true, item.getCheck_out_date()));
        parameters.add(new TextFieldParameter("Rented for", true, Integer.toString(item.getCheck_out_period())));

        ((TextFieldParameter)parameters.get(3)).markAsInteger();

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startModifying(item.getCheckout_id(),this,currentWindow, parameters, DatabaseConnection::modifyCheckout,"Modify checkout");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void add(ActionEvent event) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldWithChoiceParameter("Copy of book", true, CopiesController::getMatchingRecords));
        parameters.add(new TextFieldWithChoiceParameter("Reader", true, ReadersController::getMatchingRecords));
        parameters.add(new DateParameter("Date of checkout", true));
        parameters.add(new TextFieldParameter("Rented for", true));

        ((TextFieldParameter)parameters.get(3)).markAsInteger();

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addCheckout, "Add checkout");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void delete(ActionEvent event) {
        TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        CheckOutsTable item = mainTable.getItems().get(row);

        if(item.getReturn_date() == null)
            return;

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("Copy of book", item.getCopy_id() + " " + item.getBook_title()));
        parameters.add(new LabelParameter("Reader", item.getReader_id() + " " + item.getReader_first_name() + " " + item.getReader_last_name() ));
        parameters.add(new LabelParameter("Date of checkout", item.getCheck_out_date()));
        parameters.add(new LabelParameter("Rented for", Integer.toString(item.getCheck_out_period())));
        parameters.add(new LabelParameter("Date of return", item.getReturn_date()));
        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getCheckout_id(),this,currentWindow, parameters, DatabaseConnection::deleteCheckout,"Delete checkout");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    public void toMainMenu(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("main-menu.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void filtering(KeyEvent onKeyReleased) {
        filtered_data.clear();
        for (CheckOutsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }

    public void returnCopy(ActionEvent actionEvent) throws SQLException {
        int row;
        try {
            row = mainTable.getSelectionModel().getSelectedCells().get(0).getRow();
        }catch (IndexOutOfBoundsException e){
            return;
        }
        CheckOutsTable item = mainTable.getItems().get(row);
        DatabaseConnection.returnCopy(item.getCopy_id());
        reload();
    }
}
