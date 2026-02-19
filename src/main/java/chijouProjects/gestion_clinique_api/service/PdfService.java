package chijouProjects.gestion_clinique_api.service;

public interface PdfService {
    byte[] generatePrescriptionPdf(Long prescriptionId);
    byte[] generateInvoicePdf(Long factureId);
}
