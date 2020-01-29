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
import sql.tables.CheckOutsTable;
import sql.tables.OrdersTable;

import java.io.IOException;

public class OrdersController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<OrdersTable> mainTable;
    public TableColumn<OrdersTable, Integer> id;
    public TableColumn<OrdersTable, Date> order_date;
    public TableColumn<OrdersTable, String> reader_first_name;
    public TableColumn<OrdersTable, String> reader_last_name;
    public TableColumn<OrdersTable, String> book_title;
    public TextField filter_text_box;

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadOrders();
        data = FXCollections.observableArrayList(DatabaseConnection.getOrdersTableArrayList());
    }

    @Override
    public void reload() {
        try {
            loadToArray();
            BooksController.loadToArray();
            ReadersController.loadToArray();
            id.setCellValueFactory(new PropertyValueFactory<OrdersTable, Integer>("id"));
            order_date.setCellValueFactory(new PropertyValueFactory<OrdersTable, Date>("order_date"));
            reader_first_name.setCellValueFactory(new PropertyValueFactory<OrdersTable, String>("reader_first_name"));
            reader_last_name.setCellValueFactory(new PropertyValueFactory<OrdersTable, String>("reader_last_name"));
            book_title.setCellValueFactory(new PropertyValueFactory<OrdersTable, String>("book_title"));
            mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<OrdersTable> data;
    private ArrayList<OrdersTable> filtered_data = new ArrayList<OrdersTable>();

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

        OrdersTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldWithChoiceParameter("Book", true, BooksController::getMatchingRecords ,item.getBook_title(), item.getBook_id()));
        parameters.add(new TextFieldWithChoiceParameter("Reader", true, ReadersController::getMatchingRecords, item.getReader_id() + " " + item.getReader_first_name() + " " + item.getReader_last_name() ,item.getReader_id()));
        parameters.add(new DateParameter("Date of order", true, item.getOrder_date()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startModifying(item.getId(),this,currentWindow, parameters, DatabaseConnection::modifyOrder,"Modify order");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void add(ActionEvent event) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldWithChoiceParameter("Book", true, BooksController::getMatchingRecords));
        parameters.add(new TextFieldWithChoiceParameter("Reader", true, ReadersController::getMatchingRecords));
        parameters.add(new DateParameter("Date of order", true));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addOrder, "Add order");
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

        OrdersTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("Book", item.getBook_title()));
        parameters.add(new LabelParameter("Reader", item.getReader_id() + " " + item.getReader_first_name() + " " + item.getReader_last_name()));
        parameters.add(new LabelParameter("Date of order", item.getOrder_date()));
        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getId(),this,currentWindow, parameters, DatabaseConnection::deleteOrder,"Delete order");
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
        for (OrdersTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
