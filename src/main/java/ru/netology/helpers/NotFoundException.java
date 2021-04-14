package ru.netology.helpers;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}