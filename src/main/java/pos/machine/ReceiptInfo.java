package pos.machine;

public class ReceiptInfo {
    private String barcode;
    private String name;
    private int price;
    private int count;
    private int totalPrice;

    public ReceiptInfo(String barcode, String name, int count, int price, int totalPrice) {
        this.barcode = barcode;
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}