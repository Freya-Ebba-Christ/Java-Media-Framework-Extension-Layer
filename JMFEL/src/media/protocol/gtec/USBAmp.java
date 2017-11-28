/*
 * USBAmpA.java
 *
 * Created on 9. Juli 2007, 11:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

/**
 *
 * @author Der Olaf, der Sklave vom Cheffe
 */
public class USBAmp extends AbstractUSBAmp{
    
    /** Creates a new instance of USBAmp */
    public USBAmp() {
        super();
    }
    
    public static void main(String[] args) {
        USBAmp aUSBAmp = new USBAmp();
        aUSBAmp.setAmp(new NativeAmplifierA());
        aUSBAmp.setSerialNumber("UA-2007.04.01");
        
        int[] channels = new int[16];
        channels[0]=1;
        channels[1]=2;
        channels[2]=3;
        channels[3]=4;
        channels[4]=5;
        channels[5]=6;
        channels[6]=7;
        channels[7]=8;
        channels[8]=9;
        channels[9]=10;
        channels[10]=11;
        channels[11]=12;
        channels[12]=13;
        channels[13]=14;
        channels[14]=15;
        channels[15]=16;

        try{
            System.out.println("opening device");
            aUSBAmp.open();
        }catch (Exception e){System.out.println(e);};
        
        try{
            aUSBAmp.setSampleRate(256);
        }catch(Exception e){System.out.println(e);};
        
        try{
            System.out.println("setting mode to calibrate");
            aUSBAmp.setMode(Amplifier.M_CALIBRATE);
        }catch(Exception e){System.out.println(e);};

        
        try{
            System.out.println("setting channels");
            aUSBAmp.setChannels(channels);
        }catch(Exception e){System.out.println(e);};
        
        try{
            System.out.println("setting dac");
            aUSBAmp.setDAC(Amplifier.WS_SQUARE,1,5,2047);
        }catch(Exception e){System.out.println(e);};

/*        
        try{
            System.out.println("setting mode to normal");
            aUSBAmp.setMode(Amplifier.M_NORMAL);
        }catch(Exception e){System.out.println(e);};
*/        
        
        try{
            System.out.println("starting device");
            aUSBAmp.start();
        }catch (Exception e){System.out.println(e);};
        
        try{
            System.out.println("getting some data");
            for(int i = 0; i<256;i++) {
                aUSBAmp.getData();
            }
        }catch (Exception e){System.out.println(e);};
        
        try{
            System.out.println("stopping device");
            aUSBAmp.stop();
        }catch (Exception e){System.out.println(e);};
        
        try{
            System.out.println("closing device");
            aUSBAmp.close();
        }catch (Exception e){System.out.println(e);};
        
        /*
        try{
            System.out.println("get driver version");
            System.out.println(aUSBAmp.getDriverVersion());
        }catch(Exception e){System.out.println(e);};
         
        int[] channels = new int[8];
        channels[0] = 1;
        channels[1] = 2;
        channels[2] = 3;
        channels[3] = 4;
        channels[4] = 5;
        channels[5] = 6;
        channels[6] = 7;
        channels[7] = 8;
        try{
            System.out.println("setting channels");
            aUSBAmp.setChannels(channels);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("setting mode to calibrate");
            aUSBAmp.setMode(Amplifier.M_CALIBRATE);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("setting mode to impedance");
            aUSBAmp.setMode(Amplifier.M_IMPEDANCE);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("setting mode to normal");
            aUSBAmp.setMode(Amplifier.M_NORMAL);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("start device");
            aUSBAmp.start();
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("stop device");
            aUSBAmp.stop();
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("setting digital out 1|true");
            aUSBAmp.setDigitalOut(1,true);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("getting digital IO");
            DigitalIO aDigitalIO = aUSBAmp.getDigitalIO();
            System.out.println("DIN1 "+aDigitalIO.isDIN1());
            System.out.println("DIN2 "+aDigitalIO.isDIN1());
            System.out.println("DOUT1 "+aDigitalIO.isDOUT1());
            System.out.println("DOUT2 "+aDigitalIO.isDOUT2());
        }catch(Exception e){System.out.println(e);};
         
         
        System.out.println("getting last error");
        aUSBAmp.getLastError();
         
        System.out.println("getting last error code");
        System.out.println(aUSBAmp.getLastErrorCode());
         
        System.out.println("getting last error message");
        System.out.println(aUSBAmp.getLastErrorMessage());
         
        try{
            System.out.println("setting digital out 1|true");
            aUSBAmp.setDigitalOut(1,true);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("disable trigger line");
            aUSBAmp.enableTriggerLine(false);
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("getting impedances");
        for (int chn = 1; chn < 21; chn++) {
            try{
                System.out.println("Channel: "+chn +" "+aUSBAmp.getImpedance(chn));
            }catch(Exception e){System.out.println(e);};
        }
         
        System.out.println("calibrating...");
         
        float[] factor = new float[16];
        float[] offset = new float[16];
        try{
            aUSBAmp.calibrate(factor,offset);
            for (int chn = 0; chn < 16; chn++) {
                System.out.println("Channel "+chn + ": factor: "+factor[chn]+" offset: "+offset[chn]);
            }
        }catch(Exception e){System.out.println(e);};
         
         
        System.out.println("testing setScale()");
        System.out.println("setting factor and offset for each channel to 1.0");
         
        for (int chn = 0; chn < 16; chn++) {
            factor[chn]=1.0f;
            offset[chn]=1.0f;
        }
         
        try{
            aUSBAmp.setScale(factor,offset);
        }catch(Exception e){System.out.println(e);};
         
        for (int chn = 0; chn < 16; chn++) {
            factor[chn]=0.0f;
            offset[chn]=0.0f;
        }
         
        System.out.println("getting scale");
         
        try{
            aUSBAmp.getScale(factor,offset);
            System.out.println("display factor and offset for each channel");
            for (int chn = 0; chn < 16; chn++) {
                System.out.println("Channel "+chn + ": factor: "+factor[chn]+" offset: "+offset[chn]);
            }
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("getting available band pass filters");
        try{
            aUSBAmp.reloadFilterSpecList();
            FilterSpecList aFilterSpecList = aUSBAmp.getFilterSpecList();
            for (int filterSpec = 0; filterSpec < aFilterSpecList.getSize(); filterSpec++) {
                FilterSpec aFilterSpec =  aUSBAmp.getFilterSpecList().getFilterSpec(filterSpec);
                System.out.println("HighFreqency: "+aFilterSpec.getHighFreqency());
                System.out.println("LowFreqency: "+aFilterSpec.getLowFreqency());
                System.out.println("Order: "+aFilterSpec.getOrder());
                System.out.println("Samplingrate: "+aFilterSpec.getSamplingrate());
                System.out.println("Type: "+aFilterSpec.getType());
            }
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("getting available notch filters");
        try{
            aUSBAmp.reloadNotchFilterSpecList();
            FilterSpecList aFilterSpecList = aUSBAmp.getNotchFilterSpecList();
            for (int filterSpec = 0; filterSpec < aFilterSpecList.getSize(); filterSpec++) {
                FilterSpec aFilterSpec =  aUSBAmp.getNotchFilterSpecList().getFilterSpec(filterSpec);
                System.out.println("HighFreqency: "+aFilterSpec.getHighFreqency());
                System.out.println("LowFreqency: "+aFilterSpec.getLowFreqency());
                System.out.println("Order: "+aFilterSpec.getOrder());
                System.out.println("Samplingrate: "+aFilterSpec.getSamplingrate());
                System.out.println("Type: "+aFilterSpec.getType());
            }
        }catch(Exception e){System.out.println(e);};
         
         
        System.out.println("setting notch");
         
        try{
            aUSBAmp.setNotch(2,5);
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("setting band pass");
         
        try{
            aUSBAmp.setBandPass(1,4);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("setting mode to normal");
            aUSBAmp.setMode(Amplifier.M_NORMAL);
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("setting common ground to true|false|false|true");
         
        try{
            CommonGround aCommonGround = new CommonGround();
            aCommonGround.setGroupA(true);
            aCommonGround.setGroupB(false);
            aCommonGround.setGroupC(false);
            aCommonGround.setGroupD(true);
            aUSBAmp.setGround(aCommonGround);
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("getting common ground");
         
        try{
            CommonGround aCommonGround = aUSBAmp.getGround();
            System.out.println("Group A: "+aCommonGround.isGroupA());
            System.out.println("Group B: "+aCommonGround.isGroupB());
            System.out.println("Group C: "+aCommonGround.isGroupC());
            System.out.println("Group D: "+aCommonGround.isGroupD());
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("setting common reference to false|true|true|false");
         
        try{
            CommonReference aCommonReference = new CommonReference();
            aCommonReference.setGroupA(false);
            aCommonReference.setGroupB(true);
            aCommonReference.setGroupC(true);
            aCommonReference.setGroupD(false);
            aUSBAmp.setReference(aCommonReference);
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("getting common reference");
         
        try{
            CommonReference aCommonReference = aUSBAmp.getReference();
            System.out.println("Group A: "+aCommonReference.isGroupA());
            System.out.println("Group B: "+aCommonReference.isGroupB());
            System.out.println("Group C: "+aCommonReference.isGroupC());
            System.out.println("Group D: "+aCommonReference.isGroupD());
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("Setting a bipolar configuration");
         
        int[] bipoArray = new int[16];
        bipoArray[0] = 1;
        bipoArray[1] = 2;
        bipoArray[2] = 3;
        bipoArray[3] = 4;
        bipoArray[4] = 5;
        bipoArray[5] = 6;
        bipoArray[6] = 7;
        bipoArray[7] = 8;
        bipoArray[8] = 9;
        bipoArray[9] = 10;
        bipoArray[10] = 11;
        bipoArray[11] = 12;
        bipoArray[12] = 13;
        bipoArray[13] = 14;
        bipoArray[14] = 15;
        bipoArray[15] = 1;
         
        try{
            aUSBAmp.setBipolar(bipoArray);
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("Setting a driven right leg configuration");
         
        int[] drlArray = new int[16];
        drlArray[0] = 1;
        drlArray[1] = 2;
        drlArray[2] = 3;
        drlArray[3] = 4;
        drlArray[4] = 5;
        drlArray[5] = 6;
        drlArray[6] = 7;
        drlArray[7] = 8;
        drlArray[8] = 9;
        drlArray[9] = 10;
        drlArray[10] = 11;
        drlArray[11] = 12;
        drlArray[12] = 13;
        drlArray[13] = 14;
        drlArray[14] = 15;
        drlArray[15] = 1;
         
        try{
            aUSBAmp.setDRLChannel(drlArray);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("disable short cut");
            aUSBAmp.enableSC(false);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("setting slave mode to false");
            aUSBAmp.setSlave(false);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("setting slave mode to false");
            aUSBAmp.setSlave(false);
        }catch(Exception e){System.out.println(e);};
         
        System.out.println("Setting DAC to: \nWAVESHAPE = SAWTOOTH, AMPLITUDE = 2, FREQENCY = 64 Hz, OFFSET = 0");
         
        try{
            System.out.println("setting dac");
            aUSBAmp.setDAC(Amplifier.WS_SAWTOOTH,2,64,0);
        }catch(Exception e){System.out.println(e);};
         
        try{
            System.out.println("closing device");
            aUSBAmp.close();
        }catch(Exception e){System.out.println(e);};
         
        System.out.println();
         */
    }
}