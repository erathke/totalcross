/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2012 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *********************************************************************************/

#import <Foundation/Foundation.h>
#import <CoreFoundation/CoreFoundation.h>
#import <UIKit/UIKit.h>
#import <QuartzCore/CALayer.h>
#import <QuartzCore/QuartzCore.h>
#include <OpenGLES/ES2/gl.h>
#include <OpenGLES/ES2/glext.h>

@interface ChildView : UIView
{
   int lastEventTS;
   int shiftY;
   int clientW;
   int lastOrientation;
   UIViewController* controller;
//   CGDataProviderRef provider;
//   CGImageRef cgImage;
   char* screenBuffer;
//   CGColorSpaceRef colorSpace;
   EAGLContext *glcontext;
	GLuint defaultFramebuffer, colorRenderbuffer;
}
- (id)init:(UIViewController*) ctrl;
- (void)setScreenValues:(void*)screen;
- (void)drawRect:(CGRect)frame;
- (void)invalidateScreen:(void*)vscreen;
- (void)graphicsSetup;

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;

@end
