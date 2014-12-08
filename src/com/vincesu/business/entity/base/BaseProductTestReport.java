/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.ProductTestReport;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseProductTestReport
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "ProductTestReport";
/*  11: 11 */  public static String PROP_NAME = "Name";
/*  12: 12 */  public static String PROP_DATE = "Date";
/*  13: 13 */  public static String PROP_OPERATOR = "Operator";
/*  14: 14 */  public static String PROP_ID = "Id";
/*  15:    */  
/*  16:    */  public BaseProductTestReport()
/*  17:    */  {
/*  18: 18 */    initialize();
/*  19:    */  }
/*  20:    */  
/*  21:    */  public BaseProductTestReport(Long id)
/*  22:    */  {
/*  23: 23 */    setId(id);
/*  24: 24 */    initialize();
/*  25:    */  }
/*  26:    */  
/*  27: 27 */  private int hashCode = -2147483648;
/*  28:    */  
/*  29:    */  private Long id;
/*  30:    */  
/*  31:    */  private String name;
/*  32:    */  
/*  33:    */  private Date date;
/*  34:    */  
/*  35:    */  private String operator;
/*  36:    */  
/*  37:    */  protected void initialize() {}
/*  38:    */  
/*  39:    */  public Long getId()
/*  40:    */  {
/*  41: 41 */    return this.id;
/*  42:    */  }
/*  43:    */  
/*  44:    */  public void setId(Long id)
/*  45:    */  {
/*  46: 46 */    this.id = id;
/*  47: 47 */    this.hashCode = -2147483648;
/*  48:    */  }
/*  49:    */  
/*  50:    */  public String getName()
/*  51:    */  {
/*  52: 52 */    return this.name;
/*  53:    */  }
/*  54:    */  
/*  55:    */  public void setName(String name)
/*  56:    */  {
/*  57: 57 */    this.name = name;
/*  58:    */  }
/*  59:    */  
/*  60:    */  public Date getDate()
/*  61:    */  {
/*  62: 62 */    return this.date;
/*  63:    */  }
/*  64:    */  
/*  65:    */  public void setDate(Date date)
/*  66:    */  {
/*  67: 67 */    this.date = date;
/*  68:    */  }
/*  69:    */  
/*  70:    */  public String getOperator()
/*  71:    */  {
/*  72: 72 */    return this.operator;
/*  73:    */  }
/*  74:    */  
/*  75:    */  public void setOperator(String operator)
/*  76:    */  {
/*  77: 77 */    this.operator = operator;
/*  78:    */  }
/*  79:    */  
/*  80:    */  public boolean equals(Object obj)
/*  81:    */  {
/*  82: 82 */    if (obj == null) return false;
/*  83: 83 */    if (!(obj instanceof ProductTestReport)) { return false;
/*  84:    */    }
/*  85: 85 */    ProductTestReport productTestReport = (ProductTestReport)obj;
/*  86: 86 */    if ((getId() == null) || (productTestReport.getId() == null)) return false;
/*  87: 87 */    return getId().equals(productTestReport.getId());
/*  88:    */  }
/*  89:    */  
/*  90:    */  public int hashCode()
/*  91:    */  {
/*  92: 92 */    if (-2147483648 == this.hashCode) {
/*  93: 93 */      if (getId() == null) { return super.hashCode();
/*  94:    */      }
/*  95: 95 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/*  96: 96 */      this.hashCode = hashStr.hashCode();
/*  97:    */    }
/*  98:    */    
/*  99: 99 */    return this.hashCode;
/* 100:    */  }
/* 101:    */  
/* 102:    */  public String toString()
/* 103:    */  {
/* 104:104 */    return super.toString();
/* 105:    */  }
/* 106:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseProductTestReport
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */