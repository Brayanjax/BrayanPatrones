package com.laca.controller;


import com.laca.entity.RouteC.Route;
import com.laca.service.RouteService;
import com.laca.service.TransporterService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
@CrossOrigin(origins = "http://localhost:4200/")
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<Route> getAllTransporters() {
        List<Route> routes = routeService.getAllRoutes();
        return routes;
    }

    @PostMapping
    public Transporter saveTransporter(@RequestBody Transporter transporter) {
        return transporterService.saveTransporter(transporter);
    }

    @PutMapping("/{transporterId}")
    public ResponseEntity<?> updateTransporter(
            @PathVariable Long transporterId,
            @RequestBody Transporter updatedTransporter) {
        try {
            Transporter updated = transporterService.updateTransporter(transporterId, updatedTransporter);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating transporter: " + e.getMessage());
        }
    }

    @GetMapping("/{transporterId}")
    public ResponseEntity<?> getTransporterById(@PathVariable Long transporterId) {
        try {
            Transporter transporter = transporterService.getTransporterById(transporterId);
            return ResponseEntity.ok(transporter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transporter not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{transporterId}")
    public ResponseEntity<?> deleteTransporter(@PathVariable Long transporterId) {
        try {
            boolean isDeleted = transporterService.deleteTransporter(transporterId);
            Transporter transporter= new Transporter();
            transporter.setId(transporterId);
            if (isDeleted) {
                return ResponseEntity.ok(transporter);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transporterId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting transporter: " + e.getMessage());
        }

    }
}
