#ifndef VM_TOOLS_H
#define VM_TOOLS_H

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
 *  vm_tools.h - Header file for support functions and classes.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

/******************************************************************************
 *
 *  Statistics.
 *
 *****************************************************************************/

template <class T>
inline typename Traits<T>::float_type mean(const Vector<T>& x)
{
    typedef typename Traits<T>::float_type float_type;
    typedef typename Traits<float_type>::real_type real_float_type;
    real_float_type len = x.size();
    return float_type(x.sum()) / len;
}

template <class T>
typename Traits<T>::float_type variance(const Vector<T>& x);

template <class T>
typename Traits<T>::float_type s_variance(const Vector<T>& x);

template <class T>
inline typename Traits<T>::float_type stdv(const Vector<T>& x)
{
    return std::sqrt(variance(x));
}

template <class T>
inline typename Traits<T>::float_type s_stdv(const Vector<T>& x)
{
    return std::sqrt(s_variance(x));
}

template <class T>
Vector<double> prob_dens_func(const Vector<T>& x, size_t bins, T low = 0,
    T high = 0);


/******************************************************************************
 *
 *  Historgram Class. T should be a real floating point type, or a class which
 *  behaves like one.
 *
 *****************************************************************************/

template <class T>
class Histogram
{
private:
    Vector<int>  hist_;
    T            low_;
    T            high_;
    size_t       bins_;
    Vector<T>    dat_;

    void refresh_()
    {
        size_t i, j, len = dat_.size();

        T delta = (high_ - low_) / bins_;
        if (hist_.size() != bins_) hist_.reshape(bins_);
        hist_ = 0;
        for (i = 0; i < len; ++i)
        {
            j = size_t((dat_[i] - low_) / delta);
            if (j >= 0 && j < bins_) ++hist_[j];
            if (dat_[i] == high_) ++hist_[bins_ - 1];
        }
    }

public:
    Histogram() : hist_(0), low_(0), high_(0), bins_(0), dat_(0) {;}

    Histogram(const Histogram& h) : hist_(h.hist_.copy()), low_(h.low_),
        high_(h.high_), bins_(h.bins_), dat_(h.dat_) {;}

    Histogram(const Vector<T>& dat) : hist_(0), dat_(0)
    {
        dat_.view(dat);
        if (dat_.size())
        {
            low_  = dat_.min();
            high_ = dat_.max();
            set_bins(0);
        }
        else
        {
            low_  = 0;
            high_ = 0;
        }
    }

    ~Histogram() {;}

    Histogram& operator=(const Histogram& rhs)
    {
        if (&rhs == this) return *this;
        bins_ = rhs.bins_;
        if (bins_ != hist_.size()) hist_.reshape(bins_);
        hist_ = rhs.hist_;
        low_  = rhs.low_;
        high_ = rhs.high_;
        dat_.view(rhs.dat_);
        return *this;
    }

    void set_range(T low = 0, T high = 0)
    {
        if (low_ < high_)
        {
            low_  = low;
            high_ = high;
        }
        else if (dat_.size())
        {
            low_  = dat_.min();
            high_ = dat_.max();
        }
        else
        {
            low_  = 0;
            high_ = 0;
        }
        refresh_();
    }

    void set_bins(size_t nb = 0)
    {
        if (!nb)
        {
            size_t i, m = 0;
            for (i = 0; i < dat_.size(); ++i)
                if (dat_[i] <= high_ && low_ <= dat_[i]) ++m;
            if (m > 1)
                nb = size_t(std::exp(0.626 + 0.41 * std::log(m - 1.0)));
        }
        bins_ = nb;
        hist_.reshape(bins_);
        refresh_();
    }

    void attach(const Vector<T>& dat)
    {
        dat_.view(dat);
        if (dat_.size())
        {
            low_  = dat_.min();
            high_ = dat_.max();
            set_bins(bins_);
        }
        else
        {
            low_  = 0;
            high_ = 0;
        }
    }

    T low() const {return low_;}

    T high() const {return high_;}

    size_t bins() const {return bins_;}

    Vector<int> hist() const
    {
        return hist_.copy();
    }

    operator Vector<int>() const
    {
        return hist_.copy();
    }
};


/******************************************************************************
 *
 *  IndirectComp Class. This is a function object used to compare the elements
 *  of two vectors based on their indices.
 *
 *****************************************************************************/

template <class T>
class IndirectComp
{
private:
    typename Vector<T>::const_iterator data_;

public:
    IndirectComp() : data_() {;}
    IndirectComp(const Vector<T>& x) : data_(x.begin()) {;}
    IndirectComp(const IndirectComp& comp) : data_(comp.data_) {;}
    ~IndirectComp() {;}

    IndirectComp& operator=(const IndirectComp& comp)
    {
        data_ = comp.data_;
        return *this;
    }
    bool operator()(size_t a, size_t b) const
    {
        return (data_[a] < data_[b]);
    }
    void attach(const Vector<T>& x)
    {
        data_ = x.begin();
    }
};


/******************************************************************************
 *
 *  Sorting Functions.
 *
 *****************************************************************************/

template <class T>
inline Vector<T> sort(const Vector<T>& x)
{
    Vector<T> temp = x.copy();
    temp.sort();
    return temp;
}

template <class T, class LT>
inline Vector<T> sort(const Vector<T>& x, LT lt)
{
    Vector<T> temp = x.copy();
    temp.sort(lt);
    return temp;
}

template <class T>
inline Vector<int> index(const Vector<T>& x)
{
    size_t n = x.size();
    Vector<int> indx(0, 1, n);
    IndirectComp<T> comp(x);
    int *first, *last;
    first = indx.data();
    last  = first + n;
    std::sort(first, last, comp);
    return indx;
}


/******************************************************************************
 *
 *  Matrix Multiplication.
 *
 *****************************************************************************/

template <class T>
Matrix<T> matrix_multiply(const Matrix<T>& x, const Matrix<T>& y);

template <class T>
Vector<T> matrix_multiply(const Vector<T>& x, const Matrix<T>& y);

template <class T>
Vector<T> matrix_multiply(const Matrix<T>& x, const Vector<T>& y);

template <class T>
Matrix<T> direct_product(const Vector<T>& x, const Vector<T>& y);

template <class T>
T dot_product(const Vector<T>& x, const Vector<T>& y, bool cf = false);

template <class T>
Matrix<T> matmult_trans(const Matrix<T>& x);


} /* namespace VM */

#include <vm/vm_tools.cxx>


#endif /* VM_TOOLS_H */
