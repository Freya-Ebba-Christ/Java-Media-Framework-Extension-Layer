#ifndef VM_FFTW3_H
#define VM_FFTW3_H

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
 *  vm_fftw3.h - Wrapper and support functions for the FFTW 3.x software.
 *
 *  This header file should be included if you want to use the FFTW 3.x
 *  software in combination with the VecMat software.
 *
 *****************************************************************************/

#include <vm/vec_mat.h>
#include <vm/vm_filter.h>
#include <fftw3.h>

namespace VM {

enum Plan_Type {NULL_PLAN, C2C_PLAN, R2C_PLAN, R2R_PLAN};
enum Plan_Dir  {FORWARD, BACKWARD, BIDIR};


/******************************************************************************
 *
 *  Wrapper class for the fftw_plan structure.
 *
 *****************************************************************************/

class FFTPlan
{
private:
    double        *indp_, *outdp_;
    fftw_complex  *incdp_, *outcdp_;
    size_t        size_;
    ptrdiff_t     str_in_, str_out_;
    Plan_Type     type_;
    Plan_Dir      direction_;
    fftw_plan     forward_;
    fftw_plan     backward_;

public:
    FFTPlan() : indp_(0), outdp_(0), incdp_(0), outcdp_(0), size_(0),
        str_in_(0), str_out_(0), type_(NULL_PLAN), direction_(BIDIR),
        forward_(0), backward_(0) {;}

    FFTPlan(const FFTPlan& plan) : forward_(0), backward_(0) {(*this) = plan;}

    FFTPlan(Vec_CPLX_DP& in, Vec_CPLX_DP& out, Plan_Dir dir = BIDIR) :
         forward_(0), backward_(0) {new_plan(in, out, dir);}

    FFTPlan(Vec_CPLX_DP& dat, Plan_Dir dir = BIDIR) : forward_(0), backward_(0)
         {new_plan(dat, dir);}

    FFTPlan(Vec_DP& in, Vec_CPLX_DP& out, Plan_Dir dir = BIDIR) : forward_(0),
         backward_(0) {new_plan(in, out, dir);}

    FFTPlan(Vec_DP& in, Vec_DP& out, Plan_Dir dir = BIDIR) : forward_(0),
        backward_(0) {new_plan(in, out, dir);}

    FFTPlan(Vec_DP& dat, Plan_Dir dir = BIDIR) : forward_(0), backward_(0)
        {new_plan(dat, dir);}
    
    ~FFTPlan()
    {
        if (forward_)  fftw_destroy_plan(forward_);
        if (backward_) fftw_destroy_plan(backward_);
    }

    FFTPlan& operator=(const FFTPlan& plan)
    {
        if (&plan == this) return *this;
        free();
        Vec_DP indp, outdp;
        Vec_CPLX_DP incdp, outcdp;
        if (plan.indp_) indp.view(plan.indp_, plan.size_, plan.str_in_);
        if (plan.outdp_) outdp.view(plan.outdp_, plan.size_, plan.str_out_);
        if (plan.incdp_) incdp.view(reinterpret_cast<CPLX_DP*>(plan.incdp_),
            plan.size_, plan.str_in_);
        if (plan.outcdp_) outcdp.view(reinterpret_cast<CPLX_DP*>(plan.outcdp_),
            plan.size_, plan.str_out_);

        switch (plan.type_)
        {
        case C2C_PLAN:
            new_plan(incdp, outcdp, plan.direction_);
            break;
        case R2C_PLAN:
            new_plan(indp, outcdp, plan.direction_);
            break;
        case R2R_PLAN:
            new_plan(indp, outdp, plan.direction_);
            break;
        case NULL_PLAN:
            break;
        }
        return *this;
    }

    Plan_Type type() const {return type_;}

    void new_plan(Vec_CPLX_DP& in, Vec_CPLX_DP& out, Plan_Dir dir = BIDIR)
    {
        if (in.size() != out.size() || !in.size())
            vmerror("Size mismatch error in FFTPlan::new_plan()");

        if (forward_)  fftw_destroy_plan(forward_);
        if (backward_) fftw_destroy_plan(backward_);

        type_      = C2C_PLAN;
        direction_ = dir;
        indp_      = 0;
        outdp_     = 0;
        incdp_     = reinterpret_cast<fftw_complex*>(in.data());
        outcdp_    = reinterpret_cast<fftw_complex*>(out.data());
        size_      = in.size();
        str_in_    = in.stride();
        str_out_   = out.stride();
        forward_   = 0;
        backward_  = 0;

        int s = int(size_);
        if (dir != BACKWARD) forward_  = fftw_plan_many_dft(1, &s, 1,
            incdp_, 0, int(str_in_), 0, outcdp_, 0, int(str_out_), 0, FFTW_FORWARD,
            FFTW_ESTIMATE);
        if (dir != FORWARD)  backward_ = fftw_plan_many_dft(1, &s, 1,
            outcdp_, 0, int(str_out_), 0, incdp_, 0, int(str_in_), 0, FFTW_BACKWARD,
            FFTW_ESTIMATE);
    }
    
    void new_plan(Vec_CPLX_DP& dat, Plan_Dir dir = BIDIR)
    {
        new_plan(dat, dat, dir);
    }

    void new_plan(Vec_DP& in, Vec_CPLX_DP& out, Plan_Dir dir = BIDIR)
    {
        if (in.size() / 2 + 1 != out.size() || !in.size())
            vmerror("Size mismatch error in FFTPlan::new_plan()");

        if (forward_)  fftw_destroy_plan(forward_);
        if (backward_) fftw_destroy_plan(backward_);

        type_      = R2C_PLAN;
        direction_ = dir;
        indp_      = in.data();
        outdp_     = 0;
        incdp_     = 0;
        outcdp_    = reinterpret_cast<fftw_complex*>(out.data());
        size_      = in.size();
        str_in_    = in.stride();
        str_out_   = out.stride();
        forward_   = 0;
        backward_  = 0;

        int s = int(size_);
        if (dir != BACKWARD) forward_  = fftw_plan_many_dft_r2c(1, &s, 1,
            indp_, 0, int(str_in_), 0, outcdp_, 0, int(str_out_), 0, FFTW_ESTIMATE);
        if (dir != FORWARD)  backward_ = fftw_plan_many_dft_c2r(1, &s, 1,
            outcdp_, 0, int(str_out_), 0, indp_, 0, int(str_in_), 0, FFTW_ESTIMATE);
    }
    
    void new_plan(Vec_DP& in, Vec_DP& out, Plan_Dir dir = BIDIR)
    {
        if (in.size() != out.size() || !in.size())
            vmerror("Size mismatch error in FFTPlan::new_plan()");

        if (forward_)  fftw_destroy_plan(forward_);
        if (backward_) fftw_destroy_plan(backward_);

        type_      = R2R_PLAN;
        direction_ = dir;
        indp_      = in.data();
        outdp_     = out.data();
        incdp_     = 0;
        outcdp_    = 0;
        size_      = in.size();
        str_in_    = in.stride();
        str_out_   = out.stride();
        forward_   = 0;
        backward_  = 0;

        int s = int(size_);
        fftw_r2r_kind r2hc = FFTW_R2HC, hc2r = FFTW_HC2R;
        if (dir != BACKWARD) forward_  = fftw_plan_many_r2r(1, &s, 1,
            indp_, 0, int(str_in_), 0, outdp_, 0, int(str_out_), 0, &r2hc,
            FFTW_ESTIMATE);
        if (dir != FORWARD)  backward_ = fftw_plan_many_r2r(1, &s, 1,
            outdp_, 0, int(str_out_), 0, indp_, 0, int(str_in_), 0, &hc2r,
            FFTW_ESTIMATE | FFTW_PRESERVE_INPUT);
    }

    void new_plan(Vec_DP& dat, Plan_Dir dir = BIDIR)
    {
        new_plan(dat, dat, dir);
    }

    void free()
    {
        if (forward_)  fftw_destroy_plan(forward_);
        if (backward_) fftw_destroy_plan(backward_);

        type_      = NULL_PLAN;
        direction_ = BIDIR;
        indp_      = 0;
        outdp_     = 0;
        incdp_     = 0;
        outcdp_    = 0;
        size_      = 0;
        str_in_    = 0;
        str_out_   = 0;
        forward_   = 0;
        backward_  = 0;
    }

    Plan_Dir direction() const
    {
        return direction_;
    }

    void direction(Plan_Dir dir)
    {
        if (dir == direction_ || type_ == NULL_PLAN) return;
        int s = int(size_);
        fftw_r2r_kind hc2r = FFTW_HC2R, r2hc = FFTW_R2HC;
        switch (direction_)
        {
        case BIDIR:
            if (dir == FORWARD)  fftw_destroy_plan(backward_);
            if (dir == BACKWARD) fftw_destroy_plan(forward_);
            break;

        case FORWARD:
            if (dir == BACKWARD) fftw_destroy_plan(forward_);
            switch (type_)
            {
            case R2R_PLAN:
                backward_ = fftw_plan_many_r2r(1, &s, 1, outdp_, 0,
                    int(str_out_), 0, indp_, 0, int(str_in_), 0, &hc2r,
                    FFTW_ESTIMATE | FFTW_PRESERVE_INPUT);
                break;
            case R2C_PLAN:
                backward_ = fftw_plan_many_dft_c2r(1, &s, 1, outcdp_, 0,
                    int(str_out_), 0, indp_, 0, int(str_in_), 0, FFTW_ESTIMATE);
                break;
            case C2C_PLAN:
                backward_ = fftw_plan_many_dft(1, &s, 1, outcdp_, 0,
                    int(str_out_), 0, incdp_, 0, int(str_in_), 0, FFTW_BACKWARD,
                    FFTW_ESTIMATE);
                break;
            case NULL_PLAN:
                break;
            }
            break;
        case BACKWARD:
            if (dir == FORWARD) fftw_destroy_plan(backward_);
            switch (type_)
            {
            case R2R_PLAN:
                forward_ = fftw_plan_many_r2r(1, &s, 1, indp_, 0, int(str_out_),
                    0, outdp_, 0, int(str_in_), 0, &r2hc, FFTW_ESTIMATE);
                break;
            case R2C_PLAN:
                forward_ = fftw_plan_many_dft_r2c(1, &s, 1, indp_, 0,
                    int(str_out_), 0, outcdp_, 0, int(str_in_), 0, FFTW_ESTIMATE);
                break;
            case C2C_PLAN:
                forward_ = fftw_plan_many_dft(1, &s, 1, incdp_, 0,
                    int(str_out_), 0, outcdp_, 0, int(str_in_), 0, FFTW_FORWARD,
                    FFTW_ESTIMATE);
                break;
            case NULL_PLAN:
                break;
            }
            break;
        }
        direction_ = dir;
    }

    void fourier() const
    {
        if (type_ == NULL_PLAN) vmerror("Cannot transform a NULL plan.");
        if (direction_ == BACKWARD)
            vmerror("Cannot forward transform a backward plan.");
        fftw_execute(forward_);
    }

    void ifourier() const
    {
        if (type_ == NULL_PLAN) vmerror("Cannot transform a NULL plan.");
        if (direction_ == FORWARD)
            vmerror("Cannot backward transform a forward plan.");
        fftw_execute(backward_);
    }
    
    void power(const Vec_DP& signal, Vec_DP& pwr) const
    {
        size_t i, k, len = signal.size(), winsize = pwr.size();
        if (!len) vmerror("Power of zero size vector not allowed.");
        if (!winsize) vmerror("Zero sample size error in FFTPlan::power().");
        if (winsize != size_)
            vmerror("Size mismatch error in FFTPlan::power().");
        if (type_ != R2R_PLAN) vmerror("Wrong plan-type in FFTPlan::power().");
        if (winsize > len)
            vmerror("Size mismatch error in FFTPlan::power().");
        if (direction_ == BACKWARD)
            vmerror("Cannot calculate power with a backward plan.");

        Vec_DP welch(winsize);
        double temp;
        Vec_DP::iterator p1, p2, a, b;
        p1 = welch.begin();
        for (i = 0; i < winsize; ++i, ++p1)
        {
            temp = (2.0 * i - winsize + 1.0) / (winsize + 1.0);
            (*p1) = 1.0 - square(temp);
        }

        double data_mean = mean(signal);

        Vec_DP x, y;
        x.view(indp_, size_, str_in_);
        y.view(outdp_, size_, str_out_);
        a = y.begin();
        b = y.end();
        size_t nyq = winsize / 2;
        i = 0; k = 0; pwr = 0.0;
        while (i < len - winsize)
        {
            x = signal.slice(i, winsize);
            x -= data_mean;
            x *= welch;
            fourier();
            p2 = pwr.begin();
            for (p1 = a; p1 < b; ++p1, ++p2)
                (*p2) += square(*p1);
            i += nyq;
            ++k;
        }

        pwr /= double(k * winsize);
    }

    void cross_spectrum(const Vec_DP& signal1, const Vec_DP& signal2,
        Vec_CPLX_DP& cross) const
    {
        size_t i, k, len = signal1.size(), winsize = cross.size();
        if (!len) vmerror("Cross-Spectrum of zero size vector not allowed.");
        if (len != signal2.size())
            vmerror("Size mismatch error in FFTPlan::cross_spectrum().");
        if (!winsize)
            vmerror("Zero sample size error in FFTPlan::cross_spectrum().");
        if (winsize != size_)
            vmerror("Size mismatch error in FFTPlan::cross_spectrum().");
        if (type_ != C2C_PLAN)
            vmerror("Wrong plan-type in FFTPlan::cross_spectrum().");
        if (winsize > len)
            vmerror("Size mismatch error in FFTPlan::cross_spectrum().");
        if (direction_ == BACKWARD)
            vmerror("Cannot calculate cross-spectrum with a backward plan.");

        Vec_DP welch(winsize);
        Vec_DP::iterator p = welch.begin();
        double temp;
        for (i = 0; i < winsize; ++i, ++p)
        {
            temp = (2.0 * i - winsize + 1.0) / (winsize + 1.0);
            (*p) = 1.0 - square(temp);
        }

        double data1_mean = mean(signal1);
        double data2_mean = mean(signal2);

        Vec_CPLX_DP sft(winsize);

        Vec_CPLX_DP x, y;
        Vec_CPLX_DP::iterator p1, p2, p3, a = cross.begin(), b = cross.end();
        x.view(reinterpret_cast<CPLX_DP*>(incdp_), size_, str_in_);
        y.view(reinterpret_cast<CPLX_DP*>(outcdp_), size_, str_out_);
        Vec_DP xr = x.real(), xi = x.imag();
        xi = 0.0;
        size_t nyq = winsize / 2;
        i = 0; k = 0; cross = CPLX_DP(0.0, 0.0);
        while (i < len - winsize)
        {
            xr = signal1.slice(i, winsize);
            xr -= data1_mean;
            xr *= welch;
            fourier();
            sft = y;

            xr = signal2.slice(i, winsize);
            xr -= data2_mean;
            xr *= welch;
            fourier();
            p2 = sft.begin();
            p3 = y.begin();
            for (p1 = a; p1 < b; ++p1, ++p2, ++p3)
                (*p1) += (*p2) * std::conj(*p3);
            i += nyq;
            ++k;
        }
        cross /= CPLX_DP(k * winsize);
    }

    void spectra(const Vec_DP& signal1, const Vec_DP& signal2, Vec_DP& pow1,
        Vec_DP& pow2, Vec_CPLX_DP& cross) const
    {
        size_t i, k, len = signal1.size(), winsize = pow1.size();
        if (!len) vmerror("Spectra of zero size vector not allowed.");
        if (len != signal2.size())
            vmerror("Size mismatch error in FFTPlan::spectra().");
        if (!winsize)
            vmerror("Zero sample size error in FFTPlan::spectra().");
        if (winsize != size_ || winsize != pow2.size() || winsize != cross.size())
            vmerror("Size mismatch error in FFTPlan::spectra().");
        if (type_ != C2C_PLAN)
            vmerror("Wrong plan-type in FFTPlan::spectra().");
        if (winsize > len)
            vmerror("Size mismatch error in FFTPlan::spectra().");
        if (direction_ == BACKWARD)
            vmerror("Cannot calculate spectra with a backward plan.");

        Vec_DP welch(winsize);
        Vec_DP::iterator p = welch.begin();
        double temp;
        for (i = 0; i < winsize; ++i,++p)
        {
            temp = (2.0 * i - winsize + 1.0) / (winsize + 1.0);
            (*p) = 1.0 - square(temp);
        }

        double data1_mean = mean(signal1);
        double data2_mean = mean(signal2);

        Vec_CPLX_DP sft(winsize);

        Vec_CPLX_DP x, y;
        x.view(reinterpret_cast<CPLX_DP*>(incdp_), size_, str_in_);
        y.view(reinterpret_cast<CPLX_DP*>(outcdp_), size_, str_out_);
        Vec_DP xr = x.real(), xi = x.imag();
        xi = 0.0;
        
        Vec_CPLX_DP::iterator p1, p2, p3, a, b;
        Vec_DP::iterator p4, p5;
        size_t nyq = winsize / 2;
        i = 0; k = 0;
        
        a = cross.begin();
        b = cross.end();
        pow1 = 0.0;
        pow2 = 0.0;
        cross = CPLX_DP(0.0);
        while (i < len - winsize + 1)
        {
            xr = signal1.slice(i, winsize);
            xr -= data1_mean;
            xr *= welch;
            fourier();
            sft = y;

            xr = signal2.slice(i, winsize);
            xr -= data2_mean;
            xr *= welch;
            fourier();
            
            p2 = sft.begin();
            p3 = y.begin();
            p4 = pow1.begin();
            p5 = pow2.begin();
            for (p1 = a; p1 < b; ++p1, ++p2, ++p3, ++p4, ++p5)
            {
                (*p1) += (*p2) * std::conj(*p3);
                (*p4) += std::norm(*p2);
                (*p5) += std::norm(*p3);
            }
            i += nyq;
            ++k;
        }
        pow1 /= double(k * winsize);
        pow2 /= double(k * winsize);
        cross /= CPLX_DP(k * winsize);
    }


    void coherence(const Vec_DP& signal1, const Vec_DP& signal2,
        Vec_DP& coh) const
    {
        size_t len = signal1.size(), winsize = coh.size();
        if (!len) vmerror("Coherence of zero size vector not allowed.");
        if (len != signal2.size())
            vmerror("Size mismatch error in FFTPlan::coherence().");
        if (!winsize)
            vmerror("Zero sample size error in FFTPlan::coherence().");
        if (winsize != size_)
            vmerror("Size mismatch error in FFTPlan::coherence().");
        if (type_ != C2C_PLAN)
            vmerror("Wrong plan-type in FFTPlan::coherence().");
        if (winsize > len)
            vmerror("Size mismatch error in FFTPlan::coherence().");
        if (direction_ == BACKWARD)
            vmerror("Cannot calculate coherence with a backward plan.");

        Vec_DP pow1(winsize), pow2(winsize);
        Vec_CPLX_DP cross(winsize);
        spectra(signal1, signal2, pow1, pow2, cross);
        
        Vec_DP::iterator p1, p3, p4, e = coh.end();
        Vec_CPLX_DP::iterator p2 = cross.begin();
        p3 = pow1.begin();
        p4 = pow2.begin();
        for (p1 = coh.begin(); p1 < e; ++p1, ++p2, ++p3, ++p4)
            (*p1) = std::abs(*p2) / std::sqrt((*p3) * (*p4));
    }

    void filter(const Vec_DP& signal, Vec_DP& sigflt, const Filter& filt) const
    {
        size_t len = signal.size();
        if (!len) vmerror("Cannot filter a zero length vector.");
        if (sigflt.size() != len)
            vmerror("Size mismatch error in FFTPlan::filter().");
        if (size_ < len) vmerror("Plan too small error in FFTPlan::filter().");
        if (type_ != R2R_PLAN) vmerror("Wrong plan type in FFTPlan::filter().");
        if (direction_ != BIDIR)
            vmerror("Plan for filterring must be bidirectional.");

        Vec_DP x, y;
        x.view(indp_, size_, str_in_);
        y.view(outdp_, size_, str_out_);
        Vec_DP::iterator p1, e = x.end();
        Vec_DP::const_iterator p2, a = signal.begin(), b = signal.end();
        double sig_mean = mean(signal);
        p1 = x.begin();
        for (p2 = a; p2 < b; ++p1, ++p2) (*p1) = (*p2) - sig_mean;
        for (; p1 < e; ++p1) (*p1) = 0.0;
        Vec_DP pwr(size_);
        filt.make_filter(pwr);
        pwr.apply(std::sqrt);

        fourier();
        y *= pwr;
        ifourier();
        x /= size_;

        sig_mean *= pwr[0];
        sigflt = x.slice(0, len) + sig_mean;
    }
    
    void hilbert(const Vec_DP& signal, Vec_CPLX_DP& hil) const
    {
        size_t len = signal.size(), nyq = (size_ + 1) / 2;
        if (!len) vmerror("Cannot make Hilbert transform of a zero length vector.");
        if (hil.size() != len)
            vmerror("Size mismatch error in FFTPlan::hilbert().");
        if (size_ < len) vmerror("Size mismatch error in FFTPlan::hilbert().");
        if (type_ != C2C_PLAN) vmerror("Wrong plan type in FFTPlan::hilbert().");
        if (direction_ != BIDIR)
            vmerror("Plan for Hilbert transform must be bidirectional.");

        Vec_CPLX_DP x, y;
        x.view(reinterpret_cast<CPLX_DP*>(incdp_), size_, str_in_);
        y.view(reinterpret_cast<CPLX_DP*>(outcdp_), size_, str_out_);
        Vec_DP xr = x.real(), xi = x.imag();
        double sig_mean = mean(signal);
        xr = 0.0;
        Vec(xr.slice(0, len)) = signal;
        Vec(xr.slice(0, len)) -= sig_mean;
        xi = 0.0;    
        fourier();
        Vec_CPLX_DP::iterator p, a, b;
        a = y.begin();
        b = a + nyq;
        for (p = a + 1; p < b; ++p) (*p) *= 2.0;
        b = y.end();
        if (len % 2 == 0) ++p;
        for (; p < b; ++p) (*p) = 0.0;
        ifourier();
        hil = x.slice(0, len);
        hil /= size_;
    }    
};


/******************************************************************************
 *
 *  Global Functions.
 *
 *****************************************************************************/

inline void power(const Vec_DP& signal, Vec_DP& pwr)
{
    size_t winsize = pwr.size();
    Vec_DP in(winsize), out(winsize);
    FFTPlan fft(in, out, FORWARD);
    fft.power(signal, pwr);
}

inline void cross_spectrum(const Vec_DP& signal1, const Vec_DP& signal2,
    Vec_CPLX_DP& cross)
{
    size_t winsize = cross.size();
    Vec_CPLX_DP in(winsize), out(winsize);
    FFTPlan fft(in, out, FORWARD);
    fft.cross_spectrum(signal1, signal2, cross);
}

inline void spectra(const Vec_DP& signal1, const Vec_DP& signal2, Vec_DP& pow1,
    Vec_DP& pow2, Vec_CPLX_DP& cross)
{
    size_t winsize = pow1.size();
    Vec_CPLX_DP in(winsize), out(winsize);
    FFTPlan fft(in, out, FORWARD);
    fft.spectra(signal1, signal2, pow1, pow2, cross);
}

inline void coherence(const Vec_DP& signal1, const Vec_DP& signal2,
    Vec_DP& coh)
{
    size_t winsize = coh.size();
    Vec_CPLX_DP in(winsize), out(winsize);
    FFTPlan fft(in, out, FORWARD);
    fft.coherence(signal1, signal2, coh);
}

inline void filter(const Vec_DP& signal, Vec_DP& sigflt,
    const Filter& filt, size_t pad = 0)
{
    size_t len = signal.size();
    Vec_DP in(len + pad), out(len + pad);
    FFTPlan fft(in, out, BIDIR);
    fft.filter(signal, sigflt, filt);
}

inline void hilbert(const Vec_DP& signal, Vec_CPLX_DP& hil, size_t pad = 0)
{
    size_t len = signal.size();
    Vec_CPLX_DP in(len + pad), out(len + pad);
    FFTPlan fft(in, out, BIDIR);
    fft.hilbert(signal, hil);
}        

inline size_t good_size(size_t len, size_t factor = 1)
{
    if (!factor) return 0;
    if (len < 128 * factor) return len + factor - (len % factor);
    size_t n, i, x;
    
    static size_t num[9]   = {9, 75, 5, 45, 25, 27, 15, 1};
    static size_t denom[9] = {4,  7, 3,  6,  5,  5,  4, 0};
    
    n = factor;
    while (n < len)    n <<= 1;

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


#endif /* VM_FFTW3_H */
