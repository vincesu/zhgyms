/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.AddressBook;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseAddressBook implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "AddressBook";
/*  10: 10 */  public static String PROP_GRADUATE_SCHOOL = "GraduateSchool";
/*  11: 11 */  public static String PROP_PHONE = "Phone";
/*  12: 12 */  public static String PROP_NUMBER = "Number";
/*  13: 13 */  public static String PROP_POSI = "Posi";
/*  14: 14 */  public static String PROP_ENTRY_DATE = "EntryDate";
/*  15: 15 */  public static String PROP_NAME = "Name";
/*  16: 16 */  public static String PROP_BIRTHDAY = "Birthday";
/*  17: 17 */  public static String PROP_OFFICE_PHONE = "OfficePhone";
/*  18: 18 */  public static String PROP_EMAIL = "Email";
/*  19: 19 */  public static String PROP_EDUCATIONAL_BACKGROUND = "EducationalBackground";
/*  20: 20 */  public static String PROP_GENDER = "Gender";
/*  21: 21 */  public static String PROP_ID = "Id";
/*  22: 22 */  public static String PROP_DEPARTMENT = "Department";
/*  23:    */  
/*  24:    */  public BaseAddressBook()
/*  25:    */  {
/*  26: 26 */    initialize();
/*  27:    */  }
/*  28:    */  
/*  29:    */  public BaseAddressBook(Long id)
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
/*  41:    */  private String gender;
/*  42:    */  
/*  43:    */  private String officePhone;
/*  44:    */  
/*  45:    */  private String phone;
/*  46:    */  
/*  47:    */  private String email;
/*  48:    */  
/*  49:    */  private String number;
/*  50:    */  
/*  51:    */  private String department;
/*  52:    */  
/*  53:    */  private String posi;
/*  54:    */  
/*  55:    */  private Date birthday;
/*  56:    */  
/*  57:    */  private String graduateSchool;
/*  58:    */  private String educationalBackground;
/*  59:    */  private Date entryDate;
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
/*  84:    */  public String getGender()
/*  85:    */  {
/*  86: 86 */    return this.gender;
/*  87:    */  }
/*  88:    */  
/*  89:    */  public void setGender(String gender)
/*  90:    */  {
/*  91: 91 */    this.gender = gender;
/*  92:    */  }
/*  93:    */  
/*  94:    */  public String getOfficePhone()
/*  95:    */  {
/*  96: 96 */    return this.officePhone;
/*  97:    */  }
/*  98:    */  
/*  99:    */  public void setOfficePhone(String officePhone)
/* 100:    */  {
/* 101:101 */    this.officePhone = officePhone;
/* 102:    */  }
/* 103:    */  
/* 104:    */  public String getPhone()
/* 105:    */  {
/* 106:106 */    return this.phone;
/* 107:    */  }
/* 108:    */  
/* 109:    */  public void setPhone(String phone)
/* 110:    */  {
/* 111:111 */    this.phone = phone;
/* 112:    */  }
/* 113:    */  
/* 114:    */  public String getEmail()
/* 115:    */  {
/* 116:116 */    return this.email;
/* 117:    */  }
/* 118:    */  
/* 119:    */  public void setEmail(String email)
/* 120:    */  {
/* 121:121 */    this.email = email;
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
/* 134:    */  public String getDepartment()
/* 135:    */  {
/* 136:136 */    return this.department;
/* 137:    */  }
/* 138:    */  
/* 139:    */  public void setDepartment(String department)
/* 140:    */  {
/* 141:141 */    this.department = department;
/* 142:    */  }
/* 143:    */  
/* 144:    */  public String getPosi()
/* 145:    */  {
/* 146:146 */    return this.posi;
/* 147:    */  }
/* 148:    */  
/* 149:    */  public void setPosi(String posi)
/* 150:    */  {
/* 151:151 */    this.posi = posi;
/* 152:    */  }
/* 153:    */  
/* 154:    */  public Date getBirthday()
/* 155:    */  {
/* 156:156 */    return this.birthday;
/* 157:    */  }
/* 158:    */  
/* 159:    */  public void setBirthday(Date birthday)
/* 160:    */  {
/* 161:161 */    this.birthday = birthday;
/* 162:    */  }
/* 163:    */  
/* 164:    */  public String getGraduateSchool()
/* 165:    */  {
/* 166:166 */    return this.graduateSchool;
/* 167:    */  }
/* 168:    */  
/* 169:    */  public void setGraduateSchool(String graduateSchool)
/* 170:    */  {
/* 171:171 */    this.graduateSchool = graduateSchool;
/* 172:    */  }
/* 173:    */  
/* 174:    */  public String getEducationalBackground()
/* 175:    */  {
/* 176:176 */    return this.educationalBackground;
/* 177:    */  }
/* 178:    */  
/* 179:    */  public void setEducationalBackground(String educationalBackground)
/* 180:    */  {
/* 181:181 */    this.educationalBackground = educationalBackground;
/* 182:    */  }
/* 183:    */  
/* 184:    */  public Date getEntryDate()
/* 185:    */  {
/* 186:186 */    return this.entryDate;
/* 187:    */  }
/* 188:    */  
/* 189:    */  public void setEntryDate(Date entryDate)
/* 190:    */  {
/* 191:191 */    this.entryDate = entryDate;
/* 192:    */  }
/* 193:    */  
/* 194:    */  public boolean equals(Object obj)
/* 195:    */  {
/* 196:196 */    if (obj == null)
/* 197:197 */      return false;
/* 198:198 */    if (!(obj instanceof AddressBook))
/* 199:    */    {
/* 200:200 */      return false;
/* 201:    */    }
/* 202:202 */    AddressBook addressBook = (AddressBook)obj;
/* 203:203 */    if ((getId() == null) || (addressBook.getId() == null))
/* 204:204 */      return false;
/* 205:205 */    return getId().equals(addressBook.getId());
/* 206:    */  }
/* 207:    */  
/* 208:    */  public int hashCode()
/* 209:    */  {
/* 210:210 */    if (-2147483648 == this.hashCode)
/* 211:    */    {
/* 212:212 */      if (getId() == null)
/* 213:    */      {
/* 214:214 */        return super.hashCode();
/* 215:    */      }
/* 216:216 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 217:217 */      this.hashCode = hashStr.hashCode();
/* 218:    */    }
/* 219:    */    
/* 220:220 */    return this.hashCode;
/* 221:    */  }
/* 222:    */  
/* 223:    */  public String toString()
/* 224:    */  {
/* 225:225 */    return super.toString();
/* 226:    */  }
/* 227:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseAddressBook
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */