package nl.dare2date.kappido.steam;

import java.util.List;

/**
 * Created by Maarten on 28-9-2015.
 */
public interface ISteamGame {
    String getId();

    void setName(String name);

    String getName();

    void setGenreIds(List<String> genres);

    List<String> getGenreIds();
}
