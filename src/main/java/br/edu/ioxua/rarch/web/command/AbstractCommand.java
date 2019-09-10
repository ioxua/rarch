package br.edu.ioxua.rarch.web.command;

import br.edu.ioxua.rarch.core.Facade;
import br.edu.ioxua.rarch.core.FacadeImpl;

import javax.inject.Inject;

public abstract class AbstractCommand implements OperationCommand {
    @Inject
    protected Facade facade;
}
