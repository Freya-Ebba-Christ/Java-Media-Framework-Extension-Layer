#ifndef VM_BASIC_H
#define VM_BASIC_H 

/******************************************************************************
 *
 *  VecMat Software, version 1.5
 *  Copyright (C) 2003 Kevin Dolan
 *  kdolan@mailaps.org
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 ****************************************************************************** 
 *
 *  vm_basic.h - Header file for minimal VecMat functionality.
 *
 *  Include this file if you want minimal VecMat functionality. Include
 *  "vec_mat.h" if you want the full software package.
 *
 *****************************************************************************/

#ifdef _MSC_VER // Eliminate pointless warnings about deprecated functions
#define _CRT_SECURE_NO_DEPRECATE
#pragma warning (disable : 4800 4996)
#endif /* _MSC_VER */

#include <sys/stat.h>
#include <cstdio>
#include <cmath>
#include <string>
#include <iostream>
#include <fstream>
#include <complex>
#include <memory>
#include <iterator>
#include <algorithm>
#include <numeric>
#include <limits>

/*****************************************************************************
 *
 *  Uncomment one of the following lines to enable an alternative allocator.
 *  The std::allocator is the default.
 *
 *****************************************************************************/

//#define USE_FFTW_ALLOCATOR
#define USE_VM_ALLOCATOR

#ifdef USE_FFTW_ALLOCATOR
#include <vm/fftw_allocator.h>
namespace VM {
template <class T>
class Allocator : public fftw_allocator<T> {};
} /* namespace VM */
#elif defined USE_VM_ALLOCATOR
#include <vm/vm_allocator.h>
#else /* USE std::allocator */
namespace VM {
template <class T>
class Allocator : public std::allocator<T> {};
} /* namespace VM */
#endif /* Set Allocator */


/*****************************************************************************
 *
 *  To remove bounds-checking in debug mode, comment out the following lines.
 *  To enable bounds-checking in release mode, comment outthe #ifdef and #endif
 *  lines only, or define VM_BOUNDS_CHECK manually.
 *
 *****************************************************************************/

//#ifdef __DEBUG__
//#ifndef VM_BOUNDS_CHECK
#define VM_BOUNDS_CHECK
//#endif /* VM_BOUNDS_CHECK */
//#endif /* __DEBUG__ */


/*****************************************************************************
 *
 *  Typedef for the file offset type.
 *
 *****************************************************************************/

#ifdef __MINGW32__
typedef off64_t offset_type;
#elif defined _WIN32
typedef __int64 offset_type;
#else
typedef off_t offset_type;
#endif /* __MINGW32__ */

namespace VM {

/*****************************************************************************
 *
 *  VecMat error handler. If your application cannot output to standard error,
 *  then you can either output with the FLTK fl_alert() function by defining
 *  VM_USE_FLTK, or you can provide your own error handling function by
 *  defining CUSTOM_ERROR_HANDLER.
 *
 *****************************************************************************/

#ifdef VM_USE_FLTK
} /* namespace VM */
#include <FL/fl_ask.H>
namespace VM {
inline void vmerror(const std::string& error_text, bool recoverable = false)
{
    std::string message = "VecMat run-time error.\n" + error_text;
    fl_alert(message.c_str());
    if (recoverable) return;
    exit(1);
}    
#else
#ifndef CUSTOM_ERROR_HANDLER
inline void vmerror(const std::string& error_text, bool recoverable = false)
{
    std::cerr << "VecMat run-time error." << std::endl;
    std::cerr << error_text << std::endl;
    if (recoverable) return;
    exit(1);
}
#endif /* CUSTOM_ERROR_HANDLER */
#endif /* VM_USE_FLTK */

/*****************************************************************************
 *
 *  Some things which will be needed for file IO, including a simple wrapper
 *  for the stat function in "sys/stat.h".
 *
 *****************************************************************************/

#undef TXT
#undef BIN

enum File_Mode {TXT = 0, BIN = 1};

template <class T>
inline offset_type file_status(const std::string& fname, offset_type skip = 0)
{
    offset_type fs;
#ifdef _WIN32
    struct _stati64 f_stat;
    fs = _stati64(fname.c_str(), &f_stat);
#else
    struct stat f_stat;
    fs = stat(fname.c_str(), &f_stat);
#endif /* _WIN32 */    
    if (fs == -1) return -1;
    if ((f_stat.st_size - skip) % offset_type(sizeof(T))) return -2;
    offset_type len = (f_stat.st_size - skip) / offset_type(sizeof(T));
    if (!len) return -1;
    return len;
}


/*****************************************************************************
 *
 *  NoInit identifier for use with constructors.
 *
 *****************************************************************************/

enum NoInit_ {NoInit};


} /* namespace VM */


#undef min
#undef max

#include <vm/vm_traits.h>
#include <vm/vector.h>
#include <vm/matrix.h>
#include <vm/vm_types.h>


#endif /* VM_BASIC_H */
