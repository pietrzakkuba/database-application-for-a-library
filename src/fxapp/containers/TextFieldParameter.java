package fxapp.containers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextFieldParameter extends Parameter {
    public TextFieldParameter(String name, Boolean required, String value) {
        this.name = new Label(name);
        this.type = Type.textField;
        this.required = required;
        if(value == null)
            this.valueField = new TextField("");
        else
            this.valueField = new TextField(value);
    }

    public TextFieldParameter(String name, Boolean required) {
        this.name = new Label(name);
        this.type = Type.textField;
        this.required = required;
        this.valueField = new TextField("");
    }

    @Override
    public String valueToString() {
        return ((TextField)valueField).getText();
    }
}
