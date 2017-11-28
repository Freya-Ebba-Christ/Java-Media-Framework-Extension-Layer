/*
 * ElectrodeList.java
 *
 * Created on 20. September 2007, 20:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eeg.utilities;

import java.util.Properties;
import utilities.PropertiesReader;
import utilities.PropertiesWriter;

/**
 * This list is based on a (near) equidistant 76-channel electrode arrangement
 * Usually you will use this list to read and write a vertext to assign the actual channels to the electrodes.
 * E.g. CZ could be assigned to channel 5. This comes handy when doing a surface laplacian and or an online electrode mapping.
 *
 * @author Administrator
 */

public class ElectrodeAssignment {
    
    private PropertiesReader propertiesReader = new PropertiesReader();
    private PropertiesWriter propertiesWriter = new PropertiesWriter();
    private Properties props = new Properties();
    private int f1 = 0;
    private int f2 = 0;
    private int f3 = 0;
    private int f4 = 0;
    private int f5 = 0;
    private int f6 = 0;
    private int f7 = 0;
    private int f8 = 0;
    private int f9 = 0;
    private int f10 = 0;
    private int fp1 = 0;
    private int fp2 = 0;
    private int fpz = 0;
    private int fz = 0;
    private int af7 = 0;
    private int af8 = 0;
    private int af3 = 0;
    private int af4 = 0;
    private int afz = 0;
    private int ft7 = 0;
    private int ft8 = 0;
    private int ft9 = 0;
    private int ft10 = 0;
    private int fc1 = 0;
    private int fc2 = 0;
    private int fc3 = 0;
    private int fc4 = 0;
    private int fc5 = 0;
    private int fc6 = 0;
    private int fcz = 0;
    private int t7 = 0;
    private int t8 = 0;
    private int t9 = 0;
    private int t10 = 0;
    private int c1 = 0;
    private int c2 = 0;
    private int c3 = 0;
    private int c4 = 0;
    private int c5 = 0;
    private int c6 = 0;
    private int cz = 0;
    private int cp1 = 0;
    private int cp2 = 0;
    private int cp3 = 0;
    private int cp4 = 0;
    private int cp5 = 0;
    private int cp6 = 0;
    private int cpz = 0;
    private int tp7 = 0;
    private int tp8 = 0;
    private int tp9 = 0;
    private int tp10 = 0;
    private int pz = 0;
    private int p1 = 0;
    private int p2 = 0;
    private int p3 = 0;
    private int p4 = 0;
    private int p5 = 0;
    private int p6 = 0;
    private int p7 = 0;
    private int p8 = 0;
    private int p9 = 0;
    private int p10 = 0;
    private int foz = 0;
    private int fo3 = 0;
    private int fo4 = 0;
    private int fo7 = 0;
    private int fo8 = 0;
    private int oz = 0;
    private int o1 = 0;
    private int o2 = 0;
    private int iz = 0;
    private int fo9 = 0;
    private int fo10 = 0;
    private int o9 = 0;
    private int o10 = 0;
    private int pulse = 0;
    
    public ElectrodeAssignment() {
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }
    
    public int getT10() {
        return t10;
    }

    public int getT9() {
        return t9;
    }

    public void setT10(int t10) {
        this.t10 = t10;
    }

    public void setT9(int t9) {
        this.t9 = t9;
    }
    
    public int getAf3() {
        return af3;
    }
    
    public int getAf4() {
        return af4;
    }
    
    public int getAf7() {
        return af7;
    }
    
    public int getAf8() {
        return af8;
    }
    
    public int getAfz() {
        return afz;
    }
    
    public int getC1() {
        return c1;
    }
    
    public int getC2() {
        return c2;
    }
    
    public int getC3() {
        return c3;
    }
    
    public int getC4() {
        return c4;
    }
    
    public int getC5() {
        return c5;
    }
    
    public int getC6() {
        return c6;
    }
    
    public int getCp1() {
        return cp1;
    }
    
    public int getCp2() {
        return cp2;
    }
    
    public int getCp3() {
        return cp3;
    }
    
    public int getCp4() {
        return cp4;
    }
    
    public int getCp5() {
        return cp5;
    }
    
    public int getCp6() {
        return cp6;
    }
    
    public int getCpz() {
        return cpz;
    }
    
    public int getCz() {
        return cz;
    }
    
    public int getF1() {
        return f1;
    }
    
    public int getF10() {
        return f10;
    }
    
    public int getF2() {
        return f2;
    }
    
    public int getF3() {
        return f3;
    }
    
    public int getF4() {
        return f4;
    }
    
    public int getF5() {
        return f5;
    }
    
    public int getF6() {
        return f6;
    }
    
    public int getF7() {
        return f7;
    }
    
    public int getF8() {
        return f8;
    }
    
    public int getF9() {
        return f9;
    }
    
    public int getFc1() {
        return fc1;
    }
    
    public int getFc2() {
        return fc2;
    }
    
    public int getFc3() {
        return fc3;
    }
    
    public int getFc4() {
        return fc4;
    }
    
    public int getFc5() {
        return fc5;
    }
    
    public int getFc6() {
        return fc6;
    }
    
    public int getFcz() {
        return fcz;
    }
    
    public int getFo10() {
        return fo10;
    }
    
    public int getFo3() {
        return fo3;
    }
    
    public int getFo4() {
        return fo4;
    }
    
    public int getFo7() {
        return fo7;
    }
    
    public int getFo8() {
        return fo8;
    }
    
    public int getFo9() {
        return fo9;
    }
    
    public int getFoz() {
        return foz;
    }
    
    public int getFp1() {
        return fp1;
    }
    
    public int getFp2() {
        return fp2;
    }
    
    public int getFpz() {
        return fpz;
    }
    
    public int getFt10() {
        return ft10;
    }
    
    public int getFt7() {
        return ft7;
    }
    
    public int getFt8() {
        return ft8;
    }
    
    public int getFt9() {
        return ft9;
    }
    
    public int getFz() {
        return fz;
    }
    
    public int getIz() {
        return iz;
    }
    
    public int getO1() {
        return o1;
    }
    
    public int getO10() {
        return o10;
    }
    
    public int getO2() {
        return o2;
    }
    
    public int getO9() {
        return o9;
    }
    
    public int getOz() {
        return oz;
    }
    
    public int getP1() {
        return p1;
    }
    
    public int getP10() {
        return p10;
    }
    
    public int getP2() {
        return p2;
    }
    
    public int getP3() {
        return p3;
    }
    
    public int getP4() {
        return p4;
    }
    
    public int getP5() {
        return p5;
    }
    
    public int getP6() {
        return p6;
    }
    
    public int getP7() {
        return p7;
    }
    
    public int getP8() {
        return p8;
    }
    
    public int getP9() {
        return p9;
    }
    
    private PropertiesReader getPropertiesReader() {
        return propertiesReader;
    }
    
    private PropertiesWriter getPropertiesWriter() {
        return propertiesWriter;
    }
    
    private Properties getProps() {
        return props;
    }
    
    public int getPz() {
        return pz;
    }
    
    public int getT7() {
        return t7;
    }
    
    public int getT8() {
        return t8;
    }
    
    public int getTp10() {
        return tp10;
    }
    
    public int getTp7() {
        return tp7;
    }
    
    public int getTp8() {
        return tp8;
    }
    
    public int getTp9() {
        return tp9;
    }
    
    public void setAf3(int af3) {
        this.af3 = af3;
    }
    
    public void setAf4(int af4) {
        this.af4 = af4;
    }
    
    public void setAf7(int af7) {
        this.af7 = af7;
    }
    
    public void setAf8(int af8) {
        this.af8 = af8;
    }
    
    public void setAfz(int afz) {
        this.afz = afz;
    }
    
    public void setC1(int c1) {
        this.c1 = c1;
    }
    
    public void setC2(int c2) {
        this.c2 = c2;
    }
    
    public void setC3(int c3) {
        this.c3 = c3;
    }
    
    public void setC4(int c4) {
        this.c4 = c4;
    }
    
    public void setC5(int c5) {
        this.c5 = c5;
    }
    
    public void setC6(int c6) {
        this.c6 = c6;
    }
    
    public void setCp1(int cp1) {
        this.cp1 = cp1;
    }
    
    public void setCp2(int cp2) {
        this.cp2 = cp2;
    }
    
    public void setCp3(int cp3) {
        this.cp3 = cp3;
    }
    
    public void setCp4(int cp4) {
        this.cp4 = cp4;
    }
    
    public void setCp5(int cp5) {
        this.cp5 = cp5;
    }
    
    public void setCp6(int cp6) {
        this.cp6 = cp6;
    }
    
    public void setCpz(int cpz) {
        this.cpz = cpz;
    }
    
    public void setCz(int cz) {
        this.cz = cz;
    }
    
    public void setF1(int f1) {
        this.f1 = f1;
    }
    
    public void setF10(int f10) {
        this.f10 = f10;
    }
    
    public void setF2(int f2) {
        this.f2 = f2;
    }
    
    public void setF3(int f3) {
        this.f3 = f3;
    }
    
    public void setF4(int f4) {
        this.f4 = f4;
    }
    
    public void setF5(int f5) {
        this.f5 = f5;
    }
    
    public void setF6(int f6) {
        this.f6 = f6;
    }
    
    public void setF7(int f7) {
        this.f7 = f7;
    }
    
    public void setF8(int f8) {
        this.f8 = f8;
    }
    
    public void setF9(int f9) {
        this.f9 = f9;
    }
    
    public void setFc1(int fc1) {
        this.fc1 = fc1;
    }
    
    public void setFc2(int fc2) {
        this.fc2 = fc2;
    }
    
    public void setFc3(int fc3) {
        this.fc3 = fc3;
    }
    
    public void setFc4(int fc4) {
        this.fc4 = fc4;
    }
    
    public void setFc5(int fc5) {
        this.fc5 = fc5;
    }
    
    public void setFc6(int fc6) {
        this.fc6 = fc6;
    }
    
    public void setFcz(int fcz) {
        this.fcz = fcz;
    }
    
    public void setFo10(int fo10) {
        this.fo10 = fo10;
    }
    
    public void setFo3(int fo3) {
        this.fo3 = fo3;
    }
    
    public void setFo4(int fo4) {
        this.fo4 = fo4;
    }
    
    public void setFo7(int fo7) {
        this.fo7 = fo7;
    }
    
    public void setFo8(int fo8) {
        this.fo8 = fo8;
    }
    
    public void setFo9(int fo9) {
        this.fo9 = fo9;
    }
    
    public void setFoz(int foz) {
        this.foz = foz;
    }
    
    public void setFp1(int fp1) {
        this.fp1 = fp1;
    }
    
    public void setFp2(int fp2) {
        this.fp2 = fp2;
    }
    
    public void setFpz(int fpz) {
        this.fpz = fpz;
    }
    
    public void setFt10(int ft10) {
        this.ft10 = ft10;
    }
    
    public void setFt7(int ft7) {
        this.ft7 = ft7;
    }
    
    public void setFt8(int ft8) {
        this.ft8 = ft8;
    }
    
    public void setFt9(int ft9) {
        this.ft9 = ft9;
    }
    
    public void setFz(int fz) {
        this.fz = fz;
    }
    
    public void setIz(int iz) {
        this.iz = iz;
    }
    
    public void setO1(int o1) {
        this.o1 = o1;
    }
    
    public void setO10(int o10) {
        this.o10 = o10;
    }
    
    public void setO2(int o2) {
        this.o2 = o2;
    }
    
    public void setO9(int o9) {
        this.o9 = o9;
    }
    
    public void setOz(int oz) {
        this.oz = oz;
    }
    
    public void setP1(int p1) {
        this.p1 = p1;
    }
    
    public void setP10(int p10) {
        this.p10 = p10;
    }
    
    public void setP2(int p2) {
        this.p2 = p2;
    }
    
    public void setP3(int p3) {
        this.p3 = p3;
    }
    
    public void setP4(int p4) {
        this.p4 = p4;
    }
    
    public void setP5(int p5) {
        this.p5 = p5;
    }
    
    public void setP6(int p6) {
        this.p6 = p6;
    }
    
    public void setP7(int p7) {
        this.p7 = p7;
    }
    
    public void setP8(int p8) {
        this.p8 = p8;
    }
    
    public void setP9(int p9) {
        this.p9 = p9;
    }
    
    private void setPropertiesReader(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }
    
    private void setPropertiesWriter(PropertiesWriter propertiesWriter) {
        this.propertiesWriter = propertiesWriter;
    }
    
    private void setProps(Properties props) {
        this.props = props;
    }
    
    public void setPz(int pz) {
        this.pz = pz;
    }
    
    public void setT7(int t7) {
        this.t7 = t7;
    }
    
    public void setT8(int t8) {
        this.t8 = t8;
    }
    
    public void setTp10(int tp10) {
        this.tp10 = tp10;
    }
    
    public void setTp7(int tp7) {
        this.tp7 = tp7;
    }
    
    public void setTp8(int tp8) {
        this.tp8 = tp8;
    }
    
    public void setTp9(int tp9) {
        this.tp9 = tp9;
    }
    
    public void read(String path){
        setProps(propertiesReader.readProperties(path));
        setPulse(Integer.parseInt(getProps().getProperty("PULSE")));
        setF1(Integer.parseInt(getProps().getProperty("F1")));
        setF2(Integer.parseInt(getProps().getProperty("F2")));
        setF3(Integer.parseInt(getProps().getProperty("F3")));
        setF4(Integer.parseInt(getProps().getProperty("F4")));
        setF5(Integer.parseInt(getProps().getProperty("F5")));
        setF6(Integer.parseInt(getProps().getProperty("F6")));
        setF7(Integer.parseInt(getProps().getProperty("F7")));
        setF8(Integer.parseInt(getProps().getProperty("F8")));
        setF9(Integer.parseInt(getProps().getProperty("F9")));
        setF10(Integer.parseInt(getProps().getProperty("F10")));
        setFp1(Integer.parseInt(getProps().getProperty("FP1")));
        setFp2(Integer.parseInt(getProps().getProperty("FP2")));
        setFpz(Integer.parseInt(getProps().getProperty("FPZ")));
        setFz(Integer.parseInt(getProps().getProperty("FZ")));
        setAf7(Integer.parseInt(getProps().getProperty("AF7")));
        setAf8(Integer.parseInt(getProps().getProperty("AF8")));
        setAf3(Integer.parseInt(getProps().getProperty("AF3")));
        setAf4(Integer.parseInt(getProps().getProperty("AF4")));
        setAfz(Integer.parseInt(getProps().getProperty("AFZ")));
        setFt7(Integer.parseInt(getProps().getProperty("FT7")));
        setFt8(Integer.parseInt(getProps().getProperty("FT8")));
        setFt9(Integer.parseInt(getProps().getProperty("FT9")));
        setFt10(Integer.parseInt(getProps().getProperty("FT10")));
        
        setFc1(Integer.parseInt(getProps().getProperty("FC1")));
        setFc2(Integer.parseInt(getProps().getProperty("FC2")));
        setFc3(Integer.parseInt(getProps().getProperty("FC3")));
        setFc4(Integer.parseInt(getProps().getProperty("FC4")));
        setFc5(Integer.parseInt(getProps().getProperty("FC5")));
        setFc6(Integer.parseInt(getProps().getProperty("FC6")));
        setFcz(Integer.parseInt(getProps().getProperty("FCZ")));
        setT7(Integer.parseInt(getProps().getProperty("T7")));
        setT8(Integer.parseInt(getProps().getProperty("T8")));
        setT9(Integer.parseInt(getProps().getProperty("T9")));
        setT10(Integer.parseInt(getProps().getProperty("T10")));
        setC1(Integer.parseInt(getProps().getProperty("C1")));
        setC2(Integer.parseInt(getProps().getProperty("C2")));
        setC3(Integer.parseInt(getProps().getProperty("C3")));
        setC4(Integer.parseInt(getProps().getProperty("C4")));
        setC5(Integer.parseInt(getProps().getProperty("C5")));
        setC6(Integer.parseInt(getProps().getProperty("C6")));
        setCz(Integer.parseInt(getProps().getProperty("CZ")));
        
        setCp1(Integer.parseInt(getProps().getProperty("CP1")));
        setCp2(Integer.parseInt(getProps().getProperty("CP2")));
        setCp3(Integer.parseInt(getProps().getProperty("CP3")));
        setCp4(Integer.parseInt(getProps().getProperty("CP4")));
        setCp5(Integer.parseInt(getProps().getProperty("CP5")));
        setCp6(Integer.parseInt(getProps().getProperty("CP6")));
        setCpz(Integer.parseInt(getProps().getProperty("CPZ")));
        setTp7(Integer.parseInt(getProps().getProperty("TP7")));
        setTp8(Integer.parseInt(getProps().getProperty("TP8")));
        setTp9(Integer.parseInt(getProps().getProperty("TP9")));
        setTp10(Integer.parseInt(getProps().getProperty("TP10")));
        setPz(Integer.parseInt(getProps().getProperty("PZ")));
        setP1(Integer.parseInt(getProps().getProperty("P1")));
        setP2(Integer.parseInt(getProps().getProperty("P2")));
        setP3(Integer.parseInt(getProps().getProperty("P3")));
        setP4(Integer.parseInt(getProps().getProperty("P4")));
        setP5(Integer.parseInt(getProps().getProperty("P5")));
        setP6(Integer.parseInt(getProps().getProperty("P6")));
        setP7(Integer.parseInt(getProps().getProperty("P7")));
        setP8(Integer.parseInt(getProps().getProperty("P8")));
        setP9(Integer.parseInt(getProps().getProperty("P9")));
        setP10(Integer.parseInt(getProps().getProperty("P10")));
        setFoz(Integer.parseInt(getProps().getProperty("FOZ")));
        setFo3(Integer.parseInt(getProps().getProperty("FO3")));
        setFo4(Integer.parseInt(getProps().getProperty("FO4")));
        setFo7(Integer.parseInt(getProps().getProperty("FO7")));
        setFo8(Integer.parseInt(getProps().getProperty("FO8")));
        setFo9(Integer.parseInt(getProps().getProperty("FO9")));
        setFo10(Integer.parseInt(getProps().getProperty("FO10")));
        setOz(Integer.parseInt(getProps().getProperty("OZ")));
        setO1(Integer.parseInt(getProps().getProperty("O1")));
        setO2(Integer.parseInt(getProps().getProperty("O2")));
        setIz(Integer.parseInt(getProps().getProperty("IZ")));
        setO9(Integer.parseInt(getProps().getProperty("O9")));
        setO10(Integer.parseInt(getProps().getProperty("O10")));
    }
    
    public void write(String path){
        getProps().setProperty("PULSE",Integer.toString(getPulse()));
        getProps().setProperty("F1",Integer.toString(getF1()));
        getProps().setProperty("F2",Integer.toString(getF2()));
        getProps().setProperty("F3",Integer.toString(getF3()));
        getProps().setProperty("F4",Integer.toString(getF4()));
        getProps().setProperty("F5",Integer.toString(getF5()));
        getProps().setProperty("F6",Integer.toString(getF6()));
        getProps().setProperty("F7",Integer.toString(getF7()));
        getProps().setProperty("F8",Integer.toString(getF8()));
        getProps().setProperty("F9",Integer.toString(getF9()));
        getProps().setProperty("F10",Integer.toString(getF10()));
        getProps().setProperty("FP1",Integer.toString(getFp1()));
        getProps().setProperty("FP2",Integer.toString(getFp2()));
        getProps().setProperty("FPZ",Integer.toString(getFpz()));
        getProps().setProperty("FZ",Integer.toString(getFz()));
        getProps().setProperty("AF7",Integer.toString(getAf7()));
        getProps().setProperty("AF8",Integer.toString(getAf8()));
        getProps().setProperty("AF3",Integer.toString(getAf3()));
        getProps().setProperty("AF4",Integer.toString(getAf4()));
        getProps().setProperty("AFZ",Integer.toString(getAfz()));
        getProps().setProperty("FT7",Integer.toString(getFt7()));
        getProps().setProperty("FT8",Integer.toString(getFt8()));
        getProps().setProperty("FT9",Integer.toString(getFt9()));
        getProps().setProperty("FT10",Integer.toString(getFt10()));
        getProps().setProperty("FC1",Integer.toString(getFc1()));
        getProps().setProperty("FC2",Integer.toString(getFc2()));
        getProps().setProperty("FC3",Integer.toString(getFc3()));
        getProps().setProperty("FC4",Integer.toString(getFc4()));
        getProps().setProperty("FC5",Integer.toString(getFc5()));
        getProps().setProperty("FC6",Integer.toString(getFc6()));
        getProps().setProperty("FCZ",Integer.toString(getFcz()));
        getProps().setProperty("T7",Integer.toString(getT7()));
        getProps().setProperty("T8",Integer.toString(getT8()));
        getProps().setProperty("T9",Integer.toString(getT9()));
        getProps().setProperty("T10",Integer.toString(getT10()));
        getProps().setProperty("C1",Integer.toString(getC1()));
        getProps().setProperty("C2",Integer.toString(getC2()));
        getProps().setProperty("C3",Integer.toString(getC3()));
        getProps().setProperty("C4",Integer.toString(getC4()));
        getProps().setProperty("C5",Integer.toString(getC5()));
        getProps().setProperty("C6",Integer.toString(getC6()));
        getProps().setProperty("CZ",Integer.toString(getCz()));
        getProps().setProperty("CP1",Integer.toString(getCp1()));
        getProps().setProperty("CP2",Integer.toString(getCp2()));
        getProps().setProperty("CP3",Integer.toString(getCp3()));
        getProps().setProperty("CP4",Integer.toString(getCp4()));
        getProps().setProperty("CP5",Integer.toString(getCp5()));
        getProps().setProperty("CP6",Integer.toString(getCp6()));
        getProps().setProperty("CPZ",Integer.toString(getCpz()));
        getProps().setProperty("TP7",Integer.toString(getTp7()));
        getProps().setProperty("TP8",Integer.toString(getTp8()));
        getProps().setProperty("TP9",Integer.toString(getTp9()));
        getProps().setProperty("TP10",Integer.toString(getTp10()));
        getProps().setProperty("P1",Integer.toString(getP1()));
        getProps().setProperty("P2",Integer.toString(getP2()));
        getProps().setProperty("P3",Integer.toString(getP3()));
        getProps().setProperty("P4",Integer.toString(getP4()));
        getProps().setProperty("P5",Integer.toString(getP5()));
        getProps().setProperty("P6",Integer.toString(getP6()));
        getProps().setProperty("P7",Integer.toString(getP7()));
        getProps().setProperty("P8",Integer.toString(getP8()));
        getProps().setProperty("P9",Integer.toString(getP9()));
        getProps().setProperty("P10",Integer.toString(getP10()));
        getProps().setProperty("PZ",Integer.toString(getPz()));
        getProps().setProperty("FOZ",Integer.toString(getFoz()));
        getProps().setProperty("FO3",Integer.toString(getFo3()));
        getProps().setProperty("FO4",Integer.toString(getFo4()));
        getProps().setProperty("FO7",Integer.toString(getFo7()));
        getProps().setProperty("FO8",Integer.toString(getFo8()));
        getProps().setProperty("FO9",Integer.toString(getFo9()));
        getProps().setProperty("FO10",Integer.toString(getFo10()));
        getProps().setProperty("OZ",Integer.toString(getOz()));
        getProps().setProperty("O1",Integer.toString(getO1()));
        getProps().setProperty("O2",Integer.toString(getO2()));
        getProps().setProperty("IZ",Integer.toString(getIz()));
        getProps().setProperty("O9",Integer.toString(getO9()));
        getProps().setProperty("O10",Integer.toString(getO10()));
        
        getPropertiesWriter().writeProperties(path,getProps());
    }
    
    public static void main(String[] args) {
        String file_separator = System.getProperty("file.separator");
        String file = System.getProperty("user.dir") + file_separator + "resources" + file_separator + "eeg" + file_separator + "channels_to_electrodes_mapping.ini";
        ElectrodeAssignment aElectrodeAssignment = new ElectrodeAssignment();
        aElectrodeAssignment.write(file);
        aElectrodeAssignment.read(file);
    }
}