package fxapp.containers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateParameter extends Parameter {
    public DateParameter(String name, Boolean required, Date value) {
        this.name = new Label(name);
        this.type = Type.date;
        this.required = required;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(value == null)
            this.valueField = new DatePicker();
        else
            this.valueField = new DatePicker(StringToLocalDate.convert(formatter.format(value)));
    }

    @Override
    public String valueToString() {
        if(((DatePicker)valueField).getValue()==null){
            return "";
        }
        return ((DatePicker)valueField).getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
