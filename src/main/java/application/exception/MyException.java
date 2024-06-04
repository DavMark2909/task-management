package application.exception;

import org.springframework.http.HttpStatus;

public class MyException extends Exception{

    public HttpStatus getHttpCode(){
        return HttpStatus.BAD_REQUEST;
    }

    public MyException(String message) {
        super(message);
    }
}
