#include "usbAmp.h"
#include <iostream>

using namespace std;
USBAmp aUSBAmp;

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierA_getHeaderSize(JNIEnv * env, jobject obj){
	return aUSBAmp.GetHeaderSize(env,obj);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierA_getSizeOfFloat(JNIEnv *env, jobject obj){
	return aUSBAmp.GetSizeOfFloat(env,obj);
}

JNIEXPORT jfloat JNICALL Java_media_protocol_gtec_NativeAmplifierA_getDriverVersion(JNIEnv *env , jobject obj){
	return aUSBAmp.GetDriverVersion(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_probeDevice(JNIEnv *env , jobject obj, jint port){
	return aUSBAmp.ProbeDevice(env,obj,port);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_openDevice__I(JNIEnv *env , jobject obj , jint iPortNumber){
	return aUSBAmp.OpenDevice(env,obj,iPortNumber);
}

//ONCE WE KNOW THE PORT, WE SHOULD REOPEN THE DEVICE USING ITS SERIAL

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_openDevice__Ljava_lang_String_2(JNIEnv *env , jobject obj, jstring serial){
	return aUSBAmp.OpenDevice(env, obj, serial);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_closeDevice(JNIEnv *env , jobject obj){
	return aUSBAmp.CloseDevice(env, obj);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierA_getData(JNIEnv *env , jobject obj, jbyteArray buffer){
	return aUSBAmp.GetData(env,obj,buffer);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setBufferSize(JNIEnv *env, jobject obj, jint buffersize){
	return aUSBAmp.SetBufferSize(env,obj,buffersize);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setSampleRate(JNIEnv *env, jobject obj, jint samplerate){
	return aUSBAmp.SetSampleRate(env,obj,samplerate);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_start(JNIEnv *env, jobject obj){
	return aUSBAmp.Start(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_stop(JNIEnv *env, jobject obj){
	return aUSBAmp.Stop(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setChannels(JNIEnv *env, jobject obj, jintArray channels){
	return aUSBAmp.SetChannels(env,obj,channels);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setDigitalOut(JNIEnv *env, jobject obj, jint port, jboolean flag){
	
	return aUSBAmp.SetDigitalOut(env,obj,port,flag);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_getDigitalIO(JNIEnv *env, jobject obj, jbooleanArray digital_in, jbooleanArray digital_out){
	return aUSBAmp.GetDigitalIO(env,obj,digital_in,digital_out);
}

JNIEXPORT void JNICALL Java_media_protocol_gtec_NativeAmplifierA_getLastError(JNIEnv *env, jobject obj){
	return aUSBAmp.GetLastError(env,obj);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierA_getLastErrorCode(JNIEnv *env, jobject obj){
	return aUSBAmp.GetLastErrorCode(env,obj);
}

JNIEXPORT jstring JNICALL Java_media_protocol_gtec_NativeAmplifierA_getLastErrorMessage(JNIEnv *env, jobject obj){
	return aUSBAmp.GetLastErrorMessage(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_resetTransfer(JNIEnv *env, jobject obj){
	return aUSBAmp.ResetTransfer(env,obj);
}

JNIEXPORT jstring JNICALL Java_media_protocol_gtec_NativeAmplifierA_getSerial(JNIEnv *env, jobject obj){
	return aUSBAmp.GetSerial(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_enableTriggerLine(JNIEnv *env, jobject obj, jboolean flag){
	return aUSBAmp.EnableTriggerLine(env,obj,flag);
}

JNIEXPORT jdouble JNICALL Java_media_protocol_gtec_NativeAmplifierA_getImpedance(JNIEnv *env, jobject obj, jint channel){
	return aUSBAmp.GetImpedance(env,obj,channel);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_calibrate(JNIEnv *env , jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
	return aUSBAmp.Calibrate(env, obj, factorArray, offsetArray);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
	return aUSBAmp.SetScale(env,obj,factorArray,offsetArray);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_getScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
	return aUSBAmp.GetScale(env,obj,factorArray,offsetArray);	
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_getFilterSpec(JNIEnv *env, jobject obj, jfloatArray filterSpecArray, jint filterID){
	return aUSBAmp.GetFilterSpec(env,obj,filterSpecArray,filterID);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierA_getNumberOfFilters(JNIEnv *env, jobject obj){
	return aUSBAmp.GetNumberOfFilters(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setBandPass(JNIEnv *env, jobject obj, jint filterID, jint channelID){
	return aUSBAmp.SetBandPass(env,obj,filterID,channelID);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_getNotchFilterSpec(JNIEnv *env, jobject obj, jfloatArray notchFilterSpecArray, jint filterID){
	return aUSBAmp.GetNotchFilterSpec(env,obj,notchFilterSpecArray,filterID);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierA_getNumberOfNotchFilters(JNIEnv *env, jobject obj){
	return aUSBAmp.GetNumberOfNotchFilters(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setNotch(JNIEnv *env, jobject obj, jint filterID, jint channelID){
	return GT_SetNotch(aUSBAmp.GetAmplifier(),channelID,filterID);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setMode(JNIEnv *env, jobject obj, jint mode){
/*
	#define M_NORMAL		0
	#define	M_IMPEDANCE		1
	#define	M_CALIBRATE		2
	#define M_COUNTER		3
*/
	return aUSBAmp.SetMode(env,obj,mode);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierA_getMode(JNIEnv *env, jobject obj){
	return aUSBAmp.GetMode(env,obj);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array){
	return aUSBAmp.SetGround(env,obj,gnd_array);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_getGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array){
	return aUSBAmp.GetGround(env,obj,gnd_array);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setReference(JNIEnv *env , jobject obj, jbooleanArray ref_array){
	return aUSBAmp.SetReference(env,obj,ref_array);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_getReference(JNIEnv *env, jobject obj, jbooleanArray ref_array){
	return aUSBAmp.GetReference(env,obj,ref_array);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setBipolar(JNIEnv *env, jobject obj, jintArray channels){
	return aUSBAmp.SetBipolar(env,obj,channels);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setDRLChannel(JNIEnv *env, jobject obj, jintArray channels){
	return aUSBAmp.SetDRLChannel(env,obj,channels);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_enableSC(JNIEnv *env, jobject obj, jboolean flag){
	return aUSBAmp.EnableSC(env,obj,flag);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setSlave(JNIEnv *env, jobject obj, jboolean flag){
	return aUSBAmp.SetSlave(env,obj,flag);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierA_setDAC(JNIEnv *env, jobject obj, jbyte waveShape, jint amplitude, jint frequency, jint offset){
	return aUSBAmp.SetDAC(env,obj,waveShape,amplitude,frequency,offset);
}