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
		 * called getInstance() and get the uninitialized instance, in which
		 * case would cause problems.
		 * 
		 */
		private volatile static ClassSingle instance;

		// prevent initialization
		private ClassSingle() {
			/**
			 * initialization logic here ...
			 */
		}

		// double check
		public ClassSingle getInstance() {
			if (instance == null) {
				synchronized (this) {
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
