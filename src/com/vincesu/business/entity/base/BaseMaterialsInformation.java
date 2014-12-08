/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.MaterialsInformation;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseMaterialsInformation
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "MaterialsInformation";
/*  10: 10 */  public static String PROP_NAME = "Name";
/*  11: 11 */  public static String PROP_CATEGORY = "Category";
/*  12: 12 */  public static String PROP_PHONE = "Phone";
/*  13: 13 */  public static String PROP_NUMBER = "Number";
/*  14: 14 */  public static String PROP_CONTACT = "Contact";
/*  15: 15 */  public static String PROP_PRICE = "Price";
/*  16: 16 */  public static String PROP_ID = "Id";
/*  17: 17 */  public static String PROP_MANUFACTURERS = "Manufacturers";
/*  18: 18 */  public static String PROP_SIZE = "Size";
/*  19:    */  
/*  20:    */  public BaseMaterialsInformation()
/*  21:    */  {
/*  22: 22 */    initialize();
/*  23:    */  }
/*  24:    */  
/*  25:    */  public BaseMaterialsInformation(Long id)
/*  26:    */  {
/*  27: 27 */    setId(id);
/*  28: 28 */    initialize();
/*  29:    */  }
/*  30:    */  
/*  31: 31 */  private int hashCode = -2147483648;
/*  32:    */  
/*  33:    */  private Long id;
/*  34:    */  
/*  35:    */  private String number;
/*  36:    */  
/*  37:    */  private String name;
/*  38:    */  
/*  39:    */  private String size;
/*  40:    */  
/*  41:    */  private String price;
/*  42:    */  
/*  43:    */  private String manufacturers;
/*  44:    */  
/*  45:    */  private String contact;
/*  46:    */  
/*  47:    */  private String phone;
/*  48:    */  
/*  49:    */  private String category;
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
/*  64:    */  public String getNumber()
/*  65:    */  {
/*  66: 66 */    return this.number;
/*  67:    */  }
/*  68:    */  
/*  69:    */  public void setNumber(String number)
/*  70:    */  {
/*  71: 71 */    this.number = number;
/*  72:    */  }
/*  73:    */  
/*  74:    */  public String getName()
/*  75:    */  {
/*  76: 76 */    return this.name;
/*  77:    */  }
/*  78:    */  
/*  79:    */  public void setName(String name)
/*  80:    */  {
/*  81: 81 */    this.name = name;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public String getSize()
/*  85:    */  {
/*  86: 86 */    return this.size;
/*  87:    */  }
/*  88:    */  
/*  89:    */  public void setSize(String size)
/*  90:    */  {
/*  91: 91 */    this.size = size;
/*  92:    */  }
/*  93:    */  
/*  94:    */  public String getPrice()
/*  95:    */  {
/*  96: 96 */    return this.price;
/*  97:    */  }
/*  98:    */  
/*  99:    */  public void setPrice(String price)
/* 100:    */  {
/* 101:101 */    this.price = price;
/* 102:    */  }
/* 103:    */  
/* 104:    */  public String getManufacturers()
/* 105:    */  {
/* 106:106 */    return this.manufacturers;
/* 107:    */  }
/* 108:    */  
/* 109:    */  public void setManufacturers(String manufacturers)
/* 110:    */  {
/* 111:111 */    this.manufacturers = manufacturers;
/* 112:    */  }
/* 113:    */  
/* 114:    */  public String getContact()
/* 115:    */  {
/* 116:116 */    return this.contact;
/* 117:    */  }
/* 118:    */  
/* 119:    */  public void setContact(String contact)
/* 120:    */  {
/* 121:121 */    this.contact = contact;
/* 122:    */  }
/* 123:    */  
/* 124:    */  public String getPhone()
/* 125:    */  {
/* 126:126 */    return this.phone;
/* 127:    */  }
/* 128:    */  
/* 129:    */  public void setPhone(String phone)
/* 130:    */  {
/* 131:131 */    this.phone = phone;
/* 132:    */  }
/* 133:    */  
/* 134:    */  public String getCategory()
/* 135:    */  {
/* 136:136 */    return this.category;
/* 137:    */  }
/* 138:    */  
/* 139:    */  public void setCategory(String category)
/* 140:    */  {
/* 141:141 */    this.category = category;
/* 142:    */  }
/* 143:    */  
/* 144:    */  public boolean equals(Object obj)
/* 145:    */  {
/* 146:146 */    if (obj == null) return false;
/* 147:147 */    if (!(obj instanceof MaterialsInformation)) { return false;
/* 148:    */    }
/* 149:149 */    MaterialsInformation materialsInformation = (MaterialsInformation)obj;
/* 150:150 */    if ((getId() == null) || (materialsInformation.getId() == null)) return false;
/* 151:151 */    return getId().equals(materialsInformation.getId());
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
 * Qualified Name:     com.vincesu.business.entity.base.BaseMaterialsInformation
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */