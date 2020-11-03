package com.lk.assessment.service;

import com.lk.assessment.Exeptions.RoleNotFoundException;
import com.lk.assessment.dto.RoleDTO;
import com.lk.assessment.entity.Role;
import com.lk.assessment.enums.RoleType;
import com.lk.assessment.repository.RoleRepository;
import com.lk.assessment.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    private static final Long ROLE_ID = 1L;
    private static final String ORGANIZATION = "Organization";
    private static final String FIRST_NAME = "Toyota";
    private static final String LAST_NAME = "Mazda";
    private static final String NIC_NO = "888888888";
    private static final RoleType ROLE_TYPE = RoleType.ASSISTANT;
    private static final String ROLE_TYPE_S = "ASSISTANT";

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @DisplayName("Find All Roles")
    @Test
    void findAllRoles() {

        List<Role> actual = new ArrayList<>();
        actual.add(new Role(1L, new Date(), new Date(), "firstOrg", "first", "first", "firstNic", RoleType.DRIVER));
        actual.add(new Role(2L, new Date(), new Date(), "secondOrg", "second", "lastName", "secondNic", RoleType.DRIVER));
        actual.add(new Role(3L, new Date(), new Date(), "thirdOrg", "third", "lastName", "thirdNic", RoleType.DRIVER));

        given(roleRepository.findAll()).willReturn(actual);
        List<RoleDTO> expected = roleService.findAllRoles();
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getOrganization(), actual.get(i).getOrganization());
            assertEquals(expected.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(expected.get(i).getLastName(), actual.get(i).getLastName());
            assertEquals(expected.get(i).getNicNO(), actual.get(i).getNicNO());
            assertEquals(expected.get(i).getRoleType(), actual.get(i).getRoleType().name());
        }
    }

    @DisplayName("Create Role")
    @Test
    void createRole() {
        when(roleRepository.save(any()))
                .thenReturn(getRole());
        Role role = roleService.createRole(getRoleDTO());
        assertThat(role).isNotNull();
        checkRole(role);
    }

    @DisplayName("Update Role")
    @Test
    void updateRole() {
        when(roleRepository.save(any()))
                .thenReturn(getRole());
        Role role = roleService.updateRole(getRoleDTO());
        checkRole(role);
    }

    @DisplayName("Delete Role")
    @Test
    void deleteRole() {
        when(roleRepository.findById(any()))
                .thenReturn(Optional.of(getRole()));
        roleService.deleteRole(ROLE_ID);
    }

    @DisplayName("Find Role By Id")
    @Test
    void findRoleById() {
        when(roleRepository.findById(any()))
                .thenReturn(Optional.of(getRole()));

        RoleDTO roleById = roleService.findRoleById(ROLE_ID);
        checkRoleDTO(roleById);
    }

    @DisplayName("Find Role By Id,RoleNotFoundException")
    @Test
    void findRoleByIdWhenRoleNotFound() {
        when(roleRepository.findById(ROLE_ID)).thenThrow(new RoleNotFoundException("Role does not exists"));
        RoleNotFoundException notFoundException = Assertions.assertThrows(
                RoleNotFoundException.class,
                () -> roleService.findRoleById(1L));
        assertEquals("Role does not exists", notFoundException.getMessage());
    }
    
    @DisplayName("Find Role By Nic")
    @Test
    void findRoleByNic() {
        when(roleRepository.findByNicNO(any()))
                .thenReturn(getRole());
        RoleDTO role = roleService.findRoleByNic(NIC_NO);
        checkRoleDTO(role);
    }

    private Role getRole() {
        return Role.builder()
                .id(ROLE_ID)
                .organization(ORGANIZATION)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .nicNO(NIC_NO)
                .roleType(ROLE_TYPE)
                .build();
    }

    private RoleDTO getRoleDTO() {
        return RoleDTO.builder()
                .id(ROLE_ID)
                .organization(ORGANIZATION)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .nicNO(NIC_NO)
                .roleType(ROLE_TYPE_S)
                .build();
    }

    private void checkRoleDTO(RoleDTO role) {
        assertEquals(ROLE_ID, role.getId());
        assertEquals(ORGANIZATION, role.getOrganization());
        assertEquals(FIRST_NAME, role.getFirstName());
        assertEquals(LAST_NAME, role.getLastName());
        assertEquals(NIC_NO, role.getNicNO());
        assertEquals(ROLE_TYPE, RoleType.valueOf(role.getRoleType()));

    }

    private void checkRole(Role role) {
        assertEquals(ROLE_ID, role.getId());
        assertEquals(ORGANIZATION, role.getOrganization());
        assertEquals(FIRST_NAME, role.getFirstName());
        assertEquals(LAST_NAME, role.getLastName());
        assertEquals(NIC_NO, role.getNicNO());
        assertEquals(ROLE_TYPE, role.getRoleType());

    }
}