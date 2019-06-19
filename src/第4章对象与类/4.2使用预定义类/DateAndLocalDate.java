import java.util.*; // Date
import java.time.*; // LocalDate

public class DateAndLocalDate {
	public static void main(String[] args) {
		Date birthday = new Date();
		System.out.println("Date : " + birthday);

		LocalDate nowLocalDate = LocalDate.now();
		LocalDate oldLocalDate = LocalDate.of(1966,1,1);
		System.out.println("nowLocalDate : " + nowLocalDate);
		System.out.println("oldLocalDate : " + oldLocalDate);

		System.out.println("now year : " + nowLocalDate.getYear());
		System.out.println("now month : " + nowLocalDate.getMonth());
		System.out.println("now month value : " + nowLocalDate.getMonthValue());
		System.out.println("now day of month : " + nowLocalDate.getDayOfMonth());
		System.out.println("now day of year : " + nowLocalDate.getDayOfYear());
	}
}