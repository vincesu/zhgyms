/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.OperationLog;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseOperationLog
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "OperationLog";
/*  11: 11 */  public static String PROP_RESERVED1 = "Reserved1";
/*  12: 12 */  public static String PROP_RESERVED3 = "Reserved3";
/*  13: 13 */  public static String PROP_OPERATION = "Operation";
/*  14: 14 */  public static String PROP_RESERVED2 = "Reserved2";
/*  15: 15 */  public static String PROP_OPERATOR = "Operator";
/*  16: 16 */  public static String PROP_OPERATIONID = "Operationid";
/*  17: 17 */  public static String PROP_ID = "Id";
/*  18: 18 */  public static String PROP_OPERATIONDATE = "Operationdate";
/*  19:    */  
/*  20:    */  public BaseOperationLog()
/*  21:    */  {
/*  22: 22 */    initialize();
/*  23:    */  }
/*  24:    */  
/*  25:    */  public BaseOperationLog(Long id)
/*  26:    */  {
/*  27: 27 */    setId(id);
/*  28: 28 */    initialize();
/*  29:    */  }
/*  30:    */  
/*  31: 31 */  private int hashCode = -2147483648;
/*  32:    */  
/*  33:    */  private Long id;
/*  34:    */  
/*  35:    */  private String operation;
/*  36:    */  
/*  37:    */  private Date operationdate;
/*  38:    */  
/*  39:    */  private String operator;
/*  40:    */  
/*  41:    */  private Long operationid;
/*  42:    */  
/*  43:    */  private String reserved1;
/*  44:    */  
/*  45:    */  private String reserved2;
/*  46:    */  
/*  47:    */  private String reserved3;
/*  48:    */  
/*  49:    */  protected void initialize() {}
/*  50:    */  
/*  51:    */  public Long getId()
/*  52:    */  {
/*  53: 53 */    return this.id;
/*  54:    */  }
/*  55:    */  
/*  56:    */  public void setId(Long id)
/*  57:    */  {
/*  58: 58 */    this.id = id;
/*  59: 59 */    this.hashCode = -2147483648;
/*  60:    */  }
/*  61:    */  
/*  62:    */  public String getOperation()
/*  63:    */  {
/*  64: 64 */    return this.operation;
/*  65:    */  }
/*  66:    */  
/*  67:    */  public void setOperation(String operation)
/*  68:    */  {
/*  69: 69 */    this.operation = operation;
/*  70:    */  }
/*  71:    */  
/*  72:    */  public Date getOperationdate()
/*  73:    */  {
/*  74: 74 */    return this.operationdate;
/*  75:    */  }
/*  76:    */  
/*  77:    */  public void setOperationdate(Date operationdate)
/*  78:    */  {
/*  79: 79 */    this.operationdate = operationdate;
/*  80:    */  }
/*  81:    */  
/*  82:    */  public String getOperator()
/*  83:    */  {
/*  84: 84 */    return this.operator;
/*  85:    */  }
/*  86:    */  
/*  87:    */  public void setOperator(String operator)
/*  88:    */  {
/*  89: 89 */    this.operator = operator;
/*  90:    */  }
/*  91:    */  
/*  92:    */  public Long getOperationid()
/*  93:    */  {
/*  94: 94 */    return this.operationid;
/*  95:    */  }
/*  96:    */  
/*  97:    */  public void setOperationid(Long operationid)
/*  98:    */  {
/*  99: 99 */    this.operationid = operationid;
/* 100:    */  }
/* 101:    */  
/* 102:    */  public String getReserved1()
/* 103:    */  {
/* 104:104 */    return this.reserved1;
/* 105:    */  }
/* 106:    */  
/* 107:    */  public void setReserved1(String reserved1)
/* 108:    */  {
/* 109:109 */    this.reserved1 = reserved1;
/* 110:    */  }
/* 111:    */  
/* 112:    */  public String getReserved2()
/* 113:    */  {
/* 114:114 */    return this.reserved2;
/* 115:    */  }
/* 116:    */  
/* 117:    */  public void setReserved2(String reserved2)
/* 118:    */  {
/* 119:119 */    this.reserved2 = reserved2;
/* 120:    */  }
/* 121:    */  
/* 122:    */  public String getReserved3()
/* 123:    */  {
/* 124:124 */    return this.reserved3;
/* 125:    */  }
/* 126:    */  
/* 127:    */  public void setReserved3(String reserved3)
/* 128:    */  {
/* 129:129 */    this.reserved3 = reserved3;
/* 130:    */  }
/* 131:    */  
/* 132:    */  public boolean equals(Object obj)
/* 133:    */  {
/* 134:134 */    if (obj == null) return false;
/* 135:135 */    if (!(obj instanceof OperationLog)) { return false;
/* 136:    */    }
/* 137:137 */    OperationLog operationLog = (OperationLog)obj;
/* 138:138 */    if ((getId() == null) || (operationLog.getId() == null)) return false;
/* 139:139 */    return getId().equals(operationLog.getId());
/* 140:    */  }
/* 141:    */  
/* 142:    */  public int hashCode()
/* 143:    */  {
/* 144:144 */    if (-2147483648 == this.hashCode) {
/* 145:145 */      if (getId() == null) { return super.hashCode();
/* 146:    */      }
/* 147:147 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 148:148 */      this.hashCode = hashStr.hashCode();
/* 149:    */    }
/* 150:    */    
/* 151:151 */    return this.hashCode;
/* 152:    */  }
/* 153:    */  
/* 154:    */  public String toString()
/* 155:    */  {
/* 156:156 */    return super.toString();
/* 157:    */  }
/* 158:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseOperationLog
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */