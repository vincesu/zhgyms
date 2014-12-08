/*   1:    */package com.vincesu.framework.util;
/*   2:    */
/*   6:    */public class TextUtil
/*   7:    */{
/*   8:  8 */  private int chCharacter = 0;
/*   9:    */  
/*  11: 11 */  private int enCharacter = 0;
/*  12:    */  
/*  14: 14 */  private int spaceCharacter = 0;
/*  15:    */  
/*  17: 17 */  private int numberCharacter = 0;
/*  18:    */  
/*  20: 20 */  private int otherCharacter = 0;
/*  21:    */  
/*  23:    */  public static final int TYPE_CELL_WIDTH = 0;
/*  24:    */  
/*  26:    */  public static final int TYPE_CELL_HEIGHT = 1;
/*  27:    */  
/*  29:    */  public static final int TYPE_PIX = 2;
/*  30:    */  
/*  32: 32 */  private static final TextUtil deity = new TextUtil();
/*  33:    */  
/*  34:    */  public TextUtil(String str) {
/*  35: 35 */    countString(str);
/*  36:    */  }
/*  37:    */  
/*  38:    */  public TextUtil() {}
/*  39:    */  
/*  40:    */  public static int count(String text, String target)
/*  41:    */  {
/*  42: 42 */    int result = 0;
/*  43: 43 */    int index = text.indexOf(target);
/*  44: 44 */    while (index >= 0)
/*  45:    */    {
/*  46: 46 */      result++;
/*  47: 47 */      index = text.indexOf(target, index + 1);
/*  48:    */    }
/*  49: 49 */    return result;
/*  50:    */  }
/*  51:    */  
/*  59:    */  public static int countRowHeight(String text, int width, boolean isEnglish)
/*  60:    */  {
/*  61: 61 */    if (text == null)
/*  62: 62 */      text = "";
/*  63: 63 */    int result = 1;
/*  64: 64 */    int rule = 1;
/*  65: 65 */    int c = 0;
/*  66: 66 */    if (isEnglish) {
/*  67: 67 */      rule = (int)(width / 1.1956D);
/*  68:    */    } else
/*  69: 69 */      rule = (int)(width / 2.2916D);
/*  70: 70 */    if (isEnglish)
/*  71:    */    {
/*  72: 72 */      for (String str : text.split("\n"))
/*  73:    */      {
/*  74: 74 */        c = str.length() / rule;
/*  75: 75 */        if (c == 0) c = 1;
/*  76: 76 */        result += c;
/*  77:    */      }
/*  78:    */      
/*  79:    */    }
/*  80:    */    else {
/*  81: 81 */      for (String str : text.split("\n"))
/*  82:    */      {
/*  83: 83 */        if (str.length() % rule == 0)
/*  84:    */        {
/*  85: 85 */          c = str.length() / rule;
/*  86: 86 */          if (c == 0) c = 1;
/*  87: 87 */          result += c;
/*  88:    */        }
/*  89:    */        else
/*  90:    */        {
/*  91: 91 */          c = str.length() / rule;
/*  92: 92 */          if (c == 0) c = 1;
/*  93: 93 */          result += c;
/*  94:    */        }
/*  95:    */      }
/*  96:    */    }
/*  97:    */    
/*  98: 98 */    return result * 295 + 237;
/*  99:    */  }
/* 100:    */  
/* 109:    */  public static double countHeightPercentage(double proportion, int[] cws, double widthProportion, int[] chs)
/* 110:    */  {
/* 111:111 */    int[] _cws = new int[cws.length];
/* 112:112 */    int[] _chs = new int[chs.length];
/* 113:113 */    int width = 0;int height = 0;
/* 114:114 */    int i = 0;
/* 115:    */    
/* 116:116 */    for (i = 0; i < cws.length; i++)
/* 117:    */    {
/* 118:118 */      cws[i] *= 7;
/* 119:    */    }
/* 120:120 */    for (i = 0; i < chs.length; i++)
/* 121:    */    {
/* 122:122 */      chs[i] /= 15;
/* 123:    */    }
/* 124:    */    
/* 125:125 */    i = 0;
/* 126:126 */    while (widthProportion > 1.0D)
/* 127:    */    {
/* 128:128 */      width += _cws[i];
/* 129:129 */      i++;
/* 130:130 */      if (i == _cws.length)
/* 131:    */        break;
/* 132:132 */      widthProportion -= 1.0D;
/* 133:    */    }
/* 134:    */    
/* 135:135 */    if (i < _cws.length)
/* 136:    */    {
/* 137:137 */      width = (int)(width + _cws[i] * widthProportion);
/* 138:    */    }
/* 139:    */    
/* 140:140 */    height = (int)(width * proportion);
/* 141:    */    
/* 142:142 */    return countPercentage(height, _chs);
/* 143:    */  }
/* 144:    */  
/* 153:    */  public static double countWidthPercentage(double proportion, int[] chs, double heightProportion, int[] cws)
/* 154:    */  {
/* 155:155 */    int[] _cws = new int[cws.length];
/* 156:156 */    int[] _chs = new int[chs.length];
/* 157:157 */    int width = 0;int height = 0;
/* 158:158 */    int i = 0;
/* 159:    */    
/* 160:160 */    for (i = 0; i < cws.length; i++)
/* 161:    */    {
/* 162:162 */      cws[i] *= 7;
/* 163:    */    }
/* 164:164 */    for (i = 0; i < chs.length; i++)
/* 165:    */    {
/* 166:166 */      chs[i] /= 15;
/* 167:    */    }
/* 168:    */    
/* 169:169 */    i = 0;
/* 170:170 */    while (heightProportion > 1.0D)
/* 171:    */    {
/* 172:172 */      height += _chs[i];
/* 173:173 */      i++;
/* 174:174 */      if (i == _chs.length)
/* 175:    */        break;
/* 176:176 */      heightProportion -= 1.0D;
/* 177:    */    }
/* 178:    */    
/* 179:179 */    if (i < _chs.length)
/* 180:    */    {
/* 181:181 */      height = (int)(height + _chs[i] * heightProportion);
/* 182:    */    }
/* 183:    */    
/* 184:184 */    width = (int)(height / proportion);
/* 185:    */    
/* 186:186 */    return countPercentage(width, _cws);
/* 187:    */  }
/* 188:    */  
/* 195:    */  private static double countPercentage(int pix, int[] cells)
/* 196:    */  {
/* 197:197 */    double result = 0.0D;
/* 198:198 */    for (int i = 0; i < cells.length; i++)
/* 199:    */    {
/* 200:200 */      if (pix > cells[i])
/* 201:    */      {
/* 202:202 */        result += 1.0D;
/* 203:203 */        pix -= cells[i];
/* 204:    */      }
/* 205:    */      else
/* 206:    */      {
/* 207:207 */        result += pix / cells[i];
/* 208:208 */        break;
/* 209:    */      }
/* 210:    */    }
/* 211:    */    
/* 212:212 */    return result;
/* 213:    */  }
/* 214:    */  
/* 260:    */  public static double convertToPix(double src, int type)
/* 261:    */  {
/* 262:262 */    switch (type)
/* 263:    */    {
/* 264:    */    case 1: 
/* 265:265 */      return src / 15.0D;
/* 266:    */    case 0: 
/* 267:267 */      return src * 7.0D;
/* 268:    */    }
/* 269:    */    
/* 270:    */    
/* 271:271 */    return 0.0D;
/* 272:    */  }
/* 273:    */  
/* 274:    */  public int getChCharacter() {
/* 275:275 */    return this.chCharacter;
/* 276:    */  }
/* 277:    */  
/* 278:    */  public void setChCharacter(int chCharacter) {
/* 279:279 */    this.chCharacter = chCharacter;
/* 280:    */  }
/* 281:    */  
/* 282:    */  public int getEnCharacter() {
/* 283:283 */    return this.enCharacter;
/* 284:    */  }
/* 285:    */  
/* 286:    */  public void setEnCharacter(int enCharacter) {
/* 287:287 */    this.enCharacter = enCharacter;
/* 288:    */  }
/* 289:    */  
/* 290:    */  public int getSpaceCharacter() {
/* 291:291 */    return this.spaceCharacter;
/* 292:    */  }
/* 293:    */  
/* 294:    */  public void setSpaceCharacter(int spaceCharacter) {
/* 295:295 */    this.spaceCharacter = spaceCharacter;
/* 296:    */  }
/* 297:    */  
/* 298:    */  public int getOtherCharacter() {
/* 299:299 */    return this.otherCharacter;
/* 300:    */  }
/* 301:    */  
/* 302:    */  public void setOtherCharacter(int otherCharacter) {
/* 303:303 */    this.otherCharacter = otherCharacter;
/* 304:    */  }
/* 305:    */  
/* 306:    */  public int getNumberCharacter() {
/* 307:307 */    return this.numberCharacter;
/* 308:    */  }
/* 309:    */  
/* 310:    */  public void setNumberCharacter(int numberCharacter) {
/* 311:311 */    this.numberCharacter = numberCharacter;
/* 312:    */  }
/* 313:    */  
/* 317:    */  public void countString(String str)
/* 318:    */  {
/* 319:319 */    if ((str == null) || (str.equals(""))) {
/* 320:320 */      return;
/* 321:    */    }
/* 322:    */    
/* 323:323 */    for (int i = 0; i < str.length(); i++) {
/* 324:324 */      char tmp = str.charAt(i);
/* 325:325 */      if (((tmp >= 'A') && (tmp <= 'Z')) || ((tmp >= 'a') && (tmp <= 'z'))) {
/* 326:326 */        this.enCharacter += 1;
/* 327:327 */      } else if ((tmp >= '0') && (tmp <= '9')) {
/* 328:328 */        this.numberCharacter += 1;
/* 329:329 */      } else if (tmp == ' ') {
/* 330:330 */        this.spaceCharacter += 1;
/* 331:331 */      } else if (isChinese(tmp)) {
/* 332:332 */        this.chCharacter += 1;
/* 333:    */      } else {
/* 334:334 */        this.otherCharacter += 1;
/* 335:    */      }
/* 336:    */    }
/* 337:    */  }
/* 338:    */  
/* 344:    */  private boolean isChinese(char ch)
/* 345:    */  {
/* 346:346 */    Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
/* 347:    */    
/* 350:350 */    if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || 
/* 351:351 */      (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) || 
/* 352:352 */      (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) || 
/* 353:353 */      (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)) {
/* 354:354 */      return true;
/* 355:    */    }
/* 356:356 */    return false;
/* 357:    */  }
/* 358:    */  
/* 359:    */  private class Position
/* 360:    */  {
/* 361:361 */    public double x = 0.0D;
/* 362:362 */    public double y = 0.0D;
/* 363:    */    
/* 364:    */    public Position(double x, double y)
/* 365:    */    {
/* 366:366 */      this.x = x;
/* 367:367 */      this.y = y;
/* 368:    */    }
/* 369:    */  }
/* 370:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.util.TextUtil
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */