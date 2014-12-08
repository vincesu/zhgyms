/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Trackprogress;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*  14:    */public abstract class BaseTrackprogress
/*  15:    */  implements Serializable
/*  16:    */{
/*  17: 17 */  public static String REF = "Trackprogress";
/*  18: 18 */  public static String PROP_PRODUCT = "Product";
/*  19: 19 */  public static String PROP_CLIENTID = "Clientid";
/*  20: 20 */  public static String PROP_PROGRESS = "Progress";
/*  21: 21 */  public static String PROP_HAVEQUOTE = "Havequote";
/*  22: 22 */  public static String PROP_TRACKTIME = "Tracktime";
/*  23: 23 */  public static String PROP_FILENAME1 = "Filename1";
/*  24: 24 */  public static String PROP_FILEID3 = "Fileid3";
/*  25: 25 */  public static String PROP_FILENAME2 = "Filename2";
/*  26: 26 */  public static String PROP_FILEID4 = "Fileid4";
/*  27: 27 */  public static String PROP_ID = "Id";
/*  28: 28 */  public static String PROP_FILEID1 = "Fileid1";
/*  29: 29 */  public static String PROP_FILENAME3 = "Filename3";
/*  30: 30 */  public static String PROP_FILEID2 = "Fileid2";
/*  31: 31 */  public static String PROP_FILENAME4 = "Filename4";
/*  32:    */  
/*  34:    */  public BaseTrackprogress()
/*  35:    */  {
/*  36: 36 */    initialize();
/*  37:    */  }
/*  38:    */  
/*  41:    */  public BaseTrackprogress(Long id)
/*  42:    */  {
/*  43: 43 */    setId(id);
/*  44: 44 */    initialize();
/*  45:    */  }
/*  46:    */  
/*  51: 51 */  private int hashCode = -2147483648;
/*  52:    */  
/*  53:    */  private Long id;
/*  54:    */  
/*  55:    */  private Long clientid;
/*  56:    */  
/*  57:    */  private Date tracktime;
/*  58:    */  
/*  59:    */  private String product;
/*  60:    */  
/*  61:    */  private String progress;
/*  62:    */  
/*  63:    */  private String havequote;
/*  64:    */  
/*  65:    */  private Long fileid1;
/*  66:    */  
/*  67:    */  private String filename1;
/*  68:    */  
/*  69:    */  private Long fileid2;
/*  70:    */  private String filename2;
/*  71:    */  private Long fileid3;
/*  72:    */  private String filename3;
/*  73:    */  private Long fileid4;
/*  74:    */  private String filename4;
/*  75:    */  
/*  76:    */  protected void initialize() {}
/*  77:    */  
/*  78:    */  public Long getId()
/*  79:    */  {
/*  80: 80 */    return this.id;
/*  81:    */  }
/*  82:    */  
/*  86:    */  public void setId(Long id)
/*  87:    */  {
/*  88: 88 */    this.id = id;
/*  89: 89 */    this.hashCode = -2147483648;
/*  90:    */  }
/*  91:    */  
/*  97:    */  public Long getClientid()
/*  98:    */  {
/*  99: 99 */    return this.clientid;
/* 100:    */  }
/* 101:    */  
/* 105:    */  public void setClientid(Long clientid)
/* 106:    */  {
/* 107:107 */    this.clientid = clientid;
/* 108:    */  }
/* 109:    */  
/* 114:    */  public Date getTracktime()
/* 115:    */  {
/* 116:116 */    return this.tracktime;
/* 117:    */  }
/* 118:    */  
/* 122:    */  public void setTracktime(Date tracktime)
/* 123:    */  {
/* 124:124 */    this.tracktime = tracktime;
/* 125:    */  }
/* 126:    */  
/* 131:    */  public String getProduct()
/* 132:    */  {
/* 133:133 */    return this.product;
/* 134:    */  }
/* 135:    */  
/* 139:    */  public void setProduct(String product)
/* 140:    */  {
/* 141:141 */    this.product = product;
/* 142:    */  }
/* 143:    */  
/* 148:    */  public String getProgress()
/* 149:    */  {
/* 150:150 */    return this.progress;
/* 151:    */  }
/* 152:    */  
/* 156:    */  public void setProgress(String progress)
/* 157:    */  {
/* 158:158 */    this.progress = progress;
/* 159:    */  }
/* 160:    */  
/* 165:    */  public String getHavequote()
/* 166:    */  {
/* 167:167 */    return this.havequote;
/* 168:    */  }
/* 169:    */  
/* 173:    */  public void setHavequote(String havequote)
/* 174:    */  {
/* 175:175 */    this.havequote = havequote;
/* 176:    */  }
/* 177:    */  
/* 182:    */  public Long getFileid1()
/* 183:    */  {
/* 184:184 */    return this.fileid1;
/* 185:    */  }
/* 186:    */  
/* 190:    */  public void setFileid1(Long fileid1)
/* 191:    */  {
/* 192:192 */    this.fileid1 = fileid1;
/* 193:    */  }
/* 194:    */  
/* 199:    */  public String getFilename1()
/* 200:    */  {
/* 201:201 */    return this.filename1;
/* 202:    */  }
/* 203:    */  
/* 207:    */  public void setFilename1(String filename1)
/* 208:    */  {
/* 209:209 */    this.filename1 = filename1;
/* 210:    */  }
/* 211:    */  
/* 216:    */  public Long getFileid2()
/* 217:    */  {
/* 218:218 */    return this.fileid2;
/* 219:    */  }
/* 220:    */  
/* 224:    */  public void setFileid2(Long fileid2)
/* 225:    */  {
/* 226:226 */    this.fileid2 = fileid2;
/* 227:    */  }
/* 228:    */  
/* 233:    */  public String getFilename2()
/* 234:    */  {
/* 235:235 */    return this.filename2;
/* 236:    */  }
/* 237:    */  
/* 241:    */  public void setFilename2(String filename2)
/* 242:    */  {
/* 243:243 */    this.filename2 = filename2;
/* 244:    */  }
/* 245:    */  
/* 250:    */  public Long getFileid3()
/* 251:    */  {
/* 252:252 */    return this.fileid3;
/* 253:    */  }
/* 254:    */  
/* 258:    */  public void setFileid3(Long fileid3)
/* 259:    */  {
/* 260:260 */    this.fileid3 = fileid3;
/* 261:    */  }
/* 262:    */  
/* 267:    */  public String getFilename3()
/* 268:    */  {
/* 269:269 */    return this.filename3;
/* 270:    */  }
/* 271:    */  
/* 275:    */  public void setFilename3(String filename3)
/* 276:    */  {
/* 277:277 */    this.filename3 = filename3;
/* 278:    */  }
/* 279:    */  
/* 284:    */  public Long getFileid4()
/* 285:    */  {
/* 286:286 */    return this.fileid4;
/* 287:    */  }
/* 288:    */  
/* 292:    */  public void setFileid4(Long fileid4)
/* 293:    */  {
/* 294:294 */    this.fileid4 = fileid4;
/* 295:    */  }
/* 296:    */  
/* 301:    */  public String getFilename4()
/* 302:    */  {
/* 303:303 */    return this.filename4;
/* 304:    */  }
/* 305:    */  
/* 309:    */  public void setFilename4(String filename4)
/* 310:    */  {
/* 311:311 */    this.filename4 = filename4;
/* 312:    */  }
/* 313:    */  
/* 316:    */  public boolean equals(Object obj)
/* 317:    */  {
/* 318:318 */    if (obj == null) return false;
/* 319:319 */    if (!(obj instanceof Trackprogress)) { return false;
/* 320:    */    }
/* 321:321 */    Trackprogress trackprogress = (Trackprogress)obj;
/* 322:322 */    if ((getId() == null) || (trackprogress.getId() == null)) return false;
/* 323:323 */    return getId().equals(trackprogress.getId());
/* 324:    */  }
/* 325:    */  
/* 326:    */  public int hashCode()
/* 327:    */  {
/* 328:328 */    if (-2147483648 == this.hashCode) {
/* 329:329 */      if (getId() == null) { return super.hashCode();
/* 330:    */      }
/* 331:331 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 332:332 */      this.hashCode = hashStr.hashCode();
/* 333:    */    }
/* 334:    */    
/* 335:335 */    return this.hashCode;
/* 336:    */  }
/* 337:    */  
/* 338:    */  public String toString()
/* 339:    */  {
/* 340:340 */    return super.toString();
/* 341:    */  }
/* 342:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseTrackprogress
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */