package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.services.MatchEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maarten on 05-Oct-15.
 */
public class TestMatcher implements IMatcher {
    @Override
    public List<MatchEntry> findMatches(int dare2DateUser) {
        List<MatchEntry> matches = new ArrayList<>();

        MatchEntry match = new MatchEntry();
        match.setUserId(1);
        match.setProbability(0.5D);
        matches.add(match);

        match = new MatchEntry();
        match.setUserId(2);
        match.setProbability(0.3D);
        matches.add(match);

        return matches;
    }
}
