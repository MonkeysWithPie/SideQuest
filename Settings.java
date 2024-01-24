import java.util.HashMap;

public class Settings extends HashMap<String, Integer> {
    static final int[] MAX_SETTING_VALUES = { 1, 5 };
    static final String[] MAX_SETTINGS_STRINGS = { "Debug mode", "testing setting" };

    public Settings() {
        super();
    }

    public Settings(String dataString) {
        super();
        dataString = dataString.substring(1, dataString.length() - 1);
        for (String s : dataString.split(", ")) {
            put(s.substring(0, s.indexOf(":")), Integer.parseInt(s.substring(s.indexOf(":") + 1)));
        }
    }

    public Settings(HashMap<String, Integer> s) {
        super(s);
    }

    int checkIndexOfSetting(String s) {
        for (int i = 0; i < MAX_SETTINGS_STRINGS.length; i++) {
            if (MAX_SETTINGS_STRINGS[i].equals(s))
                return i;
        }
        return -1;
    }

    int checkMaxOfSetting(String s) {
        return MAX_SETTING_VALUES[checkIndexOfSetting(s)];
    }

    public void updateSetting(String setting, int newVal) {
        if (checkIndexOfSetting(setting) == -1) {
            return;
        }
        if (newVal > checkMaxOfSetting(setting))
            newVal = checkMaxOfSetting(setting);
        if (newVal < 0)
            newVal = 0;
        put(setting, newVal);
    }

    public void fixInvalidSettings() {
        for (String s : MAX_SETTINGS_STRINGS) {
            if (!containsKey(s))
                put(s, 0);
        }
    }
}
