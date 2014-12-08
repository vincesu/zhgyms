/*  1:   */package com.vincesu.framework.util;
/*  2:   */
/*  3:   */import com.vincesu.framework.entity.SysEncoding;
/*  4:   */import com.vincesu.persistence.PMF;
/*  5:   */import java.util.List;
/*  6:   */
/*  8:   */public class EncodingUtil
/*  9:   */{
/* 10:   */  public static boolean exists(PMF pmf, String field_name, String field_value)
/* 11:   */  {
/* 12:12 */    long c = pmf.count("select 1 from sys_encoding where field_name='" + field_name + "' and " + 
/* 13:13 */      "field_value='" + field_value + "'");
/* 14:14 */    if (c > 0L) {
/* 15:15 */      return true;
/* 16:   */    }
/* 17:17 */    return false;
/* 18:   */  }
/* 19:   */  
/* 29:   */  public static boolean update(PMF pmf, String field_name, String field_value, String coding_value, int param)
/* 30:   */  {
/* 31:31 */    List<SysEncoding> list = pmf.list("from SysEncoding where FieldName='" + field_name + 
/* 32:32 */      "' and FieldValue='" + field_value + "'");
/* 33:33 */    if (list.isEmpty())
/* 34:   */    {
/* 35:35 */      SysEncoding se = new SysEncoding();
/* 36:36 */      se.setCodingValue(coding_value);
/* 37:37 */      se.setFieldName(field_name);
/* 38:38 */      se.setFieldValue(field_value);
/* 39:39 */      pmf.save(se);
/* 42:   */    }
/* 43:43 */    else if (param == 1)
/* 44:   */    {
/* 45:45 */      SysEncoding se = (SysEncoding)list.get(0);
/* 46:46 */      se.setCodingValue(coding_value);
/* 47:47 */      pmf.update(se);
/* 48:   */    }
/* 49:   */    
/* 50:50 */    return true;
/* 51:   */  }
/* 52:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.EncodingUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */