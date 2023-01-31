package juno.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
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
  
  public static byte[] readBytes(InputStream source) throws IOException {
    return readBytes(source, 1024);
  }
  public static byte[] readBytes(InputStream source, int size) throws IOException {
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
  
  public static char[] readChars(InputStream in) throws IOException {
    byte[] data = readBytes(in);
    char[] chars = new char[data.length];
    for (int i = 0; i < data.length; i++) {
      chars[i] = (char) data[i];      
    }
    return chars;
  }
  
  public static String readString(InputStream in) throws IOException {
    byte[] data = readBytes(in);
    return new String(data);
  }
   
  public static String readString(InputStream in, String charset) throws IOException {
    return IOUtils.readString(in, Charset.forName(charset));
  }
  
  public static String readString(InputStream in, Charset charset) throws IOException {
    byte[] data = readBytes(in);
    return new String(data, charset);
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