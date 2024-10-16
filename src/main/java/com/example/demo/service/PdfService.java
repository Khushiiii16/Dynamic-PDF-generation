package com.example.demo.service;

import com.example.demo.dto.sendPdfRequest;

public interface PdfService {

	public String generatePDF(sendPdfRequest request);
}
