#ifndef VM_ALLOCATOR_H
#define VM_ALLOCATOR_H

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
 *  vm_allocator.h - STL style allocator which provides 16 byte alignment.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

/******************************************************************************
 *
 *  Allocator class for 16 byte alligned arrays.
 *
 *****************************************************************************/

template <class T>
class Allocator
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
        typedef Allocator<U> other;
    };

    pointer address(reference x) const
    {
        return &x;
    }

    const_pointer address(const_reference x) const
    {
        return &x;
    }

    void *raw_alloc16(size_type n)
    {
        void *p0, *p;
        if (!(p0 = ::operator new(n + 16))) return 0;
        p = reinterpret_cast<void*>((reinterpret_cast<size_type>(p0) + 16) &
            (~(size_type(15))));
        *(reinterpret_cast<void**>(p) - 1) = p0;
        return p;
    }
    
    pointer allocate(size_type n, const void * = 0)
    {
        return reinterpret_cast<pointer>(raw_alloc16(sizeof(value_type) * n));
    }
    
    void deallocate(void *p, size_type)
    {
        if (p) ::operator delete(*(reinterpret_cast<void**>(p) - 1));
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
 *  Binary relational operators for Allocator.
 *
 *****************************************************************************/

template<class T, class U>
inline bool operator==(const Allocator<T>&, const Allocator<U>&)
{
    return true;
}

template<class T, class U>
inline bool operator!=(const Allocator<T>&, const Allocator<U>&)
{
    return false;
}


/******************************************************************************
 *
 *  Specialization for void.
 *
 *****************************************************************************/

template<>
class Allocator<void>
{
public:
    typedef size_t       size_type;
    typedef ptrdiff_t    difference_type;
    typedef void         value_type;
    typedef void*        pointer;
    typedef const void*  const_pointer;
};

} /* namespace VM */


#endif /* VM_ALLOCATOR_H */
