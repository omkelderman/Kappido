package nl.dare2date.kappido.steam;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class SteamAPIWrapper implements ISteamAPIWrapper {

    private static final String GET_OWNED_GAMES_PATH = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=%s&steamid=%s";//TODO look into 'include_appinfo' parameter
    private static final String GET_GAME_DETAILS = "http://store.steampowered.com/api/appdetails/?appids=%s";//TODO find better api hook location than store.steampowered?
    private static final String STEAM_API_PATH = "SteamAPIKey.txt"; //Within the resources folder.
    private static final String STEAM_API_KEY;

    private IURLResourceProvider urlResourceProvider;

    public SteamAPIWrapper() {
        this(new URLResourceProvider());
    }

    public SteamAPIWrapper(IURLResourceProvider urlResourceProvider) {
        this.urlResourceProvider = urlResourceProvider;
    }

    static{
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(STEAM_API_PATH);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            STEAM_API_KEY = br.readLine();
        }catch (IOException e) {
            throw new RuntimeException(e); // TODO handle properly
        }
    }

    @Override
    public List<ISteamGame> getOwnedGames(String steamId) {
        try {
            JsonObject root = getJsonForPath(String.format(GET_OWNED_GAMES_PATH, STEAM_API_KEY, steamId));
            JsonArray followingUserArray = root.get("response").getAsJsonObject().get("games").getAsJsonArray();
            List<ISteamGame> ownedGames = new ArrayList<>();
            for(JsonElement ownedGameElement : followingUserArray){
                JsonObject ownedGameObject = ownedGameElement.getAsJsonObject();
                ownedGames.add(new SteamGame(ownedGameObject.get("appid").getAsString(), this));//TODO utilize the 'playtime_forever' key.
            }
            return ownedGames;
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO handle properly
        }
    }

    @Override
    public ISteamUser getUser(String steamId){
        return new SteamUser(steamId, this);
    }

    @Override
    public void addGameDetails(ISteamGame game){
        try {
            JsonObject root = getJsonForPath(String.format(GET_GAME_DETAILS, game.getId()));
            JsonObject gameDetails = root.get(game.getId()).getAsJsonObject().get("data").getAsJsonObject();
            game.setName(gameDetails.get("name").getAsString());
            List<String> genres = new ArrayList<>();
            for(JsonElement genreElement : gameDetails.get("genres").getAsJsonArray()){
                genres.add(genreElement.getAsJsonObject().get("description").getAsString());//TODO maybe use 'id' instead of 'description'?
            }
            game.setGenres(genres);
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO handle properly
        }
    }

    private JsonObject getJsonForPath(String path) throws IOException{
        URL url = new URL(path);
        try (BufferedReader reader = urlResourceProvider.getReaderForURL(url)) {
            return new JsonParser().parse(reader).getAsJsonObject();
        }
    }

    /**
     * Utility method
     * TODO Move/Delete
     * @param element
     */
    private void printJson(JsonElement element){
        JsonParser parser = new JsonParser();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonElement el = parser.parse(element.toString());
        System.out.println(gson.toJson(el));
    }
}
