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

// $Id: RemotePDBRecord.java,v 1.9 2011-01-04 13:19:01 guich Exp $

package totalcross.io.sync;

import totalcross.io.DataStream;

/** This class represent a Remote PDBFile Record. */

public abstract class RemotePDBRecord
{
   protected RemotePDBFile rc;
   protected int size;

   protected abstract void write(DataStream ds);
   protected abstract void read(DataStream ds) throws totalcross.io.IOException;
}
