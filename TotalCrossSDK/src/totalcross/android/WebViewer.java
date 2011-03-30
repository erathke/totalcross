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

// $Id: WebViewer.java,v 1.2 2011-01-04 13:19:27 guich Exp $

package totalcross.android;

import android.app.*;
import android.os.*;
import android.view.*;
import android.webkit.*;

public class WebViewer extends Activity 
{
   String url;
   public void onCreate(Bundle savedInstanceState) 
   {
      super.onCreate(savedInstanceState);
      WebView webview = new WebView(this);
      setContentView(webview);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      webview.loadUrl(getIntent().getExtras().getString("url"));
   }
}
