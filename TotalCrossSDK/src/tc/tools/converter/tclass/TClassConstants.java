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

// $Id: TClassConstants.java,v 1.21 2011-01-04 13:19:25 guich Exp $

package tc.tools.converter.tclass;

public interface TClassConstants
{
   // this order can't be changed, it impacts the compiler and the ObjectMemoryManager.
   // Type enum
   public static final int Type_Null         = 0;
   public static final int Type_Void         = 1;
   public static final int Type_Boolean      = 2;
   public static final int Type_Byte         = 3;
   public static final int Type_Char         = 4;
   public static final int Type_Short        = 5;
   public static final int Type_Int          = 6;
   public static final int Type_Long         = 7;
   public static final int Type_Float        = 8;
   public static final int Type_Double       = 9;
   public static final int Type_String       = 10;
   public static final int Type_Object       = 11;
   public static final int Type_BooleanArray = 12;
   public static final int Type_ByteArray    = 13;
   public static final int Type_CharArray    = 14;
   public static final int Type_ShortArray   = 15;
   public static final int Type_IntArray     = 16;
   public static final int Type_LongArray    = 17;
   public static final int Type_FloatArray   = 18;
   public static final int Type_DoubleArray  = 19;
   public static final int Type_StringArray  = 20;
   public static final int Type_ObjectArray  = 21;

   // Constant pool entries - TempConstantPool
   public static final int POOL_I32 = 1;
   public static final int POOL_I64 = 2;
   public static final int POOL_DBL = 3;
   public static final int POOL_STR = 4;
   public static final int POOL_CLS = 5;
   public static final int POOL_SYM = 6;
   public static final int POOL_MTD = 7;
   public static final int POOL_SF = 8;
   public static final int POOL_IF = 9;

   // RegType enum
   public static final int RegI = 0;
   public static final int RegO = 1;
   public static final int RegD = 2;
   public static final int RegL = 3;

   public static final int SYM12 = 12;
   public static final int SYM16 = 16;

   public static final String CONSTRUCTOR_NAME = "<C>"; // name of the constructor
   public static final String STATIC_INIT_NAME = "<S>"; // name of the static initializer
}
