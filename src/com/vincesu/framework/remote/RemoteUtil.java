/*   1:    */package com.vincesu.framework.remote;
/*   2:    */
/*   3:    */import com.vincesu.persistence.PMF;
/*   4:    */import com.vincesu.persistence.SessionFactoryHelper;
/*   5:    */import java.util.ArrayList;
/*   6:    */import java.util.HashMap;
/*   7:    */import java.util.Iterator;
/*   8:    */import java.util.List;
/*   9:    */import java.util.Map;
/*  10:    */import java.util.Map.Entry;
/*  11:    */import java.util.Set;
/*  12:    */import org.hibernate.Session;
/*  13:    */
/*  15:    */public class RemoteUtil
/*  16:    */{
/*  17:    */  public static int getIndex(Map.Entry entry)
/*  18:    */  {
/*  19: 19 */    String key = entry.getKey().toString().trim();
/*  20: 20 */    return getIndex(key);
/*  21:    */  }
/*  22:    */  
/*  23:    */  public static int getIndex(String key)
/*  24:    */  {
/*  25: 25 */    int result = -1;
/*  26:    */    try
/*  27:    */    {
/*  28: 28 */      result = Integer.parseInt(
/*  29: 29 */        key.substring(key.indexOf("[") + 1, key.indexOf("]")));
/*  30:    */    }
/*  31:    */    catch (Exception e) {
/*  32: 32 */      result = -1;
/*  33:    */    }
/*  34: 34 */    return result;
/*  35:    */  }
/*  36:    */  
/*  37:    */  public static String getProperty(String key)
/*  38:    */  {
/*  39: 39 */    String result = null;
/*  40:    */    try
/*  41:    */    {
/*  42: 42 */      result = key.substring(key.lastIndexOf("[") + 1, key.lastIndexOf("]"));
/*  43:    */    }
/*  44:    */    catch (Exception e) {
/*  45: 45 */      result = "";
/*  46:    */    }
/*  47: 47 */    return result;
/*  48:    */  }
/*  49:    */  
/*  50:    */  public static String getProperty(Map.Entry entry)
/*  51:    */  {
/*  52: 52 */    String key = entry.getKey().toString().trim();
/*  53: 53 */    return getProperty(key);
/*  54:    */  }
/*  55:    */  
/*  59:    */  public static List setData(Map map)
/*  60:    */  {
/*  61: 61 */    List<Map<Object, Object>> result = new ArrayList();
/*  62: 62 */    int index = -1;
/*  63: 63 */    String propertyName = null;
/*  64: 64 */    Map.Entry entry = null;
/*  65:    */    
/*  66: 66 */    Iterator iter = map.entrySet().iterator();
/*  67: 67 */    while (iter.hasNext())
/*  68:    */    {
/*  69: 69 */      entry = (Map.Entry)iter.next();
/*  70:    */      
/*  72: 72 */      if (entry.getKey().toString().trim().startsWith("data"))
/*  73:    */      {
/*  74: 74 */        index = getIndex(entry);
/*  75: 75 */        if (index >= 0)
/*  76:    */        {
/*  77: 77 */          propertyName = getProperty(entry);
/*  78: 78 */          while (result.size() <= index)
/*  79:    */          {
/*  80: 80 */            result.add(new HashMap());
/*  81:    */          }
/*  82: 82 */          ((Map)result.get(index)).put(propertyName, ((Object[])entry.getValue())[0]);
/*  83:    */        }
/*  84:    */      }
/*  85:    */      else {
/*  86: 86 */        propertyName = getProperty(entry);
/*  87: 87 */        if (result.size() == 0)
/*  88:    */        {
/*  89: 89 */          result.add(new HashMap());
/*  90:    */        }
/*  91: 91 */        ((Map)result.get(0)).put(entry.getKey().toString().trim(), ((Object[])entry.getValue())[0]);
/*  92:    */      }
/*  93:    */    }
/*  94:    */    
/*  95: 95 */    return result;
/*  96:    */  }
/*  97:    */  
/*  98:    */  public static PMF getPMF(RequestEntity req) {
/*  99: 99 */    PMF pmf = null;
/* 100:100 */    if ((req.getData() != null) && (req.getData().get(0) != null) && 
/* 101:101 */      (((Map)req.getData().get(0)).get("syssession") != null)) {
/* 102:102 */      pmf = new PMF((Session)((Map)req.getData().get(0)).get("syssession"));
/* 103:    */    } else {
/* 104:104 */      pmf = new PMF(SessionFactoryHelper.getSession());
/* 105:    */    }
/* 106:106 */    return pmf;
/* 107:    */  }
/* 108:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.remote.RemoteUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */