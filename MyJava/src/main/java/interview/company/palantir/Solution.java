package interview.company.palantir;

import java.io.*;
import java.util.Arrays;
public class Solution {
	private static int[][] nums = new int[1024][1024];
	private static int[][] classes = new int[1024][1024];
	
	private static int N;
	private static int sinkCount;
    public static void main(String args[] ) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("src/Palantir/input"));
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		while((line=br.readLine())!=null){
			sinkCount=0;
			N = Integer.parseInt(line.trim());
			for(int i = 0 ; i < N ; i++){
				line  = br.readLine();
				String[] dats = line.split(" ");
				for(int j = 0; j < N; j++){
					nums[i][j] = Integer.parseInt(dats[j]);
					classes[i][j] = -1;
				}
			}//end initialize
			
			//find route for each node
			for(int i=0; i<N; i++){
				for(int j=0; j<N; j++){
					findRoute(i,j);
				}
			}
			
			printClasses();
			
			//count classes, fill into basins
			int[] basins = new int[sinkCount];
			for(int i=0; i<N; i++){
				for(int j=0; j<N; j++){
					basins[classes[i][j]]++;
				}
			}

			//sort and output
			Arrays.sort(basins);
			System.out.print(basins[basins.length-1]);
			for(int i=basins.length-2; i>=0; i--)
				System.out.print(" "+basins[i]);
			System.out.println();
		}
		
		br.close();
    }
    
    
    /*
     * Check boundary of matrix[N][N]
     */
    public static boolean checkBound(int x, int y){
    	if(x<0 || x>=N || y<0 || y>=N)
    		return false;
    	else
    		return true;
    }
    
    
    /**
     * Recursively find route for the node(x, y),
     * update every node in the route after meet the sink
     * or meet the visited node while routing
     * @param x
     * @param y
     * @return
     */
    public static int findRoute(int x, int y){
    	
    	int[] xy = findBestNeighbor(x,y);
    	//System.out.println(x+" "+y+"; "+xy[0]+" "+xy[1]);
    	
    	if(classes[xy[0]][xy[1]]>-1){
    		//visited,  update classes and return
    		classes[x][y] = classes[xy[0]][xy[1]];
    		return classes[xy[0]][xy[1]];
    	}else if(xy[0]==x&&xy[1]==y){	
    		//is a sink, update classes and return
    		classes[x][y] = sinkCount++;
    		return classes[x][y];
    	}else{
    		//keep finding the sink or visited node
        	classes[x][y] = findRoute(xy[0],xy[1]);	
    	}
    	
    	return classes[xy[0]][xy[1]];
    }
    
    
    public static int[] findBestNeighbor(int x, int y){
    	int[] xy = new int[2];
    	int min = Integer.MAX_VALUE;
    	for(int i=-1; i<=1; i++){
    		for(int j=-1; j<=1; j++){
    			if(checkBound(x+i,y+j)){
    				if(nums[x+i][y+j]<min){
    					min = nums[x+i][y+j];
    					xy[0] = x+i;
    					xy[1] = y+j;
    				}
    			}
    		}
    	}
    	
    	return xy;
    }
    
    public static void printClasses(){
    	for(int i=0; i<N; i++){
    		for(int j=0; j<N; j++){
    			System.out.print(classes[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
}