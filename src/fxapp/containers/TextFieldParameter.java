package fxapp.containers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldParameter extends Parameter {
    public TextFieldParameter(String name, Boolean required, String value) {
        this.name = new Label(name);
        this.type = Type.textField;
        this.required = required;
        this.valueField = new TextField(value);
    }

    @Override
    public String valueToString() {
        return ((TextField)valueField).getText();
    }
}
