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

// $Id: event.h,v 1.24 2011-03-28 19:29:36 guich Exp $

#ifndef TC_EVENT_H
#define TC_EVENT_H

void mainEventLoop(Context currentContext);
void pumpEvents(Context currentContext);
bool isEventAvailable();

bool initEvent();
void destroyEvent();

/// hardware events that can be sent by the vm to the running instance
typedef enum
{
   KEYEVENT_KEY_PRESS = 100,
   KEYEVENT_SPECIALKEY_PRESS = 102,
   PENEVENT_PEN_DOWN = 200,
   PENEVENT_PEN_UP,
   PENEVENT_PEN_DRAG,
   MOUSEEVENT_MOUSE_MOVE = 205,
   CONTROLEVENT_SCREEN_RESIZED = 305,
   TIMEREVENT_TRIGGERED = 350,
   MEDIACLIPEVENT_STARTED = 550,
   MEDIACLIPEVENT_STOPPED,
   MEDIACLIPEVENT_CLOSED,
   MEDIACLIPEVENT_ERROR,
   MEDIACLIPEVENT_END_OF_MEDIA
} TotalCrossUiEvent;

#ifdef WIN32
typedef struct
{
   HWND hWnd;
   UINT msg;
   WPARAM wParam;
   LONG lParam;
   Context currentContext;
} WinEvent;
#endif

/// post an event to the running Java application. If mods is -1, the asynch mods will be retrieved; otherwise, pass the mods given in the key event
void postEvent(Context currentContext, TotalCrossUiEvent type, int32 key, int32 x, int32 y, int32 mods); // guich@tc126_70
void postOnMinimizeOrRestore(Context c, bool isMinimized);

#endif
