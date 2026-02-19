package chijouProjects.gestion_clinique_api.controller;

import chijouProjects.gestion_clinique_api.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medecin")
public class MedicalController {
    @Autowired
    private PdfService pdfService;

    @GetMapping("/prescription/{id}/pdf")
    public ResponseEntity<byte[]> getPrescriptionPdf(@PathVariable Long id) {
        byte[] contents = pdfService.generatePrescriptionPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("prescription.pdf").build());
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }
}
