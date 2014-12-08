/*  1:   */package com.vincesu.framework.util;
/*  2:   */
/*  3:   */import java.security.ProtectionDomain;
/*  4:   */
/*  5:   */public class SystemUtil
/*  6:   */{
/*  7:   */  public static String getLocation()
/*  8:   */  {
/*  9: 9 */    java.io.File file = new java.io.File(SystemUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile());
/* 10:10 */    for (int i = 0; i < 7; i++)
/* 11:11 */      file = file.getParentFile();
/* 12:12 */    String result = file.getAbsolutePath();
/* 13:13 */    if (result.endsWith("/"))
/* 14:14 */      result.substring(0, result.length() - 1);
/* 15:15 */    return file.getAbsolutePath();
/* 16:   */  }
/* 17:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.SystemUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */