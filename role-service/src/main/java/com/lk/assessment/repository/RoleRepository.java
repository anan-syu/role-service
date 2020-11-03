package com.lk.assessment.repository;

import com.lk.assessment.entity.Role;
import com.lk.assessment.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * @param nicNo
     * @return
     */
    Role findByNicNO(String nicNo);

    /**
     * @param organization
     * @param roleType
     * @return
     */
    List<Role> findAllByOrganizationAndRoleType(String organization, RoleType roleType);
}
