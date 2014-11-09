package interview.company.yelp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {

	public static void main(String[] args) {
		String test = "fdasjdgkqw7169032529ehgklbjzlk71jh2356jdhpiqhgk716 903 2529vjzxbv,mbpiuhqw716-903-2529ejlfhkljzbdvklhajldva]ds[pl/.,v'as;kdf]qwe/.716z903,2529v;'lasd]pksdavk/zxmv;'alskd][fas[dlvzx,v/asd;va]sdkvp";
		getPhoneNo(test);
	}
	
	public static void getPhoneNo(String s){
		String regex = "\\d\\d\\d[ |-]{0,1}\\d\\d\\d[ |-]{0,1}\\d\\d\\d\\d";
		Pattern pattern = Pattern.compile(regex);
		Matcher mtch = pattern.matcher(s);
		while(mtch.find()){
			System.out.println(mtch.group());
		}
	}

	
}
