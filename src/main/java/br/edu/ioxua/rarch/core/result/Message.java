package br.edu.ioxua.rarch.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {
    private MessageLevel level;
    private String content;

    public static Message warning(String message) {
        return new Message(MessageLevel.WARNING, message);
    }

    public static Message error(String message) {
        return new Message(MessageLevel.ERROR, message);
    }

    public static Message success(String message) {
        return new Message(MessageLevel.SUCCESS, message);
    }
}
