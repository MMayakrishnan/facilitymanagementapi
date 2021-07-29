package com.hotel.facilitymanagementapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.facilitymanagementapi.pojo.RoomType;


@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

	List<RoomType> findByAmenities_AmenitiesInfoIgnoreCase(String facility);


	Optional<RoomType> findByRoomTypeIgnoreCase(String roomType);

}
