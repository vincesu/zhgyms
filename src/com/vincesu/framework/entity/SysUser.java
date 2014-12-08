/*  1:   */package com.vincesu.framework.entity;
/*  2:   */
/*  3:   */import com.vincesu.framework.entity.base.BaseSysUser;
/*  4:   */import java.util.Date;
/*  5:   */
/*  6:   */public class SysUser
/*  7:   */  extends BaseSysUser
/*  8:   */{
/*  9:   */  private static final long serialVersionUID = 1L;
/* 10:   */  
/* 11:   */  public SysUser() {}
/* 12:   */  
/* 13:   */  public SysUser(Long id)
/* 14:   */  {
/* 15:15 */    super(id);
/* 16:   */  }
/* 17:   */  
/* 18:   */  public SysUser(Long id, String username, String pwd, Long roleId, Date joinedTime)
/* 19:   */  {
/* 20:20 */    super(id, username, pwd, roleId, joinedTime);
/* 21:   */  }
/* 22:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.entity.SysUser
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */