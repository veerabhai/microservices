package com.mbk.app.features.platform.data.repository;

import com.mbk.app.features.platform.data.model.persistence.PasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface passwordTokenRepository extends JpaRepository<PasswordTokenEntity,Integer> {


@Query(value = "SELECT EXISTS(SELECT id, uid, password_token FROM mbk_auth_schema.password_token where uid=:userId and password_token=:passwordToken)",nativeQuery = true)
    boolean isExisted(int userId, Long passwordToken);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM mbk_auth_schema.password_token WHERE password_token =:token and uid=:userId",nativeQuery = true)
    void deleteByToken(Long token,Integer userId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM mbk_auth_schema.password_token WHERE uid=:userId",nativeQuery = true)
    void deleteByUid(Integer userId);
}