package pos.machine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) throws Exception {
        Map<String,Integer> groupedBarcodeMaps = getGroupBarcodeMaps(barcodes);
        if (existUnknownBarcode(new ArrayList<>(groupedBarcodeMaps.keySet()))) {
            throw new Exception("unknown barcode");
        }
        List<ReceiptInfo> receiptInfoList = calculateReceiptInfoByBarcodes(groupedBarcodeMaps);
        return getReceiptWithFormat(receiptInfoList);
    }


    private Map<String, Integer> getGroupBarcodeMaps(List<String> barcodes) {
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
        List<Item> items = new ItemsDao().getItems();
        List<String> allBarcodes = items.stream().map(Item::getBarcode).collect(Collectors.toList());
        for (String barcode : groupedBarcodeMapKeys) {
            if (!allBarcodes.contains(barcode)) {
                return true;
            }
        }
        return false;
    }

    private List<ReceiptInfo> calculateReceiptInfoByBarcodes(Map<String, Integer> groupBarcodeMap){
        List<Item> itemsInfo = new ItemsDao().findItemInfoByBarcodes(new ArrayList<>(groupBarcodeMap.keySet()));
        List<ReceiptInfo> receiptInfoList = new ArrayList<>();
        groupBarcodeMap.forEach((barcode, count) -> {
            itemsInfo.stream().filter(i -> i.getBarcode().equals(barcode)).findFirst().ifPresent(item -> receiptInfoList.add(new ReceiptInfo(item.getBarcode(), item.getName(), count, item.getPrice(), item.getPrice() * count)));
        });
        return receiptInfoList;
    }

    /*
    ***<store earning no money>Receipt***
    Name: Coca-Cola, Quantity: 4, Unit price: 3 (yuan), Subtotal: 12 (yuan)
    Name: Sprite, Quantity: 2, Unit price: 3 (yuan), Subtotal: 6 (yuan)
    Name: Battery, Quantity: 3, Unit price: 2 (yuan), Subtotal: 6 (yuan)
    ----------------------
    Total: 24 (yuan)
    **********************
    */
    private String getReceiptWithFormat(List<ReceiptInfo> receiptInfoList) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("***<store earning no money>Receipt***\n");
        for (ReceiptInfo info : receiptInfoList) {
            receipt.append(String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                    info.getName(), info.getCount(), info.getPrice(), info.getTotalPrice()));
        }
        receipt.append("----------------------\n");
        receipt.append(String.format("Total: %d (yuan)\n", receiptInfoList.stream().mapToInt(ReceiptInfo::getTotalPrice).sum()));
        receipt.append("**********************");
        return receipt.toString();
    }
}
