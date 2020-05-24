package com.space.service;


import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;


    @Override
    public Iterable<Ship> getAll(String name, String planet, ShipType shipType,
                                 Long after, Long before, Boolean isUsed,
                                 Double minSpeed, Double maxSpeed, Integer minCrew,
                                 Integer maxCrew, Double minRating, Double maxRating,
                                 String order, Integer pageNum, Integer pageSize) {

        return shipRepository.findAll(Specification
                        .where(ShipsFilter.specByName(name))
                        .and(ShipsFilter.specByPlanet(planet))
                        .and(ShipsFilter.specByType(shipType))
                        .and(ShipsFilter.specByUse(isUsed))
                        .and(ShipsFilter.specByMinSpeed(minSpeed))
                        .and(ShipsFilter.specByMaxSpeed(maxSpeed))
                        .and(ShipsFilter.specByMinCrew(minCrew))
                        .and(ShipsFilter.specByMaxCrew(maxCrew))
                        .and(ShipsFilter.specByMaxRating(maxRating))
                        .and(ShipsFilter.specByMinRating(minRating))
                        .and(ShipsFilter.specByAfter(after))
                        .and(ShipsFilter.specByBefore(before))
                , PageRequest.of(pageNum, pageSize, Sort.by(ShipOrder.valueOf(order).getFieldName()))).getContent();
    }


    @Override
    public Ship createShip(Ship ship) throws RuntimeException {

        if (!ShipsFilter.validShip(ship)) throw new RuntimeException();
        ship.computeRating();
        return shipRepository.save(ship);
    }


    @Override
    public Ship updateShip(Long id, Ship ship) throws NoSuchElementException {

        Ship shipToUpdate = getById(id);

        if (ship.getName() != null) shipToUpdate.setName(ship.getName());
        if (ship.getPlanet() != null) shipToUpdate.setPlanet(ship.getPlanet());
        if (ship.getShipType() != null) shipToUpdate.setShipType(ship.getShipType());
        if (ship.getSpeed() != null) shipToUpdate.setSpeed(ship.getSpeed());
        if (ship.getProdDate() != null) shipToUpdate.setProdDate(ship.getProdDate());
        if (ship.getCrewSize() != null) shipToUpdate.setCrewSize(ship.getCrewSize());
        if (ship.getUsed() != null) shipToUpdate.setUsed(ship.getUsed());
        if (!ShipsFilter.validShip(shipToUpdate)) throw new NumberFormatException();

        shipToUpdate.computeRating();

        shipRepository.saveAndFlush(shipToUpdate);

        return shipToUpdate;
    }

    @Override
    public boolean deleteShip(Long id) throws NoSuchElementException {

        shipRepository.delete(getById(id));
        return true;
    }

    @Override
    public Ship getById(Long id) throws NoSuchElementException {
        return shipRepository.findById(id).get();
    }

    @Override
    public Integer getCountByFilter(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrew, Integer maxCrew, Double minRating, Double maxRating) {
        return shipRepository.findAll(Specification
                .where(ShipsFilter.specByName(name))
                .and(ShipsFilter.specByPlanet(planet))
                .and(ShipsFilter.specByType(shipType))
                .and(ShipsFilter.specByUse(isUsed))
                .and(ShipsFilter.specByMinSpeed(minSpeed))
                .and(ShipsFilter.specByMaxSpeed(maxSpeed))
                .and(ShipsFilter.specByMinCrew(minCrew))
                .and(ShipsFilter.specByMaxCrew(maxCrew))
                .and(ShipsFilter.specByMaxRating(maxRating))
                .and(ShipsFilter.specByMinRating(minRating))
                .and(ShipsFilter.specByAfter(after))
                .and(ShipsFilter.specByBefore(before))).size();
    }

}
