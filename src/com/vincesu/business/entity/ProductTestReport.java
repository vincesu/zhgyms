/*  1:   */package com.vincesu.business.entity;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.base.BaseProductTestReport;
/*  4:   */import java.util.Date;
/*  5:   */
/*  6:   */public class ProductTestReport
/*  7:   */  extends BaseProductTestReport
/*  8:   */{
/*  9:   */  private static final long serialVersionUID = 1L;
/* 10:   */  private Long fileId;
/* 11:   */  private String fileName;
/* 12:   */  
/* 13:   */  public ProductTestReport() {}
/* 14:   */  
/* 15:   */  public ProductTestReport(Long id, String name, Date date, String operator, Long fileId, String fileName)
/* 16:   */  {
/* 17:17 */    setId(id);
/* 18:18 */    setName(name);
/* 19:19 */    setDate(date);
/* 20:20 */    setOperator(operator);
/* 21:21 */    setFileId(fileId);
/* 22:22 */    setFileName(fileName);
/* 23:   */  }
/* 24:   */  
/* 25:   */  public ProductTestReport(Long id)
/* 26:   */  {
/* 27:27 */    super(id);
/* 28:   */  }
/* 29:   */  
/* 30:   */  public void setFileId(Long fileId) {
/* 31:31 */    this.fileId = fileId;
/* 32:   */  }
/* 33:   */  
/* 34:   */  public Long getFileId() {
/* 35:35 */    return this.fileId;
/* 36:   */  }
/* 37:   */  
/* 38:   */  public void setFileName(String fileName) {
/* 39:39 */    this.fileName = fileName;
/* 40:   */  }
/* 41:   */  
/* 42:   */  public String getFileName() {
/* 43:43 */    return this.fileName;
/* 44:   */  }
/* 45:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.ProductTestReport
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */