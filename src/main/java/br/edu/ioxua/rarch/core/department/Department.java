package br.edu.ioxua.rarch.core.department;

import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.entity.NamedEntityImpl;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Department extends NamedEntityImpl {
    @OneToMany(mappedBy = "department")
    private List<Employee> employed;
}
