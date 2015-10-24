package machine_learning.regression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import machine_learning.common.Tools;

public class Logistic {
	private float[] w;
	private float[] gradient;
	private float[] dir;

	private final float StopRate = 0.0005f;
	private final int MaxRound = 100;

	// data
	private float[] rightArr;
	private float[] wrongArr;
	private float[][] xArr;
	
	//others
	private DecimalFormat formatter;

	public static void main(String[] args) {

		Logistic model = new Logistic();
		String fileAddress = "./regression_dataset/LRTrainNew.txt";
		try {
			model.initialization(fileAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.gradDescent();
	}

	public void initialization(String address)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		List<float[]> data = Tools.ReadFile_Number(new File(address));
		parseData(data, 5);
		gradient = new float[5];
		dir = new float[5];
		
		 formatter = new DecimalFormat("#0.00%");
	}

	public void parseData(List<float[]> data, int xLength) {
		int length = data.size();
		rightArr = new float[length];
		wrongArr = new float[length];
		xArr = new float[length][xLength];
		for (int i = 0; i < length; i++) {
			rightArr[i] = data.get(i)[0];
			wrongArr[i] = data.get(i)[1];
			xArr[i][xLength-1] = 1; // b
			for (int j = 0; j < xLength-1; j++) {
				xArr[i][j] = data.get(i)[j+2];
				//System.out.print(xArr[i][j] + " ");
			}
			//System.out.println();
		}
		System.out.println("Parse data over!");
	}

	public void gradDescent() {
		if (xArr.length < 1) {
			System.out.println("Error! There is no data!");
			return;
		}

		float stepLength = 1;
		float tolerence = 0.5f;
		float halfFactor = 0.5f;
		float convergenceRate = 0;
		int i = 0;

		w = generateVecRandom(xArr[0].length);
		float loss = calcTotalLoss(rightArr, wrongArr, xArr, w);
		
		do {
			calcGradient(gradient, rightArr, wrongArr, xArr, w);
			printVec(gradient,"gradient");
			updateVector(dir, gradient, -1);
			//printVec(dir,"updated dir");
			normalize(dir);
			//printVec(dir,"normalized dir");
			float newloss = lineSearch(loss, w, gradient, dir, rightArr,
					wrongArr, xArr, stepLength, tolerence, halfFactor);
			convergenceRate = (newloss - loss) / loss;
			System.out.println(++i + " Converge Rate:" + formatter.format(convergenceRate) +  " loop, old loss:" + loss + " new loss:" + newloss);
			loss = newloss;
		} while (convergenceRate < StopRate);
		System.out.println("End");
	}


	private float lineSearch(float currLoss, float[] w, float[] gradient,
			float[] dir, float[] rightArrIn, float[] wrongArrIn, float[][] xArrIn,
			float stepLength, float tolerence, float halfFactor) {
		int length = w.length;
		float newLoss = 0;
		float[] w_New = new float[length];
		float[] dir_Tmp = new float[length];		
		int round = 0;
		float threshold;
		do {
			
			updateVector(dir_Tmp, dir, 1);	//set dir_Tmp = dir
			updateVector(w_New, w, 1);		//set w_New = w
			
			multiVector(dir_Tmp, stepLength);	//dir_Tmp = dir*stepLength
			addVector(w_New, dir_Tmp, length); //equals: w_New = w + dir*stepLength
			//printVec(w_New,"w_New");
			
			newLoss = calcTotalLoss(rightArrIn, wrongArrIn, xArrIn, w_New);
			stepLength *= halfFactor;
			round++;
			if (round == MaxRound) {
				System.out.println("lineSearch meet the max round!");
				break;
			}
			threshold = tolerence * Math.abs(innerProduct(gradient,
							subVector_NewVec(w_New, w, length), length)); 
			System.out.println("round "+round + " || " + (currLoss - newLoss)+" _ " +threshold);
		} while ((currLoss - newLoss) < threshold);

		// update w to w_New
		for (int i = 0; i < length; i++) {
			w[i] = w_New[i];
			System.out.print(w[i] + " ");
		}
		System.out.println();
		return newLoss;
	}

	private float calcTotalLoss(float[] rightArrIn, float[] wrongArrIn,
			float[][] xArrIn, float[] w) {
		float totalLoss = 0;
		for (int i = 0; i < xArrIn.length; i++) {
			float[] x = xArrIn[i];
			float rightNum = rightArrIn[i];
			float wrongNum = wrongArrIn[i];

			totalLoss += calcSingleLoss(rightNum, wrongNum, w, x, x.length);
		}
		//System.out.println("total loss:" + totalLoss);
		return totalLoss;
	}
	
	private float calcSingleLoss(float rightNum, float wrongNum, float[] w,
			float[] x, int length) {
		float loss = 0;
		float prob = calcLogit(w, x, length);
		//System.out.println(prob);
		loss = -(rightNum * (float) Math.log(prob) + wrongNum
				* (float) Math.log(1 - prob));
		//System.out.println(loss);
		return loss;
	}

	private void calcGradient(float[] grad, float[] rightArrIn, float[] wrongArrIn,
			float[][] xArrIn, float[] w) {
		float[] tmpVec = new float[w.length]; 
		for (int i = 1; i < xArrIn.length; i++) {
			float[] x = xArrIn[i];
			float rightNum = rightArrIn[i];
			float wrongNum = wrongArrIn[i];
			float prob = calcLogit(w, x, x.length);
			float coefficient = -(rightNum * (1 - prob) - wrongNum * prob);
			updateVector(tmpVec, x, 1);
			multiVector(tmpVec, coefficient);
			addVector(grad, tmpVec, grad.length);
		}
	}

	private float calcLogit(float[] w, float[] x, int length) {
		float wx = -innerProduct(w, x, length);
		if(wx>64){
			System.out.println("*************************************************");
			System.out.println("Error! wx Overflow! wx="+wx);
			System.out.println("*************************************************");
			//wx = 64;	//in case of overflow
		}
		float prob =  (1 / (1 + (float)Math.pow(Math.E, wx)));
		return prob;
	}
	
	private void normalize(float[] vec){
		int length = vec.length;
		double norm = 0;
		for(int i=0; i<length; i++)
			norm += vec[i]*vec[i];
		
		norm = Math.sqrt(norm);
		
		for(int i=0; i<length; i++)
			vec[i] /= norm;	
	}
	
	private void updateVector(float[] vec, float[] instead, float factor){
		for(int i=0; i<vec.length; i++)
			vec[i] = factor*instead[i];
	}

	private void multiVector(float[] vec, float factor) {
		for (int i = 0; i < vec.length; i++)
			vec[i] *= factor;
	}

	private void addVector(float[] vec, float[] increment, int length) {
		for (int i = 0; i < length; i++)
			vec[i] += increment[i];
	}

	private float[] subVector_NewVec(float[] vec, float[] reduction, int length) {
		float[] newVec = new float[length];
		for (int i = 0; i < length; i++)
			newVec[i] = vec[i] - reduction[i];
		return newVec;
	}

	private float innerProduct(float[] vec1, float[] vec2, int length) {
		float val = 0;
		for (int i = 0; i < length; i++)
			val += vec1[i] * vec2[i];
		return val;
	}
	

	private float[] generateVecRandom(int length) {
		Random ran = new Random();
		float[] vec = new float[length];
		for (int i = 0; i < vec.length; i++)
			vec[i] = ran.nextFloat();
		return vec;
	}
	
	public void printVec(float[] vec, String name){
		System.out.print(name + ":");
		for(int i=0; i<vec.length; i++)
			System.out.print(vec[i] + " ");
		System.out.println();
	}
	
	public void printVec(float[] vec){
		for(int i=0; i<vec.length; i++)
			System.out.print(vec[i] + " ");
		System.out.println();
	}
}
