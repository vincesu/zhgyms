/*  1:   */package com.vincesu.business.entity;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.base.BaseNotice;
/*  4:   */import java.util.Date;
/*  5:   */
/*  6:   */public class Notice
/*  7:   */  extends BaseNotice
/*  8:   */{
/*  9:   */  private static final long serialVersionUID = 1L;
/* 10:   */  private Long fileid;
/* 11:   */  
/* 12:   */  public Notice() {}
/* 13:   */  
/* 14:   */  public Notice(Long id)
/* 15:   */  {
/* 16:16 */    super(id);
/* 17:   */  }
/* 18:   */  
/* 19:   */  public Notice(Long id, String title, String author, String content, Date date, Long fileid)
/* 20:   */  {
/* 21:21 */    setId(id);
/* 22:22 */    setTitle(title);
/* 23:23 */    setAuthor(author);
/* 24:24 */    setContent(content);
/* 25:25 */    setDate(date);
/* 26:26 */    setFileid(fileid);
/* 27:   */  }
/* 28:   */  
/* 29:   */  public Long getFileid() {
/* 30:30 */    return this.fileid;
/* 31:   */  }
/* 32:   */  
/* 33:   */  public void setFileid(Long fileid) {
/* 34:34 */    this.fileid = fileid;
/* 35:   */  }
/* 36:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.Notice
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */