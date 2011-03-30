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

// $Id: SHA256Digest.java,v 1.5 2011-01-04 13:18:58 guich Exp $

package totalcross.crypto.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class implements the SHA-256 message digest algorithm.
 */
public class SHA256Digest extends Digest
{
   /**
    * Creates a new SHA256Digest object.
    */
   public SHA256Digest()
   {
      try
      {
         digestRef = MessageDigest.getInstance("SHA-256");
      }
      catch (NoSuchAlgorithmException e) {}
   }
   
   public final String getAlgorithm()
   {
      return "SHA-256";
   }
   
   public final int getBlockLength()
   {
      return 64;
   }
   
   public final int getDigestLength()
   {
      return 32;
   }
   
   protected final byte[] process(byte[] data)
   {
      MessageDigest digest = (MessageDigest)digestRef;
      digest.reset();
      digest.update(data);
      
      return ((MessageDigest)digestRef).digest();
   }
}
