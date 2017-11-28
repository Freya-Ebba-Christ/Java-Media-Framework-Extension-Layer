#ifndef VM_MATH_H
#define VM_MATH_H

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
 *  vm_math.h - Header file for math functionality.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

template <class T>
inline T square(const T& x) {return x * x;}

/******************************************************************************
 *
 *  These functions are needed to properly overload the vector and matrix abs
 *  functions.
 *
 *****************************************************************************/

template <class T>
inline typename Traits<T>::real_type abs(const T& x) {return (x > 0) ? x : -x;}

//template <>
inline float abs(const std::complex<float>& x) {return std::abs(x);}

//template <>
inline double abs(const std::complex<double>& x) {return std::abs(x);}


/******************************************************************************
 *
 *  Binary arithmetic operators for vectors. Vectors must be the same size.
 *
 *****************************************************************************/

template <class T>
Vector<T> operator+(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator-(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator*(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator/(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator%(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator&(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator^(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator|(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator<<(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<T> operator>>(const Vector<T>& lhs, const Vector<T>& rhs);


/******************************************************************************
 *
 *  Binary relational operators for vectors. Vectors must be the same size.
 *
 *****************************************************************************/

template <class T>
Vector<bool> operator==(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<bool> operator!=(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<bool> operator<(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<bool> operator>(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<bool> operator<=(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<bool> operator>=(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<bool> operator&&(const Vector<T>& lhs, const Vector<T>& rhs);

template <class T>
Vector<bool> operator||(const Vector<T>& lhs, const Vector<T>& rhs);


/******************************************************************************
 *
 *  Binary arithemtic operators for vectors and scalers.
 *
 *****************************************************************************/

template <class T>
Vector<T> operator+(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator+(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator-(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator-(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator*(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator*(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator/(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator/(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator%(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator%(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator&(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator&(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator^(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator^(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator|(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator|(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator<<(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator<<(const T& a, const Vector<T>& rhs);

template <class T>
Vector<T> operator>>(const Vector<T>& lhs, const T& a);

template <class T>
Vector<T> operator>>(const T& a, const Vector<T>& rhs);


/******************************************************************************
 *
 *  Binary relational operators for vectors and scalers.
 *
 *****************************************************************************/

template <class T>
Vector<bool> operator==(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator==(const T& a, const Vector<T>& rhs);

template <class T>
Vector<bool> operator!=(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator!=(const T& a, const Vector<T>& rhs);

template <class T>
Vector<bool> operator<(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator<(const T& a, const Vector<T>& rhs);

template <class T>
Vector<bool> operator>(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator>(const T& a, const Vector<T>& rhs);

template <class T>
Vector<bool> operator<=(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator<=(const T& a, const Vector<T>& rhs);

template <class T>
Vector<bool> operator>=(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator>=(const T& a, const Vector<T>& rhs);

template <class T>
Vector<bool> operator&&(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator&&(const T& a, const Vector<T>& rhs);

template <class T>
Vector<bool> operator||(const Vector<T>& lhs, const T& a);

template <class T>
Vector<bool> operator||(const T& a, const Vector<T>& rhs);


/******************************************************************************
 *
 *  Binary arithmetic operators for matrices. Matrices must have the same
 *  dimension.
 *
 *****************************************************************************/

template <class T>
Matrix<T> operator+(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator-(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator*(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator/(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator%(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator&(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator^(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator|(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator<<(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator>>(const Matrix<T>& lhs, const Matrix<T>& rhs);


/******************************************************************************
 *
 *  Binary relational operators for matrices. Matrices must have the same
 *  dimension.
 *
 *****************************************************************************/

template <class T>
Matrix<bool> operator==(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator!=(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator<(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator>(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator<=(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator>=(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator&&(const Matrix<T>& lhs, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator||(const Matrix<T>& lhs, const Matrix<T>& rhs);


/******************************************************************************
 *
 *  Binary arithmetic operators for matrices with scalers.
 *
 *****************************************************************************/

template <class T>
Matrix<T> operator+(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator+(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator-(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator-(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator*(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator*(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator/(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator/(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator%(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator%(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator&(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator&(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator^(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator^(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator|(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator|(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator<<(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator<<(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<T> operator>>(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<T> operator>>(const T& a, const Matrix<T>& rhs);


/******************************************************************************
 *
 *  Binary relational operators for matrices and scalers.
 *
 *****************************************************************************/

template <class T>
Matrix<bool> operator==(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator==(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator!=(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator!=(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator<(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator<(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator>(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator>(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator<=(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator<=(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator>=(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator>=(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator&&(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator&&(const T& a, const Matrix<T>& rhs);

template <class T>
Matrix<bool> operator||(const Matrix<T>& lhs, const T& a);

template <class T>
Matrix<bool> operator||(const T& a, const Matrix<T>& rhs);


/******************************************************************************
 *
 *  Overloaded math functions for vectors.
 *
 *****************************************************************************/

template <class T>
Vector<typename Traits<T>::real_type> abs(const Vector<T>& x);

template <class T>
Vector<T> acos(const Vector<T>& x);

template <class T>
Vector<T> asin(const Vector<T>& x);

template <class T>
Vector<T> atan(const Vector<T>& x);

template <class T>
Vector<T> atan2(const Vector<T>& x, const Vector<T>& y);

template <class T>
Vector<T> atan2(const Vector<T> x, const T& y);

template <class T>
Vector<T> atan2(const T& x, const Vector<T>& y);

template <class T>
Vector<T> cos(const Vector<T>& x);

template <class T>
Vector<T> cosh(const Vector<T>& x);

template <class T>
Vector<T> exp(const Vector<T>& x);

template <class T>
Vector<T> log(const Vector<T>& x);

template <class T>
Vector<T> log10(const Vector<T>& x);

template <class T>
Vector<T> pow(const Vector<T>& x, const Vector<T>& y);

template <class T>
Vector<T> pow(const Vector<T> x, const T& y);

template <class T>
Vector<T> pow(const T& x, const Vector<T>& y);

template <class T>
Vector<T> sin(const Vector<T>& x);

template <class T>
Vector<T> sinh(const Vector<T>& x);

template <class T>
Vector<T> sqrt(const Vector<T>& x);

template <class T>
Vector<T> tan(const Vector<T>& x);

template <class T>
Vector<T> tanh(const Vector<T>& x);


/******************************************************************************
 *
 *  Overloaded math functions for complex vectors.
 *
 *****************************************************************************/

template <class T>
Vector<T> arg(const Vector<std::complex<T> >& x);

template <class T>
Vector<T> norm(const Vector<std::complex<T> >& x);

template <class T>
Vector<std::complex<T> > polar(const Vector<T>& rho, const Vector<T>& theta);

template <class T>
Vector<std::complex<T> > polar(const Vector<T>& rho, const T& theta);

template <class T>
Vector<std::complex<T> > polar(const T& rho, const Vector<T>& theta);

template <class T>
Vector<T> real(const Vector<std::complex<T> >& x);

template <class T>
Vector<T> imag(const Vector<std::complex<T> >& x);

template <class T>
Vector<std::complex<T> > conj(const Vector<std::complex<T> >& x);


/******************************************************************************
 *
 *  Overloaded math functions for matrices.
 *
 *****************************************************************************/

template <class T>
Matrix<typename Traits<T>::real_type> abs(const Matrix<T>& x);

template <class T>
Matrix<T> acos(const Matrix<T>& x);

template <class T>
Matrix<T> asin(const Matrix<T>& x);

template <class T>
Matrix<T> atan(const Matrix<T>& x);

template <class T>
Matrix<T> atan2(const Matrix<T>& x, const Matrix<T>& y);

template <class T>
Matrix<T> atan2(const Matrix<T> x, const T& y);

template <class T>
Matrix<T> atan2(const T& x, const Matrix<T>& y);

template <class T>
Matrix<T> cos(const Matrix<T>& x);

template <class T>
Matrix<T> cosh(const Matrix<T>& x);

template <class T>
Matrix<T> exp(const Matrix<T>& x);

template <class T>
Matrix<T> log(const Matrix<T>& x);

template <class T>
Matrix<T> log10(const Matrix<T>& x);

template <class T>
Matrix<T> pow(const Matrix<T>& x, const Matrix<T>& y);

template <class T>
Matrix<T> pow(const Matrix<T> x, const T& y);

template <class T>
Matrix<T> pow(const T& x, const Matrix<T>& y);

template <class T>
Matrix<T> sin(const Matrix<T>& x);

template <class T>
Matrix<T> sinh(const Matrix<T>& x);

template <class T>
Matrix<T> sqrt(const Matrix<T>& x);

template <class T>
Matrix<T> tan(const Matrix<T>& x);

template <class T>
Matrix<T> tanh(const Matrix<T>& x);


/******************************************************************************
 *
 *  Overloaded math functions for complex matrices.
 *
 *****************************************************************************/

template <class T>
Matrix<T> arg(const Matrix<std::complex<T> >& x);

template <class T>
Matrix<T> norm(const Matrix<std::complex<T> >& x);

template <class T>
Matrix<std::complex<T> > polar(const Matrix<T>& rho, const Matrix<T>& theta);

template <class T>
Matrix<std::complex<T> > polar(const Matrix<T>& rho, const T& theta);

template <class T>
Matrix<std::complex<T> > polar(const T& rho, const Matrix<T>& theta);

template <class T>
Matrix<T> real(const Matrix<std::complex<T> >& x);

template <class T>
Matrix<T> imag(const Matrix<std::complex<T> >& x);

template <class T>
Matrix<std::complex<T> > conj(const Matrix<std::complex<T> >& x);

} /* namespace VM */

#include <vm/vm_math.cxx>


#endif /* VM_MATH_H */
