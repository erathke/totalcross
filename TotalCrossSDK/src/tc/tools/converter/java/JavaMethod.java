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

// $Id: JavaMethod.java,v 1.15 2011-01-04 13:19:07 guich Exp $

package tc.tools.converter.java;

import totalcross.io.DataStream;
import totalcross.util.ElementNotFoundException;
import totalcross.util.Vector;

public final class JavaMethod
{
   public String               name, ret, signature;
   public JavaCode             code;
   public JavaClass            classOfMethod;
   public String[]             checkedExceptions;
   public boolean              isPublic, isPrivate, isProtected, isStatic, isFinal, isVolatile, isTransient, isNative,
         isAbstract, isStrict, isSynchronized;
   // method signature
   public String[]             params;
   public int                  paramCount;
   private static Vector       vec   = new Vector(20);
   private static StringBuffer retsb = new StringBuffer(30);

   public static String[] splitParams(String s, StringBuffer ret) // return the parameters array and the return type in the string buffer
   {
      Vector v = vec;
      v.removeAllElements();
      ret.setLength(0);
      int n = s.length();
      for (int i = 1; i < n; i++)
         switch (s.charAt(i))
         {
            case '[': // array?
            {
               int last = i;
               do
               {
                  last++;
               }
               while (s.charAt(last) == '[');
               if (s.charAt(last) == 'L')
                  last = s.indexOf(';', last);
               String param = s.substring(i, last + 1);
               v.addElement(param);
               i = last;
               break;
            }
            case 'L': // object
               int i2 = s.indexOf(';', i + 1);
               String param = s.substring(i, i2 + 1);
               i = i2;
               v.addElement(param);
               break;
            case '(':
            case ')':
               break;
            default:
               v.addElement(s.substring(i, i + 1));
         }
      try
      // the return is the last added parameter
      {
         ret.append((String) v.pop());
      }
      catch (ElementNotFoundException e)
      {
      }
      return (String[]) v.toObjectArray();
   }

   public JavaMethod(JavaClass jc, DataStream ds, JavaConstantPool cp) throws totalcross.io.IOException
   {
      this.classOfMethod = jc;
      int f = ds.readUnsignedShort();
      isPublic    = (f & 0x1) != 0;
      isPrivate   = (f & 0x2) != 0;
      isProtected = (f & 0x4) != 0;
      isStatic    = (f & 0x8) != 0;
      isFinal     = (f & 0x10) != 0;
      isSynchronized = (f & 0x20) != 0;
      isNative    = (f & 0x100) != 0;
      isAbstract  = (f & 0x400) != 0;
      isStrict    = (f & 0x800) != 0;

      name = (String) cp.constants[ds.readUnsignedShort()];
      String parameters = (String) cp.constants[ds.readUnsignedShort()];
      params = splitParams(parameters, retsb);
      if (params != null)
         paramCount = params.length;
      this.ret = retsb.toString();
      signature = name + parameters.substring(0, parameters.length() - ret.length()); // (I)V -> (I)

      // read the attributes
      int n = ds.readUnsignedShort();
      for (int i = 0; i < n; i++)
      {
         String name = (String) cp.constants[ds.readUnsignedShort()];
         int len = ds.readInt();
         if (false)
            System.out.println("Method attribute: " + name);
         if (name.equals("Code") || name.equals("JavaCode"))
            code = new JavaCode(this, ds, cp);
         else if (name.equals("Exceptions"))
         {
            checkedExceptions = new String[ds.readUnsignedShort()];
            for (int j = 0; j < checkedExceptions.length; j++)
               checkedExceptions[j] = cp.getString1(ds.readUnsignedShort());
         }
         else
            ds.skipBytes(len);
      }
   }

   public String toString()
   {
      return "Method " + signature;
   }
}
