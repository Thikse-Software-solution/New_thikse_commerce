package com.example.admin.Service;

import com.example.admin.Entity.InventoryReport;
import com.example.admin.Entity.SalesReport;
import com.example.admin.Repository.InventoryReportRepository;
import com.example.admin.Repository.SalesReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportsService {

//    @Autowired
//    private SalesReportRepository salesReportRepository;
//
//    @Autowired
//    private CustomerBehaviorRepository customerBehaviorRepository;
//
//    @Autowired
//    private InventoryReportRepository inventoryReportRepository;
//
//    public List<SalesReport> getSalesReports() {
//        // Fetch sales reports from the database
//        return salesReportRepository.findAll();
//    }
//
//    public List<CustomerBehavior> getCustomerBehavior() {
//        // Fetch customer behavior data from the database
//        return customerBehaviorRepository.findAll();
//    }
//
//    public List<InventoryReport> getInventoryReports() {
//        // Fetch inventory reports from the database
//        return inventoryReportRepository.findAll();
//    }
}
