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

// $Id: XYChart.java,v 1.9 2011-01-04 13:19:02 guich Exp $

package totalcross.ui.chart;

import totalcross.ui.*;
import totalcross.ui.gfx.Coord;
import totalcross.ui.gfx.Graphics;
import totalcross.util.Vector;

/** XYChart is a scatter chart. */

public class XYChart extends PointLineChart
{
   /**
    * Creates a new XY (scatter) chart
    */
   public XYChart()
   {
      border = new Insets(5, 5, 5, 5);
      showLines = true;
      showPoints = true;
   }

   public void onPaint(Graphics g)
   {
      if (!draw(g)) // draw axis, title, etc
         return;

      // Update points
      int sCount = series.size();
      for (int i = 0; i < sCount; i ++) // for each series
      {
         if (i >= points.size())
            points.addElement(new Vector());

         Vector v = (Vector) points.items[i];
         Series s = (Series) series.items[i];
         double[] xValues = s.xValues;
         double[] yValues = s.yValues;

         int vCount = xValues.length;
         for (int j = 0; j < vCount; j ++) // for each category
         {
            if (j >= v.size())
               v.addElement(new Coord());
            Coord c = (Coord) v.items[j];

            c.x = getXValuePos(xValues[j]);
            c.y = getYValuePos(yValues[j]);
         }
      }

      super.onPaint(g);
   }
}
