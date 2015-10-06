package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.FakeURLResourceProvider;
import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.steam.FakeSteamCache;
import nl.dare2date.kappido.steam.FakeSteamURLResourceProvider;
import nl.dare2date.kappido.steam.SteamAPIWrapper;
import nl.dare2date.kappido.steam.SteamUser;
import nl.dare2date.profile.FakeD2DProfileManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maarten on 6-10-2015.
 */
public class GamePlayedMatcherTest {

    private GamesPlayedMatcher matcher;
    private static FakeURLResourceProvider fakeUrlResourceProvider;

    @BeforeClass
    public static void initAll() {
        fakeUrlResourceProvider = new FakeSteamURLResourceProvider();
    }

    @Before
    public void init() {
        SteamAPIWrapper apiWrapper = new SteamAPIWrapper("steamapikey", fakeUrlResourceProvider);
        IUserCache<SteamUser> userCache = new FakeSteamCache(apiWrapper);
        apiWrapper.setCache(userCache);
        matcher = new GamesPlayedMatcher(new FakeD2DProfileManager(), userCache);
    }

    @Test
    public void checkHasMatchForOmkelderman() {
        List<MatchEntry> matches = matcher.findMatches(UserIDs.STEAM_OMKELDERMAN);
        double minemaartenProbability = 0;
        double xikeonProbability = 0;
        for (MatchEntry match : matches) {
            switch (match.getUserId()) {
                case UserIDs.STEAM_MINEMAARTEN:
                    minemaartenProbability += match.getProbability();
                    break;
                case UserIDs.STEAM_XIKEON:
                    xikeonProbability += match.getProbability();
                    break;
            }
        }
        assertEquals(2, minemaartenProbability, 0.001);
        assertEquals(44, xikeonProbability, 0.001);
    }

    @Test
    public void checkHasMatchForMineMaarten() {
        List<MatchEntry> matches = matcher.findMatches(UserIDs.STEAM_MINEMAARTEN);
        double quetzProbability = 0;
        double happystickProbability = 0;
        for (MatchEntry match : matches) {
            switch (match.getUserId()) {
                case UserIDs.STEAM_QUETZ:
                    quetzProbability += match.getProbability();
                    break;
                case UserIDs.STEAM_HAPPYSTICK:
                    happystickProbability += match.getProbability();
                    break;
            }
        }
        assertEquals(3, quetzProbability, 0.001);
        assertEquals(2, happystickProbability, 0.001);
    }
}
