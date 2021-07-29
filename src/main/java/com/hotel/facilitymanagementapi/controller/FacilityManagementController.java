package com.hotel.facilitymanagementapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.facilitymanagementapi.exception.ResourceNotAvailableException;
import com.hotel.facilitymanagementapi.pojo.CostAndFacilities;
import com.hotel.facilitymanagementapi.pojo.RoomType;
import com.hotel.facilitymanagementapi.service.FacilityManagementService;

@RestController
@RequestMapping("/FacilityManagement")
public class FacilityManagementController {
	
	@Autowired
	FacilityManagementService facilityManagementService;

	// to retrieve facility available in a paricular room type
	
	@GetMapping("/getAmenities/{roomType}")
	public ResponseEntity<CostAndFacilities> getAmenetiesForRoomType(@PathVariable String roomType){
		return 	new ResponseEntity<CostAndFacilities>(facilityManagementService.fetchAmenitiesForType(roomType),HttpStatus.OK);
	}

	//to retrieve types of room available in a hotel
	
	@GetMapping("/typesOfRooms/{pageNo}/{pageSize}")
	public ResponseEntity<List<RoomType>> getTypesOfRoom(@PathVariable int pageNo, @PathVariable  int pageSize){
		return	new ResponseEntity<List<RoomType>>(facilityManagementService.getAllRoomTypes(pageNo,pageSize),HttpStatus.OK);
		
	}	
	
	//to retrieve types of room which has the desired facility
	@GetMapping("/searchByfacility/{facility}")
	public ResponseEntity<List<RoomType>> searchByFacility(@PathVariable String facility) {
		return new ResponseEntity<List<RoomType>>(facilityManagementService.fetchRoomTypeByFacility(facility),HttpStatus.OK);
		
	}
}
