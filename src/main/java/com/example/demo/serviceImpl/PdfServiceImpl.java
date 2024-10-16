package com.example.demo.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.demo.dto.sendPdfRequest;
import com.example.demo.service.PdfService;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private TemplateEngine engine;

	@Value("${pdf.storage.directory}")
	private String pdfStorageloc;

	@Override
	public String generatePDF(sendPdfRequest request) {
		String filePath = getFilePath(request);

		try {
			if (checkifFileExists(request)) {
				return "File already exists. Please change the data.";
			}

			Context context = new Context();
			context.setVariable("seller", request.getSeller());
			context.setVariable("sellerAddress", request.getSellerAddress());
			context.setVariable("sellerGstin", request.getSellerGstin());
			context.setVariable("buyer", request.getBuyer());
			context.setVariable("buyerAddress", request.getBuyerAddress());
			context.setVariable("items", request.getItems());

			String htmlContent = engine.process("index", context);

			System.out.println("Generated HTML: " + htmlContent);
			// Convert HTML to PDF using Flying Saucer
			File file = new File(filePath);
			try (FileOutputStream os = new FileOutputStream(file)) {
				ITextRenderer renderer = new ITextRenderer();
				renderer.setDocumentFromString(htmlContent);
				renderer.layout();
				renderer.createPDF(os);
			}

		} catch (IOException e) {
			System.err.println("Error during PDF generation (File I/O issue): " + e.getMessage());

		} catch (Exception e) {
			System.err.println("An unexpected error occurred: " + e.getMessage());

		}

		return filePath;
	}

	public boolean checkifFileExists(sendPdfRequest request) {
		String filePath = getFilePath(request);
		File file = new File(filePath);
		return file.exists();
	}

	public String getFilePath(sendPdfRequest request) {
		return pdfStorageloc + "/" + request.getSeller().replaceAll("\\s+", "_") + "_"
				+ request.getBuyer().replaceAll("\\s+", "_") + ".pdf";
	}
}
