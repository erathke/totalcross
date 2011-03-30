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

// $Id: UTF8.java,v 1.6 2011-01-04 13:18:59 guich Exp $

package tc.tools.converter.bb.constant;

import totalcross.io.DataStream;
import totalcross.io.IOException;

public class UTF8 implements ConstantInfo
{
   public java.lang.String value;

   public java.lang.String toString()
   {
      return value;
   }

   public int length()
   {
      return 2 + value.length();
   }

   public void load(DataStream ds) throws IOException
   {
      value = ds.readString();
   }

   public void save(DataStream ds) throws IOException
   {
      ds.writeString(value);
   }
}
