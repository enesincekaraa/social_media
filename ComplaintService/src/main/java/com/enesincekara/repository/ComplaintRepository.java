package com.enesincekara.repository;

import com.enesincekara.dto.response.CategoryPerformanceResponse;
import com.enesincekara.dto.response.ComplaintStatsResponse;
import com.enesincekara.dto.response.DailyComplaintReportResponse;
import com.enesincekara.dto.response.UserComplaintStatsResponse;
import com.enesincekara.entity.Complaint;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<Complaint, String> {


    @Query("select c from Complaint c where c.authId = :authId order by c.createdAt desc ")
    List<Complaint> findUserComplaint(UUID authId);


    @Query("select  c from Complaint c order by c.createdAt desc")
    List<Complaint> findAllComplaintsOrdered();

    @Query("select c from Complaint c where c.trackingNumber= :trackingNumber order by c.createdAt desc ")
    Optional<Complaint> findByTrackingCode(String trackingNumber);

    @Query(value = "SELECT * FROM complaint_db.public.tbl_complaints c WHERE " +
            "(6371 * acos(cos(radians(?1)) * cos(radians(c.latitude)) * " +
            "cos(radians(c.longitude) - radians(?2)) + sin(radians(?1)) * " +
            "sin(radians(c.latitude)))) <= ?3",
            nativeQuery = true)
    List<Complaint> findNear(@Param("lat") Double lat,
                             @Param("lon") Double lon,
                             @Param("radius") Double radius);



    @Query("SELECT new com.enesincekara.dto.response.ComplaintStatsResponse(c.category, COUNT(c)) " +
            "FROM Complaint c " +
            "GROUP BY c.category")
    List<ComplaintStatsResponse> getStatsByCategory();

    @Query("SELECT new com.enesincekara.dto.response.DailyComplaintReportResponse(" +
            "CAST(c.createdAt AS LocalDate), " +
            "COUNT(c), " +
            "SUM(CASE WHEN c.status = 'RESOLVED' THEN 1 ELSE 0 END)) " +
            "FROM Complaint c " +
            "WHERE c.createdAt >= :startDate " +
            "GROUP BY CAST(c.createdAt AS LocalDate) " +
            "ORDER BY CAST(c.createdAt AS LocalDate) ASC")
    List<DailyComplaintReportResponse> getDailyReport(@Param("startDate") java.time.LocalDateTime startDate);

    @Query("SELECT new com.enesincekara.dto.response.CategoryPerformanceResponse(" +
            "c.category, " +
            "COUNT(c), " +
            "SUM(CASE WHEN c.status = 'RESOLVED' THEN 1 ELSE 0 END), " +
            "CAST(SUM(CASE WHEN c.status = 'RESOLVED' THEN 1 ELSE 0 END) AS Double) / COUNT(c) * 100) " +
            "FROM Complaint c " +
            "GROUP BY c.category")
    List<CategoryPerformanceResponse> getCategoryPerformance();


    @Query(value = "SELECT c.category as category, COUNT(*) as count, c.status as status " +
            "FROM complaint_db.public.tbl_complaints c " +
            "WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(c.latitude)) * " +
            "cos(radians(c.longitude) - radians(:lon)) + sin(radians(:lat)) * " +
            "sin(radians(c.latitude)))) <= :radius " +
            "GROUP BY c.category, c.status",
            nativeQuery = true)
    List<Object[]> getRegionalDensity(@Param("lat") Double lat,
                                      @Param("lon") Double lon,
                                      @Param("radius") Double radius);



    @Query("SELECT new com.enesincekara.dto.response.UserComplaintStatsResponse(" +
            "c.authId, " +
            "COUNT(c), " +
            "SUM(CASE WHEN c.status = 'RESOLVED' THEN 1L ELSE 0L END), " +
            "CAST(SUM(CASE WHEN c.status = 'RESOLVED' THEN 1 ELSE 0 END) AS Double) / COUNT(c) * 100.0) " +
            "FROM Complaint c " +
            "GROUP BY c.authId " +
            "ORDER BY COUNT(c) DESC")
    List<UserComplaintStatsResponse> getUserComplaintStats();

}

