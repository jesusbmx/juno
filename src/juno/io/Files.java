package juno.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 *
 * @author jesus
 */
public final class Files {

  private Files() {
  }
  
  public static byte[] toByteArray(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.toByteArray(in);
    } finally {
      closeQuietly(in);
    }
  }
  
  public static char[] toCharArray(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.toCharArray(in);
    } finally {
      closeQuietly(in);
    }
  }
  
  public static String toString(File file, Charset charset) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.toString(in, charset);
    } finally {
      closeQuietly(in);
    }
  }
  
  public static String toString(File file, String charset) throws IOException {
    return toString(file, Charset.forName(charset));
  }
  
  public static String toString(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.toString(in);
    } finally {
      closeQuietly(in);
    }
  }
  
  public static void write(File file, CharSequence cs, boolean append) throws IOException {
    FileWriter fw = null;
    try {
      fw = new FileWriter(file, append);
      fw.append(cs);
    } finally {
      closeQuietly(fw);
    }
  }
  
   public static void writeByteArray(File file, byte[] data) throws IOException {
    OutputStream out = null;
    try {
      out = new FileOutputStream(file);
      out.write(data, 0, data.length);
    } finally {
      closeQuietly(out);
    }
  }
  
  /** Obtiene la extencion de un archivo. */
  public static String ext(File file) {
    String fileName = file.getName();
    int i = fileName.lastIndexOf('.');
    int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
    if (i > p) return fileName.substring(i + 1);
    return null;
  }
  
  public static void copy(String from, String to) throws IOException {
    OutputStream out = null;
    try {
      out = new FileOutputStream(to);
      copy(new File(from), new File(to));
    } finally {
      closeQuietly(out);
    }
  }
    
  public static void copy(File from, File to) throws IOException {
    OutputStream out = null;
    try {
      out = new FileOutputStream(to);
      copy(from, out);
    } finally {
      closeQuietly(out);
    }
  }
  
  public static void copy(File from, OutputStream out) throws IOException {
    FileInputStream in = null;
    byte[] buffer = null;
    try {
      in = new FileInputStream(from);
      int bytesAvailable = in.available();
      
      int maxBufferSize = 1024 * 1024;
      int bufferSize = Math.min(bytesAvailable, maxBufferSize);
      buffer = IOUtils.getBuf(bufferSize);
      
      int count = in.read(buffer, 0, bufferSize);
      while (count > 0) {
        out.write(buffer, 0, bufferSize);
        bytesAvailable = in.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        count = in.read(buffer, 0, bufferSize);
      }
    } finally {
      IOUtils.returnBuf(buffer);
      closeQuietly(in);
    }
  }

  public static void closeQuietly(Closeable closeable) {
    IOUtils.closeQuietly(closeable);
  }
}
