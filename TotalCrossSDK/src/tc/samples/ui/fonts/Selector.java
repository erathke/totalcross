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

// $Id: Selector.java,v 1.22 2011-03-21 18:52:28 guich Exp $

package tc.samples.ui.fonts;

import totalcross.ui.*;
import totalcross.ui.font.*;

public class Selector extends Container
{
   static ComboBox cbNames;
   Check ckBold;
   Slider slSize;

   public void initUI()
   {
      Label l1,l2;
      add(l1 = new Label("Name: "), LEFT, TOP + 3);
      add(cbNames = new ComboBox(new String[] { Font.DEFAULT, "Arial"}), AFTER, SAME);
      cbNames.setSelectedIndex(0);
      add(l2 = new Label("Size:  "+Font.MIN_FONT_SIZE), LEFT, AFTER + 3, l1);
      add(l1 = new Label(""+Font.MAX_FONT_SIZE), RIGHT, SAME);
      add(slSize = new Slider(), AFTER+2, SAME, FIT-2, SAME,l2);
      slSize.setLiveScrolling(true);
      slSize.setMinimum(Font.MIN_FONT_SIZE);
      slSize.setMaximum(Font.MAX_FONT_SIZE+1); // +1: visible items
      slSize.drawFilledArea = false;
      slSize.drawTicks = true;
      slSize.setValue(Font.NORMAL_SIZE);
      add(ckBold = new Check("Bold"), LEFT, AFTER + 3, l2);
   }

   public Font getSelectedFont()
   {
      return Font.getFont((String)cbNames.getSelectedItem(), ckBold.isChecked(), slSize.getValue());
   }

   public int getPreferredHeight()
   {
      return new Label().getPreferredHeight() * 4 + insets.top+insets.bottom;
   }
}
