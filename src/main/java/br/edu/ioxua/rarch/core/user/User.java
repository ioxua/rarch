package br.edu.ioxua.rarch.core.user;

import br.edu.ioxua.rarch.core.entity.EntityImpl;
import br.edu.ioxua.rarch.core.user.role.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends EntityImpl {
    private String login;
    private String password;

    @Delegate(types = RoleCollection.class)
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    private interface RoleCollection {
        boolean add(Role e);
        boolean addAll(Collection<? extends Role> es);
    }
}
