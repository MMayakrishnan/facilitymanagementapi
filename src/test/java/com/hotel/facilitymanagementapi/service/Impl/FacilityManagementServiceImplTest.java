package com.hotel.facilitymanagementapi.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotel.facilitymanagementapi.exception.ResourceNotAvailableException;
import com.hotel.facilitymanagementapi.pojo.Amenities;
import com.hotel.facilitymanagementapi.pojo.CostAndFacilities;
import com.hotel.facilitymanagementapi.pojo.RoomType;
import com.hotel.facilitymanagementapi.repository.RoomTypeRepository;
import com.hotel.facilitymanagementapi.service.impl.FacilityManagementServiceImpl;

public class FacilityManagementServiceImplTest {

	@Mock
	RoomTypeRepository roomTypeRepository;
	
	@InjectMocks
	FacilityManagementServiceImpl facilityManagementServiceImpl;

	@BeforeEach
	    public  void init() {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	
	@Test
	public void fetchRoomTypeByFacilityHappyPathTest() {
		RoomType roomTyp1=new RoomType();
		roomTyp1.setRoomType("Deluxe");
		roomTyp1.setPrice(new BigDecimal(25));
		RoomType roomTyp2=new RoomType();
		roomTyp2.setRoomType("Luxury");
		roomTyp2.setPrice(new BigDecimal(25));
		List<RoomType> roomsWithAmenities=new ArrayList<RoomType>();
		roomsWithAmenities.add(roomTyp2);
		roomsWithAmenities.add(roomTyp1);
		when(roomTypeRepository.findByAmenities_AmenitiesInfoIgnoreCase(Mockito.any())).thenReturn(roomsWithAmenities);
		 List<RoomType> result = facilityManagementServiceImpl.fetchRoomTypeByFacility("Fan");
		assertEquals(roomsWithAmenities.size(),result.size());
	}
	
	@Test
	public void fetchRoomTypeByFacilityExceptionPathTest() {
		List<RoomType> roomsWithAmenities=new ArrayList<RoomType>();
		when(roomTypeRepository.findByAmenities_AmenitiesInfoIgnoreCase(Mockito.any())).thenReturn(roomsWithAmenities);
		 try{
			 facilityManagementServiceImpl.fetchRoomTypeByFacility("Fan");
			 assert false;
		 }catch(ResourceNotAvailableException ex) {
			 assert true;
		 }
	}
	
	
	@Test
	public void fetchAmenitiesForTypeHappyPathTest() {
		RoomType roomTyp2=new RoomType();
		roomTyp2.setRoomType("Luxury");
		roomTyp2.setPrice(new BigDecimal(25));
		Amenities amenity=new Amenities();
		amenity.setAmenitiesInfo("Fan");
		Set<Amenities> amenitySet=new HashSet();
		amenitySet.add(amenity);
		roomTyp2.setAmenities(amenitySet);
		Optional<RoomType> romm=Optional.ofNullable(roomTyp2);
		System.out.println(romm.get());
	when(roomTypeRepository.findByRoomTypeIgnoreCase(Mockito.anyString())).thenReturn(romm);
	CostAndFacilities result=facilityManagementServiceImpl.fetchAmenitiesForType("Luxury");
	assertEquals(new BigDecimal(25), result.getPrice());
	}
	
	@Test
	public void fetchAmenitiesForTypeExceptionPathTest() {
		Optional<RoomType> romm=Optional.ofNullable(null);
		when(roomTypeRepository.findByRoomTypeIgnoreCase(Mockito.any())).thenReturn(romm);
		 try{
			 facilityManagementServiceImpl.fetchRoomTypeByFacility("Fan");
			 assert false;
		 }catch(ResourceNotAvailableException ex) {
			 assert true;
		 }
	}

}
