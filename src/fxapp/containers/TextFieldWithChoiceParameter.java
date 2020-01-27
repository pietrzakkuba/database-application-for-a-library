package fxapp.containers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class TextFieldWithChoiceParameter extends Parameter{
    private ListView<Button> choiceList;
    private ArrayList<Integer> choiceIDs;
    private int choiceSize = 20;
    private Integer chosenID;

    public TextFieldWithChoiceParameter(String name, Boolean required, getChoicesMethod method){
        this.name = new Label(name);
        this.type = Type.textFieldWithChoice;
        this.required = required;
        this.valueField = new TextField("");
        this.choiceList = new ListView<>();
        this.choiceList.setVisible(false);
        this.choiceList.setPrefWidth(190);
        this.chosenID = null;
        this.choiceIDs = new ArrayList<>();
        valueField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            setOnFocusChange(newValue);
        });
        valueField.setOnKeyTyped(e -> onKeyTyped(method));
    }

    public TextFieldWithChoiceParameter(String name, Boolean required, getChoicesMethod method, String value, Integer linkedID){
        this.name = new Label(name);
        this.type = Type.textFieldWithChoice;
        this.required = required;
        this.valueField = new TextField(value);
        this.choiceList = new ListView<>();
        this.choiceList.setVisible(false);
        this.choiceList.setPrefWidth(190);
        this.chosenID = linkedID;
        this.choiceIDs = new ArrayList<>();
        valueField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            setOnFocusChange(newValue);
        });
        valueField.setOnKeyTyped(e -> onKeyTyped(method));
    }

    private void onKeyTyped(getChoicesMethod method){
        chosenID = null;
        ArrayList<Button> list = new ArrayList<>();
        choiceIDs.clear();
        ArrayList<Choice> result = method.exec(((TextField)valueField).getText());
        for(Choice choice: result){
            Button newChoice = new Button(choice.getValue());
            newChoice.setOnAction(this::setOnChoiceChoose);
            newChoice.setPrefWidth(choiceList.getPrefWidth());
            newChoice.setPrefHeight(choiceSize);
            newChoice.setLayoutX(choiceList.getLayoutX());
            newChoice.setFocusTraversable(false);
            list.add(newChoice);
            choiceIDs.add(choice.getId());
        }
        choiceList.setItems(FXCollections.observableArrayList(list));
        if(choiceList.getItems().size() == 0){
            choiceList.setVisible(false);
        }else{
            choiceList.setVisible(true);
            if(choiceList.getItems().size() < 4)
                choiceList.setPrefHeight(choiceSize * choiceList.getItems().size() + 25);
            else
                choiceList.setPrefHeight(choiceSize * 4 + 15);
        }
    }

    private void setOnFocusChange(boolean value) {
        if (value) {
            choiceList.setLayoutX(valueField.getLayoutX()+60);
            choiceList.setLayoutY(valueField.getLayoutY()+62);
            System.out.println("Focus Gained");
            if(choiceList.getItems().size()>0)
                choiceList.setVisible(true);
        }
        else {
            System.out.println("Focus Lost");
            choiceList.setVisible(false);
        }
    }

    private void setOnChoiceChoose(ActionEvent event){
        Button clicked = (Button) event.getSource();
        for(int i=0; i<choiceList.getItems().size(); i++){
            if(clicked == choiceList.getItems().get(i)){
                chosenID = choiceIDs.get(i);
                ((TextField)valueField).setText(choiceList.getItems().get(i).getText());
                valueField.getParent().requestFocus();
                choiceList.setVisible(false);
                break;
            }
        }
    }

    @Override
    public String valueToString() {
        if(chosenID == null)
            return "";
        return Integer.toString(chosenID);
    }

    public Integer getChosenID() {
        return chosenID;
    }

    public ListView<Button> getChoiceList() {
        return choiceList;
    }
}
