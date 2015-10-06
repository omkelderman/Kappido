package nl.dare2date.kappido.common;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Olle on 05-10-2015.
 */
public class FakeURLResourceProvider implements IURLResourceProvider {
    private final String resourceFolder;
    private final Map<String, String> fakeUrlHandlers;

    public FakeURLResourceProvider(String resourceFolder) {
        this.resourceFolder = resourceFolder;
        this.fakeUrlHandlers = new HashMap<>();
    }

    public void registerFakeUrlHandler(String url, String filename) {
        String path = resourceFolder + File.separatorChar + filename;
        fakeUrlHandlers.put(url, path);
    }

    @Override
    public BufferedReader getReaderForURL(URL url) throws IOException {
        String resourcePath = fakeUrlHandlers.get(url.toString());
        if (resourcePath == null) {
            throw new RuntimeException("No fake handler available for url: " + url);
        } else {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourcePath);
            return new BufferedReader(new InputStreamReader(inputStream));
        }
    }
}
