package com.example.waes.test.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comparison")
@Data
public class ComparisonEntity {
    @Id
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "left", length = 10000, nullable = true)
    String left;

    @Column(name = "right", length = 10000, nullable = true)
    String right;
}
