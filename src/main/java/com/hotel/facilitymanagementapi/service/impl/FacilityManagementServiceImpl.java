package com.hotel.facilitymanagementapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hotel.facilitymanagementapi.exception.DefaultExceptionHandler;
import com.hotel.facilitymanagementapi.exception.ResourceNotAvailableException;
import com.hotel.facilitymanagementapi.pojo.Amenities;
import com.hotel.facilitymanagementapi.pojo.CostAndFacilities;
import com.hotel.facilitymanagementapi.pojo.RoomType;
import com.hotel.facilitymanagementapi.repository.RoomTypeRepository;
import com.hotel.facilitymanagementapi.service.FacilityManagementService;


@Service
public class FacilityManagementServiceImpl implements FacilityManagementService{
	
	private static Logger logger=LoggerFactory.getLogger(DefaultExceptionHandler.class);
	
	@Autowired
	RoomTypeRepository roomTypeRepository;
	
	
	// gets room type based on pages details throws custom exception if no data available on the page  
	@Override
	public List<RoomType> getAllRoomTypes(int pNo,int pSize){
		logger.info("getting room types in page"+pNo);
		List<RoomType> roomTypes = roomTypeRepository.findAll(PageRequest.of(pNo, pSize)).getContent();
		if(!roomTypes.isEmpty()) {
			logger.info("room types fetched");
			return roomTypes;
		}
		else {
			logger.info("No Room Type Available in selected page");
			throw new ResourceNotAvailableException("No Room Type Available in selected page");
		}
	}

	
	//gets room type based on facility available throws custom exception if facility is not available in any rooms
	@Override
	public List<RoomType> fetchRoomTypeByFacility(String facility)  {
		logger.info("finding room type by facility "+facility);
		List<RoomType> roomsWithAmenities = roomTypeRepository.findByAmenities_AmenitiesInfoIgnoreCase(facility);
		if(!roomsWithAmenities.isEmpty()) {
			logger.info(" room types found for the facility "+facility);
			return roomsWithAmenities;
		}else {
			logger.error("Requested Facility "+facility+" is not available in the hotel");
			throw new ResourceNotAvailableException("Requested Facility "+facility+" is not available in the hotel");
		}
	}

	
	//gets  facility available and rates based on room type throws custom exception if room type is not available in the hotel
	@Override
	public CostAndFacilities fetchAmenitiesForType(String roomType) {
		logger.info("fetching facilities available in  room type :"+roomType);
		Optional<RoomType> optionalRoomTypes=roomTypeRepository.findByRoomTypeIgnoreCase(roomType);
		if(optionalRoomTypes.isPresent()) {
			Set<Amenities> amenitiesSet= optionalRoomTypes.get().getAmenities();
			List<String> amenities=amenitiesSet.stream().map(x->x.getAmenitiesInfo()).collect(Collectors.toList());
			CostAndFacilities costAndFacilities=new CostAndFacilities(optionalRoomTypes.get().getPrice(), amenities);
			logger.info("cost and facilities of  room type recieved:"+roomType);
			return costAndFacilities;
		}else {
			logger.error("Requested Room Type "+roomType+" is not available in the hotel");
			throw new ResourceNotAvailableException("Requested Room Type "+roomType+" is not available in the hotel");
		}
	}

}
