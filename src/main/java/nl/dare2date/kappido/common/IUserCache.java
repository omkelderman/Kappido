package nl.dare2date.kappido.common;

/**
 * Created by Maarten on 6-10-2015.
 */
public interface IUserCache<User> {
    User getUserById(String id);

    void addToCache(User user, String id);
}
