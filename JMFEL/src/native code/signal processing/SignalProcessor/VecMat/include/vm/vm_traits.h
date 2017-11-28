#ifndef VM_TRAITS_H
#define VM_TRAITS_H

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
 *  vm_traits.h - Traits class for classifying types.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

struct true_type
{
    operator bool() {return true;}
    bool operator!() {return false;}
};

struct false_type
{
    operator bool() {return false;}
    bool operator!() {return true;}
};


/******************************************************************************
 *
 *  Traits for various types.
 *  
 *  real_type should be the same as T, unless the type is complex, in which
 *  case it should be the real equivelent. This is used as the return type for
 *  the abs() function, and is also used for construction of complex objects
 *  from real ones.
 *
 *  float_type is the floating point type that should be used for the return
 *  value of functions like mean() and stdv(). For integer types it is set to
 *  double, and for floating point types it is just the type. For pointer types
 *  it is not meaningful.
 *
 *  is_int should be a typedef for true_type if it is an integer type, and a
 *  typedef for false_type otherwise.
 *
 *  is_float should be a typedef for true_type if it is a floating point type,
 *  and a typedef for false_type otherwise.
 *
 *  is_complex should be a typedef for true_type if it is a std::complex type,
 *  and a typedef for false_type otherwise.
 *
 *  is_specialized should be a typedef for true_type for all specialized types,
 *  and a typedef for false_type otherwise.
 *
 *  is_simple should be a typedef for true_type if the default constructor for
 *  type T does not exist, or does nothing, and a typedef for false_type
 *  otherwise.
 *
 *  zero() should return a value that makes sense as a "zero". The default
 *  will return a default constructed object, or for POD types, a bit field
 *  of all zeros. If the type has no copy constructor, then this function
 *  should be ommited from the Traits specialization.
 *
 *****************************************************************************/

template <class T>
struct Traits
{
    typedef T real_type;
    typedef T float_type;
    typedef false_type is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef false_type is_specialized;
    typedef false_type is_simple;
    static T zero() {return T();}
};


/******************************************************************************
 *
 *  Partial Specialization for pointer types.
 *
 *****************************************************************************/

template <class T>
struct Traits<T*>
{
    typedef T *real_type;
    typedef T *float_type;
    typedef false_type is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static T *zero() {return 0;}
};

/******************************************************************************
 *
 *  Traits for standard numeric types.
 *
 *****************************************************************************/

template <>
struct Traits<bool>
{
    typedef bool real_type;
    typedef double float_type;
    typedef false_type is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static bool zero() {return false;}
};

template <>
struct Traits<char>
{
    typedef char real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static char zero() {return 0;}
};

template <>
struct Traits<signed char>
{
    typedef signed char real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static signed char zero() {return 0;}
};

template <>
struct Traits<unsigned char>
{
    typedef unsigned char real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static unsigned char zero() {return 0;}
};

template <>
struct Traits<short>
{
    typedef short real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static short zero() {return 0;}
};

template <>
struct Traits<unsigned short>
{
    typedef unsigned short real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static unsigned short zero() {return 0;}
};

template <>
struct Traits<int>
{
    typedef int real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static int zero() {return 0;}
};

template <>
struct Traits<unsigned int>
{
    typedef unsigned int real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static unsigned int zero() {return 0;}
};

template <>
struct Traits<long>
{
    typedef long real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static long zero() {return 0;}
};

template <>
struct Traits<unsigned long>
{
    typedef unsigned long real_type;
    typedef double float_type;
    typedef true_type  is_int;
    typedef false_type is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static unsigned long zero() {return 0;}
};

template <>
struct Traits<float>
{
    typedef float real_type;
    typedef float float_type;
    typedef false_type is_int;
    typedef true_type  is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static float zero() {return 0.0;}
};

template <>
struct Traits<double>
{
    typedef double real_type;
    typedef double float_type;
    typedef false_type is_int;
    typedef true_type  is_float;
    typedef false_type is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static double zero() {return 0.0;}
};

/******************************************************************************
 *
 *  Traits for complex types.
 *
 *****************************************************************************/

template <>
struct Traits<std::complex<float> >
{
    typedef float real_type;
    typedef std::complex<float> float_type;
    typedef false_type is_int;
    typedef true_type  is_float;
    typedef true_type  is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static std::complex<float> zero() {return 0.0;}
};

template <>
struct Traits<std::complex<double> >
{
    typedef double real_type;
    typedef std::complex<double> float_type;
    typedef false_type is_int;
    typedef true_type  is_float;
    typedef true_type  is_complex;
    typedef true_type  is_specialized;
    typedef true_type  is_simple;
    static std::complex<double> zero() {return 0.0;}
};

} /* namespace VM */


#endif /* VM_TRAITS_H */
