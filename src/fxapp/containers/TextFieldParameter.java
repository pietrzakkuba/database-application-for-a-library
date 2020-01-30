package fxapp.containers;

import fxapp.editWindows.AddElement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldParameter extends Parameter {
    private Boolean isInteger = false;
    private Boolean isDouble = false;
    private Boolean isTime = false;

    public void markAsInteger(){
        isInteger = true;
        valueField.getStyleClass().add("integer-field");
    }

    public void markAsDouble(){
        isDouble = true;
        valueField.getStyleClass().add("double-field");
    }

    public void markAsTime(){
        isTime = true;
        //((TextField)valueField).getStylesheets().add(getClass().getResource("textFieldParameter.css").toExternalForm());
        valueField.getStyleClass().add("time-field");
        addTextLimiter((TextField)valueField,5);
    }

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

    public TextFieldParameter(String name, Boolean required, String value, Integer maxLenght) {
        this.name = new Label(name);
        this.type = Type.textField;
        this.required = required;
        if(value == null)
            this.valueField = new TextField("");
        else
            this.valueField = new TextField(value);
        addTextLimiter((TextField)valueField,maxLenght);
    }

    public TextFieldParameter(String name, Boolean required, Integer maxLenght) {
        this.name = new Label(name);
        this.type = Type.textField;
        this.required = required;
        this.valueField = new TextField("");
        addTextLimiter((TextField)valueField,maxLenght);
    }

    private void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    @Override
    public String valueToString() throws wrongParameterException{
        String str = ((TextField)valueField).getText();
        if(isDouble){
            try{
                Double.parseDouble(str.trim());
            } catch (NumberFormatException e) {
                throw new doubleParameterException(this.name.getText());
            }
        }
        if(isInteger){
            try{
                Integer.parseInt(str.trim());
            } catch (NumberFormatException e) {
                throw new integerParameterException(this.name.getText());
            }
        }
        if(isTime){
            try{
                if(!(str.matches("^[0-1]?[0-9]:[0-5][0-9]$")||str.matches("^2[0-3]:[0-5][0-9]$")||str.matches("([0-1]?[0-9]|2[0-3])$"))){
                    throw new timeParameterException(this.name.getText());
                }
            } catch (NumberFormatException e) {
                throw new integerParameterException(this.name.getText());
            }
        }
        return ((TextField)valueField).getText();
    }
}
