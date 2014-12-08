/*  1:   */package com.vincesu.framework.util;
/*  2:   */
/*  3:   */import java.io.OutputStream;
/*  4:   */import java.io.PrintStream;
/*  5:   */import java.util.List;
/*  6:   */import jxl.Workbook;
/*  7:   */import jxl.write.Label;
/*  8:   */import jxl.write.WritableSheet;
/*  9:   */import jxl.write.WritableWorkbook;
/* 10:   */
/* 11:   */public class ExcelUtil
/* 12:   */{
/* 13:   */  public static void exportExcel(OutputStream os, String sheetName, String[] titles, List<Object[]> datas)
/* 14:   */    throws Exception
/* 15:   */  {
/* 16:16 */    WritableWorkbook workbook = null;
/* 17:   */    
/* 18:   */    try
/* 19:   */    {
/* 20:20 */      workbook = Workbook.createWorkbook(os);
/* 21:21 */      WritableSheet sheet = workbook.createSheet(sheetName, 0);
/* 22:   */      
/* 23:23 */      Label label = null;
/* 24:24 */      int i = 0;int j = 0;
/* 25:   */      
/* 26:26 */      for (i = 0; i < titles.length; i++)
/* 27:   */      {
/* 28:28 */        label = new Label(i, 0, titles[i]);
/* 29:29 */        sheet.addCell(label);
/* 30:   */      }
/* 31:   */      
/* 32:32 */      for (i = 0; i < datas.size(); i++)
/* 33:   */      {
/* 34:34 */        for (j = 0; j < ((Object[])datas.get(i)).length; j++)
/* 35:   */        {
/* 36:36 */          if (((Object[])datas.get(i))[j] != null)
/* 37:   */          {
/* 38:38 */            label = new Label(j, i + 1, ((Object[])datas.get(i))[j].toString());
/* 39:39 */            sheet.addCell(label);
/* 40:   */          }
/* 41:   */        }
/* 42:   */      }
/* 43:   */    }
/* 44:   */    catch (Exception e)
/* 45:   */    {
/* 46:46 */      System.out.println(e);
/* 47:   */    }
/* 48:   */    finally
/* 49:   */    {
/* 50:50 */      if (workbook != null)
/* 51:   */      {
/* 52:52 */        workbook.write();
/* 53:53 */        workbook.close();
/* 54:   */      }
/* 55:   */    }
/* 56:   */  }
/* 57:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.ExcelUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */