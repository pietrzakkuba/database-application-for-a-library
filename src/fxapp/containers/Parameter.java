package fxapp.containers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public abstract class Parameter {
    protected Label name;
    protected Type type;
    protected Boolean required;
    protected Node valueField;

    public enum Type{
        label,
        textField,
        date,
        textFieldWithChoice
    }

    public Boolean isRequired() {
        return required;
    }

    public Label getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Node getValueField() {
        return valueField;
    }

    public abstract String valueToString();

}
