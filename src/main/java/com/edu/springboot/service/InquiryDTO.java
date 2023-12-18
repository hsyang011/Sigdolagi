package com.edu.springboot.service;

import lombok.Data;

@Data
public class InquiryDTO {
	
	private String inquiryboard_idx;
	private String email;
	private String category;
	private String title;
	private String content;
	private String regidate;
	private String postdate;
	private String nickname;
	
	
	
}
