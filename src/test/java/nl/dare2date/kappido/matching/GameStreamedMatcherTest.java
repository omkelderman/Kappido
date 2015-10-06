package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.FakeURLResourceProvider;
import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.twitch.FakeTwitchCache;
import nl.dare2date.kappido.twitch.TwitchAPIWrapper;
import nl.dare2date.kappido.twitch.TwitchUser;
import nl.dare2date.profile.FakeD2DProfileManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maarten on 6-10-2015.
 */
public class GameStreamedMatcherTest {

    private GamesStreamedMatcher matcher;
    private static FakeURLResourceProvider fakeUrlResourceProvider;

    @BeforeClass
    public static void initAll() {
        fakeUrlResourceProvider = new FakeURLResourceProvider("twitch");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=0&sortby=created_at", "staiain_following_0-99.json");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=100&sortby=created_at", "staiain_following_100-199.json");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=200&sortby=created_at", "staiain_following_200-229.json");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/channels/staiain", "staiain_channel.json");

        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/users/omkelderman/follows/channels?direction=DESC&limit=100&offset=0&sortby=created_at", "omkelderman_following_0-48.json");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/channels/omkelderman", "omkelderman_channel.json");
    }

    @Before
    public void init(){
        TwitchAPIWrapper apiWrapper = new TwitchAPIWrapper(fakeUrlResourceProvider);
        IUserCache<TwitchUser> userCache = new FakeTwitchCache(apiWrapper);
        apiWrapper.setCache(userCache);
        matcher = new GamesStreamedMatcher(new FakeD2DProfileManager(), userCache);
    }

    @Test
    public void checkHasMatch(){
        List<MatchEntry> matches = matcher.findMatches(0);
        double omKeldermanProbability = 0;
        for(MatchEntry match : matches){
            if(match.getUserId() == 1) {
                omKeldermanProbability += match.getProbability();
            }
        }
        assertEquals(0, omKeldermanProbability, 0.001);
    }

}
