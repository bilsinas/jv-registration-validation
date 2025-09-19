package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null.");
        }

        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    "Login must be at least " + MIN_LOGIN_LENGTH + " characters."
            );
        }

        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password must be at least " + MIN_PASSWORD_LENGTH + " characters."
            );
        }

        if (age == null || age < MIN_AGE) {
            throw new RegistrationException(
                    "Age must be at least " + MIN_AGE + " years old."
            );
        }

        if (storageDao.get(login) != null) {
            throw new RegistrationException(
                    "User with this login already exists."
            );
        }

        return storageDao.add(user);
    }
}
