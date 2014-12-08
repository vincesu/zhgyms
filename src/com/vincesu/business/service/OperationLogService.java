/*  1:   */package com.vincesu.business.service;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.Contract;
/*  4:   */import com.vincesu.business.entity.Files;
/*  5:   */import com.vincesu.business.entity.ManufactureOrder;
/*  6:   */import com.vincesu.business.entity.OperationLog;
/*  7:   */import com.vincesu.framework.entity.SysUser;
/*  8:   */import com.vincesu.framework.remote.RequestEntity;
/*  9:   */import com.vincesu.framework.remote.ResponseEntity;
/* 10:   */import com.vincesu.framework.util.QueryUtil;
/* 11:   */import com.vincesu.persistence.PMF;
/* 12:   */import java.util.Date;
/* 13:   */
/* 14:   */public class OperationLogService
/* 15:   */{
/* 16:   */  public static final int operationAdd = 1;
/* 17:   */  public static final int operationRemove = 2;
/* 18:   */  public static final int operationModify = 3;
/* 19:   */  
/* 20:   */  public static void addLog(PMF pmf, String operation, String operator, Long operatirId)
/* 21:   */  {
/* 22:22 */    OperationLog ol = new OperationLog();
/* 23:23 */    ol.setOperation(operation);
/* 24:24 */    ol.setOperationdate(new Date());
/* 25:25 */    ol.setOperator(operator);
/* 26:26 */    ol.setOperationid(operatirId);
/* 27:27 */    pmf.save(ol);
/* 28:   */  }
/* 29:   */  
/* 30:   */  public static void addLog(PMF pmf, SysUser user, Object data, int op, String type)
/* 31:   */  {
/* 32:32 */    OperationLog ol = new OperationLog();
/* 33:33 */    ol.setReserved1(type);
/* 34:   */    
/* 35:35 */    String oper = null;
/* 36:36 */    if (op == 1) {
/* 37:37 */      oper = "上传文件";
/* 38:38 */    } else if (op == 2) {
/* 39:39 */      oper = "删除文件";
/* 40:40 */    } else if (op == 3) {
/* 41:41 */      oper = "修改文件";
/* 42:   */    }
/* 43:43 */    if (data.getClass() == Files.class)
/* 44:   */    {
/* 45:45 */      Files file = (Files)data;
/* 46:46 */      ol.setOperation(oper + "文件，文件编号：" + file.getId() + 
/* 47:47 */        ",文件名称:" + file.getName() + ",文件路径：" + file.getPath() + 
/* 48:48 */        ",文件类型:" + file.getType() + ",文件相关业务:" + 
/* 49:49 */        file.getRelatedDescription() + ",文件相关文件id：" + 
/* 50:50 */        file.getRelatedObject());
/* 51:51 */      ol.setOperationdate(new Date());
/* 52:52 */      ol.setOperator(user.getRealname());
/* 53:53 */      ol.setOperationid(user.getId());
/* 54:54 */      pmf.save(ol);
/* 55:   */    }
/* 56:56 */    else if (data.getClass() == Contract.class)
/* 57:   */    {
/* 58:58 */      Contract contract = (Contract)data;
/* 59:59 */      ol.setOperation(oper + "合同，合同编号：" + contract.getNumber());
/* 60:60 */      ol.setOperationdate(new Date());
/* 61:61 */      ol.setOperator(user.getRealname());
/* 62:62 */      ol.setOperationid(user.getId());
/* 63:63 */      pmf.save(ol);
/* 64:   */    }
/* 65:65 */    else if (data.getClass() == ManufactureOrder.class)
/* 66:   */    {
/* 67:67 */      ManufactureOrder produce = (ManufactureOrder)data;
/* 68:68 */      ol.setOperation(oper + "生产单，生产单编号：" + produce.getNumber() + ",生产单ID：" + produce.getId());
/* 69:69 */      ol.setOperationdate(new Date());
/* 70:70 */      ol.setOperator(user.getRealname());
/* 71:71 */      ol.setOperationid(user.getId());
/* 72:72 */      pmf.save(ol);
/* 73:   */    }
/* 74:   */  }
/* 75:   */  
/* 76:   */  public ResponseEntity queryList(RequestEntity req) throws Exception
/* 77:   */  {
/* 78:78 */    StringBuffer sql = new StringBuffer();
/* 79:79 */    sql.append("select a.id,a.operation,a.operationdate,a.operator,a.operationid,a.reserved1 ");
/* 80:80 */    sql.append(" from operation_log a ");
/* 81:81 */    sql.append(" where a.operator like '%:name%' ");
/* 82:82 */    sql.append(" and a.operationdate >= ':bt' ");
/* 83:83 */    sql.append(" and a.operationdate <= ':et' ");
/* 84:84 */    sql.append(" order by a.operationdate desc,a.operationid ");
/* 85:85 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 86:86 */      new String[] { "id", "operation", "date", "operator", "operatorid", "type" }, 
/* 87:87 */      new String[] { "id", "operation", "date", "operator", "operatorid", "type" }, 
/* 88:88 */      new String[] { "date" }, 
/* 89:89 */      new String[] { "yyyy-MM-dd" });
/* 90:   */  }
/* 91:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.OperationLogService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */