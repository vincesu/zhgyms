/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.AddressBook;
/*   4:    */import com.vincesu.framework.remote.RemoteUtil;
/*   5:    */import com.vincesu.framework.remote.RequestEntity;
/*   6:    */import com.vincesu.framework.remote.ResponseEntity;
/*   7:    */import com.vincesu.framework.util.BeanUtil;
/*   8:    */import com.vincesu.framework.util.QueryUtil;
/*   9:    */import com.vincesu.persistence.PMF;
/*  10:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  11:    */import java.io.FileInputStream;
/*  12:    */import java.io.IOException;
/*  13:    */import java.io.OutputStream;
/*  14:    */import java.io.PrintStream;
/*  15:    */import java.sql.Connection;
/*  16:    */import java.sql.PreparedStatement;
/*  17:    */import java.sql.SQLException;
/*  18:    */import java.util.List;
/*  19:    */import java.util.Map;
/*  20:    */import jxl.Cell;
/*  21:    */import jxl.Sheet;
/*  22:    */import jxl.Workbook;
/*  23:    */import jxl.write.Label;
/*  24:    */import jxl.write.WritableSheet;
/*  25:    */import jxl.write.WritableWorkbook;
/*  26:    */import jxl.write.WriteException;
/*  27:    */import org.hibernate.jdbc.Work;
/*  28:    */
/*  29:    */public class AddressBookService
/*  30:    */{
/*  31:    */  public ResponseEntity queryList(RequestEntity req)
/*  32:    */    throws Exception
/*  33:    */  {
/*  34: 34 */    StringBuffer sql = new StringBuffer();
/*  35: 35 */    sql.append("select a.* from address_book a ");
/*  36: 36 */    sql.append(" where a.name like '%:name%' ");
/*  37: 37 */    sql.append(" and a.gender=':gender' ");
/*  38: 38 */    sql.append(" order by a.name ");
/*  39: 39 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  40: 40 */      new String[] { "id", "name", "gender", "officePhone", "phone", "email", "number", "department", "posi", "birthday", "graduateSchool", "educationalBackground", "entryDate" }, 
/*  41: 41 */      new String[] { "id", "name", "gender", "officePhone", "phone", "email", "number", "department", "posi", "birthday", "graduateSchool", "educationalBackground", "entryDate" }, null, null);
/*  42:    */  }
/*  43:    */  
/*  44:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  45:    */  {
/*  46: 46 */    StringBuffer sql = new StringBuffer();
/*  47: 47 */    sql.append("select a.* from address_book a ");
/*  48: 48 */    sql.append(" where a.id=:id ");
/*  49: 49 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  50: 50 */      new String[] { "id", "name", "gender", "officePhone", "phone", "email", "number", "department", "posi", "birthday", "graduateSchool", "educationalBackground", "entryDate" }, null, null);
/*  51:    */  }
/*  52:    */  
/*  53:    */  public ResponseEntity add(RequestEntity req)
/*  54:    */  {
/*  55: 55 */    PMF pmf = RemoteUtil.getPMF(req);
/*  56: 56 */    ResponseEntity resp = new ResponseEntity();
/*  57:    */    try
/*  58:    */    {
/*  59: 59 */      Map val = (Map)req.getData().get(0);
/*  60: 60 */      AddressBook addressBook = new AddressBook();
/*  61: 61 */      BeanUtil.copyProperty(val, addressBook, new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/*  62: 62 */      pmf.save(addressBook);
/*  63:    */    }
/*  64:    */    catch (Exception e)
/*  65:    */    {
/*  66: 66 */      resp.setType(0);
/*  67: 67 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/*  68:    */    }
/*  69: 69 */    return resp;
/*  70:    */  }
/*  71:    */  
/*  72:    */  public ResponseEntity remove(RequestEntity req)
/*  73:    */  {
/*  74: 74 */    PMF pmf = RemoteUtil.getPMF(req);
/*  75: 75 */    ResponseEntity resp = new ResponseEntity();
/*  76:    */    try
/*  77:    */    {
/*  78: 78 */      Map val = (Map)req.getData().get(0);
/*  79: 79 */      String id = val.get("id").toString();
/*  80: 80 */      AddressBook addressBook = (AddressBook)pmf.get(AddressBook.class, new Long(id));
/*  81: 81 */      pmf.remove(addressBook);
/*  82:    */    }
/*  83:    */    catch (Exception e)
/*  84:    */    {
/*  85: 85 */      resp.setType(0);
/*  86: 86 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/*  87:    */    }
/*  88: 88 */    return resp;
/*  89:    */  }
/*  90:    */  
/*  91:    */  public ResponseEntity update(RequestEntity req)
/*  92:    */  {
/*  93: 93 */    PMF pmf = RemoteUtil.getPMF(req);
/*  94: 94 */    ResponseEntity resp = new ResponseEntity();
/*  95:    */    try
/*  96:    */    {
/*  97: 97 */      Map val = (Map)req.getData().get(0);
/*  98: 98 */      String id = val.get("id").toString();
/*  99: 99 */      AddressBook addressBook = (AddressBook)pmf.get(AddressBook.class, new Long(id));
/* 100:100 */      BeanUtil.copyProperty(val, addressBook, new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/* 101:101 */      pmf.update(addressBook);
/* 102:    */    }
/* 103:    */    catch (Exception e)
/* 104:    */    {
/* 105:105 */      resp.setType(0);
/* 106:106 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 107:    */    }
/* 108:108 */    return resp;
/* 109:    */  }
/* 110:    */  
/* 111:    */  public ResponseEntity exportData(RequestEntity req) throws IOException, WriteException
/* 112:    */  {
/* 113:113 */    PMF pmf = RemoteUtil.getPMF(req);
/* 114:114 */    ResponseEntity resp = new ResponseEntity();
/* 115:115 */    WritableWorkbook workbook = null;
/* 116:    */    try
/* 117:    */    {
/* 118:118 */      Map val = (Map)req.getData().get(0);
/* 119:119 */      OutputStream os = (OutputStream)val.get("filestream");
/* 120:120 */      workbook = Workbook.createWorkbook(os);
/* 121:121 */      WritableSheet sheet = workbook.createSheet("Address_Book", 0);
/* 122:122 */      Label label = null;
/* 123:123 */      int i = 0;int j = 0;
/* 124:    */      
/* 125:125 */      String[] titles = { "姓名", "性别", "办公室电话", 
/* 126:126 */        "移动电话", "电子邮件", "部门", "职务" };
/* 127:127 */      for (i = 0; i < titles.length; i++)
/* 128:    */      {
/* 129:129 */        label = new Label(i, 0, titles[i]);
/* 130:130 */        sheet.addCell(label);
/* 131:131 */        sheet.setColumnView(i, 13);
/* 132:    */      }
/* 133:    */      
/* 134:134 */      StringBuffer hql = new StringBuffer();
/* 135:135 */      hql.append("from AddressBook");
/* 136:136 */      List<AddressBook> datas = pmf.list(hql.toString());
/* 137:137 */      for (i = 0; i < datas.size(); i++)
/* 138:    */      {
/* 139:139 */        j = 0;
/* 140:140 */        label = new Label(j++, i + 1, ((AddressBook)datas.get(i)).getName());
/* 141:141 */        sheet.addCell(label);
/* 142:142 */        if (((AddressBook)datas.get(i)).getGender().equals("1")) {
/* 143:143 */          label = new Label(j++, i + 1, "男");
/* 144:    */        } else
/* 145:145 */          label = new Label(j++, i + 1, "女");
/* 146:146 */        sheet.addCell(label);
/* 147:147 */        label = new Label(j++, i + 1, ((AddressBook)datas.get(i)).getOfficePhone());
/* 148:148 */        sheet.addCell(label);
/* 149:149 */        label = new Label(j++, i + 1, ((AddressBook)datas.get(i)).getPhone());
/* 150:150 */        sheet.addCell(label);
/* 151:151 */        label = new Label(j++, i + 1, ((AddressBook)datas.get(i)).getEmail());
/* 152:152 */        sheet.addCell(label);
/* 153:153 */        label = new Label(j++, i + 1, ((AddressBook)datas.get(i)).getDepartment());
/* 154:154 */        sheet.addCell(label);
/* 155:155 */        label = new Label(j++, i + 1, ((AddressBook)datas.get(i)).getPosi());
/* 156:156 */        sheet.addCell(label);
/* 157:    */      }
/* 158:    */      
/* 159:    */    }
/* 160:    */    catch (Exception e)
/* 161:    */    {
/* 162:162 */      System.out.println(e.getMessage());
/* 163:163 */      resp.setType(0);
/* 164:164 */      resp.setMessage("导出失败");
/* 165:    */    }
/* 166:    */    finally
/* 167:    */    {
/* 168:168 */      if (workbook != null)
/* 169:    */      {
/* 170:170 */        workbook.write();
/* 171:171 */        workbook.close();
/* 172:    */      }
/* 173:    */    }
/* 174:174 */    return resp;
/* 175:    */  }
/* 176:    */  
/* 177:    */  public ResponseEntity importData(RequestEntity req)
/* 178:    */  {
/* 179:179 */    PMF pmf = RemoteUtil.getPMF(req);
/* 180:180 */    ResponseEntity resp = new ResponseEntity();
/* 181:181 */    Workbook workbook = null;
/* 182:    */    try
/* 183:    */    {
/* 184:184 */      Map data = (Map)req.getData().get(0);
/* 185:185 */      FileInputStream fis = (FileInputStream)data.get("filestream");
/* 186:186 */      workbook = Workbook.getWorkbook(fis);
/* 187:187 */      final Sheet sheet = workbook.getSheet(0);
/* 188:188 */      pmf.doWorkWithConnection(new Work()
/* 189:    */      {
/* 190:    */        public void execute(Connection conn) throws SQLException
/* 191:    */        {
/* 192:192 */          PMF p = new PMF(SessionFactoryHelper.getSession());
/* 193:193 */          List<AddressBook> adList = p.list("from AddressBook order by id desc", null, 0, 1);
/* 194:194 */          Long id = new Long(1L);
/* 195:195 */          if ((adList != null) && (adList.size() != 0))
/* 196:196 */            id = ((AddressBook)adList.get(0)).getId();
/* 197:197 */          String sql = "insert into address_book(id,name,gender,office_phone,phone,email,department,posi) values(?,?,?,?,?,?,?,?)";
/* 198:198 */          PreparedStatement prest = conn.prepareStatement(sql);
/* 199:    */          try
/* 200:    */          {
/* 201:201 */            for (int i = 1; i < sheet.getRows(); i++)
/* 202:    */            {
/* 203:203 */              prest.setLong(1, (id = Long.valueOf(id.longValue() + 1L)).longValue());
/* 204:204 */              prest.setString(2, sheet.getCell(0, i).getContents());
/* 205:205 */              prest.setString(3, sheet.getCell(1, i).getContents());
/* 206:206 */              prest.setString(4, sheet.getCell(2, i).getContents());
/* 207:207 */              prest.setString(5, sheet.getCell(3, i).getContents());
/* 208:208 */              prest.setString(6, sheet.getCell(4, i).getContents());
/* 209:209 */              prest.setString(7, sheet.getCell(5, i).getContents());
/* 210:210 */              prest.setString(8, sheet.getCell(6, i).getContents());
/* 211:211 */              prest.addBatch();
/* 212:    */            }
/* 213:213 */            prest.executeBatch();
/* 214:214 */            conn.commit();
/* 215:    */          }
/* 216:    */          catch (Exception e)
/* 217:    */          {
/* 218:218 */            conn.rollback();
/* 219:    */          }
/* 220:    */          finally
/* 221:    */          {
/* 222:222 */            prest.close();
/* 223:223 */            conn.close();
/* 224:    */          }
/* 225:    */        }
/* 226:    */      });
/* 227:    */    }
/* 228:    */    catch (Exception e)
/* 229:    */    {
/* 230:230 */      resp.setType(0);
/* 231:231 */      resp.setMessage("导入数据出错");
/* 232:    */    }
/* 233:    */    finally
/* 234:    */    {
/* 235:235 */      if (workbook != null)
/* 236:236 */        workbook.close();
/* 237:    */    }
/* 238:238 */    resp.setMessage("数据已导入");
/* 239:239 */    return resp;
/* 240:    */  }
/* 241:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.AddressBookService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */