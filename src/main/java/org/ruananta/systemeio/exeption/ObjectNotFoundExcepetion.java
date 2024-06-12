package org.ruananta.systemeio.exeption;

public class ObjectNotFoundExcepetion extends RuntimeException {

    public ObjectNotFoundExcepetion(String msg){
        super(msg);
    }

    public ObjectNotFoundExcepetion(String msg, Throwable cause){
        super(msg, cause);
    }
}
