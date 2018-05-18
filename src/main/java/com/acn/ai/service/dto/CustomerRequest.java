package com.acn.ai.service.dto;

import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult;

public class CustomerRequest {
	private String requestInText;
	private DetectKeyPhrasesResult keyPhrases;
	
	public CustomerRequest() {
		
	}
	
	public String getRequestInText() {
		return requestInText;
	}

	public void setRequestInText(String requestInText) {
		this.requestInText = requestInText;
	}

	public DetectKeyPhrasesResult getKeyPhrases() {
		return keyPhrases;
	}
	public void setKeyPhrases(DetectKeyPhrasesResult keyPhrases) {
		this.keyPhrases = keyPhrases;
	}
}
