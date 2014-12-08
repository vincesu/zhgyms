/*  1:   */package com.vincesu.framework.util;
/*  2:   */
/*  3:   */import java.security.MessageDigest;
/*  4:   */
/*  5:   */public class Encript
/*  6:   */{
/*  7: 7 */  private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
/*  8:   */  
/*  9:   */  public static String md5(String inputStr)
/* 10:   */  {
/* 11:11 */    return encodeByMD5(inputStr);
/* 12:   */  }
/* 13:   */  
/* 19:   */  public static boolean authenticatePassword(String password, String inputString)
/* 20:   */  {
/* 21:21 */    if (password.equals(encodeByMD5(inputString))) {
/* 22:22 */      return true;
/* 23:   */    }
/* 24:24 */    return false;
/* 25:   */  }
/* 26:   */  
/* 28:   */  private static String encodeByMD5(String originString)
/* 29:   */  {
/* 30:30 */    if (originString != null) {
/* 31:   */      try
/* 32:   */      {
/* 33:33 */        MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 34:   */        
/* 35:35 */        byte[] results = md5.digest(originString.getBytes());
/* 36:   */        
/* 37:37 */        return byteArrayToHexString(results);
/* 38:   */      }
/* 39:   */      catch (Exception e) {
/* 40:40 */        e.printStackTrace();
/* 41:   */      }
/* 42:   */    }
/* 43:43 */    return null;
/* 44:   */  }
/* 45:   */  
/* 50:   */  private static String byteArrayToHexString(byte[] b)
/* 51:   */  {
/* 52:52 */    StringBuffer resultSb = new StringBuffer();
/* 53:53 */    for (int i = 0; i < b.length; i++) {
/* 54:54 */      resultSb.append(byteToHexString(b[i]));
/* 55:   */    }
/* 56:56 */    return resultSb.toString();
/* 57:   */  }
/* 58:   */  
/* 59:   */  private static String byteToHexString(byte b)
/* 60:   */  {
/* 61:61 */    int n = b;
/* 62:62 */    if (n < 0)
/* 63:63 */      n += 256;
/* 64:64 */    int d1 = n / 16;
/* 65:65 */    int d2 = n % 16;
/* 66:66 */    return hexDigits[d1] + hexDigits[d2];
/* 67:   */  }
/* 68:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.Encript
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */