import java.util.HashMap;

public class Settings extends HashMap<String,Integer> {
    private HashMap<String,Integer> settings;

    public Settings() {
        settings = new HashMap<>();
    }
    public Settings(String dataString) {
        settings = new HashMap<>();
        dataString = dataString.substring(1, dataString.length()-1);
        for (String s : dataString.split(", ")) {
            settings.put(s.substring(0, s.indexOf(":")), Integer.parseInt(s.substring(s.indexOf(":")+1)));
        }
    }
    public Settings(HashMap<String,Integer> s) {
        settings = s;
    }

    public HashMap<String, Integer> getSettings() { return settings; }
    public void setSettings(HashMap<String,Integer> s) { settings = s; }
    public String toString() { return settings.toString(); }
}
