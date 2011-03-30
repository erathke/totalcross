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

// $Id: nativelib_c.h,v 1.19 2011-01-04 13:31:15 guich Exp $

#if defined(darwin)
 #include <dlfcn.h>
 #include "sys/syslimits.h"
 #define SHLIB_SUFFIX "dylib"
 #define VM_PATH   "/Applications/TotalCross.app/libtcvm." SHLIB_SUFFIX
#elif defined(linux) || defined(ANDROID)
 #include <dlfcn.h>
 #include <limits.h>
 #define SHLIB_SUFFIX "so"
 #ifdef ANDROID
  #define VM_PATH   "/data/data/totalcross/android/libtcvm." SHLIB_SUFFIX
 #else
  #define VM_PATH   "/usr/lib/totalcross/libtcvm." SHLIB_SUFFIX
 #endif
#elif defined(__SYMBIAN32__)
 #define SHLIB_SUFFIX "so"
 #define VM_PATH   "/sys/bin/tcvm.dll" SHLIB_SUFFIX
#else
 #error "Undefined VM installation PATH"
#endif

#ifdef __SYMBIAN32__
VoidP privateLoadLibrary(CharP libName)
{}
void privateUnloadLibrary(VoidP libPtr)
{}
VoidP privateGetProcAddress(const VoidP module, const CharP funcName)
{
  return NULL;
}
#else
static VoidP tryAt(CharP prefix, CharP prefix2, CharP lib)
{     
   char fullpath[PATH_MAX];
   TCHAR szLibName[PATH_MAX];

   xstrprintf(fullpath, "%s%slib%s.%s",prefix, prefix2, lib, SHLIB_SUFFIX);
   CharP2TCHARPBuf(fullpath, szLibName);
   return dlopen(szLibName, RTLD_LAZY);
}

VoidP privateLoadLibrary(CharP libName)
{
   VoidP library;
#ifdef ANDROID
   CharPToLower(libName);
#endif
   library = tryAt("","",libName);
   if (library == null)
      library = tryAt("../","",libName);
   if (library == null)
      library = tryAt(vmPath,"/",libName);
#ifdef ANDROID
   if (library == null)
      library = tryAt(vmPath,"/lib/",libName); // needed for single apk applications
   if (library == null && strEq(libName,"litebase"))
      library = tryAt("/data/data/litebase.android/lib/","","litebase");
#endif      
   return library;
}

void privateUnloadLibrary(VoidP libPtr)
{
   dlclose(libPtr);
}

VoidP privateGetProcAddress(const VoidP module, const CharP funcName)
{
   void *tcvm = module ? module : dlopen(TEXT(VM_PATH), RTLD_LAZY);
   if (tcvm)
      return dlsym(tcvm, funcName);
   return NULL;
}
#endif
