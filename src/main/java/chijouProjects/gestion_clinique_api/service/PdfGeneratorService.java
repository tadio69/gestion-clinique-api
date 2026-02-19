package chijouProjects.gestion_clinique_api.service;

import chijouProjects.gestion_clinique_api.entity.Facture;
import chijouProjects.gestion_clinique_api.entity.Prescription;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {

    public byte[] generatePrescription(Prescription prescription) {
        // ByteArrayOutputStream implémente AutoCloseable
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Style du titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("ORDONNANCE MÉDICALE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // Espace

            // Infos Médecin et Date
            document.add(new Paragraph("Médecin : Dr. " + prescription.getDoctor().getUsername()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            document.add(new Paragraph("Date : " + LocalDateTime.now().format(formatter)));

            document.add(new Paragraph("Patient : " + prescription.getPatient().getNom() + " " + prescription.getPatient().getPrenom()));

            document.add(new Paragraph("\n----------------------------------------------------------\n"));

            // Contenu
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("CONTENU DE LA PRESCRIPTION :", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            document.add(new Paragraph(prescription.getContent(), contentFont));

            document.close();
            return out.toByteArray();

        } catch (DocumentException e) {
            // On transforme l'exception vérifiée en RuntimeException pour simplifier le code
            throw new RuntimeException("Erreur lors de la création du document PDF", e);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de flux de données", e);
        }
    }

    public byte[] generateInvoice(Facture facture) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("FACTURE CLINIQUE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\nFacture N° : " + facture.getId()));
            document.add(new Paragraph("Date d'émission : " + facture.getDateEmission()));
            document.add(new Paragraph("Patient : " + facture.getAppointment().getPatient().getNom()));
            document.add(new Paragraph("\n----------------------------------------------------------\n"));

            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("MONTANT TOTAL À PAYER : " + facture.getMontantTotal() + " €", totalFont));

            document.add(new Paragraph("\nStatut : " + (facture.isPayee() ? "PAYÉE" : "EN ATTENTE DE PAIEMENT")));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la facture PDF", e);
        }
    }
}
