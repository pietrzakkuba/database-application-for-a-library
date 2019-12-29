package fxapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
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

public class AffiliatesController implements Initializable {
    public Button toMainMenuButton;
    public TableView<AffiliatesTable> mainTable;
    public TableColumn<AffiliatesTable, Integer> id_number;
    public TableColumn<AffiliatesTable, String> address;
    public TableColumn<AffiliatesTable, Integer> opening_hours_from;
    public TableColumn<AffiliatesTable, Integer> opening_hours_to;
    public TableColumn<AffiliatesTable, Integer> number_of_employees;
    public TextField filter_text_box;

    private ObservableList<AffiliatesTable> data;
    private ArrayList<AffiliatesTable> filtered_data = new ArrayList<AffiliatesTable>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadAffiliates();
            data = FXCollections.observableArrayList(DatabaseConnection.getAffiliatesTableArrayList());
            id_number.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, Integer>("id_number"));
            address.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, String>("address"));
            opening_hours_from.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, Integer>("opening_hours_from"));
            opening_hours_to.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, Integer>("opening_hours_to"));
            number_of_employees.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, Integer>("number_of_employees"));
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
        for (AffiliatesTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
