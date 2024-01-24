import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {
    public Inventory() {
        super();
    }

    public Inventory(ArrayList<Item> i) {
        super(i);
    }

    /**
     * @param name  name of the item to change
     * @param toAdd amount to add (also supports negatives)
     */
    public void addItem(String name, int toAdd) {
        if (getItem(name) != -1) {
            setItem(name, (getItem(name) + toAdd));
        } else {
            setItem(name, toAdd);
        }
    }

    /**
     * @param itemName name to look for
     * @return stack size of that item (-1 if not found)
     */
    public int getItem(String name) {
        for (int i = 0; i < size(); i++) {
            if (get(i) == null) {
                continue;
            }
            if (get(i).getItem().equals(name)) {
                return get(i).getStack();
            }
        }
        return -1;
    }

    /**
     * @param name  name of the item to change
     * @param value sets the stack of the item to this
     */
    public void setItem(String name, int value) {
        for (int i = 0; i < size(); i++) {
            if (get(i) == null) {
                continue;
            }
            if (get(i).getItem().equals(name)) {
                set(i, new Item(name + ";" + value));
                return;
            }
        }
        add(new Item(name + ";" + value));
    }
}
