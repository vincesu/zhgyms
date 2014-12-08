/*  1:   */package com.vincesu.business.entity;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.base.BaseManufactureOrder;
/*  4:   */import java.util.Date;
/*  5:   */
/*  6:   */public class ManufactureOrder
/*  7:   */  extends BaseManufactureOrder
/*  8:   */{
/*  9:   */  private static final long serialVersionUID = 1L;
/* 10:   */  private Long fileId;
/* 11:   */  private String fileName;
/* 12:   */  private String saleman;
/* 13:   */  
/* 14:   */  public ManufactureOrder() {}
/* 15:   */  
/* 16:   */  public ManufactureOrder(Long id)
/* 17:   */  {
/* 18:18 */    super(id);
/* 19:   */  }
/* 20:   */  
/* 21:   */  public ManufactureOrder(Long id, String name, String number, Date date, Date deliveryTime, Long salesman, String destination, Long fileId, String fileName)
/* 22:   */  {
/* 23:23 */    setId(id);
/* 24:24 */    setName(name);
/* 25:25 */    setNumber(number);
/* 26:26 */    setDate(date);
/* 27:27 */    setDeliveryTime(deliveryTime);
/* 28:28 */    setSalesman(salesman);
/* 29:29 */    setDestination(destination);
/* 30:30 */    setFileId(fileId);
/* 31:31 */    setFileName(fileName);
/* 32:   */  }
/* 33:   */  
/* 34:   */  public ManufactureOrder(Long id, String name, String number, Date date, Date deliveryTime, Long salesman, String destination, Long fileId, String fileName, String saleman)
/* 35:   */  {
/* 36:36 */    setId(id);
/* 37:37 */    setName(name);
/* 38:38 */    setNumber(number);
/* 39:39 */    setDate(date);
/* 40:40 */    setDeliveryTime(deliveryTime);
/* 41:41 */    setSalesman(salesman);
/* 42:42 */    setDestination(destination);
/* 43:43 */    setFileId(fileId);
/* 44:44 */    setFileName(fileName);
/* 45:45 */    setSaleman(saleman);
/* 46:   */  }
/* 47:   */  
/* 48:   */  public void setFileId(Long fileId)
/* 49:   */  {
/* 50:50 */    this.fileId = fileId;
/* 51:   */  }
/* 52:   */  
/* 53:   */  public Long getFileId() {
/* 54:54 */    return this.fileId;
/* 55:   */  }
/* 56:   */  
/* 57:   */  public void setFileName(String fileName) {
/* 58:58 */    this.fileName = fileName;
/* 59:   */  }
/* 60:   */  
/* 61:   */  public String getFileName() {
/* 62:62 */    return this.fileName;
/* 63:   */  }
/* 64:   */  
/* 65:   */  public void setSaleman(String saleman) {
/* 66:66 */    this.saleman = saleman;
/* 67:   */  }
/* 68:   */  
/* 69:   */  public String getSaleman() {
/* 70:70 */    return this.saleman;
/* 71:   */  }
/* 72:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.ManufactureOrder
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */