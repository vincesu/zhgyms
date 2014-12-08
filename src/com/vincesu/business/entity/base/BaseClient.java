/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Client;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*  14:    */public abstract class BaseClient
/*  15:    */  implements Serializable
/*  16:    */{
/*  17: 17 */  public static String REF = "Client";
/*  18: 18 */  public static String PROP_POTENTIALRESERVED1 = "Potentialreserved1";
/*  19: 19 */  public static String PROP_PHONE = "Phone";
/*  20: 20 */  public static String PROP_POTENTIALRESERVED3 = "Potentialreserved3";
/*  21: 21 */  public static String PROP_CLIENTFROM = "Clientfrom";
/*  22: 22 */  public static String PROP_POTENTIALRESERVED2 = "Potentialreserved2";
/*  23: 23 */  public static String PROP_NUMBER = "Number";
/*  24: 24 */  public static String PROP_CONTACT = "Contact";
/*  25: 25 */  public static String PROP_SALEMAN = "Saleman";
/*  26: 26 */  public static String PROP_RESERVED = "Reserved";
/*  27: 27 */  public static String PROP_POTENTIAL = "Potential";
/*  28: 28 */  public static String PROP_UNIT = "Unit";
/*  29: 29 */  public static String PROP_NATIONALITY = "Nationality";
/*  30: 30 */  public static String PROP_HAVEQUOTE = "Havequote";
/*  31: 31 */  public static String PROP_EMAIL = "Email";
/*  32: 32 */  public static String PROP_MAILINGSAMPLE = "Mailingsample";
/*  33: 33 */  public static String PROP_WEB = "Web";
/*  34: 34 */  public static String PROP_JOINTIME = "Jointime";
/*  35: 35 */  public static String PROP_ADDRESS = "Address";
/*  36: 36 */  public static String PROP_ID = "Id";
/*  37: 37 */  public static String PROP_REMARK = "Remark";
/*  38:    */  
/*  40:    */  public BaseClient()
/*  41:    */  {
/*  42: 42 */    initialize();
/*  43:    */  }
/*  44:    */  
/*  47:    */  public BaseClient(Long id)
/*  48:    */  {
/*  49: 49 */    setId(id);
/*  50: 50 */    initialize();
/*  51:    */  }
/*  52:    */  
/*  57: 57 */  private int hashCode = -2147483648;
/*  58:    */  
/*  59:    */  private Long id;
/*  60:    */  
/*  61:    */  private String number;
/*  62:    */  
/*  63:    */  private String contact;
/*  64:    */  
/*  65:    */  private String unit;
/*  66:    */  
/*  67:    */  private String email;
/*  68:    */  
/*  69:    */  private String phone;
/*  70:    */  
/*  71:    */  private String web;
/*  72:    */  
/*  73:    */  private String address;
/*  74:    */  
/*  75:    */  private String nationality;
/*  76:    */  private String remark;
/*  77:    */  private String reserved;
/*  78:    */  private Long saleman;
/*  79:    */  private String clientfrom;
/*  80:    */  private String jointime;
/*  81:    */  private String potential;
/*  82:    */  private String havequote;
/*  83:    */  private String mailingsample;
/*  84:    */  private String potentialreserved1;
/*  85:    */  private Integer potentialreserved2;
/*  86:    */  private Double potentialreserved3;
/*  87:    */  
/*  88:    */  protected void initialize() {}
/*  89:    */  
/*  90:    */  public Long getId()
/*  91:    */  {
/*  92: 92 */    return this.id;
/*  93:    */  }
/*  94:    */  
/*  98:    */  public void setId(Long id)
/*  99:    */  {
/* 100:100 */    this.id = id;
/* 101:101 */    this.hashCode = -2147483648;
/* 102:    */  }
/* 103:    */  
/* 109:    */  public String getNumber()
/* 110:    */  {
/* 111:111 */    return this.number;
/* 112:    */  }
/* 113:    */  
/* 117:    */  public void setNumber(String number)
/* 118:    */  {
/* 119:119 */    this.number = number;
/* 120:    */  }
/* 121:    */  
/* 126:    */  public String getContact()
/* 127:    */  {
/* 128:128 */    return this.contact;
/* 129:    */  }
/* 130:    */  
/* 134:    */  public void setContact(String contact)
/* 135:    */  {
/* 136:136 */    this.contact = contact;
/* 137:    */  }
/* 138:    */  
/* 143:    */  public String getUnit()
/* 144:    */  {
/* 145:145 */    return this.unit;
/* 146:    */  }
/* 147:    */  
/* 151:    */  public void setUnit(String unit)
/* 152:    */  {
/* 153:153 */    this.unit = unit;
/* 154:    */  }
/* 155:    */  
/* 160:    */  public String getEmail()
/* 161:    */  {
/* 162:162 */    return this.email;
/* 163:    */  }
/* 164:    */  
/* 168:    */  public void setEmail(String email)
/* 169:    */  {
/* 170:170 */    this.email = email;
/* 171:    */  }
/* 172:    */  
/* 177:    */  public String getPhone()
/* 178:    */  {
/* 179:179 */    return this.phone;
/* 180:    */  }
/* 181:    */  
/* 185:    */  public void setPhone(String phone)
/* 186:    */  {
/* 187:187 */    this.phone = phone;
/* 188:    */  }
/* 189:    */  
/* 194:    */  public String getWeb()
/* 195:    */  {
/* 196:196 */    return this.web;
/* 197:    */  }
/* 198:    */  
/* 202:    */  public void setWeb(String web)
/* 203:    */  {
/* 204:204 */    this.web = web;
/* 205:    */  }
/* 206:    */  
/* 211:    */  public String getAddress()
/* 212:    */  {
/* 213:213 */    return this.address;
/* 214:    */  }
/* 215:    */  
/* 219:    */  public void setAddress(String address)
/* 220:    */  {
/* 221:221 */    this.address = address;
/* 222:    */  }
/* 223:    */  
/* 228:    */  public String getNationality()
/* 229:    */  {
/* 230:230 */    return this.nationality;
/* 231:    */  }
/* 232:    */  
/* 236:    */  public void setNationality(String nationality)
/* 237:    */  {
/* 238:238 */    this.nationality = nationality;
/* 239:    */  }
/* 240:    */  
/* 245:    */  public String getRemark()
/* 246:    */  {
/* 247:247 */    return this.remark;
/* 248:    */  }
/* 249:    */  
/* 253:    */  public void setRemark(String remark)
/* 254:    */  {
/* 255:255 */    this.remark = remark;
/* 256:    */  }
/* 257:    */  
/* 262:    */  public String getReserved()
/* 263:    */  {
/* 264:264 */    return this.reserved;
/* 265:    */  }
/* 266:    */  
/* 270:    */  public void setReserved(String reserved)
/* 271:    */  {
/* 272:272 */    this.reserved = reserved;
/* 273:    */  }
/* 274:    */  
/* 279:    */  public Long getSaleman()
/* 280:    */  {
/* 281:281 */    return this.saleman;
/* 282:    */  }
/* 283:    */  
/* 287:    */  public void setSaleman(Long saleman)
/* 288:    */  {
/* 289:289 */    this.saleman = saleman;
/* 290:    */  }
/* 291:    */  
/* 296:    */  public String getClientfrom()
/* 297:    */  {
/* 298:298 */    return this.clientfrom;
/* 299:    */  }
/* 300:    */  
/* 304:    */  public void setClientfrom(String clientfrom)
/* 305:    */  {
/* 306:306 */    this.clientfrom = clientfrom;
/* 307:    */  }
/* 308:    */  
/* 313:    */  public String getJointime()
/* 314:    */  {
/* 315:315 */    return this.jointime;
/* 316:    */  }
/* 317:    */  
/* 321:    */  public void setJointime(String jointime)
/* 322:    */  {
/* 323:323 */    this.jointime = jointime;
/* 324:    */  }
/* 325:    */  
/* 330:    */  public String getPotential()
/* 331:    */  {
/* 332:332 */    return this.potential;
/* 333:    */  }
/* 334:    */  
/* 338:    */  public void setPotential(String potential)
/* 339:    */  {
/* 340:340 */    this.potential = potential;
/* 341:    */  }
/* 342:    */  
/* 347:    */  public String getHavequote()
/* 348:    */  {
/* 349:349 */    return this.havequote;
/* 350:    */  }
/* 351:    */  
/* 355:    */  public void setHavequote(String havequote)
/* 356:    */  {
/* 357:357 */    this.havequote = havequote;
/* 358:    */  }
/* 359:    */  
/* 364:    */  public String getMailingsample()
/* 365:    */  {
/* 366:366 */    return this.mailingsample;
/* 367:    */  }
/* 368:    */  
/* 372:    */  public void setMailingsample(String mailingsample)
/* 373:    */  {
/* 374:374 */    this.mailingsample = mailingsample;
/* 375:    */  }
/* 376:    */  
/* 381:    */  public String getPotentialreserved1()
/* 382:    */  {
/* 383:383 */    return this.potentialreserved1;
/* 384:    */  }
/* 385:    */  
/* 389:    */  public void setPotentialreserved1(String potentialreserved1)
/* 390:    */  {
/* 391:391 */    this.potentialreserved1 = potentialreserved1;
/* 392:    */  }
/* 393:    */  
/* 398:    */  public Integer getPotentialreserved2()
/* 399:    */  {
/* 400:400 */    return this.potentialreserved2;
/* 401:    */  }
/* 402:    */  
/* 406:    */  public void setPotentialreserved2(Integer potentialreserved2)
/* 407:    */  {
/* 408:408 */    this.potentialreserved2 = potentialreserved2;
/* 409:    */  }
/* 410:    */  
/* 415:    */  public Double getPotentialreserved3()
/* 416:    */  {
/* 417:417 */    return this.potentialreserved3;
/* 418:    */  }
/* 419:    */  
/* 423:    */  public void setPotentialreserved3(Double potentialreserved3)
/* 424:    */  {
/* 425:425 */    this.potentialreserved3 = potentialreserved3;
/* 426:    */  }
/* 427:    */  
/* 430:    */  public boolean equals(Object obj)
/* 431:    */  {
/* 432:432 */    if (obj == null) return false;
/* 433:433 */    if (!(obj instanceof Client)) { return false;
/* 434:    */    }
/* 435:435 */    Client client = (Client)obj;
/* 436:436 */    if ((getId() == null) || (client.getId() == null)) return false;
/* 437:437 */    return getId().equals(client.getId());
/* 438:    */  }
/* 439:    */  
/* 440:    */  public int hashCode()
/* 441:    */  {
/* 442:442 */    if (-2147483648 == this.hashCode) {
/* 443:443 */      if (getId() == null) { return super.hashCode();
/* 444:    */      }
/* 445:445 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 446:446 */      this.hashCode = hashStr.hashCode();
/* 447:    */    }
/* 448:    */    
/* 449:449 */    return this.hashCode;
/* 450:    */  }
/* 451:    */  
/* 452:    */  public String toString()
/* 453:    */  {
/* 454:454 */    return super.toString();
/* 455:    */  }
/* 456:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseClient
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */