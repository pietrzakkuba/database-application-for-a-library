package fxapp.containers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class LabelParameter extends Parameter {
    public LabelParameter(String name, String value) {
        this.name = new Label(name);
        this.type = Type.label;
        this.required = false;
        this.valueField = new Label(value);
    }

    @Override
    public String valueToString() {
        return ((Label)valueField).getText();
    }
}
