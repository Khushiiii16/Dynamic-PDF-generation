package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.sendPdfRequest;
import com.example.demo.service.PdfService;

@RestController
public class MainController {

	@Autowired
	private PdfService pdfService;

	@PostMapping("/generatePDF")
	public ResponseEntity<?> generatePDF(@RequestBody sendPdfRequest request) {
		try {
			String responseMsg = pdfService.generatePDF(request);
			if (responseMsg.equals("File already exists. Please change the data.")) {
				return ResponseEntity.ok(responseMsg);
			} else {

				return ResponseEntity.ok("PDF generated at: " + responseMsg);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF");
		}
	}

}
