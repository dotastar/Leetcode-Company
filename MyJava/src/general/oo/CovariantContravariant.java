package general.oo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CovariantContravariant {

	/**
	 * Conclusion, The Get and Put Principle (Producer Extends, Consumer Super):
	 * 
	 * Use the "? extends" wildcard if you need to retrieve object from a data
	 * structure
	 * 
	 * Use the "? super" wildcard if you need to put objects in a data structure
	 * 
	 * If you need to do both things, don’t use any wildcard.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/******************** Covariant on Array ************************/
		// Array support covariant
		Number[] numArr = new Integer[] { 9, 8, 7, 6, 5 };
		System.out.println("numArr[0]: " + numArr[0]);
		// But not contravariant
		// Integer[] intArr = numArr; //compile error

		/******************** Covariant, Invariant, Contravariant on List ********************/
		// Default List is invariant.
		// List<Number> numblist = new ArrayList<Integer>(); //compile error

		/******************** Covariant ********************/
		/**
		 * the ? extends T wildcard tells the compiler that we’re dealing with a
		 * subtype of the type T, but we cannot know which one.
		 * Since there’s no way to tell, and we need to guarantee type safety,
		 * you won’t be allowed to put anything inside such a structure. On the
		 * other hand, since we know that whichever type it might be, it will be
		 * a subtype of T, we can get data out of the structure with the
		 * guarantee that it will be a T instance
		 */
		List<Number> nums = Arrays.asList(new Number[] { 1, 2.2f, 0.3d, 4l });
		// "? extends Base" implements covariant
		List<? extends Number> covarList = nums;
		// safe to read
		System.out.print("covarList: ");
		for (Number n : covarList)
			System.out.print("\t" + n);
		System.out.println();
		// can't write anything literally
		// covarList.add(new Object()); //compile error
		// covarList.add(new Integer(1)); //compile error
		// covarList.add((Number)new Integer(9);); //compile error

		/******************** Contravariant ********************/
		/**
		 * From ? super T, although the compiler cannot know which supertype it
		 * is, but the compiler knows T and any of its subtypes will be
		 * assignment-compatible with it. So we can write any object that is the
		 * subtype of T.
		 * For read, the only thing you can get out of it will be Object
		 * instances: since we cannot know which supertype it is, the compiler
		 * can only guarantee that it will be a reference to an Object, since
		 * Object is the supertype of any Java type.
		 */
		// " ? super Base" implements contravariant
		List<Object> objList = new ArrayList<Object>();
		// compile error, cannot downcast to a subtype of Number list
		// List<? extends Number> list = objList;

		List<? super Number> contraList = objList;

		// compile error, since we cannot know which supertype it is, we aren’t
		// allowed to add instances of any.
		// contraList.add(new Object());

		// any type include and below Number is allowed to write
		contraList.add((Number) 1);
		contraList.add(new Long(2));
		contraList.add(new Double(0.003));
		System.out.print("contraList: ");
		for (Object n : contraList)
			System.out.print("\t" + n);
		System.out.println();
	}

}

/******* Covariant on function *******/
class Super_Covariant {
	Object getSomething() {
		return null;
	}
}

class SubClass1 extends Super_Covariant {
	@Override
	String getSomething() { // return type is narrowed
		return null;
	}
}

/******* Contravariant on function *******/
class Super_Contravariant {
	void doSomething(String parameter) {
	}
}

class SubClass2 extends Super_Contravariant {
	// input type is widened
	void doSomething(Object parameter) {
	}
}