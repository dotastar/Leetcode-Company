package general.oo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Example in Wikipedia
 * https://en.wikipedia.org/wiki/Duck_typing#In_Java
 * 
 * Duck typing is a layer of programming language and design rules on top of
 * typing. Typing is concerned with assigning a type to any object.
 * 
 * In duck typing, an object's suitability is determined by the presence of
 * certain methods and properties (with appropriate meaning), rather than the
 * actual type of the object.
 * 
 * In Java duck typing may be achieved with reflection.
 * 
 * @author yazhoucao
 *
 */
public class DuckTyping {

	interface Walkable {
		void walk();
	}

	interface Swimmable {
		void swim();
	}

	interface Quackable {
		void quack();
	}

	public static void main(String[] args) {
		Duck d = new Duck();
		Person p = new Person();

		as(Walkable.class, d).walk(); // OK, duck has walk() method
		as(Swimmable.class, d).swim(); // OK, duck has swim() method
		as(Quackable.class, d).quack(); // OK, duck has quack() method

		as(Walkable.class, p).walk(); // OK, person has walk() method
		as(Swimmable.class, p).swim(); // OK, person has swim() method
		as(Quackable.class, p).quack(); // Runtime Error, person does not have
										// quack() method
	}

	@SuppressWarnings("unchecked")
	static <T> T as(Class<T> t, final Object obj) {
		return (T) Proxy.newProxyInstance(t.getClassLoader(), new Class[] { t },
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						try {
							return obj
									.getClass()
									.getMethod(method.getName(),
											method.getParameterTypes()).invoke(obj, args);
						} catch (NoSuchMethodException nsme) {
							throw new NoSuchMethodError(nsme.getMessage());
						} catch (InvocationTargetException ite) {
							throw ite.getTargetException();
						}
					}
				});
	}
}

class Duck {
	public void walk() {
		System.out.println("I'm Duck, I can walk...");
	}

	public void swim() {
		System.out.println("I'm Duck, I can swim...");
	}

	public void quack() {
		System.out.println("I'm Duck, I can quack...");
	}
}

class Person {
	public void walk() {
		System.out.println("I'm Person, I can walk...");
	}

	public void swim() {
		System.out.println("I'm Person, I can swim...");
	}

	public void talk() {
		System.out.println("I'm Person, I can talk...");
	}
}
