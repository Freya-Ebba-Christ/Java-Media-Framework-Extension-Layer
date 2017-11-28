#include "utilities_signal_processing_SignalProcessor.h"
#include <hash_map>
#include <string.h>
#include <vm/fftw_allocator.h>
#include <vm/vm_fftw3.h>
#include <vm/vm_filter.h>
#include <vm/vm_basic.h>
#include <vm/vec_mat.h>
#include <jni.h>
using namespace VM;

bool initialized = false;
//define structures and initialize them with a dummy size
Filter aFilter;
FFTPlan currentR2RFFTPlan;
FFTPlan currentC2CFFTPlan;
Vec_DP realInData(1024);
Vec_DP realOutData(1024);
Vec_CPLX_DP complexInData(1024);
Vec_CPLX_DP complexOutData(1024);
using namespace std;

//define object pairs
typedef pair <const long, Filter> Object_Pair;
typedef pair <int, Filter_Type> FilterType_Pair;

//define containers to store the object pairs defined above
hash_map<long,Filter> filterContainer;

// the filterTypeContainer is used to define a mapping 
// between a numerical value and a symbolic name e.g. NOTCH. 
hash_map<int,Filter_Type> filterTypeContainer;

void initialize(){
	
	filterTypeContainer.insert(FilterType_Pair(2,NOTCH));
	filterTypeContainer.insert(FilterType_Pair(4,LOW_PASS));
	filterTypeContainer.insert(FilterType_Pair(8,HIGH_PASS));
	filterTypeContainer.insert(FilterType_Pair(16,BAND_PASS));
	initialized = true;
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_fft (JNIEnv *env , jobject obj , jdoubleArray signalArray, jdoubleArray fftArray){
	
	double *double_signal = (double*) env->GetPrimitiveArrayCritical(signalArray,0);
	double *double_fft = (double*) env->GetPrimitiveArrayCritical(fftArray,0);
	int size = env->GetArrayLength(signalArray);
	double *doublePtr_signal = double_signal;
	double *doublePtr_fft = double_fft;
	Vec_DP signalVector;
	Vec_DP fftVector;

	signalVector.view(doublePtr_signal, size);

	fftVector.view(doublePtr_fft, size);

	copy(signalVector.begin(), signalVector.end(), realInData.begin());
	
	//do in-place-transformation
	currentR2RFFTPlan.fourier();

	copy(realOutData.begin(), realOutData.end(), fftVector.begin());

	env->ReleasePrimitiveArrayCritical(signalArray,double_signal,0);
	env->ReleasePrimitiveArrayCritical(fftArray,double_fft,0);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_ifft (JNIEnv *env, jobject obj, jdoubleArray signalArray, jdoubleArray ifftArray){

	double *double_signal = (double*) env->GetPrimitiveArrayCritical(signalArray,0);
	double *double_ifft = (double*) env->GetPrimitiveArrayCritical(ifftArray,0);
	int size = env->GetArrayLength(signalArray);
	double *doublePtr_signal = double_signal;
	double *doublePtr_ifft = double_ifft;

	Vec_DP signalVector;
	Vec_DP ifftVector;

	signalVector.view(doublePtr_signal, size);
	ifftVector.view(doublePtr_ifft, size);

	copy(signalVector.begin(), signalVector.end(), realOutData.begin());
	
	//do in-place-transformation
	currentR2RFFTPlan.ifourier();

	copy(realInData.begin(), realInData.end(), ifftVector.begin());

	env->ReleasePrimitiveArrayCritical(signalArray,double_signal,0);
	env->ReleasePrimitiveArrayCritical(ifftArray,double_ifft,0);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_newR2RPlan(JNIEnv *env, jobject obj, jint size){
	
	realInData.resize(size);
	realOutData.resize(size);
	currentR2RFFTPlan = FFTPlan(realInData,realOutData, BIDIR);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_freeR2RPlan(JNIEnv *env, jobject obj){
	
	currentR2RFFTPlan.free();
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_newFilter__I(JNIEnv *env, jobject obj, jint filterIndex){
	//create new filter object and store it in a hash map for fast access.

	aFilter = Filter();
	filterContainer.erase(filterIndex);
	filterContainer.insert(Object_Pair(filterIndex,aFilter));
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_newFilter__IDIDDI(JNIEnv *env, jobject obj, jint filterType, jdouble samplerate, jint order, jdouble lowEdge, jdouble highEdge, jint filterIndex){
	
	if(!initialized){
		initialize();
	}
	//create new filter object and store it in a hash map for fast access.
	aFilter = Filter();
	aFilter.type(filterTypeContainer[filterType]);
	aFilter.samp_rate(samplerate);
	aFilter.order(order);
	aFilter.low(lowEdge);
	aFilter.high(highEdge);
	filterContainer.erase(filterIndex);
	filterContainer.insert(Object_Pair(filterIndex,aFilter));
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_freeC2CPlan(JNIEnv *env, jobject obj){
	
	currentC2CFFTPlan.free();
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_newC2CPlan(JNIEnv *env, jobject obj, jint size){
	
	complexInData.resize(size);
	complexOutData.resize(size);
	currentC2CFFTPlan = FFTPlan(complexInData,complexOutData, BIDIR);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_filterFilter (JNIEnv *env, jobject obj, jdoubleArray signalArray, jdoubleArray filteredSignalArray, jint filterIndex){
	
	double *double_signal = (double*) env->GetPrimitiveArrayCritical(signalArray,0);
	double *double_filteredSignal = (double*) env->GetPrimitiveArrayCritical(filteredSignalArray,0);
	int size = env->GetArrayLength(signalArray);
	double *doublePtr_signal = double_signal;
	double *doublePtr_filteredSignal = double_filteredSignal;
	
	Vec_DP signalVector;
	Vec_DP filteredSignalVector;	
	signalVector.view(doublePtr_signal, size);
	filteredSignalVector.view(doublePtr_filteredSignal, size);

	currentR2RFFTPlan.filter(signalVector, filteredSignalVector, filterContainer[filterIndex]);

	env->ReleasePrimitiveArrayCritical(signalArray,double_signal,0);
	env->ReleasePrimitiveArrayCritical(filteredSignalArray,double_filteredSignal,0);
}

JNIEXPORT jdouble JNICALL Java_utilities_signal_1processing_SignalProcessor_getVariance(JNIEnv *env, jobject obj, jdoubleArray signalArray){
	
	double *double_signal = (double*) env->GetPrimitiveArrayCritical(signalArray,0);
	int size = env->GetArrayLength(signalArray);
	double *doublePtr_signal = double_signal;
	jdouble var = 0.0;

	Vec_DP signalVector;
	signalVector.view(doublePtr_signal, size);
	var = variance(signalVector);
	env->ReleasePrimitiveArrayCritical(signalArray,double_signal,0);
	return var;
}

JNIEXPORT jdouble JNICALL Java_utilities_signal_1processing_SignalProcessor_getMean(JNIEnv *env, jobject obj, jdoubleArray signalArray){
	
	double *double_signal = (double*) env->GetPrimitiveArrayCritical(signalArray,0);
	int size = env->GetArrayLength(signalArray);
	double *doublePtr_signal = double_signal;
	jdouble meanValue = 0.0;

	Vec_DP signalVector;
	signalVector.view(doublePtr_signal, size);
	meanValue = mean(signalVector);
	env->ReleasePrimitiveArrayCritical(signalArray,double_signal,0);
	return meanValue;
}


JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_powerSpectrum  (JNIEnv *env, jobject obj, jdoubleArray signalArray, jdoubleArray powerSpectrumArray){
	
	double *double_signal = (double*) env->GetPrimitiveArrayCritical(signalArray,0);
	double *double_powerSpectrum = (double*) env->GetPrimitiveArrayCritical(powerSpectrumArray,0);
	int size = env->GetArrayLength(signalArray);
	double *doublePtr_signal = double_signal;
	double *doublePtr_powerSpectrum = double_powerSpectrum;
	
	Vec_DP signalVector;
	Vec_DP powerSpectrumVector;	
	signalVector.view(doublePtr_signal, size);
	powerSpectrumVector.view(doublePtr_powerSpectrum, size);
	
	//TO BE OPTIMIZED!!!
	//PRCOMPUTE FFT PLAN
	//FIX THIS WHEN MOVING TO VECMAT 2.x 
    Vector<std::complex<double> > in(powerSpectrumVector.size()), out(powerSpectrumVector.size());
	FFTPlan fft(in, out, FORWARD);
	
	fft.power(signalVector, powerSpectrumVector);

	env->ReleasePrimitiveArrayCritical(signalArray,double_signal,0);
	env->ReleasePrimitiveArrayCritical(powerSpectrumArray,double_powerSpectrum,0);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_crossSpectrum (JNIEnv *env, jobject obj, jdoubleArray signalArray_1, jdoubleArray signalArray_2, jdoubleArray realCrossSpectrumArray, jdoubleArray imaginaryCrossSpectrumArray){
	
	double *double_signal_1 = (double*) env->GetPrimitiveArrayCritical(signalArray_1,0);
	double *double_signal_2 = (double*) env->GetPrimitiveArrayCritical(signalArray_2,0);
	double *double_realCrossSpectrum = (double*) env->GetPrimitiveArrayCritical(realCrossSpectrumArray,0);
	double *double_imaginaryCrossSpectrum = (double*) env->GetPrimitiveArrayCritical(imaginaryCrossSpectrumArray,0);
	
	int size = env->GetArrayLength(signalArray_1);
	
	double *doublePtr_signal_1 = double_signal_1;
	double *doublePtr_signal_2 = double_signal_2;
	double *doublePtr_realCrossSpectrum = double_realCrossSpectrum;
	double *doublePtr_imaginaryCrossSpectrum = double_imaginaryCrossSpectrum;
	
	Vec_DP signalVector_1;
	Vec_DP signalVector_2;
	Vec_DP realCrossSpectrumSpectrumVector;
	Vec_DP imaginaryCrossSpectrumVector;

	signalVector_1.view(doublePtr_signal_1, size);
	signalVector_2.view(doublePtr_signal_2, size);
	realCrossSpectrumSpectrumVector.view(doublePtr_realCrossSpectrum, size);
	imaginaryCrossSpectrumVector.view(doublePtr_imaginaryCrossSpectrum, size);
	
	Vec_CPLX_DP crossSpectrumVector(size);

	//TO BE OPTIMIZED!!!
	//PRCOMPUTE FFT PLAN
	//FIX THIS WHEN MOVING TO VECMAT 2.x 
	Vector<std::complex<double> > in(crossSpectrumVector.size()), out(crossSpectrumVector.size());
	FFTPlan fft(in, out, FORWARD);
	fft.cross_spectrum(signalVector_1,signalVector_2,crossSpectrumVector);
	
	copy(crossSpectrumVector.real().begin(), crossSpectrumVector.real().end(), realCrossSpectrumSpectrumVector.begin());
	copy(crossSpectrumVector.imag().begin(), crossSpectrumVector.imag().end(), imaginaryCrossSpectrumVector.begin());
    
	env->ReleasePrimitiveArrayCritical(signalArray_1,double_signal_1,0);
	env->ReleasePrimitiveArrayCritical(signalArray_2,double_signal_2,0);
	env->ReleasePrimitiveArrayCritical(realCrossSpectrumArray,double_realCrossSpectrum,0);
	env->ReleasePrimitiveArrayCritical(imaginaryCrossSpectrumArray,double_imaginaryCrossSpectrum,0);
}


JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_hilbert (JNIEnv *env, jobject obj, jdoubleArray signalArray, jdoubleArray realHilbertSignalArray, jdoubleArray imaginaryHilbertSignalArray){

	double *double_signal = (double*) env->GetPrimitiveArrayCritical(signalArray,0);
	double *double_realHilbertSignal = (double*) env->GetPrimitiveArrayCritical(realHilbertSignalArray,0);
	double *double_imaginaryHilbertSignal = (double*) env->GetPrimitiveArrayCritical(imaginaryHilbertSignalArray,0);

	int size = env->GetArrayLength(signalArray);
	
	double *doublePtr_signal = double_signal;
	double *doublePtr_realHilbertSignal = double_realHilbertSignal;
	double *doublePtr_imaginaryHilbertSignal = double_imaginaryHilbertSignal;

	Vec_DP signalVector;
	Vec_DP realHilbertSignalVector;
	Vec_DP imaginaryHilbertSignal;

	signalVector.view(doublePtr_signal, size);
	realHilbertSignalVector.view(doublePtr_realHilbertSignal, size);
	imaginaryHilbertSignal.view(doublePtr_imaginaryHilbertSignal, size);

	Vec_CPLX_DP hilbertSignalVector(size);
	
	currentC2CFFTPlan.hilbert(signalVector,hilbertSignalVector);
	
	copy(hilbertSignalVector.real().begin(), hilbertSignalVector.real().end(), realHilbertSignalVector.begin());
	copy(hilbertSignalVector.imag().begin(), hilbertSignalVector.imag().end(), imaginaryHilbertSignal.begin());
	
	env->ReleasePrimitiveArrayCritical(signalArray,double_signal,0);
	env->ReleasePrimitiveArrayCritical(realHilbertSignalArray,double_realHilbertSignal,0);
	env->ReleasePrimitiveArrayCritical(imaginaryHilbertSignalArray,double_imaginaryHilbertSignal,0);
}



JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_spectra (JNIEnv *env, jobject obj, jdoubleArray signalArray_1, jdoubleArray signalArray_2, jdoubleArray powerspectrumSignalArray_1, jdoubleArray powerspectrumSignalArray_2, jdoubleArray realCrossSpectrumSignalArray, jdoubleArray imaginaryCrossSpectrumSignalArray){

	double *double_signal_1 = (double*) env->GetPrimitiveArrayCritical(signalArray_1,0);
	double *double_signal_2 = (double*) env->GetPrimitiveArrayCritical(signalArray_2,0);
	double *double_powerspectrumSignal_1 = (double*) env->GetPrimitiveArrayCritical(powerspectrumSignalArray_1,0);
	double *double_powerspectrumSignal_2 = (double*) env->GetPrimitiveArrayCritical(powerspectrumSignalArray_2,0);
	double *double_realCrossSpectrumSignal = (double*) env->GetPrimitiveArrayCritical(realCrossSpectrumSignalArray,0);
	double *double_imaginaryCrossSpectrumSignal = (double*) env->GetPrimitiveArrayCritical(imaginaryCrossSpectrumSignalArray,0);

	int size = env->GetArrayLength(signalArray_1);

	double *doublePtr_signal_1 = double_signal_1;
	double *doublePtr_signal_2 = double_signal_2;
	double *doublePtr_powerspectrumSignal_l = double_powerspectrumSignal_1;
	double *doublePtr_powerspectrumSignal_2 = double_powerspectrumSignal_2;
	double *doublePtr_realCrossSpectrumSignal = double_realCrossSpectrumSignal;
	double *doublePtr_imaginaryCrossSpectrumSignal = double_imaginaryCrossSpectrumSignal;

	Vec_DP signalVector_1;
	Vec_DP signalVector_2;
	Vec_DP powerspectrumSignalVector_1;
	Vec_DP powerspectrumSignalVector_2;
	Vec_DP realCrossSpectrumSignalVector;
	Vec_DP imaginaryCrossSpectrumSignalVector;

	signalVector_1.view(doublePtr_signal_1, size);
	signalVector_2.view(doublePtr_signal_2, size);
	powerspectrumSignalVector_1.view(doublePtr_powerspectrumSignal_l, size);
	powerspectrumSignalVector_2.view(doublePtr_powerspectrumSignal_2, size);
	realCrossSpectrumSignalVector.view(doublePtr_realCrossSpectrumSignal, size);
	imaginaryCrossSpectrumSignalVector.view(doublePtr_imaginaryCrossSpectrumSignal, size);
	
	Vec_CPLX_DP crossSpectrumVector(size);
	
	//TO BE OPTIMIZED!!!
	//PRCOMPUTE FFT PLAN
	//FIX THIS WHEN MOVING TO VECMAT 2.x 
    Vector<std::complex<double> > in(powerspectrumSignalVector_1.size()), out(powerspectrumSignalVector_1.size());
	FFTPlan fft(in, out, FORWARD);
	fft.spectra(signalVector_1,signalVector_2,powerspectrumSignalVector_1, powerspectrumSignalVector_2, crossSpectrumVector);

	copy(crossSpectrumVector.real().begin(), crossSpectrumVector.real().end(), realCrossSpectrumSignalVector.begin());
	copy(crossSpectrumVector.imag().begin(), crossSpectrumVector.imag().end(), imaginaryCrossSpectrumSignalVector.begin());

	env->ReleasePrimitiveArrayCritical(signalArray_1,double_signal_1,0);
	env->ReleasePrimitiveArrayCritical(signalArray_2,double_signal_2,0);
	env->ReleasePrimitiveArrayCritical(powerspectrumSignalArray_1,double_powerspectrumSignal_1,0);
	env->ReleasePrimitiveArrayCritical(powerspectrumSignalArray_2,double_powerspectrumSignal_2,0);
	env->ReleasePrimitiveArrayCritical(realCrossSpectrumSignalArray,double_realCrossSpectrumSignal,0);
	env->ReleasePrimitiveArrayCritical(imaginaryCrossSpectrumSignalArray,double_imaginaryCrossSpectrumSignal,0);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_coherence(JNIEnv *env, jobject obj, jdoubleArray signalArray_1, jdoubleArray signalArray_2, jdoubleArray coherenceSignalArray){

	double *double_signal_1 = (double*) env->GetPrimitiveArrayCritical(signalArray_1,0);
	double *double_signal_2 = (double*) env->GetPrimitiveArrayCritical(signalArray_2,0);
	double *double_coherenceSignal = (double*) env->GetPrimitiveArrayCritical(coherenceSignalArray,0);

	int size = env->GetArrayLength(signalArray_1);

	double *doublePtr_signal_1 = double_signal_1;
	double *doublePtr_signal_2 = double_signal_2;
	double *doublePtr_coherenceSignal = double_coherenceSignal;

	Vec_DP signalVector_1;
	Vec_DP signalVector_2;
	Vec_DP coherenceSignalVector;

	signalVector_1.view(doublePtr_signal_1, size);
	signalVector_2.view(doublePtr_signal_2, size);
	coherenceSignalVector.view(doublePtr_coherenceSignal, size);

	//TO BE OPTIMIZED!!!
	//PRCOMPUTE FFT PLAN
	//FIX THIS WHEN MOVING TO VECMAT 2.x 
    Vector<std::complex<double> > in(coherenceSignalVector.size()), out(coherenceSignalVector.size());
	FFTPlan fft(in, out, FORWARD);

	fft.coherence(signalVector_1,signalVector_2,coherenceSignalVector);
	
	env->ReleasePrimitiveArrayCritical(signalArray_1,double_signal_1,0);
	env->ReleasePrimitiveArrayCritical(signalArray_2,double_signal_2,0);
	env->ReleasePrimitiveArrayCritical(coherenceSignalArray,double_coherenceSignal,0);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_setFilterType(JNIEnv *env, jobject obj, jint filterType, jint filterIndex){
	if(!initialized){
		initialize();
	}
	filterContainer[filterIndex].type(filterTypeContainer[filterType]);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_setSampleRate(JNIEnv *env, jobject obj, jdouble sampleRate, jint filterIndex){
	filterContainer[filterIndex].samp_rate(sampleRate);
}

JNIEXPORT jdouble JNICALL Java_utilities_signal_1processing_SignalProcessor_getSampleRate(JNIEnv *env, jobject obj, jint filterIndex){
	return (jdouble) filterContainer[filterIndex].samp_rate();
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_setOrder(JNIEnv *env, jobject obj, jint order, jint filterIndex){
	filterContainer[filterIndex].order(order);
}

JNIEXPORT jint JNICALL Java_utilities_signal_1processing_SignalProcessor_getOrder(JNIEnv *env, jobject obj, jint filterIndex){
	return (jint) filterContainer[filterIndex].order();
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_setLow(JNIEnv *env, jobject obj, jdouble lowEdge, jint filterIndex){
	filterContainer[filterIndex].low(lowEdge);
}

JNIEXPORT jdouble JNICALL Java_utilities_signal_1processing_SignalProcessor_getLow(JNIEnv *env, jobject obj, jint filterIndex){
	return (jdouble) filterContainer[filterIndex].low();
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_setHigh(JNIEnv *env, jobject obj, jdouble highEdge, jint filterIndex){
	filterContainer[filterIndex].high(highEdge);
}

JNIEXPORT jdouble JNICALL Java_utilities_signal_1processing_SignalProcessor_getHigh(JNIEnv *env, jobject obj, jint filterIndex){
	return (jdouble) filterContainer[filterIndex].high();
}

JNIEXPORT jint JNICALL Java_utilities_signal_1processing_SignalProcessor_getNumNotches(JNIEnv *env, jobject obj, jint filterIndex){
	return (jint) filterContainer[filterIndex].num_notches();
}

JNIEXPORT jdouble JNICALL Java_utilities_signal_1processing_SignalProcessor_getNotchFrequency(JNIEnv *env, jobject obj, jint notchindex, jint filterIndex){
	return (jdouble) filterContainer[filterIndex].notch_freq(notchindex);
}

JNIEXPORT jdouble JNICALL Java_utilities_signal_1processing_SignalProcessor_getNotchWidth(JNIEnv *env, jobject obj, jint notchindex, jint filterIndex){
	return (jdouble) filterContainer[filterIndex].notch_width(notchindex);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_addNotch(JNIEnv *env, jobject obj, jdouble frequency, jdouble width, jint filterIndex){
	filterContainer[filterIndex].add_notch(frequency, width);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_editNotch(JNIEnv *env, jobject obj, jint notchindex, jdouble frequency, jdouble width, jint filterIndex){
	filterContainer[filterIndex].edit_notch(notchindex,frequency,width);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_removeNotch(JNIEnv *env, jobject obj, jint notchindex, jint filterIndex){
	filterContainer[filterIndex].remove_notch(notchindex);
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_clearNotches(JNIEnv *env, jobject obj, jint filterIndex){
	filterContainer[filterIndex].clear_notches();
}

JNIEXPORT void JNICALL Java_utilities_signal_1processing_SignalProcessor_sortNotches(JNIEnv *env, jobject obj, jint filterIndex){
	filterContainer[filterIndex].sort_notches();
}