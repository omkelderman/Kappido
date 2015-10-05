package nl.dare2date.kappido.twitch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Olle on 2015-10-05.
 */
public class URLResourceProvider implements IURLResourceProvider {
    @Override
    public BufferedReader getReaderForURL(URL url) throws IOException {
        return new BufferedReader(new InputStreamReader(url.openStream()));
    }
}
