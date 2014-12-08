/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Notice;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseNotice
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "Notice";
/*  11: 11 */  public static String PROP_DATE = "Date";
/*  12: 12 */  public static String PROP_AUTHOR = "Author";
/*  13: 13 */  public static String PROP_ID = "Id";
/*  14: 14 */  public static String PROP_CONTENT = "Content";
/*  15: 15 */  public static String PROP_TITLE = "Title";
/*  16:    */  
/*  17:    */  public BaseNotice()
/*  18:    */  {
/*  19: 19 */    initialize();
/*  20:    */  }
/*  21:    */  
/*  22:    */  public BaseNotice(Long id)
/*  23:    */  {
/*  24: 24 */    setId(id);
/*  25: 25 */    initialize();
/*  26:    */  }
/*  27:    */  
/*  28: 28 */  private int hashCode = -2147483648;
/*  29:    */  
/*  30:    */  private Long id;
/*  31:    */  
/*  32:    */  private String title;
/*  33:    */  
/*  34:    */  private String author;
/*  35:    */  
/*  36:    */  private String content;
/*  37:    */  
/*  38:    */  private Date date;
/*  39:    */  
/*  40:    */  protected void initialize() {}
/*  41:    */  
/*  42:    */  public Long getId()
/*  43:    */  {
/*  44: 44 */    return this.id;
/*  45:    */  }
/*  46:    */  
/*  47:    */  public void setId(Long id)
/*  48:    */  {
/*  49: 49 */    this.id = id;
/*  50: 50 */    this.hashCode = -2147483648;
/*  51:    */  }
/*  52:    */  
/*  53:    */  public String getTitle()
/*  54:    */  {
/*  55: 55 */    return this.title;
/*  56:    */  }
/*  57:    */  
/*  58:    */  public void setTitle(String title)
/*  59:    */  {
/*  60: 60 */    this.title = title;
/*  61:    */  }
/*  62:    */  
/*  63:    */  public String getAuthor()
/*  64:    */  {
/*  65: 65 */    return this.author;
/*  66:    */  }
/*  67:    */  
/*  68:    */  public void setAuthor(String author)
/*  69:    */  {
/*  70: 70 */    this.author = author;
/*  71:    */  }
/*  72:    */  
/*  73:    */  public String getContent()
/*  74:    */  {
/*  75: 75 */    return this.content;
/*  76:    */  }
/*  77:    */  
/*  78:    */  public void setContent(String content)
/*  79:    */  {
/*  80: 80 */    this.content = content;
/*  81:    */  }
/*  82:    */  
/*  83:    */  public Date getDate()
/*  84:    */  {
/*  85: 85 */    return this.date;
/*  86:    */  }
/*  87:    */  
/*  88:    */  public void setDate(Date date)
/*  89:    */  {
/*  90: 90 */    this.date = date;
/*  91:    */  }
/*  92:    */  
/*  93:    */  public boolean equals(Object obj)
/*  94:    */  {
/*  95: 95 */    if (obj == null) return false;
/*  96: 96 */    if (!(obj instanceof Notice)) { return false;
/*  97:    */    }
/*  98: 98 */    Notice notice = (Notice)obj;
/*  99: 99 */    if ((getId() == null) || (notice.getId() == null)) return false;
/* 100:100 */    return getId().equals(notice.getId());
/* 101:    */  }
/* 102:    */  
/* 103:    */  public int hashCode()
/* 104:    */  {
/* 105:105 */    if (-2147483648 == this.hashCode) {
/* 106:106 */      if (getId() == null) { return super.hashCode();
/* 107:    */      }
/* 108:108 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 109:109 */      this.hashCode = hashStr.hashCode();
/* 110:    */    }
/* 111:    */    
/* 112:112 */    return this.hashCode;
/* 113:    */  }
/* 114:    */  
/* 115:    */  public String toString()
/* 116:    */  {
/* 117:117 */    return super.toString();
/* 118:    */  }
/* 119:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseNotice
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */