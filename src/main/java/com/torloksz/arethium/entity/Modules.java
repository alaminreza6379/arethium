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

    private Integer time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users users;

    public Modules(Users users,Integer order,String title,String description,boolean completed,Integer time){
        this.moduleOrder=order;
        this.title = title;
        this.description=description;
        this.users=users;
        this.completed=completed;
        this.time=time;
    }


}
