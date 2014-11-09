package interview.company.palantir;

import java.util.Stack;

/**
 * Ѱ�Һ�Ϊ��ֵ�Ķ���� - �Ӽ����������
 * ������������ n �� sum��������1��2��3.......n �� ����ȡ������,
ʹ��͵���sum ,Ҫ���������еĿ�������г�����
 * @author Asia
 *
 */
public class Sum_Subset {

	private static Stack<Integer> records = new Stack<Integer>();
	
	public static void main(String[] args) {
		subsetSum(7,5);
	}
	
	/*
	 * Recursion solution 
	 */
	public static  void subsetSum(int sum, int n){
		//recursion
		if(sum<=0||n<=0){
			return;
		}

		if(sum==n){
			for(Integer num : records)
				System.out.print(num+" ");
			System.out.print(n);
			System.out.println();
		}
		
		records.push(n);
		subsetSum(sum-n, n-1);
		
		records.pop();
		subsetSum(sum, n-1);
	
	}
	
}
