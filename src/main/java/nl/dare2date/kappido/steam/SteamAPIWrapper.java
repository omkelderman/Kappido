package nl.dare2date.kappido.steam;

import com.google.gson.*;
import nl.dare2date.kappido.common.IURLResourceProvider;
import nl.dare2date.kappido.common.JsonAPIWrapper;
import nl.dare2date.kappido.common.URLResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class SteamAPIWrapper extends JsonAPIWrapper implements ISteamAPIWrapper {

    private static final String GET_OWNED_GAMES_PATH = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=%s&steamid=%s";//TODO look into 'include_appinfo' parameter
    private static final String GET_GAME_DETAILS = "http://store.steampowered.com/api/appdetails/?appids=%s";//TODO find better api hook location than store.steampowered?

    private final String apiKey;

    public SteamAPIWrapper(String apiKey) {
        this(apiKey, new URLResourceProvider());
    }

    public SteamAPIWrapper(String apiKey, IURLResourceProvider urlResourceProvider) {
        super(urlResourceProvider);
        this.apiKey = apiKey;
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
