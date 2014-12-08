/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.ManufactureOrder;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseManufactureOrder
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "ManufactureOrder";
/*  11: 11 */  public static String PROP_NAME = "Name";
/*  12: 12 */  public static String PROP_AUDIT = "Audit";
/*  13: 13 */  public static String PROP_IS_AUDIT = "IsAudit";
/*  14: 14 */  public static String PROP_DATE = "Date";
/*  15: 15 */  public static String PROP_CONTRACT_ID = "ContractId";
/*  16: 16 */  public static String PROP_DELIVERY_TIME = "DeliveryTime";
/*  17: 17 */  public static String PROP_NUMBER = "Number";
/*  18: 18 */  public static String PROP_ID = "Id";
/*  19: 19 */  public static String PROP_SALESMAN = "Salesman";
/*  20: 20 */  public static String PROP_REMARK = "Remark";
/*  21: 21 */  public static String PROP_DESTINATION = "Destination";
/*  22: 22 */  public static String PROP_CREATEC = "Createc";
/*  23:    */  
/*  24:    */  public BaseManufactureOrder()
/*  25:    */  {
/*  26: 26 */    initialize();
/*  27:    */  }
/*  28:    */  
/*  29:    */  public BaseManufactureOrder(Long id)
/*  30:    */  {
/*  31: 31 */    setId(id);
/*  32: 32 */    initialize();
/*  33:    */  }
/*  34:    */  
/*  35: 35 */  private int hashCode = -2147483648;
/*  36:    */  
/*  37:    */  private Long id;
/*  38:    */  
/*  39:    */  private String name;
/*  40:    */  
/*  41:    */  private String number;
/*  42:    */  
/*  43:    */  private Date date;
/*  44:    */  
/*  45:    */  private Date deliveryTime;
/*  46:    */  
/*  47:    */  private Long salesman;
/*  48:    */  
/*  49:    */  private String destination;
/*  50:    */  
/*  51:    */  private boolean isAudit;
/*  52:    */  
/*  53:    */  private String audit;
/*  54:    */  private Long contractId;
/*  55:    */  private String remark;
/*  56:    */  private Integer createc;
/*  57:    */  
/*  58:    */  protected void initialize() {}
/*  59:    */  
/*  60:    */  public Long getId()
/*  61:    */  {
/*  62: 62 */    return this.id;
/*  63:    */  }
/*  64:    */  
/*  65:    */  public void setId(Long id)
/*  66:    */  {
/*  67: 67 */    this.id = id;
/*  68: 68 */    this.hashCode = -2147483648;
/*  69:    */  }
/*  70:    */  
/*  71:    */  public String getName()
/*  72:    */  {
/*  73: 73 */    return this.name;
/*  74:    */  }
/*  75:    */  
/*  76:    */  public void setName(String name)
/*  77:    */  {
/*  78: 78 */    this.name = name;
/*  79:    */  }
/*  80:    */  
/*  81:    */  public String getNumber()
/*  82:    */  {
/*  83: 83 */    return this.number;
/*  84:    */  }
/*  85:    */  
/*  86:    */  public void setNumber(String number)
/*  87:    */  {
/*  88: 88 */    this.number = number;
/*  89:    */  }
/*  90:    */  
/*  91:    */  public Date getDate()
/*  92:    */  {
/*  93: 93 */    return this.date;
/*  94:    */  }
/*  95:    */  
/*  96:    */  public void setDate(Date date)
/*  97:    */  {
/*  98: 98 */    this.date = date;
/*  99:    */  }
/* 100:    */  
/* 101:    */  public Date getDeliveryTime()
/* 102:    */  {
/* 103:103 */    return this.deliveryTime;
/* 104:    */  }
/* 105:    */  
/* 106:    */  public void setDeliveryTime(Date deliveryTime)
/* 107:    */  {
/* 108:108 */    this.deliveryTime = deliveryTime;
/* 109:    */  }
/* 110:    */  
/* 111:    */  public Long getSalesman()
/* 112:    */  {
/* 113:113 */    return this.salesman;
/* 114:    */  }
/* 115:    */  
/* 116:    */  public void setSalesman(Long salesman)
/* 117:    */  {
/* 118:118 */    this.salesman = salesman;
/* 119:    */  }
/* 120:    */  
/* 121:    */  public String getDestination()
/* 122:    */  {
/* 123:123 */    return this.destination;
/* 124:    */  }
/* 125:    */  
/* 126:    */  public void setDestination(String destination)
/* 127:    */  {
/* 128:128 */    this.destination = destination;
/* 129:    */  }
/* 130:    */  
/* 131:    */  public boolean getIsAudit()
/* 132:    */  {
/* 133:133 */    return this.isAudit;
/* 134:    */  }
/* 135:    */  
/* 136:    */  public void setIsAudit(boolean isAudit)
/* 137:    */  {
/* 138:138 */    this.isAudit = isAudit;
/* 139:    */  }
/* 140:    */  
/* 141:    */  public String getAudit()
/* 142:    */  {
/* 143:143 */    return this.audit;
/* 144:    */  }
/* 145:    */  
/* 146:    */  public void setAudit(String audit)
/* 147:    */  {
/* 148:148 */    this.audit = audit;
/* 149:    */  }
/* 150:    */  
/* 151:    */  public Long getContractId()
/* 152:    */  {
/* 153:153 */    return this.contractId;
/* 154:    */  }
/* 155:    */  
/* 156:    */  public void setContractId(Long contractId)
/* 157:    */  {
/* 158:158 */    this.contractId = contractId;
/* 159:    */  }
/* 160:    */  
/* 161:    */  public String getRemark()
/* 162:    */  {
/* 163:163 */    return this.remark;
/* 164:    */  }
/* 165:    */  
/* 166:    */  public void setRemark(String remark)
/* 167:    */  {
/* 168:168 */    this.remark = remark;
/* 169:    */  }
/* 170:    */  
/* 171:    */  public boolean equals(Object obj)
/* 172:    */  {
/* 173:173 */    if (obj == null) return false;
/* 174:174 */    if (!(obj instanceof ManufactureOrder)) { return false;
/* 175:    */    }
/* 176:176 */    ManufactureOrder manufactureOrder = (ManufactureOrder)obj;
/* 177:177 */    if ((getId() == null) || (manufactureOrder.getId() == null)) return false;
/* 178:178 */    return getId().equals(manufactureOrder.getId());
/* 179:    */  }
/* 180:    */  
/* 181:    */  public int hashCode()
/* 182:    */  {
/* 183:183 */    if (-2147483648 == this.hashCode) {
/* 184:184 */      if (getId() == null) { return super.hashCode();
/* 185:    */      }
/* 186:186 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 187:187 */      this.hashCode = hashStr.hashCode();
/* 188:    */    }
/* 189:    */    
/* 190:190 */    return this.hashCode;
/* 191:    */  }
/* 192:    */  
/* 193:    */  public String toString()
/* 194:    */  {
/* 195:195 */    return super.toString();
/* 196:    */  }
/* 197:    */  
/* 198:    */  public Integer getCreatec() {
/* 199:199 */    return this.createc;
/* 200:    */  }
/* 201:    */  
/* 202:    */  public void setCreatec(Integer cpf) {
/* 203:203 */    this.createc = cpf;
/* 204:    */  }
/* 205:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseManufactureOrder
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */