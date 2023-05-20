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
import juno.util.Objects;

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
  
  public static void write(File file, CharSequence str, boolean append, Charset charset) throws IOException {
    Writer w = null;
    try {
      w = new OutputStreamWriter(new FileOutputStream(file, append), charset);
      w.append(str);
      
    } finally {
      closeQuietly(w);
    }
  }
  
  public static void write(File file, CharSequence str, boolean append, String charset) throws IOException {
    write(file, str, append, Charset.forName(charset));
  }
  
  public static void write(File file, CharSequence str, boolean append) throws IOException {
    Writer w = null;
    try {
      w = new FileWriter(file, append);
      w.append(str);
      
    } finally {
      closeQuietly(w);
    }
  }
  
  public static void write(File file, CharSequence str) throws IOException {
    write(file, str, false);
  }

  public static void writeByteArray(File file, byte[] data, boolean append) throws IOException {
    OutputStream out = null;
    try {
      out = new FileOutputStream(file, append);
      out.write(data, 0, data.length);
      
    } finally {
      closeQuietly(out);
    }
  }
  
  public static void writeByteArray(File file, byte[] data) throws IOException {
    writeByteArray(file, data, false);
  }
  
  public static int indexOfLastSeparator(String fileName) {
    final int lastUnixPos = fileName.lastIndexOf('/');
    final int lastWindowsPos = fileName.lastIndexOf('\\');
    return Math.max(lastUnixPos, lastWindowsPos);
  }
  
  /** Obtiene la extencion de un archivo. */
  public static String getExtension(String fileName) {
    if (Objects.isNull(fileName)) return null;
    final int extensionPos = fileName.lastIndexOf('.');
    final int lastSeparator = indexOfLastSeparator(fileName);
    if (extensionPos > lastSeparator + 1) {
      return fileName.substring(extensionPos + 1);
    }
    return null;
  }
  
  public static String getExtension(File file) {
    if (Objects.isNull(file)) return null;
    return getExtension(file.getName());
  }
  
  public static String getName(String fileName) {
    if (Objects.isNull(fileName)) return null;
    return fileName.substring(indexOfLastSeparator(fileName) + 1);
  }
  
  public static String getName(File file) {
    if (Objects.isNull(file)) return null;
    return getName(file.getName());
  }
  
  public static String getBaseName(String fileName) {
    if (Objects.isNull(fileName)) return null;
    
    final int len = fileName.length();
    final int extensionPos = fileName.lastIndexOf(".");
    final int lastSeparator = indexOfLastSeparator(fileName);
    
    if (extensionPos > lastSeparator + 1) {
      return fileName.substring(lastSeparator + 1, extensionPos);
    }
    
    if (extensionPos == -1) {
      return fileName.substring(lastSeparator + 1, len);
    }
    
    if (extensionPos == lastSeparator + 1) {
      return fileName.substring(lastSeparator + 1, len);
    }
    
    return null;
  }
  
  public static String getBaseName(File file) {
    if (Objects.isNull(file)) return null;
    return getBaseName(file.getName());
  }
  
  public static String getParent(String fileName) {
    if (Objects.isNull(fileName)) return null;
    final int lastSeparator = indexOfLastSeparator(fileName);
    if (lastSeparator != -1) {
      return fileName.substring(0, lastSeparator + 1);
    }
    return null;
  }
  
  public static String getParent(File file) {
    if (Objects.isNull(file)) return null;
    return getParent(file.toString());
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
      
      final int maxBufferSize = 1024 * 1024;
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
