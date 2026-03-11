package com.revpay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.revpay.entity.Role;
import com.revpay.entity.User;
import com.revpay.repository.UserRepository;
import com.revpay.serviceimpl.UserServiceImpl;

public class UserServiceTest {

    private static final Logger logger = LogManager.getLogger(UserServiceTest.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("===== UserServiceTest setUp complete =====");
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 1
    // Method : registerUser()
    // Checks : encoder.encode() is called exactly once
    //          repository.save() is called exactly once
    //          returned user is not null
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testRegisterUser_EncoderIsCalledOnce() {
        logger.info("Running: testRegisterUser_EncoderIsCalledOnce");

        User user = new User();
        user.setFullName("Ravi Kumar");
        user.setEmail("ravi@test.com");
        user.setPhone("9000000001");
        user.setPassword("plain123");
        user.setRole(Role.PERSONAL);

        // When encoder.encode("plain123") is called → return this fake hash
        when(encoder.encode("plain123")).thenReturn("$2a$10$fakeHash");
        // When repository.save(anything) is called → return the same user
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        // returned object must not be null
        assertNotNull("Result should not be null", result);
        // encoder must have been called exactly once
        verify(encoder, times(1)).encode("plain123");
        // save must have been called exactly once
        verify(userRepository, times(1)).save(user);

        logger.info("PASS: encoder and repository.save both called once");
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 2
    // Method : registerUser()
    // Checks : saved user keeps the exact email that was set
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testRegisterUser_EmailIsPreserved() {
        logger.info("Running: testRegisterUser_EmailIsPreserved");

        User user = new User();
        user.setEmail("sita@test.com");
        user.setPassword("pass");
        user.setRole(Role.PERSONAL);

        when(encoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals("Email must be preserved", "sita@test.com", result.getEmail());
        logger.info("PASS: email preserved = {}", result.getEmail());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 3
    // Method : registerUser()
    // Checks : a BUSINESS role user saves correctly with BUSINESS role
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testRegisterUser_BusinessRoleIsPreserved() {
        logger.info("Running: testRegisterUser_BusinessRoleIsPreserved");

        User user = new User();
        user.setEmail("corp@test.com");
        user.setPassword("bizpass");
        user.setRole(Role.BUSINESS);

        when(encoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals("Role must be BUSINESS", Role.BUSINESS, result.getRole());
        logger.info("PASS: role = {}", result.getRole());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 4
    // Method : registerUser()
    // Checks : wallet balance on a new user defaults to 0.0
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testRegisterUser_WalletBalanceIsZero() {
        logger.info("Running: testRegisterUser_WalletBalanceIsZero");

        User user = new User();
        user.setEmail("zero@test.com");
        user.setPassword("pass");
        user.setRole(Role.PERSONAL);
        // walletBalance defaults to 0.0 in the User entity constructor

        when(encoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        // 0.001 is the allowed floating-point margin
        assertEquals("Wallet balance must be 0.0 for new user", 0.0, result.getWalletBalance(), 0.001);
        logger.info("PASS: walletBalance = {}", result.getWalletBalance());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 5
    // Method : registerUser()
    // Checks : even if encoder.encode() returns a different hash,
    //          repository.save() is still called (no short-circuit)
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testRegisterUser_SaveIsAlwaysCalled() {
        logger.info("Running: testRegisterUser_SaveIsAlwaysCalled");

        User user = new User();
        user.setEmail("always@test.com");
        user.setPassword("anyPass");
        user.setRole(Role.PERSONAL);

        when(encoder.encode(anyString())).thenReturn("someHash");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.registerUser(user);

        // no matter what the encoder returns, save must run
        verify(userRepository, times(1)).save(any(User.class));
        logger.info("PASS: save() always called regardless of encoded value");
    }
}
