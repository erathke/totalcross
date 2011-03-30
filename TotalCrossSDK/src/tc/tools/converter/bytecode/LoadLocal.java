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

// $Id: LoadLocal.java,v 1.9 2011-01-04 13:18:57 guich Exp $

package tc.tools.converter.bytecode;

public class LoadLocal extends ByteCode
{
   /** Index in the local array */
   public int localIdx;

   public LoadLocal(int idx, int type)
   {
      this.localIdx = idx;
      this.targetType = type;
   }
   public void exec()
   {
      stack[stackPtr].copyFrom(local[localIdx]);
   }
}
