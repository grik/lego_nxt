package grik.sieci;

public class zLSiec{
int inputSize = 7;
int hiddenSize = 7;
int outputSize = 5;
double[] hiddenValues = new double[hiddenSize];
double[] outputValues = new double[outputSize];
double[] inputValues = new double[inputSize];
double[][] hiddenWeights = new double[][]{
{-1.84240155851,-0.16437485461,-1.20158533978,-0.708648369224,0.431673401757,-1.23162477535,0.0640142428046},
{-1.14239829649,-3.86298146434,-2.17692662714,0.132499891636,-0.312808684662,-1.72995998541,-0.615994840492},
{0.763834408967,-0.237280920378,0.183777975688,-0.430074852637,-0.124997383296,0.311703677304,-0.138615787701},
{-1.44021490636,1.6229590803,0.024259495203,-0.139767945526,-0.760772679694,-1.05381574232,2.02614059754},
{-0.581803598448,-0.272824436827,-1.2309578561,6.7750466143,-2.07566808524,0.376328881913,-0.580056160602},
{-4.42495038491,-1.0160198835,-2.19459581161,-0.0589754026536,1.59281475484,-0.388174355617,3.10060464575},
{-1.92125806978,-1.03334935634,-0.985028776974,-3.88913089106,0.564278731557,1.94416463851,-4.0145542951},
};
double[] hiddenBiases = new double[]{-0.470431346376,2.35662046566,-0.245546860951,-1.40488575172,2.0238276511,1.26418937879,0.0163489218923};
double[][] outputWeights = new double[][]{
{1.68040068581,-1.24618721595,-3.44274587729,-1.31659206196,0.712283867401,-0.937610950768,-0.300696031861},
{-1.28929700018,0.0222450030294,-0.908133009205,-0.37103157612,-2.81130117008,0.0116152963166,0.399944314665},
{-1.91711446041,1.60940606103,-0.56246011463,1.53173095454,-0.292313324771,-1.51391920179,0.297344158767},
{0.200264093779,0.252873864542,2.77451506347,0.10199682617,1.26773869915,1.53020018168,-2.08887788346},
{0.701787793294,-0.310860367767,0.50602958095,0.00877823343996,0.33463266258,0.213995473806,1.94244367892},
};
double[] outputBiases = new double[]{0.0572508355079,1.5396052618,0.362041004633,-1.9051025831,2.14438036045};
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
