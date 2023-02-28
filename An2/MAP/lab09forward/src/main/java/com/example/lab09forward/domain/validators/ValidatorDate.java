package com.example.lab09forward.domain.validators;

import com.example.lab09forward.domain.exceptions.ValidationException;

public class ValidatorDate implements Validator {
    //    singleton
    private static ValidatorDate instance = null;

    private ValidatorDate() {
    }

    public static ValidatorDate getInstance() {
        if (instance == null) {
            instance = new ValidatorDate();
        }
        return instance;
    }

    @Override
    public void validate(Object entity) throws ValidationException {
        String date = (String) entity;
        StringBuilder problem = new StringBuilder("");
        if (!verifyDateFormat(date)) {
            problem.append("Date is not in format yyyy-mm-dd\n");
        }
        String[] dateParts = date.split("-");
        if (!verifyDateParts(dateParts)) {
            problem.append("Date must be in format yyyy-mm-dd, meaning only 3 separate parts\n");
        }
        int day = Integer.parseInt(dateParts[2]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[0]);
        verifyComponents(day, month, year, problem);
        if (!problem.toString().equals("")) {
            throw new ValidationException(problem.toString());
        }
    }

    private boolean verifyDateFormat(String date) {
        if (date.length() != 10) {
            return false;
        }
        return true;
    }
    private boolean verifyDateParts(String[] dateParts) {
        if (dateParts.length != 3) {
            return false;
        }
        return true;
    }
    private void verifyComponents(int day, int month, int year, StringBuilder problem){
        if (day < 1 || day > 31) {
            problem.append("Day must be between 1 and 31");
        }
        if (month < 1 || month > 12) {
            problem.append("Month must be between 1 and 12");
        }
        if (year < 1900 || year > 2020) {
            problem.append("Year must be between 1900 and 2020");
        }
    }
}
