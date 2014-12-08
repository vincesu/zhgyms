/*  1:   */package com.vincesu.framework.model;
/*  2:   */
/*  3:   */import com.vincesu.framework.entity.SysEncoding;
/*  4:   */import com.vincesu.persistence.PMF;
/*  5:   */import java.util.HashMap;
/*  6:   */import java.util.List;
/*  7:   */import java.util.Map;
/*  8:   */
/*  9:   */public class EncodingModel
/* 10:   */  extends AbstractModel<SysEncoding>
/* 11:   */{
/* 12:12 */  protected Map<String, HashMap<String, Object>> encodingTable = null;
/* 13:13 */  protected List<SysEncoding> _encodingList = null;
/* 14:   */  
/* 15:   */  public EncodingModel(PMF p)
/* 16:   */  {
/* 17:17 */    super(p);
/* 18:18 */    this.encodingTable = new HashMap();
/* 19:19 */    getEncodingTable();
/* 20:   */  }
/* 21:   */  
/* 22:   */  public void getEncodingTable()
/* 23:   */  {
/* 24:24 */    this._encodingList = list();
/* 25:25 */    this.encodingTable.clear();
/* 26:26 */    HashMap<String, Object> m = null;
/* 27:27 */    for (SysEncoding se : this._encodingList)
/* 28:   */    {
/* 29:29 */      m = (HashMap)this.encodingTable.get(se.getFieldName());
/* 30:30 */      if (m == null)
/* 31:   */      {
/* 32:32 */        m = new HashMap();
/* 33:33 */        this.encodingTable.put(se.getFieldName(), m);
/* 34:   */      }
/* 35:35 */      m.put(se.getFieldValue(), se.getCodingValue());
/* 36:   */    }
/* 37:   */  }
/* 38:   */  
/* 39:   */  public String getCodingValue(String field, String fieldValue)
/* 40:   */  {
/* 41:41 */    Object obj = this.encodingTable.get(field);
/* 42:42 */    if (obj == null)
/* 43:43 */      return "";
/* 44:44 */    obj = ((HashMap)this.encodingTable.get(field)).get(fieldValue);
/* 45:45 */    if (obj == null) {
/* 46:46 */      return "";
/* 47:   */    }
/* 48:48 */    return obj.toString();
/* 49:   */  }
/* 50:   */  
/* 51:   */  public List<SysEncoding> getEncodingList()
/* 52:   */  {
/* 53:53 */    return this._encodingList;
/* 54:   */  }
/* 55:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.model.EncodingModel
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */