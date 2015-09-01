package general.webcrawler.yiyaodaibiao.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Job {

	@NonNull
	private String title;
	@NonNull
	private String companyName;
	private String contactName;
	private String phone;
	private String province;
	private String city;
	private Date postDate;
	private String source;
	private Page sourcePage;

	public static void main(String[] args) {
		Job job = new JobBuilder().companyName("Connectifier").title("医药代表").build();
		System.out.println(job);
	}
}
