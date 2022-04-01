package com.charles.xfz.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@SQLDelete(sql = "update user set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
public class User {

    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator" )
    @Column(length = 200)
    private String id;

    @Column(length = 11,unique = true)
    private String telephone;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    private String username;

    private Boolean active = true;

    private Boolean staff = false;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date joinTime;

    private Boolean deleted = false;
}
