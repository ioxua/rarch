package br.edu.ioxua.rarch.core.employee;

import br.edu.ioxua.rarch.core.department.Department;
import br.edu.ioxua.rarch.core.entity.NamedEntityImpl;
import br.edu.ioxua.rarch.core.position.Position;
import br.edu.ioxua.rarch.core.regional.Regional;
import br.edu.ioxua.rarch.core.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Employee extends NamedEntityImpl {
    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "register")
    private String register;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "regional_id", nullable = false)
    private Regional regional;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
