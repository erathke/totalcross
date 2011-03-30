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

// $Id: BC131_lxor.java,v 1.9 2011-01-04 13:18:57 guich Exp $

package tc.tools.converter.bytecode;

public class BC131_lxor extends Logical
{
   public BC131_lxor()
   {
      super(-1,-2,-1,LONG);
   }
   public void exec()
   {
      stack[-2].asLong ^= stack[-1].asLong;
   }
}
