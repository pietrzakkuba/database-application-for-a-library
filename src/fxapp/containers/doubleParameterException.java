package fxapp.containers;

public class doubleParameterException extends wrongParameterException {
    public doubleParameterException(String paramName) {
        super("Parameter \"" + paramName + "\" should be an real number");
    }
}
