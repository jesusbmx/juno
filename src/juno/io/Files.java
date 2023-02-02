package juno.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 *
 * @author jesus
 */
public final class Files {

  private Files() {
  }
  
  public static byte[] readByteArray(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.readByteArray(in);
      
    } finally {
      closeQuietly(in);
    }
  }
  
  public static char[] readCharArray(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.readCharArray(in);
    } finally {
      closeQuietly(in);
    }
  }
  
  public static String readString(File file, Charset charset) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.readString(in, charset);
      
    } finally {
      closeQuietly(in);
    }
  }
  
  public static String readString(File file, String charset) throws IOException {
    return readString(file, Charset.forName(charset));
  }
  
  public static String readString(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return IOUtils.readString(in);
      
    } finally {
      closeQuietly(in);
    }
  }
  
  public static void write(File file, CharSequence cs, boolean append, Charset charset) throws IOException {
    Writer w = null;
    try {
      w = new OutputStreamWriter(new FileOutputStream(file, append), charset);
      w.append(cs);
      
    } finally {
      closeQuietly(w);
    }
  }
  
  public static void write(File file, CharSequence cs, boolean append, String charset) throws IOException {
    write(file, cs, append, Charset.forName(charset));
  }
  
  public static void write(File file, CharSequence cs, boolean append) throws IOException {
    Writer w = null;
    try {
      w = new FileWriter(file, append);
      w.append(cs);
      
    } finally {
      closeQuietly(w);
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
  public static String ext(String fileName) {
    int dot = fileName.lastIndexOf('.');
    int sep = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
    if (dot > sep) return fileName.substring(dot + 1);
    return null;
  }
  
  public static String ext(File file) {
    return ext(file.getName());
  }
  
  public static String basename(String fileName) {
    int dot = fileName.lastIndexOf(".");
    int sep = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
    if (dot > sep) return fileName.substring(sep + 1, dot);
    return null;
  }
  
  public static String basename(File file) {
    return basename(file.getName());
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
