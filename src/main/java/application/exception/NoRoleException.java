package application.exception;

public class NoRoleException extends MyException{
    public NoRoleException(String role) {
        super("Role - " + role + " doesn't exist");
    }


}
