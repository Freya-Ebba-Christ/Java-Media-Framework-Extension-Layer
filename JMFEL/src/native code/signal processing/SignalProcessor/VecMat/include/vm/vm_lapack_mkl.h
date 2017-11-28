#ifndef VM_LAPACK_MKL_H
#define VM_LAPACK_MKL_H

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
 *  vm_lapack_mkl.h - Header file for Lapack wrapper functions. This header
 *  file should be used if you are using the Intel Math Kernal Library for your
 *  Lapack and CBLAS implementation.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/

#include <mkl_lapack.h>


/******************************************************************************
 *
 *  Overloaded Low-Level C++ Wrappers for Lapack Routines.
 *
 *****************************************************************************/

namespace VM {


/* Square Solver Functions. */

inline int lapack_getrf(int m, int n, float *A, int lda, int* ipiv)
{
    int info;
    sgetrf(&m, &n, A, &lda, ipiv, &info);
    return info;
}

inline int lapack_getrf(int m, int n, double *A, int lda, int* ipiv)
{
    int info;
    dgetrf(&m, &n, A, &lda, ipiv, &info);
    return info;
}

inline int lapack_getrs(int n, int nrhs, float *A, int lda, int* ipiv,
    float *B, int ldb)
{
    int info;
    char trans = 'N';
    sgetrs(&trans, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_getrs(int n, int nrhs, double *A, int lda, int* ipiv,
    double *B, int ldb)
{
    int info;
    char trans = 'N';
    dgetrs(&trans, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_getri(int n, float *A, int lda,  int* ipiv,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sgetri(&n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgetri(&n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_getri(int n, double *A, int lda, int* ipiv,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dgetri(&n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgetri(&n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_sytrf(char uplo, int n, float *A, int lda, int *ipiv,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    ssytrf(&uplo, &n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    ssytrf(&uplo, &n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_sytrf(char uplo, int n, double *A, int lda, int *ipiv,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dsytrf(&uplo, &n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dsytrf(&uplo, &n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_sytrs(char uplo, int n, int nrhs, float *A, int lda,
     int *ipiv, float *B, int ldb)
{
    int info;
    ssytrs(&uplo, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_sytrs(char uplo, int n, int nrhs, double *A, int lda,
     int *ipiv, double *B, int ldb)
{
    int info;
    dsytrs(&uplo, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_sytri(char uplo, int n, float *A, int lda, int* ipiv,
    WorkSpace<float>& wrk)
{
    int info;
    float *work = wrk(n);
    ssytri(&uplo, &n, A, &lda, ipiv, work, &info);
    return info;
}

inline int lapack_sytri(char uplo, int n, double *A, int lda, int* ipiv,
    WorkSpace<double>& wrk)
{
    int info;
    double *work = wrk(n);
    dsytri(&uplo, &n, A, &lda, ipiv, work, &info);
    return info;
}

inline int lapack_potrf(char uplo, int n, float *A, int lda)
{
    int info;
    spotrf(&uplo, &n, A, &lda, &info);
    return info;
}

inline int lapack_potrf(char uplo, int n, double *A, int lda)
{
    int info;
    dpotrf(&uplo, &n, A, &lda, &info);
    return info;
}

inline int lapack_potrs(char uplo, int n, int nrhs, float *A, int lda,
    float *B, int ldb)
{
    int info;
    spotrs(&uplo, &n, &nrhs, A, &lda, B, &ldb, &info);
    return info;
}

inline int lapack_potrs(char uplo, int n, int nrhs, double *A, int lda,
    double *B, int ldb)
{
    int info;
    dpotrs(&uplo, &n, &nrhs, A, &lda, B, &ldb, &info);
    return info;
}

inline int lapack_potri(char uplo, int n, float *A, int lda)
{
    int info;
    spotri(&uplo, &n, A, &lda, &info);
    return info;
}

inline int lapack_potri(char uplo, int n, double *A, int lda)
{
    int info;
    dpotri(&uplo, &n, A, &lda, &info);
    return info;
}


/* Eigenvalue Functions. */

inline int lapack_syev(char jobz, char uplo, int n, float *A, int lda,
    float *w, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    ssyev(&jobz, &uplo, &n, A, &lda, w, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    ssyev(&jobz, &uplo, &n, A, &lda, w, work, &lwork, &info);
    return info;
}
 
inline int lapack_syev(char jobz, char uplo, int n, double *A, int lda,
    double *w, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dsyev(&jobz, &uplo, &n, A, &lda, w, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dsyev(&jobz, &uplo, &n, A, &lda, w, work, &lwork, &info);
    return info;
}

inline int lapack_geev(char jobvl, char jobvr, int n, float *A, int lda,
    float *wr, float *wi, float *vl, int ldvl, float *vr, int ldvr,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sgeev(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgeev(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr, work,
        &lwork, &info);
    return info;
}
    
inline int lapack_geev(char jobvl, char jobvr, int n, double *A, int lda,
    double *wr, double *wi, double *vl, int ldvl, double *vr, int ldvr,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dgeev(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgeev(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr, work,
        &lwork, &info);
    return info;
}


/* SVD Functions. */

inline int lapack_gesvd(char jobu, char jobvt, int m, int n, float *A, int lda,
    float *S, float *U, int ldu, float *VT, int ldvt, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sgesvd(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgesvd(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, work, &lwork,
        &info);
    return info;
}

inline int lapack_gesvd(char jobu, char jobvt, int m, int n, double *A, int lda,
    double *S, double *U, int ldu, double *VT, int ldvt, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dgesvd(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgesvd(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, work, &lwork,
        &info);
    return info;
}


/* General Solver Functions. */

inline int lapack_gelqf(int m, int n, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{    
    int info, lwork = -1;
    float opt_lwork, *work;
    sgelqf(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgelqf(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_gelqf(int m, int n, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{    
    int info, lwork = -1;
    double opt_lwork, *work;
    dgelqf(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgelqf(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_ormlq(char side, char trans, int m, int n, int k, float *A,
    int lda, float *tau, float *C, int ldc, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sormlq(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sormlq(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_ormlq(char side, char trans, int m, int n, int k, double *A,
    int lda, double *tau, double *C, int ldc, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dormlq(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dormlq(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_geqrf(int m, int n, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{    
    int info, lwork = -1;
    float opt_lwork, *work;
    sgeqrf(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgeqrf(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_geqrf(int m, int n, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{    
    int info, lwork = -1;
    double opt_lwork, *work;
    dgeqrf(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgeqrf(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_ormqr(char side, char trans, int m, int n, int k, float *A,
    int lda, float *tau, float *C, int ldc, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sormqr(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sormqr(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_ormqr(char side, char trans, int m, int n, int k, double *A,
    int lda, double *tau, double *C, int ldc, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dormqr(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dormqr(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_orglq(int m, int n, int k, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sorglq(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sorglq(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_orglq(int m, int n, int k, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dorglq(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dorglq(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_orgqr(int m, int n, int k, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sorgqr(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sorgqr(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_orgqr(int m, int n, int k, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dorgqr(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dorgqr(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_trtri(char uplo, char diag, int n, float *A, int lda)
{
    int info;
    strtri(&uplo, &diag, &n, A, &lda, &info);
    return info;
}

inline int lapack_trtri(char uplo, char diag, int n, double *A, int lda)
{
    int info;
    dtrtri(&uplo, &diag, &n, A, &lda, &info);
    return info;
}


} /* namespace VM */



#endif /* VM_LAPACK_MKL_H */
