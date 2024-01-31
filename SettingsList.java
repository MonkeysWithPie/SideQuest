import java.util.ArrayList;
import java.util.Collections;
public class SettingsList extends ArrayList<Setting> {
    public SettingsList() {
        super();
    }
    public SettingsList(String dataString) {
        super();
        dataString = dataString.substring(1, dataString.length() - 1);
        for (String s : dataString.split(", ")) {
            add(new Setting (s.substring(0, s.indexOf(":")), Integer.parseInt(s.substring(s.indexOf(":") + 1))));
        }
    }
    public SettingsList(ArrayList<Setting> s) {
        super(s);
    }

    public void refresh() {
        for (Setting s : this) {
            s.verify();
        }
        Collections.sort(this, Collections.reverseOrder());
    }

    public Setting get(String name) {
        for (Setting s : this) { // TODO i think binary search is faster so add that maybe
            if (s.getName().equals(name)) return s;
        }
        add(new Setting(name, 0));
        refresh();
        return new Setting(name, 0);
    }

    public String toString() {
        String returned = "";
        for (int i = 0; i < size(); i++) {
            returned = returned + "\n"+(i+1)+". "+get(i);
        }
        return returned;
    }
}