package com.nnoco.id3resolver.v24;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ID3v2TextFrame extends ID3v2Frame {
	private String text;

	private String textEncoding;

	public ID3v2TextFrame(String frameId, int size, byte[] flags, byte[] body) {
		super(frameId, size, flags, body);

		bodyToText();
	}

	private void bodyToText() {
		byte[] body = getBody();

		textEncoding = TextEncoding.valueOf(body[0]).getEncoding();
		try {
			text = new String(Arrays.copyOfRange(body, 1, body.length), textEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTextEncoding() {
		return textEncoding;
	}

	public void setTextEncoding(String textEncoding) {
		this.textEncoding = textEncoding;
	}

	@Override
	public String toString() {
		return super.toString() + "ID3v2TextFrame [text=" + text + ", textEncoding="
				+ textEncoding + "]";
	}
	
	

}
