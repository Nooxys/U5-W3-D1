package CiroVitiello.U5W3D1.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("element with " + id + " not found");

    }

    public NotFoundException(String message) {
        super(message);
    }
}