package nl.dare2date.kappido.services;

import nl.dare2date.kappido.matching.MatchMaker;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class KappidoEndpoint {

    public KappidoEndpoint() {
    }

    @PayloadRoot(localPart = "MatchRequest", namespace = "http://www.han.nl/schemas/messages")
    @ResponsePayload
    public MatchResponse calculateMatch(@RequestPayload MatchRequest req) {
        MatchMaker matchMaker = new MatchMaker(); //TODO let Spring inject an instance of MatchMaker
        List<MatchEntry> matchResults = matchMaker.findMatch(0, req.getInput().getParamList());
        MatchResult matchResult = new MatchResult();
        matchResult.getMatchedUsers().addAll(matchResults);
        MatchResponse res = new MatchResponse();
        res.setResult(matchResult);
        return res;
    }
}
