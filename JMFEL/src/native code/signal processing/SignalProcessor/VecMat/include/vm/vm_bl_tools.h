#ifndef VM_BL_TOOLS_H
#define VM_BL_TOOLS_H

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
 *  vm_bl_tools.h - Header file for linear algebra tools.
 *
 *  Include this header file to use the CBLAS-Lapack based linear algebra tools.
 *
 *****************************************************************************/

#include <vm/vec_mat.h>


/******************************************************************************
 *
 *  Uncomment the following line to use the Intel MKL for CBLAS and LAPACK
 *  support.
 *
 *****************************************************************************/

//#define USE_MKL


/******************************************************************************
 *
 *  Workspace class for the Lapack functions which require additional workspace
 *  Memory.
 *
 *****************************************************************************/

namespace VM {
          
template <class T>
class WorkSpace
{
private:
    Vector<T> work_;
    int lwork_;
    
public:
    WorkSpace() : lwork_(0) {;}
    
    WorkSpace(const WorkSpace<T>& wrk) : work_(wrk.work_.copy()),
        lwork_(wrk.lwork_) {;}
    
    WorkSpace<T>& operator=(const WorkSpace<T>& wrk)
    {
        work_.ref(wrk.work_.copy());
        lwork_ = wrk.lwork_;
        return *this;
    }
    
    T* operator()(int n)
    {
        if (n > lwork_)
        {
            work_.reshape(n);
            lwork_ = n;
        }
        return work_.data();
    }
    
    Vector<T> get_vec(int n)
    {
        if (n > lwork_)
        {
            work_.reshape(n);
            lwork_ = n;
        }
        return work_;
    }
    
    Matrix<T> get_mat(int m, int n)
    {
        int nm = n * m;
        if (nm > lwork_)
        {
            work_.reshape(nm);
            lwork_ = nm;
        }
        return work_.matrix(0, m, n);
    }
    
    int capacity() const {return lwork_;}
    
    void clear()
    {
         work_.clear();
         lwork_ = 0;
    }
};

} /* namespace VM */


/******************************************************************************
 *
 *  CBLAS versions of the linear algebra functions provided in "vm_tools.h".
 *
 *****************************************************************************/

#include <vm/vm_cblas.h>

namespace VM {

template <class T>
inline T bl_dot_product(const Vector<T>& x, const Vector<T>& y, bool cf = false)
{
    BlasParam bp;
    bp.conj_flag(cf);
    return blas_dot_product(x, y, bp);
}

template <class T>
inline Matrix<T> bl_direct_product(const Vector<T>& x, const Vector<T>& y)
{
    Matrix<T> result(x.size(), y.size());
    blas_ger(1.0, x, y, result);
    return result;
}

template <class T>
inline Vector<T> bl_matrix_multiply(const Matrix<T>& A, const Vector<T>& x)
{
    Vector<T> result(NoInit, A.nrows());
    blas_gemv(1.0, A, x, 0.0, result);
    return result;
}

template <class T>
inline Vector<T> bl_matrix_multiply(const Vector<T>& x, const Matrix<T>& A)
{
    Vector<T> result(NoInit, A.ncols());
    blas_gemv(1.0, A.transpose(), x, 0.0, result);
    return result;
}


template <class T>
inline Matrix<T> bl_matrix_multiply(const Matrix<T>& A, const Matrix<T>& B)
{
    Mat_Layout mla = matrix_layout(A), mlb = matrix_layout(B);
    if (!mla || !mlb)
        vmerror("Matrix in wrong format for bl_matrix_multiply() function.");
    Matrix<T> result, bv;
    BlasParam bp;
    if (mla == ROW_MAJOR)
    {
        result.reshape(A.nrows(), B.ncols());
        if (mlb == COL_MAJOR)
        {
            bv.ref(B.transpose());
            bp.trans_flag_b(TRANS);
        }
        else bv.ref(B);
    }
    else
    {
        result.reshape(B.ncols(), A.nrows());
        result.ref(result.transpose());
        if (mlb == ROW_MAJOR)
        {
            bv.ref(B.transpose());
            bp.trans_flag_b(TRANS);
        }
        else bv.ref(B);
    }
    blas_gemm(1.0, A, bv, 0.0, result, bp);
    return result;
} 

template <class T>
inline Matrix<T> bl_matmult_trans(const Matrix<T>& A)
{
    Mat_Layout mla = matrix_layout(A);
    if (!mla) vmerror("Matrix in wrong format for bl_matmult_trans() function."); 
    Matrix<T> result(NoInit, A.nrows(), A.nrows());
    if (mla == COL_MAJOR) result.ref(result.transpose());
    BlasParam bp;
    bp.auto_complete_flag(AUTO_COMPLETE);
    blas_syrk(1.0, A, 0.0, result, bp);
    return result;
}

} /* namespace VM */


#ifdef USE_MKL
#include <vm/vm_lapack_mkl.h>
#else 
#include <vm/vm_lapack.h>
#endif /* USE_MKL */


namespace VM {

/******************************************************************************
 *
 *  Find the solution to a real square system of equations.
 *
 *****************************************************************************/

template <class T>
class SquareSolver
{
private:
    WorkSpace<T>   storage_, work_;
    WorkSpace<int> ipiv_;
    Matrix<T>      factors_;
    Vec_INT        piv_;
    T              det_;
    T              epsilon_;
    size_t         order_;
    int            method_;
    char           uplo_;
    bool           is_nonsingular_;

    SquareSolver(const SquareSolver<T>& ss);
    SquareSolver<T>& operator=(const SquareSolver<T>& ss);

    void lu_process_(const Matrix<T>& x)
    {        
        if (x.empty()) return;
        method_ = 1;
        if (x.nrows() != x.ncols())
            vmerror("SquareSolver class must take a square matrix.");
        order_ = x.nrows();
        factors_.ref(storage_.get_mat(order_, order_).transpose());
        factors_ = x;
        piv_.ref(ipiv_.get_vec(order_));
        det_ = 0.0;
        int info = lapack_getrf(order_, order_, factors_.data(), order_,
            piv_.data());
        if (info > 0) is_nonsingular_ = false;
        else check_nonsingular_();
        find_det_();
    }
    
    void ldl_process_(const Matrix<T>& x)
    {
        if (x.empty()) return;
        method_ = 2;
        if (x.nrows() != x.ncols())
            vmerror("SquareSolver class must take a square matrix.");
        order_ = x.nrows();
        factors_.ref(storage_.get_mat(order_, order_).transpose());
        factors_ = x;
        piv_.ref(ipiv_.get_vec(order_));
        det_ = 0.0;
        int info = lapack_sytrf(uplo_, order_, factors_.data(), order_,
            piv_.data(), work_);
        if (info > 0) is_nonsingular_ = false;
        else check_nonsingular_();
        find_det_();
    }
    
    void ll_process_(const Matrix<T>& x)
    {
        if (x.empty()) return;
        method_ = 3;
        if (x.nrows() != x.ncols())
            vmerror("SquareSolver class must take a square matrix.");
        order_ = x.nrows();
        factors_.ref(storage_.get_mat(order_, order_).transpose());
        factors_ = x;
        det_ = 0.0;
        int info = lapack_potrf(uplo_, order_, factors_.data(), order_);
        if (info > 0) clear(false);
        else is_nonsingular_ = true;
        find_det_();
    }

    void check_nonsingular_()
    {
        Vector<T> diag = factors_.diag();
        typename Vector<T>::iterator p, a = diag.begin(), b = diag.end();
        T eps = epsilon_, max_val = 0.0;
        is_nonsingular_ = true;
        for (p = a; p < b; ++p)
            if (VM::abs(*p) > max_val) max_val = VM::abs(*p);
        eps *= max_val;
        for (p = a; p < b; ++p)
            if (VM::abs(*p) < eps) is_nonsingular_ = false;
    }
    
    void find_det_()
    {
        if (!is_nonsingular_) return;
        det_ = 1.0;
        typename Vector<T>::const_iterator d;
        Vector<T> diag = factors_.diag();
        d = diag.begin();
        if (method_ == 1)
        {
            for (size_t i = 0; i < order_; ++i, ++d)
            {
                if (piv_[i] != i + 1) det_ *= -(*d);
                else det_ *= (*d);
            }
        }
        else if (method_ == 2)
        {
            for (size_t i = 0; i < order_; ++i, ++d)
            {
                if (piv_[i] != i + 1) det_ *= -(*d);
                else det_ *= (*d);
            }
        }
        else
        {
            for (size_t i = 0; i < order_; ++i, ++d)
            {
                det_ *= (*d);
            }
            det_ *= det_;
        }
    }

public:
    SquareSolver() : det_(0.0), epsilon_(std::numeric_limits<T>::epsilon()),
        order_(0), method_(0), uplo_(0), is_nonsingular_(false) {;}
    
    SquareSolver(const Matrix<T>& A) : det_(0.0),
        epsilon_(std::numeric_limits<T>::epsilon()), order_(0), method_(0),
        uplo_(0), is_nonsingular_(false)
    {
        lu_process_(A);
    }

    SquareSolver(const Matrix<T>& A, Up_Lo_Flag uplo, bool pdflag = false) :
        det_(0.0), epsilon_(std::numeric_limits<T>::epsilon()), order_(0),
        method_(0), uplo_(0), is_nonsingular_(false)
    {
        if (uplo == UPPER) uplo_ = 'U';
        else uplo_ = 'L';
        if (pdflag) ll_process_(A);
        else ldl_process_(A);
    }

    ~SquareSolver() {;}

    T get_epsilon() const {return epsilon_;}
    
    void set_epsilon(T eps)
    {
        epsilon_ = std::max(std::numeric_limits<T>::epsilon(), eps);
    } 

    void clear(bool clr_wrk = true)
    {
        if (clr_wrk)
        {
            storage_.clear();
            work_.clear();
            ipiv_.clear();
        }
        factors_.clear();
        piv_.clear();
        det_ = 0.0;
        order_ = 0;
        method_ = 0;
        uplo_ = 0;
        is_nonsingular_ = false;
    }
    
    size_t workspace() const
    {
        return (storage_.capacity() + work_.capacity()) * sizeof(T) +
            ipiv_.capacity() * sizeof(int);
    }    
    
    void re_init(const Matrix<T>& A)
    {
        clear(false);
        lu_process_(A);
    }
    
    void re_init(const Matrix<T>& A, Up_Lo_Flag uplo, bool pd_flag = false)
    {
        clear(false);
        if (uplo == UPPER) uplo_ = 'U';
        else uplo_ = 'L';
        if (pd_flag) ll_process_(A);
        else ldl_process_(A);
    }

    Matrix<T> get_factors() const
    {
        return factors_.copy();
    }
    
    Vec_INT get_ipiv() const
    {
        return piv_.copy();
    }
    
    Matrix<T> solve(const Matrix<T>& B)
    {
        if (B.nrows() != order_)
            vmerror("Size mismatch in SquareSolver::solve() function.");
        if (!is_nonsingular_) return Matrix<T>();
        Matrix<T> X(B.transpose().copy().transpose());
        size_t nrhs = X.ncols();
        if (method_ == 1)
        {
            lapack_getrs(order_, nrhs, factors_.data(), order_, piv_.data(),
                X.data(), order_);
        }
        else if (method_ == 2)
        {
            lapack_sytrs(uplo_, order_, nrhs, factors_.data(), order_,
                piv_.data(), X.data(), order_);
        }
        else
        {
            lapack_potrs(uplo_, order_, nrhs, factors_.data(), order_, X.data(),
                order_);
        }
        return X;
    }
    
    Vector<T> solve(const Vector<T>& b)
    {
        if (!is_nonsingular_ || b.empty()) return Vector<T>();
        Matrix<T> tmp = solve(b.col_matrix());
        return tmp.column(0);
    }        

    size_t order() const {return order_;}

    T det() const {return det_;}

    bool is_nonsingular() const {return is_nonsingular_;}
    
    Matrix<T> inverse()
    {
        if (!is_nonsingular_) return Matrix<T>();
        Matrix<T> result = factors_.copy().transpose();
        if (method_ == 1)
        {
            lapack_getri(order_, result.data(), order_, piv_.data(), work_);
        }
        else if (method_ == 2)
        {
            lapack_sytri(uplo_, order_, result.data(), order_, piv_.data(),
                work_);
        }
        else
        {
            lapack_potri(uplo_, order_, result.data(), order_);
        }
        return result;
    }
    
};


/******************************************************************************
 *
 *  Find the minimum norm-solution to an underdefined real system, or the
 *  least-squares solution to an overdefined real system.
 *
 *****************************************************************************/

template <class T>
class GenSolver
{
private:
    WorkSpace<T> storage_, work_;
    Matrix<T>    L_, R_, Q_;
    Vector<T>    tau_;
    size_t       nrows_, ncols_, rank_;
    T            epsilon_;
    bool         is_full_rank_;
    
    GenSolver(const GenSolver<T>& gs);
    GenSolver<T>& operator=(const GenSolver<T>& gs);

    void lq_process_(const Matrix<T>& x)
    {
        Vector<T> space = storage_.get_vec(nrows_ * ncols_ + nrows_ *
            nrows_ + nrows_);
        Q_.ref(space.matrix(0, nrows_, ncols_, 1, nrows_));
        Q_ = x;
        tau_.ref(space.slice(nrows_ * ncols_, nrows_));
        lapack_gelqf(nrows_, ncols_, Q_.data(), nrows_, tau_.data(), work_);
        Vector<T> diag = Q_.diag();
        typename Vector<T>::iterator a = diag.begin(), b = diag.end(), p;
        T eps = 0.0;
        for (p = a; p < b; ++p)
        {
            if (VM::abs(*p) > eps) eps = VM::abs(*p);
        }    
        eps *= epsilon_;
        for (p = a; p < b; ++p)
        {
            if (VM::abs(*p) > eps) ++rank_;
        }
        is_full_rank_ = (rank_ == nrows_);
        L_.ref(space.matrix(nrows_ * ncols_ + nrows_, nrows_, nrows_, 1,
            nrows_));
        typename Vector<T>::iterator pl, pq;
        size_t i;
        for (i = 0; i < nrows_; ++i)
        {
            a = L_.row_begin(i);
            b = a + i + 1;
            pq = Q_.row_begin(i);
            for (pl = a; pl < b; ++pl, ++pq) (*pl) = (*pq);
            b = L_.row_end(i);
            for (; pl < b; ++pl) (*pl) = 0.0;
        }
        lapack_orglq(nrows_, ncols_, nrows_, Q_.data(), nrows_, tau_.data(),
            work_);
    }
    
    void qr_process_(const Matrix<T>& x)
    {
        Vector<T> space = storage_.get_vec(nrows_ * ncols_ + ncols_ +
            ncols_ * ncols_);
        Q_.ref(space.matrix(0, nrows_, ncols_, 1, nrows_));
        Q_ = x;
        tau_.ref(space.slice(nrows_ * ncols_, ncols_));
        lapack_geqrf(nrows_, ncols_, Q_.data(), nrows_, tau_.data(), work_);
        Vector<T> diag = Q_.diag();
        typename Vector<T>::iterator a = diag.begin(), b = diag.end(), p;
        T eps = 0.0;
        for (p = a; p < b; ++p)
        {
            if (VM::abs(*p) > eps) eps = VM::abs(*p);
        }    
        eps *= epsilon_;
        for (p = a; p < b; ++p)
        {
            if (VM::abs(*p) > eps) ++rank_;
        }
        is_full_rank_ = (rank_ == ncols_);
        R_.ref(space.matrix(nrows_ * ncols_ + ncols_, ncols_, ncols_, 1,
            ncols_));
        typename Vector<T>::iterator pr, pq;
        size_t i;
        for (i = 0; i < ncols_; ++i)
        {
            a = R_.col_begin(i);
            b = a + i + 1;
            pq = Q_.col_begin(i);
            for (pr = a; pr < b; ++pr, ++pq) (*pr) = (*pq);
            b = R_.col_end(i);
            for (; pr < b; ++pr) (*pr) = 0.0;
        }
        lapack_orgqr(nrows_, ncols_, ncols_, Q_.data(), nrows_, tau_.data(),
            work_);
    }

public:
    GenSolver() : nrows_(0), ncols_(0), rank_(0),
        epsilon_(std::numeric_limits<T>::epsilon()), is_full_rank_(false) {;}
    
    GenSolver(const Matrix<T>& A) : nrows_(0), ncols_(0), rank_(0),
        epsilon_(std::numeric_limits<T>::epsilon()), is_full_rank_(false)
    {
        if (!A.empty())
        {
            nrows_ = A.nrows();
            ncols_ = A.ncols();
            if (nrows_ < ncols_) lq_process_(A);
            else qr_process_(A);
        }
    }
    
    ~GenSolver() {;}
        
    T get_epsilon() const {return epsilon_;}
    
    void set_epsilon(T eps)
    {
        epsilon_ = std::max(std::numeric_limits<T>::epsilon(), eps);
    } 

    void clear(bool clr_wrk = true)
    {
        if (clr_wrk)
        {
            storage_.clear();
            work_.clear();
        }
        L_.clear();
        R_.clear();
        Q_.clear();
        tau_.clear();
        nrows_ = 0;
        ncols_ = 0;
        rank_ = 0;
        is_full_rank_ = false;
    }

    size_t workspace() const
    {
        return (storage_.capacity() + work_.capacity()) * sizeof(T);
    }    
    
    void re_init(const Matrix<T>& A)
    {
        clear(false);
        if (!A.empty())
        {
            nrows_ = A.nrows();
            ncols_ = A.ncols();
            if (nrows_ < ncols_) lq_process_(A);
            else qr_process_(A);
        }
    } 

    size_t rank() const {return rank_;}
    
    bool is_full_rank() const {return is_full_rank_;}
    
    Matrix<T> get_L() const {return L_.copy();}
    
    Matrix<T> get_R() const {return R_.copy();}
    
    Matrix<T> get_Q() const {return Q_.copy();}
    
    Matrix<T> solve(const Matrix<T>& B)
    {
        if (B.empty() || !is_full_rank_) return Matrix<T>();
        if (B.nrows() != nrows_)
            vmerror("Size mismatch in GenSolver::solve() function.");
        size_t nrhs = B.ncols();
        Matrix<T> result(nrhs, ncols_);
        result.ref(result.transpose());
        Matrix<T> y = work_.get_mat(nrhs, nrows_);
        y.ref(y.transpose());
        y = B;
        BlasParam bp;
        if (nrows_ < ncols_) // Minimum norm solution.
        {
            bp.uplo_flag(LOWER);
            blas_trsm(1.0, L_, y, bp);
            bp.trans_flag_a(TRANS);
            blas_gemm(1.0, Q_, y, 0.0, result, bp);
        }
        else // Least squares solution.
        {
            bp.trans_flag_a(TRANS);
            blas_gemm(1.0, Q_, y, 0.0, result, bp);
            bp.uplo_flag(UPPER);
            bp.trans_flag_a(NO_TRANS);
            blas_trsm(1.0, R_, result, bp);
        }
        return result;
    }

    Vector<T> solve(const Vector<T>& b)
    {
        if (!is_full_rank_ || b.empty()) return Vector<T>();
        Matrix<T> tmp = solve(b.col_matrix());
        return tmp.column(0);
    }
    
    Matrix<T> inverse()
    {
        if (!is_full_rank_) return Matrix<T>();
        Matrix<T> result(NoInit, nrows_, ncols_);
        BlasParam bp;
        if (nrows_ < ncols_) // Minimum norm solution.
        {
            Matrix<T> li = work_.get_mat(nrows_, nrows_);
            li.ref(li.transpose());
            li = L_;
            lapack_trtri('L', 'N', nrows_, li.data(), nrows_);
            bp.uplo_flag(UPPER);
            bp.side_flag(LEFT);
            result = Q_;
            blas_trmm(1.0, li.transpose(), result, bp);
            result.ref(result.transpose()); 
        }
        else // Least squares solution.
        {
            result.ref(result.transpose());
            result = Q_.transpose();
            bp.uplo_flag(UPPER);
            blas_trsm(1.0, R_, result, bp);
        }
        return result;
    }
};


/******************************************************************************
 *
 *  Find the minimum-norm-least-squares solution to a real (possibly singular)
 *  system using Singular Value Decomposition.
 *
 *****************************************************************************/

template <class T>
class SVD
{
private:
    WorkSpace<T> storage_, work_;
    Matrix<T>    U_, VT_;
    Vector<T>    S_;
    size_t       nrows_, ncols_, rank_;
    T            epsilon_;
    bool         status_;
    
    SVD(const SVD<T>& svd);
    SVD<T>& operator=(const SVD<T>& svd);

    void process_(const Matrix<T>& x)
    {
        if (x.empty()) return;
        nrows_ = x.nrows();
        ncols_ = x.ncols();
        size_t smaller = std::min(nrows_, ncols_);
        size_t vbegin = nrows_ * ncols_;
        size_t sbegin = vbegin + ncols_ * smaller;
        size_t wrksize = sbegin + smaller;
        Vector<T> space = storage_.get_vec(wrksize);
        Matrix<T> A = space.matrix(0, nrows_, ncols_, 1, nrows_);
        A = x;
        char jobu = 'O', jobvt = 'S';
        U_.ref(space.matrix(0, nrows_, smaller, 1, nrows_));
        VT_.ref(space.matrix(vbegin, smaller, ncols_, 1, smaller));
        S_.ref(space.slice(sbegin, smaller));
        int info = lapack_gesvd(jobu, jobvt, nrows_, ncols_, A.data(), nrows_,
            S_.data(), 0, 1, VT_.data(), smaller, work_);
        if (info != 0) return;
        status_ = true;
        typename Vector<T>::iterator a = S_.begin(), b = S_.end(), i;
        T eps = S_.max() * epsilon_;
        for (i = a; i < b; ++i)
        {
            if ((*i) > eps) ++rank_;
            else (*i) = 0.0;
        }
    }
    
public:
    SVD() : nrows_(0), ncols_(0), rank_(0),
        epsilon_(std::numeric_limits<T>::epsilon()), status_(false) {;}
    
    SVD(const Matrix<T>& A) : nrows_(0), ncols_(0), rank_(0),
        epsilon_(std::numeric_limits<T>::epsilon()), status_(false)
    {
        process_(A);
    }
    
    ~SVD() {;}
    
    T get_epsilon() const {return epsilon_;}
    
    void set_epsilon(T eps)
    {
        epsilon_ = std::max(std::numeric_limits<T>::epsilon(), eps);
    } 

    void clear(bool clr_wrk = true)
    {
        if (clr_wrk)
        {
            storage_.clear();
            work_.clear();
        }
        U_.clear();
        VT_.clear();
        S_.clear();
        nrows_ = 0;
        ncols_ = 0;
        rank_ = 0;
        status_ = false;
    }

    size_t workspace() const
    {
        return (storage_.capacity() + work_.capacity()) * sizeof(T);
    }    

    void re_init(const Matrix<T>& A)
    {
         clear(false);
         process_(A);
    }

    Matrix<T> get_U() const {return U_.copy();}

    Matrix<T> get_VT() const {return VT_.copy();}

    Vector<T> get_S() const {return S_.copy();}
    
    Vector<T> view_S() const {return S_;}

    size_t rank() const {return rank_;}

    bool status() const {return status_;}
    
    Matrix<T> solve(const Matrix<T>& x)
    {
        if (!status_ || x.empty()) return Matrix<T>();
        if (x.nrows() != nrows_)
            vmerror("Size mismatch in SVD::solve() function.");
        T eps = S_.max() * epsilon_;
        size_t i, nrhs = x.ncols();
        size_t smaller = std::min(nrows_, ncols_);
        Matrix<T> W = work_.get_mat(nrows_ + ncols_, nrhs);
        Matrix<T> B = W.submatrix(0, 0, nrows_, nrhs);
        B = x;
        Matrix<T> y = W.submatrix(nrows_, 0, smaller, nrhs);
        blas_gemm(1.0, U_.transpose(), B, 0.0, y);
        typename Vector<T>::iterator a, b, p;
        T tmp;
        for (i = 0; i < smaller; ++i)
        {
            if (S_[i] > eps) tmp = 1.0 / S_[i];
            else tmp = 0.0;
            a = y.row_begin(i); b = y.row_end(i);
            for (p = a; p < b; ++p) (*p) *= tmp;
        } 
        Matrix<T> solution(NoInit, nrhs, ncols_);
        BlasParam bp;
        bp.trans_flag_a(TRANS);
        bp.trans_flag_b(TRANS);
        blas_gemm(1.0, y, VT_.transpose(), 0.0, solution, bp);
        return solution.transpose();        
    }
              
    Vector<T> solve(const Vector<T>& b)
    {
        if (!status_ || b.empty()) return Vector<T>();
        Matrix<T> tmp = solve(b.col_matrix());
        return tmp.column(0);
    }

    Matrix<T> inverse()
    {
        if (!status_) return Matrix<T>();
        T eps = S_.max() * epsilon_;
        size_t i;
        size_t smaller = std::min(nrows_, ncols_);
        Matrix<T> UT = work_.get_mat(smaller, nrows_);
        UT = U_.transpose();
        typename Vector<T>::iterator a, b, p;
        T tmp;
        for (i = 0; i < smaller; ++i)
        {
            if (S_[i] > eps) tmp = 1.0 / S_[i];
            else tmp = 0.0;
            a = UT.row_begin(i); b = UT.row_end(i);
            for (p = a; p < b; ++p) (*p) *= tmp;
        } 
        Matrix<T> solution(NoInit, nrows_, ncols_);
        BlasParam bp;
        bp.trans_flag_a(TRANS);
        bp.trans_flag_b(TRANS);
        blas_gemm(1.0, UT, VT_.transpose(), 0.0, solution, bp);
        return solution.transpose();        
    }
};


/******************************************************************************
 *
 *  Eigenvalues and Eigenvectors of a real matrix.
 *
 *****************************************************************************/

template <class T>
class EigenValue
{
private:
    WorkSpace<T>             storage_, work_;
    Matrix<T>                eigenvecs_re_;
    Matrix<std::complex<T> > eigenvecs_cplx_;
    Vector<T>                eigenvals_re_;
    Vector<std::complex<T> > eigenvals_cplx_;
    size_t                   order_;
    size_t                   num_real_, num_cplx_;
    int                      status_;
    
    EigenValue(const EigenValue<T>& ev);
    EigenValue<T>& operator=(const EigenValue<T>& ev);

    void process_(const Matrix<T>& x)
    {
        if (x.empty()) return;
        if (x.nrows() != x.ncols())
            vmerror("EigenValue class must take a square matrix.");
        order_ = x.nrows();
        char jobvl = 'N', jobvr = 'V';
        size_t wrkspace = 2* (square(order_) + order_);
        Vector<T> space = storage_.get_vec(wrkspace);
        Matrix<T> A = space.matrix(0, order_, order_, 1, order_);
        Matrix<T> vr = space.matrix(square(order_), order_, order_, 1, order_);
        Vector<T> wr = space.slice(2 * square(order_), order_);
        Vector<T> wi = space.slice(2 * square(order_) + order_, order_);
        A = x;
        status_ = lapack_geev(jobvl, jobvr, order_, A.data(), order_, wr.data(),
            wi.data(), 0, 1, vr.data(), order_, work_);
        --status_;
        size_t i, j, rcount = 0, ccount = 0;
        num_real_ = 0; num_cplx_ = 0;
        for (i = 0; i < order_; ++i)
        {
             if (wi[i] == 0.0) ++num_real_;
             else ++num_cplx_;
        }
        if (status_ == -1)
        {
            eigenvals_re_.reshape(num_real_);
            eigenvals_cplx_.reshape(num_cplx_);
            eigenvecs_re_.reshape(num_real_, order_);
            eigenvecs_cplx_.reshape(num_cplx_, order_);
        }
        for (i = 0; i < order_; ++i)
        {
            if (wi[i] == 0.0)
            {
                eigenvals_re_[rcount] = wr[i];
                if (status_ == -1)
                {
                    for (j = 0; j < order_; ++j)
                        eigenvecs_re_(rcount, j) = vr(j, i);
                }
                ++rcount;
            }
            else
            {
                eigenvals_cplx_[ccount] = std::complex<T>(wr[i], wi[i]);
                eigenvals_cplx_[ccount+1] = std::conj(eigenvals_cplx_[ccount]);
                if (status_ == -1)
                {
                    for (j = 0; j < order_; ++j)
                    {
                        eigenvecs_cplx_(ccount, j) = std::complex<T>(vr(j, i),
                            vr(j, i+1));
                        eigenvecs_cplx_(ccount + 1, j) =
                            std::conj(eigenvecs_cplx_(ccount, j));
                    }
                }
                ccount += 2;
                ++i;
            }
        }
    }

    void process_sym_(const Matrix<T>& x, Up_Lo_Flag uplo_f)
    { 
        order_ = x.nrows();
        if (x.ncols() != order_)
            vmerror("EigenValue class must take a square matrix.");
        Matrix<T> A = x.transpose().copy().transpose();
        Vector<T> evs(order_);
        char jobz = 'V';
        char uplo;
        if (uplo_f == UPPER) uplo = 'U';
        else uplo = 'L';
        status_ = lapack_syev(jobz, uplo, order_, A.data(), order_, evs.data(),
            work_);
        --status_;
        if (status_ == -1) eigenvecs_re_.ref(A.transpose());
        eigenvals_re_.ref(evs.transpose());
        num_real_ = order_;
    }

public:
    EigenValue() : order_(0), num_real_(0), num_cplx_(0), status_(-1) {;}
    
    EigenValue(const Matrix<T>& A) : order_(0), num_real_(0), num_cplx_(0),
        status_(-1)
    {
        process_(A);
    }
    
    EigenValue(const Matrix<T>& A, Up_Lo_Flag uplo) : order_(0), num_real_(0),
        num_cplx_(0), status_(-1)
    {
        process_sym_(A, uplo);
    }
    
    ~EigenValue() {;}

    size_t order() const {return order_;}
    
    size_t num_real() const {return num_real_;}

    size_t num_complex() const {return num_cplx_;}

    bool success() const {return status_ == -1;}

    int status() const {return status_;}
    
    void clear(bool clr_wrk = true)
    {
        if (clr_wrk)
        {
            storage_.clear();
            work_.clear();
        }
        order_ = 0;
        num_real_ = 0;
        num_cplx_ = 0;
        status_ = -1;
        eigenvecs_re_.clear();
        eigenvecs_cplx_.clear();
        eigenvals_re_.clear();
        eigenvals_cplx_.clear();
    }

    size_t workspace() const
    {
        return (storage_.capacity() + work_.capacity()) * sizeof(T);
    }    
    
    void re_init(const Matrix<T>& A)
    {
        clear(false);
        process_(A);
    }

    void re_init(const Matrix<T>& A, Up_Lo_Flag uplo)
    {
        clear(false);
        process_sym_(A, uplo);
    }

    Matrix<T> real_eigenvectors() const
    {
        return eigenvecs_re_;
    }
    
    Matrix<std::complex<T> > complex_eigenvectors() const
    {
        return eigenvecs_cplx_;
    }
    
    Vector<T> real_eigenvalues() const
    {
        return eigenvals_re_;
    }
    
    Vector<std::complex<T> > complex_eigenvalues() const
    {
        return eigenvals_cplx_;
    }
};        
    

} /* namespace VM */


#endif /* VM_BL_TOOLS_H */
