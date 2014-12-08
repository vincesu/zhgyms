/*  1:   */package com.vincesu.framework.util;
/*  2:   */
/*  3:   */import java.awt.image.BufferedImage;
/*  4:   */import java.io.File;
/*  5:   */import javax.imageio.ImageIO;
/*  6:   */
/*  8:   */public class ImageUtil
/*  9:   */{
/* 10:   */  public static final String IMAGE_TYPE_JPG = "jpg";
/* 11:   */  public static final String IMAGE_TYPE_PNG = "png";
/* 12:   */  
/* 13:   */  public static void convert(File source, File target, String type)
/* 14:   */    throws Exception
/* 15:   */  {
/* 16:16 */    if (source.canRead())
/* 17:   */    {
/* 18:   */      try
/* 19:   */      {
/* 20:20 */        BufferedImage src = ImageIO.read(source);
/* 21:21 */        ImageIO.write(src, type, target);
/* 22:   */      }
/* 23:   */      catch (Throwable t)
/* 24:   */      {
/* 25:25 */        throw new Exception("转换图片格式失败");
/* 26:   */      }
/* 27:   */      
/* 28:   */    }
/* 29:   */    else {
/* 30:30 */      throw new Exception("源文件不可读！路径：" + source.getAbsolutePath());
/* 31:   */    }
/* 32:   */  }
/* 33:   */  
/* 34:   */  public static void convert(String source, String target, String type) throws Exception
/* 35:   */  {
/* 36:36 */    File s = new File(source);File t = new File(target);
/* 37:37 */    convert(s, t, type);
/* 38:   */  }
/* 39:   */  
/* 40:   */  public static void convert(File source, String target, String type) throws Exception
/* 41:   */  {
/* 42:42 */    File t = new File(target);
/* 43:43 */    convert(source, t, type);
/* 44:   */  }
/* 45:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.ImageUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */