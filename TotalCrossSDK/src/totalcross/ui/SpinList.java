/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2012 SuperWaba Ltda.                                      *
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



package totalcross.ui;

import totalcross.sys.*;
import totalcross.ui.dialog.*;
import totalcross.ui.event.*;
import totalcross.ui.gfx.*;
import totalcross.util.*;

/** Creates a control with two arrows, so you can scroll values and show
  * the current one.
  * It supports auto-scroll (by clicking and holding) and can also 
  * dynamically compute the items based on ranges.
  * 
  * The SpinList can be horizontal or vertical. You can use something like:
  * <pre>
  * SpinList sl = new SpinList(..., !Settings.fingerTouch);
  * </pre>
  * This way, in finger-touch devices, it will use the horizontal appearance,
  * which is easier to deal on such devices. 
  */

public class SpinList extends Control
{
   protected String []choices;
   protected int selected;
   protected TimerEvent timer;
   /** Timer interval in which the scroll will be done. */
   public int timerInterval=300;
   /** Number of ticks of the timer interval that will be waiten until the scroll starts. */
   public int timerInitialDelay = 3;
   /** The horizontal text alignment of the SpinList: LEFT, CENTER or RIGHT */
   public int hAlign = LEFT;
   private boolean goingUp;
   private int tick;
   private boolean isVertical;
   /** Set to true if there are only numbers in the SpinList and you want to open a NumericBox to let the user
    * enter a value. The SpinList is divided into 3 areas: left (decrease), right (increase), middle (opens the NumericBox).
    * Works only when isVertical is false.
    * The area that pops up the NumericBox is drawn in a darker background.
    * @since TotalCross 1.5
    * 
    * Setting this to true shows a numeric box.
    */
   public boolean useNumericBox;
   
   /** Set to true if there are only numbers in the SpinList and you want to open a NumericBox to let the user
    * enter a value. The SpinList is divided into 3 areas: left (decrease), right (increase), middle (opens the NumericBox).
    * Works only when isVertical is false.
    * The area that pops up the NumericBox is drawn in a darker background.
    * 
    * Setting this to true shows a calculator box.
    * @since TotalCross 1.53
    */
   public boolean useCalculatorBox;
   
   /** Constructs a vertical SpinList with the given choices, selecting index 0 by default.
    * @see #setChoices 
    */
   public SpinList(String[] choices) throws InvalidNumberException
   {
      this(choices, true);
   }
   
   /** Constructs a vertical SpinList with the given choices, selecting index 0 by default.
    * @see #setChoices 
    */
   public SpinList(String[] choices, boolean isVertical) throws InvalidNumberException
   {
      this.isVertical = isVertical;
      setChoices(choices);
   }
   
   public int getPreferredWidth()
   {
      int w=fm.getMaxWidth(choices,0,choices.length);
      if (w == 0)
         return Settings.screenWidth/2;
      int aw = getArrowHeight() * 2;
      return isVertical ? w + aw : w + aw+2;
   }
   
   public int getPreferredHeight()
   {
      return fmH + Edit.prefH;
   }

   /** Sets the choices to the given ones. Searches for [i0,if] and then expands the items.
    * For example, passing some string as "Day [1,31]" will expand that to an array of 
    * <code>"Day 1","Day 2",...,"Day 31"</code>.
    */
   public void setChoices(String []choices) throws InvalidNumberException
   {
      if (choices == null) choices = new String[]{""};
      else
      {
         Vector v = new Vector(choices.length+10);
         for (int i =0; i < choices.length; i++)
            if (choices[i].indexOf('[') != -1)
               expand(v,choices[i]);
            else 
               v.addElement(choices[i]);
         if (choices.length != v.size())
            choices = (String[])v.toObjectArray();
      }
      this.choices = choices;
      selected = 0;
      Window.needsPaint = true;
   }
   
   /** Expands the items in the format "prefix [start,end] suffix", where prefix and suffix are optional.
    * For example, passing some string as "Day [1,31]" will expand that to an array of 
    * <code>"Day 1","Day 2",...,"Day 31"</code>.
    */
   public static void expand(Vector v, String str) throws InvalidNumberException
   {
      int ini = str.indexOf('[');
      int fim = str.indexOf(']');
      String prefix = str.substring(0,ini);
      String sufix  = str.substring(fim+1);
      int j;
      int start = Convert.toInt(str.substring(ini+1,j=str.indexOf(',',ini+1)));
      int end   = Convert.toInt(str.substring(j+1,fim));
      for (int k =start; k <= end; k++)
         v.addElement(prefix+k+sufix);
   }

   /** Returns the choices array, after the expansion (if any). */
   public String[] getChoices()
   {
   	return choices;
   }

   /** Returns the selected item. */
   public String getSelectedItem()
   {
      return choices[selected];
   }

   /** Returns the selected index. */
   public int getSelectedIndex()
   {
      return selected;
   }

   /** Sets the selected item; -1 is NOT accepted. */
	public void setSelectedIndex(int i)
	{
  	   if (0 <= i && i < choices.length && selected != i)
  	   {
		   selected = i;
		   Window.needsPaint = true;
	      if (Settings.sendPressEventOnChange)
	         postPressedEvent();
		}
	}
	
	/** Selects the given item. If the item is not found, the selected index remains unchanged. */
	public void setSelectedItem(String item)
	{
	   setSelectedIndex(indexOf(item));
	}
   
   /** Removes the item at the given index. */
	public String removeAt(int index)
	{
   	String ret = choices[index];
   	int last = choices.length-1;
   	String []ch = new String[last];
		Vm.arrayCopy(choices,0,ch,0,index);
      if (index < last)
      	Vm.arrayCopy(choices,index+1,ch,index,last-index);
      else
         selected--;
      this.choices = ch;
      Window.needsPaint = true;
      return ret;
	}
   
   /** Removes the current item */
   public String removeCurrent()
   {
   	return removeAt(selected);
   }
   
   /** Returns the index of the given item. */
   public int indexOf(String elem)
   {
      for (int i = 0; i < choices.length; i++)
         if (choices[i].equals(elem))
            return i;
      return -1;
   }
   
   /** Inserts the given element in order (based in the assumption that the original choices was ordered). */
	public void insertInOrder(String elem)
	{
   	// find the correct position to insert
      int index = 0;
      while (index < choices.length && elem.compareTo(choices[index]) > 0)
         index++;
      if (index == choices.length || !elem.equals(choices[index]))
      {
	      String []ch = new String[choices.length+1];
	      Vm.arrayCopy(choices,0,ch,0,index);
	      ch[index] = elem;
	      if (index < choices.length)
	      	Vm.arrayCopy(choices,index,ch,index+1,choices.length-index);
	      choices = ch;
	      selected = index;
	      Window.needsPaint = true;
      }
	}

   private int getArrowHeight()
   {
      return isVertical ? 4*fmH/11 : fmH/2;
   }
   
   public void onPaint(Graphics g)
   {
      g.backColor = backColor; // guich@341_3
      g.fillRect(0,0,width,height);
      int fore = enabled ? foreColor : Color.getCursorColor(foreColor);
      g.foreColor = fore;
      int yoff = (height - fmH) / 2 + 1;
      int wArrow = getArrowHeight();
      String s = choices.length > 0 ? choices[selected] : "";
      if (isVertical)
      {
         g.drawArrow(0,yoff,wArrow,Graphics.ARROW_UP,false,fore);
         g.drawArrow(0,yoff+height/2,wArrow,Graphics.ARROW_DOWN,false,fore);
         if (choices.length > 0) 
            g.drawText(choices[selected],hAlign==LEFT?wArrow*2:hAlign==RIGHT?width-fm.stringWidth(s):(width-fm.stringWidth(s))/2,yoff-1, textShadowColor != -1, textShadowColor);
      }
      else
      {
         if (useNumericBox || useCalculatorBox)
         {
            g.backColor = Color.darker(g.backColor,16);
            g.fillRect(width/3,0,width/3,height);
         }
         g.drawArrow(0,yoff,wArrow,Graphics.ARROW_LEFT,false,fore);
         g.drawArrow(width-wArrow,yoff,wArrow,Graphics.ARROW_RIGHT,false,fore);
         if (choices.length > 0) 
            g.drawText(choices[selected],hAlign==LEFT?wArrow:hAlign==RIGHT?width-fmH/2-1-fm.stringWidth(s):(width-fm.stringWidth(s))/2,yoff-1, textShadowColor != -1, textShadowColor);
      }
   }
   
   private void scroll(boolean up, boolean doPostEvent)
   {
      int max = choices.length-1;
  	   if (up)
      {
   	   selected--;
	      if (selected < 0) selected = max;
      }
      else
      {
         selected++;
	      if (selected > max) selected = 0;
      }
      Window.needsPaint = true;
      if (doPostEvent) 
         postPressedEvent();
   }
   
   public void onEvent(Event event)
   {
      switch (event.type)
      {
         case KeyEvent.KEY_PRESS:
         {
            KeyEvent ke = (KeyEvent)event;
            int key = ke.key;
            if (key == ' ') selected = 0; // restart a search
            else
            {
               key = Convert.toLowerCase((char)key); // converts to uppercase
               for (int i =0; i < choices.length; i++)
                  if (choices[i].charAt(0) == (char)key)
                  {
                     selected = i;
                     Window.needsPaint = true;
                     break;
                  }
            }
            break;
         }
         case KeyEvent.SPECIAL_KEY_PRESS:
         {
            KeyEvent ke = (KeyEvent)event;
            if (Settings.keyboardFocusTraversable && ke.isActionKey()) // guich@550_15
               postPressedEvent();
            else
            if (ke.isUpKey())
               scroll(true,!Settings.keyboardFocusTraversable);
            else
            if (ke.isDownKey())
               scroll(false,!Settings.keyboardFocusTraversable);
            break;
         }
	      case PenEvent.PEN_DOWN:
	      {
	         PenEvent pe = (PenEvent)event;
	         goingUp = isVertical ? pe.y > height/2 : pe.x < width/2;
	         if (!Settings.fingerTouch)
	            doScroll((PenEvent)event);
				if (timer == null)
            {
	            tick = 0;
	            timer = addTimer(timerInterval);
            }
            break;
	      }
	      case PenEvent.PEN_UP:
	      {
            PenEvent pe = (PenEvent)event;
            stopTimer();
	         if (Settings.fingerTouch && !hadParentScrolled())
	         {
	            if (!isVertical && (useNumericBox || useCalculatorBox) && width/3 <= pe.x && pe.x <= 2*width/3)
	            {
	               CalculatorBox nb = new CalculatorBox(useCalculatorBox);
	               if (useNumericBox)
   	               nb.maxLength = Math.max(choices[0].length(),choices[choices.length-1].length());
	               nb.popup();
	            }
	            else doScroll((PenEvent)event);
	         }
	 	   	break;
	      }
 		   case TimerEvent.TRIGGERED:
 		      if (hadParentScrolled() || !isTopMost())
 		         stopTimer();
 		      else
            if (timer.triggered && tick++ > timerInitialDelay)
               scroll(goingUp,!Settings.keyboardFocusTraversable);
            break;
   	}
   }
   
   private void doScroll(PenEvent pe)
   {
      if (!isVertical || pe.x < getArrowHeight()*2)
      {
         goingUp = isVertical ? pe.y > height/2 : pe.x < width/2;
         scroll(goingUp,true);
      }
   }

   private void stopTimer()
   {
      if (timer != null)
      {
         removeTimer(timer);
         timer = null;
      }
   }

   /** Clears this control, selecting element clearValueInt. */
   public void clear()
   {
      setSelectedIndex(clearValueInt);
   }

   public Control handleGeographicalFocusChangeKeys(KeyEvent ke)
   {
       if (!ke.isUpKey() && !ke.isDownKey()) return null;
       _onEvent(ke);
       return this;
   }
}