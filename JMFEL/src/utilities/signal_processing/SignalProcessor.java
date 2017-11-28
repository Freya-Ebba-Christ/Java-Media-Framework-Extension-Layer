package utilities.signal_processing;
public class SignalProcessor {
    
    //related to FFT and filtering
    public static int NOTCH = 2;
    public static int LOW_PASS = 4;
    public static int HIGH_PASS = 8;
    public static int BAND_PASS = 16;
    
    public native void fft(double[] signal,double[] fft);
    public native void ifft(double[] signal,double[] ifft);
    public native void newR2RPlan(int size);
    public native void freeR2RPlan();
    public native void newFilter(int filterIndex);
    public native void newFilter(int filterType,double samplerate, int order, double lowEdge, double highEdge, int filterIndex);
    public native void freeC2CPlan();
    public native void newC2CPlan(int size);
    public native void filterFilter(double[] signal, double[] filteredSignal, int filterIndex);
    public native void powerSpectrum(double[] signal, double[] powerSpectrum);
    public native void crossSpectrum(double[] signal_1, double[] signal_2, double[] realCrossSpectrum, double[] imaginaryCrossSpectrum);
    public native void hilbert(double[] signal, double[] realHilbert, double[] imaginaryHilbert);
    public native void spectra(double[] signal_1,double[] signal_2, double[] firstPowerSpectrum, double[]secondPowerSpectrum, double[] realCrossSpectrum, double[] imaginaryCrossSpectrum);
    public native void coherence(double[] signal_1,double[] signal_2,double[] coherenceSignal);
    public native void setFilterType(int filterType, int filterIndex);
    public native void setSampleRate(double sampleRate, int filterIndex);
    public native double getSampleRate(int filterIndex);
    public native void setOrder(int order, int filterIndex);
    public native int getOrder(int filterIndex);
    public native void setLow(double lw, int filterIndex);
    public native double getLow(int filterIndex);
    public native void setHigh(double high, int filterIndex);
    public native double getHigh(int filterIndex);
    public native int getNumNotches(int filterIndex);
    public native double getNotchFrequency(int notchindex, int filterIndex);
    public native double getNotchWidth(int notchindex, int filterIndex);
    public native void addNotch(double frequency, double width, int filterIndex);
    public native void editNotch(int notchindex,double frequency, double width, int filterIndex);
    public native void removeNotch(int notchindex, int filterIndex);
    public native void clearNotches(int filterIndex);
    public native void sortNotches(int filterIndex);
    public native double getVariance(double[] anArray);
    public native double getMean(double[] anArray);
    
    static {
        try{
            System.loadLibrary("SignalProcessor");
        }catch(Exception e){System.out.println(e);};
    }
    
}

class SignalProcessorTest {
    
    /** Creates a new instance of SignalProcessorTest */
    public SignalProcessorTest() {
    }
    
    public static void main(String[] args) {
        
        SignalProcessor aSignalProcessor = new SignalProcessor();
        SignalProcessor aSignalProcessor2 = new SignalProcessor();
        int samplerate = 128;
        double[] signal = new double[samplerate];
        double[] signal_1 = new double[samplerate];
        double[] signal_2 = new double[samplerate];
        double[] result = new double[samplerate];
        double[] realCrossSpectrum = new double[samplerate];
        double[] imaginaryCrossSpectrum = new double[samplerate];
        double[] firstPowerSpectrum = new double[samplerate];
        double[] secondPowerSpectrum = new double[samplerate];
        double[] realHilbert = new double[samplerate];
        double[] imaginaryHilbert = new double[samplerate];
        double[] coherenceSignal = new double[samplerate];
        
        //prepare plans used by fftw internally
        aSignalProcessor.newR2RPlan(signal.length);
        aSignalProcessor.newC2CPlan(signal.length);
        
        aSignalProcessor.fft(signal,result);
        aSignalProcessor.ifft(signal,result);
        aSignalProcessor.newFilter(0);
        aSignalProcessor.newFilter(SignalProcessor.NOTCH,50.0, 10, 45.0, 55.0,1);
        aSignalProcessor.filterFilter(signal, result, 1);
        //aSignalProcessor.powerSpectrum(signal, result);
        //aSignalProcessor.crossSpectrum(signal_1,signal_2,realCrossSpectrum,imaginaryCrossSpectrum);
        //aSignalProcessor.hilbert(signal,realHilbert,imaginaryHilbert);
        //aSignalProcessor.spectra(signal_1,signal_2,firstPowerSpectrum,secondPowerSpectrum,realCrossSpectrum,imaginaryCrossSpectrum);
        //aSignalProcessor.coherence(signal_1,signal_2,coherenceSignal);
        aSignalProcessor.getVariance(signal);
        aSignalProcessor.getMean(signal);
        
        double startTime = System.nanoTime();
        
        startTime = System.nanoTime();
        
        long t_start = System.currentTimeMillis();
        int cnt = 0;
        
        while(System.currentTimeMillis()-t_start<1000){
            aSignalProcessor.fft(signal,result);
            cnt++;
        }
        
        System.out.println("FFTs per second: "+cnt);
        
        t_start = System.currentTimeMillis();
        cnt = 0;
        
        while(System.currentTimeMillis()-t_start<1000){
            aSignalProcessor.ifft(signal,result);
            cnt++;
        }
        
        System.out.println("IFFTs per second: "+cnt);
        
        t_start = System.currentTimeMillis();
        cnt = 0;
        
        while(System.currentTimeMillis()-t_start<1000){
            aSignalProcessor.filterFilter(signal, result, 1);
            cnt++;
        }
        
        System.out.println("Filter operations per second: "+cnt);
        
        /*
        startTime = System.nanoTime();
        
        System.out.println("70000 powerSpectrum operations will take:");
        for (int x = 0;x<70000;x++){
            aSignalProcessor.powerSpectrum(signal, result);
        }
        System.out.println((System.nanoTime()-startTime)/1000000000 +" seconds");
        
        startTime = System.nanoTime();
        
        System.out.println("70000 crossSpectrum operations will take:");
        for (int x = 0;x<70000;x++){
            aSignalProcessor.crossSpectrum(signal_1,signal_2,realCrossSpectrum,imaginaryCrossSpectrum);
        }
        System.out.println((System.nanoTime()-startTime)/1000000000 +" seconds");
        
        startTime = System.nanoTime();
        
        System.out.println("70000 hilbert operations will take:");
        for (int x = 0;x<70000;x++){
            aSignalProcessor.hilbert(signal,realHilbert,imaginaryHilbert);
        }
        System.out.println((System.nanoTime()-startTime)/1000000000 +" seconds");
        
        startTime = System.nanoTime();
        
        System.out.println("70000 spectra operations will take:");
        for (int x = 0;x<70000;x++){
            aSignalProcessor.spectra(signal_1,signal_2,firstPowerSpectrum,secondPowerSpectrum,realCrossSpectrum,imaginaryCrossSpectrum);
        }
        System.out.println((System.nanoTime()-startTime)/1000000000 +" seconds");
        
        startTime = System.nanoTime();
        
        System.out.println("70000 coherence operations will take:");
        for (int x = 0;x<70000;x++){
            aSignalProcessor.coherence(signal_1,signal_2,coherenceSignal);
        }
        System.out.println((System.nanoTime()-startTime)/1000000000 +" seconds");
        
        startTime = System.nanoTime();
        
        */
        System.out.println("70000 variance operations will take:");
        for (int x = 0;x<70000;x++){
            aSignalProcessor.getVariance(signal);
        }
        System.out.println((System.nanoTime()-startTime)/1000000000 +" seconds");
        
        startTime = System.nanoTime();
        
        System.out.println("70000 mean operations will take:");
        for (int x = 0;x<70000;x++){
            aSignalProcessor.getMean(signal);
        }
        System.out.println((System.nanoTime()-startTime)/1000000000 +" seconds");
        
        startTime = System.nanoTime();
        
        aSignalProcessor.setFilterType(SignalProcessor.BAND_PASS,1);
        aSignalProcessor.setSampleRate(250.0, 1);
        System.out.println(aSignalProcessor.getSampleRate(1));
        aSignalProcessor.setOrder(5, 1);
        System.out.println(aSignalProcessor.getOrder(1));
        aSignalProcessor.setLow(40, 1);
        System.out.println(aSignalProcessor.getLow(1));
        aSignalProcessor.setHigh(60, 1);
        System.out.println(aSignalProcessor.getHigh(1));
        aSignalProcessor.addNotch(7.0, 3.0, 1);
        aSignalProcessor.editNotch(0,70, 13.0, 1);
        System.out.println(aSignalProcessor.getNumNotches(1));
        System.out.println(aSignalProcessor.getNotchFrequency(0,1));
        System.out.println(aSignalProcessor.getNotchWidth(0,1));
        aSignalProcessor.removeNotch(0, 1);
        aSignalProcessor.clearNotches(1);
        aSignalProcessor.sortNotches(1);
        // clean up plans used by fftw internally
        aSignalProcessor.freeR2RPlan();
        aSignalProcessor.freeC2CPlan();
    }
}