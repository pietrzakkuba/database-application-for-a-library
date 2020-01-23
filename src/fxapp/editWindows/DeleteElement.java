package fxapp.editWindows;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fxapp.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DeleteElement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane editFields;

    @FXML
    private Label errorLabel;

    private Integer recordsID;

    private MethodPasser methodPasser;

    private Controller parentController;

    private ErrorDisplay errorDisplay;

    private Stage parentWindow;

    public Stage getStage(){
        return (Stage)editFields.getScene().getWindow();
    }

    public static void startDeleting(int id, Controller parentController, Stage currentStage, ArrayList<String> elements, ArrayList<String> values, MethodPasser methodPasser, String title) throws IOException {
        DeleteElement controller = createStage(currentStage,title);
        controller.setFields(elements,values);
        controller.getStage().show();
        controller.methodPasser = methodPasser;
        controller.parentController = parentController;
        controller.recordsID = id;
    }

    private String[] getValues(){
        return new String[]{Integer.toString(recordsID)};
    }

    public void setParentWindow(Stage parentWindow){
        this.parentWindow = parentWindow;
    }

    private void setFields(ArrayList<String> elements, ArrayList<String> values){
        editFields.getChildren().removeAll();
        for(int i = 0; i<elements.size(); i++){
            editFields.add(new Label(elements.get(i)),0,i);
            editFields.add(new Label(values.get(i)),1,i);
        }
        editFields.setPrefHeight(elements.size()*30);
    }

    public static DeleteElement createStage(Stage parentWindow,String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddElement.class.getResource("deleteElement.fxml"));
        Parent parent = fxmlLoader.load();
        DeleteElement controller = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setOnCloseRequest(e -> controller.returnToParentWindow(null));
        stage.setTitle(title);
        stage.setScene(new Scene(parent, 500, 500));
        controller.setParentWindow(parentWindow);
        return controller;
    }

    public void returnToParentWindow(ActionEvent event){
        Window currentWindow = editFields.getScene().getWindow();
        // Hide this current window (if this is what you want)
        parentWindow.show();
        currentWindow.hide();
    }

    @FXML
    void cancel(ActionEvent event) {
        returnToParentWindow(event);

    }

    @FXML
    void delete(ActionEvent event) {
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
    void initialize() {
        assert editFields != null : "fx:id=\"editFields\" was not injected: check your FXML file 'deleteElement.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'deleteElement.fxml'.";
        errorDisplay = new ErrorDisplay(errorLabel);
    }
}
