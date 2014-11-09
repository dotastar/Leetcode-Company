package general.network;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyLogFormatter extends Formatter{
	private static MyLogFormatter instance;
	
	public static MyLogFormatter getInstance(){
		if(instance==null)
			instance = new MyLogFormatter();
		return instance;
	}
	
	@Override
	public String format(LogRecord log) {
		return new Date(log.getMillis()).toString() + " " + log.getLevel() + ":" + log.getMessage() + "\n";
	}

}
