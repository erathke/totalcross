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

// $Id: BC161_if_icmplt.java,v 1.9 2011-01-04 13:18:54 guich Exp $

package tc.tools.converter.bytecode;

public class BC161_if_icmplt extends ConditionalBranch
{
   public BC161_if_icmplt()
   {
      super(-2,-2,-1,readInt16(pc+1),INT);
   }
   public void exec()
   {
      if (stack[stackPtr-2].asInt < stack[stackPtr-1].asInt)
         pcInc = jumpIfTrue;
      else
         pcInc = jumpIfFalse;
   }
}
