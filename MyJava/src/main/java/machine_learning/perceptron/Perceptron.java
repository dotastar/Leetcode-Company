package machine_learning.perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import machine_learning.common.Tools;

public class Perceptron {
	
	private final float LearningRate = 0.5f;
	private float offset = 0;
	private float[] w;
	
	public static void main(String[] args){
		Perceptron model = new Perceptron();
		try {
			model.train("./svm_dataset/data1_linear.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("end");
	}
	
	public void train(String filepath) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		float[][] data = loadAllData(filepath);
		w = new float[data[0].length-1];	//exclude y
		//calcModel(data, w, 0, LearningRate, 0, 0, data.length-1);
		calcModel(data, w, 0, LearningRate);
		System.out.println("Learning Rate:" + LearningRate + ", w:"+w[0]+" "+w[1]+", b:"+offset);
		System.out.println("Finish training");
	}
	
	
	/**
	 * Minimize Loss function and calculate the parameter w, b
	 * L(w,b) = -sum(y_i(w*x_i + b))
	 * @param datalist, train data
	 * @param w, vector w
	 * @param b, offset
	 * @param learnRate, learning rate
	 * @param calcIndex, the data index need to be calculated, when index meet endCount, set calcIndex = 0
	 * @param count, count the time which the model classified(calculated) right
	 * @param endCount, the max number of data length
	 */
	public void calcModel(float[][] datalist, float[] w, float b, float learnRate, int calcIndex, int count, int endCount){
		System.out.println("calc once");	//count
		if(count==endCount){
			offset = b;
			return;
		}
		
		if(calcIndex==endCount+1)
			calcIndex = 0;
		
		float[] data = datalist[calcIndex];
		if(sign(calculateY(data, w, b))==sign(data[0]))
			calcModel(datalist, w, b, learnRate, ++calcIndex, ++count, endCount);
		else{
			updateW(w, data, data[0], learnRate);	//update w
			b += data[0];	//update b
			calcModel(datalist, w, b, learnRate, ++calcIndex, 0, endCount);
		}
	}
	
	//non-recursion version
	private void calcModel(float[][] datalist, float[] w, float b, float learnRate){
		int count = 0;
		int endCount = datalist.length - 1;
		int currIndex = 0;
		
		int times = 0;
		while(count!=endCount){
			times++;
			System.out.println("Loop:"+times);
			
			if(currIndex == endCount+1)
				currIndex = 0;
			
			float[] data = datalist[currIndex];	
			if(sign(calculateY(data, w, b))!=sign(data[0])){			
				updateW(w, data, data[0], learnRate);	//update w
				b += data[0];	//update b
				count=0;
			}
			else
				count++;

			currIndex++;
		}
		
		offset = b;
	}
	

	
	private boolean sign(float y){
		if(y>0)
			return true;
		else
			return false;
	}
	
	
	/*
	 * Calculate model value y
	 * y = f(x) = w*x + b
	 */
	private float calculateY( float[] data, float[] w, float b){
		float y = 0;
		for(int i=0; i<w.length; i++){
			y += w[i]*data[i+1];
		}
		y += b;
		return y;
	}
	
	
	/*
	 * Update parameter w
	 * w = w + x*y*learnRate
	 */
	private void updateW(float[] w, float[] data_x, float data_y, float learnRate){
		for(int i=0; i<w.length; i++){
			w[i] += data_x[i+1]*data_y*learnRate;	//data_x starts from 1
		}
	}
	
	private float[][] loadAllData(String filepath) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		String[] text = Tools.ReadFile_String(new File(filepath));
		float[][] result = new float[text.length][3];
		
		int i=0;
		for(String dataRow : text){
			String[] numbers = dataRow.split("\t");
			result[i][0] = Float.valueOf(numbers[0]);	//1 or -1
			result[i][1] = Float.valueOf(numbers[1]);	//x1
			result[i][2] = Float.valueOf(numbers[2]);	//x2
			
			i++;
		}
		
		return result;
	}

}
