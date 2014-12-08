/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.ProcessingInformation;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseProcessingInformation
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "ProcessingInformation";
/*  10: 10 */  public static String PROP_QTY = "Qty";
/*  11: 11 */  public static String PROP_NUMBER = "Number";
/*  12: 12 */  public static String PROP_CONTACT = "Contact";
/*  13: 13 */  public static String PROP_PRICE = "Price";
/*  14: 14 */  public static String PROP_ID = "Id";
/*  15: 15 */  public static String PROP_CANTACT_WAY = "CantactWay";
/*  16: 16 */  public static String PROP_PROJECT = "Project";
/*  17: 17 */  public static String PROP_REMARK = "Remark";
/*  18: 18 */  public static String PROP_UNIT = "Unit";
/*  19:    */  
/*  20:    */  public BaseProcessingInformation()
/*  21:    */  {
/*  22: 22 */    initialize();
/*  23:    */  }
/*  24:    */  
/*  25:    */  public BaseProcessingInformation(Long id)
/*  26:    */  {
/*  27: 27 */    setId(id);
/*  28: 28 */    initialize();
/*  29:    */  }
/*  30:    */  
/*  31: 31 */  private int hashCode = -2147483648;
/*  32:    */  
/*  33:    */  private Long id;
/*  34:    */  
/*  35:    */  private String project;
/*  36:    */  
/*  37:    */  private String price;
/*  38:    */  
/*  39:    */  private String contact;
/*  40:    */  
/*  41:    */  private String unit;
/*  42:    */  
/*  43:    */  private String cantactWay;
/*  44:    */  
/*  45:    */  private String remark;
/*  46:    */  
/*  47:    */  private String number;
/*  48:    */  
/*  49:    */  private String qty;
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
/*  64:    */  public String getProject()
/*  65:    */  {
/*  66: 66 */    return this.project;
/*  67:    */  }
/*  68:    */  
/*  69:    */  public void setProject(String project)
/*  70:    */  {
/*  71: 71 */    this.project = project;
/*  72:    */  }
/*  73:    */  
/*  74:    */  public String getPrice()
/*  75:    */  {
/*  76: 76 */    return this.price;
/*  77:    */  }
/*  78:    */  
/*  79:    */  public void setPrice(String price)
/*  80:    */  {
/*  81: 81 */    this.price = price;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public String getContact()
/*  85:    */  {
/*  86: 86 */    return this.contact;
/*  87:    */  }
/*  88:    */  
/*  89:    */  public void setContact(String contact)
/*  90:    */  {
/*  91: 91 */    this.contact = contact;
/*  92:    */  }
/*  93:    */  
/*  94:    */  public String getUnit()
/*  95:    */  {
/*  96: 96 */    return this.unit;
/*  97:    */  }
/*  98:    */  
/*  99:    */  public void setUnit(String unit)
/* 100:    */  {
/* 101:101 */    this.unit = unit;
/* 102:    */  }
/* 103:    */  
/* 104:    */  public String getCantactWay()
/* 105:    */  {
/* 106:106 */    return this.cantactWay;
/* 107:    */  }
/* 108:    */  
/* 109:    */  public void setCantactWay(String cantactWay)
/* 110:    */  {
/* 111:111 */    this.cantactWay = cantactWay;
/* 112:    */  }
/* 113:    */  
/* 114:    */  public String getRemark()
/* 115:    */  {
/* 116:116 */    return this.remark;
/* 117:    */  }
/* 118:    */  
/* 119:    */  public void setRemark(String remark)
/* 120:    */  {
/* 121:121 */    this.remark = remark;
/* 122:    */  }
/* 123:    */  
/* 124:    */  public String getNumber()
/* 125:    */  {
/* 126:126 */    return this.number;
/* 127:    */  }
/* 128:    */  
/* 129:    */  public void setNumber(String number)
/* 130:    */  {
/* 131:131 */    this.number = number;
/* 132:    */  }
/* 133:    */  
/* 134:    */  public String getQty()
/* 135:    */  {
/* 136:136 */    return this.qty;
/* 137:    */  }
/* 138:    */  
/* 139:    */  public void setQty(String qty)
/* 140:    */  {
/* 141:141 */    this.qty = qty;
/* 142:    */  }
/* 143:    */  
/* 144:    */  public boolean equals(Object obj)
/* 145:    */  {
/* 146:146 */    if (obj == null) return false;
/* 147:147 */    if (!(obj instanceof ProcessingInformation)) { return false;
/* 148:    */    }
/* 149:149 */    ProcessingInformation processingInformation = (ProcessingInformation)obj;
/* 150:150 */    if ((getId() == null) || (processingInformation.getId() == null)) return false;
/* 151:151 */    return getId().equals(processingInformation.getId());
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
 * Qualified Name:     com.vincesu.business.entity.base.BaseProcessingInformation
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */