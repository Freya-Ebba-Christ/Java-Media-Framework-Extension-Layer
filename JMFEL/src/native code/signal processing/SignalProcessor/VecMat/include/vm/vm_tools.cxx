#ifndef VM_TOOLS_CXX
#define VM_TOOLS_CXX

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
 *  vm_tools.cxx - Implementation file for support functions and classes.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

/******************************************************************************
 *
 *  Finds the variance of a vector. Type T should have a float_type defined in
 *  the Traits class. The variance is not defined for complex types.
 *
 *****************************************************************************/


template <class T>
typename Traits<T>::float_type variance(const Vector<T>& x)
{
    size_t len = x.size();
    typename Traits<T>::float_type m = mean(x);
    typename Vector<T>::const_iterator i, a = x.begin(), b = x.end();
    typename Traits<T>::float_type var = square(*a - m);
    for (i = a + 1; i < b; ++i)
        var += square(*i - m);
    return var /= len;
}

template <class T>
typename Traits<T>::float_type s_variance(const Vector<T>& x)
{
    size_t len = x.size();
    typename Traits<T>::float_type m = mean(x);
    typename Vector<T>::const_iterator i, a = x.begin(), b = x.end();
    typename Traits<T>::float_type var = square(*a - m);
    for (i = a + 1; i < b; ++i)
        var += square(*i - m);
    return var /= (len - 1);
}



/******************************************************************************
 *
 *  Returns the normalized probability density function of a vector. Type T
 *  should be a real arithmetic type which can be cast to double.
 *
 *****************************************************************************/

template <class T>
Vector<double> prob_dens_func(const Vector<T>& x, size_t bins, T low, T high)
{
    size_t i, j, len = x.size();
    if (!len) return Vector<double>();
    if (!bins)
    {
        if (len > 1)
            bins = size_t(std::exp(0.626 + 0.41 * std::log(len - 1.0)));
        else
            bins = 1;
    }
    Vector<double> pdf(bins);
    
    if (low >= high)
    {
        low = x.min();
        high = x.max();
    }
    
    double delta = double(high - low) / bins;
    for (i = 0; i < len; ++i)
    {
        j = size_t((x[i] - low) / delta);
        if (j >= 0 && j < bins)
            ++pdf[j];
        if (x[i] == high) ++pdf[bins - 1];
    }

    pdf /= pdf.sum();
    return pdf;
}


/******************************************************************************
 *
 *  Matrix Multiplication.
 *
 *****************************************************************************/

template <class T>
Matrix<T> matrix_multiply(const Matrix<T>& x, const Matrix<T>& y)
{
    size_t xn = x.nrows();
    size_t xm = x.ncols();
    size_t yn = y.nrows();
    size_t ym = y.ncols();
    if (!xn || !ym || xm != yn)
        vmerror("Size mismatch error in matrix_multiply() function.");
    Matrix<T> tmp(xn, ym);
    typename Vector<T>::const_iterator px, py;
    typename Vector<T>::iterator       pz;
    size_t i, j, k;
    for (i = 0; i < xn; ++i)
    {
        pz = tmp.row_begin(i);
        for (j = 0; j < ym; ++j)
        {
            px = x.row_begin(i);
            py = y.col_begin(j);
            (*pz) = (*px) * (*py);
            for (k = 1; k < xm; ++k)
            {
                ++px;
                ++py;
                (*pz) += (*px) * (*py);
            }
            ++pz;
        }
    }
    return tmp;
}

template <class T>
Vector<T> matrix_multiply(const Vector<T>& x, const Matrix<T>& y)
{
    size_t xm = x.size();
    size_t yn = y.nrows();
    size_t ym = y.ncols();
    if (!ym || xm != yn)
        vmerror("Size mismatch error in matrix_multiply() function.");
    Vector<T> tmp(ym);
    typename Vector<T>::const_iterator px, py;
    typename Vector<T>::iterator       pz = tmp.begin();
    size_t i, k;
    for (i = 0; i < ym; ++i)
    {
        px = x.begin();
        py = y.col_begin(i);
        (*pz) = (*px) * (*py);
        for (k = 1; k < xm; ++k)
        {
            ++px;
            ++py;
            (*pz) += (*px) * (*py);
        }
        ++pz;
    }
    return tmp;
}

template <class T>
Vector<T> matrix_multiply(const Matrix<T>& x, const Vector<T>& y)
{
    size_t xn = x.nrows();
    size_t xm = x.ncols();
    size_t yn = y.size();
    if (!xn || xm != yn)
        vmerror("Size mismatch error in matrix_multiply() function.");
    Vector<T> tmp(xn);
    typename Vector<T>::const_iterator px, py;
    typename Vector<T>::iterator       pz = tmp.begin();
    size_t i, k;
    for (i = 0; i < xn; ++i)
    {
        px = x.row_begin(i);
        py = y.begin();
        (*pz) = (*px) * (*py);
        for (k = 1; k < yn; ++k)
        {
            ++px;
            ++py;
            (*pz) += (*px) * (*py);
        }
        ++pz;
    }
    return tmp;
}

template <class T>
Matrix<T> direct_product(const Vector<T>& x, const Vector<T>& y)
{
    size_t n = x.size();
    size_t m = y.size();
    if (!n || !m)
        vmerror("Size mismatch error in direct_product() function.");
    Matrix<T> tmp(n, m);
    typename Vector<T>::const_iterator px, py;
    typename Vector<T>::iterator       pz;
    size_t i, j;
    px = x.begin();
    for (i = 0; i < n; ++i)
    {
        py = y.begin();
        pz = tmp.row_begin(i);
        for (j = 0; j < m; ++j)
        {
            (*pz) = (*px) * (*py);
            ++py;
            ++pz;
        }
        ++px;
    }
    return tmp;
}

template <class T>
inline T cj_(const T& x) {return x;}

template <class T>
inline std::complex<T> cj_(const std::complex<T>& x) {return std::conj(x);}

template <class T>
T dot_product(const Vector<T>& x, const Vector<T>& y, bool cf)
{
    size_t n = x.size();
    if (!n || y.size() != n)
        vmerror("Size mismatch error in dot_product() function.");
    typename Vector<T>::const_iterator px = x.begin(), py = y.begin(), e = x.end();
    T tmp;
    if (cf)
    {
        tmp = (*px) * cj_(*py);
        for (++px, ++py; px < e; ++px, ++py)
            tmp += (*px) * cj_(*py);
    }
    else
    {
        tmp = (*px) * (*py);
        for (++px, ++py; px < e; ++px, ++py)
            tmp += (*px) * (*py);
    }
    return tmp;
}

template <class T>
Matrix<T> matmult_trans(const Matrix<T>& x)
{
     size_t n = x.nrows(), m = x.ncols();
    Matrix<T> tmp(n, n);
    typename Vector<T>::const_iterator px, py;
    typename Vector<T>::iterator pz;
    size_t i, j, k;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j <= i; ++j)
        {
            px = x.row_begin(i);
            py = x.row_begin(j);
            pz = tmp.row_begin(i) + j;
            (*pz) = (*px) * (*py);
            for (k = 1; k < m; ++k)
            {
                ++px;
                ++py;
                (*pz) += (*px) * (*py);
            }
            tmp(j, i) = tmp(i, j);
        }
    }
    return tmp;   
}    

} /* namespace VM */


#endif /* VM_TOOLS_CXX */
