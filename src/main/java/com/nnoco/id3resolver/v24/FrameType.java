package com.nnoco.id3resolver.v24;

public enum FrameType {

	;
	
	private String frameId;
	private String description;
	private int[] ranges;
	private RangeHandler<?>[] handlers;
}
