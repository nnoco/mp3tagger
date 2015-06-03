package com.nnoco.id3resolver.v24;

import java.util.Arrays;

public class ID3v2Frame {
	private String frameId;
	private int size;
	private byte[] flags;
	private byte[] body;

	public ID3v2Frame() {
	}

	public ID3v2Frame(String frameId, int size, byte[] flags, byte[] body) {
		this.frameId = frameId;
		this.size = size;
		this.flags = flags;
		this.body = body;
	}

	public String getFrameId() {
		return frameId;
	}

	public void setFrameId(String frameId) {
		this.frameId = frameId;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getFlags() {
		return flags;
	}

	public void setFlags(byte[] flags) {
		this.flags = flags;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ID3v2FrameHeader [frameId=" + frameId + ", size=" + size
				+ ", flags=" + Arrays.toString(flags) + "]";
	}
	
	

}
