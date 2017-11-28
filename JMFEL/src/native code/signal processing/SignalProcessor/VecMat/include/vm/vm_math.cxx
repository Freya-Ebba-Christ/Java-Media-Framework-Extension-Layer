#ifndef VM_MATH_CXX
#define VM_MATH_CXX

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
 *  vm_math.cxx - Implementation file for math functionality.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

/******************************************************************************
 *
 *  Binary arithmetic operators for vectors. Vectors must be the same length.
 *
 *****************************************************************************/

template <class T>
Vector<T> operator+(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result += rhs;
    return result;
}

template <class T>
Vector<T> operator-(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result -= rhs;
    return result;
}

template <class T>
Vector<T> operator*(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result *= rhs;
    return result;
}

template <class T>
Vector<T> operator/(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result /= rhs;
    return result;
}

template <class T>
Vector<T> operator%(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result %= rhs;
    return result;
}

template <class T>
Vector<T> operator&(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result &= rhs;
    return result;
}

template <class T>
Vector<T> operator^(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result ^= rhs;
    return result;
}

template <class T>
Vector<T> operator|(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result |= rhs;
    return result;
}

template <class T>
Vector<T> operator<<(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result <<= rhs;
    return result;
}

template <class T>
Vector<T> operator>>(const Vector<T>& lhs, const Vector<T>& rhs)
{
    Vector<T> result = lhs.copy();
    result >>= rhs;
    return result;
}


/******************************************************************************
 *
 *  Binary relational operators for vectors. Vectors must be the same size.
 *
 *****************************************************************************/

template <class T>
Vector<bool> operator==(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) == (*p3));
    return result;
}

template <class T>
Vector<bool> operator!=(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) != (*p3));
    return result;
}

template <class T>
Vector<bool> operator<(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) < (*p3));
    return result;
}

template <class T>
Vector<bool> operator>(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) > (*p3));
    return result;
}

template <class T>
Vector<bool> operator<=(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) <= (*p3));
    return result;
}

template <class T>
Vector<bool> operator>=(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) >= (*p3));
    return result;
}

template <class T>
Vector<bool> operator&&(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) && (*p3));
    return result;
}

template <class T>
Vector<bool> operator||(const Vector<T>& lhs, const Vector<T>& rhs)
{
    if (lhs.size() != rhs.size())
        vmerror("Size mismatch error in Vector relational operator.");
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin(), p3 = rhs.begin();
    bool *p1, *a = result.data(), *b = a + lhs.size();
    for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
        (*p1) = ((*p2) || (*p3));
    return result;
}


/******************************************************************************
 *
 *  Binary arithmetic operators for vectors and scalers.
 *
 *****************************************************************************/

template <class T>
Vector<T> operator+(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result += a;
    return result;
}

template <class T>
Vector<T> operator+(const T& a, const Vector<T>& rhs)
{
    Vector<T> result = rhs.copy();
    result += a;
    return result;
}

template <class T>
Vector<T> operator-(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result -= a;
    return result;
}

template <class T>
Vector<T> operator-(const T& a, const Vector<T>& rhs)
{
    Vector<T> result(a, rhs.size());
    result -= rhs;
    return result;
}

template <class T>
Vector<T> operator*(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result *= a;
    return result;
}

template <class T>
Vector<T> operator*(const T& a, const Vector<T>& rhs)
{
    Vector<T> result = rhs.copy();
    result *= a;
    return result;
}

template <class T>
Vector<T> operator/(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result /= a;
    return result;
}

template <class T>
Vector<T> operator/(const T& a, const Vector<T>& rhs)
{
    Vector<T> result(a, rhs.size());
    result /= rhs;
    return result;
}

template <class T>
Vector<T> operator%(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result %= a;
    return result;
}

template <class T>
Vector<T> operator%(const T& a, const Vector<T>& rhs)
{
    Vector<T> result(a, rhs.size());
    result %= rhs;
    return result;
}

template <class T>
Vector<T> operator&(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result &= a;
    return result;
}

template <class T>
Vector<T> operator&(const T& a, const Vector<T>& rhs)
{
    Vector<T> result = rhs.copy();
    result &= a;
    return result;
}

template <class T>
Vector<T> operator^(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result ^= a;
    return result;
}

template <class T>
Vector<T> operator^(const T& a, const Vector<T>& rhs)
{
    Vector<T> result = rhs.copy();
    result ^= a;
    return result;
}

template <class T>
Vector<T> operator|(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result |= a;
    return result;
}

template <class T>
Vector<T> operator|(const T& a, const Vector<T>& rhs)
{
    Vector<T> result = rhs.copy();
    result |= a;
    return result;
}

template <class T>
Vector<T> operator<<(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result <<= a;
    return result;
}

template <class T>
Vector<T> operator<<(const T& a, const Vector<T>& rhs)
{
    Vector<T> result(a, rhs.size());
    result <<= rhs;
    return result;
}

template <class T>
Vector<T> operator>>(const Vector<T>& lhs, const T& a)
{
    Vector<T> result = lhs.copy();
    result >>= a;
    return result;
}

template <class T>
Vector<T> operator>>(const T& a, const Vector<T>& rhs)
{
    Vector<T> result(a, rhs.size());
    result >>= rhs;
    return result;
}


/******************************************************************************
 *
 *  Binary relational operators for vectors and scalers.
 *
 *****************************************************************************/

template <class T>
Vector<bool> operator==(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) == a);
    return result;
}

template <class T>
Vector<bool> operator==(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a == (*p2));
    return result;
}

template <class T>
Vector<bool> operator!=(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) != a);
    return result;
}

template <class T>
Vector<bool> operator!=(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a != (*p2));
    return result;
}

template <class T>
Vector<bool> operator<(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) < a);
    return result;
}

template <class T>
Vector<bool> operator<(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a < (*p2));
    return result;
}

template <class T>
Vector<bool> operator>(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) > a);
    return result;
}

template <class T>
Vector<bool> operator>(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a > (*p2));
    return result;
}

template <class T>
Vector<bool> operator<=(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) <= a);
    return result;
}

template <class T>
Vector<bool> operator<=(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a <= (*p2));
    return result;
}

template <class T>
Vector<bool> operator>=(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) >= a);
    return result;
}

template <class T>
Vector<bool> operator>=(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a >= (*p2));
    return result;
}

template <class T>
Vector<bool> operator&&(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) && a);
    return result;
}

template <class T>
Vector<bool> operator&&(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a && (*p2));
    return result;
}

template <class T>
Vector<bool> operator||(const Vector<T>& lhs, const T& a)
{
    Vector<bool> result(NoInit, lhs.size());
    typename Vector<T>::const_iterator p2 = lhs.begin();
    bool *p1, *e = result.data() + lhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = ((*p2) || a);
    return result;
}

template <class T>
Vector<bool> operator||(const T& a, const Vector<T>& rhs)
{
    Vector<bool> result(NoInit, rhs.size());
    typename Vector<T>::const_iterator p2 = rhs.begin();
    bool *p1, *e = result.data() + rhs.size();
    for (p1 = result.data(); p1 < e; ++p1, ++p2)
        (*p1) = (a || (*p2));
    return result;
}


/******************************************************************************
 *
 *  Binary arithmetic operators for matrices. Matrices must have the same
 *  dimension.
 *
 *****************************************************************************/

template <class T>
Matrix<T> operator+(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result += rhs;
    return result;
}

template <class T>
Matrix<T> operator-(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result -= rhs;
    return result;
}

template <class T>
Matrix<T> operator*(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result *= rhs;
    return result;
}

template <class T>
Matrix<T> operator%(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result %= rhs;
    return result;
}

template <class T>
Matrix<T> operator&(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result &= rhs;
    return result;
}

template <class T>
Matrix<T> operator^(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result ^= rhs;
    return result;
}

template <class T>
Matrix<T> operator|(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result |= rhs;
    return result;
}

template <class T>
Matrix<T> operator<<(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result <<= rhs;
    return result;
}

template <class T>
Matrix<T> operator>>(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result >>= rhs;
    return result;
}

template <class T>
Matrix<T> operator/(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    Matrix<T> result = lhs.copy();
    result /= rhs;
    return result;
}


/******************************************************************************
 *
 *  Binary relational operators for matrices. Matrices must have the same
 *  dimension.
 *
 *****************************************************************************/

template <class T>
Matrix<bool> operator==(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) == (*p3));
    }
    return result;
}

template <class T>
Matrix<bool> operator!=(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) != (*p3));
    }
    return result;
}

template <class T>
Matrix<bool> operator<(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) < (*p3));
    }
    return result;
}

template <class T>
Matrix<bool> operator>(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) > (*p3));
    }
    return result;
}

template <class T>
Matrix<bool> operator<=(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) <= (*p3));
    }
    return result;
}

template <class T>
Matrix<bool> operator>=(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) >= (*p3));
    }
    return result;
}

template <class T>
Matrix<bool> operator&&(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) && (*p3));
    }
    return result;
}

template <class T>
Matrix<bool> operator||(const Matrix<T>& lhs, const Matrix<T>& rhs)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    if (n != rhs.nrows() || m != rhs.ncols())
        vmerror("Size mismatch error in Matrix relational operator.");
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, p3, a, b;
    for (i = 0; i < n; ++i)
    {
        a = lhs.row_begin(i);
        b = lhs.row_end(i);
        p3 = rhs.row_begin(i);
        for (p2 = a; p2 < b; ++p2, ++p3, ++p1)
            (*p1) = ((*p2) || (*p3));
    }
    return result;
}


/******************************************************************************
 *
 *  Binary arithmetic operators for matrices with scalers.
 *
 *****************************************************************************/

template <class T>
Matrix<T> operator+(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result += a;
    return result;
}

template <class T>
Matrix<T> operator+(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result = rhs.copy();
    result += a;
    return result;
}

template <class T>
Matrix<T> operator-(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result -= a;
    return result;
}

template <class T>
Matrix<T> operator-(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result(a, rhs.nrows() * rhs.ncols());
    result -= rhs;
    return result;
}

template <class T>
Matrix<T> operator*(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result *= a;
    return result;
}

template <class T>
Matrix<T> operator*(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result = rhs.copy();
    result *= a;
    return result;
}

template <class T>
Matrix<T> operator/(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result /= a;
    return result;
}

template <class T>
Matrix<T> operator/(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result(a, rhs.nrows() * rhs.ncols());
    result /= rhs;
    return result;
}

template <class T>
Matrix<T> operator%(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result %= a;
    return result;
}

template <class T>
Matrix<T> operator%(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result(a, rhs.nrows() * rhs.ncols());
    result %= rhs;
    return result;
}

template <class T>
Matrix<T> operator&(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result &= a;
    return result;
}

template <class T>
Matrix<T> operator&(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result = rhs.copy();
    result &= a;
    return result;
}

template <class T>
Matrix<T> operator^(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result ^= a;
    return result;
}

template <class T>
Matrix<T> operator^(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result = rhs.copy();
    result ^= a;
    return result;
}

template <class T>
Matrix<T> operator|(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result |= a;
    return result;
}

template <class T>
Matrix<T> operator|(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result = rhs.copy();
    result |= a;
    return result;
}

template <class T>
Matrix<T> operator<<(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result <<= a;
    return result;
}

template <class T>
Matrix<T> operator<<(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result(a, rhs.nrows() * rhs.ncols());
    result <<= rhs;
    return result;
}

template <class T>
Matrix<T> operator>>(const Matrix<T>& lhs, const T& a)
{
    Matrix<T> result = lhs.copy();
    result >>= a;
    return result;
}

template <class T>
Matrix<T> operator>>(const T& a, const Matrix<T>& rhs)
{
    Matrix<T> result(a, rhs.nrows() * rhs.ncols());
    result >>= rhs;
    return result;
}


/******************************************************************************
 *
 *  Binary relational operators for matrices and scalers.
 *
 *****************************************************************************/

template <class T>
Matrix<bool> operator==(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) == a);
    }
    return result;
}

template <class T>
Matrix<bool> operator==(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a == (*p2));
    }
    return result;
}

template <class T>
Matrix<bool> operator!=(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) != a);
    }
    return result;
}

template <class T>
Matrix<bool> operator!=(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a != (*p2));
    }
    return result;
}

template <class T>
Matrix<bool> operator<(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) < a);
    }
    return result;
}

template <class T>
Matrix<bool> operator<(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a < (*p2));
    }
    return result;
}

template <class T>
Matrix<bool> operator>(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) > a);
    }
    return result;
}

template <class T>
Matrix<bool> operator>(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a > (*p2));
    }
    return result;
}

template <class T>
Matrix<bool> operator<=(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) <= a);
    }
    return result;
}

template <class T>
Matrix<bool> operator<=(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a <= (*p2));
    }
    return result;
}

template <class T>
Matrix<bool> operator>=(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) >= a);
    }
    return result;
}

template <class T>
Matrix<bool> operator>=(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a >= (*p2));
    }
    return result;
}

template <class T>
Matrix<bool> operator&&(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) && a);
    }
    return result;
}

template <class T>
Matrix<bool> operator&&(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a && (*p2));
    }
    return result;
}

template <class T>
Matrix<bool> operator||(const Matrix<T>& lhs, const T& a)
{
    size_t n = lhs.nrows(), m = lhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = lhs.row_begin(i);
        re = lhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = ((*p2) || a);
    }
    return result;
}

template <class T>
Matrix<bool> operator||(const T& a, const Matrix<T>& rhs)
{
    size_t n = rhs.nrows(), m = rhs.ncols(), i;
    Matrix<bool> result(NoInit, n, m);
    bool *p1 = result.data();
    typename Vector<T>::const_iterator p2, rb, re;
    for (i = 0; i < n; ++i)
    {
        rb = rhs.row_begin(i);
        re = rhs.row_end(i);
        for (p2 = rb; p2 < re; ++p2, ++p1)
            (*p1) = (a || (*p2));
    }
    return result;
}


/******************************************************************************
 *
 *  Overloaded math functions for complex vectors.
 *
 *****************************************************************************/

template <class T>
Vector<T> norm(const Vector<std::complex<T> >& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::norm(x[i]);
    return tmp;
}

template <class T>
Vector<T> arg(const Vector<std::complex<T> >& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::arg(x[i]);
    return tmp;
}

template <class T>
Vector<std::complex<T> > polar(const Vector<T>& rho, const Vector<T>& theta)
{
    if (rho.size() != theta.size())
        vmerror("Size mismatch error in polar function.");
    size_t n = rho.size();
    Vector<std::complex<T> > tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::polar(rho[i], theta[i]);
    return tmp;
}

template <class T>
Vector<std::complex<T> > polar(const Vector<T>& rho, const T& theta)
{
    size_t n = rho.size();
    Vector<std::complex<T> > tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::polar(rho[i], theta);
    return tmp;
}

template <class T>
Vector<std::complex<T> > polar(const T& rho, const Vector<T>& theta)
{
    size_t n = theta.size();
    Vector<std::complex<T> > tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::polar(rho, theta[i]);
    return tmp;
}

template <class T>
Vector<T> real(const Vector<std::complex<T> >& x)
{
    Vector<T> temp(NoInit, x.size());
    T* p1 = temp.data();
    typename Vector<std::complex<T> >::const_iterator p2, a = x.begin(),
        b = x.end();
    for (p2 = a; p2 < b; ++p2, ++p1) (*p1) = (*p2).real();    
    return temp;
}

template <class T>
Vector<T> imag(const Vector<std::complex<T> >& x)
{
    Vector<T> temp(NoInit, x.size());
    T* p1 = temp.data();
    typename Vector<std::complex<T> >::const_iterator p2, a = x.begin(),
        b = x.end();
    for (p2 = a; p2 < b; ++p2, ++p1) (*p1) = (*p2).imag();    
    return temp;
}

template <class T>
Vector<std::complex<T> > conj(const Vector<std::complex<T> >& x)
{
    Vector<std::complex<T> > tmp(NoInit, x.size());
    std::complex<T> *p1 = tmp.data();
    typename Vector<std::complex<T> >::const_iterator p2, a = x.begin(),
        b = x.end();
    for (p2 = a; p2 < b; ++p2, ++p1) (*p1) = std::conj(*p2);    
    return tmp;
}


/******************************************************************************
 *
 *  Overloaded math functions for general vectors.
 *
 *****************************************************************************/

template <class T>
Vector<typename Traits<T>::real_type> abs(const Vector<T>& x)
{
    typedef typename Traits<T>::real_type real_type;
    Vector<real_type> tmp(NoInit, x.size());
    real_type *p1 = tmp.data();
    typename Vector<T>::const_iterator p2, a = x.begin(), b = x.end();
    for (p2 = a; p2 < b; ++p2, ++p1)
        (*p1) = VM::abs(*p2);
    return tmp;
}

template <class T>
Vector<T> acos(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::acos(x[i]);
    return tmp;
}

template <class T>
Vector<T> asin(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::asin(x[i]);
    return tmp;
}

template <class T>
Vector<T> atan(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::atan(x[i]);
    return tmp;
}

template <class T>
Vector<T> atan2(const Vector<T>& x, const Vector<T>& y)
{
    if (x.size() != y.size())
        vmerror("Size mismatch error in atan2 function.");
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::atan2(x[i], y[i]);
    return tmp;
}

template <class T>
Vector<T> atan2(const Vector<T> x, const T& y)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::atan2(x[i], y);
    return tmp;
}

template <class T>
Vector<T> atan2(const T& x, const Vector<T>& y)
{
    size_t n = y.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::atan2(x, y[i]);
    return tmp;
}

template <class T>
Vector<T> cos(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::cos(x[i]);
    return tmp;
}

template <class T>
Vector<T> cosh(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::cosh(x[i]);
    return tmp;
}

template <class T>
Vector<T> exp(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::exp(x[i]);
    return tmp;
}

template <class T>
Vector<T> log(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::log(x[i]);
    return tmp;
}

template <class T>
Vector<T> log10(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::log10(x[i]);
    return tmp;
}

template <class T>
Vector<T> pow(const Vector<T>& x, const Vector<T>& y)
{
    if (x.size() != y.size())
        vmerror("Size mismatch error in pow function.");
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::pow(x[i], y[i]);
    return tmp;
}

template <class T>
Vector<T> pow(const Vector<T> x, const T& y)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::pow(x[i], y);
    return tmp;
}

template <class T>
Vector<T> pow(const T& x, const Vector<T>& y)
{
    size_t n = y.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::pow(x, y[i]);
    return tmp;
}

template <class T>
Vector<T> sin(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::sin(x[i]);
    return tmp;
}

template <class T>
Vector<T> sinh(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::sinh(x[i]);
    return tmp;
}

template <class T>
Vector<T> sqrt(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::sqrt(x[i]);
    return tmp;
}

template <class T>
Vector<T> tan(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::tan(x[i]);
    return tmp;
}

template <class T>
Vector<T> tanh(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<T> tmp(NoInit, n);
    for (size_t i = 0; i < n; ++i)
        tmp[i] = std::tanh(x[i]);
    return tmp;
}


/******************************************************************************
 *
 *  Overloaded math functions for complex matrices.
 *
 *****************************************************************************/

template <class T>
Matrix<T> norm(const Matrix<std::complex<T> >& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::norm(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> arg(const Matrix<std::complex<T> >& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::arg(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<std::complex<T> > polar(const Matrix<T>& rho, const Matrix<T>& theta)
{
    if (rho.nrows() != theta.nrows() || rho.ncols() != theta.ncols())
        vmerror("Size mismatch error in polar function.");
    Matrix<std::complex<T> > tmp(NoInit, rho.nrows(), rho.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::polar(rho(i, j), theta(i, j));
    }
    return tmp;
}

template <class T>
Matrix<std::complex<T> > polar(const Matrix<T>& rho, const T& theta)
{
    Matrix<std::complex<T> > tmp(NoInit, rho.nrows(), rho.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::polar(rho(i, j), theta);
    }
    return tmp;
}

template <class T>
Matrix<std::complex<T> > polar(const T& rho, const Matrix<T>& theta)
{
    Matrix<std::complex<T> > tmp(NoInit, theta.nrows(), theta.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::polar(rho, theta(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> real(const Matrix<std::complex<T> >& x)
{
    Matrix<T> temp(NoInit, x.nrows(), x.ncols());
    T* p1 = temp.data();
    typename Vector<std::complex<T> >::const_iterator p2, a, b;
    for (size_t i = 0; i < x.nrows(); ++i)
    {
        a = x.row_begin(i);
        b = x.row_end(i);
        for (p2 = a; p2 < b; ++p2, ++p1) (*p1) = (*p2).real();
    }    
    return temp;
}

template <class T>
Matrix<T> imag(const Matrix<std::complex<T> >& x)
{
    Matrix<T> temp(NoInit, x.nrows(), x.ncols());
    T* p1 = temp.data();
    typename Vector<std::complex<T> >::const_iterator p2, a, b;
    for (size_t i = 0; i < x.nrows(); ++i)
    {
        a = x.row_begin(i);
        b = x.row_end(i);
        for (p2 = a; p2 < b; ++p2, ++p1) (*p1) = (*p2).imag();
    }    
    return temp;
}

template <class T>
Matrix<std::complex<T> > conj(const Matrix<std::complex<T> >& x)
{
    Matrix<std::complex<T> > temp(NoInit, x.nrows(), x.ncols());
    std::complex<T> *p1 = temp.data();
    typename Vector<std::complex<T> >::const_iterator p2, a, b;
    for (size_t i = 0; i < x.nrows(); ++i)
    {
        a = x.row_begin(i);
        b = x.row_end(i);
        for (p2 = a; p2 < b; ++p2, ++p1) (*p1) = std::conj(*p2);
    }    
    return temp;
}


/******************************************************************************
 *
 *  Overloaded math functions for general matrices.
 *
 *****************************************************************************/

template <class T>
Matrix<typename Traits<T>::real_type> abs(const Matrix<T>& x)
{
    typedef typename Traits<T>::real_type real_type;
    Matrix<real_type> tmp(NoInit, x.nrows(), x.ncols());
    real_type *p1 = tmp.data();
    typename Vector<T>::const_iterator p2, a, b;
    size_t i, n = x.nrows();
    for (i = 0; i < n; ++i)
    {
        a = x.row_begin(i);
        b = x.row_end(i);
        for (p2 = a; p2 < b; ++p2, ++p1)
            (*p1) = VM::abs(*p2);
    }
    return tmp;
}

template <class T>
Matrix<T> acos(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::acos(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> asin(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::asin(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> atan(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::atan(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> atan2(const Matrix<T>& x, const Matrix<T>& y)
{
    if (x.nrows() != y.nrows() || x.ncols() != y.ncols())
        vmerror("Size mismatch in atan2 function.");
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::atan2(x(i, j), y(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> atan2(const Matrix<T> x, const T& y)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::atan2(x(i, j), y);
    }
    return tmp;
}

template <class T>
Matrix<T> atan2(const T& x, const Matrix<T>& y)
{
    Matrix<T> tmp(NoInit, y.nrows(), y.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::atan2(x, y(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> cos(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::cos(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> cosh(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::cosh(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> exp(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::exp(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> log(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::log(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> log10(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::log10(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> pow(const Matrix<T>& x, const Matrix<T>& y)
{
    if (x.nrows() != y.nrows() || x.ncols() != y.ncols())
        vmerror("Size mismatch in pow function.");
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::pow(x(i, j), y(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> pow(const Matrix<T> x, const T& y)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::pow(x(i, j), y);
    }
    return tmp;
}

template <class T>
Matrix<T> pow(const T& x, const Matrix<T>& y)
{
    Matrix<T> tmp(NoInit, y.nrows(), y.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::pow(x, y(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> sin(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::sin(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> sinh(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::sinh(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> sqrt(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::sqrt(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> tan(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::tan(x(i, j));
    }
    return tmp;
}

template <class T>
Matrix<T> tanh(const Matrix<T>& x)
{
    Matrix<T> tmp(NoInit, x.nrows(), x.ncols());
    size_t n = tmp.nrows();
    size_t m = tmp.ncols();
    size_t i, j;
    for (i = 0; i < n; ++i)
    {
        for (j = 0; j < m; ++j)
            tmp(i, j) = std::tanh(x(i, j));
    }
    return tmp;
}

} /* namespace VM */


#endif /* VM_MATH_CXX */
