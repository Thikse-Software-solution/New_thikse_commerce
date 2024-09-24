package com.example.admin.Entity;

public class AnalyticsReport {
    private CustomerBehavior customerBehavior;
    private ShippingDetail shippingDetail;
    private PendingOrder pendingOrder;
    private OutOfStockProduct outOfStockProduct;

    public CustomerBehavior getCustomerBehavior() {
        return customerBehavior;
    }

    public void setCustomerBehavior(CustomerBehavior customerBehavior) {
        this.customerBehavior = customerBehavior;
    }

    public ShippingDetail getShippingDetail() {
        return shippingDetail;
    }

    public void setShippingDetail(ShippingDetail shippingDetail) {
        this.shippingDetail = shippingDetail;
    }

    public PendingOrder getPendingOrder() {
        return pendingOrder;
    }

    public void setPendingOrder(PendingOrder pendingOrder) {
        this.pendingOrder = pendingOrder;
    }

    public OutOfStockProduct getOutOfStockProduct() {
        return outOfStockProduct;
    }

    public void setOutOfStockProduct(OutOfStockProduct outOfStockProduct) {
        this.outOfStockProduct = outOfStockProduct;
    }
}
