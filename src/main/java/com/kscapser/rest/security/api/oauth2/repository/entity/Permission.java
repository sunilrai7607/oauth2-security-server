package com.kscapser.rest.security.api.oauth2.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSION")
@Data
public class Permission extends BaseEntity {

    private String name;
}
