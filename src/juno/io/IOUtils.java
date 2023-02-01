package juno.io;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public final class IOUtils {

  public static final ByteArrayPool BYTE_ARRAY_POOL = new ByteArrayPool(4096);
  public static final CharArrayPool CHAR_ARRAY_POOL = new CharArrayPool(4096);

  private IOUtils() {
  }
  
  public static ByteArrayOutputStream arrayOutputStream() {
    return new PoolingByteArrayOutputStream(BYTE_ARRAY_POOL);
  }
  public static ByteArrayOutputStream arrayOutputStream(int size) {
    return new PoolingByteArrayOutputStream(BYTE_ARRAY_POOL, size);
  }

  public static byte[] getBuf(int bufferSize) {
    return BYTE_ARRAY_POOL.getBuf(bufferSize);
  }
  public static void returnBuf(byte[] buffer) {
    BYTE_ARRAY_POOL.returnBuf(buffer);
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
  
  public static byte[] readByteArray(InputStream source) throws IOException {
    return readByteArray(source, 1024);
  }
  public static byte[] readByteArray(InputStream source, int size) throws IOException {
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
  
  public static char[] readCharArray(Reader reader) throws IOException {
    final CharArrayWriter chars = new PoolingCharArrayWriter(CHAR_ARRAY_POOL);
    char[] buffer = CHAR_ARRAY_POOL.getBuf(1024);
    try {
       
        int len;
        while ((len = reader.read(buffer, 0, buffer.length)) != -1) {
          chars.write(buffer, 0, len);
        }
        return chars.toCharArray();
   
    } finally {
        CHAR_ARRAY_POOL.returnBuf(buffer);
        chars.close();
    }
  }
  
  public static char[] readCharArray(InputStream in) throws IOException {
    return readCharArray(new InputStreamReader(in));
  }
  
  public static String readString(Reader reader) throws IOException {
    char[] buffer = CHAR_ARRAY_POOL.getBuf(1024);
    try {
        final StringBuilder result = new StringBuilder();
        int len;
        while ((len = reader.read(buffer, 0, buffer.length)) != -1) {
          result.append(buffer, 0, len);
        }
        return result.toString();
   
    } finally {
        CHAR_ARRAY_POOL.returnBuf(buffer);
    }
  }
  
  public static String readString(InputStream in) throws IOException {
    return readString(new InputStreamReader(in));
  }
   
  public static String readString(InputStream in, String charset) throws IOException {
    return readString(in, Charset.forName(charset));
  }
  
  public static String readString(InputStream in, Charset charset) throws IOException {
    return readString(new InputStreamReader(in, charset));
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