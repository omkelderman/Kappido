package nl.dare2date.kappido.twitch;

import nl.dare2date.kappido.common.FakeURLResourceProvider;

/**
 * Created by Olle on 2015-10-05.
 */
public class FakeTwitchURLResourceProvider extends FakeURLResourceProvider {
    public FakeTwitchURLResourceProvider() {
        super("twitch");
        registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=0&sortby=created_at", "staiain_following_0-99.json");
        registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=100&sortby=created_at", "staiain_following_100-199.json");
        registerFakeUrlHandler("https://api.twitch.tv/kraken/users/staiain/follows/channels?direction=DESC&limit=100&offset=200&sortby=created_at", "staiain_following_200-229.json");
        registerFakeUrlHandler("https://api.twitch.tv/kraken/channels/staiain", "staiain_channel.json");
    }
}
