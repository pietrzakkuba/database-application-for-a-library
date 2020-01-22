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
import sql.tables.CopiesTable;

public class CopiesController implements Initializable {
    public Button toMainMenuButton;
    public TableView<CopiesTable> mainTable;
    public TableColumn<CopiesTable, Integer> copy_id;
    public TableColumn<CopiesTable, String> book_title;
    public TableColumn<CopiesTable, Boolean> availability;
    public TableColumn<CopiesTable, String> section;
    public TableColumn<CopiesTable, String> affiliate;
    public TableColumn<CopiesTable, Integer> release_number;
    public TableColumn<CopiesTable, Date> release_year;
    public TableColumn<CopiesTable, String> type_of_cover;
    public TextField filter_text_box;

    private ObservableList<CopiesTable> data;
    private ArrayList<CopiesTable> filtered_data = new ArrayList<CopiesTable>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadCopies();
            data = FXCollections.observableArrayList(DatabaseConnection.getCopiesTableArrayList());
            copy_id.setCellValueFactory(new PropertyValueFactory<CopiesTable, Integer>("copy_id"));
            book_title.setCellValueFactory(new PropertyValueFactory<CopiesTable, String>("book_title"));
            availability.setCellValueFactory(new PropertyValueFactory<CopiesTable, Boolean>("availability"));
            section.setCellValueFactory(new PropertyValueFactory<CopiesTable, String>("section"));
            affiliate.setCellValueFactory(new PropertyValueFactory<CopiesTable, String>("affiliate"));
            release_number.setCellValueFactory(new PropertyValueFactory<CopiesTable, Integer>("release_number"));
            release_year.setCellValueFactory(new PropertyValueFactory<CopiesTable, Date>("release_year"));
            type_of_cover.setCellValueFactory(new PropertyValueFactory<CopiesTable, String>("type_of_cover"));
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

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void modify(ActionEvent event) {

    }

    public void filtering(KeyEvent onKeyReleased) {
        filtered_data.clear();
        for (CopiesTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
