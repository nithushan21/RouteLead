package com.example.be.repository;

import com.example.be.model.ReturnRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReturnRouteRepository extends JpaRepository<ReturnRoute, UUID> {
    
    /**
     * Find all routes by driver ID
     */
    List<ReturnRoute> findByDriverId(UUID driverId);
    
    /**
     * Find routes by status
     */
    List<ReturnRoute> findByStatus(com.example.be.types.RouteStatus status);
    
    /**
     * Find routes by driver ID and status
     */
    List<ReturnRoute> findByDriverIdAndStatus(UUID driverId, com.example.be.types.RouteStatus status);

    @Modifying
    @Query(value = """
        INSERT INTO return_routes (
            driver_id, origin_lat, origin_lng, destination_lat, destination_lng,
            departure_time, detour_tolerance_km, suggested_price_min, suggested_price_max,
            status, created_at, updated_at
        ) VALUES (
            :driverId, :originLat, :originLng, :destinationLat, :destinationLng,
            :departureTime, :detourToleranceKm, :suggestedPriceMin, :suggestedPriceMax,
            CAST(:status AS route_status), :createdAt, :updatedAt
        )
        """, nativeQuery = true)
    void insertRouteWithEnum(
        @Param("driverId") UUID driverId,
        @Param("originLat") java.math.BigDecimal originLat,
        @Param("originLng") java.math.BigDecimal originLng,
        @Param("destinationLat") java.math.BigDecimal destinationLat,
        @Param("destinationLng") java.math.BigDecimal destinationLng,
        @Param("departureTime") java.time.ZonedDateTime departureTime,
        @Param("detourToleranceKm") java.math.BigDecimal detourToleranceKm,
        @Param("suggestedPriceMin") java.math.BigDecimal suggestedPriceMin,
        @Param("suggestedPriceMax") java.math.BigDecimal suggestedPriceMax,
        @Param("status") String status,
        @Param("createdAt") java.time.ZonedDateTime createdAt,
        @Param("updatedAt") java.time.ZonedDateTime updatedAt
    );
} 