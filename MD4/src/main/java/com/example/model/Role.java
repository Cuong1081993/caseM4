package com.example.model;

import com.example.model.enums.ERoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Long id;

private String code;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERoles name;

    @OneToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    private List<User> users;
}
