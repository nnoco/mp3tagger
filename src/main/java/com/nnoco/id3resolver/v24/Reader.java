package com.nnoco.id3resolver.v24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Reader {
	private static final int FRAME_HEADER_BYTES = 10;
	public static final byte UNSYNCHRONISATION_FLAG = (byte)0x80;
	public static final byte EXTENDED_HEADER_FLAG = (byte)0x40;
	public static final byte EXPERIMENTAL_INDICATOR_FLAG = (byte)0x20;
	public static final byte FOOTER_PRESENT_FLAG = (byte)0x10;

	public static byte[] read(String file) {
		try {
			return Files.readAllBytes(Paths.get(file));
		} catch (IOException e) {
			return new byte[0];
		}
	}
	
	public static void resolve(byte[] contents) {
		// 헤더 10바이트
		// ID3v2 header
		// ID3v2/file identifier
		byte[] identifier = slice(contents, 0, 3);
		System.out.println(new String(identifier));
		
		// ID3v2 version
		byte[] version = slice(contents, 3, 2);
		
		// ID3v2 flags
		byte flags = contents[5];
		
		// ID3v2 size
		byte[] sizeArray = slice(contents, 6, 4);
		int size = synchsafeToInteger(sizeArray);
		System.out.println("Header size : " + size);
		
		byte[] tagBody = slice(contents, 10, size);
		
		boolean hasExtendedHeader = hasExtendedHeader(flags);
		
		System.out.println(hasExtendedHeader);
		
		if (!hasExtendedHeader) {
			resolveFrame(tagBody);
		}
		
		
		// Extended Header
		
		// Frames
		
		// Padding
		
		// Footer
	}

	private static void resolveFrame(byte[] tagBody) {
		for (int cursor=0 ; cursor < tagBody.length ; ) {
			// 프레임 헤더 처리
			ID3v2Frame header = resolveFrameHeader(tagBody, cursor);
			
			if (header.getFrameId().startsWith("T")) {
				header = new ID3v2TextFrame(header.getFrameId(), header.getSize(), header.getFlags(), header.getBody());
			} else if(header.getFrameId().equals("USLT")) {
				header = new ID3v2UnsynchronisedLyricsFrame(header.getFrameId(), header.getSize(), header.getFlags(), header.getBody());
			}
			System.out.println(header);
			
			cursor += FRAME_HEADER_BYTES + header.getSize();
			
		}
		
	}
	
	private static ID3v2Frame resolveFrameHeader(byte[] frames, int cursor) {
		// 아이디 4바이트
		String frameId = new String(slice(frames, cursor, 4));
		cursor += 4;
		
		// 길이
		int size = synchsafeToInteger(slice(frames, cursor, 4));
		cursor += 4;
		
		// 플래그
		byte[] flags = slice(frames, cursor, 2);
		cursor += 2;
		
		// body
		byte[] body = slice(frames, cursor, size);
		
		return new ID3v2Frame(frameId, size, flags, body);
	}

	private static boolean hasExtendedHeader(byte flag) {
		return hasFlag(flag, EXTENDED_HEADER_FLAG);
	}
	
	private static boolean hasFooter(byte flag) {
		return hasFlag(flag, FOOTER_PRESENT_FLAG);
	}
	
	private static boolean hasFlag(byte flag, byte target) {
		return (flag & target) != 0;
	}

	private static byte[] slice(byte[] contents, int startIndex, int size) {
		return Arrays.copyOfRange(contents, startIndex, startIndex + size);
	}

	private static int synchsafeToInteger(byte[] sizeArray) {
		if (sizeArray == null || sizeArray.length != 4) throw new RuntimeException("Size 배열은 길이가 4여야 합니다.");
		int synchsafeMask = 0x7F;
		int value = 0;
		int length = 7;
		value = sizeArray[0] << length * 3;
		for (int i=0; i<4 ; i++) {
			value |= (sizeArray[i] & synchsafeMask) << length * (3-i);
		}
		
		return value;
	}
}
