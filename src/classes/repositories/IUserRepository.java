package classes.repositories;

import classes.domains.User;

import java.io.IOException;
import java.sql.SQLException;

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

    /**
     * Check if username exists in the database.
     * @param username
     * @return
     */
    Boolean checkUsernameExists(String username);

    /**
     * Create new user in the database.
     * @param user
     */
    Boolean createUser(User user);

    /**
     * Retrieve the current amount of credits linked to this username.
     * Doesn't use an entire user object because it might be fun to see other player's credits at some point.
     * @param username
     * @return
     */

    User login(String username,String password) throws SQLException, IOException, ClassNotFoundException;
}
