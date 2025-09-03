package pos.machine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pos.machine.ItemsLoader.loadAllItems;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        return null;
    }


    private Map<String, Integer> getGroupedBarcodeMaps(List<String> barcodes) {
        /**
         * 将条形码分组，并统计每个条形码出现的次数
         * @param barcodes 条形码列表
         * @return 分组后的条形码映射，键为条形码，值为出现次数
         */
        Map<String, Integer> groupedBarcodeMaps = new LinkedHashMap<>();
        for (String barcode : barcodes) {
            if (groupedBarcodeMaps.containsKey(barcode)) {
                groupedBarcodeMaps.put(barcode, groupedBarcodeMaps.get(barcode) + 1);
            } else {
                groupedBarcodeMaps.put(barcode, 1);
            }
        }
        return groupedBarcodeMaps;
    }

    private boolean existUnknownBarcode(List<String> groupedBarcodeMapKeys) {
        List<Item> items = loadAllItems();
        List<String> allBarcodes = items.stream().map(Item::getBarcode).collect(Collectors.toList());
        for (String barcode : groupedBarcodeMapKeys) {
            if (!allBarcodes.contains(barcode)) {
                return true;
            }
        }
        return false;
    }


}
