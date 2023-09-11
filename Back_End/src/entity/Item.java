package entity;

public class Item {
    private String ItemCode;
    private String ItemName;

    private double ItemPrice;
    private int ItemQty;

    public Item(String itemCode, String itemName,  double itemPrice, int itemQty) {
        ItemCode = itemCode;
        ItemName = itemName;
        ItemQty = itemQty;
        ItemPrice = itemPrice;
    }

    public Item() {
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getItemQty() {
        return ItemQty;
    }

    public void setItemQty(int itemQty) {
        ItemQty = itemQty;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ItemCode='" + ItemCode + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemQty=" + ItemQty +
                ", ItemPrice=" + ItemPrice +
                '}';
    }
}
