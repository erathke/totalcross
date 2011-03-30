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

// $Id: ImageList.java,v 1.2 2011-01-04 13:19:05 guich Exp $

package totalcross.ui;

import totalcross.ui.gfx.*;
import totalcross.ui.image.*;

/** Implements a ListBox where the items are images.
  * There's a restriction: all images must have the same width and height.
  * Next an example of how to use this class as a combobox color chooser:
  * <pre>
  * ImageList list = new ImageList();
  * list.add( ADD AT LEAST ONE IMAGE SO THE CORRECT SIZE CAN BE COMPUTED );
  * add(foreCombo = new ComboBox(list), CENTER, BOTTOM);
  * </pre>
  * If you don't add at least one image before calling add/setRect, you must compute
  * the preferred size yourself.
  */

public class ImageList extends ListBox // guich@tc126_2
{
   public ImageList()
   {
   }

   public ImageList(Object []items)
   {
      super(items);
   }
   
   protected void drawItem(Graphics g, int index, int dx, int dy)
   {
      Image img = (Image)items.items[index];
      g.drawImage(img,dx,dy);
   }
   
   protected void drawSelectedItem(Graphics g, int index, int dx, int dy)
   {
      drawItem(g, index, dx, dy); // we will draw using the selected color
   }
   
   protected void drawCursor(Graphics g, int sel, boolean on)
   {
      if (offset <= sel && sel < visibleItems+offset && sel < itemCount)
      {
         int dx = 3; // guich@580_41: cursor must be drawn at 3 or will overwrite the border on a combobox with PalmOS style
         int dy = 3;
         if (uiPalm || uiFlat) dy--;
         if (simpleBorder) {dx--; dy--;}

         int ih = getItemHeight(sel);
         int iw = getItemWidth(sel);
         dx += xOffset; // guich@552_24: added this to make scroll apply to the item
         dy += (sel-offset) * ih;
         if (on)
         {
            g.foreColor = customCursorColor != -1 ? customCursorColor : Color.RED;
            g.drawRect(dx,dy,iw,ih); // only select the Object - guich@200b4_130
         }
         else
            drawItem(g,sel, dx, dy);
      }
   }
   public int getPreferredWidth()
   {
      int max = itemCount > 0 ? ((Image)items.items[0]).getWidth() : 0;
      return max + (simpleBorder?4:6) + (sbar.visible ? sbar.getPreferredWidth() : 0) + insets.left+insets.right;
   }
   
   public int getPreferredHeight()
   {
      if (itemCount == 0)
         return fmH;
      int ih = ((Image)items.items[0]).getHeight();
      return itemCount * ih + insets.top+insets.bottom + (simpleBorder?4:6); 
   }
   
   protected int getItemWidth(int index)
   {
      return itemCount == 0 ? fmH : ((Image)items.items[index]).getWidth();
   }
   
   protected int getItemHeight(int index)
   {
      return itemCount == 0 ? 0 : ((Image)items.items[index]).getHeight();
   }
}