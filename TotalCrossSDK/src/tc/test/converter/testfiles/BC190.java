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

// $Id: BC190.java,v 1.8 2011-01-04 13:19:03 guich Exp $

package tc.test.converter.testfiles;

public class BC190
{
   public BC190()
   {
      int len = 0;

      int arInt[] = new int[4];
      len = arInt.length;

      arInt = new int[32];
      len = arInt.length;

      arInt = new int[len];
      len = arInt.length;
   }
}
