package br.edu.ioxua.rarch.core.employee;

import br.edu.ioxua.rarch.core.repository.RepositoryImpl;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

import java.util.Collection;

@Injectable
@Named(clazz = Employee.class)
public class EmployeeRepository extends RepositoryImpl<Employee> {
    public EmployeeRepository() {
        super(Employee.class);
    }
}
