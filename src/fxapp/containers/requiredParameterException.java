package fxapp.containers;

public class requiredParameterException extends wrongParameterException {
    public requiredParameterException(String paramName) {
        super("Parameter \"" + paramName + "\" is required");
    }
}
