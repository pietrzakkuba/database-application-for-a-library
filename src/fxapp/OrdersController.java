package fxapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.OrdersTable;

import java.io.IOException;

public class OrdersController implements Initializable {
    public Button toMainMenuButton;
    public TableView<OrdersTable> mainTable;
    public TableColumn<OrdersTable, Integer> id;
    public TableColumn<OrdersTable, Date> order_date;
    public TableColumn<OrdersTable, String> reader_first_name;
    public TableColumn<OrdersTable, String> reader_last_name;
    public TableColumn<OrdersTable, String> book_title;
    public TextField filter_text_box;

    private ObservableList<OrdersTable> data;
    private ArrayList<OrdersTable> filtered_data = new ArrayList<OrdersTable>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadOrders();
            data = FXCollections.observableArrayList(DatabaseConnection.getOrdersTableArrayList());
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

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void modify(ActionEvent event) {

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
