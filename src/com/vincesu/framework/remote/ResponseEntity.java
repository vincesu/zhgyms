/*   1:    */package com.vincesu.framework.remote;
/*   2:    */
/*   3:    */import java.util.List;
/*   4:    */import java.util.Map;
/*   5:    */
/*   9:    */public class ResponseEntity
/*  10:    */{
/*  11:    */  public static final int RESPONSE_TYPE_ERROR = 0;
/*  12:    */  public static final int RESPONSE_TYPE_DATA = 1;
/*  13: 13 */  private int type = -1;
/*  14: 14 */  private String message = null;
/*  15: 15 */  private String errorCode = null;
/*  16: 16 */  private int total = 0;
/*  17: 17 */  private int page = 0;
/*  18: 18 */  private int records = 0;
/*  19: 19 */  private List rows = null;
/*  20: 20 */  private List<Map<Object, Object>> data = null;
/*  21: 21 */  private boolean rollback = false;
/*  22:    */  
/*  23:    */  public void setType(int type) {
/*  24: 24 */    this.type = type;
/*  25: 25 */    if (type == 0) {
/*  26: 26 */      setRollback(true);
/*  27:    */    }
/*  28:    */  }
/*  29:    */  
/*  30:    */  public int getType() {
/*  31: 31 */    return this.type;
/*  32:    */  }
/*  33:    */  
/*  34:    */  public void setData(List<Map<Object, Object>> data) {
/*  35: 35 */    this.data = data;
/*  36:    */  }
/*  37:    */  
/*  38:    */  public List<Map<Object, Object>> getData() {
/*  39: 39 */    return this.data;
/*  40:    */  }
/*  41:    */  
/*  42:    */  public void setMessage(String message) {
/*  43: 43 */    this.message = message;
/*  44:    */  }
/*  45:    */  
/*  46:    */  public String getMessage() {
/*  47: 47 */    return this.message;
/*  48:    */  }
/*  49:    */  
/*  50:    */  public String getTypeName()
/*  51:    */  {
/*  52: 52 */    String result = null;
/*  53:    */    
/*  54: 54 */    switch (this.type) {
/*  55:    */    case -1: 
/*  56: 56 */      result = "normal";break;
/*  57: 57 */    case 0:  result = "error";break;
/*  58: 58 */    case 1:  result = "data";
/*  59:    */    }
/*  60:    */    
/*  61: 61 */    return result;
/*  62:    */  }
/*  63:    */  
/*  64:    */  public void setTotal(int total) {
/*  65: 65 */    this.total = total;
/*  66:    */  }
/*  67:    */  
/*  68:    */  public int getTotal() {
/*  69: 69 */    return this.total;
/*  70:    */  }
/*  71:    */  
/*  72:    */  public void setPage(int page) {
/*  73: 73 */    this.page = page;
/*  74:    */  }
/*  75:    */  
/*  76:    */  public int getPage() {
/*  77: 77 */    return this.page;
/*  78:    */  }
/*  79:    */  
/*  80:    */  public void setRecords(int records) {
/*  81: 81 */    this.records = records;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public int getRecords() {
/*  85: 85 */    return this.records;
/*  86:    */  }
/*  87:    */  
/*  88:    */  public void setRows(List rows) {
/*  89: 89 */    this.rows = rows;
/*  90:    */  }
/*  91:    */  
/*  92:    */  public List getRows() {
/*  93: 93 */    return this.rows;
/*  94:    */  }
/*  95:    */  
/*  96:    */  public void setErrorCode(String errorCode) {
/*  97: 97 */    this.errorCode = errorCode;
/*  98:    */  }
/*  99:    */  
/* 100:    */  public String getErrorCode() {
/* 101:101 */    return this.errorCode;
/* 102:    */  }
/* 103:    */  
/* 104:    */  public boolean isRollback() {
/* 105:105 */    return this.rollback;
/* 106:    */  }
/* 107:    */  
/* 108:    */  public void setRollback(boolean rollback) {
/* 109:109 */    this.rollback = rollback;
/* 110:    */  }
/* 111:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.remote.ResponseEntity
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */