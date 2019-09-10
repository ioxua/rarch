package br.edu.ioxua.rarch.core.result;

import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OperationResult<T> {
    private T result;

    @Singular
    private Set<Message> messages;

    @Getter(lazy = true)
    private final Set<Message> errors = errors();

    @Getter(lazy = true)
    private final Set<Message> warnings = warnings();

    @Getter(lazy = true)
    private final Set<Message> successes = successes();

    public boolean hasErrors() {
        return this.errors().size() > 0;
    }

    public boolean hasSuccesses() {
        return this.successes().size() > 0;
    }

    public boolean hasWarnings() {
        return this.warnings().size() > 0;
    }

    private Set<Message> errors() {
        return this.filterMessagesByLevel(MessageLevel.ERROR);
    }

    private Set<Message> warnings() {
        return this.filterMessagesByLevel(MessageLevel.WARNING);
    }

    private Set<Message> successes() {
        return this.filterMessagesByLevel(MessageLevel.SUCCESS);
    }

    private Set<Message> filterMessagesByLevel(MessageLevel level) {
        return this.messages.parallelStream()
                .filter(msg -> msg.getLevel() == level)
                .collect(Collectors.toSet());
    }

    public OperationResult<T> with(T t) {
        return new OperationResult<>(t, getMessages());
    }

    public static OperationResult of(Set<Message> msgs) {
        return new OperationResult<>(null, msgs);
    }

    public static <T> OperationResult<T> of(T t, Set<Message> msgs) {
        return new OperationResult<>(t, msgs);
    }

}
