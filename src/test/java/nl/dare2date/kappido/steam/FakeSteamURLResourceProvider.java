package nl.dare2date.kappido.steam;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Olle on 2015-10-05.
 */
public class FakeSteamURLResourceProvider implements IURLResourceProvider {
    @Override
    public BufferedReader getReaderForURL(URL url) throws IOException {
        switch (url.toString()) {
            case "http://store.steampowered.com/api/appdetails/?appids=4000":
                return getSystemResourceAsBufferedReader("app_4000.json");
            case "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=steamapikey&steamid=76561198034641265":
                return getSystemResourceAsBufferedReader("ownedGames_76561198034641265.json");
            default:
                throw new RuntimeException("No unit test available for url: " + url);
        }
    }

    private static BufferedReader getSystemResourceAsBufferedReader(String filename) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}