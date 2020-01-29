package fxapp.containers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LabelParameter extends Parameter {
    public LabelParameter(String name, String value) {
        this.name = new Label(name);
        this.type = Type.label;
        this.required = false;
        this.valueField = new Label(value);
    }

    public LabelParameter(String name, Date value) {
        this.name = new Label(name);
        this.type = Type.label;
        this.required = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if(value == null)
            this.valueField = new Label("");
        else
            this.valueField = new Label(formatter.format(value));
    }

    @Override
    public String valueToString() {
        return ((Label)valueField).getText();
    }
}
