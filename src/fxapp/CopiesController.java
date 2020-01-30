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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.BooksTable;
import sql.tables.CopiesTable;

public class CopiesController extends Controller implements Initializable {
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
    public Button returnCopyButton;

    private static ObservableList<CopiesTable> data;
    private ArrayList<CopiesTable> filtered_data = new ArrayList<CopiesTable>();

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (CopiesTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getCopy_id(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
    }

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadCopies();
        data = FXCollections.observableArrayList(DatabaseConnection.getCopiesTableArrayList());
    }

    @Override
    public void reload() {
        try {
            BooksController.loadToArray();
            SectionsController.loadToArray();
            loadToArray();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
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
        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldWithChoiceParameter("Book",true,  BooksController::getMatchingRecords));
        parameters.add(new TextFieldWithChoiceParameter("Section and Affiliate",true,  SectionsController::getMatchingRecords));
        parameters.add(new TextFieldParameter("Cover's type",false, ""));
        parameters.add(new DateParameter("Release year",false, null));
        parameters.add(new TextFieldParameter("Release number",false, ""));

        ((TextFieldParameter)parameters.get(4)).markAsInteger();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addCopy,"Add copy");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
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

        // Item here is the table view type:
        CopiesTable item = mainTable.getItems().get(row);

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldWithChoiceParameter("Book",true, BooksController::getMatchingRecords, item.getBook_title(), item.getBook_id()));
        parameters.add(new TextFieldWithChoiceParameter("Section and Affiliate",true, SectionsController::getMatchingRecords, item.getSection() + ", " + item.getAffiliate(), item.getSection_id()));
        parameters.add(new TextFieldParameter("Availability", true, String.valueOf(item.isAvailability())));
        parameters.add(new TextFieldParameter("Cover's type",false, item.getType_of_cover()));
        parameters.add(new DateParameter("Release year",false, item.getRelease_year()));
        parameters.add(new TextFieldParameter("Release number",false, Integer.toString(item.getRelease_number())));

        ((TextFieldParameter)parameters.get(5)).markAsInteger();

        try {
            AddElement.startModifying(item.getCopy_id(),this,currentWindow, parameters, DatabaseConnection::modifyCopy,"Modify copy");
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

        // Item here is the table view type:
        CopiesTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("Book", item.getBook_title()));
        parameters.add(new LabelParameter("Availability", String.valueOf(item.isAvailability())));
        parameters.add(new LabelParameter("Cover's type", item.getType_of_cover()));
        parameters.add(new LabelParameter("Section and Affiliate", item.getSection() + ", " + item.getAffiliate()));
        parameters.add(new LabelParameter("Release year", item.getRelease_year()));
        parameters.add(new LabelParameter("Release number",  Integer.toString(item.getRelease_number())));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getCopy_id(),this,currentWindow, parameters, DatabaseConnection::deleteCopy,"Delete copy");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
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

    public void returnCopy(ActionEvent actionEvent) throws SQLException {
        int row;
        try {
            row = mainTable.getSelectionModel().getSelectedCells().get(0).getRow();
        }catch (IndexOutOfBoundsException e){
            return;
        }
        CopiesTable item = mainTable.getItems().get(row);
//        if (!item.isAvailability()) {
            DatabaseConnection.returnCopy(item.getCopy_id());
            reload();
//        }


    }
}
