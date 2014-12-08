/*  1:   */package com.vincesu.business.entity.base;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.PrroductDirectory;
/*  4:   */import java.io.Serializable;
/*  5:   */
/*  6:   */public abstract class BasePrroductDirectory
/*  7:   */  implements Serializable
/*  8:   */{
/*  9: 9 */  public static String REF = "PrroductDirectory";
/* 10:10 */  public static String PROP_NAME = "Name";
/* 11:11 */  public static String PROP_NUMBER = "Number";
/* 12:12 */  public static String PROP_ID = "Id";
/* 13:   */  
/* 14:   */  public BasePrroductDirectory()
/* 15:   */  {
/* 16:16 */    initialize();
/* 17:   */  }
/* 18:   */  
/* 19:   */  public BasePrroductDirectory(Long id)
/* 20:   */  {
/* 21:21 */    setId(id);
/* 22:22 */    initialize();
/* 23:   */  }
/* 24:   */  
/* 25:25 */  private int hashCode = -2147483648;
/* 26:   */  
/* 27:   */  private Long id;
/* 28:   */  
/* 29:   */  private String name;
/* 30:   */  
/* 31:   */  private String number;
/* 32:   */  
/* 33:   */  protected void initialize() {}
/* 34:   */  
/* 35:   */  public Long getId()
/* 36:   */  {
/* 37:37 */    return this.id;
/* 38:   */  }
/* 39:   */  
/* 40:   */  public void setId(Long id)
/* 41:   */  {
/* 42:42 */    this.id = id;
/* 43:43 */    this.hashCode = -2147483648;
/* 44:   */  }
/* 45:   */  
/* 46:   */  public String getName()
/* 47:   */  {
/* 48:48 */    return this.name;
/* 49:   */  }
/* 50:   */  
/* 51:   */  public void setName(String name)
/* 52:   */  {
/* 53:53 */    this.name = name;
/* 54:   */  }
/* 55:   */  
/* 56:   */  public String getNumber()
/* 57:   */  {
/* 58:58 */    return this.number;
/* 59:   */  }
/* 60:   */  
/* 61:   */  public void setNumber(String number)
/* 62:   */  {
/* 63:63 */    this.number = number;
/* 64:   */  }
/* 65:   */  
/* 66:   */  public boolean equals(Object obj)
/* 67:   */  {
/* 68:68 */    if (obj == null) return false;
/* 69:69 */    if (!(obj instanceof PrroductDirectory)) { return false;
/* 70:   */    }
/* 71:71 */    PrroductDirectory prroductDirectory = (PrroductDirectory)obj;
/* 72:72 */    if ((getId() == null) || (prroductDirectory.getId() == null)) return false;
/* 73:73 */    return getId().equals(prroductDirectory.getId());
/* 74:   */  }
/* 75:   */  
/* 76:   */  public int hashCode()
/* 77:   */  {
/* 78:78 */    if (-2147483648 == this.hashCode) {
/* 79:79 */      if (getId() == null) { return super.hashCode();
/* 80:   */      }
/* 81:81 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 82:82 */      this.hashCode = hashStr.hashCode();
/* 83:   */    }
/* 84:   */    
/* 85:85 */    return this.hashCode;
/* 86:   */  }
/* 87:   */  
/* 88:   */  public String toString()
/* 89:   */  {
/* 90:90 */    return super.toString();
/* 91:   */  }
/* 92:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BasePrroductDirectory
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */