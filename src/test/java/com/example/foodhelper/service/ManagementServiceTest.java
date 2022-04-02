package com.example.foodhelper.service;

import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ManagementServiceTest {

    @InjectMocks
    private ManagementService managementService;
    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getAllAccounts() {
    }

    @Test
    void getAccountById() {
    }

    @Test
    void changeIsAccountActive() {
    }

    @Test
    void activateAccount() {
    }

    @Test
    void promoteAccount() {
    }

    @Test
    void demoteAccount() {
    }

    @Test
    void deleteAccount() {
    }


}