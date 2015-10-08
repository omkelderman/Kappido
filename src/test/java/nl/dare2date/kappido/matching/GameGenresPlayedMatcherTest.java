package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.FakeURLResourceProvider;
import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.steam.FakeSteamCache;
import nl.dare2date.kappido.steam.FakeSteamURLResourceProvider;
import nl.dare2date.kappido.steam.ISteamUser;
import nl.dare2date.kappido.steam.SteamAPIWrapper;
import nl.dare2date.profile.FakeD2DProfileManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maarten on 6-10-2015.
 */
public class GameGenresPlayedMatcherTest {

    private GameGenresPlayedMatcher matcher;
    private static FakeURLResourceProvider fakeUrlResourceProvider;

    @BeforeClass
    public static void initAll() {
        fakeUrlResourceProvider = new FakeSteamURLResourceProvider();
    }

    @Before
    public void init() {
        SteamAPIWrapper apiWrapper = new SteamAPIWrapper("steamapikey", fakeUrlResourceProvider);
        IUserCache<ISteamUser> userCache = new FakeSteamCache(apiWrapper);
        apiWrapper.setCache(userCache);
        matcher = new GameGenresPlayedMatcher(new FakeD2DProfileManager(), userCache);
    }

    @Test
    public void checkHasMatch() {
        List<MatchEntry> matches = matcher.findMatches(1);
        double omkeldermanProbability = 0;
        for (MatchEntry match : matches) {
            if (match.getUserId() == 1) {
                omkeldermanProbability += match.getProbability();
            }
        }
        assertEquals(0, omkeldermanProbability, 0.001);
    }

}
