package nl.dare2date.kappido.steam;

import com.google.gson.*;
import nl.dare2date.kappido.common.IURLResourceProvider;
import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.common.JsonAPIWrapper;
import nl.dare2date.kappido.common.URLResourceProvider;
import nl.dare2date.kappido.twitch.TwitchUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class SteamAPIWrapper extends JsonAPIWrapper implements ISteamAPIWrapper {

    private static final String GET_OWNED_GAMES_PATH = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=%s&steamid=%s";//TODO look into 'include_appinfo' parameter
    private static final String GET_GAME_DETAILS = "http://store.steampowered.com/api/appdetails/?appids=%s";//TODO find better api hook location than store.steampowered?
    private static final String STEAM_API_PATH = "SteamAPIKey.txt"; //Within the resources folder.
    private IUserCache<SteamUser> userCache;
    private final String apiKey;

    public SteamAPIWrapper() {
        this(getDefaultSteamAPIKey(), new URLResourceProvider());
    }

    public SteamAPIWrapper(String apiKey, IURLResourceProvider urlResourceProvider) {
        super(urlResourceProvider);
        this.apiKey = apiKey;
    }

    private static String getDefaultSteamAPIKey(){
        InputStream inputStream = SteamAPIWrapper.class.getClassLoader().getResourceAsStream(STEAM_API_PATH);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
            return br.readLine();
        }catch(IOException e){
            throw new IllegalStateException("No Steam API key found in the resources/SteamAPIKey.txt file!", e);
        }
    }

    /**
     * Setting the cache via a setter as opposed to via the constructor, as due to a circular reference with SteamAPIWrapper and SteamUserCache they can't set each other via the constructor.
     * @param userCache
     * @return
     */
    public void setCache(IUserCache<SteamUser> userCache){
        this.userCache = userCache;
    }

    @Override
    public List<ISteamGame> getOwnedGames(String steamId) {
        try {
            JsonObject root = getJsonForPath(String.format(GET_OWNED_GAMES_PATH, apiKey, steamId));
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
        SteamUser user = new SteamUser(steamId, this);
        if(userCache != null) userCache.addToCache(user, steamId);
        return user;
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
