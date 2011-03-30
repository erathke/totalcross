/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2011 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *********************************************************************************/

// $Id: ActivationWindow.java,v 1.20 2011-01-04 13:19:27 guich Exp $

package ras.ui;

import ras.*;
import totalcross.sys.*;
import totalcross.ui.*;
import totalcross.ui.dialog.*;

public class ActivationWindow extends MainWindow
{
   private ActivationClient client;

   public ActivationWindow()
   {
      this.client = ActivationClient.getInstance();
   }

   public ActivationWindow(ActivationClient client)
   {
      super("Activation", TAB_ONLY_BORDER);
      setUIStyle(Settings.Vista);

      this.client = client;
   }

   public void initUI()
   {
      ActivationHtml html = ActivationHtml.getInstance();
      if (html != null)
         html.popup();

      String text = "The TotalCross virtual machine needs to be activated. This process requires your device's internet connection to be properly set up.";
      Label l;
      add(l = new Label(Convert.insertLineBreak(Settings.screenWidth - 10, fm, text)));
      l.align = FILL;
      l.setRect(LEFT + 5, TOP + 2, FILL - 10, PREFERRED);
      repaintNow();

      try
      {
         client.activate();
         if (!(Settings.platform.equals(Settings.WIN32) && "SW-GUICH".equals(Settings.deviceId))) // guich: this runs everyday on my machine to check if the server is running. so, if success, just quit 
         {
            MessageBox mb = new MessageBox("Success", "TotalCross is now activated!\nPlease restart your application.");
            mb.setBackColor(0x00AA00);
            mb.yPosition = BOTTOM;
            mb.popup();
         }
         exit(0);
      }
      catch (ActivationException ex)
      {
         String s = ex.getMessage() + "; The activation process cannot continue. The application will be terminated. Try again 2 or 3 times if there's really an internet connection.";
         s = s.replace('\n', ' '); // guich@tc115_13
         MessageBox mb = new MessageBox("Failure", s, new String[] { "Exit" });
         mb.setTextAlignment(LEFT);
         mb.yPosition = BOTTOM;
         mb.popup();
         exit(1);
      }
   }
}
