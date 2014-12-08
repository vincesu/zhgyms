/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Production;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseProduction
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "Production";
/*  10: 10 */  public static String PROP_AMOUNT = "Amount";
/*  11: 11 */  public static String PROP_DESCRIPTION = "Description";
/*  12: 12 */  public static String PROP_NUMBER = "Number";
/*  13: 13 */  public static String PROP_ORDER_ID = "OrderId";
/*  14: 14 */  public static String PROP_RESERVED = "Reserved";
/*  15: 15 */  public static String PROP_ID = "Id";
/*  16: 16 */  public static String PROP_PACKING = "Packing";
/*  17: 17 */  public static String PROP_REMARK = "Remark";
/*  18: 18 */  public static String PROP_PRODUCT_ID = "ProductId";
/*  19:    */  
/*  20:    */  public BaseProduction()
/*  21:    */  {
/*  22: 22 */    initialize();
/*  23:    */  }
/*  24:    */  
/*  25:    */  public BaseProduction(Long id)
/*  26:    */  {
/*  27: 27 */    setId(id);
/*  28: 28 */    initialize();
/*  29:    */  }
/*  30:    */  
/*  31: 31 */  private int hashCode = -2147483648;
/*  32:    */  
/*  33:    */  private Long id;
/*  34:    */  
/*  35:    */  private Long productId;
/*  36:    */  
/*  37:    */  private String number;
/*  38:    */  
/*  39:    */  private Long orderId;
/*  40:    */  
/*  41:    */  private String reserved;
/*  42:    */  
/*  43:    */  private String description;
/*  44:    */  
/*  45:    */  private String amount;
/*  46:    */  
/*  47:    */  private String packing;
/*  48:    */  
/*  49:    */  private String remark;
/*  50:    */  
/*  51:    */  protected void initialize() {}
/*  52:    */  
/*  53:    */  public Long getId()
/*  54:    */  {
/*  55: 55 */    return this.id;
/*  56:    */  }
/*  57:    */  
/*  58:    */  public void setId(Long id)
/*  59:    */  {
/*  60: 60 */    this.id = id;
/*  61: 61 */    this.hashCode = -2147483648;
/*  62:    */  }
/*  63:    */  
/*  64:    */  public Long getProductId()
/*  65:    */  {
/*  66: 66 */    return this.productId;
/*  67:    */  }
/*  68:    */  
/*  69:    */  public void setProductId(Long productId)
/*  70:    */  {
/*  71: 71 */    this.productId = productId;
/*  72:    */  }
/*  73:    */  
/*  74:    */  public String getNumber()
/*  75:    */  {
/*  76: 76 */    return this.number;
/*  77:    */  }
/*  78:    */  
/*  79:    */  public void setNumber(String number)
/*  80:    */  {
/*  81: 81 */    this.number = number;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public Long getOrderId()
/*  85:    */  {
/*  86: 86 */    return this.orderId;
/*  87:    */  }
/*  88:    */  
/*  89:    */  public void setOrderId(Long orderId)
/*  90:    */  {
/*  91: 91 */    this.orderId = orderId;
/*  92:    */  }
/*  93:    */  
/*  94:    */  public String getReserved()
/*  95:    */  {
/*  96: 96 */    return this.reserved;
/*  97:    */  }
/*  98:    */  
/*  99:    */  public void setReserved(String reserved)
/* 100:    */  {
/* 101:101 */    this.reserved = reserved;
/* 102:    */  }
/* 103:    */  
/* 104:    */  public String getDescription()
/* 105:    */  {
/* 106:106 */    return this.description;
/* 107:    */  }
/* 108:    */  
/* 109:    */  public void setDescription(String description)
/* 110:    */  {
/* 111:111 */    this.description = description;
/* 112:    */  }
/* 113:    */  
/* 114:    */  public String getAmount()
/* 115:    */  {
/* 116:116 */    return this.amount;
/* 117:    */  }
/* 118:    */  
/* 119:    */  public void setAmount(String amount)
/* 120:    */  {
/* 121:121 */    this.amount = amount;
/* 122:    */  }
/* 123:    */  
/* 124:    */  public String getPacking()
/* 125:    */  {
/* 126:126 */    return this.packing;
/* 127:    */  }
/* 128:    */  
/* 129:    */  public void setPacking(String packing)
/* 130:    */  {
/* 131:131 */    this.packing = packing;
/* 132:    */  }
/* 133:    */  
/* 134:    */  public String getRemark()
/* 135:    */  {
/* 136:136 */    return this.remark;
/* 137:    */  }
/* 138:    */  
/* 139:    */  public void setRemark(String remark)
/* 140:    */  {
/* 141:141 */    this.remark = remark;
/* 142:    */  }
/* 143:    */  
/* 144:    */  public boolean equals(Object obj)
/* 145:    */  {
/* 146:146 */    if (obj == null) return false;
/* 147:147 */    if (!(obj instanceof Production)) { return false;
/* 148:    */    }
/* 149:149 */    Production production = (Production)obj;
/* 150:150 */    if ((getId() == null) || (production.getId() == null)) return false;
/* 151:151 */    return getId().equals(production.getId());
/* 152:    */  }
/* 153:    */  
/* 154:    */  public int hashCode()
/* 155:    */  {
/* 156:156 */    if (-2147483648 == this.hashCode) {
/* 157:157 */      if (getId() == null) { return super.hashCode();
/* 158:    */      }
/* 159:159 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 160:160 */      this.hashCode = hashStr.hashCode();
/* 161:    */    }
/* 162:    */    
/* 163:163 */    return this.hashCode;
/* 164:    */  }
/* 165:    */  
/* 166:    */  public String toString()
/* 167:    */  {
/* 168:168 */    return super.toString();
/* 169:    */  }
/* 170:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseProduction
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */