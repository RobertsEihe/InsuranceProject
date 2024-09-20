package com.project.InsuranceProject.data.services.pdf;

import com.project.InsuranceProject.data.entity.Policy;
import org.springframework.stereotype.Service;

@Service
public class SavePDF {

    private final PDFService pdfService;

    public SavePDF(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    public void savePDFtoFile(Policy policy) {

        String base64PDF = pdfService.getDocumentFromDatabase(policy);

        if (base64PDF != null) {
            new PDFGenerateService().decodeToPDF(base64PDF, "insurance.pdf");
        } else {
            System.out.println("Document not found in the database.");
        }
    }
}