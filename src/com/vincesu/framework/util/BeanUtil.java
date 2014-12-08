/*   1:    */package com.vincesu.framework.util;
/*   2:    */
/*   3:    */import java.lang.reflect.Method;
/*   4:    */import java.util.Date;
/*   5:    */import java.util.HashMap;
/*   6:    */import java.util.Map;
/*   7:    */import org.json.JSONArray;
/*   8:    */import org.json.JSONException;
/*   9:    */import org.json.JSONObject;
/*  10:    */
/*  13:    */public class BeanUtil
/*  14:    */{
/*  15:    */  public static JSONObject Object2JSON(Object obj)
/*  16:    */    throws JSONException, IllegalArgumentException, IllegalAccessException
/*  17:    */  {
/*  18: 18 */    JSONObject result = new JSONObject(obj);
/*  19: 19 */    return result;
/*  20:    */  }
/*  21:    */  
/*  22:    */  public static JSONObject Map2JSON(Map<Object, Object> map) throws JSONException, IllegalArgumentException, IllegalAccessException
/*  23:    */  {
/*  24: 24 */    JSONObject result = new JSONObject(map);
/*  25: 25 */    return result;
/*  26:    */  }
/*  27:    */  
/*  28:    */  public static Map Object2Map(Object obj)
/*  29:    */  {
/*  30: 30 */    Class localClass = obj.getClass();
/*  31: 31 */    Method[] arrayOfMethod = localClass.getMethods();
/*  32: 32 */    Map result = new HashMap();
/*  33:    */    
/*  34: 34 */    for (int i = 0; i < arrayOfMethod.length; i++) {
/*  35:    */      try
/*  36:    */      {
/*  37: 37 */        Method localMethod = arrayOfMethod[i];
/*  38: 38 */        String str1 = localMethod.getName();
/*  39: 39 */        String str2 = "";
/*  40: 40 */        if (str1.startsWith("get")) {
/*  41: 41 */          str2 = str1.substring(3);
/*  42: 42 */        } else if (str1.startsWith("is"))
/*  43: 43 */          str2 = str1.substring(2);
/*  44: 44 */        if ((str2.length() > 0) && 
/*  45: 45 */          (Character.isUpperCase(str2.charAt(0))) && 
/*  46: 46 */          (localMethod.getParameterTypes().length == 0)) {
/*  47: 47 */          if (str2.length() == 1) {
/*  48: 48 */            str2 = str2.toLowerCase();
/*  49: 49 */          } else if (!Character.isUpperCase(str2.charAt(1)))
/*  50: 50 */            str2 = 
/*  51: 51 */              str2.substring(0, 1).toLowerCase() + str2.substring(1);
/*  52: 52 */          result.put(str2, localMethod.invoke(obj, null));
/*  53:    */        }
/*  54:    */      }
/*  55:    */      catch (Exception localException) {}
/*  56:    */    }
/*  57:    */    
/*  58: 58 */    return result;
/*  59:    */  }
/*  60:    */  
/*  61:    */  public static JSONArray Object2JSONArray(Object obj) throws JSONException
/*  62:    */  {
/*  63: 63 */    Class localClass = obj.getClass();
/*  64: 64 */    Method[] arrayOfMethod = localClass.getMethods();
/*  65: 65 */    JSONArray result = new JSONArray();
/*  66:    */    
/*  67: 67 */    for (int i = 0; i < arrayOfMethod.length; i++) {
/*  68:    */      try
/*  69:    */      {
/*  70: 70 */        Method localMethod = arrayOfMethod[i];
/*  71: 71 */        String str1 = localMethod.getName();
/*  72: 72 */        String str2 = "";
/*  73: 73 */        if (str1.startsWith("get")) {
/*  74: 74 */          str2 = str1.substring(3);
/*  75: 75 */        } else if (str1.startsWith("is"))
/*  76: 76 */          str2 = str1.substring(2);
/*  77: 77 */        if ((str2.length() > 0) && 
/*  78: 78 */          (Character.isUpperCase(str2.charAt(0))) && 
/*  79: 79 */          (localMethod.getParameterTypes().length == 0)) {
/*  80: 80 */          if (str2.length() == 1) {
/*  81: 81 */            str2 = str2.toLowerCase();
/*  82: 82 */          } else if (!Character.isUpperCase(str2.charAt(1))) {
/*  83: 83 */            str2 = 
/*  84: 84 */              str2.substring(0, 1).toLowerCase() + str2.substring(1);
/*  85:    */          }
/*  86: 86 */          result.put(i, localMethod.invoke(obj, null));
/*  87:    */        }
/*  88:    */      }
/*  89:    */      catch (Exception localException) {}
/*  90:    */    }
/*  91:    */    
/*  92: 92 */    return result;
/*  93:    */  }
/*  94:    */  
/*  95:    */  public static Map Array2Map(String[] fields, Object[] array) throws Exception
/*  96:    */  {
/*  97: 97 */    Map<Object, Object> result = new HashMap();
/*  98:    */    
/*  99: 99 */    if (fields.length != array.length) {
/* 100:100 */      throw new Exception("数组参数长度有误");
/* 101:    */    }
/* 102:102 */    for (int i = 0; i < fields.length; i++)
/* 103:    */    {
/* 104:104 */      result.put(fields[i], array[i]);
/* 105:    */    }
/* 106:    */    
/* 107:107 */    return result;
/* 108:    */  }
/* 109:    */  
/* 150:    */  public static void copyProperty(Map source, Object target, String[] formatStr, boolean passNull)
/* 151:    */  {
/* 152:152 */    Class localClass = target.getClass();
/* 153:153 */    Method[] arrayOfMethod = localClass.getMethods();
/* 154:154 */    int j = 0;
/* 155:155 */    for (int i = 0; i < arrayOfMethod.length; i++) {
/* 156:    */      try {
/* 157:157 */        Method localMethod = arrayOfMethod[i];
/* 158:158 */        String str1 = localMethod.getName();
/* 159:159 */        String str2 = "";
/* 160:160 */        if (str1.startsWith("set"))
/* 161:161 */          str2 = str1.substring(3);
/* 162:162 */        if ((str2.length() > 0) && 
/* 163:163 */          (Character.isUpperCase(str2.charAt(0))) && 
/* 164:164 */          (localMethod.getParameterTypes().length == 1))
/* 165:    */        {
/* 166:166 */          if (str2.length() == 1) {
/* 167:167 */            str2 = str2.toLowerCase();
/* 168:168 */          } else if (!Character.isUpperCase(str2.charAt(1))) {
/* 169:169 */            str2 = 
/* 170:170 */              str2.substring(0, 1).toLowerCase() + str2.substring(1);
/* 171:    */          }
/* 172:172 */          Object value = null;
/* 173:    */          
/* 174:174 */          if ((localMethod.getParameterTypes()[0].equals(Long.class)) && 
/* 175:175 */            (source.get(str2) != null) && (!source.get(str2).equals(""))) {
/* 176:176 */            value = Long.valueOf(Long.parseLong(source.get(str2).toString()));
/* 177:177 */          } else if ((localMethod.getParameterTypes()[0].equals(Integer.class)) && 
/* 178:178 */            (source.get(str2) != null) && (!source.get(str2).equals(""))) {
/* 179:179 */            value = Integer.valueOf(Integer.parseInt(source.get(str2).toString()));
/* 180:180 */          } else if ((localMethod.getParameterTypes()[0].equals(Double.class)) && 
/* 181:181 */            (source.get(str2) != null) && (!source.get(str2).equals(""))) {
/* 182:182 */            value = Double.valueOf(Double.parseDouble(source.get(str2).toString()));
/* 183:183 */          } else if ((localMethod.getParameterTypes()[0].equals(Float.class)) && 
/* 184:184 */            (source.get(str2) != null) && (!source.get(str2).equals(""))) {
/* 185:185 */            value = Float.valueOf(Float.parseFloat(source.get(str2).toString()));
/* 186:186 */          } else if (localMethod.getParameterTypes()[0].equals(Date.class)) {
/* 187:187 */            if ((formatStr != null) && (formatStr.length > j))
/* 188:    */            {
/* 189:189 */              if ((source.get(str2) != null) && (!source.get(str2).equals("")))
/* 190:    */              {
/* 191:191 */                value = TimeUtil.toDate(source.get(str2).toString(), formatStr[j]);
/* 192:    */              }
/* 193:193 */              j++;
/* 194:    */            }
/* 195:    */            else
/* 196:    */            {
/* 197:197 */              throw new Exception("输入格式化参数有误");
/* 198:    */            }
/* 199:199 */          } else if (((localMethod.getParameterTypes()[0].equals(Boolean.class)) || 
/* 200:200 */            (localMethod.getParameterTypes()[0].equals(Boolean.TYPE))) && 
/* 201:201 */            (source.get(str2) != null) && (!source.get(str2).equals("")))
/* 202:    */          {
/* 203:203 */            if ((source.get(str2).toString().equals("1")) || (source.get(str2).toString().equals("true"))) {
/* 204:204 */              value = Boolean.valueOf(true);
/* 205:    */            } else
/* 206:206 */              value = Boolean.valueOf(false);
/* 207:    */          } else {
/* 208:208 */            value = source.get(str2);
/* 209:    */          }
/* 210:    */          
/* 211:211 */          if (passNull) {
/* 212:212 */            localMethod.invoke(target, new Object[] { value });
/* 213:    */          }
/* 214:214 */          else if ((value != null) && (!value.toString().equals("")))
/* 215:    */          {
/* 217:217 */            localMethod.invoke(target, new Object[] { value });
/* 218:    */          }
/* 219:    */        }
/* 220:    */      }
/* 221:    */      catch (Exception localException) {}
/* 222:    */    }
/* 223:    */  }
/* 224:    */  
/* 229:    */  public static void copyProperty(Map source, Object target, boolean passNull)
/* 230:    */  {
/* 231:231 */    copyProperty(source, target, null, passNull);
/* 232:    */  }
/* 233:    */  
/* 234:    */  public static void copyProperty(Map source, Object target)
/* 235:    */  {
/* 236:236 */    copyProperty(source, target, null, false);
/* 237:    */  }
/* 238:    */  
/* 239:    */  public static void copyProperty(Map source, Object target, String[] formatStr)
/* 240:    */  {
/* 241:241 */    copyProperty(source, target, formatStr, false);
/* 242:    */  }
/* 243:    */  
/* 244:    */  public static void copyProperty(Object source, Object target)
/* 245:    */  {
/* 246:246 */    if (source.getClass().equals(Map.class))
/* 247:    */    {
/* 248:248 */      copyProperty((Map)source, target, null, false);
/* 249:249 */      return;
/* 250:    */    }
/* 251:    */    
/* 252:252 */    Class localClass = target.getClass();
/* 253:253 */    Class sourceClass = source.getClass();
/* 254:254 */    Method[] arrayOfMethod = localClass.getMethods();
/* 255:255 */    Method tempMethod = null;
/* 256:256 */    Object value = null;
/* 257:257 */    for (int i = 0; i < arrayOfMethod.length; i++) {
/* 258:    */      try {
/* 259:259 */        Method localMethod = arrayOfMethod[i];
/* 260:260 */        String str1 = localMethod.getName();
/* 261:261 */        String str2 = "";
/* 262:262 */        if (str1.startsWith("set"))
/* 263:263 */          str2 = str1.substring(3);
/* 264:264 */        if ((str2.length() > 0) && 
/* 265:265 */          (Character.isUpperCase(str2.charAt(0))) && 
/* 266:266 */          (localMethod.getParameterTypes().length == 1))
/* 267:    */        {
/* 268:268 */          str2 = "get" + str2;
/* 269:269 */          tempMethod = null;
/* 270:    */          try
/* 271:    */          {
/* 272:272 */            tempMethod = sourceClass.getMethod(str2, new Class[0]);
/* 273:    */          }
/* 274:    */          catch (Exception e)
/* 275:    */          {
/* 276:276 */            continue;
/* 277:    */          }
/* 278:278 */          if ((tempMethod != null) && (tempMethod.getReturnType().equals(localMethod.getParameterTypes()[0])))
/* 279:    */          {
/* 280:280 */            value = tempMethod.invoke(source, new Object[0]);
/* 281:281 */            localMethod.invoke(target, new Object[] { value });
/* 282:    */          }
/* 283:    */        }
/* 284:    */      }
/* 285:    */      catch (Exception localException1) {}
/* 286:    */    }
/* 287:    */  }
/* 288:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.BeanUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */