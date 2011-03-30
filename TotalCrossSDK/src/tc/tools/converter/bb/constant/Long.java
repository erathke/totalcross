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

// $Id: Long.java,v 1.6 2011-01-04 13:18:59 guich Exp $

package tc.tools.converter.bb.constant;

import totalcross.io.DataStream;
import totalcross.io.IOException;

public class Long implements ConstantInfo
{
   public long value;

   public java.lang.String toString()
   {
      return "" + value;
   }

   public int length()
   {
      return 8;
   }

   public void load(DataStream ds) throws IOException
   {
      value = ds.readLong();
   }

   public void save(DataStream ds) throws IOException
   {
      ds.writeLong(value);
   }
}
