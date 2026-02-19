package chijouProjects.gestion_clinique_api.controller;

import chijouProjects.gestion_clinique_api.dto.AppointmentDTO;
import chijouProjects.gestion_clinique_api.entity.Appointment;
import chijouProjects.gestion_clinique_api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secretaire/rdv")
public class RdvController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/creer")
    public ResponseEntity<Appointment> create(@RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(appointmentService.createRdv(dto));
    }

    @DeleteMapping("/annuler/{id}")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        appointmentService.cancelRdv(id);
        return ResponseEntity.ok("Rendez-vous annulé avec succès.");
    }
}
