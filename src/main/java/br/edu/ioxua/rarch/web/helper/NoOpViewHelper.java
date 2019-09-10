package br.edu.ioxua.rarch.web.helper;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.result.OperationResult;
import lombok.extern.java.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;

@Log
public class NoOpViewHelper implements ViewHelper {

    public static final ViewHelper INSTANCE = new NoOpViewHelper();

    private NoOpViewHelper() {}

    @Override
    public Entity createInstance(HttpServletRequest request, HttpServletResponse response, String paramPrefix) throws NumberFormatException {
        log.log(Level.INFO, "NoOp ViewHelper#createInstance called ");
        return null;
    }

    @Override
    public void setView(Operation operation, OperationResult result, HttpServletRequest request, HttpServletResponse response) {
        log.log(Level.INFO, "NoOp ViewHelper#setView called with operation " + operation.getName());
    }
}
