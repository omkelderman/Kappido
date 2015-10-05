package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.services.MatchEntry;

import java.util.List;

/**
 * Created by Maarten on 05-Oct-15.
 */
public interface IMatchType {

    /**
     *
     * @param dare2DateUser
     * @return A map of which the keys are Dare2Date user id's, and the values the match probability of the users.
     */
    List<MatchEntry> findMatches(int dare2DateUser);
}
