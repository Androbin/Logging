package de.androbin.logging;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public final class LoggingPanel extends JViewport {
  private final JScrollPane scrollPane;
  private final JTextArea text;
  
  public LoggingPanel() {
    setLayout( null );
    scrollPane = new JScrollPane();
    scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
    
    text = new JTextArea();
    text.setEditable( false );
    text.setFont( new Font( "Monospace", Font.PLAIN, 20 ) );
    text.setTabSize( 4 );
    scrollPane.setViewportView( text );
    
    add( scrollPane );
    append();
    
    addComponentListener( new ComponentAdapter() {
      @ Override
      public void componentResized( final ComponentEvent event ) {
        scrollPane.setBounds( new Rectangle( getSize() ) );
        text.setBounds( new Rectangle( scrollPane.getSize() ) );
      }
    } );
  }
  
  @ SuppressWarnings( "resource" )
  public void append() {
    final OutputStream stream = new OutputStream() {
      @ Override
      public void write( final int b ) throws IOException {
        text.append( String.valueOf( (char) b ) );
      }
      
      @ Override
      public void write( final byte[] b, final int off, final int len ) throws IOException {
        text.append( new String( b, off, len ) );
      }
    };
    
    System.setOut( new PrintStream( new TeeOutputStream( System.out, stream ) ) );
    System.setErr( new PrintStream( new TeeOutputStream( System.err, stream ) ) );
  }
  
  public static JFrame inWindow( final String title, final int width, final int height ) {
    final JFrame window = new JFrame( title );
    window.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    window.setSize( width, height );
    window.setLocationRelativeTo( null );
    window.setContentPane( new LoggingPanel() );
    return window;
  }
}