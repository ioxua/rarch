package br.edu.ioxua.rarch.core.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
@Getter
@AllArgsConstructor
public enum Operations implements Operation {
    SAVE("SAVE"), UPDATE("UPDATE"), DELETE("DELETE"), FIND("FIND"), VIEW("VIEW"), NOOP("NOOP");

    private String name;

    public static Operation of(String name) {
        if (null == name || "".equals(name)) {
            log.log(Level.INFO, "NoOp Operation returned for null name");
            return FIND;
        }

        for (Operation op : Operations.values()) {
            if (name.equalsIgnoreCase(op.getName())) {
                return op;
            }
        }

        return new CustomOperation(name);
    }
}
