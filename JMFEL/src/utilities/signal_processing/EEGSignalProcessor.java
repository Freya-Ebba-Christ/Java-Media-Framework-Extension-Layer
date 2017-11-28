package utilities.signal_processing;

public class EEGSignalProcessor {
    
    private SignalProcessor aSignalProcessor = new SignalProcessor();
    private final int DELTA_LOW = 0;
    private final int THETA_LOW = 1;
    private final int THETA_HIGH = 3;
    private final int ALPHA_LOW = 4;
    private final int ALPHA_HIGH = 5;
    private final int BETA_LOW = 6;
    private final int BETA_HIGH = 7;
    private final int GAMMA_LOW = 8;
    private final int GAMMA_HIGH = 9;
    private final int SINGLENOTCH = 10;
    ///////////////////////////////////
    private final int BAND8_10_LOW = 11;
    private final int BAND10_12_LOW = 12;
    private final int BAND12_14_LOW = 13;
    private final int BAND14_16_LOW = 14;
    private final int BAND16_18_LOW = 15;
    private final int BAND18_20_LOW = 16;
    private final int BAND20_22_LOW = 17;
    private final int BAND22_24_LOW = 18;
    private final int BAND24_26_LOW = 19;
    private final int BAND26_28_LOW = 20;
    private final int BAND28_30_LOW = 21;
    private final int BAND30_32_LOW = 22;
    
    private final int BAND8_10_HIGH = 23;
    private final int BAND10_12_HIGH = 24;
    private final int BAND12_14_HIGH = 25;
    private final int BAND14_16_HIGH = 26;
    private final int BAND16_18_HIGH = 27;
    private final int BAND18_20_HIGH = 28;
    private final int BAND20_22_HIGH = 29;
    private final int BAND22_24_HIGH = 30;
    private final int BAND24_26_HIGH = 31;
    private final int BAND26_28_HIGH = 32;
    private final int BAND28_30_HIGH = 33;
    private final int BAND30_32_HIGH = 34;
    
    private static EEGSignalProcessor instance = null;
    
    public static EEGSignalProcessor getInstance(){
        if(instance == null){
            instance = new EEGSignalProcessor();
        }
        return instance;
    }
    
    private EEGSignalProcessor() {
        
    }
    
    public int getBAND10_12_HIGH() {
        return BAND10_12_HIGH;
    }
    
    public int getBAND10_12_LOW() {
        return BAND10_12_LOW;
    }
    
    public int getBAND12_14_HIGH() {
        return BAND12_14_HIGH;
    }
    
    public int getBAND12_14_LOW() {
        return BAND12_14_LOW;
    }
    
    public int getBAND14_16_HIGH() {
        return BAND14_16_HIGH;
    }
    
    public int getBAND14_16_LOW() {
        return BAND14_16_LOW;
    }
    
    public int getBAND16_18_HIGH() {
        return BAND16_18_HIGH;
    }
    
    public int getBAND16_18_LOW() {
        return BAND16_18_LOW;
    }
    
    public int getBAND18_20_HIGH() {
        return BAND18_20_HIGH;
    }
    
    public int getBAND18_20_LOW() {
        return BAND18_20_LOW;
    }
    
    public int getBAND20_22_HIGH() {
        return BAND20_22_HIGH;
    }
    
    public int getBAND20_22_LOW() {
        return BAND20_22_LOW;
    }
    
    public int getBAND22_24_HIGH() {
        return BAND22_24_HIGH;
    }
    
    public int getBAND22_24_LOW() {
        return BAND22_24_LOW;
    }
    
    public int getBAND24_26_HIGH() {
        return BAND24_26_HIGH;
    }
    
    public int getBAND24_26_LOW() {
        return BAND24_26_LOW;
    }
    
    public int getBAND26_28_HIGH() {
        return BAND26_28_HIGH;
    }
    
    public int getBAND26_28_LOW() {
        return BAND26_28_LOW;
    }
    
    public int getBAND28_30_HIGH() {
        return BAND28_30_HIGH;
    }
    
    public int getBAND28_30_LOW() {
        return BAND28_30_LOW;
    }
    
    public int getBAND30_32_HIGH() {
        return BAND30_32_HIGH;
    }
    
    public int getBAND30_32_LOW() {
        return BAND30_32_LOW;
    }
    
    public int getBAND8_10_HIGH() {
        return BAND8_10_HIGH;
    }
    
    public int getBAND8_10_LOW() {
        return BAND8_10_LOW;
    }
    
    public final int getSingleNotch(){
        return SINGLENOTCH;
    }
    
    public final int getDELTA_LOW(){
        return DELTA_LOW;
    }
    
    public final int getTHETA_LOW(){
        return THETA_LOW;
    }
    
    public final int getTHETA_HIGH(){
        return THETA_HIGH;
    }
    
    public final int getALPHA_LOW(){
        return ALPHA_LOW;
    }
    
    public final int getALPHA_HIGH(){
        return ALPHA_HIGH;
    }
    
    public final int getBETA_LOW(){
        return BETA_LOW;
    }
    
    public final int getBETA_HIGH(){
        return BETA_HIGH;
    }
    
    public final int getGAMMA_LOW(){
        return GAMMA_LOW;
    }
    
    public final int getGAMMA_HIGH(){
        return GAMMA_HIGH;
    }
    
    public double[] getFFT(double[] signal){
        double[] spectrum = new double[signal.length];
        aSignalProcessor.fft(signal,spectrum);
        return spectrum;
    }
    
    public double[] getIFFT(double[] spectrum){
        double[] signal = new double[spectrum.length];
        aSignalProcessor.ifft(spectrum,signal);
        return signal;
    }
    
    public double[] filter(double[] signal, int filterID){
        double[] filteredSignal = new double[signal.length];
        aSignalProcessor.filterFilter(signal,filteredSignal,filterID);
        return filteredSignal;
    }
    
    public double variance(double[] signal){
        return aSignalProcessor.getVariance(signal);
    }
    
    public double average(double[] signal){
        return aSignalProcessor.getMean(signal);
    }
    
    public double[] getAlphaSignal(double[] signal) {
        double[] alphaSignalArray = filter(signal,getALPHA_LOW());
        alphaSignalArray = filter(alphaSignalArray,getALPHA_HIGH());
        return alphaSignalArray;
    }
    
    public double[] getBetaSignal(double[] signal) {
        double[] betaSignalArray = filter(signal,getBETA_LOW());
        betaSignalArray = filter(betaSignalArray,getBETA_HIGH());
        return betaSignalArray;
    }
    
    public double[] getGammaSignal(double[] signal) {
        double[] gammaSignalArray = filter(signal,getGAMMA_LOW());
        gammaSignalArray = filter(gammaSignalArray,getGAMMA_HIGH());
        return gammaSignalArray;
    }
    
    public double[] getDeltaSignal(double[] signal) {
        double[] deltaSignalArray = filter(signal,getDELTA_LOW());
        return deltaSignalArray;
    }
    
    public double[] getThetaSignal(double[] signal) {
        double[] thetaSignalArray = filter(signal,getTHETA_LOW());
        thetaSignalArray = filter(thetaSignalArray,getTHETA_HIGH());
        return thetaSignalArray;
    }
    
    public double[] get8To10HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND8_10_LOW());
        signalBand = filter(signalBand,getBAND8_10_HIGH());
        return signalBand;
    }
    
    public double[] get10To12HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND10_12_LOW());
        signalBand = filter(signalBand,getBAND10_12_HIGH());
        return signalBand;
    }
    
    public double[] get12To14HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND12_14_LOW());
        signalBand = filter(signalBand,getBAND12_14_HIGH());
        return signalBand;
    }
    
    public double[] get14To16HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND14_16_LOW());
        signalBand = filter(signalBand,getBAND14_16_HIGH());
        return signalBand;
    }
    
    public double[] get16To18HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND16_18_LOW());
        signalBand = filter(signalBand,getBAND16_18_HIGH());
        return signalBand;
    }
    
    public double[] get18To20HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND18_20_LOW());
        signalBand = filter(signalBand,getBAND18_20_HIGH());
        return signalBand;
    }
    
    public double[] get20To22HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND20_22_LOW());
        signalBand = filter(signalBand,getBAND20_22_HIGH());
        return signalBand;
    }
    
    public double[] get22To24HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND22_24_LOW());
        signalBand = filter(signalBand,getBAND22_24_HIGH());
        return signalBand;
    }
    
    public double[] get24To26HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND24_26_LOW());
        signalBand = filter(signalBand,getBAND24_26_HIGH());
        return signalBand;
    }
    
    public double[] get26To28HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND26_28_LOW());
        signalBand = filter(signalBand,getBAND26_28_HIGH());
        return signalBand;
    }
    
    public double[] get28To30HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND28_30_LOW());
        signalBand = filter(signalBand,getBAND28_30_HIGH());
        return signalBand;
    }
    
    public double[] get30To32HzSignal(double[] signal) {
        double[] signalBand = filter(signal,getBAND30_32_LOW());
        signalBand = filter(signalBand,getBAND30_32_HIGH());
        return signalBand;
    }
    
    //return the energy of the unfiltered signal
    public double getSignalEnergy(double[] signal){
        return variance(signal);
    }
    
    public double getSignalPower(double[] signal){
        return Math.sqrt(variance(signal));
    }
    
    public void initialze(int sampleRate){
        
        int order = 30;
        //prepare plan used by fftw internally
        aSignalProcessor.newR2RPlan(sampleRate);
        
        //design the filter for the delta band 0 - 3 Hz
        aSignalProcessor.newFilter(DELTA_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,DELTA_LOW);
        aSignalProcessor.setSampleRate(sampleRate, DELTA_LOW);
        aSignalProcessor.setOrder(order, DELTA_LOW);
        aSignalProcessor.setHigh(3,DELTA_LOW);
        
        //design the filter for the theta band 4 - 7 Hz
        aSignalProcessor.newFilter(THETA_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,THETA_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, THETA_HIGH);
        aSignalProcessor.setOrder(order, THETA_HIGH);
        aSignalProcessor.setLow(4, THETA_HIGH);
        
        //design the filter for the theta band 4 - 7 Hz
        aSignalProcessor.newFilter(THETA_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,THETA_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, THETA_LOW);
        aSignalProcessor.setOrder(order, THETA_LOW);
        aSignalProcessor.setHigh(7, THETA_LOW);
        
        //design the filter for the alpha band 8 - 13 Hz
        aSignalProcessor.newFilter(ALPHA_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,ALPHA_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, ALPHA_HIGH);
        aSignalProcessor.setOrder(order, ALPHA_HIGH);
        aSignalProcessor.setLow(8, ALPHA_HIGH);
        
        //design the filter for the alpha band 8 - 13 Hz
        aSignalProcessor.newFilter(ALPHA_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,ALPHA_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, ALPHA_LOW);
        aSignalProcessor.setOrder(order, ALPHA_LOW);
        aSignalProcessor.setHigh(13, ALPHA_LOW);
        
        //design the filter for the beta band 14 - 38 Hz
        aSignalProcessor.newFilter(BETA_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BETA_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BETA_HIGH);
        aSignalProcessor.setOrder(order, BETA_HIGH);
        aSignalProcessor.setLow(14, BETA_HIGH);
        
        //design the filter for the beta band 14 - 38 Hz
        aSignalProcessor.newFilter(BETA_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BETA_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BETA_LOW);
        aSignalProcessor.setOrder(order, BETA_LOW);
        aSignalProcessor.setHigh(38, BETA_LOW);
        
        //design the filter for the gamma band 39 - 60 Hz
        aSignalProcessor.newFilter(GAMMA_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,GAMMA_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, GAMMA_HIGH);
        aSignalProcessor.setOrder(order, GAMMA_HIGH);
        aSignalProcessor.setLow(39, GAMMA_HIGH);
        //aSignalProcessor.addNotch(50.0,10.0,GAMMA_HIGH);
        
        //design the filter for the gamma band 39 - 60 Hz
        aSignalProcessor.newFilter(GAMMA_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,GAMMA_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, GAMMA_LOW);
        aSignalProcessor.setOrder(order, GAMMA_LOW);
        aSignalProcessor.setHigh(60, GAMMA_LOW);
        //aSignalProcessor.addNotch(50.0,10.0,GAMMA_LOW);
        
        aSignalProcessor.newFilter(SINGLENOTCH);
        aSignalProcessor.setFilterType(SignalProcessor.NOTCH,SINGLENOTCH);
        aSignalProcessor.setSampleRate((double)sampleRate, SINGLENOTCH);
        aSignalProcessor.setOrder(order, SINGLENOTCH);
        aSignalProcessor.addNotch(50.0,10.0,SINGLENOTCH);
        
        //////////////////////////////////////////////////////////////////
        
        //design the filter for the alpha band 8 - 10 Hz
        aSignalProcessor.newFilter(BAND8_10_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND8_10_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND8_10_LOW);
        aSignalProcessor.setOrder(order, BAND8_10_LOW);
        aSignalProcessor.setHigh(10, BAND8_10_LOW);
        
        //design the filter for the alpha band 8 - 10 Hz
        aSignalProcessor.newFilter(BAND8_10_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND8_10_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND8_10_HIGH);
        aSignalProcessor.setOrder(order, BAND8_10_HIGH);
        aSignalProcessor.setLow(8, BAND8_10_HIGH);
        
        //design the filter for the alpha band 10 - 12 Hz
        aSignalProcessor.newFilter(BAND10_12_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND10_12_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND10_12_LOW);
        aSignalProcessor.setOrder(order, BAND10_12_LOW);
        aSignalProcessor.setHigh(12, BAND10_12_LOW);
        
        //design the filter for the alpha band 10 - 12 Hz
        aSignalProcessor.newFilter(BAND10_12_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND10_12_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND10_12_HIGH);
        aSignalProcessor.setOrder(order, BAND10_12_HIGH);
        aSignalProcessor.setLow(10, BAND10_12_HIGH);
        
        //design the filter for the alpha band 12 - 14 Hz
        aSignalProcessor.newFilter(BAND12_14_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND12_14_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND12_14_LOW);
        aSignalProcessor.setOrder(order, BAND12_14_LOW);
        aSignalProcessor.setHigh(14, BAND12_14_LOW);
        
        //design the filter for the alpha band 12 - 14 Hz
        aSignalProcessor.newFilter(BAND12_14_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND12_14_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND12_14_HIGH);
        aSignalProcessor.setOrder(order, BAND12_14_HIGH);
        aSignalProcessor.setLow(12, BAND12_14_HIGH);
        
        //design the filter for the alpha band 14 - 16 Hz
        aSignalProcessor.newFilter(BAND14_16_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND14_16_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND14_16_LOW);
        aSignalProcessor.setOrder(order, BAND14_16_LOW);
        aSignalProcessor.setHigh(16, BAND14_16_LOW);
        
        //design the filter for the alpha band 14 - 16 Hz
        aSignalProcessor.newFilter(BAND14_16_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND14_16_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND14_16_HIGH);
        aSignalProcessor.setOrder(order, BAND14_16_HIGH);
        aSignalProcessor.setLow(14, BAND14_16_HIGH);
        
        //design the filter for the alpha band 16 - 18 Hz
        aSignalProcessor.newFilter(BAND16_18_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND16_18_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND16_18_LOW);
        aSignalProcessor.setOrder(order, BAND16_18_LOW);
        aSignalProcessor.setHigh(18, BAND16_18_LOW);
        
        //design the filter for the alpha band 16 - 18 Hz
        aSignalProcessor.newFilter(BAND16_18_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND16_18_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND16_18_HIGH);
        aSignalProcessor.setOrder(order, BAND16_18_HIGH);
        aSignalProcessor.setLow(16, BAND16_18_HIGH);
        
        //design the filter for the alpha band 18 - 20 Hz
        aSignalProcessor.newFilter(BAND18_20_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND18_20_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND18_20_LOW);
        aSignalProcessor.setOrder(order, BAND18_20_LOW);
        aSignalProcessor.setHigh(20, BAND18_20_LOW);
        
        //design the filter for the alpha band 18 - 20 Hz
        aSignalProcessor.newFilter(BAND18_20_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND18_20_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND18_20_HIGH);
        aSignalProcessor.setOrder(order, BAND18_20_HIGH);
        aSignalProcessor.setLow(18, BAND18_20_HIGH);
        
        //design the filter for the alpha band 20 - 22 Hz
        aSignalProcessor.newFilter(BAND20_22_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND20_22_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND20_22_LOW);
        aSignalProcessor.setOrder(order, BAND20_22_LOW);
        aSignalProcessor.setHigh(22, BAND20_22_LOW);
        
        //design the filter for the alpha band 20 - 22 Hz
        aSignalProcessor.newFilter(BAND20_22_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND20_22_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND20_22_HIGH);
        aSignalProcessor.setOrder(order, BAND20_22_HIGH);
        aSignalProcessor.setLow(20, BAND20_22_HIGH);
        
        //design the filter for the alpha band 22 - 24 Hz
        aSignalProcessor.newFilter(BAND22_24_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND22_24_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND22_24_LOW);
        aSignalProcessor.setOrder(order, BAND22_24_LOW);
        aSignalProcessor.setHigh(24, BAND22_24_LOW);
        
        //design the filter for the alpha band 22 - 24 Hz
        aSignalProcessor.newFilter(BAND22_24_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND22_24_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND22_24_HIGH);
        aSignalProcessor.setOrder(order, BAND22_24_HIGH);
        aSignalProcessor.setLow(22, BAND22_24_HIGH);
        
        //design the filter for the alpha band 24 - 26 Hz
        aSignalProcessor.newFilter(BAND24_26_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND24_26_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND24_26_LOW);
        aSignalProcessor.setOrder(order, BAND24_26_LOW);
        aSignalProcessor.setHigh(26, BAND24_26_LOW);
        
        //design the filter for the alpha band 24 - 26 Hz
        aSignalProcessor.newFilter(BAND24_26_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND24_26_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND24_26_HIGH);
        aSignalProcessor.setOrder(order, BAND24_26_HIGH);
        aSignalProcessor.setLow(24, BAND24_26_HIGH);
        
        //design the filter for the alpha band 26 - 28 Hz
        aSignalProcessor.newFilter(BAND26_28_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND26_28_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND26_28_LOW);
        aSignalProcessor.setOrder(order, BAND26_28_LOW);
        aSignalProcessor.setHigh(28, BAND26_28_LOW);
        
        //design the filter for the alpha band 26 - 28 Hz
        aSignalProcessor.newFilter(BAND26_28_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND26_28_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND26_28_HIGH);
        aSignalProcessor.setOrder(order, BAND26_28_HIGH);
        aSignalProcessor.setLow(26, BAND26_28_HIGH);
        
        //design the filter for the alpha band 28 - 30 Hz
        aSignalProcessor.newFilter(BAND28_30_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND28_30_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND28_30_LOW);
        aSignalProcessor.setOrder(order, BAND28_30_LOW);
        aSignalProcessor.setHigh(30, BAND28_30_LOW);
        
        //design the filter for the alpha band 28 - 30 Hz
        aSignalProcessor.newFilter(BAND28_30_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND28_30_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND28_30_HIGH);
        aSignalProcessor.setOrder(order, BAND28_30_HIGH);
        aSignalProcessor.setLow(28, BAND28_30_HIGH);
        
        //design the filter for the alpha band 30 - 32 Hz
        aSignalProcessor.newFilter(BAND30_32_LOW);
        aSignalProcessor.setFilterType(SignalProcessor.LOW_PASS,BAND30_32_LOW);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND30_32_LOW);
        aSignalProcessor.setOrder(order, BAND30_32_LOW);
        aSignalProcessor.setHigh(32, BAND30_32_LOW);
        
        //design the filter for the alpha band 30 - 32 Hz
        aSignalProcessor.newFilter(BAND30_32_HIGH);
        aSignalProcessor.setFilterType(SignalProcessor.HIGH_PASS,BAND30_32_HIGH);
        aSignalProcessor.setSampleRate((double)sampleRate, BAND30_32_HIGH);
        aSignalProcessor.setOrder(order, BAND30_32_HIGH);
        aSignalProcessor.setLow(30, BAND30_32_HIGH);
    }
}