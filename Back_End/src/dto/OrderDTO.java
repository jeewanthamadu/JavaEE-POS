package dto;

public class OrderDTO {

    private String oId;
    private String date;
    private String customerId;
    private int discount;
    private double total;
    private double subTotal;

    public OrderDTO(String oId, String date, String customerId, int discount, double total, double subTotal) {
        this.setoId(oId);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setDiscount(discount);
        this.setTotal(total);
        this.setSubTotal(subTotal);
    }

    public OrderDTO() {
    }


    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "oId='" + oId + '\'' +
                ", date='" + date + '\'' +
                ", customerId='" + customerId + '\'' +
                ", discount=" + discount +
                ", total=" + total +
                ", subTotal=" + subTotal +
                '}';
    }
}
