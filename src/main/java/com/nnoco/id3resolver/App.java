package com.nnoco.id3resolver;

import com.nnoco.id3resolver.v24.Reader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        byte[] buffer = Reader.read("44c14059-f003-4491-a9f7-db562b6b8677.mp3");
        
        Reader.resolve(buffer);
        
        System.out.println(buffer.length);
    }
}
