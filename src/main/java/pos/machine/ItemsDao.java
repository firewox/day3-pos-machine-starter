package pos.machine;

import java.util.ArrayList;
import java.util.List;

public class ItemsDao {
    private final List<Item> items = loadAllItems();
    public ItemsDao() {}
    public List<Item> findItemInfoByBarcodes(List<String> groupBarcodeMapKey) {
        List<Item> itemsInfo = new ArrayList<>();
        for (Item item : this.items){
            if (groupBarcodeMapKey.contains(item.getBarcode())) {
                itemsInfo.add(item);
            }
        }
        return itemsInfo;
    }

    public List<Item> getItems() {
        return items;
    }

    private static List<Item> loadAllItems() {
        Item item1 = new Item("ITEM000000", "Coca-Cola", 3);
        Item item2 = new Item("ITEM000001", "Sprite", 3);
        Item item3 = new Item("ITEM000004", "Battery", 2);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        return itemList;
    }
}
