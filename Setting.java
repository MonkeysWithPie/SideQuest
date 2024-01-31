public class Setting implements Comparable<Object> {
    public static final int[] MAX_SETTING_VALUES = { 1, 5 };
    public static final String[] MAX_SETTING_STRINGS = { "Debug mode", "testing setting" };

    private String name;
    private int maxVal;
    private int curVal;
    private boolean isBool;

    public Setting() {
        this("");
    }
    public Setting(String name) {
        this.name = name;
        maxVal = 0;
        curVal = 0;
        verify();
    }
    public Setting(String name, int cur) {
        this.name = name;
        maxVal = 0;
        curVal = cur;
        verify();
    }
    public Setting(String name, int max, int cur) {
        this.name = name;
        maxVal = max;
        curVal = cur;
        verify();
    }
    
    public String getName() { return name; }
    public int getMaxVal() { return maxVal; }
    public int getCurVal() { return curVal; }
    public void setName(String n) { name = n; }
    public void setMaxVal(int m) { maxVal = m; verify(); }
    public void setCurVal(int c) { curVal = c; verify(); }

    public void verify() {
        if (curVal > maxVal) { curVal = maxVal; }
        if (curVal < 0) { curVal = 0; }
        if (maxVal == 1) { isBool = true; }
        for (int i = 0; i < MAX_SETTING_STRINGS.length; i++) {
            if (name.equals(MAX_SETTING_STRINGS[i]) && !(maxVal == MAX_SETTING_VALUES[i])) {
                maxVal = MAX_SETTING_VALUES[i];
                verify();
                return;
            }
        }
    }

    public String toString() {
        if (isBool) if (curVal==1) return name + " Enabled"; else return name + " Disabled";
        return name + " "+curVal+"/"+maxVal;
    }

    public int compareTo(Object obj) {
        Setting object = (Setting) obj;
        return (object.getName().compareTo(name));
    }
}
