/*  1:   */package com.vincesu.framework.model;
/*  2:   */
/*  3:   */import com.vincesu.persistence.PMF;
/*  4:   */import com.vincesu.persistence.SessionFactoryHelper;
/*  5:   */import java.util.List;
/*  6:   */import org.hibernate.Query;
/*  7:   */import org.hibernate.Session;
/*  8:   */
/* 12:   */public abstract class AbstractModel<T>
/* 13:   */{
/* 14:14 */  protected Class<T> entityClass = null;
/* 15:15 */  protected PMF pmf = null;
/* 16:   */  
/* 17:   */  public AbstractModel(PMF p)
/* 18:   */  {
/* 19:19 */    this.pmf = p;
/* 20:20 */    this.entityClass = ((Class)((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
/* 21:   */  }
/* 22:   */  
/* 23:   */  public void save(T entity)
/* 24:   */  {
/* 25:25 */    this.pmf.save(entity);
/* 26:   */  }
/* 27:   */  
/* 28:   */  public void restore(T entity)
/* 29:   */  {
/* 30:30 */    this.pmf.restore(entity);
/* 31:   */  }
/* 32:   */  
/* 33:   */  public void modify(T entity)
/* 34:   */  {
/* 35:35 */    this.pmf.update(entity);
/* 36:   */  }
/* 37:   */  
/* 38:   */  public void remove(T entity)
/* 39:   */  {
/* 40:40 */    this.pmf.remove(entity);
/* 41:   */  }
/* 42:   */  
/* 43:   */  public Long count()
/* 44:   */  {
/* 45:45 */    String hql = "select count(*) from " + this.entityClass.getName();
/* 46:46 */    Query query = SessionFactoryHelper.getSession().createQuery(hql);
/* 47:47 */    List d = query.list();
/* 48:48 */    return (Long)d.get(0);
/* 49:   */  }
/* 50:   */  
/* 51:   */  public List<T> list()
/* 52:   */  {
/* 53:53 */    String hql = "from " + this.entityClass.getName();
/* 54:54 */    Query query = SessionFactoryHelper.getSession().createQuery(hql);
/* 55:55 */    return query.list();
/* 56:   */  }
/* 57:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.model.AbstractModel
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */