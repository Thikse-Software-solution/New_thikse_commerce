package com.example.admin.Repository;
import com.example.admin.Entity.SalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesReportRepository extends JpaRepository<SalesReport, Long> {
}

