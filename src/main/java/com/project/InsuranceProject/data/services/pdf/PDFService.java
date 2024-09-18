package com.project.InsuranceProject.data.services.pdf;

import com.project.InsuranceProject.data.entity.Document;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PDFService {

    @Autowired
    private final DocumentRepository documentRepository;

    public PDFService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void saveDocumentToDatabase(Policy policy) {
        String codedPDF = new PDFGenerateService().convertEncodeTextToPDF(policy);

        Document document = new Document();
        document.setDocument(codedPDF);
        document.setPolicy(policy);

        documentRepository.save(document);
    }

    public String getDocumentFromDatabase(Policy policy) {
        Optional<Document> document = documentRepository.findByPolicyId(policy.getPolicy_id());
        return document.map(Document::getDocument).orElse(null);
    }
}
