package br.edu.ioxua.rarch.web;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.operation.Operations;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.di.Injecthor;
import br.edu.ioxua.rarch.web.command.OperationCommand;
import br.edu.ioxua.rarch.web.helper.NoOpViewHelper;
import br.edu.ioxua.rarch.web.helper.ViewHelper;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@WebServlet({"/api/*"})
public class Servlet extends HttpServlet {

    @Inject
    private Map<String, OperationCommand> commands;

    @Inject
    private Map<String, ViewHelper> viewHelpers;

    private OperationCommand customCommand;

    @Override
    public void init() {
        Injecthor.autowire(this, "br.edu.ioxua.rarch");
        this.customCommand = this.commands.get("CUSTOM");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Operation operation = Operations.of(req.getParameter("operation"));
        String url = req.getRequestURI().replace("/api", "");

        ViewHelper helper = viewHelpers.getOrDefault(url, NoOpViewHelper.INSTANCE);

        Entity entity = helper.createInstance(req, resp);

        OperationCommand command = commands.getOrDefault(operation.getName(), this.customCommand);
        OperationResult result = command.execute(operation, entity);

        helper.setView(operation, result, req, resp);
    }
}
