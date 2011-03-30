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

// $Id: BC151_dcmpl.java,v 1.9 2011-01-04 13:18:54 guich Exp $

package tc.tools.converter.bytecode;

public class BC151_dcmpl extends Comparison
{
   public BC151_dcmpl()
   {
      super(-1,-2,-2,-1,DOUBLE);
   }
   public void exec()
   {
      double r = stack[stackPtr-2].asDouble - stack[stackPtr-1].asDouble;
      stack[stackPtr-2].asInt = (r > 0)?1:(r < 0)?-1:0;
   }
}
