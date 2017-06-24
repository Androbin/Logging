package de.androbin.logging;

import java.io.*;

public final class TeeOutputStream extends OutputStream {
  private final OutputStream[] streams;
  
  public TeeOutputStream( final OutputStream ... streams ) {
    this.streams = streams;
  }
  
  @ Override
  public void close() throws IOException {
    for ( final OutputStream stream : streams ) {
      stream.close();
    }
  }
  
  @ Override
  public void flush() throws IOException {
    for ( final OutputStream stream : streams ) {
      stream.flush();
    }
  }
  
  @ Override
  public void write( final int b ) throws IOException {
    for ( final OutputStream stream : streams ) {
      stream.write( b );
    }
  }
  
  @ Override
  public void write( final byte[] b, final int off, final int len ) throws IOException {
    for ( final OutputStream stream : streams ) {
      stream.write( b, off, len );
    }
  }
}