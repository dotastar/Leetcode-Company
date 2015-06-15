package general.oo;

public class Singleton {

	public static void main(String[] args) {

	}

	/**
	 * Example of a singleton class
	 * 
	 * Thread safe + Lazy evaluation
	 * 
	 * @author yazhoucao
	 *
	 */
	public static class ClassSingle {

		/**
		 * If there is no volatile key word, it is possible happen such a case:
		 * Thread A entered getInstance(), begin to initialize instance of
		 * ClassSingle, because of Java's memory model, reference 'instance' is
		 * not null during 'new ClassSingle()', at the same time, thread B
		 * called getInstance() and get the instance which has not finished
		 * initializing yet, in which case would cause problems.
		 */

		/**
		 * volatile ensures ClassSingle is already initialized.
		 * volatile solves one issue that is visibility issue . If you are
		 * writing to one variable that is declared volatile then the value will
		 * be visible to other thread immediately. As we all know we have
		 * different level of cache in os L1, L2, L3 and if we write to a
		 * variable in one thread it is not guaranteed to be visible to other,
		 * so if we use volatile it writes to direct memory and is visible to
		 * others. But volatile does not solve the issue of atomicity.
		 * i.e. int a; a++; is not safe.
		 * AS there are three machine instructions associated to it.
		 */

		private volatile static ClassSingle instance;

		// prevent initialization
		private ClassSingle() {
			/**
			 * initialization logic here ...
			 */
		}

		// double check
		public static ClassSingle getInstance() {
			if (instance == null) {
				synchronized (ClassSingle.class) {
					if (instance == null)
						instance = new ClassSingle();
				}
			}
			return instance;
		}

		/**
		 * other methods...
		 */
	}

}
