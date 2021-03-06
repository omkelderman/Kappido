package nl.dare2date.kappido.services;

import nl.dare2date.kappido.matching.MatchMaker;
import nl.dare2date.kappido.steam.ISteamAPIWrapper;
import nl.dare2date.kappido.steam.SteamUserCache;
import nl.dare2date.kappido.twitch.ITwitchAPIWrapper;
import nl.dare2date.kappido.twitch.TwitchUserCache;
import nl.dare2date.profile.ID2DProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * The spring-ws endpoint used by this application
 */
@Endpoint
public class KappidoEndpoint {
    private final MatchMaker matchMaker;

    /**
     * Creates the endpoint with various needed objects
     *
     * @param profileManager   The profile manager to use
     * @param twitchAPIWrapper The twitch-API-wrapper to use
     * @param steamAPIWrapper  The steam-API-wrapper to use
     */
    @Autowired
    public KappidoEndpoint(ID2DProfileManager profileManager, ITwitchAPIWrapper twitchAPIWrapper, ISteamAPIWrapper steamAPIWrapper) {
        TwitchUserCache twitchUserCache = new TwitchUserCache(twitchAPIWrapper);
        twitchAPIWrapper.setCache(twitchUserCache);

        SteamUserCache steamUserCache = new SteamUserCache(steamAPIWrapper);
        steamAPIWrapper.setCache(steamUserCache);

        matchMaker = new MatchMaker(profileManager, twitchUserCache, steamUserCache);
    }

    /**
     * Handles the MatchRequest-SOAP-message
     *
     * @param req The request object containing the request-parameters
     * @return The response-object used to create a SOAP-response
     */
    @PayloadRoot(localPart = "MatchRequest", namespace = "http://www.han.nl/schemas/messages")
    @ResponsePayload
    public MatchResponse calculateMatch(@RequestPayload MatchRequest req) {
        MatchInput matchInput = req.getInput();
        List<MatchEntry> matchResults = matchMaker.findMatch(matchInput.getUserId(), matchInput.getParamList());
        MatchResult matchResult = new MatchResult();
        matchResult.getMatchedUsers().addAll(matchResults);
        MatchResponse res = new MatchResponse();
        res.setResult(matchResult);
        return res;
    }
}
