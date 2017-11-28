#include <stdio.h>
#include <stdlib.h>
#include <alnpp.h>
#include <dtree.h>
#include <time.h>
#include <math.h>
#include <iostream>
using namespace std;
#include "classification_ALNClassifier.h"

#define NDIM 100
static DTREE* pDtree=NULL;                     /* pointer to DTREE structure */
static int nErrCode;                           /* library function return value */
static int sumOfDimensons;                     /* sum of all Dimensions */
static char szErrMsg[256];                     /* error message */
static double dblOutput;                       /* result of Dtree evaluation */

JNIEXPORT jdouble JNICALL Java_classification_ALNClassifier_init(JNIEnv *env, jobject obj, jstring aFilename){

  cout << "Opening tree" << endl;
	
  const char *c_string = env->GetStringUTFChars(aFilename, 0);

  nErrCode = ReadDtree(c_string,&pDtree);

  /* check error return */  
  if (nErrCode == DTR_NOERROR)
  {
	  cout << "succesfully parsed!" << endl;
  }
  else
  {
    GetDtreeError(nErrCode, szErrMsg, sizeof(szErrMsg));
	  cout << "error parsing Tree" << endl;
    return -1;
  }    

  /* make sure that each output variable index is NDIM -1 = 99 */
  sumOfDimensons = pDtree->nOutputIndex;
  cout << sumOfDimensons << endl;

  if (sumOfDimensons > 99)
  {
	  cout << "Fatal error: The dimension should not exceed 99" << endl;
    return -1;
  }
	  cout << "succesfully finished" << endl;
	  return 0;
}

JNIEXPORT jdouble JNICALL Java_classification_ALNClassifier_classify(JNIEnv *env, jobject obj, jdoubleArray inputArray){

		double *double_array = (double*) env->GetPrimitiveArrayCritical(inputArray,0);
		int size = env->GetArrayLength(inputArray);
		double *doublePtr_array = double_array;
		
		if ((nErrCode = EvalDtree(pDtree, doublePtr_array, &dblOutput, NULL)) == DTR_NOERROR){
		
		env->ReleasePrimitiveArrayCritical(inputArray,double_array,0);

		return (jdouble)dblOutput;
		}
	
	return -1;

}