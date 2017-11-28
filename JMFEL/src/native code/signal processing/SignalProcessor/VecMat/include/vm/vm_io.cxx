#ifndef VM_IO_CXX
#define VM_IO_CXX

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
 *  vm_io.cxx - Implementation file for iostream functionality.
 *
 *  This is an internal header file, and is not intended to be used directly.
 *
 *****************************************************************************/


namespace VM {

/******************************************************************************
 *
 *  File io functions. 
 *
 *****************************************************************************/

template <class T>
ptrdiff_t read_binary(const std::string& fname, Vector<T>& array, size_t skip)
{
    offset_type fs;
    size_t len;
    std::ifstream file;

    fs = file_status<T>(fname.c_str(), skip);
    if (fs <= 0) return fs;

    len = size_t(fs);
    array.reshape(len);

    file.open(fname.c_str(), std::ios::in | std::ios::binary);
    file.seekg(skip, std::ios::beg);
    file.read(reinterpret_cast<char *>(array.data()), len * sizeof(T));

    if (file.eof() || file.fail())
    {
        array.clear();
        return -3;
    }
    file.close();

    return len;
}

template <class T>
ptrdiff_t read_text(const std::string& fname, Vector<T>& array, size_t skip)
{
    offset_type fs;
    
    fs = file_status<char>(fname.c_str(), 0);
    if (fs <= 0) return fs;
    
    std::ifstream file(fname.c_str());
    std::string header;
    size_t buff_size = 1024, i;
    for (i = 0; i < skip; ++i)
    {
        std::getline(file, header);
        if (file.fail() || file.eof())
        {
            file.close();
            return -1;
        }
    }           
    T tmp;
    array.reshape(buff_size);
    i = 0;
    while (file >> tmp)
    {
        array[i] = tmp;
        ++i;
        if (i == buff_size)
        {
            buff_size *= 2;
            array.resize(buff_size);
        }
    }
    if (i < buff_size) array.resize(i);
    return i;
}


} /* namespace VM */


#endif /* VM_IO_CXX */
