package by.clevertec.check.exception;

public class ItemNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Item is not found";
    }
}
