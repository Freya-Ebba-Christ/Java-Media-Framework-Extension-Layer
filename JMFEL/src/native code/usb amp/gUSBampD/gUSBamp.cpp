#include "media_protocol_gtec_NativeAmplifierD.h"
#include "stdafx.h"
#include <iostream>
using namespace std;

HANDLE amplifier; 
HANDLE amplifier_Master;
HANDLE amplifier_Slave;
WORD wErrorCode;
CHAR pLastError;
HANDLE ampEvent;
OVERLAPPED ov;

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierD_getHeaderSize(JNIEnv * env, jobject obj){
	return HEADER_SIZE;
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierD_getSizeOfFloat(JNIEnv *env, jobject obj){
	return sizeof(float);
}

JNIEXPORT jfloat JNICALL Java_media_protocol_gtec_NativeAmplifierD_getDriverVersion(JNIEnv *env , jobject obj){
	return GT_GetDriverVersion();
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_probeDevice(JNIEnv *env , jobject obj, jint port){
	
	//just look if we can open an amplifier on this usb port and return if it was a success or not...simple;-) 
	HANDLE hdev;
	hdev = GT_OpenDevice(port);
	jboolean success = false;
	if(hdev != NULL){
		GT_CloseDevice(&hdev);
		success = true;
	}
	return success;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_openDevice__I(JNIEnv *env , jobject obj , jint iPortNumber){

	//PLEASE PROBE THE DEVICE BEFORE TRYING TO OPEN IT!!!
	amplifier = GT_OpenDevice(iPortNumber);
	ampEvent = CreateEvent(NULL,FALSE,FALSE,NULL);
	ov.hEvent = ampEvent;
	ov.Offset = 0;
	ov.OffsetHigh = 0;
	ResetEvent(ampEvent);
	
	jboolean success = false;
	if(amplifier != NULL){
		success = true;
	}
	return success;
}

//ONCE WE KNOW THE PORT, WE SHOULD REOPEN THE DEVICE USING ITS SERIAL

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_openDevice__Ljava_lang_String_2(JNIEnv *env , jobject obj, jstring serial){
	LPSTR ser = (LPSTR)env->GetStringUTFChars(serial, 0);
	amplifier = GT_OpenDeviceEx(ser);
	ampEvent = CreateEvent(NULL,FALSE,FALSE,NULL);
	ov.hEvent = ampEvent;
	ov.Offset = 0;
	ov.OffsetHigh = 0;
	ResetEvent(ampEvent);
	jboolean success = false;
	if(amplifier != NULL){
		success = true;
	}
	return success;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_closeDevice(JNIEnv *env , jobject obj){
	BOOL success = GT_CloseDevice(&amplifier);
	if(success){
		CloseHandle(ampEvent);
	}
	return success;
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierD_getData(JNIEnv *env , jobject obj, jbyteArray buffer){

		byte *byte_array = (byte*) env->GetPrimitiveArrayCritical(buffer,0);
		int bufLen = env->GetArrayLength(buffer);
		byte *bytePtr_array = byte_array;
		int validSamples = 0;
		DWORD dwBytesReceived = 0;

		//bufLen = HEADER_SIZE*bufLen

		GT_GetData(amplifier, bytePtr_array,bufLen,&ov);
		
		DWORD dwOVret = WaitForSingleObject(ampEvent,1000);
		if(dwOVret == WAIT_TIMEOUT)
		{
			MessageBox(NULL,"Timeout occured\n", "WARNING", MB_ICONWARNING);

			GT_ResetTransfer(amplifier);
			ResetEvent(ampEvent);

		}else{
			GetOverlappedResult(amplifier,&ov,&dwBytesReceived,FALSE);
			validSamples = (int)(dwBytesReceived-HEADER_SIZE);
			ResetEvent(ampEvent);
		}

		env->ReleasePrimitiveArrayCritical(buffer,byte_array,0);
		ResetEvent(ampEvent);
		
		return validSamples;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setBufferSize(JNIEnv *env, jobject obj, jint buffersize){
	return GT_SetBufferSize(amplifier,buffersize);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setSampleRate(JNIEnv *env, jobject obj, jint samplerate){
	return GT_SetSampleRate(amplifier,samplerate);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_start(JNIEnv *env, jobject obj){
	return GT_Start(amplifier);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_stop(JNIEnv *env, jobject obj){
	return GT_Stop(amplifier);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setChannels(JNIEnv *env, jobject obj, jintArray channels){
		int *int_channels = (int*) env->GetPrimitiveArrayCritical(channels,0);
		int size = env->GetArrayLength(channels);
		int *intPtr_channels = int_channels;

		UCHAR* ucChannels = new UCHAR[size];

		for(int i=0;i<size;i++){
			ucChannels[i]=(UCHAR)intPtr_channels[i];
		}

		env->ReleasePrimitiveArrayCritical(channels,int_channels,0);
		GT_SetChannels(amplifier, ucChannels, size);
		return true;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setDigitalOut(JNIEnv *env, jobject obj, jint port, jboolean flag){
	
	return GT_SetDigitalOut(amplifier,port,flag);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_getDigitalIO(JNIEnv *env, jobject obj, jbooleanArray digital_in, jbooleanArray digital_out){
		DigitalIO myDIO;

		bool *bool_array_in = (bool*) env->GetPrimitiveArrayCritical(digital_in,0);
		int size_in = env->GetArrayLength(digital_in);
		bool *boolPtr_array_in = bool_array_in;

		bool *bool_array_out = (bool*) env->GetPrimitiveArrayCritical(digital_out,0);
		int size_out = env->GetArrayLength(digital_out);
		bool *boolPtr_array_out = bool_array_out;
		
		BOOL ret = GT_GetDigitalIO(amplifier,&myDIO);
		boolPtr_array_in[0] = myDIO.DIN1;
		boolPtr_array_in[1] = myDIO.DIN2;
		
		boolPtr_array_out[0] = myDIO.DOUT1;
		boolPtr_array_out[1] = myDIO.DOUT2;

		env->ReleasePrimitiveArrayCritical(digital_in, bool_array_in,0);
		env->ReleasePrimitiveArrayCritical(digital_out, bool_array_out,0);

		return ret;
}

JNIEXPORT void JNICALL Java_media_protocol_gtec_NativeAmplifierD_getLastError(JNIEnv *env, jobject obj){
	GT_GetLastError(&wErrorCode,&pLastError);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierD_getLastErrorCode(JNIEnv *env, jobject obj){
	return (jint)wErrorCode;
}

JNIEXPORT jstring JNICALL Java_media_protocol_gtec_NativeAmplifierD_getLastErrorMessage(JNIEnv *env, jobject obj){
	return env->NewStringUTF(&pLastError);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_resetTransfer(JNIEnv *env, jobject obj){
	return GT_ResetTransfer(amplifier);
}

JNIEXPORT jstring JNICALL Java_media_protocol_gtec_NativeAmplifierD_getSerial(JNIEnv *env, jobject obj){
	CString strSerial;
	GT_GetSerial(amplifier, strSerial.GetBuffer(256), 256);
	return env->NewStringUTF(strSerial);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_enableTriggerLine(JNIEnv *env, jobject obj, jboolean flag){
	return GT_EnableTriggerLine(amplifier,flag);
}

JNIEXPORT jdouble JNICALL Java_media_protocol_gtec_NativeAmplifierD_getImpedance(JNIEnv *env, jobject obj, jint channel){
	double impedance;
	GT_GetImpedance(amplifier,(UCHAR)channel,&impedance);
	return impedance;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_calibrate(JNIEnv *env , jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
		
		SCALE scale;

		float *float_factorArray = (float*) env->GetPrimitiveArrayCritical(factorArray,0);
		int size_factorArray = env->GetArrayLength(factorArray);
		float *floatPtr_factorArray = float_factorArray;

		float *float_offsetArray = (float*) env->GetPrimitiveArrayCritical(offsetArray,0);
		int size_offsetArray = env->GetArrayLength(offsetArray);
		float *floatPtr_offsetArray = float_offsetArray;

		BOOL ret = GT_Calibrate(amplifier,&scale);
		
		for(int i=0;i<size_factorArray;i++){
			float_factorArray[i]=scale.factor[i];
			float_offsetArray[i]=scale.offset[i];
		}

		env->ReleasePrimitiveArrayCritical(factorArray, float_factorArray,0);
		env->ReleasePrimitiveArrayCritical(offsetArray, float_offsetArray,0);

	return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
		
		SCALE scale;
		
		float *float_factorArray = (float*) env->GetPrimitiveArrayCritical(factorArray,0);
		int size_factorArray = env->GetArrayLength(factorArray);
		float *floatPtr_factorArray = float_factorArray;

		float *float_offsetArray = (float*) env->GetPrimitiveArrayCritical(offsetArray,0);
		int size_offsetArray = env->GetArrayLength(offsetArray);
		float *floatPtr_offsetArray = float_offsetArray;
		
		for(int i=0;i<size_factorArray;i++){
			scale.factor[i]=float_factorArray[i];
			scale.offset[i]=float_offsetArray[i];
		}
		
		BOOL ret = GT_SetScale(amplifier,&scale);

		env->ReleasePrimitiveArrayCritical(factorArray, float_factorArray,0);
		env->ReleasePrimitiveArrayCritical(offsetArray, float_offsetArray,0);

	return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_getScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
		
		SCALE scale;

		float *float_factorArray = (float*) env->GetPrimitiveArrayCritical(factorArray,0);
		int size_factorArray = env->GetArrayLength(factorArray);
		float *floatPtr_factorArray = float_factorArray;

		float *float_offsetArray = (float*) env->GetPrimitiveArrayCritical(offsetArray,0);
		int size_offsetArray = env->GetArrayLength(offsetArray);
		float *floatPtr_offsetArray = float_offsetArray;

		BOOL ret = GT_GetScale(amplifier,&scale);
		
		for(int i=0;i<size_factorArray;i++){
			float_factorArray[i]=scale.factor[i];
			float_offsetArray[i]=scale.offset[i];
		}

		env->ReleasePrimitiveArrayCritical(factorArray, float_factorArray,0);
		env->ReleasePrimitiveArrayCritical(offsetArray, float_offsetArray,0);

	return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_getFilterSpec(JNIEnv *env, jobject obj, jfloatArray filterSpecArray, jint filterID){
		int nof;
		_FILT *filterSpec;
		GT_GetNumberOfFilter(&nof);
		filterSpec = new _FILT[nof];
		BOOL ret = GT_GetFilterSpec(filterSpec);
		
		float *float_filterSpecArray = (float*) env->GetPrimitiveArrayCritical(filterSpecArray,0);
		int size_filterSpecArray = env->GetArrayLength(filterSpecArray);
		float *floatPtr_filterSpecArray = float_filterSpecArray;

		floatPtr_filterSpecArray[0] = filterSpec[filterID].fo;
		floatPtr_filterSpecArray[1] = filterSpec[filterID].fs;
		floatPtr_filterSpecArray[2] = filterSpec[filterID].fu;
		floatPtr_filterSpecArray[3] = filterSpec[filterID].order;
		floatPtr_filterSpecArray[4] = filterSpec[filterID].type;

		env->ReleasePrimitiveArrayCritical(filterSpecArray, float_filterSpecArray,0);
		delete[] filterSpec;
		return ret;
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierD_getNumberOfFilters(JNIEnv *env, jobject obj){
		int nof;
		GT_GetNumberOfFilter(&nof);
		return nof;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setBandPass(JNIEnv *env, jobject obj, jint filterID, jint channelID){
	return GT_SetBandPass(amplifier,channelID,filterID);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_getNotchFilterSpec(JNIEnv *env, jobject obj, jfloatArray notchFilterSpecArray, jint filterID){
		
		int nof;
		_FILT *notchSpec;
		GT_GetNumberOfNotch(&nof);
		notchSpec = new _FILT[nof];
		BOOL ret = GT_GetNotchSpec(notchSpec);

		float *float_notchFilterSpecArray = (float*) env->GetPrimitiveArrayCritical(notchFilterSpecArray,0);
		int size_notchFilterSpecArray = env->GetArrayLength(notchFilterSpecArray);
		float *floatPtr_notchFilterSpecArray = float_notchFilterSpecArray;

		floatPtr_notchFilterSpecArray[0] = notchSpec[filterID].fo;
		floatPtr_notchFilterSpecArray[1] = notchSpec[filterID].fs;
		floatPtr_notchFilterSpecArray[2] = notchSpec[filterID].fu;
		floatPtr_notchFilterSpecArray[3] = notchSpec[filterID].order;
		floatPtr_notchFilterSpecArray[4] = notchSpec[filterID].type;

		env->ReleasePrimitiveArrayCritical(notchFilterSpecArray, float_notchFilterSpecArray,0);
		delete[] notchSpec;
		return ret;
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierD_getNumberOfNotchFilters(JNIEnv *env, jobject obj){
		int nof;
		GT_GetNumberOfNotch(&nof);
		return nof;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setNotch(JNIEnv *env, jobject obj, jint filterID, jint channelID){
	return GT_SetNotch(amplifier,channelID,filterID);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setMode(JNIEnv *env, jobject obj, jint mode){
/*
	#define M_NORMAL		0
	#define	M_IMPEDANCE		1
	#define	M_CALIBRATE		2
	#define M_COUNTER		3
*/

	return GT_SetMode(amplifier,mode);
}

JNIEXPORT jint JNICALL Java_media_protocol_gtec_NativeAmplifierD_getMode(JNIEnv *env, jobject obj){
	
	UCHAR mode;
	GT_GetMode(amplifier,&mode);
	return mode;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array){
		
		GND ground;
		
		bool *bool_gnd_array = (bool*) env->GetPrimitiveArrayCritical(gnd_array,0);
		int size_out = env->GetArrayLength(gnd_array);
		bool *boolPtr_gnd_array = bool_gnd_array;
		
		ground.GND1 = boolPtr_gnd_array[0];
		ground.GND2 = boolPtr_gnd_array[1];
		ground.GND3 = boolPtr_gnd_array[2];
		ground.GND4 = boolPtr_gnd_array[3];

		BOOL ret = GT_SetGround(amplifier,ground);

		env->ReleasePrimitiveArrayCritical(gnd_array, bool_gnd_array,0);
		return ret;

}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_getGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array){
		
		GND ground;
		
		bool *bool_gnd_array = (bool*) env->GetPrimitiveArrayCritical(gnd_array,0);
		int size_out = env->GetArrayLength(gnd_array);
		bool *boolPtr_gnd_array = bool_gnd_array;
		
		BOOL ret = GT_GetGround(amplifier,&ground);

		boolPtr_gnd_array[0] = ground.GND1;
		boolPtr_gnd_array[1] = ground.GND2;
		boolPtr_gnd_array[2] = ground.GND3;
		boolPtr_gnd_array[3] = ground.GND4;

		env->ReleasePrimitiveArrayCritical(gnd_array, bool_gnd_array,0);
		return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setReference(JNIEnv *env , jobject obj, jbooleanArray ref_array){

		REF reference;
		
		bool *bool_ref_array = (bool*) env->GetPrimitiveArrayCritical(ref_array,0);
		int size_out = env->GetArrayLength(ref_array);
		bool *boolPtr_ref_array = bool_ref_array;
		
		reference.ref1 = boolPtr_ref_array[0];
		reference.ref2 = boolPtr_ref_array[1];
		reference.ref3 = boolPtr_ref_array[2];
		reference.ref4 = boolPtr_ref_array[3];
		
		BOOL ret = GT_SetReference(amplifier,reference);

		env->ReleasePrimitiveArrayCritical(ref_array, bool_ref_array,0);
		return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_getReference(JNIEnv *env, jobject obj, jbooleanArray ref_array){
		
		REF reference;
		
		bool *bool_ref_array = (bool*) env->GetPrimitiveArrayCritical(ref_array,0);
		int size_out = env->GetArrayLength(ref_array);
		bool *boolPtr_ref_array = bool_ref_array;
		
		BOOL ret = GT_GetReference(amplifier,&reference);

		boolPtr_ref_array[0] = reference.ref1;
		boolPtr_ref_array[1] = reference.ref2;
		boolPtr_ref_array[2] = reference.ref3;
		boolPtr_ref_array[3] = reference.ref4;

		env->ReleasePrimitiveArrayCritical(ref_array, bool_ref_array,0);
		return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setBipolar(JNIEnv *env, jobject obj, jintArray channels){
		
		CHANNEL channel;

		double *double_array = (double*) env->GetPrimitiveArrayCritical(channels,0);
		int size = env->GetArrayLength(channels);
		double *doublePtr_array = double_array;
		
		channel.Channel1 = doublePtr_array[0];
		channel.Channel2 = doublePtr_array[1];	
		channel.Channel3 = doublePtr_array[2];
		channel.Channel4 = doublePtr_array[3];
		channel.Channel5 = doublePtr_array[4];
		channel.Channel6 = doublePtr_array[5];
		channel.Channel7 = doublePtr_array[6];
		channel.Channel8 = doublePtr_array[7];
		channel.Channel9 = doublePtr_array[8];
		channel.Channel10 = doublePtr_array[9];
		channel.Channel11 = doublePtr_array[10];
		channel.Channel12 = doublePtr_array[11];
		channel.Channel13 = doublePtr_array[12];
		channel.Channel14 = doublePtr_array[13];
		channel.Channel15 = doublePtr_array[14];
		channel.Channel16 = doublePtr_array[15];

		BOOL ret = GT_SetBipolar(amplifier, channel);
		
		//cout << "success: " << ret <<endl; 

		env->ReleasePrimitiveArrayCritical(channels,double_array,0);
		return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setDRLChannel(JNIEnv *env, jobject obj, jintArray channels){
		
		CHANNEL channel;

		double *double_array = (double*) env->GetPrimitiveArrayCritical(channels,0);
		int size = env->GetArrayLength(channels);
		double *doublePtr_array = double_array;
		
		channel.Channel1 = doublePtr_array[0];
		channel.Channel2 = doublePtr_array[1];	
		channel.Channel3 = doublePtr_array[2];
		channel.Channel4 = doublePtr_array[3];
		channel.Channel5 = doublePtr_array[4];
		channel.Channel6 = doublePtr_array[5];
		channel.Channel7 = doublePtr_array[6];
		channel.Channel8 = doublePtr_array[7];
		channel.Channel9 = doublePtr_array[8];
		channel.Channel10 = doublePtr_array[9];
		channel.Channel11 = doublePtr_array[10];
		channel.Channel12 = doublePtr_array[11];
		channel.Channel13 = doublePtr_array[12];
		channel.Channel14 = doublePtr_array[13];
		channel.Channel15 = doublePtr_array[14];
		channel.Channel16 = doublePtr_array[15];

		BOOL ret = GT_SetDRLChannel(amplifier, channel);
		//cout << "success: " << ret <<endl; 

		env->ReleasePrimitiveArrayCritical(channels,double_array,0);
		return ret;
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_enableSC(JNIEnv *env, jobject obj, jboolean flag){
	return GT_EnableSC(amplifier, flag);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setSlave(JNIEnv *env, jobject obj, jboolean flag){
	return GT_SetSlave(amplifier, flag);
}

JNIEXPORT jboolean JNICALL Java_media_protocol_gtec_NativeAmplifierD_setDAC(JNIEnv *env, jobject obj, jbyte waveShape, jint amplitude, jint frequency, jint offset){
	
	DAC dac;
	
	dac.Amplitude = amplitude;
	dac.Frequency = frequency;
	dac.Offset = offset;
	dac.WaveShape = waveShape;

	BOOL ret = GT_SetDAC(amplifier,dac);
	//cout << "success: " << ret <<endl;
	return ret;
}
