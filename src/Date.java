import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * 
 * 日期类
 * 
 * @author Benzolamps
 * 2018年3月1日10:50:55
 *
 */
public class Date implements Cloneable {
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public int year;
	public int month;
	public int day;
	public int hour;
	public int minute;
	public int second;

	public Date() {
		set(1900, 1, 1, 0, 0, 0);
	}

	public Date(int year, int month, int day, int hour, int minute, int second) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public void set(int year, int month, int day, int hour, int minute, int second) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public static Date getToday(TimeZone z) // 返回今天的日期
	{
		Date date = new Date();
		date.year = Calendar.getInstance(z).get(Calendar.YEAR);
		date.month = Calendar.getInstance(z).get(Calendar.MONTH) + 1;
		date.day = Calendar.getInstance(z).get(Calendar.DATE);
		int offset = (Calendar.AM == Calendar.getInstance(z).get(Calendar.AM_PM)) ? 0 : 12;
		date.hour = Calendar.getInstance(z).get(Calendar.HOUR) + offset;
		date.minute = Calendar.getInstance(z).get(Calendar.MINUTE);
		date.second = Calendar.getInstance(z).get(Calendar.SECOND);
		return date;
	}

	public boolean isLeapYear() // 判断是否是闰年
	{
		return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
	}

	public int getDayCount() // 判断每月的天数
	{
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2: {
				if (isLeapYear()) {
					return 29;
				}
				else {
					return 28;
				}
			}
			default:
				return -1;
		}
	}

	public int getWeekday() throws ParseException {
		return (int) (getOffset(new Date())) % 7 + 1;
	}

	public long getOffset(Date date) throws ParseException {
		java.util.Date o = Date.format.parse(date.year + "-" + date.month + "-" + date.day);
		java.util.Date t = Date.format.parse(year + "-" + month + "-" + day);
		long f = Math.abs(o.getTime() - t.getTime()) / (1000 * 3600 * 24);

		return f;
	}

	public String getDateString() {
		return year + "年" + month + "月" + day + "日";
	}

	public static String getNowString(TimeZone z) {

		int offset = (Calendar.AM == Calendar.getInstance(z).get(Calendar.AM_PM)) ? 0 : 12;
		int hour = Calendar.getInstance(z).get(Calendar.HOUR) + offset;
		int minute = Calendar.getInstance(z).get(Calendar.MINUTE);
		int second = Calendar.getInstance(z).get(Calendar.SECOND);

		return String.format(String.format("%02d:%02d:%02d", hour, minute, second));
	}

	public Date clone() {
		Date date = new Date();
		date.year = year;
		date.month = month;
		date.day = day;
		date.hour = hour;
		date.minute = minute;
		date.second = second;
		return date;
	}
}