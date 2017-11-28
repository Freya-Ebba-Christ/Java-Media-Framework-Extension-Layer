#ifndef VM_LAPACK_H
#define VM_LAPACK_H

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
 *  vm_lapack.h - Header file for Lapack wrapper functions. This header file
 *  assumes that your Lapack library uses lower case function names with an
 *  underscore at the end.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/

/******************************************************************************
 *
 *  Prototypes for Fortran Lapack Subroutines.
 *
 *****************************************************************************/

extern "C" {

/* Square Solver Functions. */
void sgetrf_(int* m, int* n, float* A, int* lda, int* ipiv, int* info);
void dgetrf_(int* m, int* n, double* A, int* lda, int* ipiv, int* info);

void sgetrs_(char* t, int* n, int* nrhs, float* A, int* lda, int* ipiv,
    float* B, int* ldb, int *info);
void dgetrs_(char* t, int* n, int* nrhs, double* A, int* lda, int* ipiv,
    double* B, int* ldb, int *info);
    
void sgetri_(int* n, float* A, int* lda, int* ipiv, float* work, int* lwork,
    int* info);
void dgetri_(int* n, double* A, int* lda, int* ipiv, double* work, int* lwork,
    int* info);

void ssytrf_(char* uplo, int* n, float* A, int* lda, int* ipiv, float* work,
    int* lwork, int* info);
void dsytrf_(char* uplo, int* n, double* A, int* lda, int* ipiv, double* work,
    int* lwork, int* info);
    
void ssytrs_(char* uplo, int* n, int* nrhs, float* A, int* lda, int* ipiv,
    float* B, int* ldb, int* info);
void dsytrs_(char* uplo, int* n, int* nrhs, double* A, int* lda, int* ipiv,
    double* B, int* ldb, int* info);

void ssytri_(char* uplo, int* n, float* A, int* lda, int* ipiv, float* work,
    int* info);
void dsytri_(char* uplo, int* n, double* A, int* lda, int* ipiv, double* work,
    int* info);

void spotrf_(char* uplo, int* n, float* A, int* lda, int* info);
void dpotrf_(char* uplo, int* n, double* A, int* lda, int* info);

void spotrs_(char* uplo, int* n, int* nrhs, float* A, int* lda, float* B,
    int* ldb, int* info);
void dpotrs_(char* uplo, int* n, int* nrhs, double* A, int* lda, double* B,
    int* ldb, int* info);

void spotri_(char* uplo, int* n, float* A, int* lda, int* info);
void dpotri_(char* uplo, int* n, double* A, int* lda, int* info);


/* Eigenvalue Functions. */
void ssyev_(char* jobz, char* uplo, int* n, float* A, int* lda, float* w,
    float* work, int* lwork, int* info); 
void dsyev_(char* jobz, char* uplo, int* n, double* A, int* lda, double* w,
    double* work, int* lwork, int* info); 

void sgeev_(char* jobvl, char* jobvr, int* n, float* A, int* lda, float* wr,
    float* wi, float* vl, int* ldvl, float* vr, int* ldvr, float* work,
    int* lwork, int* info);
void dgeev_(char* jobvl, char* jobvr, int* n, double* A, int* lda, double* wr,
    double* wi, double* vl, int* ldvl, double* vr, int* ldvr, double* work,
    int* lwork, int* info);

/* SVD Functions. */
void sgesvd_(char* jobu, char* jobvt, int* m, int* n, float* A, int* lda,
    float* S, float* U, int* ldu, float* VT,  int* ldvt, float* work,
    int* lwork, int* info);
void dgesvd_(char* jobu, char* jobvt, int* m, int* n, double* A, int* lda,
    double* S, double* U, int* ldu, double* VT, int* ldvt, double* work,
    int* lwork, int* info);

/* General Solver Functions. */
void sgelqf_(int* m, int* n, float* A, int* lda, float* tau, float* work,
    int* lwork, int* info);
void dgelqf_(int* m, int* n, double* A, int* lda, double* tau, double* work,
    int* lwork, int* info);

void sormlq_(char* side, char* trans, int* m, int* n, int* k, float* A,
    int* lda, float* tau, float* C, int* ldc, float* work, int* lwork,
    int* info);
void dormlq_(char* side, char* trans, int* m, int* n, int* k, double* A,
    int* lda, double* tau, double* C, int* ldc, double* work, int* lwork,
    int* info);

void sgeqrf_(int* m, int* n, float* A, int* lda, float* tau, float* work,
    int* lwork, int* info);
void dgeqrf_(int* m, int* n, double* A, int* lda, double* tau, double* work,
    int* lwork, int* info);

void sormqr_(char* side, char* trans, int* m, int* n, int* k, float* A,
    int* lda, float* tau, float* C, int* ldc, float* work, int* lwork,
    int* info);
void dormqr_(char* side, char* trans, int* m, int* n, int* k, double* A,
    int* lda, double* tau, double* C, int* ldc, double* work, int* lwork,
    int* info);

void sorglq_(int* m, int* n, int* k, float* A, int* lda, float* tau,
    float* work, int* lwork, int* info);
void dorglq_( int* m, int* n, int* k, double* A, int* lda, double* tau,
    double* work, int* lwork, int* info);

void sorgqr_(int* m, int* n, int* k, float* A, int* lda, float* tau,
    float* work, int* lwork, int* info);
void dorgqr_(int* m, int* n, int* k, double* A, int* lda, double* tau,
    double* work, int* lwork, int* info);

void strtri_(char* uplo, char* diag, int* n, float* A, int* lda, int* info);
void dtrtri_(char* uplo, char* diag, int* n, double* A, int* lda, int* info);

} /* extern "C" */



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
    sgetrf_(&m, &n, A, &lda, ipiv, &info);
    return info;
}

inline int lapack_getrf(int m, int n, double *A, int lda, int* ipiv)
{
    int info;
    dgetrf_(&m, &n, A, &lda, ipiv, &info);
    return info;
}

inline int lapack_getrs(int n, int nrhs, float *A, int lda, int* ipiv,
    float *B, int ldb)
{
    int info;
    char trans = 'N';
    sgetrs_(&trans, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_getrs(int n, int nrhs, double *A, int lda, int* ipiv,
    double *B, int ldb)
{
    int info;
    char trans = 'N';
    dgetrs_(&trans, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_getri(int n, float *A, int lda, int* ipiv,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sgetri_(&n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgetri_(&n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_getri(int n, double *A, int lda, int* ipiv,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dgetri_(&n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgetri_(&n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_sytrf(char uplo, int n, float *A, int lda, int *ipiv,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    ssytrf_(&uplo, &n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    ssytrf_(&uplo, &n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_sytrf(char uplo, int n, double *A, int lda, int *ipiv,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dsytrf_(&uplo, &n, A, &lda, ipiv, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dsytrf_(&uplo, &n, A, &lda, ipiv, work, &lwork, &info);
    return info;
}

inline int lapack_sytrs(char uplo, int n, int nrhs, float *A, int lda,
    int *ipiv, float *B, int ldb)
{
    int info;
    ssytrs_(&uplo, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_sytrs(char uplo, int n, int nrhs, double *A, int lda,
     int *ipiv, double *B, int ldb)
{
    int info;
    dsytrs_(&uplo, &n, &nrhs, A, &lda, ipiv, B, &ldb, &info);
    return info;
}

inline int lapack_sytri(char uplo, int n, float *A, int lda, int* ipiv,
    WorkSpace<float>& wrk)
{
    int info;
    float *work = wrk(n);
    ssytri_(&uplo, &n, A, &lda, ipiv, work, &info);
    return info;
}

inline int lapack_sytri(char uplo, int n, double *A, int lda, int* ipiv,
    WorkSpace<double>& wrk)
{
    int info;
    double *work = wrk(n);
    dsytri_(&uplo, &n, A, &lda, ipiv, work, &info);
    return info;
}

inline int lapack_potrf(char uplo, int n, float *A, int lda)
{
    int info;
    spotrf_(&uplo, &n, A, &lda, &info);
    return info;
}

inline int lapack_potrf(char uplo, int n, double *A, int lda)
{
    int info;
    dpotrf_(&uplo, &n, A, &lda, &info);
    return info;
}

inline int lapack_potrs(char uplo, int n, int nrhs, float *A, int lda,
    float *B, int ldb)
{
    int info;
    spotrs_(&uplo, &n, &nrhs, A, &lda, B, &ldb, &info);
    return info;
}

inline int lapack_potrs(char uplo, int n, int nrhs, double *A, int lda,
    double *B, int ldb)
{
    int info;
    dpotrs_(&uplo, &n, &nrhs, A, &lda, B, &ldb, &info);
    return info;
}

inline int lapack_potri(char uplo, int n, float *A, int lda)
{
    int info;
    spotri_(&uplo, &n, A, &lda, &info);
    return info;
}

inline int lapack_potri(char uplo, int n, double *A, int lda)
{
    int info;
    dpotri_(&uplo, &n, A, &lda, &info);
    return info;
}


/* Eigenvalue Functions. */

inline int lapack_syev(char jobz, char uplo, int n, float *A, int lda,
    float *w, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    ssyev_(&jobz, &uplo, &n, A, &lda, w, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    ssyev_(&jobz, &uplo, &n, A, &lda, w, work, &lwork, &info);
    return info;
}
 
inline int lapack_syev(char jobz, char uplo, int n, double *A, int lda,
    double *w, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dsyev_(&jobz, &uplo, &n, A, &lda, w, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dsyev_(&jobz, &uplo, &n, A, &lda, w, work, &lwork, &info);
    return info;
}

inline int lapack_geev(char jobvl, char jobvr, int n, float *A, int lda,
    float *wr, float *wi, float *vl, int ldvl, float *vr, int ldvr,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sgeev_(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr,
        &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgeev_(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr, work,
        &lwork, &info);
    return info;
}
    
inline int lapack_geev(char jobvl, char jobvr, int n, double *A, int lda,
    double *wr, double *wi, double *vl, int ldvl, double *vr, int ldvr,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dgeev_(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr,
        &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgeev_(&jobvl, &jobvr, &n, A, &lda, wr, wi, vl, &ldvl, vr, &ldvr, work,
        &lwork, &info);
    return info;
}


/* SVD Functions. */

inline int lapack_gesvd(char jobu, char jobvt, int m, int n, float *A, int lda,
    float *S, float *U, int ldu, float *VT, int ldvt, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sgesvd_(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgesvd_(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, work, &lwork,
        &info);
    return info;
}

inline int lapack_gesvd(char jobu, char jobvt, int m, int n, double *A, int lda,
    double *S, double *U, int ldu, double *VT, int ldvt, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dgesvd_(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgesvd_(&jobu, &jobvt, &m, &n, A, &lda, S, U, &ldu, VT, &ldvt, work, &lwork,
        &info);
    return info;
}


/* General Solver Functions. */

inline int lapack_gelqf(int m, int n, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{    
    int info, lwork = -1;
    float opt_lwork, *work;
    sgelqf_(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgelqf_(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_gelqf(int m, int n, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{    
    int info, lwork = -1;
    double opt_lwork, *work;
    dgelqf_(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgelqf_(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_ormlq(char side, char trans, int m, int n, int k, float *A,
    int lda, float *tau, float *C, int ldc, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sormlq_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sormlq_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_ormlq(char side, char trans, int m, int n, int k, double *A,
    int lda, double *tau, double *C, int ldc, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dormlq_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dormlq_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_geqrf(int m, int n, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{    
    int info, lwork = -1;
    float opt_lwork, *work;
    sgeqrf_(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sgeqrf_(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_geqrf(int m, int n, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{    
    int info, lwork = -1;
    double opt_lwork, *work;
    dgeqrf_(&m, &n, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dgeqrf_(&m, &n, A, &lda, tau, work, &lwork, &info);
    return info;
}

inline int lapack_ormqr(char side, char trans, int m, int n, int k, float *A,
    int lda, float *tau, float *C, int ldc, WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sormqr_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sormqr_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_ormqr(char side, char trans, int m, int n, int k, double *A,
    int lda, double *tau, double *C, int ldc, WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dormqr_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, &opt_lwork,
        &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dormqr_(&side, &trans, &m, &n, &k, A, &lda, tau, C, &ldc, work, &lwork,
        &info);
    return info;    
}

inline int lapack_orglq(int m, int n, int k, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sorglq_(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sorglq_(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_orglq(int m, int n, int k, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dorglq_(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dorglq_(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_orgqr(int m, int n, int k, float *A, int lda, float *tau,
    WorkSpace<float>& wrk)
{
    int info, lwork = -1;
    float opt_lwork, *work;
    sorgqr_(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    sorgqr_(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_orgqr(int m, int n, int k, double *A, int lda, double *tau,
    WorkSpace<double>& wrk)
{
    int info, lwork = -1;
    double opt_lwork, *work;
    dorgqr_(&m, &n, &k, A, &lda, tau, &opt_lwork, &lwork, &info);
    lwork = int(opt_lwork);
    work = wrk(lwork);
    dorgqr_(&m, &n, &k, A, &lda, tau, work, &lwork, &info);
    return info;    
}

inline int lapack_trtri(char uplo, char diag, int n, float *A, int lda)
{
    int info;
    strtri_(&uplo, &diag, &n, A, &lda, &info);
    return info;
}

inline int lapack_trtri(char uplo, char diag, int n, double *A, int lda)
{
    int info;
    dtrtri_(&uplo, &diag, &n, A, &lda, &info);
    return info;
}


} /* namespace VM */


#endif /* VM_LAPACK_H */
