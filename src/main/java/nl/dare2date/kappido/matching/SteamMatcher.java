package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.steam.ISteamUser;
import nl.dare2date.kappido.steam.SteamUser;
import nl.dare2date.kappido.twitch.ITwitchUser;
import nl.dare2date.kappido.twitch.TwitchUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maarten on 6-10-2015.
 */
public abstract class SteamMatcher implements IMatcher {
    protected ID2DProfileManager profileManager;
    protected IUserCache<SteamUser> steamUserCache;

    public SteamMatcher(ID2DProfileManager profileManager, IUserCache<SteamUser> steamUserCache){
        this.profileManager = profileManager;
        this.steamUserCache = steamUserCache;
    }

    @Override
    public List<MatchEntry> findMatches(int dare2DateUser) {
        String steamId = profileManager.getSteamId(dare2DateUser);
        if(steamId == null) throw new IllegalStateException("No Steam Id for user " + dare2DateUser); //TODO handle differently?

        ISteamUser steamUser = steamUserCache.getUserById(steamId);
        if(steamUser == null) throw new IllegalStateException("No Steam user for Steam Id " + steamId); //TODO handle differently?

        return findMatches(dare2DateUser, steamUser, getSteamDare2DateUsers());
    }

    private Map<Integer, ISteamUser> getSteamDare2DateUsers(){
        Map<Integer, ISteamUser> userMap = new HashMap<>();
        for (int dare2DateUser : profileManager.getAllUsers()) {
            String steamId = profileManager.getSteamId(dare2DateUser);
            if (steamId != null) {
                ISteamUser steamUser = steamUserCache.getUserById(steamId);
                if (steamUser != null) {
                    userMap.put(dare2DateUser, steamUser);
                }
            }
        }
        return userMap;
    }

    /**
     *
     * @param dare2DateUser
     * @param steamUser Steam user object belonging to the dare2DateUser issuing the match.
     * @param steamDare2DateUsers Key value map with the keys being dare2date user id's, and their steam user object as value.
     * @return
     */
    protected abstract List<MatchEntry> findMatches(int dare2DateUser, ISteamUser steamUser, Map<Integer, ISteamUser> steamDare2DateUsers);
}
