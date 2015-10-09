package nl.dare2date.profile;

/**
 * Created by Olle on 06-10-2015.
 * Class that describes a Dare2Date user. Is instantiated only by GSON.
 */
public class FakeD2DUser {
    private int userId;
    private String twitchId;
    private String steamId;

    public int getUserId() {
        return userId;
    }

    public String getSteamId() {
        return steamId;
    }

    public String getTwitchId() {
        return twitchId;
    }

    /**
     * A specific toString() return to allow GSON to deserialize and create this object.
     * @return
     */
    @Override
    public String toString() {
        return "FakeD2DUser{" +
                "userId=" + userId +
                ", twitchId='" + twitchId + '\'' +
                ", steamId='" + steamId + '\'' +
                '}';
    }
}
