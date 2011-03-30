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

// $Id: NameAndType.java,v 1.6 2011-01-04 13:18:59 guich Exp $

package tc.tools.converter.bb.constant;

import tc.tools.converter.bb.JavaClass;

public class NameAndType extends DoubleReference
{
   public NameAndType(JavaClass jclass)
   {
      super(jclass);
   }

   public UTF8 getValue1AsName()
   {
      return (UTF8)value1.info;
   }

   public UTF8 getValue2AsDescriptor()
   {
      return (UTF8)value2.info;
   }
}
