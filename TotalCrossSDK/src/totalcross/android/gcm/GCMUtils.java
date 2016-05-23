package totalcross.android.gcm;

import totalcross.*;

import android.content.*;
import java.io.*;

public class GCMUtils
{
   public static String pushTokenAndroid;
   
   private static String vmPath(Context cnt)
   {
      try
      {
         String ret = cnt.getPackageManager().getPackageInfo(cnt.getPackageName(), 0).applicationInfo.dataDir+"/";
         return ret;
      }
      catch (Exception e)
      {
         AndroidUtils.handleException(e,true);
      }
      return ".";
   }
   ///// send to application
   public static void writeToken(Context cnt, String id, String token) throws IOException
   {
      String name = vmPath(cnt)+"push_token."+id;
      writeChars(name, false, token);
   }

   public static void writeMessage(Context cnt, String classname, String msg)
   {
      String name = vmPath(cnt)+"push_messages."+pushTokenAndroid;
      try
      {
         writeChars(name, true, msg);
      }
      catch (Exception e)
      {
         AndroidUtils.handleException(e, false);
      }
   }

   private static void writeChars(String name, boolean append, String msg) throws IOException
   {
      try
      {
         FileOutputStream f = null;
         for (int tries = 100; --tries >= 0;)
            try {f = new FileOutputStream(name, append);} catch (Exception e) {AndroidUtils.handleException(e,false); try{Thread.sleep(50);}catch (Exception ee) {}}
         if (f == null)
            AndroidUtils.debug("Cannot open output file "+name);
         else
         {
            DataOutputStream ds = new DataOutputStream(f);
            ds.writeShort(msg.length());
            for (int i = 0, n = msg.length(); i < n; i++)
               ds.writeChar(msg.charAt(i));
            f.close();
         }         
      }
      catch (FileNotFoundException fnfe)
      {
      }
   }
   
   // broadcast events

   public static void sendBroadcast(Context cnt, int event)
   {
      Intent in = new Intent("totalcross.MESSAGE_EVENT");
      in.putExtra("event", event);
      LocalBroadcastManager.getInstance(cnt).sendBroadcast(in);
   }

   public static void startGCMService(Context cnt, String pack, String tczname)
   {
      Intent intent = new Intent(cnt, totalcross.android.gcm.GCMTokenReceiver.class);
      intent.putExtra("pack", pack);
      intent.putExtra("cls", tczname);
      cnt.startService(intent);
      // register broadcast receiver 
      LocalBroadcastManager.getInstance(cnt).registerReceiver(new BroadcastReceiver() 
      {
         public void onReceive(Context context, Intent intent) 
         {
            try
            {
               final int event = intent.getIntExtra("event", 0);
               if (event != 0)
                  Launcher4A.eventThread.pushEvent(event, 0, 0, 0, 0, 0);
            }
            catch (Throwable t)
            {
               AndroidUtils.handleException(t, false);
            }
         }
      }, new IntentFilter("totalcross.MESSAGE_EVENT"));
   }
}