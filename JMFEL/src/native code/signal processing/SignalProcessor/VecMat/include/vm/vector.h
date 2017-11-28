#ifndef VECTOR_H
#define VECTOR_H

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
 *  vector.h - Vector class declaration and definition.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

template <class T> class Matrix;


/******************************************************************************
 *
 *  Vector class.
 *
 *****************************************************************************/

template <class T>
class Vector
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

    class iterator;
    class const_iterator;
    
    typedef iterator        reverse_iterator;
    typedef const_iterator  const_reverse_iterator;

    friend class Matrix<T>;

private:
    size_type        size_;
    difference_type  stride_;
    pointer          ptr_;
    pointer          data_;
    size_type        *refs_;
    size_type        data_size_;


    void simple_ctor_(const true_type&) {;}
    
    void simple_ctor_(const false_type&)
    {
        value_type zero = Traits<value_type>::zero();
        std::uninitialized_fill_n(data_, size_, zero);
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
    
    template <class InIt>
    void two_arg_ctor_(InIt a, InIt b, const false_type&)
    {
        ptrdiff_t d = (b - a);
        if (d > 0)
        {
            size_ = d;
            data_size_ = size_;
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            std::uninitialized_copy(a, b, data_);
            ptr_  = data_;
            refs_ = new size_type(1);
        }
    }
    
    void two_arg_ctor_(const_reference a, size_type n, const true_type&)
    {
        size_ = n;
        data_size_ = size_;
        if (size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            std::uninitialized_fill_n(data_, size_, a);
            ptr_  = data_;
            refs_ = new size_type(1);
        }
    }
    
    void real_view_(Vector<real_type>& v, const true_type&) const
    {
        v.view(reinterpret_cast<real_type *>(ptr_), size_, stride_ * 2);
    }
    
    void real_view_(Vector<real_type>& v, const false_type&) const
    {
        v.view(*this);
    }
    
    void image_view_(Vector<real_type>& v, const true_type&) const
    {
        v.view(reinterpret_cast<real_type *>(ptr_) + 1, size_, stride_ * 2);
    }
    
    void image_view_(Vector<real_type>& v, const false_type&) const
    {
        vmerror("Cannot make an imaginary view of a real vector");
    }

            
public:

/******************************************************************************
 *
 *  Constructors and destructor.
 *
 *****************************************************************************/

    Vector() : size_(0), stride_(1), ptr_(0), data_(0), refs_(0), data_size_(0) {;}

    explicit Vector(size_type n) : size_(n), stride_(1), ptr_(0), data_(0),
        refs_(0), data_size_(n)
    {
        if (size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            value_type zero = Traits<value_type>::zero();
            std::uninitialized_fill_n(data_, size_, zero);
            ptr_ = data_; 
            refs_ = new size_type(1);
        }
    }

    Vector(NoInit_, size_type n) : size_(n), stride_(1), ptr_(0),
        data_(0), refs_(0), data_size_(n)
    {
        if (size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            ptr_ = data_;
            refs_ = new size_type(1);
            typename Traits<value_type>::is_simple is_simple;
            simple_ctor_(is_simple);
        }
    }

    Vector(const_reference a, size_type n) : size_(n), stride_(1), ptr_(0),
        data_(0), refs_(0), data_size_(n)
    {
        if (size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            std::uninitialized_fill_n(data_, size_, a);
            ptr_  = data_;
            refs_ = new size_type(1);
        }
    }

    Vector(pointer a, size_type n, difference_type s = 1) : size_(n), stride_(s),
         ptr_(a), data_(0), refs_(0), data_size_(0)
    {
        if (!n)
        {
            ptr_ = 0;
            stride_ = 1;
        }    
    }    

    template <class InIt>
    Vector(InIt a, InIt b) : size_(0), stride_(1), ptr_(0), data_(0),
        refs_(0), data_size_(0)
    {
        typename Traits<T>::is_int is_int;
        two_arg_ctor_(a, b, is_int);
    }    


    Vector(const Vector<T>& rhs) : size_(rhs.size_), stride_(rhs.stride_),
        ptr_(rhs.ptr_), data_(rhs.data_), refs_(rhs.refs_),
        data_size_(rhs.data_size_)
    {
        if (refs_) ++(*refs_);
    }    

    Vector(const_reference strt, const_reference step, size_type n) :
        size_(n), stride_(1), ptr_(0), data_(0), refs_(0), data_size_(n)
    {
        if (size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            ptr_  = data_;
            refs_ = new size_type(1);
            value_type a = strt;
            pointer p, e = ptr_ + size_;
            for (p = ptr_; p < e; ++p, a += step)
                alloc.construct(p, a);
        }
    }

    Vector(const Vector<real_type>& re, const Vector<real_type>& im) :
        size_(re.size()), stride_(1), ptr_(0), data_(0), refs_(0),
        data_size_(re.size())
    {
        if (im.size() != size_)
            vmerror("Size mismatch error in complex Vector constructor.");
        if (size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            ptr_  = data_;
            refs_ = new size_type(1);
            pointer p1, e = ptr_ + size_;
            typename Vector<real_type>::const_iterator p2 = re.begin(),
                p3 = im.begin();
            for (p1 = ptr_; p1 < e; ++p1, ++p2, ++p3)
                alloc.construct(p1, value_type(*p2, *p3));
        }
    }

    Vector(const Vector<real_type>& re, const real_type& im) : size_(re.size()),
        stride_(1), ptr_(0), data_(0), refs_(0), data_size_(re.size())
    {
        if (size_)
        {
            allocator_type alloc;
            data_ = alloc.allocate(size_, 0);
            if (!data_)
                vmerror("Memory allocation error in Vector constructor.");
            ptr_  = data_;
            refs_ = new size_type(1);
            pointer p1, e = ptr_ + size_;
            typename Vector<real_type>::const_iterator p2 = re.begin();
            for (p1 = ptr_; p1 < e; ++p1, ++p2)
                alloc.construct(p1, value_type(*p2, im));
        }
    }

    ~Vector()
    {
        if (size_) clear();
    }


/******************************************************************************
 *
 *  Member Operators.
 *
 *****************************************************************************/

    Vector<T>& operator=(const Vector<T>& rhs)
    {
        if (size_ == rhs.size_)
        {
            if (this == &rhs) return *this;
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) = (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator+=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) += (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator-=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) -= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator*=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) *= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator/=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) /= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator%=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) %= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator&=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) &= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator^=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) ^= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator|=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) |= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator>>=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) >>= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator<<=(const Vector<T>& rhs)
    {
        size_type n = rhs.size_;
        if (n == size_)
        {
            iterator p1, e = end();
            const_iterator p2 = rhs.begin();
            for (p1 = begin(); p1 < e; ++p1, ++p2)
            {
                (*p1) <<= (*p2);
            }
        }
        else vmerror("Size mismatch error in Vector assignment operator.");
        return *this;
    }

    Vector<T>& operator=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) = a;
        }
        return *this;
    }
    
    Vector<T>& operator+=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) += a;
        }
        return *this;
    }
    
    Vector<T>& operator-=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) -= a;
        }
        return *this;
    }
    
    Vector<T>& operator*=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) *= a;
        }
        return *this;
    }
    
    Vector<T>& operator/=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) /= a;
        }
        return *this;
    }
    
    Vector<T>& operator%=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) %= a;
        }
        return *this;
    }
    
    Vector<T>& operator&=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) &= a;
        }
        return *this;
    }
    
    Vector<T>& operator|=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) |= a;
        }
        return *this;
    }
    
    Vector<T>& operator>>=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) >>= a;
        }
        return *this;
    }
    
    Vector<T>& operator<<=(const_reference a)
    {
        iterator p, e = end();
        for (p = begin(); p < e; ++p)
        {
            (*p) <<= a;
        }
        return *this;
    }
    
    Vector<T> operator+() const
    {
        Vector<T> result = copy();
        pointer p, e = result.ptr_ + size_;
        for (p = result.ptr_; p < e; ++p)
        {
            (*p) = +(*p);
        }
        return result;
    }
    
    Vector<T> operator-() const
    {
        Vector<T> result = copy();
        pointer p, e = result.ptr_ + size_;
        for (p = result.ptr_; p < e; ++p)
        {
            (*p) = -(*p);
        }
        return result;
    }
    
    Vector<bool> operator!() const
    {
        Vector<bool> result(NoInit, size_);
        bool *p1, *e = result.data() + size_;
        const_iterator p2 = begin();
        for (p1 = result.data(); p1 < e; ++p1, ++p2)
        {
            (*p1) = !(*p2);
        }
        return result;
    }
    
    Vector<T> operator~() const
    {
        Vector<T> result = copy();
        pointer p, e = result.ptr_ + size_;
        for (p = result.ptr_; p < e; ++p)
        {
            (*p) = ~(*p);
        }
        return result;
    }
    
    reference operator[](difference_type i)
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(size_) || i < 0)
            vmerror("Vector index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return *(ptr_ + i * stride_);
    }
    
    const_reference operator[](difference_type i) const
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(size_) || i < 0)
            vmerror("Vector index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return *(ptr_ + i * stride_);
    }

    reference operator()(difference_type i)
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(size_) || i < 0)
            vmerror("Vector index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return *(ptr_ + i * stride_);
    }
    
    const_reference operator()(difference_type i) const
    {
#ifdef VM_BOUNDS_CHECK
        if (i >= difference_type(size_) || i < 0)
            vmerror("Vector index out of bounds error.");
#endif /* VM_BOUNDS_CHECK */
        return *(ptr_ + i * stride_);
    }


/******************************************************************************
 *
 *  Member Functions.
 *
 *****************************************************************************/

    Vector<T>& apply(void (*fn)(reference x))
    {
        std::for_each(begin(), end(), fn);
        return *this;
    }

    Vector<T>& apply(value_type (*fn)(value_type x))
    {
        return transform(fn);
    }
    
    Vector<T>& apply(value_type (*fn)(const_reference x))
    {
        return transform(fn);
    }
    
    bool is_owner() const
    {
        return (refs_);
    }
    
    bool is_deep() const
    {
        if (!refs_) return false;
        if (*refs_ == 1 && stride_ == 1 && data_size_ == size_) return true;
        return false;
    }

    bool empty() const
    {
        return size_ == 0;
    }    
    
    void make_deep()
    {
        if (!is_deep() && size_) ref(copy());
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
        size_      = 0;
        stride_    = 1;
        ptr_       = 0;
        data_size_ = 0;
    }
    
    void free() {clear();}
    
    void reshape(size_type n)
    {
        if (n)
        {
            Vector<T> tmp(NoInit, n);
            ref(tmp);
        }
        else if (size_) clear();
    }

    void resize(size_type n)
    {
        if (n)
        {
            Vector<T> temp(NoInit, n);
            size_type mn = std::min(size_, n);
            value_type zero = Traits<value_type>::zero();
            std::copy(begin(), begin() + mn, temp.begin());
            if (n > mn) std::fill(temp.begin() + mn, temp.begin() + n, zero);
            ref(temp);
        }
        else if (size_) clear();
    }
    
    Vector<T> copy() const
    {
        Vector<T> tmp(NoInit, size_);
        return tmp = *this;
    }
    
    void ref(const Vector<T>& rhs)
    {
        if (this != &rhs)
        {
            if (size_) clear();
            data_      = rhs.data_;
            refs_      = rhs.refs_;
            size_      = rhs.size_;
            stride_    = rhs.stride_;
            ptr_       = rhs.ptr_;
            data_size_ = rhs.data_size_;
            if (refs_) ++(*refs_);
        }
    }

    void view(const Vector<T>& rhs)
    {
        if (this != &rhs)
        {
            if (size_) clear();
            data_      = 0;
            refs_      = 0;
            size_      = rhs.size_;
            stride_    = rhs.stride_;
            ptr_       = rhs.ptr_;
            data_size_ = 0;
        }
    }

    void view(pointer p, difference_type n, difference_type s = 1)
    {
        if (size_) clear();
        if (n < 1) vmerror("Error in Vector::view().");
        data_      = 0;
        refs_      = 0;
        size_      = n;
        stride_    = s;
        ptr_       = p;
        data_size_ = 0;
    }

    Vector<T> slice(difference_type b, difference_type n, difference_type s = 1) const
    {
        if (b >= difference_type(size_) || b < 0 || n < 1 ||
            b + (n - 1) * s < 0 ||
            b + (n - 1) * s >= difference_type(size_))
            vmerror("Index out of bounds error in Vector::slice().");
        Vector<T> tmp = *this;
        tmp.size_   = n;
        tmp.stride_ = s * stride_;
        tmp.ptr_    = ptr_ + b * stride_;
        return tmp;
    }
    
    Vector<real_type> real() const
    {
        typename Traits<T>::is_complex is_complex;
        Vector<real_type> tmp;
        real_view_(tmp, is_complex);
        return tmp;
    }
    
    Vector<real_type> imag() const
    {
        typename Traits<T>::is_complex is_complex;
        Vector<real_type> tmp;
        image_view_(tmp, is_complex);
        return tmp;
    }
    
    Vector<T> rotate(difference_type n) const
    {
        if (!size_) vmerror("Rotation of zero size vector not allowed.");
        if (std::abs(n) >= difference_type(size_))
            vmerror("Index out of bounds in Vector::rotate() function.");
        Vector<T> tmp(size_);
        const_iterator m;
        if (n >= 0) m = begin() + n;
        else        m = end() + n;
        std::rotate_copy(begin(), m, end(), tmp.begin());
        return tmp;
    }
    
    Vector<T> delta() const
    {
        if (!size_) vmerror("Delta of zero size vector not allowed.");
        Vector<T> tmp(size_);
        std::adjacent_difference(begin(), end(), tmp.begin());
        return tmp;
    }
    
    Vector<T> cumsum() const
    {
        if (!size_) vmerror("Cumsum of zero size vector not allowed.");
        Vector<T> tmp(size_);
        std::partial_sum(begin(), end(), tmp.begin());
        return tmp;
    }
    
    void swap_views(Vector<T>& a)
    {
        std::swap(size_, a.size_);
        std::swap(stride_, a.stride_);
        std::swap(ptr_, a.ptr_);
        std::swap(data_, a.data_);
        std::swap(refs_, a.refs_);
        std::swap(data_size_, a.data_size_);
    }
    
    void swap(Vector<T>& a)
    {
        if (size_ != a.size_) vmerror("Size mismatch in vector swap() function.");
        std::swap_ranges(begin(), end(), a.begin());
    }
     
    size_type size() const
    {
        return size_;
    }
    
    size_type max_size() const
    {
        return std::numeric_limits<size_type>::max() / sizeof(value_type);
    }
        
    pointer data()
    {
        return ptr_;
    }
    
    const_pointer data() const
    {
        return ptr_;
    }
    
    difference_type stride() const
    {
        return stride_;
    }
    
    Matrix<T> matrix(difference_type b, difference_type n, difference_type m,
        difference_type rs = 0, difference_type cs = 1) const
    {
        if (!rs) rs = m * cs;
        if (b >= difference_type(size_) || b < 0 || n < 1 || m < 1 ||
            b + (n - 1) * rs < 0 ||
            b + (n - 1) * rs >= difference_type(size_) ||
            b + (m - 1) * cs < 0 ||
            b + (m - 1) * cs >= difference_type(size_) ||
            b + (n - 1) * rs + (m - 1) * cs < 0 ||
            b + (n - 1) * rs + (m - 1) * cs >= difference_type(size_))
            vmerror("Index out of bounds error in Vector::matrix().");
        Matrix<T> tmp;
        tmp.data_      = data_;
        tmp.refs_      = refs_;
        tmp.ptr_       = ptr_ + b * stride_;
        tmp.nrows_     = n;
        tmp.ncols_     = m;
        tmp.rstride_   = stride_ * rs;
        tmp.cstride_   = stride_ * cs;
        tmp.data_size_ = data_size_;
        if (refs_) ++(*refs_);
        return tmp;
    }
    
    Matrix<T> row_matrix() const
    {
        Matrix<T> tmp;
        tmp.data_      = data_;
        tmp.refs_      = refs_;
        tmp.ptr_       = ptr_;
        tmp.nrows_     = 1;
        tmp.ncols_     = size_;
        tmp.rstride_   = difference_type(size_) * stride_;
        tmp.cstride_   = stride_;
        tmp.data_size_ = data_size_;
        if (refs_) ++(*refs_);
        return tmp;
    }
    
    Matrix<T> col_matrix() const
    {
        Matrix<T> tmp;
        tmp.data_      = data_;
        tmp.refs_      = refs_;
        tmp.ptr_       = ptr_;
        tmp.nrows_     = size_;
        tmp.ncols_     = 1;
        tmp.rstride_   = stride_;
        tmp.cstride_   = difference_type(size_) * stride_;
        tmp.data_size_ = data_size_;
        if (refs_) ++(*refs_);
        return tmp;
    }
    
    void sort()
    {
        if (!size_) return;
        if (stride_ == 1) std::sort(ptr_, ptr_ + size_);
        else std::sort(begin(), end());
    }

    template <class LT>
    void sort(LT lt)
    {
        if (!size_) return;
        if (stride_ == 1) std::sort(ptr_, ptr_ + size_, lt);
        else std::sort(begin(), end(), lt);
    }

    void swap(size_type i, size_type j)
    {
        if (i >= size_ || j >= size_)
            vmerror("Index out of bounds error in Vector::swap().");
        std::swap(*(ptr_ + difference_type(i) * stride_),
            *(ptr_ + difference_type(j) * stride_));
    }

    value_type sum() const
    {
        if (!size_) vmerror("Sum of zero size Vector not allowed.");
        value_type tmp = *ptr_;
        return std::accumulate(begin() + 1, end(), tmp);
    }
    
    value_type min() const
    {
        if (!size_) vmerror("Minimum of zero size Vector not allowed.");
        return *(std::min_element(begin(), end()));
    }
    
    value_type max() const
    {
        if (!size_) vmerror("Maximum of zero size Vector not allowed.");
        return *(std::max_element(begin(), end()));
    }
    
    iterator begin()
    {
        return iterator(ptr_, stride_);
    }
    
    const_iterator begin() const
    {
        return const_iterator(ptr_, stride_);
    }
    
    iterator end()
    {
        return iterator(ptr_ + difference_type(size_) * stride_, stride_);
    }
    
    const_iterator end() const
    {
        return const_iterator(ptr_ + difference_type(size_) * stride_, stride_);
    }

    reverse_iterator rbegin()
    {
        return iterator(ptr_ + difference_type(size_ - 1) * stride_, -stride_);
    }
    
    const_reverse_iterator rbegin() const
    {
        return const_iterator(ptr_ + difference_type(size_ - 1) * stride_, -stride_);
    }
    
    reverse_iterator rend()
    {
        return iterator(ptr_ - stride_, -stride_);
    }
    
    const_reverse_iterator rend() const
    {
        return reverse_iterator(ptr_ - stride_, -stride_);
    }


    void write(std::ostream& out, File_Mode fm = TXT) const
    {
        if (fm)
        {
            if (stride_ == 1) out.write(reinterpret_cast<const char *>(ptr_),
				std::streamsize(size_ * sizeof(T)));
            else
            {
                const_pointer p = ptr_, e = ptr_ + difference_type(size_) * stride_;
                for (; p < e; p += stride_)
                    out.write(reinterpret_cast<const char *>(p), sizeof(T));
            }
        }
        else
        {
            const_iterator p = begin();
            size_type i;
            for (i = 0; i < size_; ++i, ++p)
            {
                out << *p;
                if (i < size_ - 1) out << '\n';
            }
        }
    }
    
    void write(const std::string& fname, File_Mode fm = TXT) const
    {
        std::ofstream file;
        if (fm) file.open(fname.c_str(), std::ios::out | std::ios::binary);
        else file.open(fname.c_str());
        write(file, fm);
        file.close();
    }
 
    ptrdiff_t read(std::istream& in, File_Mode fm = TXT)
    {
        iterator p = begin();
        size_type i;
        if (fm)
        {
            for (i = 0; i < size_; ++i, ++p)
            {
                if (!in.read(reinterpret_cast<char*>(&(*p)), sizeof(T)))
                    i = size_;
            }
        }
        else
        {
            value_type tmp;
            for (i = 0; i < size_; ++i, ++p)
            {
                if (in >> tmp) *p = tmp;
                else i = size_;
            }
        }
        return p - begin();
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
    Vector<T>& fill(G gen)
    {
        std::generate(begin(), end(), gen);
        return *this;
    }

    template <class G>
    Vector& transform(G fn)
    {
        std::transform(begin(), end(), begin(), fn);
        return *this;
    }

    template <class U>
    Vector<U> convert() const
    {
        Vector<U> tmp(NoInit, size_);
        const_iterator p2 = begin();
        U *p1, *e = tmp.data() + size_;
        for (p1 = tmp.data(); p1 < e; ++p1, ++p2)
            (*p1) = static_cast<U>(*p2);
        return tmp;
    }

    template <class U>
    Vector<U> shallow_cast() const
    {
        if (sizeof(U) != sizeof(value_type))
            vmerror("Size mismatch error in Vector::shallow_cast().");
        Vector<U> tmp;
        tmp.view(reinterpret_cast<U*>(ptr_), size_, stride_);
        return tmp;
    }


/******************************************************************************
 *
 *  Iterator Class.
 *
 *****************************************************************************/

    class iterator : public std::iterator<std::random_access_iterator_tag,
        value_type, difference_type>
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
    
    private:
        pointer          ptr_;
        difference_type  stride_;

    public:
        explicit iterator(pointer p = 0, difference_type s = 1) : ptr_(p),
            stride_(s) {;}
        iterator(const iterator& rhs) : ptr_(rhs.ptr_), stride_(rhs.stride_) {;}
        ~iterator() {;}

        operator void *() const
        {
            return ptr_;
        }

        iterator& operator++()
        {
            return (ptr_ += stride_, *this);
        }
        iterator& operator--()
        {
            return (ptr_ -= stride_, *this);
        }
        iterator operator++(int)
        {
            iterator tmp = *this;
            ptr_ += stride_;
            return tmp;
        }
        iterator operator--(int)
        {
            iterator tmp = *this;
            ptr_ -= stride_;
            return tmp;
        }
        iterator& operator+=(difference_type d)
        {
            return (ptr_ += d * stride_, *this);
        }
        iterator& operator-=(difference_type d)
        {
            return (ptr_ -= d * stride_, *this);
        }
        iterator& operator=(const iterator& iter)
        {
            stride_ = iter.stride_;
            ptr_    = iter.ptr_;
            return *this;
        }
        difference_type operator-(const iterator& iter) const
        {
            return (ptr_ - iter.ptr_) / stride_;
        }
        iterator operator+(difference_type n) const
        {
            iterator tmp = *this;
            tmp += n;
            return tmp;
        }
        iterator operator-(difference_type n) const
        {
            iterator tmp = *this;
            tmp -= n;
            return tmp;
        }
        reference operator[](difference_type n) const
        {
            return ptr_[n * stride_];
        }
        reference operator*() const
        {
            return *ptr_;
        }
        pointer operator->() const
        {
            return ptr_;
        }
        bool operator==(const iterator& iter) const
        {
            return (iter.ptr_ == ptr_);
        }
        bool operator!=(const iterator& iter) const
        {
            return (iter.ptr_ != ptr_);
        }
        bool operator<(const iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ < iter.ptr_) : (iter.ptr_ < ptr_);
        }
        bool operator>(const iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ > iter.ptr_) : (iter.ptr_ > ptr_);
        }
        bool operator<=(const iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ <= iter.ptr_) : (iter.ptr_ <= ptr_);
        }
        bool operator>=(const iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ >= iter.ptr_) : (iter.ptr_ >= ptr_);
        }
        friend class const_iterator;
    };

/******************************************************************************
 *
 *  Constant Iterator Class.
 *
 *****************************************************************************/

    class const_iterator : public std::iterator<std::random_access_iterator_tag,
        value_type, difference_type>
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

    private:
        const_pointer    ptr_;
        difference_type  stride_;

    public:
        explicit const_iterator(const_pointer p = 0, difference_type s = 1) :
            ptr_(p), stride_(s) {;}
        const_iterator(const const_iterator& rhs) : ptr_(rhs.ptr_),
            stride_(rhs.stride_) {;}
        const_iterator(const iterator& rhs) : ptr_(rhs.ptr_),
            stride_(rhs.stride_) {;}
        ~const_iterator() {}

        operator const void *() const
        {
            return ptr_;
        }

        const_iterator& operator++()
        {
            return (ptr_ += stride_, *this);
        }
        const_iterator& operator--()
        {
            return (ptr_ -= stride_, *this);
        }
        const_iterator operator++(int)
        {
            const_iterator tmp = *this;
            ptr_ += stride_;
            return tmp;
        }
        const_iterator operator--(int)
        {
            const_iterator tmp = *this;
            ptr_ -= stride_;
            return tmp;
        }
        const_iterator& operator+=(difference_type d)
        {
            return (ptr_ += d * stride_, *this);
        }
        const_iterator& operator-=(difference_type d)
        {
            return (ptr_ -= d * stride_, *this);
        }
        const_iterator& operator=(const const_iterator& iter)
        {
            stride_ = iter.stride_;
            ptr_    = iter.ptr_;
            return *this;
        }
        const_iterator& operator=(const iterator& iter)
        {
            stride_ = iter.stride_;
            ptr_    = iter.ptr_;
            return *this;
        }
        difference_type operator-(const const_iterator& iter) const
        {
            return (ptr_ - iter.ptr_) / stride_;
        }
        const_iterator operator+(difference_type n) const
        {
            const_iterator tmp = *this;
            tmp += n;
            return tmp;
        }
        const_iterator operator-(difference_type n) const
        {
            const_iterator tmp = *this;
            tmp -= n;
            return tmp;
        }
        const_reference operator[](difference_type n) const
        {
            return ptr_[n * stride_];
        }
        const_reference operator*() const
        {
            return *ptr_;
        }
        const_pointer operator->() const
        {
            return ptr_;
        }
        bool operator==(const const_iterator& iter) const
        {
            return (iter.ptr_ == ptr_);
        }
        bool operator!=(const const_iterator& iter) const
        {
            return (iter.ptr_ != ptr_);
        }
        bool operator<(const const_iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ < iter.ptr_) : (iter.ptr_ < ptr_);
        }
        bool operator>(const const_iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ > iter.ptr_) : (iter.ptr_ > ptr_);
        }
        bool operator<=(const const_iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ <= iter.ptr_) : (iter.ptr_ <= ptr_);
        }
        bool operator>=(const const_iterator& iter) const
        {
            return (stride_ > 0) ? (ptr_ >= iter.ptr_) : (iter.ptr_ >= ptr_);
        }
    };
};

template <class T>
inline Vector<T>& Vec(const Vector<T>& v) {return const_cast<Vector<T>&>(v);}

} /* namespace VM */


#endif /* VECTOR_H */
