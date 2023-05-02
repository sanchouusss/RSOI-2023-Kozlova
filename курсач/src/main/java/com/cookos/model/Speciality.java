package com.cookos.model;

import java.io.Serializable;
import java.util.*;

import com.cookos.net.ModelType;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "speciality")
public class Speciality implements Serializable, Model, Identifiable {

    @ManyToMany(targetEntity = Subject.class, cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
        name = "speciality_subjects", 
        inverseJoinColumns = { @JoinColumn(name = "subjects_id", referencedColumnName = "id") }, 
        joinColumns = { @JoinColumn(name = "speciality_id", referencedColumnName = "id") }
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "speciality")
    @Builder.Default
    @ToString.Exclude
    private Set<Student> students = new HashSet<>();

    @Id
    @Column(name = "id")
    private int id;    

    @Column(name = "name")
    private String name;

    @Column(name = "mult5")
    private float mult5;

    @Column(name = "mult6")
    private float mult6;

    @Column(name = "mult7")
    private float mult7;

    @Column(name = "mult8")
    private float mult8;

    @Column(name = "mult9")
    private float mult9;

    @Override
    public ModelType getModelType() {
        return ModelType.Speciality;
    }

}
