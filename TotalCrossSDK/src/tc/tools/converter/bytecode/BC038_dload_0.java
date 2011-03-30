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

// $Id: BC038_dload_0.java,v 1.9 2011-01-04 13:18:56 guich Exp $

package tc.tools.converter.bytecode;

public class BC038_dload_0 extends LoadLocal
{
   public BC038_dload_0()
   {
      super(0,DOUBLE);
      stackInc = 2;
   }
}
