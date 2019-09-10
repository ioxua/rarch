package br.edu.ioxua.rarch.web.helper;

import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;
import br.edu.ioxua.rarch.util.Util;
import lombok.extern.java.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
@Injectable
@Named({"/employees"})
public class EmployeeHelper implements ViewHelper<Employee> {
    @Override
    public Employee createInstance(HttpServletRequest request, HttpServletResponse response, String paramPrefix) {
        Employee employee = new Employee();

        employee.setId(Util.safeLongFromString(request.getParameter(paramPrefix + "id")));
        employee.setName(request.getParameter(paramPrefix + "name"));
        employee.setNationalId(request.getParameter(paramPrefix + "nationalId"));

        employee.setDepartment(new DepartmentViewHelper().createInstance(request, response, paramPrefix + "department."));
        employee.setPosition(new PositionViewHelper().createInstance(request, response, paramPrefix + "position."));
        employee.setRegional(new RegionalViewHelper().createInstance(request, response, paramPrefix + "regional."));
        employee.setUser(new UserViewHelper().createInstance(request, response, paramPrefix + "user."));

        if (request.getParameterMap().containsKey(paramPrefix + "owner.id")) {
            employee.setOwner(createInstance(request, response, paramPrefix + "owner."));
        }

        return employee;
    }

}
