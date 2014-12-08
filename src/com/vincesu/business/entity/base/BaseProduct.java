/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Product;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseProduct
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "Product";
/*  10: 10 */  public static String PROP_NAME = "Name";
/*  11: 11 */  public static String PROP_CATEGORY = "Category";
/*  12: 12 */  public static String PROP_DESCRIPTION = "Description";
/*  13: 13 */  public static String PROP_REFERENCE_PRICE = "ReferencePrice";
/*  14: 14 */  public static String PROP_NUMBER = "Number";
/*  15: 15 */  public static String PROP_RESERVED = "Reserved";
/*  16: 16 */  public static String PROP_ID = "Id";
/*  17: 17 */  public static String PROP_PERMISSIONS = "Permissions";
/*  18: 18 */  public static String PROP_MOQ = "Moq";
/*  19: 19 */  public static String PROP_SIZE = "Size";
/*  20:    */  
/*  21:    */  public BaseProduct()
/*  22:    */  {
/*  23: 23 */    initialize();
/*  24:    */  }
/*  25:    */  
/*  26:    */  public BaseProduct(Long id)
/*  27:    */  {
/*  28: 28 */    setId(id);
/*  29: 29 */    initialize();
/*  30:    */  }
/*  31:    */  
/*  32: 32 */  private int hashCode = -2147483648;
/*  33:    */  
/*  34:    */  private Long id;
/*  35:    */  
/*  36:    */  private String name;
/*  37:    */  
/*  38:    */  private String number;
/*  39:    */  
/*  40:    */  private String size;
/*  41:    */  
/*  42:    */  private String referencePrice;
/*  43:    */  
/*  44:    */  private String moq;
/*  45:    */  
/*  46:    */  private String description;
/*  47:    */  
/*  48:    */  private String category;
/*  49:    */  
/*  50:    */  private String permissions;
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
/*  66:    */  public String getName()
/*  67:    */  {
/*  68: 68 */    return this.name;
/*  69:    */  }
/*  70:    */  
/*  71:    */  public void setName(String name)
/*  72:    */  {
/*  73: 73 */    this.name = name;
/*  74:    */  }
/*  75:    */  
/*  76:    */  public String getNumber()
/*  77:    */  {
/*  78: 78 */    return this.number;
/*  79:    */  }
/*  80:    */  
/*  81:    */  public void setNumber(String number)
/*  82:    */  {
/*  83: 83 */    this.number = number;
/*  84:    */  }
/*  85:    */  
/*  86:    */  public String getSize()
/*  87:    */  {
/*  88: 88 */    return this.size;
/*  89:    */  }
/*  90:    */  
/*  91:    */  public void setSize(String size)
/*  92:    */  {
/*  93: 93 */    this.size = size;
/*  94:    */  }
/*  95:    */  
/*  96:    */  public String getReferencePrice()
/*  97:    */  {
/*  98: 98 */    return this.referencePrice;
/*  99:    */  }
/* 100:    */  
/* 101:    */  public void setReferencePrice(String referencePrice)
/* 102:    */  {
/* 103:103 */    this.referencePrice = referencePrice;
/* 104:    */  }
/* 105:    */  
/* 106:    */  public String getMoq()
/* 107:    */  {
/* 108:108 */    return this.moq;
/* 109:    */  }
/* 110:    */  
/* 111:    */  public void setMoq(String moq)
/* 112:    */  {
/* 113:113 */    this.moq = moq;
/* 114:    */  }
/* 115:    */  
/* 116:    */  public String getDescription()
/* 117:    */  {
/* 118:118 */    return this.description;
/* 119:    */  }
/* 120:    */  
/* 121:    */  public void setDescription(String description)
/* 122:    */  {
/* 123:123 */    this.description = description;
/* 124:    */  }
/* 125:    */  
/* 126:    */  public String getCategory()
/* 127:    */  {
/* 128:128 */    return this.category;
/* 129:    */  }
/* 130:    */  
/* 131:    */  public void setCategory(String category)
/* 132:    */  {
/* 133:133 */    this.category = category;
/* 134:    */  }
/* 135:    */  
/* 136:    */  public String getPermissions()
/* 137:    */  {
/* 138:138 */    return this.permissions;
/* 139:    */  }
/* 140:    */  
/* 141:    */  public void setPermissions(String permissions)
/* 142:    */  {
/* 143:143 */    this.permissions = permissions;
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
/* 159:159 */    if (!(obj instanceof Product)) { return false;
/* 160:    */    }
/* 161:161 */    Product product = (Product)obj;
/* 162:162 */    if ((getId() == null) || (product.getId() == null)) return false;
/* 163:163 */    return getId().equals(product.getId());
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
 * Qualified Name:     com.vincesu.business.entity.base.BaseProduct
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */