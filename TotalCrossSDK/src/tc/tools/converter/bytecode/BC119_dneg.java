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

// $Id: BC119_dneg.java,v 1.9 2011-01-04 13:18:55 guich Exp $

package tc.tools.converter.bytecode;

public class BC119_dneg extends Arithmetic
{
   public BC119_dneg()
   {
      super(0,-1,-1,DOUBLE);
   }
   public void exec()
   {
      stack[stackPtr-1].asDouble = -stack[stackPtr-1].asDouble;
   }
}
