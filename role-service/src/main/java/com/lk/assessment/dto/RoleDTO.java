package com.lk.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO implements Serializable {

    private Long id;
    private String organization;
    private String firstName;
    private String lastName;
    private String nicNO;
    private String roleType;
}
