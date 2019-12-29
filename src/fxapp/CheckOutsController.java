package fxapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import sql.tables.CheckOutsTable;


public class CheckOutsController implements Initializable {
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

    private ObservableList<CheckOutsTable> data;
    private ArrayList<CheckOutsTable> filtered_data = new ArrayList<CheckOutsTable>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadCheckOuts();
            data = FXCollections.observableArrayList(DatabaseConnection.getCheckOutsTableArrayList());
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
}
