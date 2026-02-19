package chijouProjects.gestion_clinique_api.controller;

import chijouProjects.gestion_clinique_api.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secretaire/factures")
public class InvoiceController {
    @Autowired
    private PdfService pdfService;

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id) {
        byte[] pdfContent = pdfService.generateInvoicePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("facture-" + id + ".pdf").build());

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
