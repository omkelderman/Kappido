package nl.dare2date.profile;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Maarten on 05-Oct-15.
 */
public class FakeD2DProfileManager implements ID2DProfileManager {
    @Override
    public String getTwitchId(int dare2DateUserId) {
        switch(dare2DateUserId){
            case 0:
                return "MineMaarten";
            case 1:
                return "omkelderman";
        }
        return null;
    }

    @Override
    public String getSteamId(int dare2DateUserId) {
        switch(dare2DateUserId){
            case 0:
                return "76561198034641265";
        }
        return null;
    }

    @Override
    public List<Integer> getAllUsers() {
        return Arrays.asList(0, 1);
    }
}
