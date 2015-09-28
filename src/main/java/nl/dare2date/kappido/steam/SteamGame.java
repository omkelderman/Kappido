package nl.dare2date.kappido.steam;

import java.util.List;

/**
 * Created by Maarten on 28-9-2015.
 */
public class SteamGame implements ISteamGame{
    private final String appId;
    private String name;
    private List<String> genres;

    public SteamGame(String appId){
        this.appId = appId;
    }

    @Override
    public String getId() {
        return appId;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        if(name == null) new SteamAPIWrapper().addGameDetails(this);
        return name;
    }

    @Override
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public List<String> getGenres() {
        if(genres == null) new SteamAPIWrapper().addGameDetails(this);
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SteamGame)) return false;
        SteamGame steamGame = (SteamGame) o;
        return appId.equals(steamGame.appId);
    }

    @Override
    public int hashCode() {
        return appId.hashCode();
    }
}
