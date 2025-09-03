package pos.machine;

public class ItemsDao {
    public List<Item> findItemInfoByBarcodes(List<String> groupBarcodeMapKey) {
        List<Item> items = loadAllItems();
        List<Item> itemsInfo = new ArrayList<Item>{};
        for (item in items){
            if (groupBarcodeMapKey.contains(item.getBarcode())) {
                itemsInfo.add(item)
            }
        }
        return itemsInfo;
    }
}
