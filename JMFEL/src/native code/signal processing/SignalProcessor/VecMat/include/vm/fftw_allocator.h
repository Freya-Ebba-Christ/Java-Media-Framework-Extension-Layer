#ifndef FFTW_ALLOCATOR_H
#define FFTW_ALLOCATOR_H

/******************************************************************************
 *
 *  FFTW Allocator, version 1.1
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
 *  fftw_allocator.h - STL style allocator for use with FFTW 3.x software.
 *
 *****************************************************************************/


#include <fftw3.h>
#include <new>
#include <limits>

/******************************************************************************
 *
 *  Allocator class for wrapping the fftw_malloc() and fftw_free() functions.
 *
 *****************************************************************************/

template <class T>
class fftw_allocator
{
public:
    typedef size_t     size_type;
    typedef ptrdiff_t  difference_type;
    typedef T          value_type;
    typedef T*         pointer;
    typedef const T*   const_pointer;
    typedef T&         reference;
    typedef const T&   const_reference;

    template <class U>
    struct rebind
    {
        typedef fftw_allocator<U> other;
    };

    pointer address(reference x) const
    {
        return &x;
    }

    const_pointer address(const_reference x) const
    {
        return &x;
    }
    pointer allocate(size_type n, const void * = 0)
    {
        return static_cast<pointer>(fftw_malloc(sizeof(value_type) * n));
    }
    
    void *raw_alloc16(size_type n)
    {
        return fftw_malloc(n);
    }
    
    void deallocate(void *p, size_type)
    {
        if (p) fftw_free(p);
    }
    
    void construct(pointer p, const_reference v)
    {
        new (p) value_type(v);
    }
    
    void destroy(pointer p)
    {
        p->~T();
    }
    
    size_type max_size() const
    {
        return std::numeric_limits<size_type>::max() / sizeof(value_type);
    }
};


/******************************************************************************
 *
 *  Binary relational operators for fftw_allocator.
 *
 *****************************************************************************/

template<class T, class U>
inline bool operator==(const fftw_allocator<T>&, const fftw_allocator<U>&)
{
    return true;
}

template<class T, class U>
inline bool operator!=(const fftw_allocator<T>&, const fftw_allocator<U>&)
{
    return false;
}


/******************************************************************************
 *
 *  Specialization for void.
 *
 *****************************************************************************/

template<>
class fftw_allocator<void>
{
public:
    typedef size_t       size_type;
    typedef ptrdiff_t    difference_type;
    typedef void         value_type;
    typedef void*        pointer;
    typedef const void*  const_pointer;
};


#endif /* FFTW_ALLOCATOR_H */
