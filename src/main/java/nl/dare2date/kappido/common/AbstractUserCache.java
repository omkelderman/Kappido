package nl.dare2date.kappido.common;

import java.util.HashMap;

/**
 * Created by Maarten on 6-10-2015.
 */
public abstract class AbstractUserCache<User> implements IUserCache<User> {
    private HashMap<String, User> cache = new HashMap<>();

    @Override
    public User getUserById(String id){
        User user = cache.get(id);
        if(user == null) {
            user = createNewUser(id);
            addToCache(user, id);
        }
        return user;
    }

    protected abstract User createNewUser(String id);

    @Override
    public void addToCache(User user, String id){
        cache.put(id, user);
    }
}
