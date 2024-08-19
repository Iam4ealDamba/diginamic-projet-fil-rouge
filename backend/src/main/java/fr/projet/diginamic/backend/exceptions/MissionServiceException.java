package fr.projet.diginamic.backend.exceptions;

public class MissionServiceException extends RuntimeException {

    public MissionServiceException(String message) {
        super(message);
    }

    public MissionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

