package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.stereotype.Service;

@Service
public interface ShipService {

    Iterable<Ship> getAll(String name,
                          String planet,
                          ShipType shipType,
                          Long after,
                          Long before,
                          Boolean isUsed,
                          Double minSpeed,
                          Double maxSpeed,
                          Integer minCrew,
                          Integer maxCrew,
                          Double minRating,
                          Double maxRating,
                          String order,
                          Integer pageNum,
                          Integer pageSize);

    Ship createShip(Ship shipData);

    Ship updateShip(Long id, Ship ship);

    boolean deleteShip(Long id);

    Ship getById(Long id);

    Integer getCountByFilter(String name,
                             String planet,
                             ShipType shipType,
                             Long after,
                             Long before,
                             Boolean isUsed,
                             Double minSpeed,
                             Double maxSpeed,
                             Integer minCrewSize,
                             Integer maxCrewSize,
                             Double maxRating,
                             Double rating);


}




