package nl.dare2date.profile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Maarten on 05-Oct-15.
 */
public class FakeD2DProfileManager implements ID2DProfileManager {
    private static Map<Integer, FakeD2DUser> users;

    public FakeD2DProfileManager() {
        InputStream fakeUsersResource = FakeD2DProfileManager.class.getClassLoader().getResourceAsStream("fakeUsers.json");
        if (fakeUsersResource == null) throw new IllegalStateException("Could not find the fake users json-file!");
        Gson gson = new Gson();

        users = new HashMap<>();
        Type fakeD2DUserListType = new TypeToken<List<FakeD2DUser>>() {
        }.getType();
        ArrayList<FakeD2DUser> userList = gson.fromJson(new InputStreamReader(fakeUsersResource), fakeD2DUserListType);
        for (FakeD2DUser user : userList) {
            users.put(user.getUserId(), user);
        }
    }

    @Override
    public String getTwitchId(int dare2DateUserId) {
        FakeD2DUser user = users.get(dare2DateUserId);
        return user == null ? null : user.getTwitchId();
    }

    @Override
    public String getSteamId(int dare2DateUserId) {
        FakeD2DUser user = users.get(dare2DateUserId);
        return user == null ? null : user.getSteamId();
    }

    @Override
    public Set<Integer> getAllUsers() {
        return users.keySet();
    }
}
