package com.nnoco.id3resolver.v24;

public enum TextEncoding {
	ISO_8859_1(0x00, "ISO-8859-1", false, 1), UTF_16(0x01, "UTF-16", true, 2), UTF_16BE(
			0x02, "UTF-16BE", false, 2), UTF_8(0x03, "UTF-8", false, 1)

	;

	TextEncoding(int code, String encoding, boolean isBom, int endBytes) {
		this.code = code;
		this.encoding = encoding;
		this.bom = isBom;
		this.endBytes = endBytes;
	}

	public final int code;
	public final String encoding;
	private final boolean bom;
	private final int endBytes;

	public int getCode() {
		return code;
	}

	public String getEncoding() {
		return encoding;
	}

	public boolean isBom() {
		return bom;
	}

	public int getEndBytes() {
		return endBytes;
	}

	public static TextEncoding valueOf(int code) {
		switch (code) {
		case 0x00:
			return ISO_8859_1;
		case 0x01:
			return UTF_16;
		case 0x02:
			return UTF_16BE;
		case 0x03:
			return UTF_8;
		default:
			throw new RuntimeException(
					"잘못된 텍스트 인코딩 코드 값입니다. 유효한 값은 0x00 ~ 0x03 입니다.");
		}
	}
}
