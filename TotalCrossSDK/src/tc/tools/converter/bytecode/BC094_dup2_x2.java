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

// $Id: BC094_dup2_x2.java,v 1.9 2011-01-04 13:18:55 guich Exp $

package tc.tools.converter.bytecode;

public class BC094_dup2_x2 extends StackManipulation
{
   public BC094_dup2_x2()
   {
      super(2,true);
   }
   public void exec()
   {
      stack[stackPtr+1].copyFrom(stack[stackPtr-1]);
      stack[stackPtr  ].copyFrom(stack[stackPtr-2]);
      stack[stackPtr-1].copyFrom(stack[stackPtr-3]);
      stack[stackPtr-2].copyFrom(stack[stackPtr-4]);
      stack[stackPtr-3].copyFrom(stack[stackPtr+1]);
      stack[stackPtr-4].copyFrom(stack[stackPtr  ]);
   }
}
