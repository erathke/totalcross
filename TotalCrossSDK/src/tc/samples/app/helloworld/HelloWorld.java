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

// $Id: HelloWorld.java,v 1.24 2011-03-28 15:44:14 guich Exp $

package tc.samples.app.helloworld;

import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.ListBox;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.KeyEvent;

public class HelloWorld extends MainWindow
{
   Button btn;

   public HelloWorld()
   {
      super("Hello World Sample", TAB_ONLY_BORDER);
   }

   public void initUI()
   {
      add(btn = new Button("  Exit  "), CENTER, TOP + 1);
      ListBox lb = new ListBox(new String[]
      {
         "Version is " + Settings.versionStr,
         "Platform is " + Settings.platform,
         "User is " + Settings.userName,
         "Pen is " + (Settings.keyboardFocusTraversable ? "missing" : "available"),
         "Has Keypad only is " + Settings.keypadOnly,
         "Virtual keyboard is " + Settings.virtualKeyboard,
         "Screen is " + Settings.screenWidth + "x" + Settings.screenHeight,
         "Is color? " + Settings.isColor,
         "Is high color? " + Settings.isHighColor,
         "Max Colors is " + Settings.maxColors,
         "timeZone is " + Settings.timeZone,
         "dateFormat is " + Settings.dateFormat,
         "dateSeparator is " + Settings.dateSeparator,
         "decimalSeparator is " + Settings.decimalSeparator,
         "thousandsSeparator is " + Settings.thousandsSeparator + "  ",
         "timeSeparator is " + Settings.timeSeparator,
         "daylightSavings is " + Settings.daylightSavings,
         "is24Hour is " + Settings.is24Hour,
         "weekStart is " + Settings.weekStart,
         "Battery is at " + Vm.getRemainingBattery() + "%",
         "Free memory is at " + Vm.getFreeMemory(),
         "Rom serial number is " + Settings.romSerialNumber,
         "IMEI is "+ Settings.imei,
         "ICCID is "+ Settings.iccid,
         "Rom version is " + Settings.romVersion,
         "Device id is " + Settings.deviceId,
         "App path is " + Settings.appPath,
      });
      lb.enableHorizontalScroll();
      add(lb, LEFT, AFTER + 2, FILL, FILL);
      lb.requestFocus();
      repaint();
   }

   public void onEvent(Event e)
   {
      if ((e.type == ControlEvent.PRESSED && e.target == btn) ||
          (e.type == KeyEvent.KEY_PRESS && ((KeyEvent) e).key == 'x'))
      {
         exit(0);
      }
   }
}
