package classes.repositories;

import classes.domains.User;

public class SQLUserRepository implements IUserRepository {
    @Override
    public User getDummyUser() {
        return new User();
    }
}
