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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.BooksTable;
import sql.tables.ReadersTable;
import sql.tables.SectionsTable;

import java.io.IOException;

public class SectionsController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<SectionsTable> mainTable;
    public TableColumn<SectionsTable, Integer> section_id;
    public TableColumn<SectionsTable, String> section_name;
    public TableColumn<SectionsTable, String> section_short_name;
    public TableColumn<SectionsTable, Integer> number_of_books;
    public TableColumn<SectionsTable, String> affiliate_name;
    public TextField filter_text_box;

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadSections();
        data = FXCollections.observableArrayList(DatabaseConnection.getSectionsTableArrayList());
    }

    @Override
    public void reload() {
        try{
        loadToArray();
        AffiliatesController.loadToArray();
        section_id.setCellValueFactory(new PropertyValueFactory<SectionsTable, Integer>("section_id"));
        section_name.setCellValueFactory(new PropertyValueFactory<SectionsTable, String>("section_name"));
        section_short_name.setCellValueFactory(new PropertyValueFactory<SectionsTable, String>("section_short_name"));
        number_of_books.setCellValueFactory(new PropertyValueFactory<SectionsTable, Integer>("number_of_books"));
        affiliate_name.setCellValueFactory(new PropertyValueFactory<SectionsTable, String>("affiliate_name"));
        mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<SectionsTable> data;
    private ArrayList<SectionsTable> filtered_data = new ArrayList<SectionsTable>();

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (SectionsTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getSection_id(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
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

        SectionsTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Section name", true, item.getSection_name()));
        parameters.add(new TextFieldParameter("Short name", true, item.getSection_short_name()));
        parameters.add(new TextFieldWithChoiceParameter("Affiliate", true, AffiliatesController::getMatchingRecords,item.getAffiliate_id() + " " + item.getAffiliate_name(), item.getAffiliate_id()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startModifying(item.getSection_id(),this,currentWindow, parameters, DatabaseConnection::modifySection,"Modify section");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void add(ActionEvent event) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Section name", true));
        parameters.add(new TextFieldParameter("Short name", true));
        parameters.add(new TextFieldWithChoiceParameter("Affiliate", true, AffiliatesController::getMatchingRecords));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addSection, "Add section");
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

        SectionsTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("Section name", item.getSection_name()));
        parameters.add(new LabelParameter("Short name", item.getSection_short_name()));
        parameters.add(new LabelParameter("Affiliate",item.getAffiliate_id() + " " + item.getAffiliate_name()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getSection_id(),this,currentWindow, parameters, DatabaseConnection::deleteSection,"Delete section");
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
        for (SectionsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
