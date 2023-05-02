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
@Table(name = "subjects")
public class Subject implements Serializable, Model, Identifiable {

    @ManyToMany(targetEntity = Speciality.class, mappedBy = "subjects", fetch = FetchType.LAZY)
    //@JoinTable(
    //    name = "speciality_subjects", 
    //    joinColumns = { @JoinColumn(name = "subjects_id", referencedColumnName = "id") }, 
    //    inverseJoinColumns = { @JoinColumn(name = "speciality_id", referencedColumnName = "id") }
    //)
    @Builder.Default
    @ToString.Exclude
    private Set<Speciality> specialities = new HashSet<>();

    @OneToMany(mappedBy = "subject")
    @Builder.Default
    @ToString.Exclude
    private Set<Performance> performance = new HashSet<>();

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "hours")
    private int hours;

    @Override
    public ModelType getModelType() {
        return ModelType.Subject;
    }
    
}
