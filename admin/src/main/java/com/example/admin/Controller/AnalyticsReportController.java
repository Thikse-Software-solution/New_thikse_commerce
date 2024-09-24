package com.example.admin.Controller;
import com.example.admin.Entity.AnalyticsReport;
import com.example.admin.Service.CustomerBehaviorService;
import com.example.admin.Service.OutOfStockProductService;
import com.example.admin.Service.PendingOrderService;
import com.example.admin.Service.ShippingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics/report")
public class AnalyticsReportController {

    @Autowired
    private CustomerBehaviorService customerBehaviorService;

    @Autowired
    private ShippingDetailService shippingDetailService;

    @Autowired
    private PendingOrderService pendingOrderService;

    @Autowired
    private OutOfStockProductService outOfStockProductService;

    @GetMapping
    public AnalyticsReport getAnalyticsReport() {
        // Example IDs for demonstration
        Long customerId = 1L;
        Long orderId = 1L;
        Long productId = 1L;

        AnalyticsReport report = new AnalyticsReport();
        report.setCustomerBehavior(customerBehaviorService.getCustomerBehavior(customerId));
        report.setShippingDetail(shippingDetailService.getShippingDetail(orderId));
        report.setPendingOrder(pendingOrderService.getPendingOrder(orderId));
        report.setOutOfStockProduct(outOfStockProductService.getOutOfStockProduct(productId));

        return report;
    }
}
