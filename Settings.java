import java.util.HashMap;

public class Settings extends HashMap<String,Integer> {
    private HashMap<String,Integer> settings;

    public Settings() {
        settings = new HashMap<>();
    }
    public Settings(String dataString) {

    }
    public Settings(HashMap<String,Integer> s) {
        settings = s;
    }

    public HashMap<String, Integer> getSettings() { return settings; }
    public void setSettings(HashMap<String,Integer> s) { settings = s; }
    
}
