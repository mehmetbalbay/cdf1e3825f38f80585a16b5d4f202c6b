package mehmetbalbay.spaceApp.utils;

import com.orhanobut.hawk.Hawk;

public class SharedPreferenceHelper {

    public static <T> void saveSharedData(String key, T value) {
        Hawk.put(key, value);
    }

    public static <T> T getSharedData(String key) {
        if (Hawk.get(key) != null) {
            return Hawk.get(key);
        }
        return null;
    }

    public static void allDeletePref() {
        Hawk.deleteAll();
    }
}
