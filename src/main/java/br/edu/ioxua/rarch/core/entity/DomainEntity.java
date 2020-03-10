package br.edu.ioxua.rarch.core.entity;

import java.time.LocalDateTime;

public interface DomainEntity {
    Long getId();
    LocalDateTime getCreationDate();
    LocalDateTime getLastUpdate();
    Boolean getEnabled();
}
