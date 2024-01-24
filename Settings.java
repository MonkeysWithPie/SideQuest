import java.util.HashMap;

public class Settings extends HashMap<String,Integer> {
    public Settings() {
        super();
    }
    public Settings(String dataString) {
        super();
        dataString = dataString.substring(1, dataString.length()-1);
        for (String s : dataString.split(", ")) {
            put(s.substring(0, s.indexOf(":")), Integer.parseInt(s.substring(s.indexOf(":")+1)));
        }
    }
    public Settings(HashMap<String,Integer> s) {
        super(s);
    }
}
