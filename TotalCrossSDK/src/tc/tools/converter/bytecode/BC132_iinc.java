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

// $Id: BC132_iinc.java,v 1.12 2011-01-04 13:18:57 guich Exp $

package tc.tools.converter.bytecode;

public class BC132_iinc extends Arithmetic
{
   public BC132_iinc()
   {
      super(0, readUInt8(pc+1), code[pc+2],INT);
      pcInc = 3;
   }
   public BC132_iinc(boolean wide)
   {
      super(0, readUInt16(pc+2), readInt16(pc+4),INT); // note: pc+1 stores the opcode
      pcInc = 5;
      bc = IINC;
   }
   public void exec()
   {
      local[result].asInt += operand;
   }
}
