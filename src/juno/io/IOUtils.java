package juno.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public final class IOUtils {

  public static final ByteArrayPool POOL = new ByteArrayPool(4096);

  private IOUtils() {
  }
  
  public static ByteArrayOutputStream arrayOutputStream() {
    return new PoolingByteArrayOutputStream(POOL);
  }
  public static ByteArrayOutputStream arrayOutputStream(int size) {
    return new PoolingByteArrayOutputStream(POOL, size);
  }

  public static byte[] getBuf(int bufferSize) {
    return POOL.getBuf(bufferSize);
  }
  public static void returnBuf(byte[] buffer) {
    POOL.returnBuf(buffer);
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
      buffer = getBuf(bufferSize);
      
      int count = in.read(buffer, 0, bufferSize);
      while (count > 0) {
        out.write(buffer, 0, bufferSize);
        bytesAvailable = in.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        count = in.read(buffer, 0, bufferSize);
      }
    } finally {
      returnBuf(buffer);
      closeQuietly(in);
    }
  }

  public static void copy(InputStream from, OutputStream out) throws IOException {
    if (from == null) throw new IOException("source == null");
    byte[] buffer = getBuf(1024);
    try {
      int count;
      while ((count = from.read(buffer)) != -1) {
        out.write(buffer, 0, count);
      }
    } finally {
      returnBuf(buffer);
    }
  }
  
  public static InputStream inputStream(final HttpURLConnection hurlc) {
    InputStream inputStream;
    try {
      inputStream = hurlc.getInputStream();
    } catch(IOException e) {
      inputStream = hurlc.getErrorStream();
    }
    return new FilterInputStream(inputStream) {
      @Override public void close() {
        closeQuietly(in);
        hurlc.disconnect();
      }
    };
  }
  
  public static byte[] toByteArray(InputStream source) throws IOException {
    return toByteArray(source, 1024);
  }
  public static byte[] toByteArray(InputStream source, int size) throws IOException {
    if (source == null) throw new IOException("source == null");
    ByteArrayOutputStream bytes = arrayOutputStream(source.available());
    byte[] buffer = getBuf(size);
    try {
      int count;
      while ((count = source.read(buffer)) != -1) {
        bytes.write(buffer, 0, count);
      }
      return bytes.toByteArray();
    } finally {
      returnBuf(buffer);
      bytes.close();
    }
  }
  public static byte[] toByteArray(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return toByteArray(in);
    } finally {
      closeQuietly(in);
    }
  }
  
  public static String toString(InputStream in) throws IOException {
    byte[] data = toByteArray(in);
    return new String(data);
  }
  public static String toString(InputStream in, Charset charset) throws IOException {
    byte[] data = toByteArray(in);
    return new String(data, charset);
  }
  public static String toString(InputStream in, String charset) throws IOException {
    return toString(in, Charset.forName(charset));
  }
  
  public static String toString(File file) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return toString(in);
    } finally {
      closeQuietly(in);
    }
  }
  public static String toString(File file, Charset charset) throws IOException {
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      return toString(in, charset);
    } finally {
      closeQuietly(in);
    }
  }
  public static String toString(File file, String charset) throws IOException {
    return toString(file, Charset.forName(charset));
  }
  
  /** Obtiene la extencion de un archivo. */
  public static String ext(File file) {
    String fileName = file.getName();
    int i = fileName.lastIndexOf('.');
    int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
    if (i > p) return fileName.substring(i + 1);
    return null;
  }

  public static void closeQuietly(Closeable closeable) {
    if (closeable == null) return;
    try {
      closeable.close();
    } catch (IOException ignore) {
      // empty
    }
  }
}