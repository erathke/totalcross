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

// $Id$

package totalcross.android;

import totalcross.*;

import java.io.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;

public class Loader extends Activity
{
   public Handler achandler;
   private boolean runningVM;
   private static final int CHECK_LITEBASE = 1234324329;
   private static final int TAKE_PHOTO = 1234324330;
   private static final int JUST_QUIT = 1234324331;
   private static boolean onMainLoop;
   public static boolean isFullScreen;
   
   /** Called when the activity is first created. */
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      try
      {
         AndroidUtils.initialize(this);
         AndroidUtils.checkInstall();
         checkLitebase(); // calls runVM() on close
      }
      catch (Throwable e)
      {
         String stack = Log.getStackTraceString(e);
         AndroidUtils.debug(stack);
         AndroidUtils.error("An exception was issued when launching the program. Please inform this stack trace to your software's vendor:\n\n"+stack,true);
      }
   }
   
   private void checkLitebase()
   {
      // launch the vm's intent
      Intent intent = new Intent("android.intent.action.MAIN");
      intent.setClassName("litebase.android","litebase.android.Loader");
      intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
      try
      {
         startActivityForResult(intent, CHECK_LITEBASE);
      }
      catch (android.content.ActivityNotFoundException anfe)
      {
         AndroidUtils.debug("Litebase not installed.");
      }
   }
   
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      switch (requestCode)
      {
         case JUST_QUIT:
            finish();
            break;
         case CHECK_LITEBASE:
            runVM();
            break;
         case TAKE_PHOTO:
            Launcher4A.pictureTaken(resultCode != RESULT_OK ? 1 : 0);
            break;
      }
   }
   
   private void captureCamera(String s)
   {
      Intent intent = new Intent(this, CameraViewer.class);
      intent.putExtra("file",s);
      startActivityForResult(intent, TAKE_PHOTO);
   }
   
   private void dialNumber(String number)
   {
      startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number)));
   }

   public static final int DIAL = 1;
   public static final int CAMERA = 2;
   public static final int TITLE = 3;
   public static final int EXEC = 4;
   
   static String tcz;
   
   private void runVM()
   {
      if (runningVM) return;
      runningVM = true;
      Hashtable<String,String> ht = AndroidUtils.readVMParameters();
      String tczname = tcz = ht.get("tczname");
      if (tczname == null)
      {
         // this is a single apk. get the app name from the package
         String sharedId = AndroidUtils.pinfo.sharedUserId;
         if (sharedId.equals("totalcross.app.sharedid")) // is it the default shared id?
            AndroidUtils.error("Launching parameters not found",true);
         else
         {
            tczname = sharedId.substring(sharedId.lastIndexOf('.')+1);
            ht.put("apppath", AndroidUtils.pinfo.applicationInfo.dataDir);
            ht.put("fullscreen","true");
         }
      }
      String appPath = ht.get("apppath");
      isFullScreen = ht.get("fullscreen").equals("true");
      setTitle(tczname);
      if (isFullScreen)
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      
      achandler = new Handler()
      {
         public void handleMessage(Message msg) 
         {
            Bundle b = msg.getData();
            switch (b.getInt("type"))
            {
               case DIAL:
                  dialNumber(b.getString("dial.number"));
                  break;
               case CAMERA:
                  captureCamera(b.getString("showCamera.fileName"));
                  break;
               case TITLE:
                  setTitle(b.getString("setDeviceTitle.title"));
                  break;
               case EXEC:
                  intentExec(b.getString("command"), b.getString("args"), b.getInt("launchCode"), b.getBoolean("wait"));
                  break;
            }
         }
      };
      setContentView(new Launcher4A(this, tczname, appPath));
      onMainLoop = true;
   }

   // Vm.exec("url","http://www.google.com/search?hl=en&source=hp&q=abraham+lincoln",0,false): launches a url
   // Vm.exec("totalcross.app.UIGadgets",null,0,false): launches another TotalCross' application
   // Vm.exec("viewer","file:///sdcard/G3Assets/541.jpg", 0, true);
   private void intentExec(String command, String args, int launchCode, boolean wait)
   {
      if (command.equalsIgnoreCase("viewer"))
      {
         Intent intent = new Intent(this, WebViewer.class);
         intent.putExtra("url",args);
         if (!wait)
            startActivityForResult(intent, JUST_QUIT);
         else
            startActivity(intent);
         return;
      }
      else
      if (command.equalsIgnoreCase("url"))
      {
         if (args != null)
         {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(args));
            startActivity(i);
         }
      }
      else
      if (command.toLowerCase().endsWith(".apk"))
      {
         Intent i = new Intent(Intent.ACTION_VIEW);
         i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         i.setDataAndType(Uri.fromFile(new File(command)), "application/vnd.android.package-archive");
         startActivity(i);
      }
      else
      {
         Intent i = new Intent();
         i.setClassName(command,command+"."+args);
         startActivity(i);
      }
      if (!wait)
         finish();
   }
   
   public void onConfigurationChanged(Configuration newConfig)
   {
      // TODO change the Settings.virtualKeyboard to true when newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES;
      super.onConfigurationChanged(newConfig);
   }

   protected void onSaveInstanceState(Bundle outState) 
   {
   }
   
   protected void onDestroy()
   {
      if (runningVM) // guich@tc126_60: call app 1, home, call app 2: onDestroy is called
         quitVM();
      super.onDestroy();
   }
   
   protected void onPause()
   {
      Launcher4A.appPaused = true;
      if (onMainLoop)
         Launcher4A.appPaused();
      super.onPause();
      if (isFinishing() && runningVM) // guich@tc126_60: stop the vm if finishing is true, since onDestroy is not guaranteed to be called
         quitVM();                    // call app 1, exit, call app 2: onPause is called but onDestroy not
   }
   
   private void quitVM()
   {
      runningVM = false;
      Launcher4A.stopVM();
      while (!Launcher4A.canQuit)
         try {Thread.sleep(1);} catch (Exception e) {}
      android.os.Process.killProcess(android.os.Process.myPid());
      // with these two lines, the application may have problems when then stub tries to load another vm instance.
      //try {Thread.sleep(1000);} catch (Exception e) {} // let the app take time to exit
      // System.exit(0); // make sure all threads will stop. also ensures that one app is not called as the app launched previously
   }
   
   protected void onResume()
   {
      if (onMainLoop)
         Launcher4A.appResumed();
      Launcher4A.appPaused = false;
      super.onResume();
   }
}