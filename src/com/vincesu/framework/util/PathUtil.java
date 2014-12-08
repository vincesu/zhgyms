/*   1:    */package com.vincesu.framework.util;
/*   2:    */
/*   3:    */import com.vincesu.persistence.PMF;
/*   4:    */import java.io.File;
/*   5:    */import java.io.FileInputStream;
/*   6:    */import java.io.FileOutputStream;
/*   7:    */import java.io.IOException;
/*   8:    */import java.io.InputStream;
/*   9:    */import java.io.OutputStream;
/*  10:    */import org.apache.tools.zip.ZipEntry;
/*  11:    */import org.apache.tools.zip.ZipOutputStream;
/*  12:    */
/*  13:    */public class PathUtil
/*  14:    */{
/*  15:    */  public static void write(InputStream is, OutputStream os)
/*  16:    */    throws IOException
/*  17:    */  {
/*  18: 18 */    if ((is == null) || (os == null)) {
/*  19: 19 */      return;
/*  20:    */    }
/*  21: 21 */    byte[] bytes = new byte[1024];
/*  22: 22 */    int index = is.read(bytes);
/*  23: 23 */    while (-1 != index)
/*  24:    */    {
/*  25: 25 */      os.write(bytes);
/*  26: 26 */      index = is.read(bytes);
/*  27:    */    }
/*  28:    */  }
/*  29:    */  
/*  30:    */  public static void saveFile(FileInputStream fis, File file)
/*  31:    */    throws IOException
/*  32:    */  {
/*  33: 33 */    FileOutputStream fos = null;
/*  34:    */    try
/*  35:    */    {
/*  36: 36 */      if (fis == null) {
/*  37: 37 */        return;
/*  38:    */      }
/*  39: 39 */      fos = new FileOutputStream(file);
/*  40: 40 */      byte[] bytes = new byte[1024];
/*  41:    */      
/*  42:    */      int len;
/*  43: 43 */      while ((len = fis.read(bytes)) > 0) { 
/*  44: 44 */        fos.write(bytes, 0, len);
/*  45:    */      }
/*  46:    */    }
/*  47:    */    finally
/*  48:    */    {
/*  49: 49 */      if (fos != null)
/*  50:    */      {
/*  51: 51 */        fos.flush();
/*  52: 52 */        fos.close();
/*  53:    */      }
/*  54:    */    }
/*  55: 49 */    if (fos != null)
/*  56:    */    {
/*  57: 51 */      fos.flush();
/*  58: 52 */      fos.close();
/*  59:    */    }
/*  60:    */    
/*  61: 55 */    if (fos != null)
/*  62:    */    {
/*  63: 57 */      fos.flush();
/*  64: 58 */      fos.close();
/*  65:    */    }
/*  66:    */  }
/*  67:    */  
/*  68:    */  public static void saveFile(FileInputStream fis, String path)
/*  69:    */    throws IOException
/*  70:    */  {
/*  71: 65 */    File file = new File(path);
/*  72: 66 */    saveFile(fis, file);
/*  73:    */  }
/*  74:    */  
/*  75:    */  public static String getExtensionName(String filename)
/*  76:    */  {
/*  77: 71 */    if ((filename != null) && (filename.length() > 0))
/*  78:    */    {
/*  79: 73 */      int dot = filename.lastIndexOf('.');
/*  80: 74 */      if ((dot > -1) && (dot < filename.length() - 1))
/*  81:    */      {
/*  82: 76 */        return filename.substring(dot + 1);
/*  83:    */      }
/*  84:    */    }
/*  85: 79 */    return filename;
/*  86:    */  }
/*  87:    */  
/*  88:    */  public static String getFileNameNoEx(String filename)
/*  89:    */  {
/*  90: 84 */    if ((filename != null) && (filename.length() > 0))
/*  91:    */    {
/*  92: 86 */      int dot = filename.lastIndexOf('.');
/*  93: 87 */      if ((dot > -1) && (dot < filename.length()))
/*  94:    */      {
/*  95: 89 */        return filename.substring(0, dot);
/*  96:    */      }
/*  97:    */    }
/*  98: 92 */    return filename;
/*  99:    */  }
/* 100:    */  
/* 101:    */  public static boolean exportFiles(PMF pmf, ZipOutputStream zos, String sourceFilePath, String path, String filename)
/* 102:    */    throws IOException
/* 103:    */  {
/* 104: 98 */    if (new File(sourceFilePath).exists())
/* 105:    */    {
/* 106:100 */      ZipEntry entry = new ZipEntry(path + filename);
/* 107:101 */      zos.putNextEntry(entry);
/* 108:102 */      FileInputStream in = new FileInputStream(sourceFilePath);
/* 109:103 */      int nNumber = 0;
/* 110:104 */      byte[] buffer = new byte[512];
/* 111:105 */      while ((nNumber = in.read(buffer)) != -1) {
/* 112:106 */        zos.write(buffer, 0, nNumber);
/* 113:    */      }
/* 114:108 */      in.close();
/* 115:109 */      return true;
/* 116:    */    }
/* 117:    */    
/* 118:112 */    return false;
/* 119:    */  }
/* 120:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.PathUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */