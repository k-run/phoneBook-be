package com.example.phonebook.Response;

import com.example.phonebook.Model.PhoneBook;

public class SuccessResponse {
    private String status;
    private String message;
    private PhoneBook responseData;

    public SuccessResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public SuccessResponse(String status, String message, PhoneBook responseData) {
        this.status = status;
        this.message = message;
        this.responseData = responseData;
    }
}
