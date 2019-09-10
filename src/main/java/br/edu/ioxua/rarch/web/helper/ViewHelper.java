package br.edu.ioxua.rarch.web.helper;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.result.OperationResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ViewHelper<E extends Entity> {
    E createInstance(HttpServletRequest request, HttpServletResponse response, String paramPrefix)
            throws NumberFormatException;

    default E createInstance(HttpServletRequest request, HttpServletResponse response)
            throws NumberFormatException {
        return createInstance(request, response, "");
    }

    default void setView(Operation operation, OperationResult result, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        response.getWriter().print(objectMapper.writeValueAsString(result));
    }
}
