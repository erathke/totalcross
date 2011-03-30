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

// $Id: AESKey.java,v 1.7 2011-01-04 13:19:16 guich Exp $

package totalcross.crypto.cipher;

/**
 * This class implements the AES cryptographic cipher key.
 */
public class AESKey extends Key
{
   private byte[] data;
   
   /**
    * Creates a new AESKey object with the given data.
    * @param data a byte array containing the key data.
    */
   public AESKey(byte[] data)
   {
      this.data = data;
   }
   
   /**
    * @return a copy of the byte array containing the key data.
    */
   public byte[] getData()
   {
      return data;
   }
}
