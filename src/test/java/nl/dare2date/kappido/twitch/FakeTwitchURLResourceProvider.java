package nl.dare2date.kappido.twitch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Olle on 2015-10-05.
 */
public class FakeTwitchURLResourceProvider implements IURLResourceProvider {
    @Override
    public BufferedReader getReaderForURL(URL url) throws IOException {
        switch (url.toString()) {
            case "https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=0&sortby=created_at":
                return getSystemResourceAsBufferedReader("staiain_following_0-99.json");
            case "https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=100&sortby=created_at":
                return getSystemResourceAsBufferedReader("staiain_following_100-199.json");
            case "https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=200&sortby=created_at":
                return getSystemResourceAsBufferedReader("staiain_following_200-229.json");
            case "https://api.twitch.tv/kraken/channels/staiain":
                return getSystemResourceAsBufferedReader("staiain_channel.json");
            default:
                throw new RuntimeException("No unit test available for url: " + url);
        }
    }

    private static BufferedReader getSystemResourceAsBufferedReader(String filename) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
