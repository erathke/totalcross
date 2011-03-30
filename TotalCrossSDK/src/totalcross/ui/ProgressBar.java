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

// $Id: ProgressBar.java,v 1.20 2011-01-13 15:27:22 guich Exp $

package totalcross.ui;

import totalcross.sys.*;
import totalcross.ui.gfx.*;

/** A basic progress bar, with the bar and a text.
 * The text is comprised of a prefix and a suffix.
 * <p>
 * You can create a horizontal endless ProgressBar, always going from left to right, 
 * by setting the given parameters:
 * <ul>
 * <li> call setEndless()
 * <li> max-min: used to compute the width of the bar
 * <li> prefix and suffix: displayed, but the current value is not displayed
 * <li> setValue(n): n used to increment the current value, not to set the value to n.
 * </ul>
 * Then set a timer to update the value. See the UIGadgets sample.
 */

public class ProgressBar extends Control
{
   /** The minimum value of a progress bar. */
   public int min;

   /** The maximum value of a progress bar. */
   public int max;

   /** The current value of a progress bar. */
   private int value;

   /** The string prefix. The displayed label will be prefix+value+sufix. The default is an empty string. */
   public String prefix="";

   /** The string prefix. The displayed label will be prefix+value+sufix. The default is the percentage symbol. */
   public String suffix="%";

   /** Set to false to don't let the text be drawn.
    * @since TotalCross 1.0
    */
   public boolean drawText = true;
   
   /** Set to false to don't let the value be drawn. Note that the prefix and suffix will still be drawn.
    * @since TotalCross 1.0
    */
   public boolean drawValue = true;

   /** The text color */
	public int textColor = totalcross.sys.Settings.isColor?Color.RED:Color.WHITE; // guich@340_5

   /** If false, no border is drawn. */
   public boolean drawBorder = true;
   
   /** If true, the text is highlighted. */
   public boolean highlight;
   
   /** The highlight color, or -1 to use one based on textColor. */
   public int highlightColor = -1;

   /** If false, use horizontal mode (default)
    * @since TotalCross 1.15 
    */
   public boolean vertical; // guich@tc115_67
   
   /** Used for String concatenations */
	private static StringBuffer sb = new StringBuffer(10);
	
	private boolean endless;

   /**
    * Creates a progress bar, with minimum and maximum set as 0 and 100, respectively.
    */
   public ProgressBar()
   {
      this(0,100);
   }

   /**
    * Creates a progress bar, String is set to <code>null</code>, which is the default.
    * Uses the specified minimum for the initial value of the progress bar.
    * @param min The minimum value
    * @param max The maximum value
    */
   public ProgressBar(int min, int max)
   {
      this.min = value = min;
      this.max = max;
      if (!totalcross.sys.Settings.isColor)
      {
         foreColor = Color.BLACK; // rogerio@341_13
         backColor = Color.DARK;  // rogerio@341_13
      }
      else
         foreColor = Color.getRGB(0,0,200);
      focusTraversable = false;
   }

   /** Call this method to make this ProgressBar a horizontal endless progressbar; 
    * just keep calling <code>setValue(step)</code>
    * to increase the value of the progressbar.
    */
   public void setEndless() // guich@tc125_19
   {
      if (vertical)
         throw new RuntimeException("Endless progressbar cannot be vertical, only horizontal)");
      suffix = "";
      endless = true;
   }

   /** Gets the preferred width, which is the parent's width-6, or the screen's width. */
   public int getPreferredWidth()
   {
      if (vertical)
         return fm.stringWidth(max+prefix+suffix)+8;
      return parent==null ? Settings.screenWidth : (parent.getWidth()-6);
   }

   /** Returns the preferred height, which is fmH+2 */
   public int getPreferredHeight()
   {
      if (vertical)
         return parent==null ? Settings.screenHeight : (parent.getHeight()-6);
      return fmH+2;
   }

   /**
    * Sets the current value. Due to performance reasons,
    * min and max are not verified.
    * If the value was not changed, nothing happens.
    * The progress bar is repainted immediately.
    * <p>
    * If this is an endless ProgressBar, the given number is used as 
    * an increment to the current value. Note that n must be greater than 0.
    *
    * @param   n The new value
    * @see     #getValue
    */
   public void setValue(int n)
   {
      if (endless || value != n)
      {
         value = endless ? value+n : n;
         repaintNow();
      }
   }

   /**
    * Sets the current value and the prefix and suffix. Note that, due to performance reasons,
    * min and max are not verified. This does not check if the value had changed; it always
    * repaint the progress bar immediately.
    *
    * @param  value The new value
    * @see     #getValue
    */
   public void setValue(int value, String prefix, String suffix)
   {
      this.value = endless ? this.value+value : value;
      this.prefix = prefix;
      this.suffix = suffix;
      repaintNow();
   }

   /** Returns the current value */
   public int getValue()
   {
      return value;
   }

   /** Paint the Progress Bar. The filled part of the bar is painted with the foreground color;
     * the empty part of the bar is painted with the background color; the text is painted with
     * the defined textColor color; no border is drawn.
     */
   public void onPaint(Graphics g)
   {
      // computes the current width of the bar
      int dif = max-min;
      int size = vertical ? height : width;
      int s = (dif == 0 || value==max) ? size : (value == 0) ? 0 : (int)(size * (value - min) / dif);
      if (s > size) s = size;
      // draw the filled part
      int bc = getBackColor();
      if (s > 0)
      {
         if (endless)
         {
            g.backColor = bc;
            g.fillRect(0,0,width,height);
         }
         if (uiVista && enabled) // guich@573_6
         {
            if (vertical)
               g.fillVistaRect(0, height - s, width, s, getForeColor(), false, false);
            else
            if (!endless)
               g.fillVistaRect(0,0,s,height,getForeColor(),false,false);
            else
               g.fillVistaRect(value-dif,0,dif,height,getForeColor(),false,false);
         }
         else
         {
            g.backColor = getForeColor();
            if (vertical)
               g.fillRect(0, height - s, width, s);
            else
            if (!endless)
               g.fillRect(0,0,s,height);
            else
               g.fillRect(value-dif,0,dif,height);
         }
         if (endless && value-dif >= width) value = 0; 
      }
      // draw the empty part
      g.backColor = bc;
      int ss = size-s;
      if (ss > 0 && !transparentBackground)
      {
         if (vertical)
            g.fillRect(0,0,width,ss);
         else
            g.fillRect(s,0,ss,height);
      }
      // draw the text
      if (drawText)
      {
         StringBuffer sb = ProgressBar.sb; // get a local reference
         sb.setLength(0);
         sb.append(prefix);
         if (!endless && drawValue)
            sb.append(value);
         String st = sb.append(suffix).toString();
         int x = (width-fm.stringWidth(st))>>1;
         int y = ((height-fmH)>>1)-1;
         int shadow = textShadowColor != -1 ? textShadowColor : !highlight ? -1 : highlightColor == -1 ? Color.getCursorColor(textColor) : highlightColor;
         g.foreColor = textColor;
         g.drawText(st, x, y, shadow != -1, shadow);
      }
      g.foreColor = textColor;
      if (drawBorder)
         g.drawRect(0,0,width,height);
   }
   
   /** Clears this control, setting the value to clearValueInt. */
   public void clear() // guich@572_19
   {
      setValue(clearValueInt);
   }
}
