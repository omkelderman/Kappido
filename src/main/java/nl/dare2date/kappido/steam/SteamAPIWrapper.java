package nl.dare2date.kappido.steam;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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

    static{
        try {
            InputStream inputStream = SteamAPIWrapper.class.getClassLoader().getResourceAsStream(STEAM_API_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            STEAM_API_KEY = br.readLine();
            inputStream.close();
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
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
                ownedGames.add(new SteamGame(ownedGameObject.get("appid").getAsString()));//TODO utilize the 'playtime_forever' key.
            }
            return ownedGames;
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO handle properly
        }
    }

    @Override
    public ISteamUser getUser(String steamId){
        return new SteamUser(steamId);
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        JsonObject root =  new JsonParser().parse(reader).getAsJsonObject();
        reader.close(); //TODO close stream properly when exception thrown
        return root;
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
