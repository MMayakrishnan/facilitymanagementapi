package com.hotel.facilitymanagementapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.facilitymanagementapi.exception.ResourceNotAvailableException;
import com.hotel.facilitymanagementapi.pojo.CostAndFacilities;
import com.hotel.facilitymanagementapi.pojo.RoomType;

@Service
public interface FacilityManagementService {
	List<RoomType> getAllRoomTypes(int pageNo, int pageSize) ;
	CostAndFacilities fetchAmenitiesForType(String roomType) ;
	List<RoomType> fetchRoomTypeByFacility(String facility);

}
