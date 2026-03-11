package com.revpay.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.revpay.entity.Role;
import com.revpay.entity.User;

// @RunWith(SpringRunner.class)
//   → Required for JUnit4 to boot a Spring context.
//     Without this line, @Autowired does not work in JUnit4.
@RunWith(SpringRunner.class)

// @DataJpaTest
//   → Loads ONLY the JPA layer: entities, repositories, H2 datasource.
//     Does NOT load controllers, security, or service classes.
//     This makes it faster than @SpringBootTest.
@DataJpaTest

// @ActiveProfiles("test")
//   → Tells Spring to load application-test.properties instead of
//     application.properties. Your application-test.properties points
//     to H2 in-memory DB so no real MySQL connection is needed.
@ActiveProfiles("test")
public class UserRepositoryTest {

    private static final Logger logger = LogManager.getLogger(UserRepositoryTest.class);

    // Spring injects the real UserRepository backed by H2
    @Autowired
    private UserRepository userRepository;

    // @Before runs before EVERY test method — clears all rows from H2
    // so tests don't interfere with each other
    @Before
    public void setUp() {
        userRepository.deleteAll();
        logger.info("===== UserRepositoryTest setUp — H2 database cleared =====");
    }

    // Helper – creates and persists a User to H2
    private User saveUser(String name, String email, String phone, Role role) {
        User u = new User();
        u.setFullName(name);
        u.setEmail(email);
        u.setPhone(phone);
        u.setPassword("encodedHash");
        u.setRole(role);
        u.setWalletBalance(0.0);
        return userRepository.save(u);
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 1
    // Method : findByEmail()
    // Checks : a saved user can be found by their exact email
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testFindByEmail_FindsSavedUser() {
        logger.info("Running: testFindByEmail_FindsSavedUser");

        saveUser("Alice", "alice@test.com", "9111100001", Role.PERSONAL);

        Optional<User> found = userRepository.findByEmail("alice@test.com");

        assertTrue("User must be found by email", found.isPresent());
        assertEquals("Full name must match", "Alice", found.get().getFullName());
        logger.info("PASS: found user with email = {}", found.get().getEmail());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 2
    // Method : findByEmail()
    // Checks : searching for an email that does not exist returns empty Optional
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testFindByEmail_UnknownEmailReturnsEmpty() {
        logger.info("Running: testFindByEmail_UnknownEmailReturnsEmpty");

        Optional<User> found = userRepository.findByEmail("ghost@test.com");

        assertFalse("Unknown email must return empty Optional", found.isPresent());
        logger.info("PASS: Optional is empty for unknown email");
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 3
    // Method : save() → getId()
    // Checks : a saved user is assigned a generated primary key (ID > 0)
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testSave_GeneratesAutoId() {
        logger.info("Running: testSave_GeneratesAutoId");

        User u = saveUser("Bob", "bob@test.com", "9222200002", Role.PERSONAL);

        assertNotNull("ID must not be null after save", u.getId());
        assertTrue("ID must be greater than 0", u.getId() > 0);
        logger.info("PASS: saved user got ID = {}", u.getId());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 4
    // Method : findByStatusNot()
    // Checks : DELETED users are excluded from results
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testFindByStatusNot_ExcludesDeletedUsers() {
        logger.info("Running: testFindByStatusNot_ExcludesDeletedUsers");

        // active user — status defaults to "ACTIVE" in entity
        saveUser("Charlie", "charlie@test.com", "9333300003", Role.PERSONAL);

        // manually create a DELETED user
        User deleted = new User();
        deleted.setFullName("Deleted User");
        deleted.setEmail("deleted@test.com");
        deleted.setPhone("9000000099");
        deleted.setPassword("pass");
        deleted.setRole(Role.PERSONAL);
        deleted.setStatus("DELETED");
        deleted.setWalletBalance(0.0);
        userRepository.save(deleted);

        List<User> active = userRepository.findByStatusNot("DELETED");

        assertEquals("Must return only 1 active user", 1, active.size());
        assertEquals("Active user must be charlie", "charlie@test.com", active.get(0).getEmail());
        logger.info("PASS: {} active user(s) found, deleted excluded", active.size());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 5
    // Method : findAll()
    // Checks : all 3 saved users are returned
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testFindAll_ReturnsAllSavedUsers() {
        logger.info("Running: testFindAll_ReturnsAllSavedUsers");

        saveUser("User1", "u1@test.com", "9001000001", Role.PERSONAL);
        saveUser("User2", "u2@test.com", "9001000002", Role.PERSONAL);
        saveUser("User3", "u3@test.com", "9001000003", Role.BUSINESS);

        List<User> all = userRepository.findAll();

        assertEquals("findAll must return all 3 saved users", 3, all.size());
        logger.info("PASS: findAll returned {} users", all.size());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 6
    // Method : delete()
    // Checks : a deleted user cannot be found afterwards
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testDelete_UserNoLongerFound() {
        logger.info("Running: testDelete_UserNoLongerFound");

        User u = saveUser("ToDelete", "todelete@test.com", "9888800001", Role.PERSONAL);
        Long id = u.getId();

        userRepository.deleteById(id);

        Optional<User> found = userRepository.findById(id);
        assertFalse("Deleted user must not be found by ID", found.isPresent());
        logger.info("PASS: user with ID {} no longer exists after delete", id);
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 7
    // Method : findByEmail() + wallet balance check
    // Checks : wallet balance is stored and retrieved correctly
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testFindByEmail_WalletBalanceStoredCorrectly() {
        logger.info("Running: testFindByEmail_WalletBalanceStoredCorrectly");

        User u = saveUser("Priya", "priya@test.com", "9777700007", Role.PERSONAL);
        u.setWalletBalance(2500.0);
        userRepository.save(u); // update wallet

        Optional<User> found = userRepository.findByEmail("priya@test.com");

        assertTrue("User must be found", found.isPresent());
        assertEquals("Wallet balance must be 2500.0", 2500.0, found.get().getWalletBalance(), 0.001);
        logger.info("PASS: wallet balance = {}", found.get().getWalletBalance());
    }
}
