#ifndef MATRIX_H
#define MATRIX_H

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
 *  matrix.h - Matrix class declaration and definition.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {


template <class T>
class Matrix
{
public:
    typedef Allocator<T> allocator_type;
    
    typedef typename allocator_type::value_type       value_type;
    typedef typename allocator_type::pointer          pointer;
    typedef typename allocator_type::const_pointer    const_pointer;
    typedef typename allocator_type::size_type        size_type;
    typedef typename allocator_type::difference_type  difference_type;
    typedef typename allocator_type::reference        reference;
    typedef typename allocator_type::const_reference  const_reference;
    typedef typename Traits<T>::real_type             real_type;
    typedef typename Vector<T>::iterator              iterator;
    typedef typename Vector<T>::const_iterator        const_iterator;

    friend class Vector<T>;

private:
    size_type        nrows_, ncols_;
    difference_type  rstride_;
    difference_type  cstride_;
    pointer          ptr_;
    pointer          data_;
    size_type        *refs_;
    size_type        data_size_;


    void simple_ctor_(const true_type&) {;}
    void simple_ctor_(const false_type&)
    {
        value_type zero = Traits<value_type>::zero();
        std::uninitialized_fill_n(data_, data_size_, zero);
    }
    void simple_dtor_(const true_type&, allocator_type& alloc) {;}
    void simple_dtor_(const false_type&, allocator_type& alloc)
    {
        pointer p, e = data_ + data_size_;
        for (p = data_; p < e; ++p)
        {
            alloc.destroy(p);
        }
    }

    void real_view_(Matrix<real_type>& v, const true_type&) const
    {
        v.view(reinterpret_cast<real_type *>(ptr_), nrows_, ncols_,
            rstride_ * 2, cstride_ * 2);
    }
    
    void real_view_(Matrix<real_type>& v, const false_type&) const
    {
        v.view(*this);
    }
    
    void image_view_(Matrix<real_type>& v, const true_type&) const
    {
        v.view(reinterpret_cast<real_type *>(ptr_) + 1, nrows_, ncols_,
            rstride_ * 2, cstride_ * 2);
    }
    
    void image_view_(Matrix<real_type>& v, const false_type&) const
    {
        vmerror("Cannot make an imaginary view of a real matrix");
    }


public:

/******************************************************************************
 *
 *  Constructors and destructor.
 *
 *****************************************************************************/

    Matrix() : nrows_(0), ncols_(0), rstride_(0), cstride_(1), ptr_(0), data_(0),
        refs_(0), data_size_(0) {;}
    
    Matrix(size_type n, size_type m) : nrows_(n), ncols_(m), rstride_(m),
        cstride_(1), ptr_(0), data_(0), refs_(0), data_size_(n * m)
    {
        if (data_size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(data_size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Matrix constructor.");
            value_type zero = Traits<value_type>::zero();
            std::uninitialized_fill_n(data_, data_size_, zero);
            ptr_ = data_;
            refs_ = new size_type(1);
        }
        else
        {
            nrows_ = 0;
            ncols_ = 0;
            rstride_ = 0;
        }
    }
    
    Matrix(NoInit_, size_type n, size_type m) : nrows_(n), ncols_(m), rstride_(m),
        cstride_(1), ptr_(0), data_(0), refs_(0), data_size_(n * m)
    {
        if (data_size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(data_size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Matrix constructor.");
            ptr_ = data_;
            refs_ = new size_type(1);
            typename Traits<value_type>::is_simple is_simple;
            simple_ctor_(is_simple);
        }
        else
        {
            nrows_ = 0;
            ncols_ = 0;
            rstride_ = 0;
        }
    }
    
    Matrix(const_reference a, size_type n, size_type m) : nrows_(n), ncols_(m),
        rstride_(m), cstride_(1), ptr_(0), data_(0), refs_(0), data_size_(n * m)
    {
        if (data_size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(data_size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Matrix constructor.");
            std::uninitialized_fill_n(data_, data_size_, a);
            ptr_ = data_;
            refs_ = new size_type(1);
        }
        else
        {
            nrows_ = 0;
            ncols_ = 0;
            rstride_ = 0;
        }
    }
    
    Matrix(pointer a, size_type n, size_type m, difference_type rs = 0,
        difference_type cs = 1) : nrows_(n), ncols_(m), rstride_(rs), cstride_(cs),
        ptr_(a), data_(0), refs_(0), data_size_(0)
    {
        if (!rs) rstride_ = difference_type(ncols_) * cstride_;
        if (!n || !m)
        {
             nrows_ = 0;
             ncols_ = 0;
             rstride_ = 0;
             cstride_ = 1;
             ptr_ = 0;
        }       
    }
    
    Matrix(const Matrix<T>& rhs) : nrows_(rhs.nrows_), ncols_(rhs.ncols_),
        rstride_(rhs.rstride_), cstride_(rhs.cstride_), ptr_(rhs.ptr_),
        data_(rhs.data_), refs_(rhs.refs_), data_size_(rhs.data_size_)
    {
        if (refs_) ++(*refs_);
    }
    
    Matrix(const Matrix<real_type>& re, const Matrix<real_type>& im) :
        nrows_(re.nrows()), ncols_(re.ncols()), rstride_(re.ncols()), cstride_(1),
        ptr_(0), data_(0), refs_(0), data_size_(re.nrows() * re.ncols())
    {
        if (im.ncols() != ncols_ || im.nrows() != nrows_)
            vmerror("Size mismatch error in complex Matrix constructor.");
        if (data_size_)
        {
            difference_type i;
            allocator_type alloc;
            data_ = alloc.allocate(data_size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Matrix constructor.");
            ptr_ = data_;
            refs_ = new size_type(1);
            pointer p1, a, b;
            typename Vector<real_type>::const_iterator p2, p3;
            for (i = 0; i < difference_type(nrows_); ++i)
            {
                p2 = re.row_begin(i);
                p3 = im.row_begin(i);
                a = ptr_ + i * rstride_;
                b = a + ncols_;
                for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
                    alloc.construct(p1, value_type(*p2, *p3));
            }
        }
    }
    
    Matrix(const Matrix<real_type>& re, const real_type& im) : nrows_(re.nrows()),
        ncols_(re.ncols()), rstride_(re.ncols()), cstride_(1), ptr_(0), data_(0),
        refs_(0), data_size_(re.nrows() * re.ncols())
    {
        if (data_size_)
        {
            difference_type i;
            allocator_type alloc;
            data_ = alloc.allocate(data_size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Matrix constructor.");
            ptr_ = data_;
            refs_ = new size_type(1);
            pointer p1, a, b;
            typename Vector<real_type>::const_iterator p2;
            for (i = 0; i < difference_type(nrows_); ++i)
            {
                p2 = re.row_begin(i);
                a = ptr_ + i * rstride_;
                b = a + ncols_;
                for (p1 = a; p1 < b; ++p1, ++p2)
                    alloc.construct(p1, value_type(*p2, im));
            }
        }
    }
    
    ~Matrix()
    {
        if (nrows_) clear();
    }
    
    
/******************************************************************************
 *
 *  Member operators.
 *
 *****************************************************************************/

    Matrix<T>& operator=(const Matrix<T>& rhs)
    {
        if (this == &rhs) return *this;
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) = (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator+=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) += (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator-=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) -= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator*=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) *= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator/=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) /= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator%=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) %= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator&=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) &= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator^=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) ^= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator|=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) |= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator<<=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) <<= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator>>=(const Matrix<T>& rhs)
    {
        if (nrows_ == rhs.nrows_ && ncols_ == rhs.ncols_)
        {
            size_type i;
            iterator p1, a, b;
            const_iterator p2;
            for (i = 0; i < nrows_; ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                p2 = rhs.row_begin(i);
                for (p1 = a; p1 < b; ++p1, ++p2)
                {
                    (*p1) >>= (*p2);
                }
            }
        }
        else vmerror("Size mismatch error in Matrix assignment operator.");
        return *this;
    }
    
    Matrix<T>& operator=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) = a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator+=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) += a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator-=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) -= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator*=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) *= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator/=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) /= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator%=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) %= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator&=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) &= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator^=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) ^= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator|=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) |= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator>>=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) >>= a;
            }
        }
        return *this;
    }
    
    Matrix<T>& operator<<=(const_reference a)
    {
        size_type i;
        iterator p, e;
        for (i = 0; i < nrows_; ++i)
        {
            e = row_end(i);
            for (p = row_begin(i); p < e; ++p)
            {
                (*p) <<= a;
            }
        }
        return *this;
    }
    
    Matrix<T> operator+() const
    {
        Matrix<T> result = copy();
        size_type nm = nrows_ * ncols_;
        pointer p, e = result.ptr_ + nm;
        for (p = result.ptr_; p < e; ++p)
        {
            (*p) = +(*p);
        }
        return result;
    }
    
    Matrix<T> operator-() const
    {
        Matrix<T> result = copy();
        size_type nm = nrows_ * ncols_;
        pointer p, e = result.ptr_ + nm;
        for (p = result.ptr_; p < e; ++p)
        {
            (*p) = -(*p);
        }
        return result;
    }
    
    Matrix<bool> operator!() const
    {
        Matrix<bool> result(NoInit, nrows_, ncols_);
        size_type i;
        bool *p1, *a, *b;
        const_iterator p2; 
        for (i = 0; i < nrows_; ++i)
        {
            a = result.ptr_ + i * ncols_;
            b = a + ncols_;
            p2 = row_begin(i);
            for (p1 = a; p1 < b; ++p1, ++p2)
            {
                (*p1) = !(*p2);
            }
        }
        return result;
    }
    
    Matrix<T> operator~() const
    {
        Matrix<T> result = copy();
        size_type i, nm = nrows_ * ncols_;
        pointer p, e = result.ptr_ + nm;
        for (p = result.ptr_; p < e; ++p)
        {
            (*p) = ~(*p);
        }
        return result;
    }
    
    iterator operator[](difference_type i)
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(nrows_) || i < 0 )
            vmerror("Matrix index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return iterator(ptr_ + i * rstride_, cstride_);
    }
    
    const_iterator operator[](difference_type i) const
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(nrows_) || i < 0)
            vmerror("Matrix index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return const_iterator(ptr_ + i * rstride_, cstride_);
    }
    
    reference operator()(difference_type i, difference_type j)
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(nrows_) || j >= difference_type(ncols_) ||
            i < 0 || j < 0) vmerror("Matrix index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return *(ptr_ + i * rstride_ + j * cstride_);
    }
    
    const_reference operator()(difference_type i, difference_type j) const
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(nrows_) || j >= difference_type(ncols_) ||
            i < 0 || j < 0) vmerror("Matrix index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return *(ptr_ + i * rstride_ + j * cstride_);
    }
    

/******************************************************************************
 *  Member functions.
 *
 *****************************************************************************/

    Matrix<T>& apply(void (*fn)(reference x))
    {
        size_type i;
        for (i = 0; i < nrows_; ++i)
            std::for_each(row_begin(i), row_end(i), fn);
        return *this;
    }

    Matrix<T>& apply(value_type (*fn)(value_type x))
    {
        return transform(fn);
    }
    
    Matrix<T>& apply(value_type (*fn)(const_reference x))
    {
        return transform(fn);
    }
    
    bool is_deep() const
    {
        if (!refs_) return false;
        if (*refs_ == 1 && rstride_ == ncols_ && cstride_ == 1 &&
            data_size_ == (nrows_ * ncols_)) return true;
        return false;
    }

    bool is_owner() const
    {
        return (refs_);
    }

    bool empty() const
    {
        return nrows_ == 0;
    }
    
    void make_deep()
    {
        if (!is_deep() && nrows_) ref(copy());
    }
    
    void clear()
    {
        if (refs_)
        {
            if (*refs_ == 1)
            {
                allocator_type alloc;
                typename Traits<value_type>::is_simple is_simple;
                simple_dtor_(is_simple, alloc);
                alloc.deallocate(data_, data_size_);
                delete refs_;
            }
            else --(*refs_);
        }
        data_      = 0;
        refs_      = 0;
        nrows_     = 0;
        ncols_     = 0;
        rstride_   = 0;
        cstride_   = 1;
        ptr_       = 0;
        data_size_ = 0;
    }

    void free() {clear();}
    
    void reshape(size_type n, size_type m)
    {
        if (n && m)
        {
            Matrix<T> tmp(NoInit, n, m);
            ref(tmp);
        }
        else if (nrows_) clear();
    }
    
    
    void resize(size_type n, size_type m)
    {
        if (n && m)
        {
            Matrix<T> temp(NoInit, n, m);
            size_type i;
            size_type mn = std::min(nrows_, n);
            size_type mm = std::min(ncols_, m);
            value_type zero = Traits<value_type>::zero();
            for (i = 0; i < mn; ++i)
            {
                std::copy(row_begin(i), row_begin(i) + mm, temp.row_begin(i));
                if (m > mm)
                    std::fill(temp.row_begin(i) + mm, temp.row_end(i), zero);
            }
            if (n > mn)
                std::fill(temp.ptr_ + mn * m, temp.ptr_ + temp.data_size_, zero);
            ref(temp);
        }
        else if (nrows_) clear();
    }
    
    Matrix<T> copy() const
    {
        Matrix<T> tmp(NoInit, nrows_, ncols_);
        return tmp = *this;
    }
    
    void ref(const Matrix<T>& rhs)
    {
        if (this != &rhs)
        {
            if (nrows_) clear();
            data_      = rhs.data_;
            refs_      = rhs.refs_;
            nrows_     = rhs.nrows_;
            ncols_     = rhs.ncols_;
            rstride_   = rhs.rstride_;
            cstride_   = rhs.cstride_;
            ptr_       = rhs.ptr_;
            data_size_ = rhs.data_size_;
            if (refs_) ++(*refs_);
        }
    }

    void view(const Matrix<T>& rhs)
    {
        if (this != &rhs)
        {
            if (nrows_) clear();
            data_      = 0;
            refs_      = 0;
            nrows_     = rhs.nrows_;
            ncols_     = rhs.ncols_;
            rstride_   = rhs.rstride_;
            cstride_   = rhs.cstride_;
            ptr_       = rhs.ptr_;
            data_size_ = 0;
        }
    }

    void view(pointer p, difference_type n, difference_type m,
        difference_type rs = 0, difference_type cs = 1)
    {
        if (n < 1 || m < 1)
            vmerror("Error in Matrix::view().");
        if (nrows_) clear();
        if (!rs) rs = m * cs;
        data_      = 0;
        refs_      = 0;
        nrows_     = n;
        ncols_     = m;
        rstride_   = rs;
        cstride_   = cs;
        ptr_       = p;
        data_size_ = 0;
    }

    Matrix<T> submatrix(difference_type rb, difference_type cb, difference_type n,
        difference_type m, difference_type rs = 1, difference_type cs = 1) const
    {
        if (rb >= difference_type(nrows_) || rb < 0 ||
            cb >= difference_type(ncols_) || cb < 0 ||
            rb + (n - 1) * rs < 0 ||
            rb + (n - 1) * rs >= difference_type(nrows_) ||
            cb + (m - 1) * cs < 0 ||
            cb + (m - 1) * cs >= difference_type(ncols_))
            vmerror("Index out of bounds error in Matrix::submatrix().");
        Matrix<T> tmp;
        tmp.ref(*this);
        tmp.nrows_   = n;
        tmp.ncols_   = m;
        tmp.rstride_ = rstride_ * rs;
        tmp.cstride_ = cstride_ * cs;
        tmp.ptr_     = ptr_ + rstride_ * rb + cstride_ * cb;
        return tmp;
    }
    
    Matrix<T> transpose() const
    {
        Matrix<T> tmp = *this;
        tmp.nrows_   = ncols_;
        tmp.ncols_   = nrows_;
        tmp.rstride_ = cstride_;
        tmp.cstride_ = rstride_;
        return tmp;
    }
    
    Vector<T> diag() const
    {
        Vector<T> tmp;
        tmp.data_      = data_;
        tmp.refs_      = refs_;
        tmp.size_      = (nrows_ < ncols_) ? nrows_ : ncols_;
        tmp.ptr_       = ptr_;
        tmp.stride_    = rstride_ + cstride_;
        tmp.data_size_ = data_size_;
        if (refs_) ++(*refs_);
        return tmp;
    }    
        
    Matrix<real_type> real() const
    {
        typename Traits<T>::is_complex is_complex;
        Matrix<real_type> tmp;
        real_view_(tmp, is_complex);
        return tmp;
    }
    
    Matrix<real_type> imag() const
    {
        typename Traits<T>::is_complex is_complex;
        Vector<real_type> tmp;
        image_view_(tmp, is_complex);
        return tmp;
    }
    
    void swap_views(Matrix<T>& a)
    {
        std::swap(nrows_, a.nrows_);
        std::swap(ncols_, a.ncols_);
        std::swap(rstride_, a.rstride_);
        std::swap(cstride_, a.cstride_);
        std::swap(ptr_, a.ptr_);
        std::swap(data_, a.data_);
        std::swap(refs_, a.refs_);
        std::swap(data_size_, a.data_size);
    }
    
    void swap(Matrix<T>& a)
    {
        if (nrows_ != a.nrows_ || ncols_ != a.ncols_)
             vmerror("Size mismatch in matrix swap() function.");
        for (size_t i = 0; i < nrows_; ++i)
             std::swap_ranges(row_begin(i), row_end(i), a.row_begin(i));
    }
     
    size_type nrows() const
    {
        return nrows_;
    }
    
    size_type ncols() const
    {
        return ncols_;
    }
    
    pointer data()
    {
        return ptr_;
    }
    
    const_pointer data() const
    {
        return ptr_;
    }
    
    difference_type row_stride() const
    {
        return rstride_;
    }
    
    difference_type col_stride() const
    {
        return cstride_;
    }
    
    Vector<T> row(difference_type i) const
    {
        if (i >= difference_type(nrows_) || i < 0)
            vmerror("Index out of bounds error in Matrix::row().");
        Vector<T> tmp;
        tmp.data_      = data_;
        tmp.refs_      = refs_;
        tmp.size_      = ncols_;
        tmp.stride_    = cstride_;
        tmp.ptr_       = ptr_ + i * rstride_;
        tmp.data_size_ = data_size_;
        if (refs_) ++(*refs_);
        return tmp;
    }
    
    Vector<T> column(difference_type j) const
    {
        if (j >= difference_type(ncols_) || j < 0)
             vmerror("Index out of bounds error in Matrix::column().");
        Vector<T> tmp;
        tmp.data_      = data_;
        tmp.refs_      = refs_;
        tmp.size_      = nrows_;
        tmp.stride_    = rstride_;
        tmp.ptr_       = ptr_ + j * cstride_;
        tmp.data_size_ = data_size_;
        if (refs_) ++(*refs_);
        return tmp;
    }
    
    Vector<T> unwrap() const
    {
        int flag = 0;
        if (rstride_ == cstride_ * difference_type(ncols_)) flag = 1;
        else if (cstride_ == rstride_ * difference_type(nrows_)) flag = 2;
        else vmerror("Size mismatch error in Matrix::unwrap().");
        Vector<T> tmp;
        tmp.data_      = data_;
        tmp.refs_      = refs_;
        tmp.size_      = nrows_ * ncols_;
        tmp.stride_    = (flag == 1) ? cstride_ : rstride_;
        tmp.ptr_       = ptr_;
        tmp.data_size_ = data_size_;
        if (refs_) ++(*refs_);
        return tmp;
    }
    
    iterator row_begin(difference_type n)
    {
        if (n >= difference_type(nrows_) || n < 0)
            vmerror("Index out of bounds error in Matrix::row_begin().");
        return iterator(ptr_ + n * rstride_, cstride_);
    }
    
    const_iterator row_begin(difference_type n) const
    {
        if (n >= difference_type(nrows_) || n < 0)
            vmerror("Index out of bounds error in Matrix::row_begin().");
        return const_iterator(ptr_ + n * rstride_, cstride_);
    }
    
    iterator row_end(difference_type n)
    {
        if (n >= difference_type(nrows_) || n < 0)
            vmerror("Index out of bounds error in Matrix::row_end().");
        return iterator(ptr_ + n * rstride_ + ncols_ * cstride_, cstride_);
    }
    
    const_iterator row_end(difference_type n) const
    {
        if (n >= difference_type(nrows_) || n < 0)
            vmerror("Index out of bounds error in Matrix::row_end().");
        return const_iterator(ptr_ + n * rstride_ + ncols_ * cstride_, cstride_);
    }
    
    iterator col_begin(difference_type n)
    {
        if (n >= difference_type(ncols_) || n < 0)
            vmerror("Index out of bounds error in Matrix::col_begin().");
        return iterator(ptr_ + n * cstride_, rstride_);
    }
    
    const_iterator col_begin(difference_type n) const
    {
        if (n >= difference_type(ncols_) || n < 0)
            vmerror("Index out of bounds error in Matrix::col_begin().");
        return const_iterator(ptr_ + n * cstride_, rstride_);
    }
    
    iterator col_end(difference_type n)
    {
        if (n >= difference_type(ncols_) || n < 0)
            vmerror("Index out of bounds error in Matrix::col_end().");
        return iterator(ptr_ + n * cstride_ + nrows_ * rstride_, rstride_);
    }
    
    const_iterator col_end(difference_type n) const
    {
        if (n >= difference_type(ncols_) || n < 0)
            vmerror("Index out of bounds error in Matrix::col_end().");
        return const_iterator(ptr_ + n * cstride_ + nrows_ * rstride_, rstride_);
    }
    
    void write(std::ostream& out, File_Mode fm = TXT) const
    {
        difference_type i;
        if (fm)
        {
            if (rstride_ == difference_type(ncols_) && rstride_ > 0 &&
                cstride_ == 1)
                out.write(reinterpret_cast<const char *>(ptr_), nrows_ *
                    ncols_ * sizeof(T));
            else
            {
                const_pointer p, a, b;
                for (i = 0; i < difference_type(nrows_); ++i)
                {
                    a = ptr_ + i * rstride_;
                    b = a + difference_type(ncols_) * cstride_;
                    for (p = a; p < b; p += cstride_)
                        out.write(reinterpret_cast<const char *>(p), sizeof(T));
                }
            }
        }
        else
        {
            const_iterator p, a, b;
            for (i = 0; i < difference_type(nrows_); ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                for (p = a; p < b; ++p)
                {
                    out << (*p);
                    if (p == b - 1)
                    {
                        if (i < difference_type(nrows_ - 1)) out << '\n';
                    }
                    else out << '\t';
                }
            }
        }
    }
    
    void write(const std::string& fname, File_Mode fm = TXT) const
    {
        std::ofstream file;
        if (fm) file.open(fname.c_str(), std::ios::out | std::ios::binary);
        else file.open(fname.c_str());
        write(file, fm);
    }            
    
    ptrdiff_t read(std::istream& in, File_Mode fm = TXT)
    {
        ptrdiff_t k = 0;
        difference_type i;
        if (fm)
        {
            pointer p, a, b;
            for (i = 0; i < difference_type(nrows_); ++i)
            {
                a = ptr_ + i * rstride_;
                b = a + cstride_ * difference_type(ncols_);
                for (p = a; p < b; p += cstride_)
                {
                    if (in.read(reinterpret_cast<char*>(p), sizeof(T))) ++k;
                    else
                    {
                        i = nrows_;
                        p = b;
                    }
                }          
            }       
        }
        else
        {
            iterator p, a, b;
            value_type tmp;
            for (i = 0; i < difference_type(nrows_); ++i)
            {
                a = row_begin(i);
                b = row_end(i);
                for (p = a; p < b; ++p)
                {
                    if (in >> tmp)
                    {
                        *(p) = tmp;
                        ++k;
                    }
                    else
                    {
                        i = nrows_;
                        p = b;
                    }
                }
            }
        }
        return k;
    }
    
    ptrdiff_t read(const std::string& fname, File_Mode fm = TXT)
    {
        offset_type result;
        std::ifstream file;
        if (fm)
        {
            result = file_status<value_type>(fname.c_str(), 0);
            if (result <= 0) return result;
            file.open(fname.c_str(), std::ios::in | std::ios::binary);
        }
        else
        {
            result = file_status<char>(fname.c_str(), 0);
            if (result <= 0) return result; 
            file.open(fname.c_str());
        }
        result = read(file, fm);
        file.close();
        return result;
    }              


    template <class G>
    Matrix& fill(G gen)
    {
        size_type i;
        for (i = 0; i < nrows_; ++i)
            std::generate(row_begin(i), row_end(i), gen);
        return *this;
    }

    template <class G>
    Matrix& transform(G fn)
    {
        size_type i;
        for (i = 0; i < nrows_; ++i)
            std::transform(row_begin(i), row_end(i), row_begin(i), fn);
        return *this;
    }

    template <class U>
    Matrix<U> convert() const
    {
        Matrix<U> tmp(NoInit, nrows_, ncols_);
        size_type i;
        const_iterator p2, a, b;
        U *p1 = tmp.data();
        for (i = 0; i < nrows_; ++i)
        {
            a = row_begin(i);
            b = row_end(i);
            for (p2 = a; p2 < b; ++p2, ++p1)
                (*p1) = static_cast<U>(*p2);
        }
        return tmp;
    }

    template <class U>
    Matrix<U> shallow_cast() const
    {
        if (sizeof(U) != sizeof(value_type))
            vmerror("Size mismatch error in Matrix::shallow_cast().");
        Matrix<U> tmp;
        tmp.view(reinterpret_cast<U*>(ptr_), nrows_, ncols_, rstride_, cstride_);
        return tmp;
    }
};

template <class T>
inline Matrix<T>& Mat(const Matrix<T>& m) {return const_cast<Matrix<T>&>(m);}

} /* namespace VM */


#endif /* MATRIX_H */
