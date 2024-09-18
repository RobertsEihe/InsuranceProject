package com.project.InsuranceProject.data.services.pdf;

import com.project.InsuranceProject.data.entity.Policy;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PDFGenerateService {

    public String convertEncodeTextToPDF(Policy policy) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream contentPDF = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(100, 700);

                contentStream.showText("Insurance Company");
                contentStream.newLine();
                contentStream.showText("");
                contentStream.newLine();
                contentStream.showText("This is the certificate of insurance with policy number: "+policy.getPolicy_id()+".");
                contentStream.newLine();
                contentStream.showText("");
                contentStream.newLine();
                contentStream.showText("Customer details");
                contentStream.newLine();
                contentStream.showText(policy.getUsers().getName());
                contentStream.newLine();
                contentStream.showText("Customer's ID: "+policy.getUsers().getId()+"." );
                contentStream.newLine();
                contentStream.showText("Email: "+policy.getUsers().getEmail());
                contentStream.newLine();
                contentStream.showText("Address: "+policy.getUsers().getAddress());
                contentStream.newLine();
                contentStream.showText("");
                contentStream.newLine();
                contentStream.showText("Policy details");
                contentStream.newLine();
                contentStream.showText("Policy start date: "+policy.getStart_date()+"." );
                contentStream.newLine();
                contentStream.showText("Policy end date: "+policy.getEnd_date()+"." );
                contentStream.newLine();
                contentStream.showText("Sum insured: € "+policy.getSum_insured()+"." );
                contentStream.newLine();
                String totalPremium = String.format("%.2f", policy.getTotalPremium());
                contentStream.showText("Total premium: € "+totalPremium+"." );
                if(policy.getDuration() > 1){
                    String monthlyPremium = String.format("%.2f", policy.getTotalPremium() / policy.getDuration());
                    contentStream.newLine();
                    contentStream.showText("Monthly premium: € "+monthlyPremium+"." );
                }

                contentStream.endText();
            }

            document.save(contentPDF);

            return Base64.getEncoder().encodeToString(contentPDF.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void decodeToPDF(String codedString, String filePath) {
        try {
            byte[] pdfBytes = Base64.getDecoder().decode(codedString);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(pdfBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}