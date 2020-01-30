package fxapp;

import fxapp.containers.Choice;
import fxapp.containers.LabelParameter;
import fxapp.containers.Parameter;
import fxapp.containers.TextFieldParameter;
import fxapp.editWindows.AddElement;
import fxapp.editWindows.DeleteElement;
import fxapp.editWindows.MethodPasser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.AuthorsTable;

public class AffiliatesController extends Controller implements Initializable  {
    public Button toMainMenuButton;
    public TableView<AffiliatesTable> mainTable;
    public TableColumn<AffiliatesTable, Integer> id_number;
    public TableColumn<AffiliatesTable, String> address;
    public TableColumn<AffiliatesTable, String> opening_hours_from;
    public TableColumn<AffiliatesTable, String> opening_hours_to;
    public TableColumn<AffiliatesTable, Integer> number_of_employees;
    public TextField filter_text_box;

    private static ObservableList<AffiliatesTable> data;
    private ArrayList<AffiliatesTable> filtered_data = new ArrayList<AffiliatesTable>();

    @FXML
    void add(ActionEvent event) {
        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Address",true, 50));
        parameters.add(new TextFieldParameter("Opened from",true));
        parameters.add(new TextFieldParameter("Opened till",true));

        ((TextFieldParameter)parameters.get(1)).markAsTime();
        ((TextFieldParameter)parameters.get(2)).markAsTime();
        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addAffiliates,"Add affiliate");
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
        AffiliatesTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("Number", Integer.toString(item.getId_number())));
        parameters.add(new LabelParameter("Address",item.getAddress()));
        parameters.add(new LabelParameter("Opened from",item.getOpening_hours_from()));
        parameters.add(new LabelParameter("Opened till", item.getOpening_hours_to()));
        parameters.add(new LabelParameter("Number of employees", Integer.toString(item.getNumber_of_employees())));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getId_number(),this,currentWindow, parameters, DatabaseConnection::deleteAffiliates,"Delete affiliate");
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
        AffiliatesTable item = mainTable.getItems().get(row);
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Address",true, item.getAddress(), 50));
        parameters.add(new TextFieldParameter("Opened from",true, item.getOpening_hours_from()));
        parameters.add(new TextFieldParameter("Opened till",true, item.getOpening_hours_to()));

        ((TextFieldParameter)parameters.get(1)).markAsTime();
        ((TextFieldParameter)parameters.get(2)).markAsTime();

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();
        try {
            AddElement.startModifying(item.getId_number(),this,currentWindow, parameters, DatabaseConnection::modifyAffiliates,"Modify affiliate");
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentWindow.hide();
    }

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (AffiliatesTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getId_number(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
    }

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadAffiliates();
        data = FXCollections.observableArrayList(DatabaseConnection.getAffiliatesTableArrayList());
    }

    @Override
    public void reload(){
        try {
            loadToArray();
            id_number.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, Integer>("id_number"));
            address.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, String>("address"));
            opening_hours_from.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, String>("opening_hours_from"));
            opening_hours_to.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, String>("opening_hours_to"));
            number_of_employees.setCellValueFactory(new PropertyValueFactory<AffiliatesTable, Integer>("number_of_employees"));
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
