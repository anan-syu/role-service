package com.lk.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lk.assessment.dto.RoleDTO;
import com.lk.assessment.entity.Role;
import com.lk.assessment.enums.RoleType;
import com.lk.assessment.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoleController.class)
class RoleControllerTest {

    private static final Long ROLE_ID = 1L;
    private static final String ORGANIZATION = "Organization";
    private static final String FIRST_NAME = "Toyota";
    private static final String LAST_NAME = "Mazda";
    private static final String NIC_NO = "888888888";
    private static final String ROLE_TYPE_S = "ASSISTANT";
    private static final RoleType ROLE_TYPE = RoleType.ASSISTANT;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private RoleService roleService;

    @Test
    void findAllRoles() throws Exception {
        when(roleService.findAllRoles()).thenReturn(Arrays.asList(
                new RoleDTO(1L, "oOne", "fOne", "lOne", "nicNO", "roleType"),
                new RoleDTO(2L, "oTwo", "fTwo", "lTwo", "nicNO", "roleType"),
                new RoleDTO(3L, "oThree", "fThree", "lastName", "nicNO", "roleType")
        ));

        MvcResult result = mvc.perform(get("/api/role")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].organization", is("oOne")))
                .andExpect(jsonPath("$[0].firstName", is("fOne")))
                .andExpect(jsonPath("$[0].lastName", is("lOne")))
                .andExpect(jsonPath("$[0].nicNO", is("nicNO")))
                .andExpect(jsonPath("$[0].roleType", is("roleType")))
                .andReturn();
    }

    @Test
    void findRole() throws Exception {
        when(roleService.findRoleById(any()))
                .thenReturn(getRoleDTO());

        MvcResult result = mvc.perform(get("/api/role/{id}", ROLE_ID))
                .andExpect(status().isOk())
                .andReturn();
        RoleDTO rating = fromJson(result, RoleDTO.class);
        checkRoleDto(rating);
    }

    @Test
    void createRole() throws Exception {
        when(roleService.createRole(any()))
                .thenReturn(getRole());

        MvcResult result = mvc.perform(post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(getRoleDTO())))
                .andExpect(status().isCreated())
                .andReturn();

        RoleDTO role = fromJson(result, RoleDTO.class);
        checkRoleDto(role);
    }

    @Test
    void deleteRole() throws Exception {

        mvc.perform(delete("/api/role/{id}", ROLE_ID))
                .andExpect(status().isOk());
    }

    @Test
    void updateRole() throws Exception {

        mvc.perform(delete("/api/role/{id}", ROLE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
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

    private <T> T fromJson(MvcResult result, Class<T> clazz) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(), clazz);
    }


    private void checkRoleDto(RoleDTO role) {
        assertNotNull(role);
        assertEquals(ROLE_ID, role.getId());
        assertEquals(ORGANIZATION, role.getOrganization());
        assertEquals(FIRST_NAME, role.getFirstName());
        assertEquals(LAST_NAME, role.getLastName());
        assertEquals(NIC_NO, role.getNicNO());
        assertEquals(ROLE_TYPE_S, role.getRoleType());

    }
}