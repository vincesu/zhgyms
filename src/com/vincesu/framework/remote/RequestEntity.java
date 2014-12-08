/*   1:    */package com.vincesu.framework.remote;
/*   2:    */
/*   3:    */import com.vincesu.framework.entity.SysPermissions;
/*   4:    */import com.vincesu.framework.entity.SysRole;
/*   5:    */import com.vincesu.framework.entity.SysUser;
/*   6:    */import com.vincesu.framework.exception.LoginInTimeOutException;
/*   7:    */import com.vincesu.framework.model.EncodingModel;
/*   8:    */import com.vincesu.persistence.PMF;
/*   9:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  10:    */import java.util.List;
/*  11:    */import java.util.Map;
/*  12:    */import javax.servlet.http.HttpSession;
/*  13:    */
/*  17:    */public class RequestEntity
/*  18:    */{
/*  19: 19 */  protected HttpSession session = null;
/*  20:    */  
/*  21: 21 */  private List<Map<Object, Object>> data = null;
/*  22:    */  
/*  23:    */  public static final String timeoutMsg = "登录验证已过期，请重新登陆";
/*  24:    */  
/*  25:    */  public RequestEntity(HttpSession s)
/*  26:    */  {
/*  27: 27 */    this.session = s;
/*  28:    */  }
/*  29:    */  
/*  30:    */  public void setData(List data) {
/*  31: 31 */    this.data = data;
/*  32:    */  }
/*  33:    */  
/*  34:    */  public List getData() {
/*  35: 35 */    return this.data;
/*  36:    */  }
/*  37:    */  
/*  38:    */  public SysRole getRole() throws Exception {
/*  39: 39 */    return (SysRole)getSessionAttribute("role");
/*  40:    */  }
/*  41:    */  
/*  42:    */  public void setRole(SysRole role) throws Exception {
/*  43: 43 */    setSessionAttribute("role", role);
/*  44:    */  }
/*  45:    */  
/*  46:    */  public SysUser getUser() throws Exception {
/*  47: 47 */    return (SysUser)getSessionAttribute("user");
/*  48:    */  }
/*  49:    */  
/*  50:    */  public void removeUser() throws Exception {
/*  51: 51 */    removeSessionAttribute("user");
/*  52:    */  }
/*  53:    */  
/*  54:    */  public void setUser(SysUser user) throws Exception {
/*  55: 55 */    setSessionAttribute("user", user);
/*  56:    */  }
/*  57:    */  
/*  58:    */  public List<SysPermissions> getPermissions() throws Exception {
/*  59: 59 */    return (List)getSessionAttribute("permissions");
/*  60:    */  }
/*  61:    */  
/*  62:    */  public void setPermissions(List<SysPermissions> permissions) throws Exception {
/*  63: 63 */    setSessionAttribute("permissions", permissions);
/*  64:    */  }
/*  65:    */  
/*  66:    */  public void setSessionAttribute(String key, Object value) throws Exception
/*  67:    */  {
/*  68: 68 */    if (this.session == null)
/*  69: 69 */      throw new LoginInTimeOutException("登录验证已过期，请重新登陆");
/*  70: 70 */    this.session.setAttribute(key, value);
/*  71:    */  }
/*  72:    */  
/*  73:    */  public Object getSessionAttribute(String key) throws Exception
/*  74:    */  {
/*  75: 75 */    if (this.session == null)
/*  76: 76 */      throw new LoginInTimeOutException("登录验证已过期，请重新登陆");
/*  77: 77 */    Object result = this.session.getAttribute(key);
/*  78: 78 */    if (result == null) {
/*  79: 79 */      throw new LoginInTimeOutException("登录验证已过期，请重新登陆");
/*  80:    */    }
/*  81: 81 */    return result;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public void removeSessionAttribute(String key) throws Exception
/*  85:    */  {
/*  86: 86 */    if (this.session == null)
/*  87: 87 */      throw new LoginInTimeOutException("登录验证已过期，请重新登陆");
/*  88: 88 */    this.session.removeAttribute(key);
/*  89:    */  }
/*  90:    */  
/*  91:    */  public HttpSession getSession() {
/*  92: 92 */    return this.session;
/*  93:    */  }
/*  94:    */  
/*  95:    */  public void setSession(HttpSession session) {
/*  96: 96 */    this.session = session;
/*  97:    */  }
/*  98:    */  
/*  99:    */  public void setEncodingTable(EncodingModel value) throws Exception
/* 100:    */  {
/* 101:101 */    setSessionAttribute("encoding", value);
/* 102:    */  }
/* 103:    */  
/* 104:    */  public EncodingModel getEncodingTable() throws Exception
/* 105:    */  {
/* 106:106 */    PMF pmf = new PMF(SessionFactoryHelper.getSession());
/* 107:107 */    EncodingModel em = null;
/* 108:    */    try
/* 109:    */    {
/* 110:110 */      em = (EncodingModel)getSessionAttribute("encoding");
/* 111:    */    }
/* 112:    */    catch (LoginInTimeOutException localLoginInTimeOutException) {}
/* 113:113 */    if (em == null)
/* 114:114 */      setEncodingTable(new EncodingModel(pmf));
/* 115:115 */    return (EncodingModel)getSessionAttribute("encoding");
/* 116:    */  }
/* 117:    */  
/* 118:    */  public void checkTimeout() throws LoginInTimeOutException
/* 119:    */  {
/* 120:120 */    if (this.session == null)
/* 121:121 */      throw new LoginInTimeOutException("登录验证已过期，请重新登陆");
/* 122:122 */    Object result = this.session.getAttribute("user");
/* 123:123 */    if (result == null) {
/* 124:124 */      throw new LoginInTimeOutException("登录验证已过期，请重新登陆");
/* 125:    */    }
/* 126:    */  }
/* 127:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.remote.RequestEntity
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */