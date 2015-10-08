package nl.dare2date.kappido.common;

/**
 * Created by Olle on 2015-10-08.
 */
public interface IUserCacheable<User> {
    void setCache(IUserCache<User> userCache);
}
