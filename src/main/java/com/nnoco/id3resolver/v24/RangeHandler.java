package com.nnoco.id3resolver.v24;

public interface RangeHandler<T> {
	public T handle(byte[] array, int start, int end);
}
