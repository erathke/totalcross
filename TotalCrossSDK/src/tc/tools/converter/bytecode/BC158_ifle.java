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

// $Id: BC158_ifle.java,v 1.9 2011-01-04 13:18:57 guich Exp $

package tc.tools.converter.bytecode;

public class BC158_ifle extends ConditionalBranch
{
   public BC158_ifle()
   {
      super(-1,-1,0,readInt16(pc+1),INT);
   }
   public void exec()
   {
      if (stack[stackPtr-1].asInt <= 0)
         pcInc = jumpIfTrue;
      else
         pcInc = jumpIfFalse;
   }
}
