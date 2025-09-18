package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class StorageDaoImpl implements StorageDao {
    @Override
    public User add(User user) {
        Storage.people.put(user.getLogin(), user);
        return user;
    }

    @Override
    public User get(String login) {
        return Storage.people.get(login);
    }
}
