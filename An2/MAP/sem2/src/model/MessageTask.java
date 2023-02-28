package model;

import utils.Constants;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {
    private String message;
    private String from;
    private String to;
    private LocalDateTime date;

    public MessageTask(String _taskID, String _description, String message, String from, String to, LocalDateTime date) {
        super(_taskID, _description);
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @Override
    public void execute() {
        System.out.println(toString());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return super.toString() + " " + getMessage() +
                "\nFrom: " + getFrom() + "\nData: " +
                getDate().format(Constants.DATE_TIME_FORMATTER);
    }
}
