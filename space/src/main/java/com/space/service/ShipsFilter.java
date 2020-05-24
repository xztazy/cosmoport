package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Calendar;
import java.util.Date;

public class ShipsFilter {

    private static final int MAX_SHIP_NAME_LENGTH = 50;
    private static final int MAX_PLANET_NAME_LENGTH = 50;
    private static final int MIN_PROD_DATE = 2800;
    private static final int MAX_PROD_DATE = 3019;
    private static final double MIN_SPEED = 0.1;
    private static final double MAX_SPEED = 0.99;
    private static final int MAX_CREW_SIZE = 9999;
    private static final int MIN_CREW_SIZE = 1;

    public static Specification<Ship> specByName(final String name) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Ship> specByPlanet(final String planet) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("planet"), "%" + planet + "%");
    }

    public static Specification<Ship> specByType(final ShipType shipType) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (shipType == null) return null;
            return criteriaBuilder.equal(root.get("shipType"), shipType);
        };
    }

    public static Specification<Ship> specByAfter(final Long after) {
        if (after == null) return null;
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.<Date>get("prodDate"), new Date(after));
    }

    public static Specification<Ship> specByBefore(final Long before) {
        if (before == null) return null;
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.<Date>get("prodDate"), new Date(before));
    }

    public static Specification<Ship> specByUse(final Boolean isUsed) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (isUsed == null) return null;
            return criteriaBuilder.equal(root.get("isUsed"), isUsed);
        };
    }

    public static Specification<Ship> specByMinSpeed(final Double speed) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (speed == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), speed);
        };
    }

    public static Specification<Ship> specByMaxSpeed(final Double speed) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (speed == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("speed"), speed);
        };
    }

    public static Specification<Ship> specByMinRating(final Double rating) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (rating == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }

    public static Specification<Ship> specByMaxRating(final Double rating) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (rating == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), rating);
        };
    }

    public static Specification<Ship> specByMinCrew(final Integer crewSize) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (crewSize == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), crewSize);
        };
    }

    public static Specification<Ship> specByMaxCrew(final Integer crewSize) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (crewSize == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), crewSize);
        };
    }

    public static Boolean validShip(Ship ship){

        if (ship.getUsed() == null) ship.setUsed(false);

        if (ship.getName() == null || ship.getName().length() < 1 || ship.getName().length() > MAX_SHIP_NAME_LENGTH) return false;
        if (ship.getPlanet() == null || ship.getPlanet().length() < 1 || ship.getPlanet().length() > MAX_PLANET_NAME_LENGTH) return false;
        if (ship.getCrewSize() < MIN_CREW_SIZE || ship.getCrewSize() > MAX_CREW_SIZE) return false;
        if (ship.getSpeed() < MIN_SPEED || ship.getSpeed() > MAX_SPEED) return false;

        Calendar c = Calendar.getInstance();
        c.set(MIN_PROD_DATE, 1, 1);
        long from = c.getTimeInMillis();
        c.set(MAX_PROD_DATE + 1 , 1, 1);
        long to = c.getTimeInMillis() - 1;
        if (ship.getProdDate().getTime() < from || ship.getProdDate().getTime() > to ) return false;

        return true;
    }

}



