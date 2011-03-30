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

// $Id: AddressException.java,v 1.3 2011-01-04 13:18:59 guich Exp $

package totalcross.net.mail;

/**
 * Thrown by the Address constructor if the given String address is invalid
 * 
 * @since TotalCross 1.13
 */
public class AddressException extends Exception
{
   /** Constructs an empty Exception. */
   public AddressException()
   {
      super();
   }

   /** Constructs an exception with the given message. */
   public AddressException(String msg)
   {
      super(msg);
   }
}
