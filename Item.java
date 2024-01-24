public class Item {
    public String item;
    public int count;

    public Item() {
        item = "Air";
        count = 0;
    }

    /**
     * Creates an Item from a data string.
     * 
     * @param arg The data string to use.
     *            Should be formatted {@code itemName;count} (ex. "Air;0")
     */
    public Item(String arg) {
        item = arg.substring(0, arg.indexOf(";"));
        count = Integer.parseInt(arg.substring(arg.indexOf(";") + 1));
    }

    /**
     * @param i the item's name
     * @param c count of the item
     */
    public Item(String i, int c) {
        item = i;
        count = c;
    }

    public String toString() {
        return item + " x" + count;
    }

    public String toDataString() {
        return item + ";" + count;
    }

    public String getItem() {
        return item;
    }

    public int getStack() {
        return count;
    }

    public void setItem(String i) {
        item = i;
    }

    public void setStack(int i) {
        count = i;
    }
}