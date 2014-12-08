/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.ContractDetail;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseContractDetail
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "ContractDetail";
/*  10: 10 */  public static String PROP_QTY = "Qty";
/*  11: 11 */  public static String PROP_AMOUNT = "Amount";
/*  12: 12 */  public static String PROP_DESCRIPTION = "Description";
/*  13: 13 */  public static String PROP_NUMBER = "Number";
/*  14: 14 */  public static String PROP_CONTRACT_ID = "ContractId";
/*  15: 15 */  public static String PROP_RESERVED = "Reserved";
/*  16: 16 */  public static String PROP_ID = "Id";
/*  17: 17 */  public static String PROP_REMARK = "Remark";
/*  18: 18 */  public static String PROP_UNIT = "Unit";
/*  19: 19 */  public static String PROP_PRODUCT_ID = "ProductId";
/*  20:    */  
/*  21:    */  public BaseContractDetail()
/*  22:    */  {
/*  23: 23 */    initialize();
/*  24:    */  }
/*  25:    */  
/*  26:    */  public BaseContractDetail(Long id)
/*  27:    */  {
/*  28: 28 */    setId(id);
/*  29: 29 */    initialize();
/*  30:    */  }
/*  31:    */  
/*  32: 32 */  private int hashCode = -2147483648;
/*  33:    */  
/*  34:    */  private Long id;
/*  35:    */  
/*  36:    */  private Long contractId;
/*  37:    */  
/*  38:    */  private Long productId;
/*  39:    */  
/*  40:    */  private String number;
/*  41:    */  
/*  42:    */  private String description;
/*  43:    */  
/*  44:    */  private Integer qty;
/*  45:    */  
/*  46:    */  private Double unit;
/*  47:    */  
/*  48:    */  private Double amount;
/*  49:    */  
/*  50:    */  private String remark;
/*  51:    */  private String reserved;
/*  52:    */  
/*  53:    */  protected void initialize() {}
/*  54:    */  
/*  55:    */  public Long getId()
/*  56:    */  {
/*  57: 57 */    return this.id;
/*  58:    */  }
/*  59:    */  
/*  60:    */  public void setId(Long id)
/*  61:    */  {
/*  62: 62 */    this.id = id;
/*  63: 63 */    this.hashCode = -2147483648;
/*  64:    */  }
/*  65:    */  
/*  66:    */  public Long getContractId()
/*  67:    */  {
/*  68: 68 */    return this.contractId;
/*  69:    */  }
/*  70:    */  
/*  71:    */  public void setContractId(Long contractId)
/*  72:    */  {
/*  73: 73 */    this.contractId = contractId;
/*  74:    */  }
/*  75:    */  
/*  76:    */  public Long getProductId()
/*  77:    */  {
/*  78: 78 */    return this.productId;
/*  79:    */  }
/*  80:    */  
/*  81:    */  public void setProductId(Long productId)
/*  82:    */  {
/*  83: 83 */    this.productId = productId;
/*  84:    */  }
/*  85:    */  
/*  86:    */  public String getNumber()
/*  87:    */  {
/*  88: 88 */    return this.number;
/*  89:    */  }
/*  90:    */  
/*  91:    */  public void setNumber(String number)
/*  92:    */  {
/*  93: 93 */    this.number = number;
/*  94:    */  }
/*  95:    */  
/*  96:    */  public String getDescription()
/*  97:    */  {
/*  98: 98 */    return this.description;
/*  99:    */  }
/* 100:    */  
/* 101:    */  public void setDescription(String description)
/* 102:    */  {
/* 103:103 */    this.description = description;
/* 104:    */  }
/* 105:    */  
/* 106:    */  public Integer getQty()
/* 107:    */  {
/* 108:108 */    return this.qty;
/* 109:    */  }
/* 110:    */  
/* 111:    */  public void setQty(Integer qty)
/* 112:    */  {
/* 113:113 */    this.qty = qty;
/* 114:    */  }
/* 115:    */  
/* 116:    */  public Double getUnit()
/* 117:    */  {
/* 118:118 */    return this.unit;
/* 119:    */  }
/* 120:    */  
/* 121:    */  public void setUnit(Double unit)
/* 122:    */  {
/* 123:123 */    this.unit = unit;
/* 124:    */  }
/* 125:    */  
/* 126:    */  public Double getAmount()
/* 127:    */  {
/* 128:128 */    return this.amount;
/* 129:    */  }
/* 130:    */  
/* 131:    */  public void setAmount(Double amount)
/* 132:    */  {
/* 133:133 */    this.amount = amount;
/* 134:    */  }
/* 135:    */  
/* 136:    */  public String getRemark()
/* 137:    */  {
/* 138:138 */    return this.remark;
/* 139:    */  }
/* 140:    */  
/* 141:    */  public void setRemark(String remark)
/* 142:    */  {
/* 143:143 */    this.remark = remark;
/* 144:    */  }
/* 145:    */  
/* 146:    */  public String getReserved()
/* 147:    */  {
/* 148:148 */    return this.reserved;
/* 149:    */  }
/* 150:    */  
/* 151:    */  public void setReserved(String reserved)
/* 152:    */  {
/* 153:153 */    this.reserved = reserved;
/* 154:    */  }
/* 155:    */  
/* 156:    */  public boolean equals(Object obj)
/* 157:    */  {
/* 158:158 */    if (obj == null) return false;
/* 159:159 */    if (!(obj instanceof ContractDetail)) { return false;
/* 160:    */    }
/* 161:161 */    ContractDetail contractDetail = (ContractDetail)obj;
/* 162:162 */    if ((getId() == null) || (contractDetail.getId() == null)) return false;
/* 163:163 */    return getId().equals(contractDetail.getId());
/* 164:    */  }
/* 165:    */  
/* 166:    */  public int hashCode()
/* 167:    */  {
/* 168:168 */    if (-2147483648 == this.hashCode) {
/* 169:169 */      if (getId() == null) { return super.hashCode();
/* 170:    */      }
/* 171:171 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 172:172 */      this.hashCode = hashStr.hashCode();
/* 173:    */    }
/* 174:    */    
/* 175:175 */    return this.hashCode;
/* 176:    */  }
/* 177:    */  
/* 178:    */  public String toString()
/* 179:    */  {
/* 180:180 */    return super.toString();
/* 181:    */  }
/* 182:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseContractDetail
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */