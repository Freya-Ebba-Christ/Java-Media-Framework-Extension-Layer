#ifndef VM_TYPES_H
#define VM_TYPES_H 

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
 *  vm_types.h - Typedefs.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


/******************************************************************************
 *
 *  Complex types.
 *
 *****************************************************************************/

typedef std::complex<float>   CPLX_SP;
typedef std::complex<double>  CPLX_DP;


/******************************************************************************
 *
 *  Vector Types.
 *
 *****************************************************************************/

typedef VM::Vector<bool>            Vec_BOOL;
typedef VM::Vector<char>            Vec_CHR;
typedef VM::Vector<signed char>     Vec_SCHR;
typedef VM::Vector<unsigned char>   Vec_UCHR;
typedef VM::Vector<short>           Vec_SHRT;
typedef VM::Vector<unsigned short>  Vec_USHRT;
typedef VM::Vector<int>             Vec_INT;
typedef VM::Vector<unsigned int>    Vec_UINT;
typedef VM::Vector<long>            Vec_LNG;
typedef VM::Vector<unsigned long>   Vec_ULNG;
typedef VM::Vector<float>           Vec_SP;
typedef VM::Vector<double>          Vec_DP;
typedef VM::Vector<CPLX_SP>         Vec_CPLX_SP;
typedef VM::Vector<CPLX_DP>         Vec_CPLX_DP;


/******************************************************************************
 *
 *  Matrix Types.
 *
 *****************************************************************************/

typedef VM::Matrix<bool>            Mat_BOOL;
typedef VM::Matrix<char>            Mat_CHR;
typedef VM::Matrix<signed char>     Mat_SCHR;
typedef VM::Matrix<unsigned char>   Mat_UCHR;
typedef VM::Matrix<short>           Mat_SHRT;
typedef VM::Matrix<unsigned short>  Mat_USHRT;
typedef VM::Matrix<int>             Mat_INT;
typedef VM::Matrix<unsigned int>    Mat_UINT;
typedef VM::Matrix<long>            Mat_LNG;
typedef VM::Matrix<unsigned long>   Mat_ULNG;
typedef VM::Matrix<float>           Mat_SP;
typedef VM::Matrix<double>          Mat_DP;
typedef VM::Matrix<CPLX_SP>         Mat_CPLX_SP;
typedef VM::Matrix<CPLX_DP>         Mat_CPLX_DP;


#endif /* VM_TYPES_H */
