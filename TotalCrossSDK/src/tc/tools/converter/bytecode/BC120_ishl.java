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

// $Id: BC120_ishl.java,v 1.9 2011-01-04 13:18:55 guich Exp $

package tc.tools.converter.bytecode;

public class BC120_ishl extends Logical
{
   public BC120_ishl()
   {
      super(-1,-2,-1,INT);
   }
   public void exec()
   {
      stack[-2].asInt <<= stack[-1].asInt & 0x1F;
   }
}
