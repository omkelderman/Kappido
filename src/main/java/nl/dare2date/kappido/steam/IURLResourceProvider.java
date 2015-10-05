package nl.dare2date.kappido.steam;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Olle on 2015-10-05.
 */
public interface IURLResourceProvider {
    BufferedReader getReaderForURL(URL url) throws IOException;
}
