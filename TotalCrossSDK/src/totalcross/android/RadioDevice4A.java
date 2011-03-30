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

// $Id: RadioDevice4A.java,v 1.2 2011-01-04 13:19:27 guich Exp $

package totalcross.android;

import totalcross.Launcher4A;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

public class RadioDevice4A
{
   // types
   public static final int WIFI = 0;
   public static final int PHONE = 1;
   public static final int BLUETOOTH = 2;

   // generic states
   public static final int RADIO_STATE_DISABLED = 0;
   public static final int RADIO_STATE_ENABLED = 1;

   // bluetooth states
   public static final int BLUETOOTH_STATE_DISCOVERABLE = 2;

   private RadioDevice4A()
   {
   }

   public static boolean isSupported(int type)
   {
      switch (type)
      {
         case WIFI:
            return ConnectivityManager.isNetworkTypeValid(ConnectivityManager.TYPE_WIFI);
         case PHONE:
            return ConnectivityManager.isNetworkTypeValid(ConnectivityManager.TYPE_MOBILE);
         case BLUETOOTH:
            return false;
         default:
            return false;
      }
   }

   public static int getState(int type)
   {
      switch (type)
      {
         case WIFI:
         {
            WifiManager wifiMgr = (WifiManager) Launcher4A.getAppContext().getSystemService(Context.WIFI_SERVICE);
            return wifiMgr.isWifiEnabled() ? RADIO_STATE_ENABLED : RADIO_STATE_DISABLED;
         }
         case PHONE:
         {
            return RADIO_STATE_DISABLED;
         }
         case BLUETOOTH:
         {
            return RADIO_STATE_DISABLED;
         }
         default:
            return RADIO_STATE_DISABLED;
      }
   }

   public static void setState(int type, int state)
   {
      switch (type)
      {
         case WIFI:
         {
            WifiManager wifiMgr = (WifiManager) Launcher4A.getAppContext().getSystemService(Context.WIFI_SERVICE);
            wifiMgr.setWifiEnabled(state ==  RADIO_STATE_ENABLED);
         }
         case PHONE:
         {
            //
         }
         case BLUETOOTH:
         {
            //
         }
      }      
   }
}
