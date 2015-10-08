package nl.dare2date.kappido.steam;

import nl.dare2date.kappido.common.IUserCacheable;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public interface ISteamAPIWrapper extends IUserCacheable<ISteamUser> {
    List<ISteamGame> getOwnedGames(String steamId);

    ISteamUser getUser(String steamId);

    void addGameDetails(ISteamGame game);
}
