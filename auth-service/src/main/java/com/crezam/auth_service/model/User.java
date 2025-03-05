package com.crezam.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String gstNumber;

    private String organizationName;


}
