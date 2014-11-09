package interview.leetcode;

import java.util.Stack;

/**
 * Given an absolute path for a file (Unix-style), simplify it.
 * 
 * For example, path = "/home/", => "/home" path = "/a/./b/../../c/", => "/c"
 * 
 * @author yazhoucao
 * 
 */
public class Simplify_Path {

	public static void main(String[] args) {
		System.out.println(simplifyPath("///"));
		System.out.println(simplifyPath("/"));
		System.out.println(simplifyPath("/a/./b/../../c/"));
		System.out.println(simplifyPath2("/.."));
	}
	
	/**
	 * Second time practice
	 * Use string.split() to reduce a some amount of code.
	 */
    public static String simplifyPath2(String path) {
        Stack<String> pathStk = new Stack<String>();
        String[] dirs = path.split("/");
        for(String dir : dirs){
            if(dir.length()==0||dir.equals("."))
                continue;
            else if(dir.equals("..")){
                if(!pathStk.isEmpty())
            		pathStk.pop();
            }else
                pathStk.push(dir);
        }
        StringBuilder sb = new StringBuilder();
        for(String dir : pathStk)
            sb.append('/'+dir);
        return sb.length()==0 ? "/" : sb.toString();
    }
    
    /**
     * Scan, recognize and take actions 
     */
	public static String simplifyPath(String path) {
		Stack<String> dirstack = new Stack<String>();
		for (int i = 0; i < path.length(); i++) {
			String dir = nextBlock(path, i);
			if(dir.length()==0)
				continue;
			else if(dir.equals("."))
				i++;
			else if(dir.equals("..")){
				if(dirstack.size()>1)
					dirstack.pop();
				i += 2;
			}else{
				dirstack.push(dir+'/');
				i += dir.length();
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(String dir : dirstack)
			sb.append(dir);
		if(sb.length()>1)
			sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	/**
	 * Assume begin is the index of '/', given a path, it will return the string
	 * block between current '/' and next '/', exclude both '/'.
	 * 
	 * @return
	 */
	public static String nextBlock(String path, int begin) {
		int end = begin + 1;
		while (end < path.length()) {
			if (path.charAt(end) == '/')
				break;
			else
				end++;
		}
		return path.substring(begin + 1, end);
	}
}
