package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_validUser_ok() {
        User user = new User("valid_login", "password123", 25);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_loginShorterThanMin_notOk() {
        User user = new User("short", "password123", 25);
        Exception exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters.", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "password123", 25);
        Exception exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters.", exception.getMessage());
    }

    @Test
    void register_passwordShorterThanMin_notOk() {
        User user = new User("valid_login", "pass", 25);
        Exception exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters.", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("valid_login", null, 25);
        Exception exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters.", exception.getMessage());
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User("valid_login", "password123", 17);
        Exception exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old.", exception.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("valid_login", "password123", null);
        Exception exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old.", exception.getMessage());
    }

    @Test
    void register_existingLogin_notOk() {
        User user1 = new User("existing_login", "password123", 25);
        registrationService.register(user1);
        User user2 = new User("existing_login", "another_password", 30);
        Exception exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
        assertEquals("User with this login already exists.", exception.getMessage());
    }

    @Test
    void register_validUserWithMinLoginAndPasswordLength_ok() {
        User user = new User("sixlog", "sixpas", 25);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_validUserWithMinAge_ok() {
        User user = new User("valid_login", "password123", 18);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }
}
