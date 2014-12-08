/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Suppliers;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseSuppliers
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "Suppliers";
/*  10: 10 */  public static String PROP_CONTACT_WAY = "ContactWay";
/*  11: 11 */  public static String PROP_MAIN_ITEMS = "MainItems";
/*  12: 12 */  public static String PROP_PHONE = "Phone";
/*  13: 13 */  public static String PROP_NUMBER = "Number";
/*  14: 14 */  public static String PROP_BANK = "Bank";
/*  15: 15 */  public static String PROP_ADDRESS = "Address";
/*  16: 16 */  public static String PROP_CONTACT = "Contact";
/*  17: 17 */  public static String PROP_BANK_ACCOUNT_NAME = "BankAccountName";
/*  18: 18 */  public static String PROP_RESERVED = "Reserved";
/*  19: 19 */  public static String PROP_BANK_ACCOUNT = "BankAccount";
/*  20: 20 */  public static String PROP_ID = "Id";
/*  21: 21 */  public static String PROP_UNIT = "Unit";
/*  22:    */  
/*  23:    */  public BaseSuppliers()
/*  24:    */  {
/*  25: 25 */    initialize();
/*  26:    */  }
/*  27:    */  
/*  28:    */  public BaseSuppliers(Long id)
/*  29:    */  {
/*  30: 30 */    setId(id);
/*  31: 31 */    initialize();
/*  32:    */  }
/*  33:    */  
/*  34: 34 */  private int hashCode = -2147483648;
/*  35:    */  
/*  36:    */  private Long id;
/*  37:    */  
/*  38:    */  private String number;
/*  39:    */  
/*  40:    */  private String contact;
/*  41:    */  
/*  42:    */  private String unit;
/*  43:    */  
/*  44:    */  private String phone;
/*  45:    */  
/*  46:    */  private String mainItems;
/*  47:    */  
/*  48:    */  private String contactWay;
/*  49:    */  
/*  50:    */  private String address;
/*  51:    */  
/*  52:    */  private String bank;
/*  53:    */  private String bankAccount;
/*  54:    */  private String bankAccountName;
/*  55:    */  private String reserved;
/*  56:    */  
/*  57:    */  protected void initialize() {}
/*  58:    */  
/*  59:    */  public Long getId()
/*  60:    */  {
/*  61: 61 */    return this.id;
/*  62:    */  }
/*  63:    */  
/*  64:    */  public void setId(Long id)
/*  65:    */  {
/*  66: 66 */    this.id = id;
/*  67: 67 */    this.hashCode = -2147483648;
/*  68:    */  }
/*  69:    */  
/*  70:    */  public String getNumber()
/*  71:    */  {
/*  72: 72 */    return this.number;
/*  73:    */  }
/*  74:    */  
/*  75:    */  public void setNumber(String number)
/*  76:    */  {
/*  77: 77 */    this.number = number;
/*  78:    */  }
/*  79:    */  
/*  80:    */  public String getContact()
/*  81:    */  {
/*  82: 82 */    return this.contact;
/*  83:    */  }
/*  84:    */  
/*  85:    */  public void setContact(String contact)
/*  86:    */  {
/*  87: 87 */    this.contact = contact;
/*  88:    */  }
/*  89:    */  
/*  90:    */  public String getUnit()
/*  91:    */  {
/*  92: 92 */    return this.unit;
/*  93:    */  }
/*  94:    */  
/*  95:    */  public void setUnit(String unit)
/*  96:    */  {
/*  97: 97 */    this.unit = unit;
/*  98:    */  }
/*  99:    */  
/* 100:    */  public String getPhone()
/* 101:    */  {
/* 102:102 */    return this.phone;
/* 103:    */  }
/* 104:    */  
/* 105:    */  public void setPhone(String phone)
/* 106:    */  {
/* 107:107 */    this.phone = phone;
/* 108:    */  }
/* 109:    */  
/* 110:    */  public String getMainItems()
/* 111:    */  {
/* 112:112 */    return this.mainItems;
/* 113:    */  }
/* 114:    */  
/* 115:    */  public void setMainItems(String mainItems)
/* 116:    */  {
/* 117:117 */    this.mainItems = mainItems;
/* 118:    */  }
/* 119:    */  
/* 120:    */  public String getContactWay()
/* 121:    */  {
/* 122:122 */    return this.contactWay;
/* 123:    */  }
/* 124:    */  
/* 125:    */  public void setContactWay(String contactWay)
/* 126:    */  {
/* 127:127 */    this.contactWay = contactWay;
/* 128:    */  }
/* 129:    */  
/* 130:    */  public String getAddress()
/* 131:    */  {
/* 132:132 */    return this.address;
/* 133:    */  }
/* 134:    */  
/* 135:    */  public void setAddress(String address)
/* 136:    */  {
/* 137:137 */    this.address = address;
/* 138:    */  }
/* 139:    */  
/* 140:    */  public String getBank()
/* 141:    */  {
/* 142:142 */    return this.bank;
/* 143:    */  }
/* 144:    */  
/* 145:    */  public void setBank(String bank)
/* 146:    */  {
/* 147:147 */    this.bank = bank;
/* 148:    */  }
/* 149:    */  
/* 150:    */  public String getBankAccount()
/* 151:    */  {
/* 152:152 */    return this.bankAccount;
/* 153:    */  }
/* 154:    */  
/* 155:    */  public void setBankAccount(String bankAccount)
/* 156:    */  {
/* 157:157 */    this.bankAccount = bankAccount;
/* 158:    */  }
/* 159:    */  
/* 160:    */  public String getBankAccountName()
/* 161:    */  {
/* 162:162 */    return this.bankAccountName;
/* 163:    */  }
/* 164:    */  
/* 165:    */  public void setBankAccountName(String bankAccountName)
/* 166:    */  {
/* 167:167 */    this.bankAccountName = bankAccountName;
/* 168:    */  }
/* 169:    */  
/* 170:    */  public String getReserved()
/* 171:    */  {
/* 172:172 */    return this.reserved;
/* 173:    */  }
/* 174:    */  
/* 175:    */  public void setReserved(String reserved)
/* 176:    */  {
/* 177:177 */    this.reserved = reserved;
/* 178:    */  }
/* 179:    */  
/* 180:    */  public boolean equals(Object obj)
/* 181:    */  {
/* 182:182 */    if (obj == null) return false;
/* 183:183 */    if (!(obj instanceof Suppliers)) { return false;
/* 184:    */    }
/* 185:185 */    Suppliers suppliers = (Suppliers)obj;
/* 186:186 */    if ((getId() == null) || (suppliers.getId() == null)) return false;
/* 187:187 */    return getId().equals(suppliers.getId());
/* 188:    */  }
/* 189:    */  
/* 190:    */  public int hashCode()
/* 191:    */  {
/* 192:192 */    if (-2147483648 == this.hashCode) {
/* 193:193 */      if (getId() == null) { return super.hashCode();
/* 194:    */      }
/* 195:195 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 196:196 */      this.hashCode = hashStr.hashCode();
/* 197:    */    }
/* 198:    */    
/* 199:199 */    return this.hashCode;
/* 200:    */  }
/* 201:    */  
/* 202:    */  public String toString()
/* 203:    */  {
/* 204:204 */    return super.toString();
/* 205:    */  }
/* 206:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseSuppliers
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */