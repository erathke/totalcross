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

// $Id: BC180_getfield.java,v 1.8 2011-01-04 13:18:55 guich Exp $

package tc.tools.converter.bytecode;

public class BC180_getfield extends LoadStoreField
{
   public BC180_getfield()
   {
      super(readUInt16(pc+1));
      stackInc = 0;
   }
   public void exec()
   {
      classInstance = (String)stack[stackPtr].asObj;
      stack[stackPtr].asObj = fieldName;
   }
}
