#ifndef VM_FFTW2_H
#define VM_FFTW2_H

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
 *  vm_fftw2.h - Wrapper class for use with the FFTW 2.x software.
 *
 *****************************************************************************/


#include <fftw.h>
#include <rfftw.h>

typedef fftw_real              REAL;
typedef VM::Vector<REAL>       Vec_REAL;
typedef std::complex<REAL>     CPLX_REAL;
typedef VM::Vector<CPLX_REAL>  Vec_CPLX_REAL;
typedef fftw_complex           FFTW_CPLX;
typedef VM::Vector<FFTW_CPLX>  Vec_FFTW_CPLX;


namespace VM {

/******************************************************************************
 *
 *  This class wraps the fftw libraries for use with complex Vectors.
 *
 *****************************************************************************/

class FFTWPlan
{
protected:
    size_t     size_;
    fftw_plan  frwrd_;
    fftw_plan  invrs_;

public:
    explicit FFTWPlan(size_t len = 0) : size_(len)
    {
        if (!size_)
        {
            frwrd_ = 0;
            invrs_ = 0;
        }
        else
        {
            frwrd_ = fftw_create_plan(size_, FFTW_FORWARD, FFTW_ESTIMATE);
            invrs_ = fftw_create_plan(size_, FFTW_BACKWARD, FFTW_ESTIMATE);
        }
    }

    FFTWPlan(const FFTWPlan& srvr) : size_(srvr.size_)
    {
        if (!size_)
        {
            frwrd_ = 0;
            invrs_ = 0;
        }
        else
        {
            frwrd_ = fftw_create_plan(size_, FFTW_FORWARD, FFTW_ESTIMATE);
            invrs_ = fftw_create_plan(size_, FFTW_BACKWARD, FFTW_ESTIMATE);
        }
    }

    ~FFTWPlan()
    {
        if (frwrd_)
            fftw_destroy_plan(frwrd_);
        if (invrs_)
            fftw_destroy_plan(invrs_);
    }

    FFTWPlan& operator=(const FFTWPlan& srvr)
    {
        resize(srvr.size_);
        return *this;
    }

    size_t size() const
    {
        return size_;
    }

    void resize(size_t len)
    {
        if (frwrd_)
            fftw_destroy_plan(frwrd_);
        if (invrs_)
            fftw_destroy_plan(invrs_);    

        size_ = len;
        if (!size_)
        {
            frwrd_ = 0;
            invrs_ = 0;
        }
        else
        {
            frwrd_ = fftw_create_plan(size_, FFTW_FORWARD, FFTW_ESTIMATE);
            invrs_ = fftw_create_plan(size_, FFTW_BACKWARD, FFTW_ESTIMATE);
        }
    }

    void fourier(const Vec_CPLX_REAL& array, Vec_CPLX_REAL& arrayft)
    {
        ptrdiff_t istride, ostride;

        if (array.size() != arrayft.size())
            vmerror("Length mismatch error in FFTWPlan::fourier().");

        if (size_ != array.size())
        {
            if (!array.size()) return;
            else resize(array.size());
        }

        const FFTW_CPLX *temp = reinterpret_cast<const FFTW_CPLX *>(array.data());
        FFTW_CPLX *ar_time = const_cast<FFTW_CPLX *>(temp);
        FFTW_CPLX *ar_freq = reinterpret_cast<FFTW_CPLX *>(arrayft.data());    

        istride = array.stride();
        ostride = arrayft.stride();
        fftw(frwrd_, 1, ar_time, istride, size_, ar_freq, ostride, size_);
    }

    void ifourier(const Vec_CPLX_REAL& arrayft, Vec_CPLX_REAL& array)
    {
        ptrdiff_t istride, ostride;

        if (array.size() != arrayft.size())
            vmerror("Length mismatch error in FFTWPlan::ifourier().");

        if (size_ != array.size())
        {
            if (!array.size()) return;
            else resize(array.size());
        }

        const FFTW_CPLX *temp = reinterpret_cast<const FFTW_CPLX *>(arrayft.data());
        FFTW_CPLX *ar_freq = const_cast<FFTW_CPLX *>(temp);
        FFTW_CPLX *ar_time = reinterpret_cast<FFTW_CPLX *>(array.data());    

        istride = arrayft.stride();
        ostride = array.stride();
        fftw(invrs_, 1, ar_freq, istride, size_, ar_time, ostride, size_);
    }

    void fourier(const Vec_FFTW_CPLX& array, Vec_FFTW_CPLX& arrayft)
    {
        ptrdiff_t istride, ostride;

        if (array.size() != arrayft.size())
            vmerror("Length mismatch error in FFTWplan::fourier().");

        if (size_ != array.size())
        {
            if (!array.size()) return;
            else resize(array.size());
        }

        istride = array.stride();
        ostride = arrayft.stride();
        fftw(frwrd_, 1, const_cast<FFTW_CPLX *>(array.data()), istride, size_,
            arrayft.data(), ostride, size_);
    }

    void ifourier(const Vec_FFTW_CPLX& arrayft, Vec_FFTW_CPLX& array)
    {
        ptrdiff_t istride, ostride;

        if (array.size() != arrayft.size())
            vmerror("Length mismatch error in FFTWPlan::ifourier().");

        if (size_ != array.size())
        {
            if (!array.size()) return;
            else resize(array.size());
        }

        ostride = array.stride();
        istride = arrayft.stride();
        fftw(invrs_, 1, const_cast<FFTW_CPLX *>(arrayft.data()), istride, size_,
            array.data(), ostride, size_);
    }
};


/******************************************************************************
 *
 *  This class wraps the fftw libraries for use with real Vectors.
 *
 *****************************************************************************/

class RFFTWPlan
{
protected:
    size_t      size_;
    rfftw_plan  frwrd_;
    rfftw_plan  invrs_;

public:
    explicit RFFTWPlan(size_t len = 0) : size_(len)
    {
        if (!size_)
        {
            frwrd_ = 0;
            invrs_ = 0;
        }
        else
        {
            frwrd_ = rfftw_create_plan(size_, FFTW_REAL_TO_COMPLEX, FFTW_ESTIMATE);
            invrs_ = rfftw_create_plan(size_, FFTW_COMPLEX_TO_REAL, FFTW_ESTIMATE);
        }
    }

    RFFTWPlan(const RFFTWPlan& srvr) : size_(srvr.size_)
    {
        if (!size_)
        {
            frwrd_ = 0;
            invrs_ = 0;
        }
        else
        {
            frwrd_ = rfftw_create_plan(size_, FFTW_REAL_TO_COMPLEX, FFTW_ESTIMATE);
            invrs_ = rfftw_create_plan(size_, FFTW_COMPLEX_TO_REAL, FFTW_ESTIMATE);
        }
    }

    ~RFFTWPlan()
    {
        if (frwrd_)
            rfftw_destroy_plan(frwrd_);
        if (invrs_)
            rfftw_destroy_plan(invrs_);
    }

    RFFTWPlan& operator=(const RFFTWPlan& srvr)
    {
        resize(srvr.size_);
        return *this;
    }

    size_t size() const
    {
        return size_;
    }

    void resize(size_t len)
    {
        if (frwrd_)
            rfftw_destroy_plan(frwrd_);
        if (invrs_)
            rfftw_destroy_plan(invrs_);    

        size_ = len;
        if (!size_)
        {
            frwrd_ = 0;
            invrs_ = 0;
        }
        else
        {
            frwrd_ = rfftw_create_plan(size_, FFTW_REAL_TO_COMPLEX, FFTW_ESTIMATE);
            invrs_ = rfftw_create_plan(size_, FFTW_COMPLEX_TO_REAL, FFTW_ESTIMATE);
        }
    }

    void fourier(const Vec_REAL& array, Vec_REAL& arrayft)
    {
        ptrdiff_t istride, ostride;

        if (array.size() != arrayft.size())
            vmerror("Length mismatch error in RFFTWPlan::fourier().");

        if (size_ != array.size())
        {
            if (!array.size()) return;
            else resize(array.size());
        }

        istride = array.stride();
        ostride = arrayft.stride();
        rfftw(frwrd_, 1, const_cast<REAL *>(array.data()), istride, size_,
            arrayft.data(), ostride, size_);
    }

    void ifourier(Vec_REAL& arrayft, Vec_REAL& array)
    {
        ptrdiff_t istride, ostride;

        if (array.size() != arrayft.size())
            vmerror("Length mismatch error in RFFTWPlan::ifourier().");

        if (size_ != array.size())
        {
            if (!array.size()) return;
            else resize(array.size());
        }

        istride = arrayft.stride();
        ostride = array.stride();
        rfftw(invrs_, 1, arrayft.data(), istride, size_, array.data(), ostride, size_);
    }

    Vec_REAL power(const Vec_REAL& data, size_t winsize)
    {
        size_t i, len = data.size();
        if (!len) vmerror("Power of zero size vector not allowed.");
        if (!winsize) return Vec_REAL(0);

        if (size_ != winsize) resize(winsize);

        Vec_REAL welch(winsize);
        REAL temp;
        for (i = 0; i < winsize; ++i)
        {
            temp     = (2.0 * i - winsize + 1.0) / (winsize + 1.0);
            welch[i] = 1.0 - temp * temp;
        }

        REAL data_mean = data.sum() / len;

        Vec_REAL sample, sampft(winsize), pwr(winsize);

        size_t nyq = winsize / 2;
        i = 0;
        while (i < len - winsize)
        {
            sample.ref(data.slice(i, winsize) - data_mean);
            fourier(sample, sampft);
            pwr += sampft * sampft;
            i += nyq;
        }

        for (i = 1; i < nyq; i++)
        {
            pwr[i] += pwr[winsize - i];
            pwr[winsize - i] = 0.0;
        }

        if (winsize % 2)
        {
            pwr[nyq] += pwr[nyq + 1];
            pwr[nyq + 1] = 0;
        }

        return pwr;
    }

    Vec_REAL cross_spectrum(const Vec_REAL& data1, const Vec_REAL& data2, size_t winsize)
    {
        size_t i, j, len = data1.size();
        if (!len) vmerror("Cross-Spectrum of zero size vector not allowed.");
        if (len != data2.size())
            vmerror("Size mismatch error in RFFTWPlan::cross_spectrum().");
        if (!winsize) return Vec_REAL(0);

        if (size_ != winsize) resize(winsize);

        Vec_REAL welch(winsize);
        REAL temp;
        for (i = 0; i < winsize; ++i)
        {
            temp     = (2.0 * i - winsize + 1.0) / (winsize + 1.0);
            welch[i] = 1.0 - temp * temp;
        }

        REAL data1_mean = data1.sum() / len;
        REAL data2_mean = data2.sum() / len;

        Vec_REAL sample;
        Vec_REAL samp1ft(winsize), samp2ft(winsize);
        Vec_REAL cross(winsize);

        size_t nyq = winsize / 2, flag = winsize % 2;
        i = 0;
        while (i < len - winsize)
        {
            sample.ref(data1.slice(i, winsize) - data1_mean);
            fourier(sample, samp1ft);
            sample.ref(data2.slice(i, winsize) - data2_mean);
            fourier(sample, samp2ft);
            cross[0] += samp1ft[0] * samp2ft[0];
            for (j = 1; j < nyq; ++j)
            {
                cross[j] += samp1ft[j] * samp2ft[j];
                cross[j] += samp1ft[winsize - j] * samp2ft[winsize - j];
                cross[winsize - j] += samp1ft[j] * samp2ft[winsize - j];
                cross[winsize - j] -= samp2ft[j] * samp1ft[winsize - j];
            }
            if (flag)
            {
                cross[nyq] += samp1ft[nyq] * samp2ft[nyq];
                cross[nyq] += samp1ft[nyq + 1] * samp2ft[nyq + 1];
                cross[nyq + 1] += samp1ft[nyq] * samp2ft[nyq + 1];
                cross[nyq + 1] -= samp2ft[nyq] * samp1ft[nyq + 1];
            }
            else cross[nyq] += samp1ft[nyq] * samp2ft[nyq];
            
            i += nyq;
        }

        return cross;
    }

    Vec_REAL convolve(const Vec_REAL& signal, const Vec_REAL& response, size_t pad = 0)
    {
        size_t len1 = signal.size(), len2 = response.size();
        if (!len1 || !len2) vmerror("Convolution of zero size vector not allowed.");
        if (len2 > len1)
            vmerror("Size mismatch error in RFFTWPlan::convolve().");

        size_t i, nyq, len  = len1 + pad;
        Vec_REAL sig = signal, resp = response;
        if (pad) sig.resize(len);
        if (size_ != len) resize(len);

        if (len2 < len)
        {
            resp.reshape(len);
            nyq = len2 / 2 + 1;
            resp[0] = response[0];
            for (i = 1; i < nyq; ++i)
            {
                resp[i] = response[i];
                resp[len - i] = response[len2 - i];
            }
        }

        Vec_REAL sigft(len), respft(len);
        fourier(sig, sigft);
        fourier(resp, respft);

        sig.free();
        resp.free();
        nyq = len / 2;
        Vec_REAL resultft(len);
        resultft[0] = sigft[0] * respft[0];
        for (i = 1; i < nyq; ++i)
        {
            resultft[i]  = sigft[i] * respft[i];
            resultft[i] -= sigft[len - i] * respft[len - i];
            resultft[len - i]  = sigft[i] * respft[len - i];
            resultft[len - i] += sigft[len - i] * respft[i];
        }
        if (len % 2)
        {
            resultft[nyq]  = sigft[nyq] * respft[nyq];
            resultft[nyq] -= sigft[nyq + 1] * respft[nyq + 1];
            resultft[nyq + 1]  = sigft[nyq] * respft[nyq + 1];
            resultft[nyq + 1] += sigft[nyq + 1] * respft[nyq];
        }
        else resultft[nyq] = sigft[nyq] * respft[nyq];
        sigft.free();
        respft.free();
        Vec_REAL result(len);
        ifourier(resultft, result);

        return result.slice(0, len1) / REAL(len);
    }
};

inline size_t good_size(size_t len, size_t factor = 1)
{
    if (!factor) return 0;
    if (len < 128 * factor) return len + factor - (len % factor);
    size_t n, i, x;
    
    static size_t num[9]   = {9, 75, 5, 45, 25, 27, 15, 1};
    static size_t denom[9] = {4,  7, 3,  6,  5,  5,  4, 0};
    
    n = factor;
    while (n < len) n <<= 1;

    x = n >> 1;
    i = 0;
    while (x < len)
    {
        x = (n >> denom[i]) * num[i];
        ++i;
    }

    return x;
}

} /* namespace VM */


#endif    /* VM_FFTW2_H */
