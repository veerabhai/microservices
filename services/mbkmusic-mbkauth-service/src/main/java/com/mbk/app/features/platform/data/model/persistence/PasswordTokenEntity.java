package com.mbk.app.features.platform.data.model.persistence;

import com.ibm.db2.cmx.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PasswordToken")
@Entity
public class PasswordTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int  id;

    @Column(name = "Uid", nullable = false)
    private int Uid;

    @Column(name = "password_token",length = 6)
    private Long passwordToken;


}