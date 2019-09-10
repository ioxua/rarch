package br.edu.ioxua.rarch.core.util;

import br.edu.ioxua.rarch.core.result.Message;
import br.edu.ioxua.rarch.core.result.MessageLevel;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class Util {

    public boolean hasError(Set<Message> messages) {
        return hasMessageOfLevel(messages, MessageLevel.ERROR);
    }

    public boolean hasWarning(Set<Message> messages) {
        return hasMessageOfLevel(messages, MessageLevel.WARNING);
    }

    public boolean hasSuccess(Set<Message> messages) {
        return hasMessageOfLevel(messages, MessageLevel.SUCCESS);
    }

    private boolean hasMessageOfLevel(Set<Message> messages, MessageLevel level) {
        return messages.parallelStream()
                .anyMatch(m -> m.getLevel() == level);
    }

}
