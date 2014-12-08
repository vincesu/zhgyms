/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Contract;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseContract
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "Contract";
/*  11: 11 */  public static String PROP_DELIVERY_DATE = "DeliveryDate";
/*  12: 12 */  public static String PROP_ORDER_DATE = "OrderDate";
/*  13: 13 */  public static String PROP_NUMBER = "Number";
/*  14: 14 */  public static String PROP_FAX = "Fax";
/*  15: 15 */  public static String PROP_ORDER_ID = "OrderId";
/*  16: 16 */  public static String PROP_SHIPPING_TREM = "ShippingTrem";
/*  17: 17 */  public static String PROP_SALEMAN = "Saleman";
/*  18: 18 */  public static String PROP_RESERVED = "Reserved";
/*  19: 19 */  public static String PROP_LEAD_TIME = "LeadTime";
/*  20: 20 */  public static String PROP_PAYMENT = "Payment";
/*  21: 21 */  public static String PROP_NAME = "Name";
/*  22: 22 */  public static String PROP_AUDIT = "Audit";
/*  23: 23 */  public static String PROP_IS_AUDIT = "IsAudit";
/*  24: 24 */  public static String PROP_EMAIL = "Email";
/*  25: 25 */  public static String PROP_BUYER_ID = "BuyerId";
/*  26: 26 */  public static String PROP_ID = "Id";
/*  27: 27 */  public static String PROP_REMARK = "Remark";
/*  28: 28 */  public static String PROP_TEL = "Tel";
/*  29: 29 */  public static String PROP_CURRENCY_SYMBOL = "CurrencySymbol";
/*  30: 30 */  public static String PROP_CREATE = "Createc";
/*  31: 31 */  public static String PROP_MAKEPOINT = "Makepoint";
/*  32: 32 */  public static String PROP_COMPLETE = "Complete";
/*  33:    */  
/*  34:    */  public BaseContract()
/*  35:    */  {
/*  36: 36 */    initialize();
/*  37:    */  }
/*  38:    */  
/*  39:    */  public BaseContract(Long id)
/*  40:    */  {
/*  41: 41 */    setId(id);
/*  42: 42 */    initialize();
/*  43:    */  }
/*  44:    */  
/*  45: 45 */  private int hashCode = -2147483648;
/*  46:    */  
/*  47:    */  private Long id;
/*  48:    */  
/*  49:    */  private Long buyerId;
/*  50:    */  
/*  51:    */  private Long orderId;
/*  52:    */  
/*  53:    */  private String name;
/*  54:    */  
/*  55:    */  private String number;
/*  56:    */  
/*  57:    */  private Date orderDate;
/*  58:    */  
/*  59:    */  private String deliveryDate;
/*  60:    */  
/*  61:    */  private Long saleman;
/*  62:    */  private String reserved;
/*  63:    */  private String leadTime;
/*  64:    */  private String shippingTrem;
/*  65:    */  private String payment;
/*  66:    */  private String remark;
/*  67:    */  private String currencySymbol;
/*  68:    */  private boolean isAudit;
/*  69:    */  private Long audit;
/*  70:    */  private String tel;
/*  71:    */  private String fax;
/*  72:    */  private String email;
/*  73:    */  private Integer createc;
/*  74:    */  private String makepoint;
/*  75:    */  private String complete;
/*  76:    */  
/*  77:    */  protected void initialize() {}
/*  78:    */  
/*  79:    */  public Long getId()
/*  80:    */  {
/*  81: 81 */    return this.id;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public void setId(Long id)
/*  85:    */  {
/*  86: 86 */    this.id = id;
/*  87: 87 */    this.hashCode = -2147483648;
/*  88:    */  }
/*  89:    */  
/*  90:    */  public Long getBuyerId()
/*  91:    */  {
/*  92: 92 */    return this.buyerId;
/*  93:    */  }
/*  94:    */  
/*  95:    */  public void setBuyerId(Long buyerId)
/*  96:    */  {
/*  97: 97 */    this.buyerId = buyerId;
/*  98:    */  }
/*  99:    */  
/* 100:    */  public Long getOrderId()
/* 101:    */  {
/* 102:102 */    return this.orderId;
/* 103:    */  }
/* 104:    */  
/* 105:    */  public void setOrderId(Long orderId)
/* 106:    */  {
/* 107:107 */    this.orderId = orderId;
/* 108:    */  }
/* 109:    */  
/* 110:    */  public String getName()
/* 111:    */  {
/* 112:112 */    return this.name;
/* 113:    */  }
/* 114:    */  
/* 115:    */  public void setName(String name)
/* 116:    */  {
/* 117:117 */    this.name = name;
/* 118:    */  }
/* 119:    */  
/* 120:    */  public String getNumber()
/* 121:    */  {
/* 122:122 */    return this.number;
/* 123:    */  }
/* 124:    */  
/* 125:    */  public void setNumber(String number)
/* 126:    */  {
/* 127:127 */    this.number = number;
/* 128:    */  }
/* 129:    */  
/* 130:    */  public Date getOrderDate()
/* 131:    */  {
/* 132:132 */    return this.orderDate;
/* 133:    */  }
/* 134:    */  
/* 135:    */  public void setOrderDate(Date orderDate)
/* 136:    */  {
/* 137:137 */    this.orderDate = orderDate;
/* 138:    */  }
/* 139:    */  
/* 140:    */  public String getDeliveryDate()
/* 141:    */  {
/* 142:142 */    return this.deliveryDate;
/* 143:    */  }
/* 144:    */  
/* 145:    */  public void setDeliveryDate(String deliveryDate)
/* 146:    */  {
/* 147:147 */    this.deliveryDate = deliveryDate;
/* 148:    */  }
/* 149:    */  
/* 150:    */  public Long getSaleman()
/* 151:    */  {
/* 152:152 */    return this.saleman;
/* 153:    */  }
/* 154:    */  
/* 155:    */  public void setSaleman(Long saleman)
/* 156:    */  {
/* 157:157 */    this.saleman = saleman;
/* 158:    */  }
/* 159:    */  
/* 160:    */  public String getReserved()
/* 161:    */  {
/* 162:162 */    return this.reserved;
/* 163:    */  }
/* 164:    */  
/* 165:    */  public void setReserved(String reserved)
/* 166:    */  {
/* 167:167 */    this.reserved = reserved;
/* 168:    */  }
/* 169:    */  
/* 170:    */  public String getLeadTime()
/* 171:    */  {
/* 172:172 */    return this.leadTime;
/* 173:    */  }
/* 174:    */  
/* 175:    */  public void setLeadTime(String leadTime)
/* 176:    */  {
/* 177:177 */    this.leadTime = leadTime;
/* 178:    */  }
/* 179:    */  
/* 180:    */  public String getShippingTrem()
/* 181:    */  {
/* 182:182 */    return this.shippingTrem;
/* 183:    */  }
/* 184:    */  
/* 185:    */  public void setShippingTrem(String shippingTrem)
/* 186:    */  {
/* 187:187 */    this.shippingTrem = shippingTrem;
/* 188:    */  }
/* 189:    */  
/* 190:    */  public String getPayment()
/* 191:    */  {
/* 192:192 */    return this.payment;
/* 193:    */  }
/* 194:    */  
/* 195:    */  public void setPayment(String payment)
/* 196:    */  {
/* 197:197 */    this.payment = payment;
/* 198:    */  }
/* 199:    */  
/* 200:    */  public String getRemark()
/* 201:    */  {
/* 202:202 */    return this.remark;
/* 203:    */  }
/* 204:    */  
/* 205:    */  public void setRemark(String remark)
/* 206:    */  {
/* 207:207 */    this.remark = remark;
/* 208:    */  }
/* 209:    */  
/* 210:    */  public String getCurrencySymbol()
/* 211:    */  {
/* 212:212 */    return this.currencySymbol;
/* 213:    */  }
/* 214:    */  
/* 215:    */  public void setCurrencySymbol(String currencySymbol)
/* 216:    */  {
/* 217:217 */    this.currencySymbol = currencySymbol;
/* 218:    */  }
/* 219:    */  
/* 220:    */  public boolean isIsAudit()
/* 221:    */  {
/* 222:222 */    return this.isAudit;
/* 223:    */  }
/* 224:    */  
/* 225:    */  public void setIsAudit(boolean isAudit)
/* 226:    */  {
/* 227:227 */    this.isAudit = isAudit;
/* 228:    */  }
/* 229:    */  
/* 230:    */  public Long getAudit()
/* 231:    */  {
/* 232:232 */    return this.audit;
/* 233:    */  }
/* 234:    */  
/* 235:    */  public void setAudit(Long audit)
/* 236:    */  {
/* 237:237 */    this.audit = audit;
/* 238:    */  }
/* 239:    */  
/* 240:    */  public String getTel()
/* 241:    */  {
/* 242:242 */    return this.tel;
/* 243:    */  }
/* 244:    */  
/* 245:    */  public void setTel(String tel)
/* 246:    */  {
/* 247:247 */    this.tel = tel;
/* 248:    */  }
/* 249:    */  
/* 250:    */  public String getFax()
/* 251:    */  {
/* 252:252 */    return this.fax;
/* 253:    */  }
/* 254:    */  
/* 255:    */  public void setFax(String fax)
/* 256:    */  {
/* 257:257 */    this.fax = fax;
/* 258:    */  }
/* 259:    */  
/* 260:    */  public String getEmail()
/* 261:    */  {
/* 262:262 */    return this.email;
/* 263:    */  }
/* 264:    */  
/* 265:    */  public void setEmail(String email)
/* 266:    */  {
/* 267:267 */    this.email = email;
/* 268:    */  }
/* 269:    */  
/* 270:    */  public boolean equals(Object obj)
/* 271:    */  {
/* 272:272 */    if (obj == null) return false;
/* 273:273 */    if (!(obj instanceof Contract)) { return false;
/* 274:    */    }
/* 275:275 */    Contract contract = (Contract)obj;
/* 276:276 */    if ((getId() == null) || (contract.getId() == null)) return false;
/* 277:277 */    return getId().equals(contract.getId());
/* 278:    */  }
/* 279:    */  
/* 280:    */  public int hashCode()
/* 281:    */  {
/* 282:282 */    if (-2147483648 == this.hashCode) {
/* 283:283 */      if (getId() == null) { return super.hashCode();
/* 284:    */      }
/* 285:285 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 286:286 */      this.hashCode = hashStr.hashCode();
/* 287:    */    }
/* 288:    */    
/* 289:289 */    return this.hashCode;
/* 290:    */  }
/* 291:    */  
/* 292:    */  public String toString()
/* 293:    */  {
/* 294:294 */    return super.toString();
/* 295:    */  }
/* 296:    */  
/* 297:    */  public Integer getCreatec() {
/* 298:298 */    return this.createc;
/* 299:    */  }
/* 300:    */  
/* 301:    */  public void setCreatec(Integer create) {
/* 302:302 */    this.createc = create;
/* 303:    */  }
/* 304:    */  
/* 305:    */  public String getMakepoint()
/* 306:    */  {
/* 307:307 */    return this.makepoint;
/* 308:    */  }
/* 309:    */  
/* 310:    */  public void setMakepoint(String makepoint)
/* 311:    */  {
/* 312:312 */    this.makepoint = makepoint;
/* 313:    */  }
/* 314:    */  
/* 315:    */  public String getComplete()
/* 316:    */  {
/* 317:317 */    return this.complete;
/* 318:    */  }
/* 319:    */  
/* 320:    */  public void setComplete(String complete)
/* 321:    */  {
/* 322:322 */    this.complete = complete;
/* 323:    */  }
/* 324:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseContract
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */