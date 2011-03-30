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

// $Id: PDBFileTest.java,v 1.10 2011-01-04 13:19:32 guich Exp $

package tc.test.totalcross.io;

import totalcross.io.IOException;
import totalcross.io.PDBFile;
import totalcross.unit.TestCase;

public class PDBFileTest extends TestCase
{
   public void testRun()
   {
      String name = "PDBFileTest.Test.Test";
      PDBFile c;
      try
      {
         c = new PDBFile(name, PDBFile.READ_WRITE);
         c.delete();
      }
      catch (IOException e)
      {
         fail(e.getMessage());
      }

      try
      {
         c = new PDBFile(name, PDBFile.CREATE_EMPTY);
         c.delete();
      }
      catch (IOException e)
      {
         fail(e.getMessage());
      }

      try
      {
         c = new PDBFile(name, PDBFile.CREATE);
         c.addRecord(100);
         c.addRecord(50);
         c.setRecordPos(0);
         c.deleteRecord();
         c.setRecordPos(1);
         fail();
         c.setRecordPos(0);
         c.close();
      }
      catch (IOException e)
      {
         fail(e.getMessage());
      }

      try
      {
         c = new PDBFile(name, PDBFile.CREATE_EMPTY);
         assertEquals(c.getRecordCount(), 0);
         c.addRecord(100);
         c.addRecord(50);
         c.setRecordPos(0);
         c.deleteRecord();
         c.setRecordPos(1);
         fail();
         c.setRecordPos(0);
         c.close();
      }
      catch (IOException e)
      {
         fail(e.getMessage());
      }
   }

}
