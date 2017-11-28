#include "media_protocol_gtec_NativeAmplifierD.h"
#include "stdafx.h"

#ifndef USBAMP_H
#define USBAMP_H

class USBAmp{

	public:
	 USBAmp();
	~USBAmp();

	HANDLE GetAmplifier();//
	jboolean ProbeDevice(JNIEnv *env , jobject obj, jint port);//
	jboolean OpenDevice(JNIEnv *env , jobject obj , jint iPortNumber);//
	jboolean OpenDevice(JNIEnv *env , jobject obj, jstring serial);//
	jboolean CloseDevice(JNIEnv *env , jobject obj);//
	jint GetData(JNIEnv *env , jobject obj, jbyteArray buffer);
	jboolean SetBufferSize(JNIEnv *env, jobject obj, jint buffersize);
	jboolean SetSampleRate(JNIEnv *env, jobject obj, jint samplerate);
	jboolean Start(JNIEnv *env, jobject obj);
	jboolean Stop(JNIEnv *env, jobject obj);
	jboolean SetChannels(JNIEnv *env, jobject obj, jintArray channels);
	jboolean SetDigitalOut(JNIEnv *env, jobject obj, jint port, jboolean flag);
	jboolean GetDigitalIO(JNIEnv *env, jobject obj, jbooleanArray digital_in, jbooleanArray digital_out);
	void GetLastError(JNIEnv *env, jobject obj);
	jint GetLastErrorCode(JNIEnv *env, jobject obj);
	jstring GetLastErrorMessage(JNIEnv *env, jobject obj);
	jboolean ResetTransfer(JNIEnv *env, jobject obj);
	jstring GetSerial(JNIEnv *env, jobject obj);
	jboolean EnableTriggerLine(JNIEnv *env, jobject obj, jboolean flag);
	jdouble GetImpedance(JNIEnv *env, jobject obj, jint channel);
	jboolean Calibrate(JNIEnv *env , jobject obj, jfloatArray factorArray, jfloatArray offsetArray);
	jboolean SetScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray);
	jboolean GetScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray); 
	jboolean GetFilterSpec(JNIEnv *env, jobject obj, jfloatArray filterSpecArray, jint filterID);
	jint GetNumberOfFilters(JNIEnv *env, jobject obj);
	jboolean SetBandPass(JNIEnv *env, jobject obj, jint filterID, jint channelID);
	jboolean GetNotchFilterSpec(JNIEnv *env, jobject obj, jfloatArray notchFilterSpecArray, jint filterID);
	jint GetNumberOfNotchFilters(JNIEnv *env, jobject obj);
	jboolean SetNotch(JNIEnv *env, jobject obj, jint filterID, jint channelID);
	jboolean SetMode(JNIEnv *env, jobject obj, jint mode);
	jint GetMode(JNIEnv *env, jobject obj);
	jboolean SetGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array);
	jboolean GetGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array);		
	jboolean SetReference(JNIEnv *env , jobject obj, jbooleanArray ref_array);
	jboolean GetReference(JNIEnv *env, jobject obj, jbooleanArray ref_array);
	jboolean SetBipolar(JNIEnv *env, jobject obj, jintArray channels);
	jboolean SetDRLChannel(JNIEnv *env, jobject obj, jintArray channels);
	jboolean EnableSC(JNIEnv *env, jobject obj, jboolean flag);
	jboolean SetSlave(JNIEnv *env, jobject obj, jboolean flag);
	jboolean SetDAC(JNIEnv *env, jobject obj, jbyte waveShape, jint amplitude, jint frequency, jint offset);
	jint GetHeaderSize(JNIEnv * env, jobject obj);
	jint GetSizeOfFloat(JNIEnv *env, jobject obj);
	jfloat GetDriverVersion(JNIEnv *env , jobject obj);

	private:
	HANDLE amplifier; 
	WORD wErrorCode;
	CHAR pLastError;
	HANDLE ampEvent;
};
#endif

USBAmp::USBAmp(){
}

USBAmp::~USBAmp(){
}

HANDLE USBAmp::GetAmplifier(){
	return amplifier;
}

jint USBAmp::GetHeaderSize(JNIEnv * env, jobject obj){
	return HEADER_SIZE;
}

jint USBAmp::GetSizeOfFloat(JNIEnv *env, jobject obj){
	return sizeof(float);
}

jfloat USBAmp::GetDriverVersion(JNIEnv *env , jobject obj){
	return GT_GetDriverVersion();
}

jboolean USBAmp::ProbeDevice(JNIEnv *env , jobject obj, jint port){
	
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

jboolean USBAmp::OpenDevice(JNIEnv *env , jobject obj , jint iPortNumber){
	
	//PLEASE PROBE THE DEVICE BEFORE TRYING TO OPEN IT!!!
	amplifier = GT_OpenDevice(iPortNumber);
	ampEvent = CreateEvent(NULL,FALSE,FALSE,"MY_GTEC_EVENT");
	jboolean success = false;
	if(amplifier != NULL){
		success = true;
	}
	return success;
}

jboolean USBAmp::OpenDevice(JNIEnv *env , jobject obj, jstring serial){
	LPSECURITY_ATTRIBUTES lpEventAttributes;
	LPSTR ser = (LPSTR)env->GetStringUTFChars(serial, 0);
	amplifier = GT_OpenDeviceEx(ser);
	ampEvent = CreateEvent(NULL,FALSE,FALSE,"MY_GTEC_EVENT");
	jboolean success = false;
	if(amplifier != NULL){
		success = true;
	}
	return success;
}

jboolean USBAmp::CloseDevice(JNIEnv *env , jobject obj){
	
	BOOL success = GT_CloseDevice(&amplifier);
	if(success){
		CloseHandle(ampEvent);
	}
	return success;
}

jint USBAmp::GetData(JNIEnv *env , jobject obj, jbyteArray buffer){
		OVERLAPPED ov;
		ov.hEvent = ampEvent;
		ov.Offset = 0;
		ov.OffsetHigh = 0;

		byte *byte_array = (byte*) env->GetPrimitiveArrayCritical(buffer,0);
		int bufLen = env->GetArrayLength(buffer);
		byte *bytePtr_array = byte_array;
		int validSamples = 0;

		DWORD dwBytesReceived = 0;
		if (env->MonitorEnter(obj) != JNI_OK) {};

		GT_GetData(amplifier, bytePtr_array,bufLen,&ov);
	
		DWORD dwOVret = WaitForSingleObject(ampEvent,INFINITE);

			if(dwOVret == WAIT_TIMEOUT)
			{
				MessageBox(NULL,"Timeout occured\n", "WARNING", MB_ICONWARNING);
				GT_ResetTransfer(amplifier);

			}else{
				GetOverlappedResult(amplifier,&ov,&dwBytesReceived,FALSE);
				validSamples = (int)((dwBytesReceived-HEADER_SIZE)/sizeof(float));
			}
		
		if (env->MonitorExit(obj) != JNI_OK) {};

		env->ReleasePrimitiveArrayCritical(buffer,byte_array,0);
		
		return validSamples;
}

jboolean USBAmp::SetBufferSize(JNIEnv *env, jobject obj, jint buffersize){
	return GT_SetBufferSize(amplifier,buffersize);
}
jboolean USBAmp::SetSampleRate(JNIEnv *env, jobject obj, jint samplerate){
	return GT_SetSampleRate(amplifier,samplerate);
}
jboolean USBAmp::Start(JNIEnv *env, jobject obj){
	return GT_Start(amplifier);
}
jboolean USBAmp::Stop(JNIEnv *env, jobject obj){
	return GT_Stop(amplifier);
}
jboolean USBAmp::SetChannels(JNIEnv *env, jobject obj, jintArray channels){
		
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

jboolean USBAmp::SetDigitalOut(JNIEnv *env, jobject obj, jint port, jboolean flag){
	return GT_SetDigitalOut(amplifier,port,flag);
}

jboolean USBAmp::GetDigitalIO(JNIEnv *env, jobject obj, jbooleanArray digital_in, jbooleanArray digital_out){
		
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
void USBAmp::GetLastError(JNIEnv *env, jobject obj){
	GT_GetLastError(&wErrorCode,&pLastError);
}

jint USBAmp::GetLastErrorCode(JNIEnv *env, jobject obj){
	return (jint)wErrorCode;
}
jstring USBAmp::GetLastErrorMessage(JNIEnv *env, jobject obj){
	return env->NewStringUTF(&pLastError);
}

jboolean USBAmp::ResetTransfer(JNIEnv *env, jobject obj){
	return GT_ResetTransfer(amplifier);
}

jstring USBAmp::GetSerial(JNIEnv *env, jobject obj){
	CString strSerial;
	GT_GetSerial(amplifier, strSerial.GetBuffer(256), 256);
	return env->NewStringUTF(strSerial);
}

jboolean USBAmp::EnableTriggerLine(JNIEnv *env, jobject obj, jboolean flag){
	return GT_EnableTriggerLine(amplifier,flag);
}

jdouble USBAmp::GetImpedance(JNIEnv *env, jobject obj, jint channel){
	double impedance;
	GT_GetImpedance(amplifier,(UCHAR)channel,&impedance);
	return impedance;
}

jboolean USBAmp::Calibrate(JNIEnv *env , jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
	
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

jboolean USBAmp::SetScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
		
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

jboolean USBAmp::GetScale(JNIEnv *env, jobject obj, jfloatArray factorArray, jfloatArray offsetArray){
			
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

jboolean USBAmp::GetFilterSpec(JNIEnv *env, jobject obj, jfloatArray filterSpecArray, jint filterID){
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

jint USBAmp::GetNumberOfFilters(JNIEnv *env, jobject obj){
		int nof;
		GT_GetNumberOfFilter(&nof);
		return nof;
}

jboolean USBAmp::SetBandPass(JNIEnv *env, jobject obj, jint filterID, jint channelID){
	return GT_SetBandPass(amplifier,channelID,filterID);
}

jboolean USBAmp::GetNotchFilterSpec(JNIEnv *env, jobject obj, jfloatArray notchFilterSpecArray, jint filterID){
		
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

jint USBAmp::GetNumberOfNotchFilters(JNIEnv *env, jobject obj){
		int nof;
		GT_GetNumberOfNotch(&nof);
		return nof;
}

jboolean USBAmp::SetNotch(JNIEnv *env, jobject obj, jint filterID, jint channelID){
	return GT_SetNotch(amplifier,channelID,filterID);
}

jboolean USBAmp::SetMode(JNIEnv *env, jobject obj, jint mode){
/*
	#define M_NORMAL		0
	#define	M_IMPEDANCE		1
	#define	M_CALIBRATE		2
	#define M_COUNTER		3
*/

	return GT_SetMode(amplifier,mode);
}

jint USBAmp::GetMode(JNIEnv *env, jobject obj){
	
	UCHAR mode;
	GT_GetMode(amplifier,&mode);
	return mode;
}

jboolean USBAmp::SetGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array){
		
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

jboolean USBAmp::GetGround(JNIEnv *env, jobject obj, jbooleanArray gnd_array){
		
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

jboolean USBAmp::SetReference(JNIEnv *env , jobject obj, jbooleanArray ref_array){

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

jboolean USBAmp::GetReference(JNIEnv *env, jobject obj, jbooleanArray ref_array){
		
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

jboolean USBAmp::SetBipolar(JNIEnv *env, jobject obj, jintArray channels){
		
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

jboolean USBAmp::SetDRLChannel(JNIEnv *env, jobject obj, jintArray channels){
		
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

jboolean USBAmp::EnableSC(JNIEnv *env, jobject obj, jboolean flag){
	return GT_EnableSC(amplifier, flag);
}

jboolean USBAmp::SetSlave(JNIEnv *env, jobject obj, jboolean flag){
	return GT_SetSlave(amplifier, flag);
}

jboolean USBAmp::SetDAC(JNIEnv *env, jobject obj, jbyte waveShape, jint amplitude, jint frequency, jint offset){
		
	DAC dac;
	
	dac.Amplitude = amplitude;
	dac.Frequency = frequency;
	dac.Offset = offset;
	dac.WaveShape = waveShape;

	BOOL ret = GT_SetDAC(amplifier,dac);
	//cout << "success: " << ret <<endl;
	return ret;
}