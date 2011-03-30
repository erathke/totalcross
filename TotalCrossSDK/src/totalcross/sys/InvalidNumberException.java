/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2011 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *  This file is covered by the GNU LESSER GENERAL PUBLIC LICENSE VERSION 3.0    *
 *  A copy of this license is located in file license.txt at the root of this    *
 *  SDK or can be downloaded here:                                               *
 *  http://www.gnu.org/licenses/lgpl-3.0.txt                                     *
 *                                                                               *
 *********************************************************************************/

// $Id: InvalidNumberException.java,v 1.7 2011-01-04 13:19:10 guich Exp $

package totalcross.sys;

/** Thrown when you try to convert a String that does not represents a valid number. */

public class InvalidNumberException extends Exception
{
   /** Constructs an empty Exception. */
   public InvalidNumberException()
   {
   }

   /** Constructs an exception with the given message. */
   public InvalidNumberException(String message)
   {
      super(message);
   }
}
