#ifndef RANDOM_H
#define RANDOM_H

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
 *  random.h - Random number generator class declarations and definitions.
 *
 *  This header file does not depend on any of the other VecMat files, and it
 *  can be used independently of the rest of the software.
 *
 *****************************************************************************/

#include <cmath>
#include <ctime>
#include <algorithm>
#include <iterator>

/******************************************************************************
 *
 *  These classes must use 32 bit integers. If your compiler does not provide
 *  the C99 "stdint.h" header file, then comment out the next include statement,
 *  and uncomment the following typedefs. If 'int' is not 32 bits on your
 *  compiler, then substitute an integer type that is. MSVC++ does not seem to
 *  have this header file.
 *
 *****************************************************************************/
#ifndef _MSC_VER
#include <stdint.h>
#else
typedef int int32_t;
typedef unsigned int uint32_t;
#endif /* _MSC_VER */

namespace VM {

inline int32_t abs_(const int32_t& x) {return (x > 0) ? x : -x;}
inline double abs(const double& x) {return (x > 0) ? x : -x;}


/******************************************************************************
 *
 *  Static constants used by the random number generators.
 *
 *****************************************************************************/

static const uint32_t CM_    = 69069;
static const uint32_t CA_    = 1234567;
static const uint32_t MWC1_  = 36939;
static const uint32_t MWC2_  = 18000;
static const uint32_t MASK_  = 65535;


/******************************************************************************
 *
 *  Base random number generator class, based on George Marsaglia's KISS
 *  algorithm. Generates uniformly distributed unsigned 32 bit integers on the
 *  closed interval (0, 2^32 - 1). This class is used by all of the other
 *  generators.
 *
 *****************************************************************************/

class BaseGen
{
private:
    mutable uint32_t z_, w_, jsr_, jc_;
    
    static uint32_t get_seed_()
    {
        uint32_t seed = uint32_t(std::time(0));
        static BaseGen gen(seed);
        return gen();
    }    

public:
    explicit BaseGen(uint32_t seed = 0)
    {
        if (!seed) seed = get_seed_();
        jc_  = seed;
        z_   = (jc_ = CM_ * jc_ + CA_);
        w_   = (jc_ = CM_ * jc_ + CA_);
        jsr_ = (jc_ = CM_ * jc_ + CA_);
        jc_  = CM_ * jc_ + CA_;
    }

    BaseGen(const BaseGen& gen)
    {
        jc_  = gen();
        z_   = (jc_ = CM_ * jc_ + CA_);
        w_   = (jc_ = CM_ * jc_ + CA_);
        jsr_ = (jc_ = CM_ * jc_ + CA_);
        jc_  = CM_ * jc_ + CA_;
    }

    ~BaseGen() {;}

    BaseGen& operator=(const BaseGen& gen)
    {
        jc_  = gen();
        z_   = (jc_ = CM_ * jc_ + CA_);
        w_   = (jc_ = CM_ * jc_ + CA_);
        jsr_ = (jc_ = CM_ * jc_ + CA_);
        jc_  = CM_ * jc_ + CA_;
        return *this;
    }
    
    uint32_t operator()() const
    {
        z_    = MWC1_ * (z_ & MASK_) + (z_ >> 16);
        w_    = MWC2_ * (w_ & MASK_) + (w_ >> 16);
        jsr_ ^= (jsr_ << 17); jsr_ ^= (jsr_ >> 13); jsr_ ^= (jsr_ << 5);
        jc_   = CM_ * jc_ + CA_;
        return (((z_ << 16) + (w_ & MASK_)) ^ jc_) + jsr_;
    }
};


/******************************************************************************
 *
 * Generate uniformly distributed integers from a closed interval.
 *
 *****************************************************************************/

class IntGen
{
private:
    BaseGen  gen_;
    int32_t  low_;
    int32_t delta_;

public:
    explicit IntGen(uint32_t seed = 0, int32_t a = 0, int32_t b = 0) : gen_(seed)
    {
        if (a == b)
        {
            low_  = 0;
            delta_ = 1;
        }
        else
        {
            low_  = std::min(a, b);
            delta_ = VM::abs(b - a);
        }
    }

    IntGen(const IntGen& uni) : gen_(uni.gen_), low_(uni.low_),
        delta_(uni.delta_) {;}

    ~IntGen() {;}

    int32_t operator()() const
    {
        double x = gen_() * 2.328306e-10;
        return low_ + int32_t(x * (delta_ + 1));
    }

    IntGen& operator=(const IntGen& uni)
    {
        low_   = uni.low_;
        delta_ = uni.delta_;
        gen_   = uni.gen_;
        return *this;
    }

    void set_range(int32_t a = 0, int32_t b = 0)
    {
        if (a == b)
        {
            low_   = 0;
            delta_ = 1;
        }
        else
        {
            low_   = std::min(a, b);
            delta_ = VM::abs(b - a);
        }
        return;
    }

    int32_t low() const
    {
        return low_;
    }

    int32_t high() const
    {
        return low_ + delta_;
    }

    uint32_t base() const
    {
        return gen_();
    }
};


/******************************************************************************
 *
 *  Generate uniformly distributed doubles from an open interval.
 *
 *****************************************************************************/

class UniformGen
{
private:
    BaseGen gen_;
    double  mean_, delta_;

public:
    explicit UniformGen(uint32_t seed = 0, double a = 0, double b = 0) :
        gen_(seed)
    {
        if (a == b)
        {
            mean_  = 0.5;
            delta_ = 0.5;
        }
        else
        {
            mean_  = 0.5 * (b + a);
            delta_ = 0.5 * VM::abs(b - a);
        }
        delta_ *= 4.656613e-10;
    }

    UniformGen(const UniformGen& uni) : gen_(uni.gen_), mean_(uni.mean_),
        delta_(uni.delta_) {;}
    
    ~UniformGen() {;}

    double operator()() const
    {
        return mean_ + int32_t(gen_()) * delta_;
    }

    UniformGen& operator=(const UniformGen& uni)
    {
        mean_  = uni.mean_;
        delta_ = uni.delta_;
        gen_   = uni.gen_;
        return *this;
    }

    void set_range(double a = 0, double b = 0)
    {
        if (a == b)
        {
            mean_  = 0.5;
            delta_ = 0.5;
        }
        else
        {
            mean_  = 0.5 * (b + a);
            delta_ = 0.5 * VM::abs(b - a);
        }
        delta_ *= 4.656613e-10;
    }

    double low() const
    {
        return mean_ - delta_;
    }

    double high() const
    {
        return mean_ + delta_;
    }

    uint32_t base() const
    {
        return gen_();
    }
};


/******************************************************************************
 *
 *  Generate normally distributed doubles, using the Monty Python method.
 *
 *****************************************************************************/

class NormalGen
{
private:
    BaseGen gen_;
    double  mean_, stdv_;

public:
    explicit NormalGen(uint32_t seed = 0, double m = 0, double v = 1) :
        gen_(seed), mean_(m), stdv_(v)
    {
        if (stdv_ < 0) stdv_ = - stdv_;
    }

    NormalGen(const NormalGen& nrm) : gen_(nrm.gen_), mean_(nrm.mean_),
        stdv_(nrm.stdv_) {;}

    ~NormalGen() {;}

    double operator()() const
    {
        double x, y, v;
        x = int32_t(gen_()) * 1.167239e-9;
        if (VM::abs(x) < 1.17741) return mean_ + stdv_ * x;
        y = gen_() * 2.328306e-10;
        if (std::log(y) < (0.6931472 - 0.5 * x * x)) return mean_ + stdv_ * x;
        x = (x > 0) ? (0.8857913 * (2.506628 - x)) :
            (-0.8857913 * (2.506628 + x));
        if (std::log(1.8857913 - y) < (0.5718733 - 0.5 * x * x))
            return mean_ + stdv_ * x;
        do
        {
            v = int32_t(gen_()) * 4.656613e-10;
            x = -std::log(VM::abs(v)) * 0.3989423;
            y = -std::log(gen_() * 2.328306e-10);
        }
        while (y + y < x * x);
        return (v > 0) ? (mean_ + stdv_ * (2.506628 + x)) :
            (mean_ - stdv_ * (2.506628 + x));
    }

    NormalGen& operator=(const NormalGen& nrm)
    {
        mean_ = nrm.mean_;
        stdv_ = nrm.stdv_;
        gen_  = nrm.gen_;
        return *this;
    }

    void set_range(double m = 0, double v = 1.0)
    {
        mean_ = m;
        stdv_ = v;
        if (stdv_ < 0) stdv_ = -stdv_;
    }

    double mean() const
    {
        return mean_;
    }

    double stdv() const
    {
        return stdv_;
    }

    uint32_t base() const
    {
        return gen_();
    }
};

struct RanTools
{
    template <class RanIt>
    static void shuffle(RanIt a, RanIt b)
    {
        size_t len = b - a;
        IntGen gen(0, 0, len - 1);
        RanIt i, j;
        for (i = a; i < b; ++i)
        {
             j = a + gen();
             std::swap(*i, *j);
        }    
    }

    template <class ForIt, class G>
    static void assign(ForIt a, ForIt b, G gen)
    {
        for (ForIt i = a; i < b; ++i) *i = gen();
    }         

    template <class ForIt, class G>
    static void add(ForIt a, ForIt b, G gen)
    {
        for (ForIt i = a; i < b; ++i) *i += gen();
    }         

    template <class ForIt, class G>
    static void subtract(ForIt a, ForIt b, G gen)
    {
        for (ForIt i = a; i < b; ++i) *i -= gen();
    }         

    template <class ForIt, class G>
    static void multiply(ForIt a, ForIt b, G gen)
    {
        for (ForIt i = a; i < b; ++i) *i *= gen();
    }         

    template <class ForIt, class G>
    static void divide(ForIt a, ForIt b, G gen)    
    {
        for (ForIt i = a; i < b; ++i) *i /= gen();
    }         
};

} /* namespace VM */


#endif /* RANDOM_H */
