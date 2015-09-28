package nl.dare2date.kappido.steam;

import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by Maarten on 28-9-2015.
 */
public class SteamAPIWrapperTest {
    private static final String MINEMAARTEN_STEAM_ID = "76561198034641265";
    private static final String GARRYS_MOD_APP_ID = "4000";

    @Test
    public void checkSteamAPIKey(){
        new SteamAPIWrapper();
    }

    @Test
    public void checkUserRetrieval(){
        ISteamUser user = new SteamAPIWrapper().getUser(MINEMAARTEN_STEAM_ID);
        List<ISteamGame> ownedGames = user.getOwnedGames();
        assertTrue(ownedGames.contains(new SteamGame(GARRYS_MOD_APP_ID)));
    }

    @Test
    public void checkGameDetails(){
        SteamGame garrysMod = new SteamGame(GARRYS_MOD_APP_ID);
        assertEquals(garrysMod.getName(), "Garry's Mod");
        assertTrue(garrysMod.getGenres().contains("Simulation"));
        assertTrue(garrysMod.getGenres().contains("Indie"));
    }
}