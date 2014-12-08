/*   1:    */package com.vincesu.business.entity.base;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Orders;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Date;
/*   6:    */
/*   7:    */public abstract class BaseOrders
/*   8:    */  implements Serializable
/*   9:    */{
/*  10: 10 */  public static String REF = "Orders";
/*  11: 11 */  public static String PROP_SUM = "Sum";
/*  12: 12 */  public static String PROP_NAME = "Name";
/*  13: 13 */  public static String PROP_DELIVERY_DATE = "DeliveryDate";
/*  14: 14 */  public static String PROP_ORDER_DATE = "OrderDate";
/*  15: 15 */  public static String PROP_BUYER = "Buyer";
/*  16: 16 */  public static String PROP_RESERVED = "Reserved";
/*  17: 17 */  public static String PROP_ID = "Id";
/*  18: 18 */  public static String PROP_SALESMAN = "Salesman";
/*  19: 19 */  public static String PROP_CONTRACT_NUMBER = "ContractNumber";
/*  20:    */  
/*  21:    */  public BaseOrders()
/*  22:    */  {
/*  23: 23 */    initialize();
/*  24:    */  }
/*  25:    */  
/*  26:    */  public BaseOrders(Long id)
/*  27:    */  {
/*  28: 28 */    setId(id);
/*  29: 29 */    initialize();
/*  30:    */  }
/*  31:    */  
/*  32: 32 */  private int hashCode = -2147483648;
/*  33:    */  
/*  34:    */  private Long id;
/*  35:    */  
/*  36:    */  private String buyer;
/*  37:    */  
/*  38:    */  private String contractNumber;
/*  39:    */  
/*  40:    */  private Date deliveryDate;
/*  41:    */  
/*  42:    */  private String name;
/*  43:    */  
/*  44:    */  private Date orderDate;
/*  45:    */  
/*  46:    */  private String reserved;
/*  47:    */  
/*  48:    */  private String salesman;
/*  49:    */  
/*  50:    */  private String sum;
/*  51:    */  
/*  52:    */  protected void initialize() {}
/*  53:    */  
/*  54:    */  public Long getId()
/*  55:    */  {
/*  56: 56 */    return this.id;
/*  57:    */  }
/*  58:    */  
/*  59:    */  public void setId(Long id)
/*  60:    */  {
/*  61: 61 */    this.id = id;
/*  62: 62 */    this.hashCode = -2147483648;
/*  63:    */  }
/*  64:    */  
/*  65:    */  public String getBuyer()
/*  66:    */  {
/*  67: 67 */    return this.buyer;
/*  68:    */  }
/*  69:    */  
/*  70:    */  public void setBuyer(String buyer)
/*  71:    */  {
/*  72: 72 */    this.buyer = buyer;
/*  73:    */  }
/*  74:    */  
/*  75:    */  public String getContractNumber()
/*  76:    */  {
/*  77: 77 */    return this.contractNumber;
/*  78:    */  }
/*  79:    */  
/*  80:    */  public void setContractNumber(String contractNumber)
/*  81:    */  {
/*  82: 82 */    this.contractNumber = contractNumber;
/*  83:    */  }
/*  84:    */  
/*  85:    */  public Date getDeliveryDate()
/*  86:    */  {
/*  87: 87 */    return this.deliveryDate;
/*  88:    */  }
/*  89:    */  
/*  90:    */  public void setDeliveryDate(Date deliveryDate)
/*  91:    */  {
/*  92: 92 */    this.deliveryDate = deliveryDate;
/*  93:    */  }
/*  94:    */  
/*  95:    */  public String getName()
/*  96:    */  {
/*  97: 97 */    return this.name;
/*  98:    */  }
/*  99:    */  
/* 100:    */  public void setName(String name)
/* 101:    */  {
/* 102:102 */    this.name = name;
/* 103:    */  }
/* 104:    */  
/* 105:    */  public Date getOrderDate()
/* 106:    */  {
/* 107:107 */    return this.orderDate;
/* 108:    */  }
/* 109:    */  
/* 110:    */  public void setOrderDate(Date orderDate)
/* 111:    */  {
/* 112:112 */    this.orderDate = orderDate;
/* 113:    */  }
/* 114:    */  
/* 115:    */  public String getReserved()
/* 116:    */  {
/* 117:117 */    return this.reserved;
/* 118:    */  }
/* 119:    */  
/* 120:    */  public void setReserved(String reserved)
/* 121:    */  {
/* 122:122 */    this.reserved = reserved;
/* 123:    */  }
/* 124:    */  
/* 125:    */  public String getSalesman()
/* 126:    */  {
/* 127:127 */    return this.salesman;
/* 128:    */  }
/* 129:    */  
/* 130:    */  public void setSalesman(String salesman)
/* 131:    */  {
/* 132:132 */    this.salesman = salesman;
/* 133:    */  }
/* 134:    */  
/* 135:    */  public String getSum()
/* 136:    */  {
/* 137:137 */    return this.sum;
/* 138:    */  }
/* 139:    */  
/* 140:    */  public void setSum(String sum)
/* 141:    */  {
/* 142:142 */    this.sum = sum;
/* 143:    */  }
/* 144:    */  
/* 145:    */  public boolean equals(Object obj)
/* 146:    */  {
/* 147:147 */    if (obj == null) return false;
/* 148:148 */    if (!(obj instanceof Orders)) { return false;
/* 149:    */    }
/* 150:150 */    Orders orders = (Orders)obj;
/* 151:151 */    if ((getId() == null) || (orders.getId() == null)) return false;
/* 152:152 */    return getId().equals(orders.getId());
/* 153:    */  }
/* 154:    */  
/* 155:    */  public int hashCode()
/* 156:    */  {
/* 157:157 */    if (-2147483648 == this.hashCode) {
/* 158:158 */      if (getId() == null) { return super.hashCode();
/* 159:    */      }
/* 160:160 */      String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 161:161 */      this.hashCode = hashStr.hashCode();
/* 162:    */    }
/* 163:    */    
/* 164:164 */    return this.hashCode;
/* 165:    */  }
/* 166:    */  
/* 167:    */  public String toString()
/* 168:    */  {
/* 169:169 */    return super.toString();
/* 170:    */  }
/* 171:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.entity.base.BaseOrders
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */