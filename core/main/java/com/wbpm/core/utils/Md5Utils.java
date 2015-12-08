package com.wbpm.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils
{
  private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', 
    '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  protected static MessageDigest messagedigest = null;

  static
  {
    try
    {
      messagedigest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  public static String stringMd5(String input, String charSet)
  {
    MessageDigest md;
    try
    {
      md = MessageDigest.getInstance("MD5");
      md.update(input.getBytes(charSet));
      byte[] b = md.digest();

      StringBuffer buf = new StringBuffer("");
      for (int offset = 0; offset < b.length; ++offset) {
        int i = b[offset];
        if (i < 0)
          i += 256;

        if (i < 16)
          buf.append("0");

        buf.append(Integer.toHexString(i & 0xFF));
      }
      return buf.toString().toUpperCase(); } catch (Exception e) {
    }
    return "";
  }

  public static String stringMd5(String input) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(input.getBytes());
      byte[] b = md.digest();

      StringBuffer buf = new StringBuffer("");
      for (int offset = 0; offset < b.length; ++offset) {
        int i = b[offset];
        if (i < 0)
          i += 256;

        if (i < 16)
          buf.append("0");

        buf.append(Integer.toHexString(i & 0xFF));
      }
      return buf.toString().toUpperCase(); } catch (Exception e) {
    }
    return "";
  }

  public static String fileMd5(File file)
    throws IOException
  {
    InputStream fis = new FileInputStream(file);
    byte[] buffer = new byte[1024];
    int numRead = 0;
    while ((numRead = fis.read(buffer)) > 0)
      messagedigest.update(buffer, 0, numRead);

    fis.close();
    return bufferToHex(messagedigest.digest());
  }

  public static String bytesMd5(byte[] bytes) {
    messagedigest.update(bytes);
    return bufferToHex(messagedigest.digest());
  }

  private static String bufferToHex(byte[] bytes) {
    return bufferToHex(bytes, 0, bytes.length);
  }

  private static String bufferToHex(byte[] bytes, int m, int n) {
    StringBuffer stringbuffer = new StringBuffer(2 * n);
    int k = m + n;
    for (int l = m; l < k; ++l)
      appendHexPair(bytes[l], stringbuffer);

    return stringbuffer.toString();
  }

  private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
    char c0 = hexDigits[((bt & 0xF0) >> 4)];

    char c1 = hexDigits[(bt & 0xF)];
    stringbuffer.append(c0);
    stringbuffer.append(c1);
  }
  
  public static void main(String [] args)
  {
	  System.out.println( stringMd5("touna").toLowerCase());
  }
}