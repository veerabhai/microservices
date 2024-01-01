/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.instrumentation.Instrument;
import com.mbk.app.features.platform.data.mapper.RoleMapper;
import com.mbk.app.features.platform.data.model.experience.role.CreateRoleRequest;
import com.mbk.app.features.platform.data.model.experience.role.PatchRoleRequest;
import com.mbk.app.features.platform.data.model.experience.role.Role;
import com.mbk.app.features.platform.data.model.experience.role.UpdateRoleRequest;
import com.mbk.app.features.platform.data.model.persistence.RoleEntity;
import com.mbk.app.features.platform.data.repository.RoleRepository;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link RoleEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class RoleService {
    /** Repository implementation of type {@link RoleRepository}. */
    private final RoleRepository roleRepository;

    /** Mapper implementation of type {@link RoleMapper} to transform between different types. */
    private final RoleMapper roleMapper;

    /**
     * Constructor.
     *
     * @param roleRepository Repository instance of type {@link RoleRepository}.
     * @param roleMapper Mapper instance of type {@link RoleMapper}.
     */
    public RoleService(final RoleRepository roleRepository, final RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * This method attempts to create an instance of type {@link RoleEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     RoleEntity}.
     * @return An experience model of type {@link Role} that represents the newly created entity of
     *     type {@link RoleEntity}.
     */
    @Instrument
    @Transactional
    public Role createRole(@Valid final CreateRoleRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final RoleEntity roleEntity = roleMapper.transform(payload);

        // 2. Save the entity.
        RoleService.LOGGER.debug("Saving a new instance of type - RoleEntity");
        final RoleEntity newInstance = roleRepository.save(roleEntity);

        // 3. Transform the created entity to an experience model and return it.
        return roleMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link RoleEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateRoleRequest}.
     *
     * @param roleId Unique identifier of Role in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Role, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Role} containing the updated details.
     */
    @Instrument
    @Transactional
    public Role updateRole(final String roleId, @Valid final UpdateRoleRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final RoleEntity matchingInstance = roleRepository.findByIdOrThrow(roleId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        roleMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        RoleService.LOGGER.debug("Saving the updated entity - RoleEntity");
        final RoleEntity updatedInstance = roleRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return roleMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link RoleEntity} using the
     * details from the provided input, which is an instance of type {@link PatchRoleRequest}.
     *
     * @param roleId Unique identifier of Role in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Role, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Role} containing the updated details.
     */
    @Instrument
    @Transactional
    public Role patchRole(final String roleId, @Valid final PatchRoleRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final RoleEntity matchingInstance = roleRepository.findByIdOrThrow(roleId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(RoleEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        RoleService.LOGGER.debug("Saving the updated entity - RoleEntity");
        final RoleEntity updatedInstance = roleRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return roleMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link RoleEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param roleId Unique identifier of Role in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Role} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Role findRole(final String roleId) {
        // 1. Find a matching entity and throw an exception if not found.
        final RoleEntity matchingInstance = roleRepository.findByIdOrThrow(roleId);

        // 2. Transform the matching entity to the desired output.
        return roleMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type RoleEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Role}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Role> findAllRoles(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        RoleService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<RoleEntity> pageData = roleRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Role> dataToReturn =
                    pageData.getContent().stream()
                            .map(roleMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link RoleEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param roleId Unique identifier of Role in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type RoleEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteRole(final String roleId) {
        // 1. Delegate to our repository method to handle the deletion.
        return roleRepository.deleteOne(roleId);
    }
}
