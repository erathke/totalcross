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

// $Id: ClassConsistencyException.java,v 1.7 2011-01-04 13:19:21 guich Exp $

package tc.tools.converter.bb;

public class ClassConsistencyException extends Exception
{
   public ClassConsistencyException(JavaClass jc1, String reason)
   {
      super("Class '" + jc1 + "' is not consistent" + (reason != null ? ": " + reason : ""));
   }

   public ClassConsistencyException(JavaClass jc1, JavaClass jc2, String reason)
   {
      super("Class '" + jc1 + "' is not consistent with '" + jc2 + "'" + (reason != null ? ": " + reason : ""));
   }
}
