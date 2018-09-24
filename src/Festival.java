import java.text.ParseException;
import java.util.Vector;

/**
 * 
 * @author Benzolamps
 *
 */
public class Festival {
	private Date date;
	private String name;
	private static Vector<Festival> finalFestival, lunarFestival;

	private Festival(Date date, String name) {
		this.date = date;
		this.name = name;
	}

	public static void finalFestival() { // 公历节日
		finalFestival = new Vector<Festival>();

		finalFestival.add(new Festival(new Date(1990, 1, 1, -1, -1, -1), "元旦")); // 元旦(1月1日)
		finalFestival.add(new Festival(new Date(1990, 2, 14, -1, -1, -1), "情人节")); // 情人节(2月14日)
		finalFestival.add(new Festival(new Date(1990, 3, 8, -1, -1, -1), "妇女节")); // 妇女节(3月8日)
		finalFestival.add(new Festival(new Date(1990, 3, 12, -1, -1, -1), "植树节")); // 植树节(3月12日)
		finalFestival.add(new Festival(new Date(1990, 4, 1, -1, -1, -1), "愚人节")); // 愚人节(4月1日)
		finalFestival.add(new Festival(new Date(1990, 5, 1, -1, -1, -1), "劳动节")); // 劳动节(5月1日)
		finalFestival.add(new Festival(new Date(1990, 5, 4, -1, -1, -1), "青年节")); // 青年节(5月4日)
		finalFestival.add(new Festival(new Date(1990, 6, 1, -1, -1, -1), "儿童节")); // 儿童节(6月1日)
		finalFestival.add(new Festival(new Date(1990, 7, 1, -1, -1, -1), "建党节")); // 建党节(7月1日)
		finalFestival.add(new Festival(new Date(1990, 8, 1, -1, -1, -1), "建军节")); // 建军节(8月1日)
		finalFestival.add(new Festival(new Date(1990, 9, 10, -1, -1, -1), "教师节")); // 教师节(9月10日)
		finalFestival.add(new Festival(new Date(1990, 10, 1, -1, -1, -1), "国庆节")); // 国庆节(10月1日)
		finalFestival.add(new Festival(new Date(1990, 11, 1, -1, -1, -1), "万圣节")); // 万圣节(11月1日)
		finalFestival.add(new Festival(new Date(1990, 11, 2, -1, -1, -1), "万灵节")); // 万灵节(11月2日)
		finalFestival.add(new Festival(new Date(1990, 11, 11, -1, -1, -1), "光棍节")); // 光棍节(11月11日)
		finalFestival.add(new Festival(new Date(1990, 12, 24, -1, -1, -1), "平安夜")); // 平安夜(12月24日)
		finalFestival.add(new Festival(new Date(1990, 12, 25, -1, -1, -1), "圣诞节")); // 圣诞节(12月25日)
	}

	public static boolean isMotherDay(Date date) throws ParseException { // 母亲节，5月第2个星期六
		if (date.month != 5)
			return false;

		if (date.getWeekday() != 7)
			return false;

		if (date.day <= 7)
			return false;

		if (date.day > 14)
			return false;

		return true;
	}

	public static boolean isFatherDay(Date date) throws ParseException { // 父亲节，6月第3个星期日
		if (date.month != 6)
			return false;

		if (date.getWeekday() != 7)
			return false;

		if (date.day <= 14)
			return false;

		if (date.day > 21)
			return false;

		return true;
	}

	public static boolean isThanksDay(Date date) throws ParseException { // 感恩节，11月第4个星期五
		if (date.month != 11)
			return false;

		if (date.getWeekday() != 4)
			return false;

		if (date.day <= 21)
			return false;

		if (date.day > 28)
			return false;

		return true;
	}

	public static void lunarFestival() {
		lunarFestival = new Vector<Festival>();

		lunarFestival.add(new Festival(new Date(1990, 1, 1, -1, -1, -1), "春节")); // 春节(正月初一)
		lunarFestival.add(new Festival(new Date(1990, 1, 15, -1, -1, -1), "元宵节")); // 元宵节(正月十五)
		lunarFestival.add(new Festival(new Date(1990, 2, 2, -1, -1, -1), "龙抬头")); // 龙抬头(二月初二)
		lunarFestival.add(new Festival(new Date(1990, 5, 5, -1, -1, -1), "端午节")); // 端午节(五月初五)
		lunarFestival.add(new Festival(new Date(1990, 7, 7, -1, -1, -1), "七夕")); // 七夕(七月初七)
		lunarFestival.add(new Festival(new Date(1990, 7, 15, -1, -1, -1), "中元节")); // 中元节(七月十五)
		lunarFestival.add(new Festival(new Date(1990, 8, 15, -1, -1, -1), "中秋节")); // 中秋节(八月十五)
		lunarFestival.add(new Festival(new Date(1990, 9, 9, -1, -1, -1), "重阳节")); // 重阳节(九月初九)
		lunarFestival.add(new Festival(new Date(1990, 12, 8, -1, -1, -1), "腊八")); // 腊八(腊月初八)
	}

	public static boolean isChuxi(Date date) throws ParseException { // 除夕，大月腊月三十，小月腊月廿九
		Lunar lunarDate = new Lunar(date);
		if (lunarDate.month != 12)
			return false;

		if (lunarDate.isLeap)
			return false;

		if (lunarDate.day < 29)
			return false;

		if (lunarDate.isBigMonth() && lunarDate.day == 29)
			return false;

		return true;
	}

	public static String getFestival(Date date)[] throws ParseException {
		finalFestival();
		lunarFestival();
		Vector<String> f = new Vector<String>();

		for (Festival a : finalFestival) {
			Date d = a.date;
			if (date.month == d.month && date.day == d.day)
				f.add(a.name);
		}

		for (Festival a : lunarFestival) {
			Date d = a.date;
			Lunar l = new Lunar(date);
			if (d.month == l.month && d.day == l.day && !l.isLeap)
				f.add(a.name);
		}

		if (isMotherDay(date))
			f.add("母亲节");
		if (isFatherDay(date))
			f.add("父亲节");
		if (isThanksDay(date))
			f.add("感恩节");
		if (isChuxi(date))
			f.add("除夕");

		return f.toArray(new String[f.size()]);
	}

}
