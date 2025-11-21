package ru.indeece.urlshortenerprod.exceptions;

public class MissingUrlException extends RuntimeException {

    public MissingUrlException(String message) {
        super(message);
    }

}