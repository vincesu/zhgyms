/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.SalesProgram;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseSalesProgram
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "SalesProgram";
/*  11: 11 */  public static String PROP_PRODUCTID = "Productid";
/*  12: 12 */  public static String PROP_QUANTITY = "Quantity";
/*  13: 13 */  public static String PROP_DATE = "Date";
/*  14: 14 */  public static String PROP_PRICE = "Price";
/*  15: 15 */  public static String PROP_RESERVED = "Reserved";
/*  16: 16 */  public static String PROP_ID = "Id";
/*  17: 17 */  public static String PROP_SALESMAN = "Salesman";
/*  18: 18 */  public static String PROP_PERMISSIONS = "Permissions";
/*  19:    */  
/*  20:    */  public BaseSalesProgram()
/*  21:    */  {
/*  22: 22 */    initialize();
/*  23:    */  }
/*  24:    */  
/*  25:    */  public BaseSalesProgram(Long id)
/*  26:    */  {
/*  27: 27 */    setId(id);
/*  28: 28 */    initialize();
/*  29:    */  }
/*  30:    */  
/*  31: 31 */  private int hashCode = -2147483648;
/*  32:    */  
/*  33:    */  private Long id;
/*  34:    */  
/*  35:    */  private Date date;
/*  36:    */  
/*  37:    */  private String permissions;
/*  38:    */  
/*  39:    */  private String price;
/*  40:    */  
/*  41:    */  private Long productid;
/*  42:    */  
/*  43:    */  private String quantity;
/*  44:    */  
/*  45:    */  private String reserved;
/*  46:    */  
/*  47:    */  private String salesman;
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
/*  62:    */  public Date getDate()
/*  63:    */  {
/*  64: 64 */    return this.date;
/*  65:    */  }
/*  66:    */  
/*  67:    */  public void setDate(Date date)
/*  68:    */  {
/*  69: 69 */    this.date = date;
/*  70:    */  }
/*  71:    */  
/*  72:    */  public String getPermissions()
/*  73:    */  {
/*  74: 74 */    return this.permissions;
/*  75:    */  }
/*  76:    */  
/*  77:    */  public void setPermissions(String permissions)
/*  78:    */  {
/*  79: 79 */    this.permissions = permissions;
/*  80:    */  }
/*  81:    */  
/*  82:    */  public String getPrice()
/*  83:    */  {
/*  84: 84 */    return this.price;
/*  85:    */  }
/*  86:    */  
/*  87:    */  public void setPrice(String price)
/*  88:    */  {
/*  89: 89 */    this.price = price;
/*  90:    */  }
/*  91:    */  
/*  92:    */  public Long getProductid()
/*  93:    */  {
/*  94: 94 */    return this.productid;
/*  95:    */  }
/*  96:    */  
/*  97:    */  public void setProductid(Long productid)
/*  98:    */  {
/*  99: 99 */    this.productid = productid;
/* 100:    */  }
/* 101:    */  
/* 102:    */  public String getQuantity()
/* 103:    */  {
/* 104:104 */    return this.quantity;
/* 105:    */  }
/* 106:    */  
/* 107:    */  public void setQuantity(String quantity)
/* 108:    */  {
/* 109:109 */    this.quantity = quantity;
/* 110:    */  }
/* 111:    */  
/* 112:    */  public String getReserved()
/* 113:    */  {
/* 114:114 */    return this.reserved;
/* 115:    */  }
/* 116:    */  
/* 117:    */  public void setReserved(String reserved)
/* 118:    */  {
/* 119:119 */    this.reserved = reserved;
/* 120:    */  }
/* 121:    */  
/* 122:    */  public String getSalesman()
/* 123:    */  {
/* 124:124 */    return this.salesman;
/* 125:    */  }
/* 126:    */  
/* 127:    */  public void setSalesman(String salesman)
/* 128:    */  {
/* 129:129 */    this.salesman = salesman;
/* 130:    */  }
/* 131:    */  
/* 132:    */  public boolean equals(Object obj)
/* 133:    */  {
/* 134:134 */    if (obj == null) return false;
/* 135:135 */    if (!(obj instanceof SalesProgram)) { return false;
/* 136:    */    }
/* 137:137 */    SalesProgram salesProgram = (SalesProgram)obj;
/* 138:138 */    if ((getId() == null) || (salesProgram.getId() == null)) return false;
/* 139:139 */    return getId().equals(salesProgram.getId());
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
 * Qualified Name:     com.vincesu.business.entity.base.BaseSalesProgram
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */