package nl.dare2date.kappido.steam;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public interface ISteamAPIWrapper {
    List<ISteamGame> getOwnedGames(String steamId);

    ISteamUser getUser(String steamId);

    void addGameDetails(ISteamGame game);
}
