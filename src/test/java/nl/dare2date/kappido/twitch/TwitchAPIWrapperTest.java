package nl.dare2date.kappido.twitch;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class TwitchAPIWrapperTest {

    @Before
    public void initialize(){

    }

    @Test
    public void checkUserRetrieval() throws Exception{
        ITwitchUser user = new TwitchAPIWrapper().getUser("MineMaarten");
        assertEquals(user, new TwitchUser("MineMaarten"));
    }

    @Test
    public void checkUserFollowsOtherUser() throws Exception{
        List<ITwitchUser> users = new TwitchAPIWrapper().getFollowingUsers("MineMaarten");
        assertTrue(users.contains(new TwitchUser("ohaiichun")));
    }
}
