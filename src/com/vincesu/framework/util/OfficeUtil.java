/*  1:   */package com.vincesu.framework.util;
/*  2:   */
/*  3:   */import com.artofsolving.jodconverter.DocumentConverter;
/*  4:   */import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
/*  5:   */import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
/*  6:   */import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
/*  7:   */import java.io.File;
/*  8:   */import java.io.IOException;
/*  9:   */import java.net.ConnectException;
/* 10:   */
/* 12:   */public class OfficeUtil
/* 13:   */{
/* 14:   */  public static int office2PDF(String sourceFile, String destFile)
/* 15:   */  {
/* 16:16 */    File inputFile = new File(sourceFile);
/* 17:17 */    File outputFile = new File(destFile);
/* 18:18 */    return office2PDF(inputFile, outputFile);
/* 19:   */  }
/* 20:   */  
/* 27:   */  public static int office2PDF(File sourceFile, File destFile)
/* 28:   */  {
/* 29:   */    try
/* 30:   */    {
/* 31:31 */      File inputFile = sourceFile;
/* 32:32 */      if (!inputFile.exists())
/* 33:   */      {
/* 34:34 */        return -1;
/* 35:   */      }
/* 36:   */      
/* 38:38 */      if (!destFile.getParentFile().exists())
/* 39:   */      {
/* 40:40 */        destFile.getParentFile().mkdirs();
/* 41:   */      }
/* 42:   */      
/* 43:43 */      OpenOfficeConnection connection = new SocketOpenOfficeConnection(
/* 44:44 */        "127.0.0.1", 8100);
/* 45:45 */      connection.connect();
/* 46:   */      
/* 48:48 */      DocumentConverter converter = new OpenOfficeDocumentConverter(
/* 49:49 */        connection);
/* 50:50 */      converter.convert(inputFile, destFile);
/* 51:   */      
/* 53:53 */      connection.disconnect();
/* 54:   */      
/* 55:55 */      return 0;
/* 56:   */    }
/* 57:   */    catch (ConnectException e)
/* 58:   */    {
/* 59:59 */      e.printStackTrace();
/* 60:   */    }
/* 61:   */    catch (IOException e)
/* 62:   */    {
/* 63:63 */      e.printStackTrace();
/* 64:   */    }
/* 65:   */    
/* 66:66 */    return 1;
/* 67:   */  }
/* 68:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.OfficeUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */