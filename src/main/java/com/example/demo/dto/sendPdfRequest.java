package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class sendPdfRequest {
	private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;

}
