package nl.dare2date.kappido.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Olle on 05-10-2015.
 */
public abstract class JsonAPIWrapper {
    private IURLResourceProvider urlResourceProvider;

    public JsonAPIWrapper(IURLResourceProvider urlResourceProvider) {
        this.urlResourceProvider = urlResourceProvider;
    }

    protected JsonObject getJsonForPath(String path) throws IOException {
        URL url = new URL(path);
        try (BufferedReader reader = urlResourceProvider.getReaderForURL(url)) {
            return new JsonParser().parse(reader).getAsJsonObject();
        }
    }
}
