package com.spring.security.example.ui.model.response;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

    private String errorMessages;

    ErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessages() {
        return errorMessages;
    }

    /**
     * @param errorMessage to set
     */

    public void setErrorMessages(String errorMessage) {
        this.errorMessages = errorMessages;
    }
}
