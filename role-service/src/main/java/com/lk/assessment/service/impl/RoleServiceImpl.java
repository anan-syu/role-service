package com.lk.assessment.service.impl;

import com.lk.assessment.Exeptions.RoleNotFoundException;
import com.lk.assessment.dto.RoleDTO;
import com.lk.assessment.entity.Role;
import com.lk.assessment.enums.RoleType;
import com.lk.assessment.repository.RoleRepository;
import com.lk.assessment.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return convertEntityListToDTOList(roles);
    }

    @Override
    public Role createRole(RoleDTO roleDTO) {
        Role role = Role.builder().build();
        BeanUtils.copyProperties(roleDTO, role);
        role.setRoleType(convertRoleType(roleDTO.getRoleType()));
        if (role.getId() != null) {
            role.setId(null);
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleDTO.getId())
                .orElseThrow(() -> new RoleNotFoundException("This Role does not exists"));
        BeanUtils.copyProperties(roleDTO, role);
        role.setRoleType(convertRoleType(roleDTO.getRoleType()));
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("This Role does not exists"));
        roleRepository.deleteById(roleId);
    }

    @Override
    public RoleDTO findRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("This Role does not exists"));
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        roleDTO.setRoleType(role.getRoleType().name());
        return roleDTO;
    }

    @Override
    public RoleDTO findRoleByNic(String nic) {
        RoleDTO roleDTO = RoleDTO.builder().build();
        Role byNicNO = roleRepository.findByNicNO(nic);
        BeanUtils.copyProperties(byNicNO, roleDTO);
        roleDTO.setRoleType(byNicNO.getRoleType().name());
        return roleDTO;
    }

    @Override
    public List<RoleDTO> findAllByOrganizationAndRoleType(String organization, String type) {
        RoleType roleType = convertRoleType(type);
        List<Role> roles = roleRepository.findAllByOrganizationAndRoleType(organization, roleType);
        return convertEntityListToDTOList(roles);
    }

    private RoleType convertRoleType(String type) {
        RoleType roleType;
        switch (type) {
            case "DRIVER":
                roleType = RoleType.DRIVER;
                break;
            case "ASSISTANT":
                roleType = RoleType.ASSISTANT;
                break;
            default:
                throw new IllegalArgumentException("Invalid Role type");
        }
        return roleType;
    }

    private List<RoleDTO> convertEntityListToDTOList(List<Role> roles) {
        return roles.stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(role, roleDTO);
            roleDTO.setRoleType(role.getRoleType().name());
            return roleDTO;
        }).collect(Collectors.toList());
    }
}
