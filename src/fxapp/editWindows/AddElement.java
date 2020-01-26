package fxapp.editWindows;

import fxapp.Controller;
import fxapp.containers.Parameter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddElement {
    public static void startAdding(Controller parentController, Stage currentStage, ArrayList<Parameter> parameters, MethodPasser methodPasser, String title) throws IOException {
        AddElement controller = createStage(currentStage,title);
        controller.setEditFields(parameters);
        controller.getStage().show();
        controller.parameters = parameters;
        controller.methodPasser = methodPasser;
        controller.parentController = parentController;
    }

    public static void startModifying(int id, Controller parentController, Stage currentStage, ArrayList<Parameter> parameters ,MethodPasser methodPasser, String title) throws IOException {
        AddElement controller = createStage(currentStage,title);
        controller.setEditFields(parameters);
        controller.getStage().show();
        controller.parameters = parameters;
        controller.methodPasser = methodPasser;
        controller.parentController = parentController;
        controller.recordsID = id;
        controller.isCreating = false;
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

    private ArrayList<Parameter> parameters;

    private Boolean isCreating = true;

    private Integer recordsID;

    private MethodPasser methodPasser;

    private Controller parentController;

    @FXML
    private Label errorLabel;

    private ErrorDisplay errorDisplay;

    private Stage parentWindow;

    public Stage getStage(){
        return (Stage)editFields.getScene().getWindow();
    }

    private String[] getValuesToModify(){
        String[] values = new String[parameters.size()+1];
        values[0] = Integer.toString(recordsID);
        for(int i=1; i<values.length; i++){
            Parameter.Type type = parameters.get(i-1).getType();
            if(type == Parameter.Type.textField){
                values[i] = ((TextField)parameters.get(i-1).getValueField()).getText();
            }else if(type == Parameter.Type.textFieldWithChoice){
                if(parameters.get(i-1).getChosenID() == null){
                    values[i] = "";
                }else {
                    values[i] = Integer.toString(parameters.get(i-1).getChosenID());
                }
            }else if(type == Parameter.Type.date){
                values[i] = ((DatePicker)parameters.get(i-1).getValueField()).getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        return values;
    }

    private String[] getValuesToAdd(){
        String[] values = new String[parameters.size()];
        for(int i=0; i<values.length; i++){
            Parameter.Type type = parameters.get(i).getType();
            if(type == Parameter.Type.textField){
                values[i] = ((TextField)parameters.get(i).getValueField()).getText();
            }else if(type == Parameter.Type.textFieldWithChoice){
                if(parameters.get(i).getChosenID() == null){
                    values[i] = "";
                }else {
                    values[i] = Integer.toString(parameters.get(i).getChosenID());
                }
            }else if(type == Parameter.Type.date){
                values[i] = ((DatePicker)parameters.get(i).getValueField()).getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        return values;
    }

    @FXML
    private GridPane editFields;

    @FXML
    void add(ActionEvent event) {
        String errorMessage;
        if(isCreating){
            errorMessage = methodPasser.exec(getValuesToAdd());
        }else {
            errorMessage = methodPasser.exec(getValuesToModify());
        }
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

    public void setEditFields(ArrayList <Parameter> parameters){
        editFields.getChildren().removeAll();
        for(int i=0; i<parameters.size(); i++){
            editFields.add(parameters.get(i).getName(),0,i);
            editFields.add(parameters.get(i).getValueField(),1,i);
            if(parameters.get(i).getType() == Parameter.Type.textFieldWithChoice){
                ((AnchorPane)editFields.getParent()).getChildren().add(parameters.get(i).getChoiceList());
            }
        }
        editFields.setPrefHeight(parameters.size()*30);
    }

    @FXML
    void initialize() {
        assert editFields != null : "fx:id=\"editFields\" was not injected: check your FXML file 'addElement.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'addElement.fxml'.";
        errorDisplay = new ErrorDisplay(errorLabel);
    }
}
