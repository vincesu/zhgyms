/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Contract;
/*   4:    */import com.vincesu.business.entity.FinancialStatements;
/*   5:    */import com.vincesu.framework.remote.RemoteUtil;
/*   6:    */import com.vincesu.framework.remote.RequestEntity;
/*   7:    */import com.vincesu.framework.remote.ResponseEntity;
/*   8:    */import com.vincesu.framework.util.BeanUtil;
/*   9:    */import com.vincesu.framework.util.ExcelUtil;
/*  10:    */import com.vincesu.framework.util.QueryUtil;
/*  11:    */import com.vincesu.persistence.PMF;
/*  12:    */import java.io.IOException;
/*  13:    */import java.io.OutputStream;
/*  14:    */import java.util.List;
/*  15:    */import java.util.Map;
/*  16:    */import jxl.write.WriteException;
/*  17:    */
/*  18:    */public class FinancialStatementsService
/*  19:    */{
/*  20:    */  public ResponseEntity queryList(RequestEntity req) throws Exception
/*  21:    */  {
/*  22: 22 */    StringBuffer sql = new StringBuffer();
/*  23: 23 */    sql.append("SELECT a.`id`,b.`order_date`,b.`delivery_date`,b.`number`,e.`unit`,e.`nationality`,e.`clientfrom`,");
/*  24: 24 */    sql.append("b.`name`,c.`amount`,a.`earnest`,a.`balance`,a.`shipping`,a.`actual_receipts`,a.`fee`,b.`payment`,");
/*  25: 25 */    sql.append("a.`pay_fees`,a.`agency_fees`,a.`billing_fee`,a.`drawback`,a.`status`,d.`realname`,b.makepoint,' ' operator ");
/*  26: 26 */    sql.append("FROM financial_statements a,contract b left join vw_contract_amount c on (b.id=c.id) ,sys_user d,`client` e ");
/*  27: 27 */    sql.append("WHERE a.`contract_id`=b.`id` AND a.`userid`=d.`uid` AND b.`buyer_id`=e.`id` and (b.reserved REGEXP 'locked' or b.reserved REGEXP 'finish') ");
/*  28: 28 */    sql.append("AND b.`order_date`>=':startdate' AND b.`order_date`<=':enddate' and b.`order_date` like ':year%' and a.`status`=':status' and d.`realname` like ':username' and b.number=':number' order by b.id desc ");
/*  29: 29 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  30: 30 */      new String[] { "theid", "orderDate", "deliveryDate", "number", "unit", 
/*  31: 31 */      "nationality", "clientfrom", "name", "amount", "earnest", 
/*  32: 32 */      "balance", "shipping", "actualReceipts", "fee", "payment", 
/*  33: 33 */      "payFees", "agencyFees", "billingFee", "drawback", "status", 
/*  34: 34 */      "username", "makepoint", "operator" }, 
/*  35: 35 */      new String[] { "theid", "orderDate", "deliveryDate", "number", "unit", 
/*  36: 36 */      "nationality", "clientfrom", "name", "amount", "earnest", 
/*  37: 37 */      "balance", "shipping", "actualReceipts", "fee", "payment", 
/*  38: 38 */      "payFees", "agencyFees", "billingFee", "drawback", "status", 
/*  39: 39 */      "username", "makepoint", "operator" }, 
/*  40: 40 */      new String[] { "orderDate" }, 
/*  41: 41 */      new String[] { "yyyy-MM-dd" });
/*  42:    */  }
/*  43:    */  
/*  44:    */  public ResponseEntity queryFinancialstatementsTotal(RequestEntity req)
/*  45:    */    throws Exception
/*  46:    */  {
/*  47: 47 */    StringBuffer sql = new StringBuffer();
/*  48: 48 */    sql.append("SELECT b.`currency_symbol`,sum(IFNULL(a.`actual_receipts`,0.0)),SUM(IFNULL(SUBSTR(c.`amount`,2), 0.0))  ");
/*  49: 49 */    sql.append("FROM financial_statements a,contract b,vw_contract_amount c,sys_user d,`client` e ");
/*  50: 50 */    sql.append("WHERE a.`contract_id`=b.`id` AND a.`userid`=d.`uid` AND a.`contract_id`=c.`id` AND b.`buyer_id`=e.`id` and (b.reserved REGEXP 'locked' or b.reserved REGEXP 'finish') ");
/*  51: 51 */    sql.append("AND b.`order_date`>=':startdate' AND b.`order_date`<=':enddate' and b.`order_date` like ':year%' and a.`status`=':status' and d.`realname` like ':username' group by b.`currency_symbol` ");
/*  52: 52 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  53: 53 */      new String[] { "currencySymbol", "amount", "contractamount" }, null, null);
/*  54:    */  }
/*  55:    */  
/*  56:    */  public ResponseEntity restore(RequestEntity req)
/*  57:    */  {
/*  58: 58 */    ResponseEntity resp = new ResponseEntity();
/*  59: 59 */    FinancialStatements fs = null;
/*  60: 60 */    Map val = null;
/*  61: 61 */    PMF pmf = RemoteUtil.getPMF(req);
/*  62:    */    try
/*  63:    */    {
/*  64: 64 */      for (int i = 0; i < req.getData().size(); i++)
/*  65:    */      {
/*  66: 66 */        val = (Map)req.getData().get(i);
/*  67: 67 */        if ((val.get("id") != null) && 
/*  68: 68 */          (!val.get("id").toString().equals("")))
/*  69:    */        {
/*  70: 70 */          if ((val.get("actualReceipts").toString().startsWith("$")) || 
/*  71:    */          
/*  72: 72 */            (val.get("actualReceipts").toString().startsWith("￥")))
/*  73:    */          {
/*  74: 74 */            val.put("actualReceipts", val.get("actualReceipts")
/*  75: 75 */              .toString().substring(1));
/*  76:    */          }
/*  77: 77 */          fs = (FinancialStatements)pmf.get(
/*  78: 78 */            FinancialStatements.class, new Long(val.get("id")
/*  79: 79 */            .toString()));
/*  80: 80 */          BeanUtil.copyProperty(val, fs);
/*  81:    */          
/*  82: 82 */          pmf.update(fs);
/*  83:    */        }
/*  84:    */      }
/*  85: 85 */      pmf.flush();
/*  86:    */    }
/*  87:    */    catch (Exception e)
/*  88:    */    {
/*  89: 89 */      resp.setType(0);
/*  90: 90 */      resp.setMessage("保存错误，错误原因：" + e.getMessage());
/*  91:    */    }
/*  92: 92 */    return resp;
/*  93:    */  }
/*  94:    */  
/*  95:    */  public ResponseEntity finish(RequestEntity req)
/*  96:    */  {
/*  97: 97 */    ResponseEntity resp = new ResponseEntity();
/*  98: 98 */    FinancialStatements fs = null;
/*  99: 99 */    Map val = null;
/* 100:100 */    PMF pmf = RemoteUtil.getPMF(req);
/* 101:    */    try
/* 102:    */    {
/* 103:103 */      val = (Map)req.getData().get(0);
/* 104:104 */      fs = (FinancialStatements)pmf.get(FinancialStatements.class, 
/* 105:105 */        new Long(val.get("id").toString()));
/* 106:106 */      if (fs != null)
/* 107:    */      {
/* 108:108 */        Contract contract = (Contract)pmf.get(Contract.class, 
/* 109:109 */          fs.getContractId());
/* 110:110 */        if (contract != null)
/* 111:    */        {
/* 112:112 */          ContractService cs = new ContractService();
/* 113:113 */          if (cs.checkContractStatus(contract) >= 2)
/* 114:    */          {
/* 115:115 */            contract.setReserved(contract.getReserved()
/* 116:116 */              .replaceFirst("finish", "locked"));
/* 117:117 */            pmf.update(contract);
/* 118:118 */            fs.setStatus(Integer.valueOf(2));
/* 119:119 */            pmf.update(fs);
/* 120:    */          }
/* 121:    */          else
/* 122:    */          {
/* 123:123 */            resp.setType(0);
/* 124:124 */            resp.setMessage("此合同未审核通过，不能修改为完成状态！");
/* 125:125 */            return resp;
/* 126:    */          }
/* 127:    */        }
/* 128:    */        else
/* 129:    */        {
/* 130:130 */          resp.setType(0);
/* 131:131 */          resp.setMessage("发生错误，系统找不到此合同信息，请与管理员联系。");
/* 132:132 */          return resp;
/* 133:    */        }
/* 134:    */      }
/* 135:    */      else
/* 136:    */      {
/* 137:137 */        resp.setType(0);
/* 138:138 */        resp.setMessage("发生错误，系统找不到此合同信息，请与管理员联系。");
/* 139:139 */        return resp;
/* 140:    */      }
/* 141:    */    }
/* 142:    */    catch (Exception e)
/* 143:    */    {
/* 144:144 */      resp.setType(0);
/* 145:145 */      resp.setMessage("保存错误，错误原因：" + e.getMessage());
/* 146:    */    }
/* 147:147 */    return resp;
/* 148:    */  }
/* 149:    */  
/* 150:    */  public ResponseEntity exportData(RequestEntity req)
/* 151:    */    throws IOException, WriteException
/* 152:    */  {
/* 153:153 */    PMF pmf = RemoteUtil.getPMF(req);
/* 154:154 */    ResponseEntity resp = new ResponseEntity();
/* 155:    */    try
/* 156:    */    {
/* 157:157 */      Map val = (Map)req.getData().get(0);
/* 158:158 */      OutputStream os = (OutputStream)val.get("filestream");
/* 159:    */      
/* 160:160 */      StringBuffer sql = new StringBuffer();
/* 161:161 */      sql.append("SELECT b.`order_date`,b.`delivery_date`,b.`number`,e.`unit`,e.`nationality`,e.`clientfrom`,");
/* 162:162 */      sql.append("b.`name`,c.`amount`,a.`earnest`,a.`balance`,a.`shipping`,a.`actual_receipts`,a.`fee`,b.`payment`,");
/* 163:163 */      sql.append("a.`pay_fees`,a.`agency_fees`,a.`billing_fee`,a.`drawback`,");
/* 164:164 */      sql.append("(select coding_value from sys_encoding where field_name='financialstatementstatus' and field_value=a.`status`) status,");
/* 165:165 */      sql.append("d.`realname`,b.makepoint ");
/* 166:166 */      sql.append("FROM financial_statements a,contract b left join vw_contract_amount c on (b.id=c.id) ,sys_user d,`client` e ");
/* 167:167 */      sql.append("WHERE a.`contract_id`=b.`id` AND a.`userid`=d.`uid` AND b.`buyer_id`=e.`id` ");
/* 168:168 */      sql.append(" order by b.id desc ");
/* 169:    */      
/* 170:170 */      String[] title = 
/* 171:171 */        { "合同时间", "发货时间", "合同编号", "客户", "国籍", "客户来源", "品名", "合同金额", "定金", 
/* 172:172 */        "余款", "运费", "实际收款", "RMB汇总", "出口方式", "买单费", "代理费", "开票费", 
/* 173:173 */        "退税", "状态", "业务员", "提成点" };
/* 174:    */      
/* 175:175 */      List<Object[]> datas = pmf.get(sql.toString());
/* 176:    */      
/* 177:177 */      ExcelUtil.exportExcel(os, "sheet", title, datas);
/* 178:    */    }
/* 179:    */    catch (Exception e)
/* 180:    */    {
/* 181:181 */      resp.setType(0);
/* 182:182 */      resp.setMessage("导出失败");
/* 183:    */    }
/* 184:    */    
/* 185:185 */    return resp;
/* 186:    */  }
/* 187:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.FinancialStatementsService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */