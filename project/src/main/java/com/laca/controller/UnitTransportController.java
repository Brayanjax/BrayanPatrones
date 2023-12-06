package com.laca.controller;

import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.Transporter;
import com.laca.service.TransporterService;
import com.laca.service.UnitTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unit_transporter")
@CrossOrigin(origins = "http://localhost:4200/")
public class UnitTransportController {
    private final UnitTransportService unitTransportService;

    @Autowired
    public UnitTransportController(UnitTransportService unitTransportService) {
        this.unitTransportService = unitTransportService;
    }


    @GetMapping
    public List<UnitTransporterAbstract> getAllUnitTransporters() {// se encarga de traer todos las unidades de transporte
        List<UnitTransporterAbstract> unitTransporterAbstractList = unitTransportService.getAllUnitTransporters();
        return unitTransporterAbstractList;
    }

    @PostMapping
    public UnitTransporterAbstract saveUnitTransporter(@RequestBody UnitTransporterAbstract unitTransporterAbstract) {
        return unitTransportService.saveUnitTransporter(unitTransporterAbstract);
    }



    @PostMapping("/{DuplicateUnitTransporter}")
    public UnitTransporterAbstract DuplicateUnitTransporter(@RequestBody UnitTransporterAbstract unitTransporterAbstract) {
        return unitTransportService.DuplicateUnitTransporter(unitTransporterAbstract);
    }

    @PutMapping("/{unit_transporter_I}")
    public ResponseEntity<?> updateUnitTransporter(
            @PathVariable Long transporterId,
            @RequestBody UnitTransporterAbstract updatedTransporter) {
        try {
            UnitTransporterAbstract updated = unitTransportService.updateUnitTransporter(transporterId, updatedTransporter);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating unit transporter: " + e.getMessage());
        }
    }

    @GetMapping("/{unit_transporter_Id}")
    public ResponseEntity<?> getUnitTransporterById(@PathVariable Long UnitTransporterId) {
        try {
            UnitTransporterAbstract unitTransporterAbstract = unitTransportService.getUnitTransporterById(UnitTransporterId);
            return ResponseEntity.ok(unitTransporterAbstract);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("unit transporter not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{Unit_transporter_Id}")
    public ResponseEntity<?> deleteUnitTransporter(@PathVariable Long UnitTransporterId) {
        try {
            boolean isDeleted = unitTransportService.deleteUnitTransporter(UnitTransporterId);
            Transporter transporter= new Transporter();
            transporter.setId(UnitTransporterId);
            if (isDeleted) {
                return ResponseEntity.ok(transporter);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UnitTransporterId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting unit transporter: " + e.getMessage());
        }

    }
}
