package classes.repositories;

import classes.domains.User;

public interface IUserRepository {
    /**
     * Test method that returns a dummy User object called John.
     * @return
     */
    User getDummyUser();

    /**
     * Check if user exists in the database.
     * @return
     */
    Boolean checkUserExists(User user);
}
