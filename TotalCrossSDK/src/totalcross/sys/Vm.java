/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 * Copyright (C) 1998, 1999 Wabasoft <www.wabasoft.com>                          *
 *  Copyright (C) 2000-2012 SuperWaba Ltda.                                      *
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

package totalcross.sys;

import totalcross.*;
import totalcross.ui.*;
import totalcross.util.*;

/**
 * Vm contains various system-level methods.
 * <p>
 * This class contains methods to copy arrays, obtain a timestamp, sleep and get platform and version information, among
 * many other things.
 */

public final class Vm
{
   /** Internal use only. Used only at desktop. */
   public static int[] keysBeingIntercepted;

   /** Pass this to Vm.debug to erase the memo/txt file used to store the output. */
   public final static String ERASE_DEBUG = "!erase debug!";
   
   /** Pass this to Vm.debug to redirect the output to an alternative debugging viewer. The current platforms that support
    * this command are:
    * <ul>
    *  <li> Android: use the logging window throught ADB, which can be viewed by calling
    * <code>\TotalCrossSDK\etc\tools\android\debug_console.bat</code>
    * </ul>
    * This flag can be used only once, at the Application's constructor. Calling it afterwards can result in debugging data loss.
    */
   public final static String ALTERNATIVE_DEBUG = "!alt_debug!"; // guich@tc122_14

   /**
    * Set to <code>true</code> to disable <code>Vm.debug</code> message output. Note that the method calls will still
    * remain, and can slowdown your program. Note also that Vm.warning is not affected by this flag.
    *
    * @since SuperWaba 5.52
    */
   public static boolean disableDebug; // guich@552_30

   private static long startTS = System.currentTimeMillis(); // guich@567_15

   private Vm()
   {
   }

   /**
    * Copies the elements of one array to another array. This method returns true if the copy is successful. It will
    * return false if the array types are not compatible or if either array is null. If the length parameter would cause
    * the copy to read or write past the end of one of the arrays, an index out of range error will occur. If false is
    * returned then no copying has been performed.
    *
    * @param srcArray the array to copy elements from
    * @param srcStart the starting position in the source array
    * @param dstArray the array to copy elements to
    * @param dstStart the starting position in the destination array
    * @param length the number of elements to copy
    * @throws ArrayStoreException If the source array and destination array are not compatible
    */
   public static boolean arrayCopy(Object srcArray, int srcStart, Object dstArray, int dstStart, int length)
   {
      if (length < 0 || srcArray == null || dstArray == null) // guich@566_27:
         // check if null
         return false;
      try
      {
         if (length > 0) // guich@tc110_80: Java throws AIOOBE if length is 0
            System.arraycopy(srcArray, srcStart, dstArray, dstStart, length);
      }
      catch (ArrayIndexOutOfBoundsException aioobe)
      {
         throw aioobe; // guich@566_27: do the same thing as the device.
      }
      catch (ArrayStoreException ase) // this one must be passed along
      {
         throw ase;
      }
      catch (Exception e)
      {
         if (e.getMessage() != null) // guich@566_35: VmTest throws this
            Launcher.print("Exception thrown in arrayCopy: " + e.getMessage());
         return false;
      }
      return true;
   }

   /**
    * Returns a time stamp in milliseconds. The time stamp is the time in milliseconds since the program started. The
    * maximum time stamp value is (1 << 30) which represents near 14 days of continuous use, and when it is reached,
    * the timer will reset to 0 and will continue counting from there.
    */
   public static int getTimeStamp()
   {
      return (int) (System.currentTimeMillis() - startTS);
   }

   /**
    * Sets the device time to the given arguments. Note that in some systems, the millis field is ignored. Some devices
    * require that you add <code>Settings.timeZone</code>.
    * Does not work on Android, which does not allow to do this programatically.
    * @since TotalCross 1.0
    * @deprecated It won't work on most devices.
    */
   public static void setTime(Time t)
   {
      //startTS = System.currentTimeMillis() - t.getTimeLong(); - guich@tc100b5_18: prevent timer problems when running in Java
   }

   /**
    * Reboots the device. This is the equivalent of a warm boot (or soft reset). Calling this method terminates the
    * program, closes all open files, calls MainWindow.onExit and, finaly, resets the device. <br>
    * The implementation in JDK just exits the program: there's no reboot at all.
    *
    * @since SuperWaba 4.01
    */
   public static void exitAndReboot()
   {
      MainWindow.exit(0);
   }

   /**
    * Executes a command.
    * <p>
    * As an example, the following call could be used to run the command "scandir /p mydir" under Java, Win32 or WinCE:
    *
    * <pre>
    * int result = Vm.exec(&quot;scandir&quot;, &quot;/p mydir&quot;, 0, true);
    * </pre>
    *
    * This example executes the Scribble program under PalmOS:
    *
    * <pre>
    * Vm.exec(&quot;Scribble&quot;, null, 0, false);
    * </pre>
    *
    * This example executes the web clipper program under PalmOS, telling it to display a web page by using launchCode
    * 54 (CmdGoToURL).
    *
    * <pre>
    * Vm.exec(&quot;Clipper&quot;, &quot;http://www.yahoo.com&quot;, 54, true);
    * </pre>
    *
    * The args parameter passed to this method is the arguments string to pass to the program being executed.
    * <p>
    * The launchCode parameter is only used under PalmOS. Under PalmOS, it is the launch code value to use when the Vm
    * calls SysUIAppSwitch(). If 0 is passed, the default launch code (CmdNormalLaunch) is used to execute the program.
    * <p>
    * The wait parameter passed to this method determines whether to execute the command asynchronously. If false, then
    * the method will return without waiting for the command to complete execution. If true, the method will wait for
    * the program to finish executing and the return value of the method will be the value returned from the application
    * under Java, Win32 and WinCE. In Palm OS, only a few headless applications can be called with wait being true.
    * <p>
    * <b>Note: this method kills all running threads.</b>
    * <p>
    * To run another TotalCross program from Windows CE devices, use:
    *
    * <pre>
    * Vm.exec(&quot;\\TotalCross\\Path\\Program.exe&quot;, null, 0, false);
    * </pre>
    * Here's a sample of how to run the Internet Explorer in Windows CE:
    * <pre>
    * Vm.exec("\\windows\\iexplore.exe","about:blank",0,false);
    * </pre>
    * You cannot pass null nor "" in the args, or you'll get error 87. IExplore requires a page to open; in this case,
    * we open the blank page.
    * <p>
    * To run another TotalCross program from Palm OS devices, use:
    *
    * <pre>
    * Vm.exec(&quot;ProgName&quot;, null, 0, false);
    * </pre>
    *
    * In other words, you must call the program's executable. Note that in Palm OS, the program's name is the name of the prc
    * appended by the _ character. For example, to launch UIGadgets, you must pass "UIGadgets_" as the command.
    * <p>
    * When calling a TotalCross program, you can pass a command line parameter to the calling application, just placing
    * the parameters in the proper argument. It can be retrieved with <code>getCommandLine</code> method from the
    * MainWindow.
    * <p>
    * In some platforms, the caller application must be quit by calling <code>exit</code>.
    * <p>
    * In Windows 98 and beyond, if you don't pass the full path to the file, it is searched in the current PATH
    * environment variable. The command must contain only the exe name, and all the other arguments must bein the args
    * parameter. For example, the following code uses the CMD to open a file with the proper editor:
    *
    * <pre>
    * Vm.exec(&quot;cmd.exe&quot;, &quot;/c start C:\\WINNT\\DirectX.log&quot;, 0, false);
    * </pre>
    * Important notes about path separators:
    * <ul>
    * <li>In the companion we tell to always use / as the path separator. Vm.exec must be used differently of the other
    * places, because you don't pass only paths in the args parameter, pass other things too. So, when you use a path
    * in File, we convert the / to the target separator (\ or /), but in Vm.exec we can't do that, for example,
    * changing "/c start" to "\c start" will make the execution fail. So, you'll have to choose the right separator in the
    * parameters.
    * <li> Make sure that, in Windows, the paths and parameters use \\, not / as path separator, or the command will not
    * run.
    * </ul>
    * If you're using /cmd and the command fails, change /c option to /k, which will keep the open window instead
    * of closing it, so you can see the error cause.
    *<p>
    * In BlackBerry, the command must be a module name. For instance, you can open TotalCross programs, just providing its 
    * module (cod) name.
    * <ul>
    * <li> Vm.exec("net_rim_bb_browser_daemon", "http://www.google.com/search?hl=en&source=hp&q=abraham+lincoln",0,true); -- launchers a url </li>
    * </ul>
    * <p>
    * In Android, you can launch an application, an url at the browser, execute a shell command, or install an apk:
    * <ul>
    * <li> Vm.exec("cmd","logcat -d -f /sdcard/error.log -v time *:I",0,true); -- creates the adb report (useful to get errors - note that you must wait for the file to be created, since the wait parameter does not work - like File f = new File("/sdcard/error.log"); while (!f.exists()) Vm.sleep(500);)
    * <li> Vm.exec("url","http://www.google.com/search?hl=en&source=hp&q=abraham+lincoln",0,true): -- launches a url
    * <li> Vm.exec("totalcross.app.uigadgets","UIGadgets",0,false): -- launches another TotalCross' application
    * <li> Vm.exec("com.android.calculator2","Calculator",0,true); -- runs the Calculator program 
    * <li> Vm.exec("/sdcard/myapp.apk",null,0,false); -- calls the apk installation tool. To access the card in Android devices, prefix the path with <code>/sdcard</code>. Be sure that the sdcard is NOT MOUNTED, otherwise your application will not have access to it.
    * <li> Vm.exec("viewer","file:///sdcard/files/chlorine-bogusInfo.txt", 0, true); -- uses an internal viewer to show the txt file to the user (here, stored in the sdcard, but could be anywhere). Also accepts HTM(L).  Also accepts HTM(L) and JPG files. 
    * <li> Vm.exec("viewer","/sdcard/Download/handbook.pdf",0,true); -- opens a pdf. Note: you must have a pdf reader installed; search for the free adobe reader in your favorite store. Returns -1 if args is null, -2 if file was not found.
    * <li> Vm.exec("viewer","/sdcard/photo1.jpg",0,true); -- opens a jpeg/jpg/png image so the image can be panned and zoomed. Returns -1 if args is null, -2 if file was not found.
    * <li> Vm.exec("totalcross.appsrvc","TCService",0,true); -- starts the given service
    * <li> Vm.exec("broadcast","broadcast package",flags,true); -- sends a broadcast intent. "flag" is used in intent.addFlags if different of 0.
    * </ul>
    * To be able to find what's the class name of a program you want to launch, install it in the Android Emulator
    * (which is inside the Android SDK) and run the "Dev Tools" / Package Browser. Then click on the package, and click
    * in the desired Activities button. The <code>command</code> parameter for Vm.exec is the "Process" description, and the 
    * <code>args</code> parameter is the activitie's name. Note, however, that there's no guarantee that the program
    * will be available in a real device.
    * <p>
    * To install a cab file in Windows Mobile, you can use:
    * <pre>
    * Vm.exec("wceload.exe", fullPathToCabFile, 0, true);
    * </pre>
    * However, trying to update the program itself or the vm will close the program. So, update it at last.
    * 
    * @param command the command to execute
    * @param args command arguments. Does not work on Android.
    * @param launchCode launch code for PalmOS applications.
    * @param wait whether to wait for the command to complete execution before returning. If wait is false,
    * don't forget to call the <code>exit</code> method right after this command is called, otherwise the application may
    * not be called. In Android, if you're calling a TotalCross program, the wait parameter is ignored (and defaults to false). 
    * @return Usually is 0 if no error occured, or a system error code. -999 means that the file was not found. In Android, is always 0.
    */
   public static int exec(String command, String args, int launchCode, boolean wait)
   {
      // guich@tc: if (!wait) totalcross.ui.MainWindow.getMainWindow().killThreads();
      int status = -1;
      try
      {
         if (launchCode == -1) // guich@120
         {
            // guich@120: the ideal were that all classes should be re-instantiated, because any static methods that
            // used the last MainWindow are now pointing to invalid data.
            Class c = Class.forName(command);
            Launcher.instance.setNewMainWindow((totalcross.ui.MainWindow) c.newInstance(), args);
            status = 0;
         }
         else
         {
            java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
            if (args != null)
               command = command + " " + args;
            java.lang.Process p = runtime.exec(command);
            if (wait)
               status = p.waitFor();
            else
               status = 0;
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return status;
   }

   /**
    * Enables or disables device automatic turn-off, which happens after a period of inactivity of the device. If you disable the
    * auto-off, the original auto-off time will be restored automatically when the program exits. Keeping the device
    * always on is desired only when you're working with Sockets. Use it carefully, because it drains the battery.
    */
   public static void setAutoOff(boolean enabled)
   {
   }

   /**
    * Causes the VM (or the current thread, if called from it) to pause execution for the given number of milliseconds.
    *
    * @param millis time to sleep in milliseconds
    */
   public static void sleep(int millis)
   {
      try
      {
         java.lang.Thread.sleep(millis);
      }
      catch (InterruptedException e)
      {
      }
   }
   
   /**
    * Causes the VM (or the current thread, if called from it) to pause execution for aproximately the given number 
    * of milliseconds.
    * 
    * This method does not block the user interface engine, as Vm.sleep does, since it keeps calling Window.pumpEvents.
    * So, if you rotate the screen during a Vm.sleep, the rotation is blocked; but during a Vm.safeSleep, the rotation
    * occurs normally. Button presses and other user interface operation also occurs normally during this method.
    * 
    * Obviously, if you call Vm.safeSleep(100) and a screen rotation (or any other event) occurs, this method will take 
    * to return much more than 100ms.
    * 
    * This method only makes sense for sleeps above 500ms, unless you're calling it from a loop (which is reasonable
    * to keep call it), and should not be called from threads.
    *
    * @param millis time to sleep in milliseconds
    * @since TotalCross 1.3.4
    */
   public static void safeSleep(int millis)
   {
      int cur = getTimeStamp();
      int end = cur + millis;
      while (cur <= end)
      {
         millis = end - cur;
         int s = millis > 100 ? 100 : millis;
         try {java.lang.Thread.sleep(s);} catch (InterruptedException e) {}
         //if (Event.isAvailable()) // always call pumpEvents, otherwise a thread that use this method will not be able to update the screen
            Window.pumpEvents();
         cur = getTimeStamp();
      }
   }
   
   /** Vibrates the device for the specified number of milliseconds.
    * Note that on WP8 the maximum vibration time is 5 seconds. If millis is greater than 5000, it will vibrate only for 5 seconds.
    * 
    * @since TotalCross 1.22
    */
   public static void vibrate(int millis)
   {
      Launcher.instance.vibrate(millis);
   }

   /**
    * Returns the free memory in the device.
    * Returns maximum of 2GB free, even if the device has more than that.
    *
    * @since SuperWaba 2.0 beta 4
    */
   public static int getFreeMemory()
   {
      return (int) Runtime.getRuntime().freeMemory();
   }

   /**
    * Calls the Garbage Collector. Usually, this isn't necessary; the gc is called everytime theres
    * no more memory to allocate, but you may call it before running a memory-consuming routine.
    */
   public static void gc()
   {
      Runtime.getRuntime().gc();
      System.runFinalization();
   }

   /**
    * Specify which special keys to intercept; use the constants available in SpecialKeys or pass device-specific
    * constants, which can be retrieved calling Vm.showKeyCodes(true).
    * 
    * Note that the HOME key cannot be intercepted in Android due to OS restrictions.
    *
    * @since TotalCross 1.0
    */
   public static void interceptSpecialKeys(int[] keys)
   {
      keysBeingIntercepted = keys; // for desktop
   }

   /**
    * Gets the current keys being pressed. You must set which keys you want to receive notification with the
    * interceptSpecialKeys method. Note that you can use this method to verify if more than one key is pressed at once,
    * just keep pooling with this method to see when a state has changed. If you just want to intercept the key with a
    * single press, handle it in the onEvent method, with a KeyEvent event.
    *
    * @see #interceptSpecialKeys
    * @since TotalCross 1.0
    */
   public static boolean isKeyDown(int key)
   {
      try
      {
         return Launcher.instance.keysPressed.get(key) == 1;
      }
      catch (ElementNotFoundException e)
      {
         return false;
      }
   }

   /**
    * Sends a text, preceeded with the current time stamp and followed by a line feed, to:
    * <ul>
    * <li>Palm OS: the "DebugConsole" file in the NVFS volume
    * <li>Windows CE / 32: the "DebugConsole.txt" file at the current application's folder
    * <li>Blackberry: the event logger, which can be accessed using alt+LGLG
    * <li>iPhone: the "DebugConsole.txt" file in the application's folder.
    * <li>Java SE: the System.err console
    * </ul>
    * You can delete the debug memo/txt file passing the ERASE_DEBUG, a constant String declared in this class. E.g.:
    * <code>Vm.debug(Vm.ERASE_DEBUG);</code>.
    * <p>
    * Note: if you're debugging a String that contains \0 in it, all chars after the first \0 are ignored.
    * @see Settings#nvfsVolume
    * @see #disableDebug
    * @see #ALTERNATIVE_DEBUG
    * @see #ERASE_DEBUG
    */
   public static void debug(String s)
   {
      if (disableDebug)
         return;
      if (s == null)
         s = "null";
      if (!ERASE_DEBUG.equals(s)) // guich@420_18 - guich@510_19: fixed NPE when s==null
         System.err.println(Settings.showDebugTimestamp ? (getTimeStamp() + " - " + s) : s); // alexmuc@400_63 - guich@567_16: now using getTimeStamp - guich@tc115_50: don't display timestamp if user don't want
      else
         System.err.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // just scroll the screen
      System.err.flush(); // guich@570_36
   }

   /**
    * Shows an alert IMMEDIATELY on screen. It uses the sytem message box to show the alert,
    * not the totalcross.ui.MessageBox class.
    * Showing an alert from Palm OS threads may reset the device.
    * <br><br>Note that unicode characters are not displayed on alerts.
    *
    * @since TotalCross 1.0
    */
   public static void alert(String s)
   {
      if (s == null)
         throw new NullPointerException("Argument 's' cannot have a null value");
      Launcher.instance.alert(s);
   }

   /**
    * This function can be used to show permanent warnings. It was created because Vm.debug is, in theory, just for
    * debugging. Some TotalCross classes displays warnings to the user, and are not debug (i.e., temporary) messages. It
    * just calls <code>Vm.debug("Warning! "+s)</code>, so it differs from it only in a semanthical way.
    * <p>
    * Note: you cannot use this function with ERASE_DEBUG; it won't work.
    *
    * @since SuperWaba 4.21
    */
   public static void warning(String s) // guich@421_4
   {
      debug("Warning! " + s);
   }

   private static class ClipboardObserver implements java.awt.datatransfer.ClipboardOwner
   {
      public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, java.awt.datatransfer.Transferable contents)
      {
      }
   }

   private static java.awt.datatransfer.ClipboardOwner defaultClipboardOwner = new ClipboardObserver();

   /** Copies the specific string to the clipboard. 
    */
   public static void clipboardCopy(String s)
   {
      java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(s), defaultClipboardOwner);
   }

   /** Gets the last string from the clipboard. if none, returns "". 
    */
   public static String clipboardPaste()
   {
      java.awt.datatransfer.Transferable content = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getContents(defaultClipboardOwner);
      if (content != null)
         try
         {
            return (String) (content.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor));
         }
         catch (Exception e)
         {
         }
      return "";
   }

   /**
    * Attaches a library to this application instance. TotalCross libraries are files that have a <b>Lib</b>
    * suffix.
    * <p>
    * This method is useful if you have a library and want to make its classes available to your app. At the
    * initialization, TotalCross scans all libraries in the device and automatically attach them. However, if
    * you downloaded a new library from a server or saved a bitmap into a file, you will need to attach the
    * file where it is located so you can instantiate its classes or load the image using <code>new Image(filename)</code>.
    * <p>
    * After a library is added, its file is blocked and thus cannot be modified.
    * <p>
    * To see what libraries are in the device, you can use <code>PDBFile.listPDBFiles</code> or
    * <code>File.listFiles</code> and check the files that ends with Lib
    * <p>
    * A check is made to prevent a library from being loaded twice.
    * @param name the library's name, with a 'Lib' suffix. Cannot contain a creator neither a type.
    * @return true if the library was found and attached, false otherwise.
    * @deprecated This method never worked in TotalCross. Previously, in SuperWaba, all class and resources were placed in a PDB file,
    * so attaching a pdb file was ok. However, in TotalCross, everything is placed in TCZ files, so attaching a PDB is useless.
    * To load an image that you saved with Image.saveTo, use Image.loadFrom.
    * @see totalcross.ui.image.Image#loadFrom
    */
   public static boolean attachLibrary(String name)
   {
      return false;
   }

   private static Hashtable htLoadedNatLibs = new Hashtable(13);

   /**
    * Attaches a native library to this application instance (only works at device). You must attach the native library
    * before use any native methods from your library, otherwise you'll get errors like "cannot find native method
    * implementation for ...". To learn how to create a native library, look at the Library Tutorial chapter. In desktop,
    * always returns true. A check is made to prevent a library from being loaded twice.
    * <br><br>Does not work on Android.
    * @param name the library's name. The suffix 'Lib' is not mandatory. Cannot contain a creator neither a type.
    */
   public static boolean attachNativeLibrary(String name)
   {
      if (htLoadedNatLibs.exists(name)) // guich@500_6: already loaded?
         return true;

      if (privateAttachNativeLibrary(name))
      {
         htLoadedNatLibs.put(name, name);
         return true;
      }
      return false;
   }

   private static boolean privateAttachNativeLibrary(String name)
   {
      return true;
   }

   /**
    * Returns a byte array of a file contained in the same tcz where this one resides, or in some attached library (or JAR, 
    * if application is running in a browser). The
    * returned array is a <i>fake</i> pointer; does NOT point directly to the file (a new buffer is created to store the
    * contents). Example:
    *
    * <pre>
    * byte[] b = Vm.getFile(&quot;textfiles/About.txt&quot;);
    * if (b != null)
    *    new MessageBox(&quot;About&quot;, new String(b, 0, b.length)).popup();
    * </pre>
    *
    * The TotalCross deployer will insert the .txt file into the program's tcz/pdb.
    *
    * @param name The name of the file, exactly as shown by tc.Deploy in the output window. In desktop it is case
    *           insensitive, while in device it is CASE SENSITIVE.
    * @return a byte array with the file or null if file not found
    */
   public static byte[] getFile(String name)
   {
      return Launcher.instance.readBytes(name);
   }

   /**
    * Returns the percentage of the remaining battery life of this device. In systems that can have an auxiliary
    * battery, it will return the sum of the main and the auxiliary battery, but this sum will always be limited to
    * 100%. In desktop, it will always return 100. Note that in some OSes, when on the cradle, the returned value may be
    * 100% even when charging.
    *
    * @since SuperWaba 4.21
    */
   public static int getRemainingBattery()
   {
      return 100;
   }

   /** Each time the garbage collector runs, it will issue a beep. To be used in the tweak method. */
   public static final int TWEAK_AUDIBLE_GC = 1;
   
   /** When the program ends, it will dump the memory status of the program with the following information:
    * <ul>
    * <li> Times gc was called: number of times the garbage collector was called. 
    * <li> Total gc time: total time in milisseconds that the gc took
    * <li> Chunks created: number of chunks (block of memory used to store objects, each one with 65500 bytes)
    * <li> Max allocated: maximum memory allocated.
    * </ul>
    * To be used in the tweak method. */
   public static final int TWEAK_DUMP_MEM_STATS = 2;
   
   /** Computes the maximum memory in use between two consecutive calls. For example:
    * <pre>
    * Vm.debug("P Starting profiler for xxxx");
    * Vm.tweak(Vm.TWEAK_MEM_PROFILER,true);
    * // now run the program during some time
    * Vm.tweak(Vm.TWEAK_MEM_PROFILER,false);
    * Vm.debug("P Stopping profiler for xxxx");
    * </pre>
    * When it starts, it prints the currently used memory.
    * When you set it off, it will dump the currently used memory (if different of the maximum),
    * and then the maximum memory used between the two calls.
    * For example:
    * <pre>
    * P Now allocated: 40852408 // printed when turning on
    * P Now allocated: 1137060  // printed when turning off
    * P Max allocated: 40868844 // printed when turning off
    * </pre> 
    * All profiler messages are prefixed with P in the debug console, because it makes easy to filter them.
    * To be used in the tweak method.
    * @since TotalCross 1.11 
    */
   public static final int TWEAK_MEM_PROFILER = 3;
   
   /** Disables the Garbage Collector. This can greatly decrease the time to load some things, but note that you 
    * must re-enable it later, otherwise, the system will run out of memory quickly.
    * Example:
    * <pre>
    * Vm.tweak(TWEAK_DISABLE_GC,true);
    * ...
    * Vm.tweak(TWEAK_DISABLE_GC,false); // the gc is automatically called here
    * </pre> 
    * @since TotalCross 1.14
    */
   public static final int TWEAK_DISABLE_GC = 4;

   /**
    * Tweak some parameters of the virtual machine. Note that these
    * parameters are only available at the device, NOT when running as Java.
    * Also, the configuration is not persisted:
    * you must set this every time you run the program.
    * Each tweak must be made separately; they are NOT bit masks. For example:
    * <pre>
    * Vm.tweak(Vm.TWEAK_AUDIBLE_GC,true);
    * Vm.tweak(Vm.TWEAK_DUMP_MEM_STATS,true);
    * </pre>
    *
    * @since SuperWaba 5.82
    * @see #TWEAK_AUDIBLE_GC
    * @see #TWEAK_DUMP_MEM_STATS
    * @see #TWEAK_MEM_PROFILER
    */
   public static void tweak(int param, boolean set) // guich@582_3
   {
   }

   /**
    * Returns the given throwable stack trace as a String.
    * A good alternative to show an exception to the user is:
    * <pre>
    * try
    * {
    *    ...
    * }
    * catch (Exception e)
    * {
    *    MessageBox.showException(e, false); // last parameter dumps to the console if true
    * }
    * </pre>
    *
    * @since SuperWaba 5.82
    */
   public static String getStackTrace(Throwable t) // guic@582_6
   {
      java.io.StringWriter sw = new java.io.StringWriter(); // guich@tc100b4_6: with StringWriter it always works
      t.printStackTrace(new java.io.PrintWriter(sw));
      String stacktrace = sw.toString();
      if (stacktrace != null)
         stacktrace = Convert.replace(stacktrace, Convert.CRLF, "\n").replace("\tat ","");
      return stacktrace;
   }

   /**
    * Set to true to popup an alert for each key pressed. This is useful for you to discover key codes that may be
    * intercepted with Vm.interceptSpecialKeys. Note that this will probably block the application and you will have to
    * provide a button to exit from it or just reset the device. <br>
    * <br>
    * On Windows CE, the key 91 is always displayed when a hotkey is pressed, so the vm will silently ignore it.
    *
    * @since TotalCross 1.0
    */
   public static void showKeyCodes(boolean on)
   {
      Launcher.instance.showKeyCodes = on;
   }
   
   /**
    * Turns the screen on or off, but keeps the device running. This greatly improves battery performance.
    * @return If the method succeed.
    * @since TotalCross 1.15
    */ 
   public static boolean turnScreenOn(boolean on) // guich@tc115_75
   {
      return true;
   }

   /** Prints the stack trace to the debug console file. 
    * Implemented as:
    * <pre>
      try 
      {
         throw new Exception("Stack trace");
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
      }
    * </pre>
    * @since TotalCross 1.3
    */
   public static void printStackTrace()
   {
      try 
      {
         throw new Exception("Stack trace");
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
      }
   }
   
   /** This method pre-allocates space for an array of objects with the given length.
    * It can reduce the number of calls to GC when allocating big arrays.
    * If the total size (Object size * length) is small, calling this method is useless
    * and will not result in any performance gain.
    * 
    * You can always measure how the gc affects your program by doing this:
    * 
    * <pre>
    * int gcc = Settings.gcCount, gct = Settings.gcTime, ini = Vm.getTimeStamp();
    * ... run some memory-consuming routine
    * Vm.debug("gcCount: "+(Settings.gcCount-gcc)+" ("+(Settings.gcTime-gct)+"ms), elapsed: "+(Vm.getTimeStamp()-ini)+"ms");
    * </pre>
    * 
    * This is a sample that pre-allocates space for a ListContainer, taken from the PopupMenu class:
    * 
    * <pre>
    * Vm.preallocateArray(new ListContainer.Item(layout), itemCount);
    * </pre>
    * 
    * This method does nothing under Blackberry and Java SE.
    * 
    * @since TotalCross 1.5
    */
   public static void preallocateArray(Object sample, int length)
   {
   }
}