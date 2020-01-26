package fxapp.containers;

import fxapp.editWindows.MethodPasser;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Parameter {
    private Label name;
    private Type type;
    private Boolean required;
    private Node valueField;
    private ListView<Button> choiceList;
    private ArrayList<Integer> choiceIDs;
    private int choiceSize = 20;
    private Integer chosenID;

    public enum Type{
        label,
        textField,
        date,
        textFieldWithChoice
    }

    public Label getName() {
        return name;
    }

    public Integer getChosenID() {
        return chosenID;
    }

    public ListView<Button> getChoiceList() {
        return choiceList;
    }

    public Type getType() {
        return type;
    }

    public Node getValueField() {
        return valueField;
    }

    public Parameter(String name, Type type, Boolean required, String value){
        this.name = new Label(name);
        this.type = type;
        this.required = required;
        if(type == Type.label){
            this.valueField = new Label(value);
        }else if(type == Type.date){
            this.valueField = new DatePicker(StringToLocalDate.convert(value));
        }else if(type == Type.textField){
            this.valueField = new TextField(value);
        }else if(type == Type.textFieldWithChoice){
            this.valueField = new TextField(value);

            this.choiceList = new ListView<>();
            this.choiceList.setVisible(false);
            this.choiceList.setPrefWidth(190);

            this.choiceIDs = new ArrayList<>();
            valueField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                setOnFocusChange(newValue);
            });
        }
    }

    public void setChoicesGetMethod(getChoicesMethod method){
        chosenID = null;
        if(type == Type.textFieldWithChoice){
            valueField.setOnKeyReleased(t -> {
                ArrayList<Button> list = new ArrayList<>();
                choiceIDs.clear();
                ArrayList<Choice> result = method.exec(((TextField)valueField).getText());
                for(Choice choice: result){
                    Button newChoice = new Button(choice.getValue());
                    newChoice.setOnAction(this::setOnChoiceChose);
                    newChoice.setPrefWidth(choiceList.getPrefWidth());
                    newChoice.setPrefHeight(choiceSize);
                    list.add(newChoice);
                    choiceIDs.add(choice.getId());
                }
                choiceList.setItems(FXCollections.observableArrayList(list));
                if(choiceList.getItems().size() == 0){
                    choiceList.setVisible(false);
                }else{
                    choiceList.setVisible(true);
                    if(choiceList.getItems().size() < 4)
                        choiceList.setPrefHeight(choiceSize * choiceList.getItems().size() + 15);
                    else
                        choiceList.setPrefHeight(choiceSize * 4 + 15);
                }
            });
        }
    }

    private void setOnFocusChange(boolean value) {
        if (value) {
            choiceList.setFixedCellSize(choiceSize);
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

    private void setOnChoiceChose(ActionEvent event){
        Button clicked = (Button) event.getSource();
        for(int i=0; i<choiceList.getItems().size(); i++){
            if(clicked == choiceList.getItems().get(i)){
                chosenID = choiceIDs.get(i);
                ((TextField)valueField).setText(choiceList.getItems().get(i).getText());

                break;
            }
        }
    }
}
