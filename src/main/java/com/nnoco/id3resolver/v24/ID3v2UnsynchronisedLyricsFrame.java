package com.nnoco.id3resolver.v24;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ID3v2UnsynchronisedLyricsFrame extends ID3v2Frame {
	private String lyrics;

	private String textEncoding;
	
	private String language;
	
	private String contentDescriptor;

	public ID3v2UnsynchronisedLyricsFrame(String frameId, int size, byte[] flags, byte[] body) {
		super(frameId, size, flags, body);

		try {
			bodyToText();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void bodyToText() throws UnsupportedEncodingException {
		byte[] body = getBody();
		
		// 인코딩 1바이트
		textEncoding = TextEncoding.valueOf(body[0]).getEncoding();
		
		// 언어 3바이트
		language = new String(Arrays.copyOfRange(body, 1, 1 + 3));
		
		// 컨텐츠 설명 $00 (00) 로 끝 
		byte[] part = Arrays.copyOfRange(body, 4, body.length);
		
		TextEncoding enc = TextEncoding.valueOf(body[0]);
		int endIndex = 0;
		if (enc.getEndBytes() == 1) {
			for (int i=0 ; i< part.length ; i++) {
				if (part[i] == 0) {
					endIndex = i;
					break;
				}
			}
		} else {
			for (int i=0 ; i<part.length-1 ; i++) {
				if (part[i] == 0 && part[i+1] == 0) {
					endIndex = i+1;
					break;
				}
			}
		}
		
		endIndex += 1;
		
		contentDescriptor = new String(Arrays.copyOfRange(part, 0, endIndex), textEncoding);
		
		// 가사 내용
		lyrics = new String(Arrays.copyOfRange(part, endIndex, part.length), textEncoding);
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public String getTextEncoding() {
		return textEncoding;
	}

	public void setTextEncoding(String textEncoding) {
		this.textEncoding = textEncoding;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContentDescriptor() {
		return contentDescriptor;
	}

	public void setContentDescriptor(String contentDescriptor) {
		this.contentDescriptor = contentDescriptor;
	}

	@Override
	public String toString() {
		return "ID3v2UnsynchronisedLyricsFrame [lyrics=" + lyrics
				+ ", textEncoding=" + textEncoding + ", language=" + language
				+ ", contentDescriptor=" + contentDescriptor
				+ ", getFrameId()=" + getFrameId() + ", getSize()=" + getSize()
				+ ", getFlags()=" + Arrays.toString(getFlags()) + "]";
	}
	
}
