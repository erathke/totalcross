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

// $Id: Window_c.h,v 1.13 2011-01-04 13:31:07 guich Exp $

#ifdef __cplusplus
extern "C" {
#endif

void windowSetSIP(Context currentContext, int32 sipOption, Object control, bool secret);

static void windowSetDeviceTitle(Object titleObj)
{
   UNUSED(titleObj)
}

#ifdef __cplusplus
}
#endif
