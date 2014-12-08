/*   1:    */package com.vincesu.framework.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.framework.entity.SysUser;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseSysUser
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "SysUser";
/*  11: 11 */  public static String PROP_PHONE = "Phone";
/*  12: 12 */  public static String PROP_JOINED_TIME = "JoinedTime";
/*  13: 13 */  public static String PROP_CONTRACT_NOTE_CN = "ContractNoteCn";
/*  14: 14 */  public static String PROP_FAX = "Fax";
/*  15: 15 */  public static String PROP_PWD = "Pwd";
/*  16: 16 */  public static String PROP_RESERVED = "Reserved";
/*  17: 17 */  public static String PROP_REALNAME = "Realname";
/*  18: 18 */  public static String PROP_ROLE_ID = "RoleId";
/*  19: 19 */  public static String PROP_LAST_LOGIN_TIME = "LastLoginTime";
/*  20: 20 */  public static String PROP_EMAIL = "Email";
/*  21: 21 */  public static String PROP_USERNAME = "Username";
/*  22: 22 */  public static String PROP_PAYMENT_EN = "PaymentEn";
/*  23: 23 */  public static String PROP_PAYMENT_CN = "PaymentCn";
/*  24: 24 */  public static String PROP_ID = "Id";
/*  25: 25 */  public static String PROP_SHIPPINGTREM = "Shippingtrem";
/*  26: 26 */  public static String PROP_CONTRACT_NOTE = "ContractNote";
/*  27:    */  
/*  28:    */  public BaseSysUser()
/*  29:    */  {
/*  30: 30 */    initialize();
/*  31:    */  }
/*  32:    */  
/*  33:    */  public BaseSysUser(Long id)
/*  34:    */  {
/*  35: 35 */    setId(id);
/*  36: 36 */    initialize();
/*  37:    */  }
/*  38:    */  
/*  39:    */  public BaseSysUser(Long id, String username, String pwd, Long roleId, Date joinedTime)
/*  40:    */  {
/*  41: 41 */    setId(id);
/*  42: 42 */    setUsername(username);
/*  43: 43 */    setPwd(pwd);
/*  44: 44 */    setRoleId(roleId);
/*  45: 45 */    setJoinedTime(joinedTime);
/*  46: 46 */    initialize();
/*  47:    */  }
/*  48:    */  
/*  49: 49 */  private int hashCode = -2147483648;
/*  50:    */  
/*  51:    */  private Long id;
/*  52:    */  
/*  53:    */  private String username;
/*  54:    */  
/*  55:    */  private String pwd;
/*  56:    */  
/*  57:    */  private Long roleId;
/*  58:    */  
/*  59:    */  private String reserved;
/*  60:    */  
/*  61:    */  private Date joinedTime;
/*  62:    */  
/*  63:    */  private Date lastLoginTime;
/*  64:    */  
/*  65:    */  private String realname;
/*  66:    */  
/*  67:    */  private String email;
/*  68:    */  private String paymentEn;
/*  69:    */  private String paymentCn;
/*  70:    */  private String shippingtrem;
/*  71:    */  private String phone;
/*  72:    */  private String fax;
/*  73:    */  private String contractNote;
/*  74:    */  private String contractNoteCn;
/*  75:    */  
/*  76:    */  protected void initialize() {}
/*  77:    */  
/*  78:    */  public Long getId()
/*  79:    */  {
/*  80: 80 */    return this.id;
/*  81:    */  }
/*  82:    */  
/*  83:    */  public void setId(Long id)
/*  84:    */  {
/*  85: 85 */    this.id = id;
/*  86: 86 */    this.hashCode = -2147483648;
/*  87:    */  }
/*  88:    */  
/*  89:    */  public String getUsername()
/*  90:    */  {
/*  91: 91 */    return this.username;
/*  92:    */  }
/*  93:    */  
/*  94:    */  public void setUsername(String username)
/*  95:    */  {
/*  96: 96 */    this.username = username;
/*  97:    */  }
/*  98:    */  
/*  99:    */  public String getPwd()
/* 100:    */  {
/* 101:101 */    return this.pwd;
/* 102:    */  }
/* 103:    */  
/* 104:    */  public void setPwd(String pwd)
/* 105:    */  {
/* 106:106 */    this.pwd = pwd;
/* 107:    */  }
/* 108:    */  
/* 109:    */  public Long getRoleId()
/* 110:    */  {
/* 111:111 */    return this.roleId;
/* 112:    */  }
/* 113:    */  
/* 114:    */  public void setRoleId(Long roleId)
/* 115:    */  {
/* 116:116 */    this.roleId = roleId;
/* 117:    */  }
/* 118:    */  
/* 119:    */  public String getReserved()
/* 120:    */  {
/* 121:121 */    return this.reserved;
/* 122:    */  }
/* 123:    */  
/* 124:    */  public void setReserved(String reserved)
/* 125:    */  {
/* 126:126 */    this.reserved = reserved;
/* 127:    */  }
/* 128:    */  
/* 129:    */  public Date getJoinedTime()
/* 130:    */  {
/* 131:131 */    return this.joinedTime;
/* 132:    */  }
/* 133:    */  
/* 134:    */  public void setJoinedTime(Date joinedTime)
/* 135:    */  {
/* 136:136 */    this.joinedTime = joinedTime;
/* 137:    */  }
/* 138:    */  
/* 139:    */  public Date getLastLoginTime()
/* 140:    */  {
/* 141:141 */    return this.lastLoginTime;
/* 142:    */  }
/* 143:    */  
/* 144:    */  public void setLastLoginTime(Date lastLoginTime)
/* 145:    */  {
/* 146:146 */    this.lastLoginTime = lastLoginTime;
/* 147:    */  }
/* 148:    */  
/* 149:    */  public String getRealname()
/* 150:    */  {
/* 151:151 */    return this.realname;
/* 152:    */  }
/* 153:    */  
/* 154:    */  public void setRealname(String realname)
/* 155:    */  {
/* 156:156 */    this.realname = realname;
/* 157:    */  }
/* 158:    */  
/* 159:    */  public String getEmail()
/* 160:    */  {
/* 161:161 */    return this.email;
/* 162:    */  }
/* 163:    */  
/* 164:    */  public void setEmail(String email)
/* 165:    */  {
/* 166:166 */    this.email = email;
/* 167:    */  }
/* 168:    */  
/* 169:    */  public String getPaymentEn()
/* 170:    */  {
/* 171:171 */    return this.paymentEn;
/* 172:    */  }
/* 173:    */  
/* 174:    */  public void setPaymentEn(String paymentEn)
/* 175:    */  {
/* 176:176 */    this.paymentEn = paymentEn;
/* 177:    */  }
/* 178:    */  
/* 179:    */  public String getPaymentCn()
/* 180:    */  {
/* 181:181 */    return this.paymentCn;
/* 182:    */  }
/* 183:    */  
/* 184:    */  public void setPaymentCn(String paymentCn)
/* 185:    */  {
/* 186:186 */    this.paymentCn = paymentCn;
/* 187:    */  }
/* 188:    */  
/* 189:    */  public String getShippingtrem()
/* 190:    */  {
/* 191:191 */    return this.shippingtrem;
/* 192:    */  }
/* 193:    */  
/* 194:    */  public void setShippingtrem(String shippingtrem)
/* 195:    */  {
/* 196:196 */    this.shippingtrem = shippingtrem;
/* 197:    */  }
/* 198:    */  
/* 199:    */  public String getPhone()
/* 200:    */  {
/* 201:201 */    return this.phone;
/* 202:    */  }
/* 203:    */  
/* 204:    */  public void setPhone(String phone)
/* 205:    */  {
/* 206:206 */    this.phone = phone;
/* 207:    */  }
/* 208:    */  
/* 209:    */  public String getFax()
/* 210:    */  {
/* 211:211 */    return this.fax;
/* 212:    */  }
/* 213:    */  
/* 214:    */  public void setFax(String fax)
/* 215:    */  {
/* 216:216 */    this.fax = fax;
/* 217:    */  }
/* 218:    */  
/* 219:    */  public String getContractNote()
/* 220:    */  {
/* 221:221 */    return this.contractNote;
/* 222:    */  }
/* 223:    */  
/* 224:    */  public void setContractNote(String contractNote)
/* 225:    */  {
/* 226:226 */    this.contractNote = contractNote;
/* 227:    */  }
/* 228:    */  
/* 229:    */  public String getContractNoteCn()
/* 230:    */  {
/* 231:231 */    return this.contractNoteCn;
/* 232:    */  }
/* 233:    */  
/* 234:    */  public void setContractNoteCn(String contractNoteCn)
/* 235:    */  {
/* 236:236 */    this.contractNoteCn = contractNoteCn;
/* 237:    */  }
/* 238:    */  
/* 239:    */  public boolean equals(Object obj)
/* 240:    */  {
/* 241:241 */    if (obj == null) return false;
/* 242:242 */    if (!(obj instanceof SysUser)) { return false;
/* 243:    */    }
/* 244:244 */    SysUser sysUser = (SysUser)obj;
/* 245:245 */    if ((getId() == null) || (sysUser.getId() == null)) return false;
/* 246:246 */    return getId().equals(sysUser.getId());
/* 247:    */  }
/* 248:    */  
/* 249:    */  public int hashCode()
/* 250:    */  {
/* 251:251 */    if (-2147483648 == this.hashCode) {
/* 252:252 */      if (getId() == null) { return super.hashCode();
/* 253:    */      }
/* 254:254 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 255:255 */      this.hashCode = hashStr.hashCode();
/* 256:    */    }
/* 257:    */    
/* 258:258 */    return this.hashCode;
/* 259:    */  }
/* 260:    */  
/* 261:    */  public String toString()
/* 262:    */  {
/* 263:263 */    return super.toString();
/* 264:    */  }
/* 265:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.entity.base.BaseSysUser
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */