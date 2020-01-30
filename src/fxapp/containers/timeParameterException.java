package fxapp.containers;

public class timeParameterException extends wrongParameterException {
    public timeParameterException(String paramName) {
        super("Parameter \"" + paramName + "\" should be a time");
    }}
