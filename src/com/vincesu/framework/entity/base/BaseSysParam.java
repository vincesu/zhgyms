/*   1:    */package com.vincesu.framework.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.framework.entity.SysParam;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseSysParam
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "SysParam";
/*  10: 10 */  public static String PROP_AVAILABLE = "Available";
/*  11: 11 */  public static String PROP_DESCRIPTION = "Description";
/*  12: 12 */  public static String PROP_VALUE = "Value";
/*  13: 13 */  public static String PROP_ID = "Id";
/*  14: 14 */  public static String PROP_CODE = "Code";
/*  15:    */  
/*  16:    */  public BaseSysParam()
/*  17:    */  {
/*  18: 18 */    initialize();
/*  19:    */  }
/*  20:    */  
/*  21:    */  public BaseSysParam(Long id)
/*  22:    */  {
/*  23: 23 */    setId(id);
/*  24: 24 */    initialize();
/*  25:    */  }
/*  26:    */  
/*  27: 27 */  private int hashCode = -2147483648;
/*  28:    */  
/*  29:    */  private Long id;
/*  30:    */  
/*  31:    */  private String code;
/*  32:    */  
/*  33:    */  private String value;
/*  34:    */  
/*  35:    */  private Integer available;
/*  36:    */  
/*  37:    */  private String description;
/*  38:    */  
/*  39:    */  protected void initialize() {}
/*  40:    */  
/*  41:    */  public Long getId()
/*  42:    */  {
/*  43: 43 */    return this.id;
/*  44:    */  }
/*  45:    */  
/*  46:    */  public void setId(Long id)
/*  47:    */  {
/*  48: 48 */    this.id = id;
/*  49: 49 */    this.hashCode = -2147483648;
/*  50:    */  }
/*  51:    */  
/*  52:    */  public String getCode()
/*  53:    */  {
/*  54: 54 */    return this.code;
/*  55:    */  }
/*  56:    */  
/*  57:    */  public void setCode(String code)
/*  58:    */  {
/*  59: 59 */    this.code = code;
/*  60:    */  }
/*  61:    */  
/*  62:    */  public String getValue()
/*  63:    */  {
/*  64: 64 */    return this.value;
/*  65:    */  }
/*  66:    */  
/*  67:    */  public void setValue(String value)
/*  68:    */  {
/*  69: 69 */    this.value = value;
/*  70:    */  }
/*  71:    */  
/*  72:    */  public Integer getAvailable()
/*  73:    */  {
/*  74: 74 */    return this.available;
/*  75:    */  }
/*  76:    */  
/*  77:    */  public void setAvailable(Integer available)
/*  78:    */  {
/*  79: 79 */    this.available = available;
/*  80:    */  }
/*  81:    */  
/*  82:    */  public String getDescription()
/*  83:    */  {
/*  84: 84 */    return this.description;
/*  85:    */  }
/*  86:    */  
/*  87:    */  public void setDescription(String description)
/*  88:    */  {
/*  89: 89 */    this.description = description;
/*  90:    */  }
/*  91:    */  
/*  92:    */  public boolean equals(Object obj)
/*  93:    */  {
/*  94: 94 */    if (obj == null) return false;
/*  95: 95 */    if (!(obj instanceof SysParam)) { return false;
/*  96:    */    }
/*  97: 97 */    SysParam sysParam = (SysParam)obj;
/*  98: 98 */    if ((getId() == null) || (sysParam.getId() == null)) return false;
/*  99: 99 */    return getId().equals(sysParam.getId());
/* 100:    */  }
/* 101:    */  
/* 102:    */  public int hashCode()
/* 103:    */  {
/* 104:104 */    if (-2147483648 == this.hashCode) {
/* 105:105 */      if (getId() == null) { return super.hashCode();
/* 106:    */      }
/* 107:107 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 108:108 */      this.hashCode = hashStr.hashCode();
/* 109:    */    }
/* 110:    */    
/* 111:111 */    return this.hashCode;
/* 112:    */  }
/* 113:    */  
/* 114:    */  public String toString()
/* 115:    */  {
/* 116:116 */    return super.toString();
/* 117:    */  }
/* 118:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.entity.base.BaseSysParam
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */