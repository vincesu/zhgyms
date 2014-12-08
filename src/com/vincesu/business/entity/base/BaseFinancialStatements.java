/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.FinancialStatements;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseFinancialStatements
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "FinancialStatements";
/*  10: 10 */  public static String PROP_PAY_FEES = "PayFees";
/*  11: 11 */  public static String PROP_USERID = "Userid";
/*  12: 12 */  public static String PROP_AGENCY_FEES = "AgencyFees";
/*  13: 13 */  public static String PROP_CONTRACT_ID = "ContractId";
/*  14: 14 */  public static String PROP_FEE = "Fee";
/*  15: 15 */  public static String PROP_ACTUAL_RECEIPTS = "ActualReceipts";
/*  16: 16 */  public static String PROP_EARNEST = "Earnest";
/*  17: 17 */  public static String PROP_RESERVED = "Reserved";
/*  18: 18 */  public static String PROP_BILLING_FEE = "BillingFee";
/*  19: 19 */  public static String PROP_STATUS = "Status";
/*  20: 20 */  public static String PROP_DRAWBACK = "Drawback";
/*  21: 21 */  public static String PROP_ID = "Id";
/*  22: 22 */  public static String PROP_SHIPPING = "Shipping";
/*  23: 23 */  public static String PROP_BALANCE = "Balance";
/*  24:    */  
/*  25:    */  public BaseFinancialStatements()
/*  26:    */  {
/*  27: 27 */    initialize();
/*  28:    */  }
/*  29:    */  
/*  30:    */  public BaseFinancialStatements(Long id)
/*  31:    */  {
/*  32: 32 */    setId(id);
/*  33: 33 */    initialize();
/*  34:    */  }
/*  35:    */  
/*  36: 36 */  private int hashCode = -2147483648;
/*  37:    */  
/*  38:    */  private Long id;
/*  39:    */  
/*  40:    */  private Long contractId;
/*  41:    */  
/*  42:    */  private Double earnest;
/*  43:    */  
/*  44:    */  private Double balance;
/*  45:    */  
/*  46:    */  private Double fee;
/*  47:    */  
/*  48:    */  private Double shipping;
/*  49:    */  
/*  50:    */  private Double actualReceipts;
/*  51:    */  
/*  52:    */  private Double payFees;
/*  53:    */  
/*  54:    */  private Double agencyFees;
/*  55:    */  private Double billingFee;
/*  56:    */  private Double drawback;
/*  57:    */  private Integer status;
/*  58:    */  private Long userid;
/*  59:    */  private String reserved;
/*  60:    */  
/*  61:    */  protected void initialize() {}
/*  62:    */  
/*  63:    */  public Long getId()
/*  64:    */  {
/*  65: 65 */    return this.id;
/*  66:    */  }
/*  67:    */  
/*  68:    */  public void setId(Long id)
/*  69:    */  {
/*  70: 70 */    this.id = id;
/*  71: 71 */    this.hashCode = -2147483648;
/*  72:    */  }
/*  73:    */  
/*  74:    */  public Long getContractId()
/*  75:    */  {
/*  76: 76 */    return this.contractId;
/*  77:    */  }
/*  78:    */  
/*  79:    */  public void setContractId(Long contractId)
/*  80:    */  {
/*  81: 81 */    this.contractId = contractId;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public Double getEarnest()
/*  85:    */  {
/*  86: 86 */    return this.earnest;
/*  87:    */  }
/*  88:    */  
/*  89:    */  public void setEarnest(Double earnest)
/*  90:    */  {
/*  91: 91 */    this.earnest = earnest;
/*  92:    */  }
/*  93:    */  
/*  94:    */  public Double getBalance()
/*  95:    */  {
/*  96: 96 */    return this.balance;
/*  97:    */  }
/*  98:    */  
/*  99:    */  public void setBalance(Double balance)
/* 100:    */  {
/* 101:101 */    this.balance = balance;
/* 102:    */  }
/* 103:    */  
/* 104:    */  public Double getFee()
/* 105:    */  {
/* 106:106 */    return this.fee;
/* 107:    */  }
/* 108:    */  
/* 109:    */  public void setFee(Double fee)
/* 110:    */  {
/* 111:111 */    this.fee = fee;
/* 112:    */  }
/* 113:    */  
/* 114:    */  public Double getShipping()
/* 115:    */  {
/* 116:116 */    return this.shipping;
/* 117:    */  }
/* 118:    */  
/* 119:    */  public void setShipping(Double shipping)
/* 120:    */  {
/* 121:121 */    this.shipping = shipping;
/* 122:    */  }
/* 123:    */  
/* 124:    */  public Double getActualReceipts()
/* 125:    */  {
/* 126:126 */    return this.actualReceipts;
/* 127:    */  }
/* 128:    */  
/* 129:    */  public void setActualReceipts(Double actualReceipts)
/* 130:    */  {
/* 131:131 */    this.actualReceipts = actualReceipts;
/* 132:    */  }
/* 133:    */  
/* 134:    */  public Double getPayFees()
/* 135:    */  {
/* 136:136 */    return this.payFees;
/* 137:    */  }
/* 138:    */  
/* 139:    */  public void setPayFees(Double payFees)
/* 140:    */  {
/* 141:141 */    this.payFees = payFees;
/* 142:    */  }
/* 143:    */  
/* 144:    */  public Double getAgencyFees()
/* 145:    */  {
/* 146:146 */    return this.agencyFees;
/* 147:    */  }
/* 148:    */  
/* 149:    */  public void setAgencyFees(Double agencyFees)
/* 150:    */  {
/* 151:151 */    this.agencyFees = agencyFees;
/* 152:    */  }
/* 153:    */  
/* 154:    */  public Double getBillingFee()
/* 155:    */  {
/* 156:156 */    return this.billingFee;
/* 157:    */  }
/* 158:    */  
/* 159:    */  public void setBillingFee(Double billingFee)
/* 160:    */  {
/* 161:161 */    this.billingFee = billingFee;
/* 162:    */  }
/* 163:    */  
/* 164:    */  public Double getDrawback()
/* 165:    */  {
/* 166:166 */    return this.drawback;
/* 167:    */  }
/* 168:    */  
/* 169:    */  public void setDrawback(Double drawback)
/* 170:    */  {
/* 171:171 */    this.drawback = drawback;
/* 172:    */  }
/* 173:    */  
/* 174:    */  public Integer getStatus()
/* 175:    */  {
/* 176:176 */    return this.status;
/* 177:    */  }
/* 178:    */  
/* 179:    */  public void setStatus(Integer status)
/* 180:    */  {
/* 181:181 */    this.status = status;
/* 182:    */  }
/* 183:    */  
/* 184:    */  public Long getUserid()
/* 185:    */  {
/* 186:186 */    return this.userid;
/* 187:    */  }
/* 188:    */  
/* 189:    */  public void setUserid(Long userid)
/* 190:    */  {
/* 191:191 */    this.userid = userid;
/* 192:    */  }
/* 193:    */  
/* 194:    */  public String getReserved()
/* 195:    */  {
/* 196:196 */    return this.reserved;
/* 197:    */  }
/* 198:    */  
/* 199:    */  public void setReserved(String reserved)
/* 200:    */  {
/* 201:201 */    this.reserved = reserved;
/* 202:    */  }
/* 203:    */  
/* 204:    */  public boolean equals(Object obj)
/* 205:    */  {
/* 206:206 */    if (obj == null) return false;
/* 207:207 */    if (!(obj instanceof FinancialStatements)) { return false;
/* 208:    */    }
/* 209:209 */    FinancialStatements financialStatements = (FinancialStatements)obj;
/* 210:210 */    if ((getId() == null) || (financialStatements.getId() == null)) return false;
/* 211:211 */    return getId().equals(financialStatements.getId());
/* 212:    */  }
/* 213:    */  
/* 214:    */  public int hashCode()
/* 215:    */  {
/* 216:216 */    if (-2147483648 == this.hashCode) {
/* 217:217 */      if (getId() == null) { return super.hashCode();
/* 218:    */      }
/* 219:219 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 220:220 */      this.hashCode = hashStr.hashCode();
/* 221:    */    }
/* 222:    */    
/* 223:223 */    return this.hashCode;
/* 224:    */  }
/* 225:    */  
/* 226:    */  public String toString()
/* 227:    */  {
/* 228:228 */    return super.toString();
/* 229:    */  }
/* 230:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseFinancialStatements
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */