/*  1:   */package com.vincesu.business.model;
/*  2:   */
/*  3:   */import com.sun.star.uno.Exception;
/*  4:   */import com.vincesu.business.entity.Client;
/*  5:   */import com.vincesu.business.entity.Contract;
/*  6:   */import com.vincesu.business.entity.Files;
/*  7:   */import com.vincesu.framework.entity.SysUser;
/*  8:   */import com.vincesu.persistence.PMF;
/*  9:   */import java.util.List;
/* 10:   */
/* 11:   */public class ContractModel
/* 12:   */{
/* 13:   */  public static String removeLockStatus(String reserved)
/* 14:   */  {
/* 15:15 */    String target = new String(reserved);
/* 16:16 */    while (target.length() > 0)
/* 17:   */    {
/* 18:18 */      if ((target.charAt(0) >= '0') && (target.charAt(0) <= '9'))
/* 19:   */        break;
/* 20:20 */      target = target.substring(1);
/* 21:   */    }
/* 22:22 */    return target;
/* 23:   */  }
/* 24:   */  
/* 25:   */  public static void changeSalesman(Contract contract, SysUser newSalesman) throws Exception
/* 26:   */  {
/* 27:27 */    if ((contract == null) || (newSalesman == null) || 
/* 28:28 */      (contract.getReserved() == null) || (newSalesman.getReserved() == null)) {
/* 29:29 */      throw new Exception("入参有误");
/* 30:   */    }
/* 31:31 */    String reserved = removeLockStatus(contract.getReserved());
/* 32:32 */    contract.setReserved(contract.getReserved().replace(reserved, newSalesman.getReserved()));
/* 33:33 */    contract.setSaleman(newSalesman.getId());
/* 34:   */  }
/* 35:   */  
/* 36:   */  public static void chanageSalesman(PMF pmf, Client client, SysUser oldSalesman, SysUser newSalesman)
/* 37:   */    throws Exception
/* 38:   */  {
/* 39:39 */    StringBuffer hql = new StringBuffer();
/* 40:40 */    hql.append("from Contract a ");
/* 41:41 */    hql.append(" where a.Reserved='");
/* 42:42 */    hql.append("lock").append(oldSalesman.getReserved());
/* 43:43 */    hql.append("' or a.Reserved='");
/* 44:44 */    hql.append(oldSalesman.getReserved());
/* 45:45 */    hql.append("'");
/* 46:46 */    hql.append(" and a.BuyerId=").append(client.getId());
/* 47:   */    
/* 48:48 */    List<Contract> contracts = pmf.list(hql.toString());
/* 49:   */    
/* 50:50 */    for (Contract contract : contracts)
/* 51:   */    {
/* 52:52 */      changeSalesman(contract, newSalesman);
/* 53:   */    }
/* 54:   */  }
/* 55:   */  
/* 56:   */  public void setContractComplete(PMF pmf, Contract contract)
/* 57:   */  {
/* 58:58 */    setContractComplete(pmf, contract, false);
/* 59:   */  }
/* 60:   */  
/* 61:   */  public void setContractComplete(PMF pmf, Contract contract, boolean isNew)
/* 62:   */  {
/* 63:63 */    if (isNew)
/* 64:   */    {
/* 65:65 */      contract.setComplete("0%");
/* 66:   */    }
/* 67:   */    else
/* 68:   */    {
/* 69:69 */      Files file = new Files();
/* 70:70 */      file.getRelatedDescription();
/* 71:71 */      file.getType();
/* 72:72 */      List localList = pmf.list("from Files where RelatedObject=" + contract.getId() + 
/* 73:73 */        " and Type=" + "24");
/* 74:   */    }
/* 75:   */  }
/* 76:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.model.ContractModel
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */