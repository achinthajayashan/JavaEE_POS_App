package entity;

public class Customer {
    private String CusID;
    private String CusName;
    private String CusAddress;
    private int CusTel;

    public Customer(String cusID, String cusName, String cusAddress, int cusTel) {
        CusID = cusID;
        CusName = cusName;
        CusAddress = cusAddress;
        CusTel = cusTel;
    }

    public Customer() {
    }

    public String getCusID() {
        return CusID;
    }

    public void setCusID(String cusID) {
        CusID = cusID;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getCusAddress() {
        return CusAddress;
    }

    public void setCusAddress(String cusAddress) {
        CusAddress = cusAddress;
    }

    public int getCusTel() {
        return CusTel;
    }

    public void setCusTel(int cusTel) {
        CusTel = cusTel;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CusID='" + CusID + '\'' +
                ", CusName='" + CusName + '\'' +
                ", CusAddress='" + CusAddress + '\'' +
                ", CusTel=" + CusTel +
                '}';
    }
}
