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

// $Id: Reference.java,v 1.6 2011-01-04 13:18:59 guich Exp $

package tc.tools.converter.bb.constant;

import tc.tools.converter.bb.JavaClass;
import tc.tools.converter.bb.JavaConstant;
import totalcross.io.DataStream;
import totalcross.io.IOException;

abstract class Reference implements ConstantInfo
{
   private JavaClass jclass;
   public JavaConstant value;

   public Reference(JavaClass jclass)
   {
      this.jclass = jclass;
   }

   public java.lang.String toString()
   {
      return "[" + value.toString() + "]";
   }

   public int length()
   {
      return 2;
   }

   public void load(DataStream ds) throws IOException
   {
      value = jclass.getConstant(ds.readUnsignedShort(), this);
   }

   public void save(DataStream ds) throws IOException
   {
      ds.writeShort(jclass.getConstantIndex(value, this));
   }
}
