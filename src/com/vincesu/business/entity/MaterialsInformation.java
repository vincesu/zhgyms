/*  1:   */package com.vincesu.business.entity;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.base.BaseMaterialsInformation;
/*  4:   */
/*  5:   */public class MaterialsInformation
/*  6:   */  extends BaseMaterialsInformation
/*  7:   */{
/*  8:   */  private static final long serialVersionUID = 1L;
/*  9:   */  private String picPath;
/* 10:   */  
/* 11:   */  public MaterialsInformation() {}
/* 12:   */  
/* 13:   */  public MaterialsInformation(Long id)
/* 14:   */  {
/* 15:15 */    super(id);
/* 16:   */  }
/* 17:   */  
/* 18:   */  public void setPicPath(String picPath)
/* 19:   */  {
/* 20:20 */    this.picPath = picPath;
/* 21:   */  }
/* 22:   */  
/* 23:   */  public String getPicPath()
/* 24:   */  {
/* 25:25 */    return this.picPath;
/* 26:   */  }
/* 27:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.MaterialsInformation
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */