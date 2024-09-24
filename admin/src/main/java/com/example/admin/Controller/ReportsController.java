package com.example.admin.Controller;// src/main/java/com/example/yourapp/ReportsController.java
//
//import com.example.admin.Entity.Customer;
import com.example.admin.Entity.InventoryReport;
import com.example.admin.Entity.SalesReport;
import com.example.admin.Service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

//    private final ReportsService reportsService;
//
//    @Autowired
//    public ReportsController(ReportsService reportsService) {
//        this.reportsService = reportsService;
//    }
//
//    @GetMapping("/sales-reports")
//    public List<SalesReport> getSalesReports() {
//        return reportsService.getSalesReports();
//    }
//
//    @GetMapping("/customer-behavior")
//    public List<Customer> getCustomerBehavior() {
//        return reportsService.getCustomer();
//    }
//
//    @GetMapping("/inventory-reports")
//    public List<InventoryReport> getInventoryReports() {
//        return reportsService.getInventoryReports();
//    }
//}
}
