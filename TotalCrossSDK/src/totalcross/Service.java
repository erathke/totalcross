package totalcross;

import totalcross.io.*;
import totalcross.sys.*;
import totalcross.ui.dialog.*;
import totalcross.util.*;

public abstract class Service implements MainClass
{
   protected int loopDelay = 30000;
   private String serviceName, fileName;
   private static final String regkey = "\\Services\\TotalCrossSrv";
   private static boolean ANDROID = Settings.platform.equals(Settings.ANDROID);
   
   public Service()
   {
      serviceName = getClass().getName().replace('.','/');
      serviceName = serviceName.substring(serviceName.lastIndexOf('/')+1);
      serviceName = serviceName.substring(serviceName.lastIndexOf('$')+1);
      fileName = ANDROID ? "/sdcard/" : "/";
      fileName += serviceName+".ctrl";
   }

   protected abstract void onStart();
   protected abstract void onService();
   protected abstract void onStop();

   
   final public void appStarting(int timeAvail)
   {
      totalcross.ui.MainWindow.minimize(); // run on background
      if (!registerService()) // run the service loop only if it was previously registered
         serviceLoop();
   }

   private void serviceLoop()
   {
      onStart();
      try
      {
         start();
         while (isRunning())
         {
            onService();
            Vm.sleep(loopDelay);
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         onStop();
      }
   }

   public void launchService()
   {
      if (!ANDROID)
      {
         String path = "\\"+serviceName+"\\"+serviceName+".exe";
         Vm.exec(path,null,0,false);
      }
   }
   
   private int rCtrl()
   {
      try
      {
         byte[] b = new File(fileName,File.READ_ONLY).readAndClose();
         return b[0];
      }
      catch (FileNotFoundException fnfe)
      {
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return -1;
   }

   private void wCtrl(int v)
   {
      while (true)
      try
      {
         File f = new File(fileName,File.CREATE_EMPTY);
         f.writeBytes(new byte[]{(byte)v});
         f.close();
         break;
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   public void start() throws Exception
   {
      wCtrl(1);
   }
   
   public void stop() throws Exception
   {
      wCtrl(0);
   }
   
   public boolean isRunning() throws Exception
   {
      return rCtrl() == 1;
   }
   
   public boolean registerService()
   {
      if (ANDROID)
         return false;
      try
      {
         Registry.getInt(Registry.HKEY_LOCAL_MACHINE, regkey,"Index");
      }
      catch (ElementNotFoundException enfe)
      {
         try
         {
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Dll","\\"+serviceName+"\\tcvm.dll");
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Context",1);
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"FriendlyName","TotalCrossSrv");
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Index",0);
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Description","TotalCross Service");
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Order",8);
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Flags",0);
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Keep",1);
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"Prefix","TSV");
            Registry.set(Registry.HKEY_LOCAL_MACHINE, regkey,"TCZ",serviceName+".tcz");
         }
         catch (Exception ee)
         {
            MessageBox.showException(ee,true);
         }
      }
      catch (Exception ee)
      {
         MessageBox.showException(ee,true);
      }
      int ret = Vm.exec("register service",null,0,true); // register the service
      return ret == 1;
   }
   
   public void unregisterService()
   {
      if (!ANDROID)
      {
         Vm.exec("unregister service",null,0,true);
         Vm.sleep(500);
      }
   }

   public void _postEvent(int type, int key, int x, int y, int modifiers, int timeStamp) {}
   public void appEnding() {}
   public void _onTimerTick(boolean canUpdate) {}
}
