/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.ExpressDelivery;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseExpressDelivery
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "ExpressDelivery";
/*  10: 10 */  public static String PROP_PHONE = "Phone";
/*  11: 11 */  public static String PROP_CONTACT = "Contact";
/*  12: 12 */  public static String PROP_ID = "Id";
/*  13: 13 */  public static String PROP_COMPANY = "Company";
/*  14:    */  
/*  15:    */  public BaseExpressDelivery()
/*  16:    */  {
/*  17: 17 */    initialize();
/*  18:    */  }
/*  19:    */  
/*  20:    */  public BaseExpressDelivery(Long id)
/*  21:    */  {
/*  22: 22 */    setId(id);
/*  23: 23 */    initialize();
/*  24:    */  }
/*  25:    */  
/*  26: 26 */  private int hashCode = -2147483648;
/*  27:    */  
/*  28:    */  private Long id;
/*  29:    */  
/*  30:    */  private String company;
/*  31:    */  
/*  32:    */  private String contact;
/*  33:    */  
/*  34:    */  private String phone;
/*  35:    */  
/*  36:    */  protected void initialize() {}
/*  37:    */  
/*  38:    */  public Long getId()
/*  39:    */  {
/*  40: 40 */    return this.id;
/*  41:    */  }
/*  42:    */  
/*  43:    */  public void setId(Long id)
/*  44:    */  {
/*  45: 45 */    this.id = id;
/*  46: 46 */    this.hashCode = -2147483648;
/*  47:    */  }
/*  48:    */  
/*  49:    */  public String getCompany()
/*  50:    */  {
/*  51: 51 */    return this.company;
/*  52:    */  }
/*  53:    */  
/*  54:    */  public void setCompany(String company)
/*  55:    */  {
/*  56: 56 */    this.company = company;
/*  57:    */  }
/*  58:    */  
/*  59:    */  public String getContact()
/*  60:    */  {
/*  61: 61 */    return this.contact;
/*  62:    */  }
/*  63:    */  
/*  64:    */  public void setContact(String contact)
/*  65:    */  {
/*  66: 66 */    this.contact = contact;
/*  67:    */  }
/*  68:    */  
/*  69:    */  public String getPhone()
/*  70:    */  {
/*  71: 71 */    return this.phone;
/*  72:    */  }
/*  73:    */  
/*  74:    */  public void setPhone(String phone)
/*  75:    */  {
/*  76: 76 */    this.phone = phone;
/*  77:    */  }
/*  78:    */  
/*  79:    */  public boolean equals(Object obj)
/*  80:    */  {
/*  81: 81 */    if (obj == null) return false;
/*  82: 82 */    if (!(obj instanceof ExpressDelivery)) { return false;
/*  83:    */    }
/*  84: 84 */    ExpressDelivery expressDelivery = (ExpressDelivery)obj;
/*  85: 85 */    if ((getId() == null) || (expressDelivery.getId() == null)) return false;
/*  86: 86 */    return getId().equals(expressDelivery.getId());
/*  87:    */  }
/*  88:    */  
/*  89:    */  public int hashCode()
/*  90:    */  {
/*  91: 91 */    if (-2147483648 == this.hashCode) {
/*  92: 92 */      if (getId() == null) { return super.hashCode();
/*  93:    */      }
/*  94: 94 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/*  95: 95 */      this.hashCode = hashStr.hashCode();
/*  96:    */    }
/*  97:    */    
/*  98: 98 */    return this.hashCode;
/*  99:    */  }
/* 100:    */  
/* 101:    */  public String toString()
/* 102:    */  {
/* 103:103 */    return super.toString();
/* 104:    */  }
/* 105:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseExpressDelivery
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */