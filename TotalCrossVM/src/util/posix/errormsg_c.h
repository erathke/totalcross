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

// $Id: errormsg_c.h,v 1.7 2011-01-04 13:31:15 guich Exp $

#include <string.h>

CharP privateGetErrorMessage(int32 errorCode, CharP buffer, uint32 size)
{
   if (strerror_r(errorCode, buffer, size))
      return null;
   return buffer;
}
