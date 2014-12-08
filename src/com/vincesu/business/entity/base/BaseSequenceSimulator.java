/*  1:   */package com.vincesu.business.entity.base;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.SequenceSimulator;
/*  4:   */import java.io.Serializable;
/*  5:   */
/*  6:   */public abstract class BaseSequenceSimulator
/*  7:   */  implements Serializable
/*  8:   */{
/*  9: 9 */  public static String REF = "SequenceSimulator";
/* 10:10 */  public static String PROP_ID = "Id";
/* 11:   */  
/* 12:   */  public BaseSequenceSimulator()
/* 13:   */  {
/* 14:14 */    initialize();
/* 15:   */  }
/* 16:   */  
/* 17:   */  public BaseSequenceSimulator(Long id)
/* 18:   */  {
/* 19:19 */    setId(id);
/* 20:20 */    initialize();
/* 21:   */  }
/* 22:   */  
/* 23:23 */  private int hashCode = -2147483648;
/* 24:   */  
/* 25:   */  private Long id;
/* 26:   */  
/* 27:   */  protected void initialize() {}
/* 28:   */  
/* 29:   */  public Long getId()
/* 30:   */  {
/* 31:31 */    return this.id;
/* 32:   */  }
/* 33:   */  
/* 34:   */  public void setId(Long id)
/* 35:   */  {
/* 36:36 */    this.id = id;
/* 37:37 */    this.hashCode = -2147483648;
/* 38:   */  }
/* 39:   */  
/* 40:   */  public boolean equals(Object obj)
/* 41:   */  {
/* 42:42 */    if (obj == null) return false;
/* 43:43 */    if (!(obj instanceof SequenceSimulator)) { return false;
/* 44:   */    }
/* 45:45 */    SequenceSimulator sequenceSimulator = (SequenceSimulator)obj;
/* 46:46 */    if ((getId() == null) || (sequenceSimulator.getId() == null)) return false;
/* 47:47 */    return getId().equals(sequenceSimulator.getId());
/* 48:   */  }
/* 49:   */  
/* 50:   */  public int hashCode()
/* 51:   */  {
/* 52:52 */    if (-2147483648 == this.hashCode) {
/* 53:53 */      if (getId() == null) { return super.hashCode();
/* 54:   */      }
/* 55:55 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 56:56 */      this.hashCode = hashStr.hashCode();
/* 57:   */    }
/* 58:   */    
/* 59:59 */    return this.hashCode;
/* 60:   */  }
/* 61:   */  
/* 62:   */  public String toString()
/* 63:   */  {
/* 64:64 */    return super.toString();
/* 65:   */  }
/* 66:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseSequenceSimulator
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */