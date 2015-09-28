package nl.dare2date.kappido.steam;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class SteamUser implements ISteamUser {

    private final String steamId;
    private List<ISteamGame> ownedGames;

    public SteamUser(String steamId){
        this.steamId = steamId.toLowerCase();
    }

    @Override
    public String getSteamId() {
        return steamId;
    }

    @Override
    public List<ISteamGame> getOwnedGames() {
        if(ownedGames == null){
            ownedGames = new SteamAPIWrapper().getOwnedGames(steamId);
        }
        return ownedGames;
    }

    public String toString(){
        return getSteamId();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SteamUser)) return false;
        SteamUser user = (SteamUser) o;
        return steamId.equals(user.steamId);
    }

    @Override
    public int hashCode() {
        return steamId.hashCode();
    }
}
