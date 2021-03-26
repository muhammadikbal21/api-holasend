package com.enigmacamp.api.holasend.entities;

import com.enigmacamp.api.holasend.enums.IdentityCategoryEnum;
import com.enigmacamp.api.holasend.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EntityGetterSetterTest {

    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void setup() {
        Integer anyInteger = 1;
        Double anyDouble = 1.0;
        String anyString = "x";
        LocalDateTime anyTime = LocalDateTime.MIN;

        userDetails = new UserDetails();
        userDetails.setId(anyString);
        userDetails.setIdentityCategory(IdentityCategoryEnum.KTP);
        userDetails.setIdentificationNumber(anyString);
        userDetails.setFirstName(anyString);
        userDetails.setLastName(anyString);
        userDetails.setContactNumber(anyString);

        userDetails.setIsDeleted(true);
        userDetails.prePersist();
        userDetails.setCreateDate(anyTime);
        userDetails.preUpdate();
        userDetails.setModifiedDate(anyTime);

        user = new User();
        user.setId(anyString);
        user.setUsername(anyString);
        user.setEmail(anyString);
        user.setPassword(anyString);
        user.setToken(anyString);
        user.setRole(RoleEnum.ADMIN);
        user.setUserDetails(userDetails );

    }

    @Test
    void userDetailsSetterShouldEqualsGetter() {
        UserDetails expected = userDetails;

        UserDetails actual = new UserDetails();
        actual.setId(expected.getId());
        actual.setIdentityCategory(expected.getIdentityCategory());
        actual.setIdentificationNumber(expected.getIdentificationNumber());
        actual.setFirstName(expected.getFirstName());
        actual.setLastName(expected.getLastName());
        actual.setContactNumber(expected.getContactNumber());
        actual.setCreateDate(expected.getCreateDate());
        actual.setModifiedDate(expected.getModifiedDate());
        actual.setIsDeleted(expected.getIsDeleted());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getIdentityCategory(), actual.getIdentityCategory());
        assertEquals(expected.getIdentificationNumber(), actual.getIdentificationNumber());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getContactNumber(), actual.getContactNumber());
        assertEquals(expected.getCreateDate(), actual.getCreateDate());
        assertEquals(expected.getModifiedDate(), actual.getModifiedDate());
        assertEquals(expected.getIsDeleted(), actual.getIsDeleted());
        assertNotNull(actual.toString());
    }

    @Test
    void userSetterShouldEqualsGetter() {
        User expected = user;

        User actual = new User();
        actual.setId(expected.getId());
        actual.setUsername(expected.getUsername());
        actual.setEmail(expected.getEmail());
        actual.setPassword(expected.getPassword());
        actual.setToken(expected.getToken());
        actual.setRole(expected.getRole());
        actual.setUserDetails(expected.getUserDetails());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getToken(), actual.getToken());
        assertEquals(expected.getRole(), actual.getRole());
        assertEquals(expected.getUserDetails(), actual.getUserDetails());

        assertNotNull(actual.toString());
    }
}