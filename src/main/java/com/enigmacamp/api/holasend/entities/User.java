package com.enigmacamp.api.holasend.entities;


import com.enigmacamp.api.holasend.enums.RoleEnum;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table
@Entity(name = "user")
@Data
public class User extends AbstractEntity<String> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String token;

    @Enumerated
    @Column
    private RoleEnum role;

    @OneToOne
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
