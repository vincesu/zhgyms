/*  1:   */package com.vincesu.framework.util;
/*  2:   */
/*  3:   */import java.util.Date;
/*  4:   */import java.util.Properties;

/*  5:   */import javax.mail.Address;
/*  6:   */import javax.mail.Authenticator;
import javax.mail.Message;
/*  7:   */import javax.mail.Message.RecipientType;
/*  8:   */import javax.mail.PasswordAuthentication;
/*  9:   */import javax.mail.Session;
/* 10:   */import javax.mail.Transport;
/* 11:   */import javax.mail.internet.InternetAddress;
/* 12:   */import javax.mail.internet.MimeMessage;
/* 13:   */
/* 15:   */public class MailUtil
/* 16:   */{
/* 17:17 */  private String username = null;
/* 18:   */  
/* 19:19 */  private String password = null;
/* 20:   */  
/* 21:21 */  private String host = null;
/* 22:   */  
/* 23:23 */  private String mail_from = null;
/* 24:   */  
/* 25:25 */  private String personalName = null;
/* 26:   */  
/* 27:   */  public MailUtil(String username, String pwd, String host, String personalName, String mail_from)
/* 28:   */  {
/* 29:29 */    this.username = username;
/* 30:30 */    this.password = pwd;
/* 31:31 */    this.host = host;
/* 32:32 */    this.personalName = personalName;
/* 33:33 */    this.mail_from = mail_from;
/* 34:   */  }
/* 35:   */  
/* 40:   */  public void send(String mailHeadName, String mailHeadValue, String mailTo, String mailSubject, String mailBody)
/* 41:   */    throws Exception
/* 42:   */  {
/* 43:   */    try
/* 44:   */    {
/* 45:45 */      Properties props = new Properties();
/* 46:46 */      Authenticator auth = getAuthenticator();
/* 47:47 */      props.put("mail.smtp.host", this.host);
/* 48:48 */      props.put("mail.smtp.auth", "true");
/* 49:49 */      Session session = Session.getDefaultInstance(props, auth);
/* 50:   */      
/* 51:51 */      MimeMessage message = new MimeMessage(session);
/* 52:   */      
/* 53:53 */      message.setSubject(mailSubject);
/* 54:54 */      message.setText(mailBody);
/* 55:55 */      message.setHeader(mailHeadName, mailHeadValue);
/* 56:56 */      message.setSentDate(new Date());
/* 57:57 */      Address address = new InternetAddress(this.mail_from, this.personalName);
/* 58:58 */      message.setFrom(address);
/* 59:59 */      Address toAddress = new InternetAddress(mailTo);
/* 60:60 */      message.addRecipient(Message.RecipientType.TO, toAddress);
/* 61:61 */      Transport.send(message);
/* 62:   */    }
/* 63:   */    catch (Exception ex)
/* 64:   */    {
/* 65:65 */      ex.printStackTrace();
/* 66:66 */      throw new Exception(ex.getMessage());
/* 67:   */    }
/* 68:   */  }
/* 69:   */  
/* 70:   */  public Authenticator getAuthenticator()
/* 71:   */  {
/* 72:72 */    return new Authenticator()
/* 73:   */    {
/* 74:   */      public PasswordAuthentication getPasswordAuthentication()
/* 75:   */      {
/* 76:76 */        return new PasswordAuthentication(MailUtil.this.username, MailUtil.this.password);
/* 77:   */      }
/* 78:   */    };
/* 79:   */  }
/* 80:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.MailUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */