package com.example.admin.Repository;


import com.example.admin.Entity.InventoryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryReportRepository extends JpaRepository<InventoryReport, Long> {
}
