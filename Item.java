public class Item {
    public String item;
    public int count;

    public Item() {
        item = "Air";
        count = 0;
    }
    public Item(String arg) {
        
    }

    public Item(String i, int c) {
        item = i;
        count = c;
    }

    public String toString() {
        return item + " x" + count;
    }
}