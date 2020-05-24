package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@Controller
@RequestMapping("/rest/ships")
public class CosmoportRestController {


    private final ShipServiceImpl service;

    @Autowired
    public CosmoportRestController(ShipServiceImpl service) {
        this.service = service;
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<?> allByFilter
            (@RequestParam(value = "name", defaultValue = "") String name,
             @RequestParam(value = "planet", defaultValue = "") String planet,
             @RequestParam(value = "shipType", required = false) ShipType shipType,
             @RequestParam(value = "after", required = false) Long after,
             @RequestParam(value = "before", required = false) Long before,
             @RequestParam(value = "isUsed", required = false) Boolean isUsed,
             @RequestParam(value = "minSpeed", required = false) Double minSpeed,
             @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
             @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
             @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
             @RequestParam(value = "minRating", required = false) Double minRating,
             @RequestParam(value = "maxRating", required = false) Double maxRating,
             @RequestParam(value = "order", defaultValue = "ID") String order,
             @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
             @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        return new ResponseEntity<>(service.getAll(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, order, pageNumber, pageSize), HttpStatus.OK);
//        return new ResponseEntity<>(service.testAll(pageNumber, pageSize, order), HttpStatus.OK);
    }


    //    /TODO
    @PostMapping()
    @ResponseBody
    public ResponseEntity<?> createShip(@RequestBody Ship ship) {
        try {
            return new ResponseEntity<>(service.createShip(ship), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<?> getCount(@RequestParam(value = "name", defaultValue = "") String name,
                                      @RequestParam(value = "planet", defaultValue = "") String planet,
                                      @RequestParam(value = "shipType", required = false) ShipType shipType,
                                      @RequestParam(value = "after", required = false) Long after,
                                      @RequestParam(value = "before", required = false) Long before,
                                      @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                      @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                      @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                      @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                      @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                      @RequestParam(value = "minRating", required = false) Double minRating,
                                      @RequestParam(value = "maxRating", required = false) Double maxRating) {
        return new ResponseEntity<>(service.getCountByFilter(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            Long l = validate(id);
            return new ResponseEntity<>(service.getById(l), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Long validate(String id) {
        long res = Long.parseLong(id);
        if (res <= 0) throw new NumberFormatException();
        return res;
    }


    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateShip(@PathVariable String id, @RequestBody Ship ship) {

        try {
            Long idLong = validate(id);
            return new ResponseEntity<>(service.updateShip(idLong, ship), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteById(@PathVariable String id) {

        try {
            Long res = validate(id);
            return new ResponseEntity<>(service.deleteShip(res), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
