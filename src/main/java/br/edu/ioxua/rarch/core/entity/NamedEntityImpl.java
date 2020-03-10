package br.edu.ioxua.rarch.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class NamedEntityImpl extends DomainEntityImpl implements NamedEntity {
    @Column(name = "name")
    private String name;
}
