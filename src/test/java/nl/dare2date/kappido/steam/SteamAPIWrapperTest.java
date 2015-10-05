package nl.dare2date.kappido.steam;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maarten on 28-9-2015.
 */
public class SteamAPIWrapperTest {
    private static final String MINEMAARTEN_STEAM_ID = "76561198034641265";
    private static final String GARRYS_MOD_APP_ID = "4000";
    private static final String STEAM_API_PATH = "SteamAPIKey.txt"; //Within the resources folder.

    SteamAPIWrapper steamAPIWrapper;

    @Before
    public void init() {
        final String apiKey;
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(STEAM_API_PATH);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            apiKey = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO handle properly
        }
        steamAPIWrapper = new SteamAPIWrapper(apiKey, new FakeSteamURLResourceProvider());
    }

    @Test
    public void checkUserRetrieval(){
        ISteamUser user = steamAPIWrapper.getUser(MINEMAARTEN_STEAM_ID);
        List<ISteamGame> ownedGames = user.getOwnedGames();
        assertTrue(ownedGames.contains(new SteamGame(GARRYS_MOD_APP_ID, steamAPIWrapper)));
    }

    @Test
    public void checkGameDetails(){
        SteamGame garrysMod = new SteamGame(GARRYS_MOD_APP_ID, steamAPIWrapper);
        assertEquals(garrysMod.getName(), "Garry's Mod");
        assertTrue(garrysMod.getGenres().contains("Simulation"));
        assertTrue(garrysMod.getGenres().contains("Indie"));
    }
}
