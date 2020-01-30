package fxapp.containers;

public class integerParameterException extends wrongParameterException {
    public integerParameterException(String paramName) {
        super("Parameter \"" + paramName + "\" should be an integer");
    }}
