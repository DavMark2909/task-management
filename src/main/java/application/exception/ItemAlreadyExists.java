package application.exception;

public class ItemAlreadyExists extends MyException{

    public ItemAlreadyExists(String message) {
        super("Item " + message + " already exists");
    }
}
