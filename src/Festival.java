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

	public static void finalFestival() { // ��������
		finalFestival = new Vector<Festival>();

		finalFestival.add(new Festival(new Date(1990, 1, 1, -1, -1, -1), "Ԫ��")); // Ԫ��(1��1��)
		finalFestival.add(new Festival(new Date(1990, 2, 14, -1, -1, -1), "���˽�")); // ���˽�(2��14��)
		finalFestival.add(new Festival(new Date(1990, 3, 8, -1, -1, -1), "��Ů��")); // ��Ů��(3��8��)
		finalFestival.add(new Festival(new Date(1990, 3, 12, -1, -1, -1), "ֲ����")); // ֲ����(3��12��)
		finalFestival.add(new Festival(new Date(1990, 4, 1, -1, -1, -1), "���˽�")); // ���˽�(4��1��)
		finalFestival.add(new Festival(new Date(1990, 5, 1, -1, -1, -1), "�Ͷ���")); // �Ͷ���(5��1��)
		finalFestival.add(new Festival(new Date(1990, 5, 4, -1, -1, -1), "�����")); // �����(5��4��)
		finalFestival.add(new Festival(new Date(1990, 6, 1, -1, -1, -1), "��ͯ��")); // ��ͯ��(6��1��)
		finalFestival.add(new Festival(new Date(1990, 7, 1, -1, -1, -1), "������")); // ������(7��1��)
		finalFestival.add(new Festival(new Date(1990, 8, 1, -1, -1, -1), "������")); // ������(8��1��)
		finalFestival.add(new Festival(new Date(1990, 9, 10, -1, -1, -1), "��ʦ��")); // ��ʦ��(9��10��)
		finalFestival.add(new Festival(new Date(1990, 10, 1, -1, -1, -1), "�����")); // �����(10��1��)
		finalFestival.add(new Festival(new Date(1990, 11, 1, -1, -1, -1), "��ʥ��")); // ��ʥ��(11��1��)
		finalFestival.add(new Festival(new Date(1990, 11, 2, -1, -1, -1), "�����")); // �����(11��2��)
		finalFestival.add(new Festival(new Date(1990, 11, 11, -1, -1, -1), "�����")); // �����(11��11��)
		finalFestival.add(new Festival(new Date(1990, 12, 24, -1, -1, -1), "ƽ��ҹ")); // ƽ��ҹ(12��24��)
		finalFestival.add(new Festival(new Date(1990, 12, 25, -1, -1, -1), "ʥ����")); // ʥ����(12��25��)
	}

	public static boolean isMotherDay(Date date) throws ParseException { // ĸ�׽ڣ�5�µ�2��������
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

	public static boolean isFatherDay(Date date) throws ParseException { // ���׽ڣ�6�µ�3��������
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

	public static boolean isThanksDay(Date date) throws ParseException { // �ж��ڣ�11�µ�4��������
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

		lunarFestival.add(new Festival(new Date(1990, 1, 1, -1, -1, -1), "����")); // ����(���³�һ)
		lunarFestival.add(new Festival(new Date(1990, 1, 15, -1, -1, -1), "Ԫ����")); // Ԫ����(����ʮ��)
		lunarFestival.add(new Festival(new Date(1990, 2, 2, -1, -1, -1), "��̧ͷ")); // ��̧ͷ(���³���)
		lunarFestival.add(new Festival(new Date(1990, 5, 5, -1, -1, -1), "�����")); // �����(���³���)
		lunarFestival.add(new Festival(new Date(1990, 7, 7, -1, -1, -1), "��Ϧ")); // ��Ϧ(���³���)
		lunarFestival.add(new Festival(new Date(1990, 7, 15, -1, -1, -1), "��Ԫ��")); // ��Ԫ��(����ʮ��)
		lunarFestival.add(new Festival(new Date(1990, 8, 15, -1, -1, -1), "�����")); // �����(����ʮ��)
		lunarFestival.add(new Festival(new Date(1990, 9, 9, -1, -1, -1), "������")); // ������(���³���)
		lunarFestival.add(new Festival(new Date(1990, 12, 8, -1, -1, -1), "����")); // ����(���³���)
	}

	public static boolean isChuxi(Date date) throws ParseException { // ��Ϧ������������ʮ��С������إ��
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
			f.add("ĸ�׽�");
		if (isFatherDay(date))
			f.add("���׽�");
		if (isThanksDay(date))
			f.add("�ж���");
		if (isChuxi(date))
			f.add("��Ϧ");

		return f.toArray(new String[f.size()]);
	}

}
