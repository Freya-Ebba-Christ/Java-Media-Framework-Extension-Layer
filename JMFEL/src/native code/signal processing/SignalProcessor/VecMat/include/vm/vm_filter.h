#ifndef VM_FILTER_H
#define VM_FILTER_H

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
 *  vm_filter.h - Filter class declaration and definition.
 *
 *  Include this header file if you wish to use the Filter class and support
 *  functions.
 *
 *****************************************************************************/

#include <sstream>
#include <vm/vec_mat.h>


namespace VM {

enum Filter_Type {NOTCH, LOW_PASS, HIGH_PASS, BAND_PASS};

const size_t	MAX_NOTCHES_ = 10;
const double    MAX_SAMP_RATE_ = 65536.0;


/******************************************************************************
 *
 *  Filter information class.
 *
 *****************************************************************************/

class Filter
{
private:
    Filter_Type     type_;
    size_t          order_;
    size_t          num_notches_;
    double          low_, high_, samp_rate_;
    Vector<double>  notch_freq_, notch_width_;

public:
    Filter() : type_(NOTCH), order_(4), num_notches_(0), low_(0.0),
        high_(0.0), samp_rate_(2.0), notch_freq_(MAX_NOTCHES_),
        notch_width_(MAX_NOTCHES_) {;}

    Filter(const Filter& f) : type_(f.type_), order_(f.order_),
        num_notches_(f.num_notches_), low_(f.low_), high_(f.high_),
        samp_rate_(f.samp_rate_), notch_freq_((f.notch_freq_).copy()),
        notch_width_((f.notch_width_).copy()) {;}

    Filter(Filter_Type t, double sr = 2, size_t o = 4, double e1 = 0.0,
        double e2 = 0) : type_(t), order_(o), num_notches_(0), low_(0.0),
        high_(0.0), samp_rate_(sr), notch_freq_(MAX_NOTCHES_),
        notch_width_(MAX_NOTCHES_)
    {
        if (samp_rate_ <= 0.0 || samp_rate_ > MAX_SAMP_RATE_) samp_rate_ = 2.0;
        if (o < 1 || o > 64) o = 4;
        switch (type_)
        {
        case LOW_PASS:
            if (e1 > 0.0 && e1 < samp_rate_ / 2.0) high_ = e1;
            else high_ = samp_rate_ / 4.0;
            break;
        case HIGH_PASS:
            if (e1 > 0.0 && e1 < samp_rate_ / 2.0) low_ = e1;
            else low_ = samp_rate_ / 4.0;
            break;
        case BAND_PASS:
            if (e1 > 0.0 && e2 > 0.0 && e1 < e2 && e2 < samp_rate_ / 2.0)
            {
                low_  = e1;
                high_ = e2;
            }
            else
            {
                low_  = samp_rate_ / 8.0;
                high_ = samp_rate_ / 4.0;
            }
            break;
        case NOTCH:
            break;
        }
    }

    ~Filter() {;}

    Filter& operator=(const Filter& f)
    {
        type_        = f.type_;
        order_       = f.order_;
        num_notches_ = f.num_notches_;
        low_         = f.low_;
        high_        = f.high_;
        samp_rate_   = f.samp_rate_;
        notch_freq_  = f.notch_freq_;
        notch_width_ = f.notch_width_;
        return *this;
    }

    Filter_Type type() const {return type_;}

    void type(Filter_Type t)
    {
        type_ = t;
        switch (type_)
        {
        case NOTCH:
            low_ = 0.0;
            high_ = 0.0;
            break;
        case LOW_PASS:
            low_ = 0;
            if (high_ == 0.0) high_ = samp_rate_ / 4.0;
            break;
        case HIGH_PASS:
            high_ = 0.0;
            if (low_ == 0.0) low_ = samp_rate_ / 4.0;
            break;
        case BAND_PASS:
            if (low_ == 0.0 && high_ == 0.0)
            {
                low_  = samp_rate_ / 8.0;
                high_ = samp_rate_ / 4.0;
            }
            else if (high_ == 0.0)
                high_ = low_ / 2.0 + samp_rate_ / 4.0;
            else
                low_ = high_ / 2.0;
            break;
        }
        return;
    }

    size_t order() const {return order_;}

    bool order(size_t o)
    {
        if (o > 0 && o < 65)
        {
            order_ = o;
            return true;
        }
        else return false;
    }

    size_t num_notches() const {return num_notches_;}

    double low() const {return low_;}

    bool low(double l)
    {
        if ((type_ == HIGH_PASS || type_ == BAND_PASS) && l > 0.0 &&
            l < samp_rate_ / 2.0)
        {
            low_ = l;
            if (low_ >= high_ && type_ == BAND_PASS)
                high_ = low_ / 2.0 + samp_rate_ / 4.0;
            return true;
        }
        else return false;
    }

    double high() const {return high_;}

    bool high(double h)
    {
        if ((type_ == LOW_PASS || type_ == BAND_PASS) && h > 0.0 &&
            h < samp_rate_ / 2.0)
        {
            high_ = h;
            if (high_ <= low_ && type_ == BAND_PASS) low_ = high_ / 2.0;
            return true;
        }
        else return false;
    }

    double samp_rate() const {return samp_rate_;}

    bool samp_rate(double sr)
    {
        if (sr > 0.0 && sr <= MAX_SAMP_RATE_)
        {
            double temp = sr / samp_rate_;
            samp_rate_ = sr;
            low_ *= temp;
            high_ *= temp;
            notch_freq_ *= temp;
            notch_width_ *= temp;
            return true;
        }
        else return false;
    }

    double notch_freq(size_t i) const
    {
        if (i >= num_notches_)
            vmerror("Filter notches indexed out of bounds.");
        return notch_freq_[i];
    }

    double notch_width(size_t i) const
    {
        if (i >= num_notches_)
            vmerror("Filter notches indexed out of bounds.");
        return notch_width_[i];
    }

    bool add_notch(double f, double w)
    {
        if (num_notches_ < MAX_NOTCHES_ && f >= 0.0 && f < samp_rate_ / 2.0 &&
            w > 0.0 && w < samp_rate_ / 4.0)
        {
            notch_freq_[num_notches_] = f;
            notch_width_[num_notches_] = w;
            ++num_notches_;
            return true;
        }
        else return false;
    }

    bool edit_notch(size_t i, double f, double w)
    {
        if (num_notches_ < MAX_NOTCHES_ && f >= 0 && f < samp_rate_ / 2.0 &&
            w > 0.0 && w < samp_rate_ / 4.0 && i < num_notches_)
        {
            notch_freq_[i] = f;
            notch_width_[i] = w;
            return true;
        }
        else return false;
    }

    bool remove_notch(size_t i)
    {
        if (i >= num_notches_) return false;
        --num_notches_;
        notch_freq_[i] = notch_freq_[num_notches_];
        notch_width_[i] = notch_width_[num_notches_];
        notch_freq_[num_notches_] = 0.0;
        notch_width_[num_notches_] = 0.0;
        return true;
    }

    void clear_notches()
    {
        num_notches_ = 0;
        notch_freq_  = 0.0;
        notch_width_ = 0.0;
    }

    void sort_notches()
    {
        if (!num_notches_) return;
        double mf, temp;
        unsigned int i, j, k;
        for (i = 1; i < num_notches_; ++i)
        {
            k = i - 1;
            mf = notch_freq_[k];
            for (j = i; j < num_notches_; ++j)
            {
                if (notch_freq_[j] < mf)
                {
                    mf = notch_freq_[j];
                    k = j;
                }
            }
            temp = notch_freq_[i-1];
            notch_freq_[i-1] = notch_freq_[k];
            notch_freq_[k] = temp;
            temp = notch_width_[i-1];
            notch_width_[i-1] = notch_width_[k];
            notch_width_[k] = temp;
        }
    }

    void make_filter(Vector<double>& power, double low_freq = 0.0,
        double high_freq = 0.0) const
    {
        size_t  i, j, len;
        double  nyq, omega, n_fac, delta, sigma, norm, temp;

        n_fac = std::sqrt(std::log(2.0));

        len = power.size();

        nyq = samp_rate_ / 2.0;

        if (high_freq <= low_freq || high_freq > samp_rate_ || low_freq < 0.0)
        {
            low_freq  = 0.0;
            high_freq = samp_rate_;
        }

        delta = (high_freq - low_freq) / len;
        Vector<double> freq(low_freq, delta, len);    

        switch (type_)
        {
        case NOTCH: /* Flat filter */
            power = 1.0;
            break;
        
        case LOW_PASS: /* Butterworth low-pass filter */
            for (i = 0; i < len; ++i)
            {
                if (freq[i] > nyq) temp = samp_rate_ - freq[i];
                else temp = freq[i];
                omega = temp / high_;
                power[i] = 1.0 / (1.0 + std::pow(omega, 2.0 * order_));
            }
            break;
        
        case HIGH_PASS: /* Butterworth high-pass filter */
            if (freq[0] == 0.0)
            {
                power[0] = 0.0;
                j = 1;
            }
            else j = 0;
            for (i = j; i < len; ++i)
            {
                if (freq[i] > nyq)    temp = samp_rate_ - freq[i];
                else temp = freq[i];
                omega = low_ / temp;
                power[i] = 1.0 / (1.0 + std::pow(omega, 2.0 * order_));
            }
            break;    

        case BAND_PASS: /* Butterworth band-pass filter */
            omega = (2.0 * high_ * low_) / (std::sqrt(high_ * low_) * (high_ - low_));
            norm = 1.0 + std::pow(omega, 2.0 * order_);
            if (freq[0] == 0.0)
            {
                power[0] = 0.0;
                j = 1;
            }
            else j = 0;
            for (i = j; i < len; ++i)
            {
                if (freq[i] > nyq) temp = samp_rate_ - freq[i];
                else temp = freq[i];
                omega = (temp * temp + high_ * low_) / (temp * (high_ - low_));
                power[i] = norm / (1.0 + std::pow(omega, 2.0 * order_));
            }
            break;
        }

        for (j = 0; j < num_notches_; ++j)
        {
            sigma = n_fac * notch_width_[j] / 2.0;
            for (i = 0; i < len; i++)
            {
                if (freq[i] > nyq) temp = samp_rate_ - freq[i];
                else temp = freq[i];
                omega = temp - notch_freq_[j];
                if (omega == 0.0) power[i] = 0.0;
                else
                {
                    omega = sigma / omega;
                    omega *= omega;
                    power[i] *= std::exp(-omega);
                }
            }
        }
    }

    bool read(const std::string& parameters)
    {
        using namespace std;
        size_t i, k, n, ord;
        bool   f_o, f_s, f_l, f_h;
        double sr, le, he;
        Vector<double> nf(MAX_NOTCHES_), nw(MAX_NOTCHES_);
        const size_t MAX_LINES = 256;
        const double DEFAULT_WIDTH = 6.0;
        
        Vector<string> lines(MAX_LINES);
        string    token;

        f_o   = false;
        f_s   = false;
        f_l   = false;
        f_h   = false;
        k     = 0;

        ifstream fp(parameters.c_str());
        if (!fp.is_open()) return false;

        *this = Filter();

        i = 0;
        while (std::getline(fp, lines[i]) && i < MAX_LINES - 1) ++i;

        fp.close();

        n = i;    
        for (i = 0; i < n; ++i)
        {
            if (lines[i][0] != '#')
            {
                istringstream line(lines[i]);    

                while(line >> token)
                {
                    if (token == "filter_order")
                    {
                        line >> ord;
                        if (line) f_o = true;
                    }
                    else if (token == "low_edge")
                    {
                        line >> le;
                        if (line) f_l = true;
                    }
                    else if (token == "high_edge")
                    {
                        line >> he;
                        if (line) f_h = true;
                    }
                    else if (token == "sampling_rate")
                    {
                        line >> sr;
                        if (line) f_s = true;
                    }
                    else if (token == "notch")
                    {
                        if (k >= MAX_NOTCHES_) ++k;
                        else
                        {
                            line >> nf[k];
                            if (line)
                            {
                                line >> nw[k];
                                if (!line) nw[k] = DEFAULT_WIDTH;
                                ++k;
                            }
                        }
                    }
                }
            }
        }
        
        if (f_s) samp_rate(sr);
    
        for (i = 0; i < k; ++i)
            add_notch(nf[i], nw[i]);
    
        if (f_l)
        {
            if (f_h) type(BAND_PASS);
            else type(HIGH_PASS);
        }
        else if (f_h) type(LOW_PASS);

        if (f_o) order(ord);

        if (f_l) low(le);

        if (f_h) high(he);

        if (k > MAX_NOTCHES_) return false;
        return true;
    }

    void write(const std::string& parameters) const
    {
        using namespace std;
        size_t numlines = num_notches_ + 4, i;
        Vector<string> lines(numlines);

        ostringstream line;

        line << "sampling_rate\t\t" << samp_rate_;
        lines[0] = line.str();

        line.seekp(0, ios::beg);
        line.str("");
        line << "filter_order\t\t" << order_;
        lines[1] = line.str();

        line.seekp(0, ios::beg);
        line.str("");
        if (type_ < 2) line << '#';
        line << "low_edge\t\t" << low_;
        lines[2] = line.str();

        line.seekp(0, ios::beg);
        line.str("");
        if (type_ == 0 || type_ == 2) line << '#';
        line << "high_edge\t\t" << high_;
        lines[3] = line.str();

        for (i = 0; i < num_notches_; ++i)
        {
            line.seekp(0, ios::beg);
            line.str("");
            line << "notch\t\t";
            line << notch_freq_[i] << '\t' << notch_width_[i];
            lines[i + 4] = line.str();
        }

        ofstream out(parameters.c_str());
        lines.write(out);
        out << '\n';
        out.close();
    }

    size_t estimate_padded_size(size_t len) const
    {
        size_t j, pad = 0;
        double temp;
        switch (type_)
        {
        case NOTCH:
            pad = 0;
            break;
        case LOW_PASS:
            pad = size_t(samp_rate_ / high_);
            break;
        case HIGH_PASS:
            pad = size_t(samp_rate_ / low_);
            break;
        case BAND_PASS:
            pad = size_t(samp_rate_ / (high_ - low_));
            j   = size_t(samp_rate_ / low_);
            pad  = (pad > j) ? pad : j;
            break;
        }
        for (j = 0; j < num_notches_; ++j)
        {
            temp = samp_rate_ / notch_width_[j];
            if (temp > pad) pad = size_t(temp);
        }
        len += pad;
        
        return len;
    }
};

} /* namespace VM */


#endif /* VM_FILTER_H */
