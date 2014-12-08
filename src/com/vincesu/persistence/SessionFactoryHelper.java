/*  1:   */package com.vincesu.persistence;
/*  2:   */
/*  3:   */import com.vincesu.framework.util.TimeUtil;
/*  4:   */import java.io.PrintStream;
/*  5:   */import org.hibernate.HibernateException;
/*  6:   */import org.hibernate.Session;
/*  7:   */import org.hibernate.SessionFactory;
/*  8:   */import org.hibernate.cfg.Configuration;
/*  9:   */import org.hibernate.service.ServiceRegistry;
/* 10:   */import org.hibernate.service.ServiceRegistryBuilder;
/* 11:   */
/* 12:   */public class SessionFactoryHelper
/* 13:   */{
/* 14:   */  private static SessionFactory sessionFactory;
/* 15:   */  private static ServiceRegistry serviceRegistry;
/* 16:16 */  private static ThreadLocal session = new ThreadLocal();
/* 17:   */  
/* 18:   */  static
/* 19:   */  {
/* 20:   */    try
/* 21:   */    {
/* 22:22 */      initFactory();
/* 23:   */      
/* 24:24 */      TimeUtil.init();
/* 25:   */    }
/* 26:   */    catch (Throwable e) {
/* 27:27 */      System.err.println("Error in creating SessionFactory object." + 
/* 28:28 */        e.getMessage());
/* 29:29 */      throw new ExceptionInInitializerError(e);
/* 30:   */    }
/* 31:   */  }
/* 32:   */  
/* 33:   */  public static void initFactory()
/* 34:   */  {
/* 35:35 */    Configuration configuration = new Configuration();
/* 36:36 */    configuration.configure();
/* 37:37 */    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
/* 38:38 */    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
/* 39:   */  }
/* 40:   */  
/* 41:   */  public static SessionFactory getSessionFactory()
/* 42:   */  {
/* 43:43 */    return sessionFactory;
/* 44:   */  }
/* 45:   */  
/* 46:   */  public static Session getSession()
/* 47:   */    throws HibernateException
/* 48:   */  {
/* 49:49 */    Session s = (Session)session.get();
/* 50:50 */    if ((s == null) || (!s.isOpen()))
/* 51:   */    {
/* 52:52 */      s = getSessionFactory().openSession();
/* 53:53 */      session.set(s);
/* 54:   */    }
/* 55:55 */    return s;
/* 56:   */  }
/* 57:   */  
/* 58:   */  public static void closeSession() throws HibernateException
/* 59:   */  {
/* 60:60 */    Session s = (Session)session.get();
/* 61:61 */    session.set(null);
/* 62:62 */    if (s != null)
/* 63:   */    {
/* 64:64 */      s.close();
/* 65:   */    }
/* 66:   */  }
/* 67:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.persistence.SessionFactoryHelper
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */