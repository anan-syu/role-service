package com.lk.assessment.service;

import com.lk.assessment.dto.RoleDTO;
import com.lk.assessment.entity.Role;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAllRoles();

    Role createRole(RoleDTO roleDTO);

    Role updateRole(RoleDTO roleDTO);

    void deleteRole(Long roleId);

    RoleDTO findRoleById(Long roleId);

    RoleDTO findRoleByNic(String nic);

    List<RoleDTO> findAllByOrganizationAndRoleType(String organization, String roleType);
}
