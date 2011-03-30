/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2011 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *  This file is covered by the GNU LESSER GENERAL PUBLIC LICENSE VERSION 3.0    *
 *  A copy of this license is located in file license.txt at the root of this    *
 *  SDK or can be downloaded here:                                               *
 *  http://www.gnu.org/licenses/lgpl-3.0.txt                                     *
 *                                                                               *
 *********************************************************************************/

// $Id: Image4D.java,v 1.42 2011-01-04 13:19:23 guich Exp $

package totalcross.ui.image;

import totalcross.io.*;
import totalcross.sys.*;
import totalcross.ui.*;
import totalcross.ui.gfx.*;
import totalcross.util.zip.*;

public class Image4D extends GfxSurface
{
   public int surfaceType = 1; // don't move from here! must be at static position 0
   protected int width;
   protected int height;
   public int transparentColor = Color.WHITE; // default if the bitmap is monochromatic (WHITE color) - petrus@402_02
   private int frameCount=1;
   private int currentFrame=-1, widthOfAllFrames;
   public boolean useAlpha; // guich@tc126_12
   private int[] pixels; // must be at Object position 0
   protected int[] pixelsOfAllFrames;
   public String comment;
   private Graphics4D gfx;

   public Image4D(int width, int height) throws ImageException
   {
      this.width = width;
      this.height = height;
      try
      {
         pixels = new int[height*width]; // just create the pixels array
      } catch (OutOfMemoryError oome) {throw new ImageException("Out of memory: cannot allocated "+width+"x"+height+" offscreen image.");}
      init();
   }

   public Image4D(String path) throws ImageException
   {
      imageLoad(path);
      if (width == 0)
         throw new ImageException("Could not load image, file not found: "+path);
      init();
   }

   public Image4D(Stream s) throws ImageException, totalcross.io.IOException
   {
      // the buffer must go initially filled so that the native parser can discover if this is a jpeg or png image
      byte[] buf = new byte[512];
      int n = s.readBytes(buf, 0, 4);
      if (n < 4)
         throw new ImageException("Can't read from Stream");
      imageParse(s, buf);
      if (width == 0)
         throw new ImageException("Error when loading image from stream");
      init();
   }

   public Image4D(byte []fullDescription) throws ImageException
   {
      if (fullDescription.length < 4)
         throw new ImageException("Invalid image description");
      ByteArrayStream bas = new ByteArrayStream(fullDescription);
      bas.skipBytes(4); // first 4 bytes are read directly from the fullDescription buffer
      imageParse(bas, fullDescription);
      if (width == 0)
         throw new ImageException("Error when loading image from stream");
      init();
   }

   private void init() throws ImageException
   {
      // frame count information?
      if (comment != null && comment.startsWith("FC="))
         try {setFrameCount(Convert.toInt(comment.substring(3)));} catch (InvalidNumberException ine) {}
      // init the Graphics
      gfx = new Graphics4D(this);
      gfx.refresh(0,0,width,height,0,0,null);
   }

   native private void imageLoad(String path);
   native private void imageParse(totalcross.io.Stream in, byte[] buf);

   public void setFrameCount(int n) throws ImageException
   {
      if (n > 1 && frameCount <= 1)
         try
         {
            frameCount = n;
            comment = "FC="+n;
            if ((width % n) != 0) throw new ImageException("The width must be a multiple of the frame count");
            widthOfAllFrames = width;
            width /= frameCount;
            // the pixels will hold the pixel of a single frame
            pixelsOfAllFrames = pixels;
            pixels = new int[width * height];
            setCurrentFrame(0);
         }
         catch (OutOfMemoryError oome) {throw new ImageException("Not enough memory to create the single frame");}
   }

   public int getFrameCount()
   {
      return frameCount;
   }

   native public void setCurrentFrame(int nr);

   public int getCurrentFrame()
   {
      return currentFrame;
   }

   public void nextFrame()
   {
      if (frameCount > 1)
         setCurrentFrame(currentFrame+1);
   }

   public void prevFrame()
   {
      if (frameCount > 1)
         setCurrentFrame(currentFrame-1);
   }

   public int getHeight()
   {
      return height;
   }

   public int getWidth()
   {
      return width;
   }

   public Graphics4D getGraphics()
   {
      gfx.setFont(MainWindow.getDefaultFont());
      return gfx;
   }

   native public void changeColors(int from, int to);

   public void saveTo(PDBFile cat, String name) throws ImageException, IOException
   {
      name = name.toLowerCase();
      if (!name.endsWith(".png"))
         name += ".png";
      int index = findPosition(cat, name, true);
      if (index == -1) 
         index = cat.getRecordCount();
      ResizeRecord rs = new ResizeRecord(cat,Math.min(65500, width*height*3+200)); // guich@tc114_17: make sure is not bigger than 64k
      DataStream ds = new DataStream(rs);
      rs.startRecord(index);
      ds.writeString(name); // write the name
      createPng(rs);
      rs.endRecord();
   }
   
   public static Image loadFrom(PDBFile cat, String name) throws IOException, ImageException
   {
      name = name.toLowerCase();
      if (!name.endsWith(".png"))
         name += ".png";
      int idx = findPosition(cat, name, false);
      if (idx == -1)
         throw new IOException("The image "+name+" is not inside "+cat.getName());
      
      cat.setRecordPos(idx);
      DataStream ds = new DataStream(cat);
      cat.skipBytes(ds.readUnsignedShort());
      Image img = new Image(cat);
      cat.setRecordPos(-1);
      return img;
   }
   
   private static int findPosition(PDBFile cat, String name, boolean isWrite) throws IOException
   {
      DataStream ds = new DataStream(cat);
      // guich@200b4_45: fixed the insert_in_order routine
      int n = cat.getRecordCount();
      for (int i =0; i < n; i++) // find the correct position to insert the record. the records must be sorted
      {
         cat.setRecordPos(i);
         String recName = ds.readString();
         if (recName.compareTo(name) >= 0) // is recName greater than name
         {
            if (isWrite && name.equals(recName)) // same name? delete it
               cat.deleteRecord();
            return i;
         }
      }
      return -1;
   }
   
   public void createPng(Stream s) throws ImageException, totalcross.io.IOException
   {
      try
      {
         // based in a code from J. David Eisenberg of PngEncoder, version 1.5
         byte[]  pngIdBytes = {(byte)-119, (byte)80, (byte)78, (byte)71, (byte)13, (byte)10, (byte)26, (byte)10};

         CRC32Stream crc = new CRC32Stream(s);
         DataStream ds = new DataStream(crc);

         int w = frameCount > 1 ? this.widthOfAllFrames : this.width;
         int h = this.height;

         ds.writeBytes(pngIdBytes);
         // write the header
         ds.writeInt(13);
         crc.reset();
         ds.writeBytes("IHDR".getBytes());
         ds.writeInt(w);
         ds.writeInt(h);
         ds.writeByte(8); // bit depth of each rgb component
         ds.writeByte(2); // direct model
         ds.writeByte(0); // compression method
         ds.writeByte(0); // filter method
         ds.writeByte(0); // no interlace
         int c = (int)crc.getValue();
         ds.writeInt(c);

         // write transparent pixel information, if any
         if (transparentColor >= 0) // transparency bit set?
         {
            ds.writeInt(6);
            crc.reset();
            ds.writeBytes("tRNS".getBytes());
            ds.writeShort((transparentColor >> 16) & 0xFF);
            ds.writeShort((transparentColor >> 8) & 0xFF);
            ds.writeShort((transparentColor ) & 0xFF);
            ds.writeInt((int)crc.getValue());
         }
         if (comment != null && comment.length() > 0)
         {
            ds.writeInt("Comment".length() + 1 + comment.length());
            crc.reset();
            ds.writeBytes("tEXt".getBytes());
            ds.writeBytes("Comment".getBytes());
            ds.writeByte(0);
            ds.writeBytes(comment.getBytes());
            ds.writeInt((int)crc.getValue());
         }

         // write the image data
         crc.reset();
         byte[] row = new byte[3*w];
         byte[] filterType = new byte[1];
         ByteArrayStream databas = new ByteArrayStream(3*w*h+h);

         for (int y = 0; y < h; y++)
         {
            getPixelRow(row, y);
            databas.writeBytes(filterType,0,1);
            databas.writeBytes(row,0,row.length);
         }
         databas.mark();
         ByteArrayStream compressed = new ByteArrayStream(w*h+h);
         int ncomp = ZLib.deflate(databas, compressed, 9);
         ds.writeInt(ncomp);
         crc.reset();
         ds.writeBytes("IDAT".getBytes());
         ds.writeBytes(compressed.getBuffer(), 0, ncomp);
         c = (int)crc.getValue();
         ds.writeInt(c);

         // write the footer
         ds.writeInt(0);
         crc.reset();
         ds.writeBytes("IEND".getBytes());
         ds.writeInt((int)crc.getValue());
      }
      catch (OutOfMemoryError oome)
      {
         throw new ImageException(oome.getMessage()+"");
      }
   }

   native protected void getPixelRow(byte []fillIn, int y);

   private static final int SCALED_INSTANCE = 0;
   private static final int SMOOTH_SCALED_INSTANCE = 1;
   private static final int ROTATED_SCALED_INSTANCE = 2;
   private static final int TOUCHEDUP_INSTANCE = 3;
   private static final int FADED_INSTANCE = 4; // guich@tc110_50

   native private void getModifiedInstance(totalcross.ui.image.Image4D newImg, int angle, int percScale, int color, int brightness, int contrast, int type);

   private totalcross.ui.image.Image4D getModifiedInstance(int newW, int newH, int angle, int percScale, int color, int brightness, int contrast, int type) throws totalcross.ui.image.ImageException
   {
      if (type != FADED_INSTANCE && newW == width && newH == height && (angle%360) == 0 && brightness == 0 && contrast == 0)
         return this;
      Image4D imageOut;
      newW *= frameCount;
      try
      {
         imageOut = new Image4D(newW, newH);
      }
      catch (Throwable e)
      {
         throw new ImageException("Out of memory");
      }
      if (type == ROTATED_SCALED_INSTANCE && frameCount > 1)
         imageOut.setFrameCount(frameCount);
      imageOut.transparentColor = transparentColor;
      imageOut.useAlpha = useAlpha;
      getModifiedInstance(imageOut, angle, percScale, color, brightness, contrast, type);
      if (type != ROTATED_SCALED_INSTANCE && frameCount > 1)
         imageOut.setFrameCount(frameCount);
      return imageOut;
   }

   public Image4D getFadedInstance(int backColor) throws ImageException // guich@tc110_50
   {
      return getModifiedInstance(width, height, 0, 0, backColor, 0, 0, FADED_INSTANCE);
   }

   public totalcross.ui.image.Image4D getScaledInstance(int newWidth, int newHeight) throws totalcross.ui.image.ImageException // guich@350_22
   {
      return getModifiedInstance(newWidth, newHeight, 0, 0, -1, 0, 0, SCALED_INSTANCE);
   }
   public totalcross.ui.image.Image4D getSmoothScaledInstance(int newWidth, int newHeight, int backColor) throws totalcross.ui.image.ImageException // guich@350_22
   {
      return getModifiedInstance(newWidth, newHeight, 0, 0, backColor, 0, 0, SMOOTH_SCALED_INSTANCE);
   }
   public totalcross.ui.image.Image4D getRotatedScaledInstance(int percScale, int angle, int fillColor) throws totalcross.ui.image.ImageException
   {
      if (percScale <= 0) percScale = 1;
      if (fillColor < 0 && transparentColor < 0)
         fillColor = Color.WHITE;
      int backColor = (fillColor < 0) ? this.transparentColor : fillColor;

      /* xplying by 0x10000 allow integer math, while not loosing much prec. */

      int rawSine=0;
      int rawCosine=0;

      angle = angle % 360;
      if ((angle % 90) == 0)
      {
         if (angle < 0) angle += 360;
         switch (angle)
         {
            case 0:
               rawCosine = 0x10000;
               break;
            case 90:
               rawSine = 0x10000;
               break;
            case 180:
               rawCosine = -0x10000;
               break;
            default: // case 270:
               rawSine = -0x10000;
               break;
         }
      }
      else
      {
         double rad = angle * 0.0174532925;
         rawSine = (int) (Math.sin(rad) * 0x10000);
         rawCosine = (int) (Math.cos(rad) * 0x10000);
      }

      int hIn = this.height;
      int wIn = this.width;

      /* create imageOut */
      int cornersX[] = new int[3];
      int cornersY[] = new int[3];
      int xMin = 0;
      int yMin = 0;
      int xMax = 0;
      int yMax = 0;
      cornersX[0] = (wIn * rawCosine) >> 16;
      cornersY[0] = (wIn * rawSine) >> 16;
      cornersX[2] = (-hIn * rawSine) >> 16;
      cornersY[2] = (hIn * rawCosine) >> 16;
      cornersX[1] = cornersX[0] + cornersX[2];
      cornersY[1] = cornersY[0] + cornersY[2];

      for (int i = 2; i >= 0; i--)
      {
         if (cornersX[i] < xMin)
            xMin = cornersX[i];
         else if (cornersX[i] > xMax) xMax = cornersX[i];

         if (cornersY[i] < yMin)
            yMin = cornersY[i];
         else if (cornersY[i] > yMax) yMax = cornersY[i];
      }
      if (width == height)
      {
         xMax = yMax = width;
         xMin = yMin = 0;
      }
      int wOut = ((xMax - xMin) * percScale) / 100;
      int hOut = ((yMax - yMin) * percScale) / 100;
      int x0 = ((wIn << 16) - (((xMax - xMin) * rawCosine) - ((yMax - yMin) * rawSine)) - 1) / 2;
      int y0 = ((hIn << 16) - (((xMax - xMin) * rawSine) + ((yMax - yMin) * rawCosine)) - 1) / 2;
      return getModifiedInstance(wOut, hOut, percScale, angle, backColor, x0, y0, ROTATED_SCALED_INSTANCE);
   }
   public totalcross.ui.image.Image4D getTouchedUpInstance(byte brightness, byte contrast) throws totalcross.ui.image.ImageException
   {
      return getModifiedInstance(width, height, 0, 0, 0, brightness, contrast, TOUCHEDUP_INSTANCE);
   }

   public Image4D scaledBy(double scaleX, double scaleY) throws totalcross.ui.image.ImageException // guich@402_6
   {
      return ((scaleX == 1 && scaleY == 1) || scaleX <= 0 || scaleY <= 0)?this:getScaledInstance((int)(width*scaleX), (int)(height*scaleY)); // guich@400_23: now test if the width/height are the same, what returns the original image
   }

   public Image4D smoothScaledBy(double scaleX, double scaleY, int backColor) throws totalcross.ui.image.ImageException // guich@402_6
   {
      return ((scaleX == 1 && scaleY == 1) || scaleX <= 0 || scaleY <= 0)?this:getSmoothScaledInstance((int)(width*scaleX), (int)(height*scaleY), backColor); // guich@400_23: now test if the width/height are the same, what returns the original image
   }

   public int getX()
   {
      return 0;
   }

   public int getY()
   {
      return 0;
   }

   public static boolean isSupported(String filename)
   {
      if (filename == null) return false;
      filename = filename.toLowerCase();
      return filename.endsWith(".jpeg") || filename.endsWith(".jpg") || filename.endsWith(".png");
   }
   
   public Image4D getFrameInstance(int frame) throws ImageException
   {
      Image4D img = new Image4D(width,height);
      setCurrentFrame(frame);
      img.gfx.drawImage(this,0,0);      
      img.transparentColor = this.transparentColor;
      img.useAlpha = useAlpha;
      return img;
   }
   
   native public void applyColor(int color); // guich@tc112_24
   
   final public Image4D smoothScaledFromResolution(int originalRes, int backColor) throws ImageException // guich@tc112_23
   {
      int k = Math.min(Settings.screenWidth,Settings.screenHeight);
      return getSmoothScaledInstance(width*k/originalRes, height*k/originalRes, backColor);
   }
}
