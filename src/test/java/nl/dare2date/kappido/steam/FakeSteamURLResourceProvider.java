package nl.dare2date.kappido.steam;

import nl.dare2date.kappido.common.FakeURLResourceProvider;

/**
 * Created by Olle on 06-10-2015.
 */
public class FakeSteamURLResourceProvider extends FakeURLResourceProvider {
    public FakeSteamURLResourceProvider() {
        super("steam");
        registerFakeUrlHandler("http://store.steampowered.com/api/appdetails/?appids=4000", "app_4000.json");
        registerFakeUrlHandler("http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=steamapikey&steamid=76561198034641265", "ownedGames_76561198034641265.json");
    }
}
