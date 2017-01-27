package interview.company.epic;

/**
 * Glitch is a walking robot moves in a peculiar problem: it takes x steps
 * forward , then x+1 steps backward, then 2x steps forward, x+2 steps
 * backward,3x steps forward x+3 steps backward , and so on... until it has
 * taken y steps,glitch turns 180 degrees before continuing with its pattern.
 * Write a program that prompts x and y and total number of steps taken and
 * outputs how many steps away from its starting point
 * 
 * @author yazhoucao
 * 
 */
public class BackAndForthGlitch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int glitch_steps(int x, int y) {
		int steps = 0;
		int i = 1;
		while (steps != y) {
			steps = i * x - x - i;
			i++;
		}
		return steps;
	}
}
