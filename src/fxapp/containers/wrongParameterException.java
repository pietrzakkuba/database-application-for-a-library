package fxapp.containers;

public class wrongParameterException extends Exception {
    public wrongParameterException(String paramName) {
        super(paramName);
    }
}
