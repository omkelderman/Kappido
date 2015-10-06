package nl.dare2date.kappido.twitch;

import nl.dare2date.kappido.common.FakeURLResourceProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class TwitchAPIWrapperTest {

    private TwitchAPIWrapper twitchAPIWrapper;
    private static FakeURLResourceProvider fakeUrlResourceProvider;

    @BeforeClass
    public static void initAll() {
        fakeUrlResourceProvider = new FakeURLResourceProvider("twitch");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=0&sortby=created_at", "staiain_following_0-99.json");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=100&sortby=created_at", "staiain_following_100-199.json");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=200&sortby=created_at", "staiain_following_200-229.json");
        fakeUrlResourceProvider.registerFakeUrlHandler("https://api.twitch.tv/kraken/channels/staiain", "staiain_channel.json");
    }

    @Before
    public void initialize() {
        twitchAPIWrapper = new TwitchAPIWrapper(fakeUrlResourceProvider);
    }

    @Test
    public void checkUserRetrieval() throws Exception {
        ITwitchUser user = twitchAPIWrapper.getUser("staiain");
        assertEquals(user, new TwitchUser("staiain"));
    }

    @Test
    public void checkUserFollowsOtherUser() throws Exception {
        List<ITwitchUser> users = twitchAPIWrapper.getFollowingUsers("staiain");
        assertTrue(users.contains(new TwitchUser("kommisar"))); // 0-99|0
        assertTrue(users.contains(new TwitchUser("Woddles"))); // 0-99|42
        assertTrue(users.contains(new TwitchUser("denkyu"))); // 100-199|0
        assertTrue(users.contains(new TwitchUser("konnochan"))); // 100-199|42
        assertTrue(users.contains(new TwitchUser("PortalLifu"))); // 200-299|0
        assertTrue(users.contains(new TwitchUser("linkmaster500"))); // 200-229|28
    }
}
