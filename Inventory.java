import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {
    private ArrayList<Item> inv;

    public Inventory() {
        inv = new ArrayList<>();
    }

    public Inventory(ArrayList<Item> i) {
        inv = i;
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
        for (int i = 0; i < inv.size(); i++) {
            if (inv.get(i) == null) {
                continue;
            }
            if (inv.get(i).getItem().equals(name)) {
                return inv.get(i).getStack();
            }
        }
        return -1;
    }

    /**
     * @param name  name of the item to change
     * @param value sets the stack of the item to this
     */
    public void setItem(String name, int value) {
        for (int i = 0; i < inv.size(); i++) {
            if (inv.get(i) == null) {
                continue;
            }
            if (inv.get(i).getItem().equals(name)) {
                inv.set(i, new Item(name + ";" + value));
                return;
            }
        }
        inv.add(new Item(name + ";" + value));
    }
}
