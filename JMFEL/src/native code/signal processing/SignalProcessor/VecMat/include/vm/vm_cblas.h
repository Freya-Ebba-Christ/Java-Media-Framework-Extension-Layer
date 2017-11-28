#ifndef VM_CBLAS_H
#define VM_CBLAS_H

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
 *  vm_cblas.h - Header file for CBLAS wrapper functions.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/

#ifdef USE_MKL
#include <mkl_cblas.h>
#else
extern "C" {
#include <cblas.h>
}
#endif /* USE_MKL */

namespace VM {

enum Trans_Flag {NO_TRANS = 0, TRANS = 1, TRANS_CONJ = 2};
enum Side_Flag  {LEFT = 0, RIGHT = 1};
enum Diag_Flag  {NON_UNIT = 0, UNIT = 1};
enum Up_Lo_Flag {UPPER = 0, LOWER = 1};
enum Mat_Layout {GENERAL = 0, ROW_MAJOR = 1, COL_MAJOR = 2};
enum Conj_Flag  {NO_CONJ = 0, CONJ = 1};
enum Auto_Complete_Flag {NO_AUTO_COMPLETE = 0, AUTO_COMPLETE = 1};

template <class T>
inline Mat_Layout matrix_layout(const Matrix<T>& x)
{
    if (x.empty()) return GENERAL; 
    if (x.col_stride() == 1 && x.row_stride() >= ptrdiff_t(x.ncols()) &&
        x.row_stride() > 0) return ROW_MAJOR;
    else if (x.row_stride() == 1 && x.col_stride() >= ptrdiff_t(x.nrows()) &&
        x.col_stride() > 0) return COL_MAJOR;
    return GENERAL;
} 

struct BlasParam
{
    enum CBLAS_TRANSPOSE trans_flag_a_, trans_flag_b_;
    enum CBLAS_UPLO uplo_flag_;
    enum CBLAS_DIAG diag_flag_;
    enum CBLAS_SIDE side_flag_;
    bool conj_flag_;
    bool auto_complete_flag_;

    BlasParam() : trans_flag_a_(CblasNoTrans), trans_flag_b_(CblasNoTrans),
        uplo_flag_(CblasUpper), diag_flag_(CblasNonUnit), side_flag_(CblasLeft),
        conj_flag_(false), auto_complete_flag_(false) {;}

    BlasParam(Trans_Flag fa, Trans_Flag fb = NO_TRANS) :
        uplo_flag_(CblasUpper), diag_flag_(CblasNonUnit), side_flag_(CblasLeft),
        conj_flag_(false), auto_complete_flag_(false)
    {
        trans_flag_a(fa);
        trans_flag_b(fb);
    }

    BlasParam(Up_Lo_Flag uplo) : trans_flag_a_(CblasNoTrans),
        trans_flag_b_(CblasNoTrans), diag_flag_(CblasNonUnit),
        side_flag_(CblasLeft), conj_flag_(false), auto_complete_flag_(false)
    {
        uplo_flag(uplo);
    }

    BlasParam(Side_Flag side) : trans_flag_a_(CblasNoTrans),
        trans_flag_b_(CblasNoTrans), uplo_flag_(CblasUpper),
        diag_flag_(CblasNonUnit), conj_flag_(false), auto_complete_flag_(false)
    {
        side_flag(side);
    }

    BlasParam(Diag_Flag diag) : trans_flag_a_(CblasNoTrans),
        trans_flag_b_(CblasNoTrans), uplo_flag_(CblasUpper),
        side_flag_(CblasLeft), conj_flag_(false), auto_complete_flag_(false)
    {
        diag_flag(diag);
    }

    BlasParam(Conj_Flag cf) : trans_flag_a_(CblasNoTrans),
        trans_flag_b_(CblasNoTrans), uplo_flag_(CblasUpper),
        diag_flag_(CblasNonUnit), side_flag_(CblasLeft),
        auto_complete_flag_(false)
    {
        conj_flag(cf);
    }
    
    BlasParam(Auto_Complete_Flag acf) : trans_flag_a_(CblasNoTrans),
        trans_flag_b_(CblasNoTrans), uplo_flag_(CblasUpper),
        diag_flag_(CblasNonUnit), side_flag_(CblasLeft), conj_flag_(false)
    {
        auto_complete_flag(acf);
    }
    
    
    void trans_flag_a(Trans_Flag f)
    {
        if (f == TRANS) trans_flag_a_ = CblasTrans;
        else if (f == TRANS_CONJ) trans_flag_a_ = CblasConjTrans;
        else trans_flag_a_ = CblasNoTrans;
    }
    
    Trans_Flag trans_flag_a() const
    {
        if (trans_flag_a_ == CblasTrans) return TRANS;
        else if (trans_flag_a_ == CblasConjTrans) return TRANS_CONJ;
        else return NO_TRANS;
    }

    void trans_flag_b(Trans_Flag f)
    {
        if (f == TRANS) trans_flag_b_ = CblasTrans;
        else if (f == TRANS_CONJ) trans_flag_b_ = CblasConjTrans;
        else trans_flag_b_ = CblasNoTrans;
    }
    
    Trans_Flag trans_flag_b() const
    {
        if (trans_flag_b_ == CblasTrans) return TRANS;
        else if (trans_flag_b_ == CblasConjTrans) return TRANS_CONJ;
        else return NO_TRANS;
    }

    void uplo_flag(Up_Lo_Flag f)
    {
        if (f == LOWER) uplo_flag_ = CblasLower;
        else uplo_flag_ = CblasUpper;
    } 

    Up_Lo_Flag uplo_flag() const
    {
        if (uplo_flag_ == CblasLower) return LOWER;
        else return UPPER;
    }

    void side_flag(Side_Flag f)
    {
        if (f == RIGHT) side_flag_ = CblasRight;
        else side_flag_ = CblasLeft;
    } 

    Side_Flag side_flag() const
    {
        if (side_flag_ == CblasRight) return RIGHT;
        else return LEFT;
    }

    void diag_flag(Diag_Flag f)
    {
        if (f == UNIT) diag_flag_ = CblasUnit;
        else diag_flag_ = CblasNonUnit;
    } 

    Diag_Flag diag_flag() const
    {
        if (diag_flag_ == CblasUnit) return UNIT;
        else return NON_UNIT;
    }

    void conj_flag(Conj_Flag f)
    {
        conj_flag_ = (f == CONJ);
    }
    
    bool conj_flag() const {return (conj_flag_) ? CONJ : NO_CONJ;}
    
    void auto_complete_flag(Auto_Complete_Flag f)
    {
        auto_complete_flag_ = (f == AUTO_COMPLETE);
    }
    
    bool auto_complete_flag() const
    {
        return (auto_complete_flag_) ? AUTO_COMPLETE : NO_AUTO_COMPLETE;
    }
    
    template <class T>
    void sym_complete(Matrix<T>& A) const
    {
        size_t i, n = std::min(A.nrows(), A.ncols());
        typename Vector<T>::iterator j, k, a, b;
        for (i = 0; i < n - 1; ++i)
        {
            a = A.row_begin(i) + i + 1;
            b = A.row_begin(i) + n;
            k = A.col_begin(i) + i + 1;
            for (j = a; j < b; ++j, ++k)
            {
                if (uplo_flag_ == CblasUpper) *k = *j;
                else *j = *k;
            }
        }
    }

    template <class T>
    void her_complete(Matrix<T>& A) const
    {
        size_t i, n = std::min(A.nrows(), A.ncols());
        typename Vector<T>::iterator j, k, a, b;
        for (i = 0; i < n - 1; ++i)
        {
            a = A.row_begin(i) + i + 1;
            b = A.row_begin(i) + n;
            k = A.col_begin(i) + i + 1;
            for (j = a; j < b; ++j, ++k)
            {
                if (uplo_flag_ == CblasUpper) *k = std::conj(*j);
                else *j = std::conj(*k);
            }
        }
    }
    
};


/******************************************************************************
 *
 *  Level 1 Blas functions for 1 dimensional arrays.
 *
 *****************************************************************************/

/* asum functions */

inline float blas_asum(const Vec_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_asum() function requires a positive stride.");
    return cblas_sasum(x.size(), x.data(), x.stride());
}

inline double blas_asum(const Vec_DP& x)
{   
    if (x.stride() < 1)
        vmerror("blas_asum() function requires a positive stride.");
    return cblas_dasum(x.size(), x.data(), x.stride());
}
 
inline float blas_asum(const Vec_CPLX_SP& x)
{   
    if (x.stride() < 1)
        vmerror("blas_asum() function requires a positive stride.");
    return cblas_scasum(x.size(), x.data(), x.stride());
}

inline double blas_asum(const Vec_CPLX_DP& x)
{   
    if (x.stride() < 1)
        vmerror("blas_asum() function requires a positive stride.");
    return cblas_dzasum(x.size(), x.data(), x.stride());
}

/* axpy functions */

inline void blas_axpy(float a, const Vec_SP& x, Vec_SP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_axpy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_axpy() function.");  
    cblas_saxpy(x.size(), a, x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_axpy(double a, const Vec_DP& x, Vec_DP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_axpy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_axpy() function.");  
    cblas_daxpy(x.size(), a, x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_axpy(const CPLX_SP& a, const Vec_CPLX_SP& x, Vec_CPLX_SP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_axpy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_axpy() function.");  
    cblas_caxpy(x.size(), &a, x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_axpy(const CPLX_DP& a, const Vec_CPLX_DP& x, Vec_CPLX_DP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_axpy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_axpy() function.");  
    cblas_zaxpy(x.size(), &a, x.data(), x.stride(), y.data(), y.stride());
}

/* copy functions */

inline void blas_copy(const Vec_SP& x, Vec_SP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_copy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_copy() function.");  
    cblas_scopy(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_copy(const Vec_DP& x, Vec_DP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_copy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_copy() function.");  
    cblas_dcopy(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_copy(const Vec_CPLX_SP& x, Vec_CPLX_SP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_copy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_copy() function.");  
    cblas_ccopy(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_copy(const Vec_CPLX_DP& x, Vec_CPLX_DP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_copy() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_copy() function.");  
    cblas_zcopy(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

/* dot functions */

inline float blas_dot(const Vec_SP& x, const Vec_SP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_dot() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_dot() function.");  
    return cblas_sdot(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline double blas_dot(const Vec_DP& x, const Vec_DP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_dot() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_dot() function.");  
    return cblas_ddot(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline CPLX_SP blas_dot(const Vec_CPLX_SP& x, const Vec_CPLX_SP& y,
    const BlasParam& bp = BlasParam())
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_dot() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_dot() function.");  
    CPLX_SP res;
    if (bp.conj_flag_) cblas_cdotc_sub(x.size(), x.data(), x.stride(),
        y.data(), y.stride(), &res);
    else cblas_cdotu_sub(x.size(), x.data(), x.stride(), y.data(), y.stride(),
        &res);
    return res;
}

inline CPLX_DP blas_dot(const Vec_CPLX_DP& x, const Vec_CPLX_DP& y,
    const BlasParam& bp = BlasParam())
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_dot() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_dot() function.");  
    CPLX_DP res;
    if (bp.conj_flag_) cblas_zdotc_sub(x.size(), x.data(), x.stride(),
        y.data(), y.stride(), &res);
    else cblas_zdotu_sub(x.size(), x.data(), x.stride(), y.data(), y.stride(),
        &res);
    return res;
}

/* nrm2 functions */

inline float blas_nrm2(const Vec_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_nrm2() function requires a positive stride.");
    return cblas_snrm2(x.size(), x.data(), x.stride());
}

inline double blas_nrm2(const Vec_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_nrm2() function requires a positive stride.");
    return cblas_dnrm2(x.size(), x.data(), x.stride());
}

inline float blas_nrm2(const Vec_CPLX_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_nrm2() function requires a positive stride.");
    return cblas_scnrm2(x.size(), x.data(), x.stride());
}

inline double blas_nrm2(const Vec_CPLX_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_nrm2() function requires a positive stride.");
    return cblas_dznrm2(x.size(), x.data(), x.stride());
}

/* rot functions */

inline void blas_rot(Vec_SP& x, Vec_SP& y, float c, float s)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_rot() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_rot() function.");  
    cblas_srot(x.size(), x.data(), x.stride(), y.data(), y.stride(), c, s);
}

inline void blas_rot(Vec_DP& x, Vec_DP& y, double c, double s)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_rot() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_rot() function.");  
    cblas_drot(x.size(), x.data(), x.stride(), y.data(), y.stride(), c, s);
}

/* rotg functions */

inline void blas_rotg(float& a, float& b, float& c, float& s)
{
    cblas_srotg(&a, &b, &c, &s);
}

inline void blas_rotg(double& a, double& b, double& c, double& s)
{
    cblas_drotg(&a, &b, &c, &s);
}

/* rotm functions */

inline void blas_rotm(Vec_SP& x, Vec_SP& y, const Vec_SP& p)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_rotm() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_rotm() function.");
    if (p.size() != 5 || p.stride() != 1)
        vmerror("Parameter vector not valid in blas_rotm() function.");  
    cblas_srotm(x.size(), x.data(), x.stride(), y.data(), y.stride(), p.data());    
}

inline void blas_rotm(Vec_DP& x, Vec_DP& y, const Vec_DP& p)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_rotm() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_rotm() function.");
    if (p.size() != 5 || p.stride() != 1)
        vmerror("Parameter vector not valid in blas_rotm() function.");  
    cblas_drotm(x.size(), x.data(), x.stride(), y.data(), y.stride(), p.data());    
}


/* rotmg functions */

inline void blas_rotmg(float& d1, float& d2, float& b1, const float& b2, Vec_SP& p)
{
    if (p.size() != 5 || p.stride() != 1)
        vmerror("Parameter vector not valid in blas_rotmg() function.");  
#ifdef USE_MKL
    cblas_srotmg(&d1, &d2, &b1, &b2, p.data());    
#else
    cblas_srotmg(&d1, &d2, &b1, b2, p.data());    
#endif /* USE_MKL */
}

inline void blas_rotmg(double& d1, double& d2, double& b1, const double& b2, Vec_DP& p)
{
    if (p.size() != 5 || p.stride() != 1)
        vmerror("Parameter vector not valid in blas_rotmg() function.");  
#ifdef USE_MKL
    cblas_drotmg(&d1, &d2, &b1, &b2, p.data());    
#else
    cblas_drotmg(&d1, &d2, &b1, b2, p.data());    
#endif /* USE_MKL */
}

/* scal functions */

inline void blas_scal(float a, Vec_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_scal() function requires a positive stride.");
    cblas_sscal(x.size(), a, x.data(), x.stride());
}

inline void blas_scal(double a, Vec_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_scal() function requires a positive stride.");
    cblas_dscal(x.size(), a, x.data(), x.stride());
}

inline void blas_scal(const CPLX_SP& a, Vec_CPLX_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_scal() function requires a positive stride.");
    cblas_cscal(x.size(), &a, x.data(), x.stride());
}

inline void blas_scal(const CPLX_DP& a, Vec_CPLX_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_scal() function requires a positive stride.");
    cblas_zscal(x.size(), &a, x.data(), x.stride());
}

inline void blas_scal(float a, Vec_CPLX_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_scal() function requires a positive stride.");
    cblas_csscal(x.size(), a, x.data(), x.stride());
}

inline void blas_scal(double a, Vec_CPLX_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_scal() function requires a positive stride.");
    cblas_zdscal(x.size(), a, x.data(), x.stride());
}

/* swap functions */

inline void blas_swap(Vec_SP& x, Vec_SP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_swap() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_swap() function.");
    cblas_sswap(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_swap(Vec_DP& x, Vec_DP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_swap() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_swap() function.");
    cblas_dswap(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_swap(Vec_CPLX_SP& x, Vec_CPLX_SP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_swap() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_swap() function.");
    cblas_cswap(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

inline void blas_swap(Vec_CPLX_DP& x, Vec_CPLX_DP& y)
{
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_swap() function requires positive strides.");
    if (x.size() != y.size())
        vmerror("Size mismatch in blas_swap() function.");
    cblas_zswap(x.size(), x.data(), x.stride(), y.data(), y.stride());
}

/* amax functions */

inline size_t blas_amax(const Vec_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amax() function requires a positive stride.");
    return cblas_isamax(x.size(), x.data(), x.stride());
}

inline size_t blas_amax(const Vec_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amax() function requires a positive stride.");
    return cblas_idamax(x.size(), x.data(), x.stride());
}

inline size_t blas_amax(const Vec_CPLX_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amax() function requires a positive stride.");
    return cblas_icamax(x.size(), x.data(), x.stride());
}

inline size_t blas_amax(const Vec_CPLX_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amax() function requires a positive stride.");
    return cblas_izamax(x.size(), x.data(), x.stride());
}

/* amin functions */
/*
inline size_t blas_amin(const Vec_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amin() function requires a positive stride.");
    return cblas_isamin(x.size(), x.data(), x.stride());
}

inline size_t blas_amin(const Vec_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amax() function requires a positive stride.");
    return cblas_idamin(x.size(), x.data(), x.stride());
}

inline size_t blas_amin(const Vec_CPLX_SP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amax() function requires a positive stride.");
    return cblas_icamin(x.size(), x.data(), x.stride());
}

inline size_t blas_amin(const Vec_CPLX_DP& x)
{
    if (x.stride() < 1)
        vmerror("blas_amax() function requires a positive stride.");
    return cblas_izamin(x.size(), x.data(), x.stride());
}
*/

/******************************************************************************
 *
 *  Level 2 Blas functions for 1 & 2 dimensional arrays.
 *
 *****************************************************************************/

/* gemv functions */

inline void blas_gemv(float alpha, const Mat_SP& A, const Vec_SP& x, float beta,
    Vec_SP& y, const BlasParam& bp = BlasParam())
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_gemv() function requires positive strides.");

    n = x.size();
    m = y.size();
    if (bp.trans_flag_a() == NO_TRANS)
    {
        if (m != A.nrows() || n != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    else
    {
        if (n != A.nrows() || m != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemv() function");
        return;
    } 
    cblas_sgemv(order, bp.trans_flag_a_, m, n, alpha, A.data(), lda, x.data(),
        x.stride(), beta, y.data(), y.stride()); 
}

inline void blas_gemv(double alpha, const Mat_DP& A, const Vec_DP& x,
    double beta, Vec_DP& y, const BlasParam& bp = BlasParam())
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_gemv() function requires positive strides.");

    n = x.size();
    m = y.size();
    if (bp.trans_flag_a() == NO_TRANS)
    {
        if (m != A.nrows() || n != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    else
    {
        if (n != A.nrows() || m != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemv() function");
        return;
    } 
    cblas_dgemv(order, bp.trans_flag_a_, m, n, alpha, A.data(), lda, x.data(),
        x.stride(), beta, y.data(), y.stride()); 
}

inline void blas_gemv(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const Vec_CPLX_SP& x, const CPLX_SP& beta, Vec_CPLX_SP& y,
    const BlasParam& bp = BlasParam())
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_gemv() function requires positive strides.");

    n = x.size();
    m = y.size();
    if (bp.trans_flag_a() == NO_TRANS)
    {
        if (m != A.nrows() || n != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    else
    {
        if (n != A.nrows() || m != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemv() function");
        return;
    } 
    cblas_cgemv(order, bp.trans_flag_a_, m, n, &alpha, A.data(), lda, x.data(),
        x.stride(), &beta, y.data(), y.stride()); 
}

inline void blas_gemv(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const Vec_CPLX_DP& x, const CPLX_DP& beta, Vec_CPLX_DP& y,
    const BlasParam& bp = BlasParam())
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_gemv() function requires positive strides.");

    n = x.size();
    m = y.size();
    if (bp.trans_flag_a() == NO_TRANS)
    {
        if (m != A.nrows() || n != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    else
    {
        if (n != A.nrows() || m != A.ncols())
            vmerror("Size mismatch in blas_gemv() function.");
    }
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemv() function");
        return;
    } 
    cblas_zgemv(order, bp.trans_flag_a_, m, n, &alpha, A.data(), lda, x.data(),
        x.stride(), &beta, y.data(), y.stride()); 
}

/* ger functions */

inline void blas_ger(float alpha, const Vec_SP& x, const Vec_SP& y, Mat_SP& A)
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_ger() function requires positive strides.");

    m = A.nrows();
    n = A.ncols();
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_ger() function");
        return;
    } 
    if (x.size() != m || y.size() != n)
        vmerror("Size mismatch in blas_ger() function.");

    cblas_sger(order, m, n, alpha, x.data(), x.stride(), y.data(), y.stride(),
        A.data(), lda); 
}

inline void blas_ger(double alpha, const Vec_DP& x, const Vec_DP& y, Mat_DP& A)
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_ger() function requires positive strides.");

    m = A.nrows();
    n = A.ncols();
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_ger() function");
        return;
    } 
    if (x.size() != m || y.size() != n)
        vmerror("Size mismatch in blas_ger() function.");

    cblas_dger(order, m, n, alpha, x.data(), x.stride(), y.data(), y.stride(),
        A.data(), lda); 
}

inline void blas_ger(const CPLX_SP& alpha, const Vec_CPLX_SP& x,
    const Vec_CPLX_SP& y, Mat_CPLX_SP& A,  const BlasParam& bp = BlasParam())
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_ger() function requires positive strides.");

    m = A.nrows();
    n = A.ncols();
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_ger() function");
        return;
    } 
    if (x.size() != m || y.size() != n)
        vmerror("Size mismatch in blas_ger() function.");

    if (bp.conj_flag_) cblas_cgerc(order, m, n, &alpha, x.data(), x.stride(),
        y.data(), y.stride(), A.data(), lda);
    else cblas_cgeru(order, m, n, &alpha, x.data(), x.stride(), y.data(),
        y.stride(), A.data(), lda);
}

inline void blas_ger(const CPLX_DP& alpha, const Vec_CPLX_DP& x,
    const Vec_CPLX_DP& y, Mat_CPLX_DP& A, const BlasParam& bp = BlasParam())
{
    size_t n, m, lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_ger() function requires positive strides.");

    m = A.nrows();
    n = A.ncols();
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_ger() function");
        return;
    } 
    if (x.size() != m || y.size() != n)
        vmerror("Size mismatch in blas_ger() function.");

    if (bp.conj_flag_) cblas_zgerc(order, m, n, &alpha, x.data(), x.stride(),
        y.data(), y.stride(), A.data(), lda);
    else cblas_zgeru(order, m, n, &alpha, x.data(), x.stride(), y.data(),
        y.stride(), A.data(), lda);
}

/* symv functions */

inline void blas_symv(float alpha, const Mat_SP& A, const Vec_SP& x,
    float beta, Vec_SP& y, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_symv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_symv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_symv() function");
        return;
    }
    cblas_ssymv(order, bp.uplo_flag_, n, alpha, A.data(), lda, x.data(),
        x.stride(), beta, y.data(), y.stride());
}

inline void blas_symv(double alpha, const Mat_DP& A, const Vec_DP& x,
    double beta, Vec_DP& y, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_symv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_symv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_symv() function");
        return;
    }
    cblas_dsymv(order, bp.uplo_flag_, n, alpha, A.data(), lda, x.data(),
        x.stride(), beta, y.data(), y.stride());
}

/* hemv functions */

inline void blas_hemv(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const Vec_CPLX_SP& x, const CPLX_SP& beta, Vec_CPLX_SP& y,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_hemv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_hemv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_hemv() function");
        return;
    }
    cblas_chemv(order, bp.uplo_flag_, n, &alpha, A.data(), lda, x.data(),
        x.stride(), &beta, y.data(), y.stride());
}

inline void blas_hemv(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const Vec_CPLX_DP& x, const CPLX_DP& beta, Vec_CPLX_DP& y,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_hemv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_hemv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_hemv() function");
        return;
    }
    cblas_zhemv(order, bp.uplo_flag_, n, &alpha, A.data(), lda, x.data(),
        x.stride(), &beta, y.data(), y.stride());
}

/* syr functions */

inline void blas_syr(float alpha, const Vec_SP& x, Mat_SP& A,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_syr() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_syr() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr() function");
        return;
    }
    cblas_ssyr(order, bp.uplo_flag_, n, alpha, x.data(), x.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.sym_complete(A);
}

inline void blas_syr(double alpha, const Vec_DP& x, Mat_DP& A,
     const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_syr() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_syr() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr() function");
        return;
    }
    cblas_dsyr(order, bp.uplo_flag_, n, alpha, x.data(), x.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.sym_complete(A);
}

/* her functions */

inline void blas_her(float alpha, const Vec_CPLX_SP& x, Mat_CPLX_SP& A,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_her() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_her() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_her() function");
        return;
    }
    cblas_cher(order, bp.uplo_flag_, n, alpha, x.data(), x.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.her_complete(A);
}

inline void blas_her(double alpha, const Vec_CPLX_DP& x, Mat_CPLX_DP& A,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_her() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_her() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_her() function");
        return;
    }
    cblas_zher(order, bp.uplo_flag_, n, alpha, x.data(), x.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.her_complete(A);
}

/* syr2 functions */

inline void blas_syr2(float alpha, const Vec_SP& x, const Vec_SP& y, Mat_SP& A,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_syr2() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_syr2() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr2() function");
        return;
    }
    cblas_ssyr2(order, bp.uplo_flag_, n, alpha, x.data(), x.stride(), y.data(),
        y.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.sym_complete(A);
}

inline void blas_syr2(double alpha, const Vec_DP& x, const Vec_DP& y, Mat_DP& A,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_syr2() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_syr2() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr2() function");
        return;
    }
    cblas_dsyr2(order, bp.uplo_flag_, n, alpha, x.data(), x.stride(), y.data(),
        y.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.sym_complete(A);
}

/* her2 functions */

inline void blas_her2(const CPLX_SP& alpha, const Vec_CPLX_SP& x,
    const Vec_CPLX_SP& y, Mat_CPLX_SP& A, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_her2() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_her2() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_her2() function");
        return;
    }
    cblas_cher2(order, bp.uplo_flag_, n, &alpha, x.data(), x.stride(), y.data(),
        y.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.her_complete(A);
}

inline void blas_her2(const CPLX_DP& alpha, const Vec_CPLX_DP& x,
    const Vec_CPLX_DP& y, Mat_CPLX_DP& A, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1 || y.stride() < 1)
        vmerror("blas_her2() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n || y.size() != n)
        vmerror("Size mismatch in blas_her2() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_her2() function");
        return;
    }
    cblas_zher2(order, bp.uplo_flag_, n, &alpha, x.data(), x.stride(), y.data(),
        y.stride(), A.data(), lda);
    if (bp.auto_complete_flag_) bp.her_complete(A);
}

/* trmv functions */

inline void blas_trmv(const Mat_SP& A, Vec_SP& x, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trmv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trmv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmv() function");
        return;
    }
    cblas_strmv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}

inline void blas_trmv(const Mat_DP& A, Vec_DP& x, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trmv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trmv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmv() function");
        return;
    }
    cblas_dtrmv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}

inline void blas_trmv(const Mat_CPLX_SP& A, Vec_CPLX_SP& x,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trmv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trmv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmv() function");
        return;
    }
    cblas_ctrmv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}

inline void blas_trmv(const Mat_CPLX_DP& A, Vec_CPLX_DP& x,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trmv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trmv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmv() function");
        return;
    }
    cblas_ztrmv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}

/* trsv functions */

inline void blas_trsv(const Mat_SP& A, Vec_SP& x, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trsv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trsv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsv() function");
        return;
    }
    cblas_strsv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}

inline void blas_trsv(const Mat_DP& A, Vec_DP& x, const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trsv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trsv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsv() function");
        return;
    }
    cblas_dtrsv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}

inline void blas_trsv(const Mat_CPLX_SP& A, Vec_CPLX_SP& x,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trsv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trsv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsv() function");
        return;
    }
    cblas_ctrsv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}

inline void blas_trsv(const Mat_CPLX_DP& A, Vec_CPLX_DP& x,
    const BlasParam& bp = BlasParam())
{
    size_t n = x.size(), lda;
    enum CBLAS_ORDER order;
    if (x.stride() < 1)
        vmerror("blas_trsv() function requires positive strides.");
    if (A.nrows() != n || A.ncols() != n)
        vmerror("Size mismatch in blas_trsv() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsv() function");
        return;
    }
    cblas_ztrsv(order, bp.uplo_flag_, bp.trans_flag_a_, bp.diag_flag_, n,
        A.data(), lda, x.data(), x.stride());
}


/******************************************************************************
 *
 *  Level 3 Blas functions for 2 dimensional arrays.
 *
 *****************************************************************************/

/* gemm functions */

inline void blas_gemm(float alpha, const Mat_SP& A, const Mat_SP& B, float beta,
    Mat_SP& C, const BlasParam& bp = BlasParam())
{
    size_t m, n, k, lda, ldb, ldc;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.nrows();
        k = A.ncols();
    }
    else
    { 
        m = A.ncols();
        k = A.nrows();
    }
    if (bp.trans_flag_b() == NO_TRANS)
    {
        n = B.ncols();
        if (B.nrows() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    else
    {
        n = B.nrows();
        if (B.ncols() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    if (C.nrows() != m || C.ncols() != n)
        vmerror("Size mismatch in blas_gemm() function.");
            
    enum CBLAS_ORDER order;
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_gemm() function");
    
    cblas_sgemm(order, bp.trans_flag_a_, bp.trans_flag_b_, m, n, k, alpha,
        A.data(), lda, B.data(), ldb, beta, C.data(), ldc);
}

inline void blas_gemm(double alpha, const Mat_DP& A, const Mat_DP& B,
    double beta, Mat_DP& C, const BlasParam& bp = BlasParam())
{
    size_t m, n, k, lda, ldb, ldc;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.nrows();
        k = A.ncols();
    }
    else
    { 
        m = A.ncols();
        k = A.nrows();
    }
    if (bp.trans_flag_b() == NO_TRANS)
    {
        n = B.ncols();
        if (B.nrows() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    else
    {
        n = B.nrows();
        if (B.ncols() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    if (C.nrows() != m || C.ncols() != n)
        vmerror("Size mismatch in blas_gemm() function.");

    enum CBLAS_ORDER order;
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_gemm() function");
    
    cblas_dgemm(order, bp.trans_flag_a_, bp.trans_flag_b_, m, n, k, alpha,
        A.data(), lda, B.data(), ldb, beta, C.data(), ldc);
}

inline void blas_gemm(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const Mat_CPLX_SP& B, const CPLX_SP& beta, Mat_CPLX_SP& C,
    const BlasParam& bp = BlasParam())
{
    size_t m, n, k, lda, ldb, ldc;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.nrows();
        k = A.ncols();
    }
    else
    { 
        m = A.ncols();
        k = A.nrows();
    }
    if (bp.trans_flag_b() == NO_TRANS)
    {
        n = B.ncols();
        if (B.nrows() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    else
    {
        n = B.nrows();
        if (B.ncols() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    if (C.nrows() != m || C.ncols() != n)
        vmerror("Size mismatch in blas_gemm() function.");

    enum CBLAS_ORDER order;
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_gemm() function");
    
    cblas_cgemm(order, bp.trans_flag_a_, bp.trans_flag_b_, m, n, k, &alpha,
        A.data(), lda, B.data(), ldb, &beta, C.data(), ldc);
}

inline void blas_gemm(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const Mat_CPLX_DP& B, const CPLX_DP& beta, Mat_CPLX_DP& C,
    const BlasParam& bp = BlasParam())
{
    size_t m, n, k, lda, ldb, ldc;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.nrows();
        k = A.ncols();
    }
    else
    { 
        m = A.ncols();
        k = A.nrows();
    }
    if (bp.trans_flag_b() == NO_TRANS)
    {
        n = B.ncols();
        if (B.nrows() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    else
    {
        n = B.nrows();
        if (B.ncols() != k)
            vmerror("Size mismatch in blas_gemm() function.");
    }
    if (C.nrows() != m || C.ncols() != n)
        vmerror("Size mismatch in blas_gemm() function.");

    enum CBLAS_ORDER order;
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_gemm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_gemm() function");
    
    cblas_zgemm(order, bp.trans_flag_a_, bp.trans_flag_b_, m, n, k, &alpha,
        A.data(), lda, B.data(), ldb, &beta, C.data(), ldc);
}

/* symm functions */

inline void blas_symm(float alpha, const Mat_SP& A, const Mat_SP& B, float beta,
    Mat_SP& C, const BlasParam& bp = BlasParam())
{
    size_t m = C.nrows(), n = C.ncols(), lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_symm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_symm() function.");
    if (B.nrows() != m || B.ncols() != n)
        vmerror("Size mismatch in blas_symm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_symm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_symm() function");
    
    cblas_ssymm(order, bp.side_flag_, bp.uplo_flag_, m, n, alpha, A.data(),
        lda, B.data(), ldb, beta, C.data(), ldc);
}

inline void blas_symm(double alpha, const Mat_DP& A, const Mat_DP& B,
    double beta, Mat_DP& C, const BlasParam& bp = BlasParam())
{
    size_t m = C.nrows(), n = C.ncols(), lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_symm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_symm() function.");
    if (B.nrows() != m || B.ncols() != n)
        vmerror("Size mismatch in blas_symm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_symm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_symm() function");
    
    cblas_dsymm(order, bp.side_flag_, bp.uplo_flag_, m, n, alpha, A.data(),
        lda, B.data(), ldb, beta, C.data(), ldc);
}

inline void blas_symm(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const Mat_CPLX_SP& B, const CPLX_SP& beta, Mat_CPLX_SP& C,
    const BlasParam& bp = BlasParam())
{
    size_t m = C.nrows(), n = C.ncols(), lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_symm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_symm() function.");
    if (B.nrows() != m || B.ncols() != n)
        vmerror("Size mismatch in blas_symm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_symm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_symm() function");
    
    cblas_csymm(order, bp.side_flag_, bp.uplo_flag_, m, n, &alpha, A.data(),
        lda, B.data(), ldb, &beta, C.data(), ldc);
}

inline void blas_symm(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const Mat_CPLX_DP& B, const CPLX_DP& beta, Mat_CPLX_DP& C,
    const BlasParam& bp = BlasParam())
{
    size_t m = C.nrows(), n = C.ncols(), lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_symm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_symm() function.");
    if (B.nrows() != m || B.ncols() != n)
        vmerror("Size mismatch in blas_symm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_symm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_symm() function");
    
    cblas_zsymm(order, bp.side_flag_, bp.uplo_flag_, m, n, &alpha, A.data(),
        lda, B.data(), ldb, &beta, C.data(), ldc);
}

/* hemm functions */

inline void blas_hemm(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const Mat_CPLX_SP& B, const CPLX_SP& beta, Mat_CPLX_SP& C,
    const BlasParam& bp = BlasParam())
{
    size_t m = C.nrows(), n = C.ncols(), lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_hemm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_hemm() function.");
    if (B.nrows() != m || B.ncols() != n)
        vmerror("Size mismatch in blas_hemm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_hemm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_hemm() function");
    
    cblas_chemm(order, bp.side_flag_, bp.uplo_flag_, m, n, &alpha, A.data(),
        lda, B.data(), ldb, &beta, C.data(), ldc);
}

inline void blas_hemm(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const Mat_CPLX_DP& B, const CPLX_DP& beta, Mat_CPLX_DP& C,
    const BlasParam& bp = BlasParam())
{
    size_t m = C.nrows(), n = C.ncols(), lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_hemm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_hemm() function.");
    if (B.nrows() != m || B.ncols() != n)
        vmerror("Size mismatch in blas_hemm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_hemm() function");
        return;
    }
    if (matrix_layout(B) != ml || matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_hemm() function");
    
    cblas_zhemm(order, bp.side_flag_, bp.uplo_flag_, m, n, &alpha, A.data(),
        lda, B.data(), ldb, &beta, C.data(), ldc);
}

/* syrk functions */

inline void blas_syrk(float alpha, const Mat_SP& A, float beta, Mat_SP& C,
    const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syrk() function");
        return;
    }
    if (matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_syrk() function");
    
    cblas_ssyrk(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, alpha, A.data(),
        lda, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

inline void blas_syrk(double alpha, const Mat_DP& A, double beta, Mat_DP& C,
    const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syrk() function");
        return;
    }
    if (matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_syrk() function");
    
    cblas_dsyrk(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, alpha, A.data(),
        lda, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

inline void blas_syrk(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const CPLX_SP& beta, Mat_CPLX_SP& C, const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syrk() function");
        return;
    }
    if (matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_syrk() function");
    
    cblas_csyrk(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, &alpha, A.data(),
        lda, &beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

inline void blas_syrk(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const CPLX_DP& beta, Mat_CPLX_DP& C, const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_syrk() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syrk() function");
        return;
    }
    if (matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_syrk() function");
    
    cblas_zsyrk(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, &alpha, A.data(),
        lda, &beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

/* herk functions */

inline void blas_herk(float alpha, const Mat_CPLX_SP& A, float beta,
    Mat_CPLX_SP& C, const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_herk() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_herk() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_herk() function");
        return;
    }
    if (matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_herk() function");
    
    cblas_cherk(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, alpha, A.data(),
        lda, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.her_complete(C);
}

inline void blas_herk(double alpha, const Mat_CPLX_DP& A, double beta,
    Mat_CPLX_DP& C, const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_herk() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n)
        vmerror("Size mismatch in blas_herk() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_herk() function");
        return;
    }
    if (matrix_layout(C) != ml)
        vmerror("Matrix not in right format for blas_herk() function");
    
    cblas_zherk(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, alpha, A.data(),
        lda, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.her_complete(C);
}

/* syr2k functions */

inline void blas_syr2k(float alpha, const Mat_SP& A, const Mat_SP& B,
    float beta, Mat_SP& C, const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n || B.ncols() != m || B.nrows() != n)
            vmerror("Size mismatch in blas_syr2k() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n || B.ncols() != n || B.nrows() != m)
            vmerror("Size mismatch in blas_syr2k() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr2k() function");
        return;
    }
    if (matrix_layout(C) != ml || matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_syr2k() function");
    
    cblas_ssyr2k(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, alpha, A.data(),
        lda, B.data(), ldb, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

inline void blas_syr2k(double alpha, const Mat_DP& A, const Mat_DP& B,
    double beta, Mat_DP& C, const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n || B.ncols() != m || B.nrows() != n)
            vmerror("Size mismatch in blas_syr2k() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n || B.ncols() != n || B.nrows() != m)
            vmerror("Size mismatch in blas_syr2k() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr2k() function");
        return;
    }
    if (matrix_layout(C) != ml || matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_syr2k() function");
    
    cblas_dsyr2k(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, alpha, A.data(),
        lda, B.data(), ldb, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

inline void blas_syr2k(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const Mat_CPLX_SP& B, const CPLX_SP& beta, Mat_CPLX_SP& C,
    const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n || B.ncols() != m || B.nrows() != n)
        vmerror("Size mismatch in blas_syr2k() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n || B.ncols() != n || B.nrows() != m)
        vmerror("Size mismatch in blas_syr2k() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr2k() function");
        return;
    }
    if (matrix_layout(C) != ml || matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_syr2k() function");
    
    cblas_csyr2k(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, &alpha, A.data(),
        lda, B.data(), ldb, &beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

inline void blas_syr2k(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const Mat_CPLX_DP& B, const CPLX_DP& beta, Mat_CPLX_DP& C,
    const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n || B.ncols() != m || B.nrows() != n)
        vmerror("Size mismatch in blas_syr2k() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n || B.ncols() != n || B.nrows() != m)
        vmerror("Size mismatch in blas_syr2k() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_syr2k() function");
        return;
    }
    if (matrix_layout(C) != ml || matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_syr2k() function");
    
    cblas_zsyr2k(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, &alpha, A.data(),
        lda, B.data(), ldb, &beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.sym_complete(C);
}

/* her2k functions */

inline void blas_her2k(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    const Mat_CPLX_SP& B, float beta, Mat_CPLX_SP& C,
    const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n || B.ncols() != m || B.nrows() != n)
            vmerror("Size mismatch in blas_her2k() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n || B.ncols() != n || B.nrows() != m)
            vmerror("Size mismatch in blas_her2k() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_her2k() function");
        return;
    }
    if (matrix_layout(C) != ml || matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_her2k() function");
    
    cblas_cher2k(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, &alpha, A.data(),
        lda, B.data(), ldb, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.her_complete(C);
}

inline void blas_her2k(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    const Mat_CPLX_DP& B, double beta, Mat_CPLX_DP& C,
    const BlasParam& bp = BlasParam())
{
    size_t n = C.nrows(), m, lda, ldb, ldc;
    enum CBLAS_ORDER order;
    if (bp.trans_flag_a() == NO_TRANS)
    {
        m = A.ncols();
        if (A.nrows() != n || C.ncols() != n || B.ncols() != m || B.nrows() != n)
        vmerror("Size mismatch in blas_her2k() function.");
    }
    else
    {
        m = A.nrows();
        if (A.ncols() != n || C.ncols() != n || B.ncols() != n || B.nrows() != m)
        vmerror("Size mismatch in blas_her2k() function.");
    }
        
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
        ldc = C.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
        ldc = C.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_her2k() function");
        return;
    }
    if (matrix_layout(C) != ml || matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_syr2k() function");
    
    cblas_zher2k(order, bp.uplo_flag_, bp.trans_flag_a_, n, m, &alpha, A.data(),
        lda, B.data(), ldb, beta, C.data(), ldc);
    if (bp.auto_complete_flag_) bp.her_complete(C);
}

/* trmm functions */

inline void blas_trmm(float alpha, const Mat_SP& A, Mat_SP& B,
    const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trmm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trmm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trmm() function");
    
    cblas_strmm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, alpha, A.data(), lda, B.data(), ldb);
}

inline void blas_trmm(double alpha, const Mat_DP& A, Mat_DP& B,
    const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trmm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trmm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trmm() function");
    
    cblas_dtrmm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, alpha, A.data(), lda, B.data(), ldb);
}

inline void blas_trmm(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    Mat_CPLX_SP& B, const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trmm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trmm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trmm() function");
    
    cblas_ctrmm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, &alpha, A.data(), lda, B.data(), ldb);
}

inline void blas_trmm(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    Mat_CPLX_DP& B, const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trmm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trmm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trmm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trmm() function");
    
    cblas_ztrmm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, &alpha, A.data(), lda, B.data(), ldb);
}

/* trsm functions */

inline void blas_trsm(float alpha, const Mat_SP& A, Mat_SP& B,
    const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trsm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trsm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trsm() function");
    
    cblas_strsm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, alpha, A.data(), lda, B.data(), ldb);
}

inline void blas_trsm(double alpha, const Mat_DP& A, Mat_DP& B,
    const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trsm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trsm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trsm() function");
    
    cblas_dtrsm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, alpha, A.data(), lda, B.data(), ldb);
}

inline void blas_trsm(const CPLX_SP& alpha, const Mat_CPLX_SP& A,
    Mat_CPLX_SP& B, const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trsm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trsm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trsm() function");
    
    cblas_ctrsm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, &alpha, A.data(), lda, B.data(), ldb);
}

inline void blas_trsm(const CPLX_DP& alpha, const Mat_CPLX_DP& A,
    Mat_CPLX_DP& B, const BlasParam& bp = BlasParam())
{
    size_t m = B.nrows(), n = B.ncols(), lda, ldb;
    enum CBLAS_ORDER order;
    if (bp.side_flag() == LEFT && (A.nrows() != m || A.ncols() != m))
        vmerror("Size mismatch in blas_trsm() function.");
    if (bp.side_flag() == RIGHT && (A.nrows() != n || A.ncols() != n))
        vmerror("Size mismatch in blas_trsm() function.");
    
    Mat_Layout ml = matrix_layout(A);
    if (ml == ROW_MAJOR)
    {
        order = CblasRowMajor;
        lda = A.row_stride();
        ldb = B.row_stride();
    }
    else if (ml == COL_MAJOR)
    {
        order = CblasColMajor;
        lda = A.col_stride();
        ldb = B.col_stride();
    }
    else
    {
        vmerror("Matrix not in right format for blas_trsm() function");
        return;
    }
    if (matrix_layout(B) != ml)
        vmerror("Matrix not in right format for blas_trsm() function");
    
    cblas_ztrsm(order, bp.side_flag_, bp.uplo_flag_, bp.trans_flag_a_,
        bp.diag_flag_, m, n, &alpha, A.data(), lda, B.data(), ldb);
}


} /* namespace VM */


#endif /* VM_CBLAS_H */
