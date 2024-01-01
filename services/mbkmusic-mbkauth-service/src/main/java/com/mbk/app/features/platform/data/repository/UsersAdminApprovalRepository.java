package com.mbk.app.features.platform.data.repository;

import com.mbk.app.features.platform.data.model.experience.admin.AdminApproval;
import com.mbk.app.features.platform.data.model.persistence.Users_Admin_ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface UsersAdminApprovalRepository extends JpaRepository<Users_Admin_ApprovalEntity,Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE mbk_auth_schema.users_admin_approval SET  approved_date=:approvedOn , approval_status=:approval WHERE Username =:userName",nativeQuery = true)
    void setContributorStatus(String userName, String approval, Date approvedOn);

    @Query(value = "select * from mbk_auth_schema.users_admin_approval where approval_status='NOT_APPROVED'", nativeQuery = true)
    List<Users_Admin_ApprovalEntity> findContributors();


    @Query(value = "select approval_status from mbk_auth_schema.users_admin_approval where Uid=:userId", nativeQuery = true)
    String getApprovalStatus(Integer userId);

}
