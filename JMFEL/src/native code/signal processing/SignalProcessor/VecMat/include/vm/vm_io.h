#ifndef VM_IO_H
#define VM_IO_H


// begin
// These lines are needed when using VCS .net 2003. 
#ifndef __MINGW32__
extern "C" int __cdecl _fseeki64(FILE *, __int64, int);
extern "C" __int64 __cdecl _ftelli64(FILE *);
#define fseeko(fm, pos, type) _lseeki64(_fileno(fm), (pos), (type))
#define ftello(fm) _lseeki64(_fileno(fm), 0, SEEK_CUR)
#endif /* __MINGW32__ */
// end

/******************************************************************************
 *
 *  VecMat Software, version 1.41
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
 *  vm_io.h - Header file for iostream functionality.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

/******************************************************************************
 *
 *  Stream operators for vectors.
 *
 *****************************************************************************/

template <class T>
inline std::ostream& operator<<(std::ostream& out, const Vector<T>& x)
{
    x.write(out);
    return out;
}

template <class T>
inline std::istream& operator>>(std::istream& in, Vector<T>& x)
{
    x.read(in);
    return in;
}


/******************************************************************************
 *
 *  Stream operators for matrices.
 *
 *****************************************************************************/

template <class T>
inline std::ostream& operator<<(std::ostream& out, const Matrix<T>& x)
{
    x.write(out);
    return out;
}

template <class T>
inline std::istream& operator>>(std::istream& in, Matrix<T>& x)
{
    x.read(in);
    return in;
}


/******************************************************************************
 *
 * File IO functions.
 *
 *****************************************************************************/

template <class T>
ptrdiff_t read_binary(const std::string& fname, Vector<T>& array, size_t skip = 0);

template <class T>
inline void write_binary(const std::string& fname, const Vector<T>& array)
{
    array.write(fname, BIN);
}

template <class T>
ptrdiff_t read_text(const std::string& fname, Vector<T>& array, size_t skip = 0);

template <class T>
inline void write_text(const std::string& fname, const Vector<T>& array)
{
    array.write(fname, TXT);
}    


/******************************************************************************
 *
 * File Class for large file support.
 *
 *****************************************************************************/

enum Open_Mode {IN, OUT, IN_OUT, IN_OUT_OW};

class File
{
private:
    std::FILE    *file_;
    Open_Mode    mode_;
    offset_type  size_;
    
    File(const File& f);
    File& operator=(const File& f);
    
public:
    File() : file_(0), mode_(IN), size_(0) {;}
    
    File(const std::string& fname, Open_Mode fm) : file_(0), mode_(fm),
        size_(0)
    {
        open(fname, fm);
    }
    
    ~File() {if (file_) std::fclose(file_);}
    
    bool open(const std::string& fname, Open_Mode fm)
    {
        mode_ = fm;
        if (file_) close();
        offset_type fs = file_status<char>(fname, 0);
        size_ = 0;
        if (fs < 1)
        {
            if (fm == IN || fm == IN_OUT) file_ = 0;
            else if (fm == OUT) file_ = std::fopen(fname.c_str(), "wb");
            else file_ = std::fopen(fname.c_str(), "w+b");
        }
        else
        {
            if (fm == IN)
            {
                file_ = std::fopen(fname.c_str(), "rb");
                if (file_) size_ = fs;
            }
            else if (fm == OUT) file_ = std::fopen(fname.c_str(), "wb");
            else if (fm == IN_OUT)
            {
                file_ = std::fopen(fname.c_str(), "r+b");
                if (file_) size_ = fs;
            }
            else file_ = std::fopen(fname.c_str(), "w+b");
        }
        return file_;
    }
    
    bool close()
    {
        bool f = true;
        if (file_) f = std::fclose(file_);
        file_ = 0;
        size_ = 0;
        return !f;
    }
    
    offset_type tell() const
    {
        using namespace std;
#ifdef __MINGW32__
        return (file_) ? ftello64(file_) : -1;
#else
        return (file_) ? ftello(file_) : -1;
#endif /* __MINGW32__ */
    }
    
    offset_type size() const {return size_;}

    operator void *() const {return fail() ? 0 : file_;}
    
    bool operator!() const {return fail();}
    
    bool is_open() const {return file_;}
    
    Open_Mode mode() const {return mode_;}

// begin
// These lines are needed when using VCS .net 2003.
#ifdef __MINGW32__    
    bool fail() const {return (file_) ? std::ferror(file_) : true;}
#else
    bool fail() const {return (file_) ? ferror(file_) : true;}
#endif /* __MINGW32__ */

#ifdef __MINGW32__  
	bool eof() const {return (file_) ? std::feof(file_) : false;}
#else
	bool eof() const {return (file_) ? feof(file_) : false;}
#endif /* __MINGW32__ */
// end

    bool good() const {return !fail();}
    
    bool flush() {return (file_) ? !std::fflush(file_) : false;}
    
    void clear() {if (file_) std::clearerr(file_);}
    
    bool seek(offset_type offset, int origin = SEEK_CUR)
    {
        using namespace std;
        if (!file_) return false;
#ifdef __MINGW32__
        return !fseeko64(file_, offset, origin);
#else
        return !fseeko(file_, offset, origin);
#endif /* __MINGW32__ */
    }
    
    void rewind()
    {
        if (!file_) return;
        std::rewind(file_);
    }     

    template <class T>
    offset_type read(Vector<T>& x, offset_type s = 1)
    {
        if (fail() || eof() || mode_ == OUT) return -1;
        offset_type len = x.size();
        if (!len) return 0;
        if (s < 1) return -2;
        len = std::min((size_ - tell()) / s, len);
        offset_type dpr;
        T* dat = x.data();
        ptrdiff_t xs = x.stride();
        if (s == 1 && xs == 1) dpr = std::fread(dat, sizeof(T), len, file_);
        else
        {
            dpr = 0;
            for (size_t i = 0; i < len; ++i)
            {
                dpr += std::fread(dat, sizeof(T), 1, file_);
                if (s != 1) seek(sizeof(T) * (s - 1), SEEK_CUR);
                dat += xs;
            }
        }
        return dpr;
    }
    
    template <class T>
    offset_type write(const Vector<T>& x, offset_type s = 1)
    {
        if (fail() || mode_ == IN) return -1;
        offset_type len = x.size();
        if (!len) return 0;
        if (s < 1) return -2;
        if (s > 1 && (size_ - tell()) / s < len) return -3; 
        offset_type dpr;
        const T* dat = x.data();
        ptrdiff_t xs = x.stride();
        if (s == 1 && xs == 1) dpr = std::fwrite(dat, sizeof(T), len, file_);
        else
        {
            dpr = 0;
            for (size_t i = 0; i < len; ++i)
            {
                dpr += std::fwrite(dat, sizeof(T), 1, file_);
                if (s != 1) seek(sizeof(T) * (s - 1), SEEK_CUR);
                dat += xs;
            }
        }
        size_ = std::max(size_, tell());
        return dpr;
    }
    
    template <class T>
    offset_type zero_fill(offset_type n)
    {
        if (n < 1) return 0;
        if (fail() || mode_ == IN) return -1;
        offset_type dpr = 0;
        T zero = Traits<T>::zero();
        for (offset_type i = 0; i < n; ++i)
            dpr += std::fwrite(&zero, sizeof(T), 1, file_);
        size_ = std::max(size_, tell());
        return dpr;
    }
    
    static bool remove(const std::string& fname)
    {
        return !std::remove(fname.c_str());
    }
    
    static bool rename(const std::string& oldname, const std::string& newname)
    {
        return !std::rename(oldname.c_str(), newname.c_str());
    }
};

} /* namespace VM */

#include <vm/vm_io.cxx>


#endif /* VM_IO_H */
