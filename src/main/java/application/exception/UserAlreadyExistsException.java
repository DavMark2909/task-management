package application.exception;

public class UserAlreadyExistsException extends MyException{
    public UserAlreadyExistsException() {
        super("User with such username already exists");
    }
}
