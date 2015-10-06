package nl.dare2date.kappido.services;

import nl.dare2date.kappido.matching.MatchMaker;
import nl.dare2date.kappido.steam.SteamAPIWrapper;
import nl.dare2date.kappido.steam.SteamUserCache;
import nl.dare2date.kappido.twitch.TwitchAPIWrapper;
import nl.dare2date.kappido.twitch.TwitchUserCache;
import nl.dare2date.profile.FakeD2DProfileManager;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class KappidoEndpoint {

    private final TwitchUserCache twitchUserCache;//TODO let Spring inject an instance
    private final SteamUserCache steamUserCache;//TODO let Spring inject an instance

    public KappidoEndpoint() {
        TwitchAPIWrapper twitchAPIWrapper = new TwitchAPIWrapper();
        twitchUserCache = new TwitchUserCache(twitchAPIWrapper);
        twitchAPIWrapper.setCache(twitchUserCache);

        SteamAPIWrapper steamAPIWrapper = new SteamAPIWrapper();
        steamUserCache = new SteamUserCache(steamAPIWrapper);
        steamAPIWrapper.setCache(steamUserCache);
    }

    @PayloadRoot(localPart = "MatchRequest", namespace = "http://www.han.nl/schemas/messages")
    @ResponsePayload
    public MatchResponse calculateMatch(@RequestPayload MatchRequest req) {
        MatchMaker matchMaker = new MatchMaker(new FakeD2DProfileManager(), twitchUserCache, steamUserCache); //TODO let Spring inject an instance of MatchMaker

        List<MatchEntry> matchResults = matchMaker.findMatch(0, req.getInput().getParamList());
        MatchResult matchResult = new MatchResult();
        matchResult.getMatchedUsers().addAll(matchResults);
        MatchResponse res = new MatchResponse();
        res.setResult(matchResult);
        return res;
    }
}
