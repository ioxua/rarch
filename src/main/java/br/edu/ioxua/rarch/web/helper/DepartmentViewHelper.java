package br.edu.ioxua.rarch.web.helper;

import br.edu.ioxua.rarch.core.department.Department;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;
import br.edu.ioxua.rarch.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Injectable
@Named({"/departments"})
public class DepartmentViewHelper implements ViewHelper<Department> {
    @Override
    public Department createInstance(HttpServletRequest request, HttpServletResponse response, String paramPrefix) {
        Department department = new Department();

        department.setId(Util.safeLongFromString(request.getParameter(paramPrefix + "id")));
        department.setName(request.getParameter(paramPrefix + "name"));

        return department;
    }
}