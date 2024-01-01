/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.exception.ServiceException;
import com.mbk.app.commons.instrumentation.Instrument;
import com.mbk.app.features.platform.data.mapper.UserMapper;
import com.mbk.app.features.platform.data.model.experience.admin.AdminApproval;
import com.mbk.app.features.platform.data.model.experience.admin.AdminRequest;
import com.mbk.app.features.platform.data.model.experience.email.EmailDetails;
import com.mbk.app.features.platform.data.model.experience.user.CreateUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.PatchUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.UpdateUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.User;
import com.mbk.app.features.platform.data.model.persistence.PasswordTokenEntity;
import com.mbk.app.features.platform.data.model.persistence.Users_Admin_ApprovalEntity;
import com.mbk.app.features.platform.data.model.persistence.UserEntity;

import com.mbk.app.features.platform.data.repository.RoleRepository;
import com.mbk.app.features.platform.data.repository.UsersAdminApprovalRepository;
import com.mbk.app.features.platform.data.repository.UserRepository;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.mbk.app.features.platform.data.repository.passwordTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link UserEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class UserService {

    /**
     * Repository implementation of type {@link UserRepository}.
     */
    private static  UserRepository userRepository;

    /**
     * Mapper implementation of type {@link UserMapper} to transform between different types.
     */
    private final UserMapper userMapper;

    /**
     * Repository implementation of type {@link RoleRepository}.
     */
    private final RoleRepository roleRepository;
    @Autowired
    private UsersAdminApprovalRepository usersAdminApprovalRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private passwordTokenRepository passwordTokenRepository;


    /**
     * Constructor.
     *
     * @param userRepository Repository instance of type {@link UserRepository}.
     * @param userMapper     Mapper instance of type {@link UserMapper}.
     * @param roleRepository Repository instance of type {@link RoleRepository}.
     */
    public UserService(
            final UserRepository userRepository,
            final UserMapper userMapper,
            final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    /**
     * This method attempts to create an instance of type {@link UserEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *                UserEntity}.
     * @return An experience model of type {@link User} that represents the newly created entity of
     * type {@link UserEntity}.
     */
    @Instrument
    @Transactional
    public User createUser(@Valid final CreateUserRequest payload) {
        if (payload.getRoles().toArray()[0].equals("38a47da9-8077-4872-9d75-8bb43b041f9a") && payload.getReason().length() < 1) {
            throw new RuntimeException("Please Provide the Reason Why You Wanted To Become A Contributor...!");
        }
        // 1. Transform the experience model to a persistence model.
        final UserEntity userEntity = userMapper.transform(payload);
        final UserEntity matching = userRepository.findByUsername(payload.getUsername());
        if (matching != null) {
            throw ServiceException.instance("Username", "user already exist!");
        }
        // 2. Save the entity.
        UserService.LOGGER.debug("Saving a new instance of type - UserEntity");
        final UserEntity newInstance = userRepository.save(userEntity);
        //if its a contributor or a viewer then
        //Saving Contributor
        if (payload.getRoles().toArray()[0].equals("38a47da9-8077-4872-9d75-8bb43b041f9a") ||
                (payload.getRoles().toArray()[0].equals("b65d0f04-4795-4cff-b8f8-67a03608876f"))) {
            Users_Admin_ApprovalEntity users_admin_approvalEntity = new Users_Admin_ApprovalEntity();
            users_admin_approvalEntity.setUid(newInstance.getId());
            users_admin_approvalEntity.setRoleId(String.valueOf(payload.getRoles().toArray()[0]));
            users_admin_approvalEntity.setUsername(payload.getUsername());
            if(payload.getRoles().toArray()[0].equals("38a47da9-8077-4872-9d75-8bb43b041f9a")){
                users_admin_approvalEntity.setApproval("NOT_APPROVED");
                users_admin_approvalEntity.setReason(payload.getReason());
            }
            else{
                users_admin_approvalEntity.setApproval("APPROVED");
            }
            users_admin_approvalEntity.setCreated_date(new Date(System.currentTimeMillis()));
            usersAdminApprovalRepository.save(users_admin_approvalEntity);
        }

        // 3. Transform the created entity to an experience model and return it.
        return userMapper.transform(newInstance);

    }




    /**
     * This method attempts to update an existing instance of type {@link UserEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateUserRequest}.
     *
     * @param userId Unique identifier of User in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing User, which needs to be
     *     updated in the system.
     * @return A instance of type {@link User} containing the updated details.
     */
    @Instrument
    @Transactional
    public User updateUser(final Integer userId, @Valid final UpdateUserRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserEntity matchingInstance = userRepository.findByIdOrThrow(userId);

        // 2. Transform the experience model to a persistence model and delegate to the save(..)
        // method.
        userMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        UserService.LOGGER.debug("Saving the updated entity - UserEntity");
        final UserEntity updatedInstance = userRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserEntity} using the
     * details from the provided input, which is an instance of type {@link PatchUserRequest}.
     *
     * @param userId Unique identifier of User in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing User, which needs to be
     *     updated in the system.
     * @return A instance of type {@link User} containing the updated details.
     */
    @Instrument
    @Transactional
    public User patchUser(final Integer userId, @Valid final PatchUserRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserEntity matchingInstance = userRepository.findByIdOrThrow(userId);

        // 2. Transform the experience model to a persistence model and delegate to the save(..)
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(UserEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        UserService.LOGGER.debug("Saving the updated entity - UserEntity");
        final UserEntity updatedInstance = userRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link UserEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param userId Unique identifier of User in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link User} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public User findUser(final Integer userId) {
        // 1. Find a matching entity and throw an exception if not found.
        final UserEntity matchingInstance = userRepository.findByIdOrThrow(userId);


        // 2. Transform the matching entity to the desired output.
        return userMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type UserEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link User}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<User> findAllUsers(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        UserService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<UserEntity> pageData = userRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<User> dataToReturn =
                    pageData.getContent().stream()
                            .map(userMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link UserEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param userId Unique identifier of User in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type UserEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteUser(final Integer userId) {
        // 1. Delegate to our repository method to handle the deletion.
        return userRepository.deleteOne(userId);
    }
    @Instrument
    @Transactional
    public List<AdminRequest> adminRequestList() {
        List<AdminRequest> adminRequestList = new ArrayList<>();
        List<Users_Admin_ApprovalEntity> users_admin_approvals = usersAdminApprovalRepository.findContributors();
        for (Users_Admin_ApprovalEntity uaa:users_admin_approvals) {
            if(uaa.getRoleId().equals("38a47da9-8077-4872-9d75-8bb43b041f9a") && "NOT_APPROVED".equals(uaa.getApproval()))
                adminRequestList.add(new AdminRequest(uaa.getReason(),uaa.getUsername()));
        }
        return adminRequestList;
    }
    @Instrument
    @Transactional
    public String adminApprovalStatus(List<AdminApproval> adminApproval) {
        for (AdminApproval approval:adminApproval) {
            Date approvedOn = new Date(System.currentTimeMillis());
            String userName= approval.getUserName();
            String approvalStatus= approval.getApprovalStatus();
            usersAdminApprovalRepository.setContributorStatus(userName,approvalStatus,approvedOn);
        }

        return "updated";
    }


    @Instrument
    @Transactional
    public  String getPasswordToken(String userName) {

        UserEntity userEntity = userRepository.findByUsername(userName);
        if (Objects.isNull(userEntity)) {
            throw ServiceException.instance(userName,"Your email doesn't exist in our database..!");
        }
        Integer userId = userEntity.getId();
        passwordTokenRepository.deleteByUid(userEntity.getId());

        String approvalStatus = usersAdminApprovalRepository.getApprovalStatus(userId);

        String userApprovalStatus = usersAdminApprovalRepository.getApprovalStatus(userId);
        if (userApprovalStatus != null && !userApprovalStatus.isEmpty() && userApprovalStatus.equalsIgnoreCase("NOT_APPROVED")) {
            throw ServiceException.instance(userName,"Please wait, Your request for being a CONTRIBUTOR is under process");
        }

        Random random = new Random();

        int otp = 100000 + random.nextInt(900000);
        PasswordTokenEntity passwordTokenEntity = new PasswordTokenEntity();
        passwordTokenEntity.setUid(userId);
        passwordTokenEntity.setPasswordToken((long) otp);

        passwordTokenRepository.save(passwordTokenEntity);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setTo(userName);
        emailDetails.setSubject("Request for Reset Password ");
        emailDetails.setBody(String.valueOf("Hi user,\n\nPlease enter the following OTP to reset your password: "+ otp+ " \n\nThank you, \nBalamurali Support"));
        sendEmail(emailDetails);
        return "received otp to mail";

    }

    @Instrument
    @Transactional
    public String validatePasswordToken(String userName, Long passwordToken,String newPassword,String confirmPassword) {

        UserEntity userEntity = userRepository.findByUsername(userName);
        newPassword=passwordEncoder.encode(newPassword);
        if(newPassword.equals(userEntity.getPassword())){
            throw ServiceException.instance(newPassword,"New password should not match old password");
        }


        String updation="Failed to update password";
        boolean validate=passwordTokenRepository.isExisted(userEntity.getId(),passwordToken);
        if(validate){
            if (Objects.nonNull(newPassword) && !newPassword.isEmpty()) {

                userRepository.updatePassword(userEntity.getId(),newPassword);
                updation="successfully updated password";
                passwordTokenRepository.deleteByToken(passwordToken,userEntity.getId());
            }
            else {
                throw ServiceException.instance(newPassword,"please provide the new password...!");
            }
        }
        else {
            throw ServiceException.instance(String.valueOf(passwordToken),"please enter the valid OTP...!");
        }

        return updation;
    }

    public String sendEmail(EmailDetails emaildetails) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emaildetails.getTo());
        message.setSubject(emaildetails.getSubject());
        message.setText(emaildetails.getBody());
        emailSender.send(message);

        return "sent";

    }
}
