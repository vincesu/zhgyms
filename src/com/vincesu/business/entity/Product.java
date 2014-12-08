/*  1:   */package com.vincesu.business.entity;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.base.BaseProduct;
/*  4:   */
/*  5:   */public class Product
/*  6:   */  extends BaseProduct
/*  7:   */{
/*  8:   */  private static final long serialVersionUID = 1L;
/*  9:   */  private String webPath;
/* 10:   */  private String path;
/* 11:   */  private String pngpath;
/* 12:   */  
/* 13:   */  public Product() {}
/* 14:   */  
/* 15:   */  public Product(Long id)
/* 16:   */  {
/* 17:17 */    super(id);
/* 18:   */  }
/* 19:   */  
/* 20:   */  public Product(Long id, String name, String number, String size, String referencePrice, String moq, String description, String category, String permissions, String reserved, String webPath, String path)
/* 21:   */  {
/* 22:22 */    setId(id);
/* 23:23 */    setName(name);
/* 24:24 */    setNumber(number);
/* 25:25 */    setSize(size);
/* 26:26 */    setReferencePrice(referencePrice);
/* 27:27 */    setMoq(moq);
/* 28:28 */    setDescription(description);
/* 29:29 */    setCategory(category);
/* 30:30 */    setPermissions(permissions);
/* 31:31 */    setReserved(reserved);
/* 32:32 */    this.webPath = webPath;
/* 33:33 */    this.path = path;
/* 34:   */  }
/* 35:   */  
/* 36:   */  public Product(Long id, String name, String number, String size, String referencePrice, String moq, String description, String category, String permissions, String reserved, String webPath, String path, String pngpath)
/* 37:   */  {
/* 38:38 */    setId(id);
/* 39:39 */    setName(name);
/* 40:40 */    setNumber(number);
/* 41:41 */    setSize(size);
/* 42:42 */    setReferencePrice(referencePrice);
/* 43:43 */    setMoq(moq);
/* 44:44 */    setDescription(description);
/* 45:45 */    setCategory(category);
/* 46:46 */    setPermissions(permissions);
/* 47:47 */    setReserved(reserved);
/* 48:48 */    this.webPath = webPath;
/* 49:49 */    this.path = path;
/* 50:50 */    this.pngpath = pngpath;
/* 51:   */  }
/* 52:   */  
/* 53:   */  public void setWebPath(String webPath)
/* 54:   */  {
/* 55:55 */    this.webPath = webPath;
/* 56:   */  }
/* 57:   */  
/* 58:   */  public String getWebPath()
/* 59:   */  {
/* 60:60 */    return this.webPath;
/* 61:   */  }
/* 62:   */  
/* 63:   */  public void setPath(String path)
/* 64:   */  {
/* 65:65 */    this.path = path;
/* 66:   */  }
/* 67:   */  
/* 68:   */  public String getPath()
/* 69:   */  {
/* 70:70 */    return this.path;
/* 71:   */  }
/* 72:   */  
/* 73:   */  public void setPngpath(String pngpath)
/* 74:   */  {
/* 75:75 */    this.pngpath = pngpath;
/* 76:   */  }
/* 77:   */  
/* 78:   */  public String getPngpath() {
/* 79:79 */    return this.pngpath;
/* 80:   */  }
/* 81:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.Product
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */