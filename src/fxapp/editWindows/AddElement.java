package fxapp.editWindows;

import fxapp.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;
import sql.DatabaseConnection;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddElement {
    public static void startAdding(Controller parentController, Stage currentStage, ArrayList<String> elements, ArrayList<Integer> dateElements, MethodPasser methodPasser, String title) throws IOException {
        AddElement controller = createStage(currentStage,title);
        controller.setEditFields(elements,dateElements);
        controller.getStage().show();
        controller.methodPasser = methodPasser;
        controller.dateFields = dateElements;
        controller.parentController = parentController;
    }

    public static void startModifying(int id, Controller parentController, Stage currentStage, ArrayList<String> elements, ArrayList<Integer> dateElements, ArrayList<String> values, MethodPasser methodPasser, String title) throws IOException {
        AddElement controller = createStage(currentStage,title);
        controller.setEditFields(elements,dateElements,values);
        controller.getStage().show();
        controller.methodPasser = methodPasser;
        controller.dateFields = dateElements;
        controller.parentController = parentController;
        controller.recordsID = id;
    }

    public static AddElement createStage(Stage parentWindow,String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddElement.class.getResource("addElement.fxml"));
        Parent parent = fxmlLoader.load();
        AddElement controller = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setOnCloseRequest(e -> controller.returnToParentWindow(null));
        stage.setTitle(title);
        stage.setScene(new Scene(parent, 500, 500));
        controller.setParentWindow(parentWindow);
        return controller;
    }

    private Integer recordsID;

    private MethodPasser methodPasser;

    private Controller parentController;

    @FXML
    private Label errorLabel;

    private ErrorDisplay errorDisplay;

    private Stage parentWindow;

    private ArrayList<Integer> dateFields;

    public Stage getStage(){
        return (Stage)editFields.getScene().getWindow();
    }

    private String[] getValues(){
        ObservableList<Node> children = editFields.getChildren();
        int size = editFields.getChildren().size()/2;
        int j = 0;
        if(recordsID != null){
            size++;
            j = 1;
        }
        String[] values = new String[size];
        if(recordsID != null){
            values[0] = Integer.toString(recordsID);
        }
        for(int i=0; i<values.length-j; i++){
            if(dateFields.contains(i)){
                values[i+j] = String.valueOf(((DatePicker)children.get(i*2 + 1)).getValue());
                if(values[i+j] == null) values[i+j] = "";
            }else{
                values[i+j] = ((TextField)children.get(i*2 + 1)).getText();
                if(values[i+j] == null) values[i+j] = "";
            }
        }
        return values;
    }

    @FXML
    private GridPane editFields;

    @FXML
    void add(ActionEvent event) {
        String errorMessage = methodPasser.exec(getValues());
        if(errorMessage!=null){
            errorDisplay.setString(errorMessage);
            errorDisplay.displayErrorMessage();
        }else{
            parentController.reload();
            returnToParentWindow(event);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        returnToParentWindow(event);

    }

    public void returnToParentWindow(ActionEvent event){
        Window currentWindow = editFields.getScene().getWindow();
        // Hide this current window (if this is what you want)
        parentWindow.show();
        currentWindow.hide();
    }

    public void setParentWindow(Stage parentWindow){
        this.parentWindow = parentWindow;
    }

    public void setEditFields(ArrayList <String> fields, ArrayList<Integer> dateFields){
        editFields.getChildren().removeAll();
        for(int i=0; i<fields.size(); i++){
            editFields.add(new Label(fields.get(i)),0,i);
            if(dateFields.contains(i)){
                editFields.add(new DatePicker(),1,i);
            }else {
                editFields.add( new TextField(), 1, i);
            }
        }
        editFields.setPrefHeight(fields.size()*30);
    }

    public void setEditFields(ArrayList <String> fields, ArrayList<Integer> dateFields, ArrayList<String> values){
        editFields.getChildren().removeAll();
        for(int i=0; i<fields.size(); i++){
            editFields.add(new Label(fields.get(i)),0,i);
            if(dateFields.contains(i)){
                if(values.get(i).equals("")){
                    editFields.add(new DatePicker(),1,i);
                }else{
                    String[] date = values.get(i).split("-");
                    editFields.add(new DatePicker(LocalDate.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]))),1,i);
                }
            }else {
                editFields.add( new TextField(values.get(i)), 1, i);
            }
        }
        editFields.setPrefHeight(fields.size()*30);
    }

    @FXML
    void initialize() {
        assert editFields != null : "fx:id=\"editFields\" was not injected: check your FXML file 'addElement.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'addElement.fxml'.";
        errorDisplay = new ErrorDisplay(errorLabel);
    }

}
