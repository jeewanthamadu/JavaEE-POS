package dto;

import entity.OrderDetails;

import java.util.ArrayList;

public class OrderDTO {

    private String oId;
    private String date;
    private String customerId;
    private int discount;
    private double total;
    private double subTotal;
    private ArrayList<OrderDetails> orderDetails;

    public OrderDTO(String oId, String date, String customerId, int discount, double total, double subTotal, ArrayList<OrderDetails> orderDetails) {
        this.setoId(oId);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setDiscount(discount);
        this.setTotal(total);
        this.setSubTotal(subTotal);
        this.setOrderDetails(orderDetails);
    }

    public OrderDTO() {
    }

    public OrderDTO(String oId, ArrayList<OrderDetails> orderDetails) {
        this.oId = oId;
        this.orderDetails = orderDetails;
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

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
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
                ", orderDetails=" + orderDetails +
                '}';
    }
}
