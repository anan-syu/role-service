package com.lk.assessment.controller;

import com.lk.assessment.dto.RoleDTO;
import com.lk.assessment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/role")
@RestController
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAllRoles() {
        List<RoleDTO> roles = roleService.findAllRoles();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Count", roles.size() + "");
        return new ResponseEntity<>(roles, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findRole(@PathVariable("id") Long roleId) {
        RoleDTO roleDTO = roleService.findRoleById(roleId);
        HttpStatus status = (roleDTO != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(roleDTO, status);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody RoleDTO roleDTO) {
        roleService.createRole(roleDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRole(@PathVariable("id") Long roleId) {
        roleService.deleteRole(roleId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRole(@PathVariable("id") Long roleId,
                                     @RequestBody RoleDTO roleDTO) {
        if (!roleId.equals(roleDTO.getId())) {
            throw new IllegalArgumentException("Role IDs mismatch");
        } else {
            roleService.updateRole(roleDTO);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/nic/{nic}")
    public ResponseEntity<RoleDTO> findRoleByNic(@PathVariable("nic") String nicNO) {
        RoleDTO roleDTO = roleService.findRoleByNic(nicNO);
        HttpStatus status = (roleDTO != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(roleDTO, status);
    }

    @GetMapping("/organization/{orgname}/roletype/{roleType}")
    public ResponseEntity<List<RoleDTO>> findAllByOrganizationAndRoleType(@PathVariable("orgname") String organization, @PathVariable("roleType") String roleType) {
        List<RoleDTO> roleDTO = roleService.findAllByOrganizationAndRoleType(organization, roleType);
        HttpStatus status = (roleDTO != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(roleDTO, status);
    }
}
