package com.torloksz.arethium.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "modules")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Modules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer moduleOrder;

    private String title;

    @Column(length = 5000)
    private String description;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    public Modules(Users users,Integer order,String title,String description,boolean completed){
        this.moduleOrder=order;
        this.title = title;
        this.description=description;
        this.users=users;
        this.completed=completed;
    }
}
