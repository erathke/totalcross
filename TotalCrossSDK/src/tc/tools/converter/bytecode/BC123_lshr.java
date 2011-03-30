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

// $Id: BC123_lshr.java,v 1.9 2011-01-04 13:18:54 guich Exp $

package tc.tools.converter.bytecode;

public class BC123_lshr extends Logical
{
   public BC123_lshr()
   {
      super(-1,-2,-1,LONG);
   }
   public void exec()
   {
      stack[-2].asLong >>= stack[-1].asLong & 0x1F;
   }
}
