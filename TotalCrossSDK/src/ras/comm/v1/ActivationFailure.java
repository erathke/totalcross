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

// $Id: ActivationFailure.java,v 1.8 2011-01-04 13:19:21 guich Exp $

package ras.comm.v1;

import totalcross.io.DataStream;
import totalcross.io.IOException;

public class ActivationFailure extends ActivationResponse
{
   private String reason;
   
   public ActivationFailure()
   {
   }
   
   public ActivationFailure(ActivationRequest request, String reason)
   {
      super(request);
      this.reason = reason;
   }
   
   public String getReason()
   {
      return reason;
   }

   protected void read(DataStream ds) throws IOException
   {
      super.read(ds);
      reason = ds.readString();
   }

   protected void write(DataStream ds) throws IOException
   {
      super.write(ds);
      ds.writeString(reason);
   }
}
