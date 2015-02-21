package grik.sieci;

public class krawedz_nn{
int inputSize = 4;
int hiddenSize = 3;
int outputSize = 2;
double[] hiddenValues = new double[hiddenSize];
double[] outputValues = new double[outputSize];
double[] inputValues = new double[inputSize];
double[][] hiddenWeights = new double[][]{
{-0.826395902302,-5.09717708295,-0.35839157147,-2.87205989602},
{-0.174679306923,0.871698764786,2.62256151806,2.95747104269},
{-0.500814543845,-0.550131793492,-0.829514533089,-0.637980856866},
};
double[] hiddenBiases = new double[]{5.01196297251,-3.21976120669,-0.892117776477};
double[][] outputWeights = new double[][]{
{1.59774942188,0.314516059427,-0.679477303194},
{-2.44239799418,-1.43796077792,-1.57129227583},
};
double[] outputBiases = new double[]{0.426449441594,-0.354988711752};
public void calcHiddenValues(){
  double sum=0;
  for (int h=0;h<hiddenSize;h++){
    sum=0;
    for (int i=0;i<inputSize;i++){
      sum += hiddenWeights[h][i] * inputValues[i];
    }
    sum=sum+hiddenBiases[h];
    hiddenValues[h] = (Math.pow(Math.E, sum)-Math.pow(Math.E, -sum))/(Math.pow(Math.E, sum)+Math.pow(Math.E, -sum));
  }
}

public void calcOutputValues(){
  double sum=0;
  for (int out=0;out<outputSize;out++){
    sum=0;
    for (int h=0;h<hiddenSize;h++){
      sum += outputWeights[out][h] * hiddenValues[h];
    }
    sum=sum+outputBiases[out];
    outputValues[out] = (Math.pow(Math.E, sum)-Math.pow(Math.E, -sum))/(Math.pow(Math.E, sum)+Math.pow(Math.E, -sum));
  }
}
public int outputValue(){
  int out = -1;
double max=-100.0;
  for (int i=0;i<outputSize;i++){
    if (outputValues[i] > max){
    max=outputValues[i];
      out=i;
    }
  }
  return out;
}
public int getClass(double[] Input){
  inputValues = Input;
  calcHiddenValues();
  calcOutputValues();
  return outputValue();
}

}
