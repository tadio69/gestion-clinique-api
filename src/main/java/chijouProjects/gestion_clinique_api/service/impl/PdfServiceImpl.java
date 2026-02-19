package chijouProjects.gestion_clinique_api.service.impl;

import chijouProjects.gestion_clinique_api.entity.Facture;
import chijouProjects.gestion_clinique_api.entity.Prescription;
import chijouProjects.gestion_clinique_api.repository.FactureRepository;
import chijouProjects.gestion_clinique_api.repository.PrescriptionRepository;
import chijouProjects.gestion_clinique_api.service.PdfGeneratorService;
import chijouProjects.gestion_clinique_api.service.PdfService;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private FactureRepository factureRepo;

    @Autowired
    private PdfGeneratorService pdfGeneratorService; // On injecte le générateur corrigé

    @Override
    public byte[] generatePrescriptionPdf(Long prescriptionId) {
        Prescription p = prescriptionRepo.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription non trouvée"));

        // On délègue la création technique au GeneratorService
        return pdfGeneratorService.generatePrescription(p);
    }

    @Override
    public byte[] generateInvoicePdf(Long factureId) {
        Facture f = factureRepo.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

        // Logique pour la facture (similaire à la prescription mais avec les montants)
        return pdfGeneratorService.generateInvoice(f);
    }
}
