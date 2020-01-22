package fxapp.editWindows;

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
import java.util.ArrayList;

public class AddElement {
    public static void startAdding(Stage currentStage, ArrayList<String> elements, ArrayList<Integer> dateElements, MethodPasser methodPasser, String title) throws IOException {
        AddElement controller = createStage(currentStage,title);
        controller.setEditFields(elements,dateElements);
        controller.getStage().show();
        controller.methodPasser = methodPasser;
        controller.dateFields = dateElements;
    }

    public void startModifying(Stage currentStage, ArrayList<String> elements, ArrayList<Integer> dateElements, ArrayList<String> values){

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

    private MethodPasser methodPasser;

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
        String[] values = new String[editFields.getRowCount()];
        for(int i=0; i<values.length; i++){
            if(dateFields.contains(i)){
                values[i] = String.valueOf(((DatePicker)children.get(i*2 + 1)).getValue());
            }else{
                values[i] = ((TextField)children.get(i*2 + 1)).getText();
            }
        }
        return values;
    }

    @FXML
    private GridPane editFields;

    @FXML
    void add(ActionEvent event) {
        methodPasser.exec(getValues());
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

    @FXML
    void initialize() {
        assert editFields != null : "fx:id=\"editFields\" was not injected: check your FXML file 'addElement.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'addElement.fxml'.";
        errorDisplay = new ErrorDisplay(errorLabel);
    }

}
