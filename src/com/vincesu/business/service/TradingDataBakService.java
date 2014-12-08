/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Client;
/*   4:    */import com.vincesu.business.entity.Contract;
/*   5:    */import com.vincesu.business.entity.Files;
/*   6:    */import com.vincesu.framework.remote.RemoteUtil;
/*   7:    */import com.vincesu.framework.remote.RequestEntity;
/*   8:    */import com.vincesu.framework.remote.ResponseEntity;
/*   9:    */import com.vincesu.framework.util.PathUtil;
/*  10:    */import com.vincesu.persistence.PMF;
/*  11:    */import java.io.IOException;
/*  12:    */import java.io.OutputStream;
/*  13:    */import java.io.PrintStream;
/*  14:    */import java.util.List;
/*  15:    */import java.util.Map;
/*  16:    */import org.apache.tools.zip.ZipOutputStream;
/*  17:    */
/*  18:    */public class TradingDataBakService
/*  19:    */{
/*  20:    */  public ResponseEntity tradingDataBak(RequestEntity req) throws IOException
/*  21:    */  {
/*  22: 22 */    PMF pmf = RemoteUtil.getPMF(req);
/*  23: 23 */    ResponseEntity resp = new ResponseEntity();
/*  24: 24 */    Map val = (Map)req.getData().get(0);
/*  25: 25 */    OutputStream os = (OutputStream)val.get("filestream");
/*  26: 26 */    String params = val.get("params").toString();
/*  27: 27 */    String bt = null;
/*  28: 28 */    String et = null;
/*  29: 29 */    String[] tempStr = null;
/*  30: 30 */    String[] clientIds = null;
/*  31:    */    try
/*  32:    */    {
/*  33: 33 */      tempStr = params.split("_");
/*  34: 34 */      if (tempStr.length != 3)
/*  35:    */      {
/*  36: 36 */        resp.setType(0);
/*  37: 37 */        resp.setMessage("入参有误");
/*  38: 38 */        return resp;
/*  39:    */      }
/*  40: 40 */      ZipOutputStream zos = new ZipOutputStream(os);
/*  41: 41 */      bt = tempStr[0];
/*  42: 42 */      et = tempStr[1];
/*  43: 43 */      clientIds = tempStr[2].split("-");
/*  44:    */      
/*  45: 45 */      Client client = null;
/*  46: 46 */      for (String id : clientIds)
/*  47:    */      {
/*  48: 48 */        client = (Client)pmf.get(Client.class, new Long(id));
/*  49: 49 */        exportClient(pmf, zos, bt, et, client);
/*  50:    */      }
/*  51:    */      
/*  52: 52 */      zos.flush();
/*  53: 53 */      zos.close();
/*  54:    */    }
/*  55:    */    catch (Exception e)
/*  56:    */    {
/*  57: 57 */      System.out.println(e);
/*  58:    */    }
/*  59: 59 */    return resp;
/*  60:    */  }
/*  61:    */  
/*  62:    */  private void exportClient(PMF pmf, ZipOutputStream zos, String bt, String et, Client client)
/*  63:    */    throws IOException
/*  64:    */  {
/*  65: 65 */    StringBuffer hql = new StringBuffer();
/*  66: 66 */    hql.append("from Contract where DATE_FORMAT(order_date,'%Y%m%d')>='");
/*  67: 67 */    hql.append(bt);
/*  68: 68 */    hql.append("' and DATE_FORMAT(order_date,'%Y%m%d')<='");
/*  69: 69 */    hql.append(et);
/*  70: 70 */    hql.append("' and buyer_id=");
/*  71: 71 */    hql.append(client.getId());
/*  72:    */    
/*  73: 73 */    List<Contract> list = pmf.list(hql.toString());
/*  74:    */    
/*  75: 75 */    for (Contract contract : list)
/*  76:    */    {
/*  77: 77 */      exportContract(pmf, zos, client, contract);
/*  78: 78 */      exportRealtedFiles(pmf, zos, client, contract);
/*  79:    */    }
/*  80:    */  }
/*  81:    */  
/*  82:    */  private void exportContract(PMF pmf, ZipOutputStream zos, Client client, Contract contract)
/*  83:    */    throws IOException
/*  84:    */  {
/*  85: 85 */    List<Files> files = pmf.list("from Files b where b.RelatedObject=" + 
/*  86: 86 */      contract.getId() + 
/*  87: 87 */      " AND b.Type=24 AND b.RelatedDescription='合同'");
/*  88: 88 */    if ((files != null) && (files.size() == 1))
/*  89:    */    {
/*  90: 90 */      exportFiles(
/*  91: 91 */        pmf, 
/*  92: 92 */        zos, 
/*  93: 93 */        (Files)files.get(0), 
/*  94: 94 */        client.getUnit().trim().replaceAll("/", "-") + "/", 
/*  95: 95 */        contract.getNumber().trim().replaceAll("/", "-") + 
/*  96: 96 */        "." + 
/*  97: 97 */        PathUtil.getExtensionName(((Files)files.get(0))
/*  98: 98 */        .getName()));
/*  99:    */    }
/* 100:    */  }
/* 101:    */  
/* 102:    */  private void exportRealtedFiles(PMF pmf, ZipOutputStream zos, Client client, Contract contract)
/* 103:    */    throws IOException
/* 104:    */  {
/* 105:105 */    List<Files> files = pmf.list("from Files b where b.RelatedObject=" + 
/* 106:106 */      contract.getId() + 
/* 107:107 */      " AND b.Type=24 ");
/* 108:108 */    if ((files != null) && (files.size() > 0))
/* 109:    */    {
/* 110:110 */      for (Files f : files)
/* 111:    */      {
/* 112:112 */        exportFiles(pmf, zos, f, client.getUnit().trim()
/* 113:113 */          .replaceAll("/", "-") + 
/* 114:114 */          "/" + 
/* 115:115 */          contract.getNumber().trim().replaceAll("/", "-") + 
/* 116:116 */          "/", 
/* 117:117 */          "(" + 
/* 118:118 */          f.getRelatedDescription() + 
/* 119:119 */          ")" + 
/* 120:120 */          f.getName().trim()
/* 121:121 */          .replaceAll("/", "-"));
/* 122:    */      }
/* 123:    */    }
/* 124:    */  }
/* 125:    */  
/* 126:    */  private void exportFiles(PMF pmf, ZipOutputStream zos, Files file, String path, String filename)
/* 127:    */    throws IOException
/* 128:    */  {
/* 129:129 */    PathUtil.exportFiles(pmf, zos, file.getPath(), path, filename);
/* 130:    */  }
/* 131:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.TradingDataBakService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */