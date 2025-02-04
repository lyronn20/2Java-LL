package models;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int storeId;
    private List<Item> items;

    public Inventory(int storeId) {
        this.storeId = storeId;
        this.items = new ArrayList<>();
    }

    public int getStoreId() {
        return storeId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }


}