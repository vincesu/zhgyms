/*   1:    */package com.vincesu.persistence;
/*   2:    */
/*   3:    */import java.io.PrintStream;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.math.BigInteger;
/*   6:    */import java.util.Iterator;
/*   7:    */import java.util.List;
/*   8:    */import java.util.Map;
/*   9:    */import java.util.Set;
/*  10:    */import org.hibernate.Query;
/*  11:    */import org.hibernate.SQLQuery;
/*  12:    */import org.hibernate.Session;
/*  13:    */import org.hibernate.jdbc.Work;
/*  14:    */
/*  15:    */public class PMF
/*  16:    */{
/*  17: 17 */  private Session session = null;
/*  18:    */  
/*  19:    */  public PMF() {
/*  20: 20 */    this.session = SessionFactoryHelper.getSession();
/*  21:    */  }
/*  22:    */  
/*  23:    */  public PMF(Session s) {
/*  24: 24 */    this.session = s;
/*  25:    */  }
/*  26:    */  
/*  27:    */  public Session getSession() {
/*  28: 28 */    return this.session;
/*  29:    */  }
/*  30:    */  
/*  31:    */  public void flush() {
/*  32: 32 */    this.session.flush();
/*  33:    */  }
/*  34:    */  
/*  35:    */  public List get(String sql, Class[] entityClasses)
/*  36:    */  {
/*  37: 37 */    List result = null;
/*  38: 38 */    SQLQuery query = null;
/*  39:    */    try
/*  40:    */    {
/*  41: 41 */      query = this.session.createSQLQuery(sql);
/*  42: 42 */      if (entityClasses != null)
/*  43:    */      {
/*  44: 44 */        query = this.session.createSQLQuery(sql);
/*  45: 45 */        for (Class c : entityClasses)
/*  46:    */        {
/*  47: 47 */          query.addEntity(c);
/*  48:    */        }
/*  49:    */      }
/*  50:    */      
/*  51: 51 */      result = query.list();
/*  52:    */    }
/*  53:    */    catch (Exception e)
/*  54:    */    {
/*  55: 55 */      e.printStackTrace();
/*  56:    */    }
/*  57: 57 */    return result;
/*  58:    */  }
/*  59:    */  
/*  60:    */  public List get(String sql, Class[] entityClasses, String[] alias)
/*  61:    */  {
/*  62: 62 */    List result = null;
/*  63: 63 */    SQLQuery query = null;
/*  64:    */    try
/*  65:    */    {
/*  66: 66 */      query = this.session.createSQLQuery(sql);
/*  67: 67 */      if (entityClasses != null)
/*  68:    */      {
/*  69: 69 */        query = this.session.createSQLQuery(sql);
/*  70: 70 */        for (int i = 0; i < entityClasses.length; i++)
/*  71:    */        {
/*  72: 72 */          query.addEntity(alias[i], entityClasses[i]);
/*  73:    */        }
/*  74:    */      }
/*  75:    */      
/*  76: 76 */      result = query.list();
/*  77:    */    }
/*  78:    */    catch (Exception e)
/*  79:    */    {
/*  80: 80 */      e.printStackTrace();
/*  81:    */    }
/*  82: 82 */    return result;
/*  83:    */  }
/*  84:    */  
/*  85:    */  public List get(String sql)
/*  86:    */  {
/*  87: 87 */    return get(sql, null);
/*  88:    */  }
/*  89:    */  
/*  90:    */  public Object get(Class c, Object primaryKey)
/*  91:    */  {
/*  92: 92 */    Object result = null;
/*  93:    */    try
/*  94:    */    {
/*  95: 95 */      return this.session.get(c, (Serializable)primaryKey);
/*  96:    */    }
/*  97:    */    catch (Exception localException) {}
/*  98:    */    
/*  99: 99 */    return null;
/* 100:    */  }
/* 101:    */  
/* 102:    */  public <T> void save(T entity)
/* 103:    */  {
/* 104:    */    try
/* 105:    */    {
/* 106:106 */      this.session.save(entity);
/* 107:107 */      this.session.flush();
/* 108:    */    } catch (Exception e) {
/* 109:109 */      e.printStackTrace();
/* 110:    */    }
/* 111:    */  }
/* 112:    */  
/* 113:    */  public <T> void update(T entity)
/* 114:    */  {
/* 115:    */    try
/* 116:    */    {
/* 117:117 */      this.session.update(entity);
/* 118:118 */      this.session.flush();
/* 119:    */    } catch (Exception e) {
/* 120:120 */      e.printStackTrace();
/* 121:    */    }
/* 122:    */  }
/* 123:    */  
/* 124:    */  public <T> void restore(T entity)
/* 125:    */  {
/* 126:    */    try
/* 127:    */    {
/* 128:128 */      this.session.saveOrUpdate(entity);
/* 129:129 */      this.session.flush();
/* 130:    */    } catch (Exception e) {
/* 131:131 */      e.printStackTrace();
/* 132:    */    }
/* 133:    */  }
/* 134:    */  
/* 135:    */  public <T> void remove(T entity)
/* 136:    */  {
/* 137:    */    try
/* 138:    */    {
/* 139:139 */      this.session.clear();
/* 140:140 */      this.session.delete(entity);
/* 141:141 */      this.session.flush();
/* 142:    */    }
/* 143:    */    catch (Exception e) {
/* 144:144 */      e.printStackTrace();
/* 145:    */    }
/* 146:    */  }
/* 147:    */  
/* 148:    */  public <T> List<T> list(String hql)
/* 149:    */  {
/* 150:150 */    Query query = this.session.createQuery(hql);
/* 151:151 */    return query.list();
/* 152:    */  }
/* 153:    */  
/* 154:    */  public <T> List<T> list(String hql, Map<Object, Object> map)
/* 155:    */  {
/* 156:156 */    Query query = this.session.createQuery(hql);
/* 157:157 */    Set<Object> key = map.keySet();
/* 158:158 */    for (Iterator it = key.iterator(); it.hasNext();)
/* 159:    */    {
/* 160:160 */      String s = (String)it.next();
/* 161:161 */      query.setParameter(s, map.get(s));
/* 162:    */    }
/* 163:163 */    return query.list();
/* 164:    */  }
/* 165:    */  
/* 166:    */  public <T> List<T> list(String hql, Map<String, Object> map, int start, int count)
/* 167:    */  {
/* 168:168 */    Query query = this.session.createQuery(hql);
/* 169:169 */    if (map != null)
/* 170:    */    {
/* 171:171 */      Set<String> key = map.keySet();
/* 172:172 */      for (Iterator it = key.iterator(); it.hasNext();)
/* 173:    */      {
/* 174:174 */        String s = (String)it.next();
/* 175:175 */        query.setParameter(s, map.get(s));
/* 176:    */      }
/* 177:    */    }
/* 178:178 */    query.setFirstResult(start).setMaxResults(count);
/* 179:179 */    return query.list();
/* 180:    */  }
/* 181:    */  
/* 182:    */  public long count(String sql)
/* 183:    */  {
/* 184:184 */    StringBuffer queryStr = new StringBuffer();
/* 185:185 */    queryStr.append("select count(1) from (").append(sql).append(") t");
/* 186:186 */    Query query = this.session.createSQLQuery(new String(queryStr));
/* 187:187 */    List d = query.list();
/* 188:188 */    return ((BigInteger)d.get(0)).longValue();
/* 189:    */  }
/* 190:    */  
/* 191:    */  public void doWorkWithConnection(Work work)
/* 192:    */  {
/* 193:    */    try
/* 194:    */    {
/* 195:195 */      this.session.doWork(work);
/* 196:    */    }
/* 197:    */    catch (Exception es)
/* 198:    */    {
/* 199:199 */      System.out.println(es.getMessage());
/* 200:    */    }
/* 201:    */  }
/* 202:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.persistence.PMF
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */
