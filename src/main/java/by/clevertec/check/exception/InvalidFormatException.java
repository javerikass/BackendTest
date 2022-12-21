package by.clevertec.check.exception;

public class InvalidFormatException extends Exception{
    @Override
    public String getMessage() {
        return "Invalid format";
    }
}
