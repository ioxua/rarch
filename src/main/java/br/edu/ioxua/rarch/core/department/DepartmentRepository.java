package br.edu.ioxua.rarch.core.department;

import br.edu.ioxua.rarch.core.department.dto.EmployeeQuantityByDepartmentDTO;
import br.edu.ioxua.rarch.core.repository.RepositoryImpl;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Injectable
@Named(clazz = Department.class)
public class DepartmentRepository extends RepositoryImpl<Department> {
    public DepartmentRepository() {
        super(Department.class);
    }

    public List<EmployeeQuantityByDepartmentDTO> findEnabledGroupByDepartment() {
        String query = "SELECT NEW br.edu.ioxua.rarch.core.department.dto.EmployeeQuantityByDepartmentDTO(d) FROM Department d";
        return this.runQuery(query, new HashMap<>())
                .parallelStream()
                .map(EmployeeQuantityByDepartmentDTO.class::cast)
                .collect(Collectors.toList());
    }
}
