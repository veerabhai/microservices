package com.mbk.app.features.platform.data.model.persistence;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERS_ADMIN_APPROVAL")
@Entity
public class Users_Admin_ApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
   private  int id;
    private int Uid;
    @Column(name = "Role_Id", length = 36)
    private String RoleId;
    @Column(name = "username", length = 36)
    private String Username;
    @Column(name = "Approval_Status",length= 15)
    private String  approval;
    @Column(name = "Reason",length = 512)
    private String reason;
    @Column(name = "Created_Date")
    private Date created_date;
    @Column(name="Approved_Date")
    private Date Approved_Date;
    @Column(name = "Approved_By",length = 25)
    private String approved_by;


}