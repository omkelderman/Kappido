package nl.dare2date.kappido.steam;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Olle on 2015-10-05.
 */
public class FakeSteamURLResourceProvider implements IURLResourceProvider {
    @Override
    public BufferedReader getReaderForURL(URL url) throws IOException {
        final String host = url.getHost();
        final String path = url.getPath();
        final Map<String, String> queryString = queryStringToMap(url.getQuery());

        if (host.equals("store.steampowered.com") && path.equals("/api/appdetails/") && "4000".equals(queryString.get("appids"))) {
            return getSystemResourceAsBufferedReader("app_4000.json");
        } else if (host.equals("api.steampowered.com") && path.equals("/IPlayerService/GetOwnedGames/v0001/") && "76561198034641265".equals(queryString.get("steamid"))) {
            return getSystemResourceAsBufferedReader("ownedGames_76561198034641265.json");
        } else {
            throw new RuntimeException("No unit test available for url: " + url);
        }
    }

    private static BufferedReader getSystemResourceAsBufferedReader(String filename) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private static Map<String, String> queryStringToMap(String queryString) {
        Map<String, String> map = new HashMap<>();
        for (String keyValue : queryString.split("&")) {
            String[] tmp = keyValue.split("=", 2);
            map.put(tmp[0], tmp[1]);
        }
        return map;
    }
}