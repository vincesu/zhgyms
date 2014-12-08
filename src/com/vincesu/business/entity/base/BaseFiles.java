/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Files;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseFiles
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "Files";
/*  11: 11 */  public static String PROP_DESCRIPTION = "Description";
/*  12: 12 */  public static String PROP_TYPE = "Type";
/*  13: 13 */  public static String PROP_OPERATOR = "Operator";
/*  14: 14 */  public static String PROP_RESERVED = "Reserved";
/*  15: 15 */  public static String PROP_IS_PRIMARY = "IsPrimary";
/*  16: 16 */  public static String PROP_PATH = "Path";
/*  17: 17 */  public static String PROP_PNGPATH = "Pngpath";
/*  18: 18 */  public static String PROP_NAME = "Name";
/*  19: 19 */  public static String PROP_DATE = "Date";
/*  20: 20 */  public static String PROP_WEBPATH = "Webpath";
/*  21: 21 */  public static String PROP_RELATED_OBJECT = "RelatedObject";
/*  22: 22 */  public static String PROP_ID = "Id";
/*  23: 23 */  public static String PROP_PDFPATH = "Pdfpath";
/*  24: 24 */  public static String PROP_RELATED_DESCRIPTION = "RelatedDescription";
/*  25:    */  
/*  26:    */  public BaseFiles()
/*  27:    */  {
/*  28: 28 */    initialize();
/*  29:    */  }
/*  30:    */  
/*  31:    */  public BaseFiles(Long id)
/*  32:    */  {
/*  33: 33 */    setId(id);
/*  34: 34 */    initialize();
/*  35:    */  }
/*  36:    */  
/*  37: 37 */  private int hashCode = -2147483648;
/*  38:    */  
/*  39:    */  private Long id;
/*  40:    */  
/*  41:    */  private String name;
/*  42:    */  
/*  43:    */  private String description;
/*  44:    */  
/*  45:    */  private String path;
/*  46:    */  
/*  47:    */  private String pdfpath;
/*  48:    */  
/*  49:    */  private Integer type;
/*  50:    */  
/*  51:    */  private String relatedDescription;
/*  52:    */  
/*  53:    */  private Long relatedObject;
/*  54:    */  
/*  55:    */  private Date date;
/*  56:    */  private String operator;
/*  57:    */  private Integer isPrimary;
/*  58:    */  private String reserved;
/*  59:    */  private String webpath;
/*  60:    */  private String pngpath;
/*  61:    */  
/*  62:    */  protected void initialize() {}
/*  63:    */  
/*  64:    */  public Long getId()
/*  65:    */  {
/*  66: 66 */    return this.id;
/*  67:    */  }
/*  68:    */  
/*  69:    */  public void setId(Long id)
/*  70:    */  {
/*  71: 71 */    this.id = id;
/*  72: 72 */    this.hashCode = -2147483648;
/*  73:    */  }
/*  74:    */  
/*  75:    */  public String getName()
/*  76:    */  {
/*  77: 77 */    return this.name;
/*  78:    */  }
/*  79:    */  
/*  80:    */  public void setName(String name)
/*  81:    */  {
/*  82: 82 */    this.name = name;
/*  83:    */  }
/*  84:    */  
/*  85:    */  public String getDescription()
/*  86:    */  {
/*  87: 87 */    return this.description;
/*  88:    */  }
/*  89:    */  
/*  90:    */  public void setDescription(String description)
/*  91:    */  {
/*  92: 92 */    this.description = description;
/*  93:    */  }
/*  94:    */  
/*  95:    */  public String getPath()
/*  96:    */  {
/*  97: 97 */    return this.path;
/*  98:    */  }
/*  99:    */  
/* 100:    */  public void setPath(String path)
/* 101:    */  {
/* 102:102 */    this.path = path;
/* 103:    */  }
/* 104:    */  
/* 105:    */  public String getPdfpath()
/* 106:    */  {
/* 107:107 */    return this.pdfpath;
/* 108:    */  }
/* 109:    */  
/* 110:    */  public void setPdfpath(String pdfpath)
/* 111:    */  {
/* 112:112 */    this.pdfpath = pdfpath;
/* 113:    */  }
/* 114:    */  
/* 115:    */  public Integer getType()
/* 116:    */  {
/* 117:117 */    return this.type;
/* 118:    */  }
/* 119:    */  
/* 120:    */  public void setType(Integer type)
/* 121:    */  {
/* 122:122 */    this.type = type;
/* 123:    */  }
/* 124:    */  
/* 125:    */  public String getRelatedDescription()
/* 126:    */  {
/* 127:127 */    return this.relatedDescription;
/* 128:    */  }
/* 129:    */  
/* 130:    */  public void setRelatedDescription(String relatedDescription)
/* 131:    */  {
/* 132:132 */    this.relatedDescription = relatedDescription;
/* 133:    */  }
/* 134:    */  
/* 135:    */  public Long getRelatedObject()
/* 136:    */  {
/* 137:137 */    return this.relatedObject;
/* 138:    */  }
/* 139:    */  
/* 140:    */  public void setRelatedObject(Long relatedObject)
/* 141:    */  {
/* 142:142 */    this.relatedObject = relatedObject;
/* 143:    */  }
/* 144:    */  
/* 145:    */  public Date getDate()
/* 146:    */  {
/* 147:147 */    return this.date;
/* 148:    */  }
/* 149:    */  
/* 150:    */  public void setDate(Date date)
/* 151:    */  {
/* 152:152 */    this.date = date;
/* 153:    */  }
/* 154:    */  
/* 155:    */  public String getOperator()
/* 156:    */  {
/* 157:157 */    return this.operator;
/* 158:    */  }
/* 159:    */  
/* 160:    */  public void setOperator(String operator)
/* 161:    */  {
/* 162:162 */    this.operator = operator;
/* 163:    */  }
/* 164:    */  
/* 165:    */  public Integer getIsPrimary()
/* 166:    */  {
/* 167:167 */    return this.isPrimary;
/* 168:    */  }
/* 169:    */  
/* 170:    */  public void setIsPrimary(Integer isPrimary)
/* 171:    */  {
/* 172:172 */    this.isPrimary = isPrimary;
/* 173:    */  }
/* 174:    */  
/* 175:    */  public String getReserved()
/* 176:    */  {
/* 177:177 */    return this.reserved;
/* 178:    */  }
/* 179:    */  
/* 180:    */  public void setReserved(String reserved)
/* 181:    */  {
/* 182:182 */    this.reserved = reserved;
/* 183:    */  }
/* 184:    */  
/* 185:    */  public String getWebpath()
/* 186:    */  {
/* 187:187 */    return this.webpath;
/* 188:    */  }
/* 189:    */  
/* 190:    */  public void setWebpath(String webpath)
/* 191:    */  {
/* 192:192 */    this.webpath = webpath;
/* 193:    */  }
/* 194:    */  
/* 195:    */  public String getPngpath()
/* 196:    */  {
/* 197:197 */    return this.pngpath;
/* 198:    */  }
/* 199:    */  
/* 200:    */  public void setPngpath(String pngpath)
/* 201:    */  {
/* 202:202 */    this.pngpath = pngpath;
/* 203:    */  }
/* 204:    */  
/* 205:    */  public boolean equals(Object obj)
/* 206:    */  {
/* 207:207 */    if (obj == null) return false;
/* 208:208 */    if (!(obj instanceof Files)) { return false;
/* 209:    */    }
/* 210:210 */    Files files = (Files)obj;
/* 211:211 */    if ((getId() == null) || (files.getId() == null)) return false;
/* 212:212 */    return getId().equals(files.getId());
/* 213:    */  }
/* 214:    */  
/* 215:    */  public int hashCode()
/* 216:    */  {
/* 217:217 */    if (-2147483648 == this.hashCode) {
/* 218:218 */      if (getId() == null) { return super.hashCode();
/* 219:    */      }
/* 220:220 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 221:221 */      this.hashCode = hashStr.hashCode();
/* 222:    */    }
/* 223:    */    
/* 224:224 */    return this.hashCode;
/* 225:    */  }
/* 226:    */  
/* 227:    */  public String toString()
/* 228:    */  {
/* 229:229 */    return super.toString();
/* 230:    */  }
/* 231:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseFiles
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */