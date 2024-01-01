/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.repository;

import com.mbk.app.commons.data.jpa.repository.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

/**
 * Repository interface to handle the operations pertaining to domain models of type "TaalaEntity".
 *
 * @author Editor
 */
@Repository
public interface TaalaRepository extends ExtendedJpaRepository<TaalaEntity, String> {


    @Query(value = "SELECT entity FROM TaalaEntity entity WHERE entity.description = :description ")
    TaalaEntity findTaalaByDescription(final String description);
}
