package nl.dare2date.kappido.services;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class KappidoEndpoint {

    public KappidoEndpoint() {
    }

    @PayloadRoot(localPart = "MatchRequest", namespace = "http://www.han.nl/schemas/messages")
    @ResponsePayload
    public MatchResponse calculateMatch(@RequestPayload MatchRequest req) {
        MatchResponse res = new MatchResponse();
        return res;
    }
}
