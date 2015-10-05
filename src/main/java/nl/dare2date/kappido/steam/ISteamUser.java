package nl.dare2date.kappido.steam;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public interface ISteamUser {
    String getSteamId();

    List<ISteamGame> getOwnedGames();
}
