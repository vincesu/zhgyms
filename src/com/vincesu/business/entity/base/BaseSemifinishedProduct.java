/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.SemifinishedProduct;
/*   4:    */import java.io.Serializable;
/*   5:    */
/*   6:    */public abstract class BaseSemifinishedProduct
/*   7:    */  implements Serializable
/*   8:    */{
/*   9:  9 */  public static String REF = "SemifinishedProduct";
/*  10: 10 */  public static String PROP_NAME = "Name";
/*  11: 11 */  public static String PROP_RESERVED = "Reserved";
/*  12: 12 */  public static String PROP_ID = "Id";
/*  13: 13 */  public static String PROP_SIZE = "Size";
/*  14:    */  
/*  15:    */  public BaseSemifinishedProduct()
/*  16:    */  {
/*  17: 17 */    initialize();
/*  18:    */  }
/*  19:    */  
/*  20:    */  public BaseSemifinishedProduct(Long id)
/*  21:    */  {
/*  22: 22 */    setId(id);
/*  23: 23 */    initialize();
/*  24:    */  }
/*  25:    */  
/*  26: 26 */  private int hashCode = -2147483648;
/*  27:    */  
/*  28:    */  private Long id;
/*  29:    */  
/*  30:    */  private String name;
/*  31:    */  
/*  32:    */  private String size;
/*  33:    */  
/*  34:    */  private String reserved;
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
/*  49:    */  public String getName()
/*  50:    */  {
/*  51: 51 */    return this.name;
/*  52:    */  }
/*  53:    */  
/*  54:    */  public void setName(String name)
/*  55:    */  {
/*  56: 56 */    this.name = name;
/*  57:    */  }
/*  58:    */  
/*  59:    */  public String getSize()
/*  60:    */  {
/*  61: 61 */    return this.size;
/*  62:    */  }
/*  63:    */  
/*  64:    */  public void setSize(String size)
/*  65:    */  {
/*  66: 66 */    this.size = size;
/*  67:    */  }
/*  68:    */  
/*  69:    */  public String getReserved()
/*  70:    */  {
/*  71: 71 */    return this.reserved;
/*  72:    */  }
/*  73:    */  
/*  74:    */  public void setReserved(String reserved)
/*  75:    */  {
/*  76: 76 */    this.reserved = reserved;
/*  77:    */  }
/*  78:    */  
/*  79:    */  public boolean equals(Object obj)
/*  80:    */  {
/*  81: 81 */    if (obj == null) return false;
/*  82: 82 */    if (!(obj instanceof SemifinishedProduct)) { return false;
/*  83:    */    }
/*  84: 84 */    SemifinishedProduct semifinishedProduct = (SemifinishedProduct)obj;
/*  85: 85 */    if ((getId() == null) || (semifinishedProduct.getId() == null)) return false;
/*  86: 86 */    return getId().equals(semifinishedProduct.getId());
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
 * Qualified Name:     com.vincesu.business.entity.base.BaseSemifinishedProduct
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */