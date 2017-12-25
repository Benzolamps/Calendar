import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Benzolamps
 *
 */
public class SolarTerm {
	private static final double D = 0.2422;
	private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<String, Integer[]>();// +1ƫ��
	private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<String, Integer[]>();// -1ƫ��

	/** 24���� **/
	private static enum SolarTermsEnum {
		LICHUN, // ����
		YUSHUI, // ��ˮ
		JINGZHE, // ����
		CHUNFEN, // ����
		QINGMING, // ����
		GUYU, // ����
		LIXIA, // ����
		XIAOMAN, // С��
		MANGZHONG, // â��
		XIAZHI, // ����
		XIAOSHU, // С��
		DASHU, // ����
		LIQIU, // ����
		CHUSHU, // ����
		BAILU, // ��¶
		QIUFEN, // ���
		HANLU, // ��¶
		SHUANGJIANG, // ˪��
		LIDONG, // ����
		XIAOXUE, // Сѩ
		DAXUE, // ��ѩ
		DONGZHI, // ����
		XIAOHAN, // С��
		DAHAN;// ��
	}

	static {
		DECREASE_OFFSETMAP.put(SolarTermsEnum.YUSHUI.name(), new Integer[] { 2026 });// ��ˮ
		INCREASE_OFFSETMAP.put(SolarTermsEnum.CHUNFEN.name(), new Integer[] { 2084 });// ����
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOMAN.name(), new Integer[] { 2008 });// С��
		INCREASE_OFFSETMAP.put(SolarTermsEnum.MANGZHONG.name(), new Integer[] { 1902 });// â��
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAZHI.name(), new Integer[] { 1928 });// ����
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOSHU.name(), new Integer[] { 1925, 2016 });// С��
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DASHU.name(), new Integer[] { 1922 });// ����
		INCREASE_OFFSETMAP.put(SolarTermsEnum.LIQIU.name(), new Integer[] { 2002 });// ����
		INCREASE_OFFSETMAP.put(SolarTermsEnum.BAILU.name(), new Integer[] { 1927 });// ��¶
		INCREASE_OFFSETMAP.put(SolarTermsEnum.QIUFEN.name(), new Integer[] { 1942 });// ���
		INCREASE_OFFSETMAP.put(SolarTermsEnum.SHUANGJIANG.name(), new Integer[] { 2089 });// ˪��
		INCREASE_OFFSETMAP.put(SolarTermsEnum.LIDONG.name(), new Integer[] { 2089 });// ����
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOXUE.name(), new Integer[] { 1978 });// Сѩ
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DAXUE.name(), new Integer[] { 1954 });// ��ѩ
		DECREASE_OFFSETMAP.put(SolarTermsEnum.DONGZHI.name(), new Integer[] { 1918, 2021 });// ����
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[] { 1982 });// С��
		DECREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[] { 2019 });// С��
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DAHAN.name(), new Integer[] { 2082 });// ��
	}

	// ����һ����ά���飬��һά����洢����20���͵Ľ���Cֵ���ڶ�ά����洢����21���͵Ľ���Cֵ,0��23�������δ�����������ˮ...�󺮽�����Cֵ
	private static final double[][] CENTURY_ARRAY = {
			{ 4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44,
					23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84 },
			{ 3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83, 7.5, 23.13, 7.646, 23.042,
					8.318, 23.438, 7.438, 22.36, 7.18, 21.94, 5.4055, 20.12 } };

	/**
	 * 
	 * @param year
	 *            ���
	 * @param name
	 *            ����������
	 * @return ���ؽ�������Ӧ�·ݵĵڼ���
	 */
	public static int getSolarTermNum(int year, String name) {
		double centuryValue = 0;// ����������ֵ��ÿ��������ÿ������ֵ����ͬ
		name = name.trim().toUpperCase();
		int ordinal = SolarTermsEnum.valueOf(name).ordinal();
		int centuryIndex = -1;
		if (year >= 1901 && year <= 2000) {// 20����
			centuryIndex = 0;
		} else if (year >= 2001 && year <= 2100) {// 21����
			centuryIndex = 1;
		} else {
			throw new RuntimeException("��֧�ִ���ݣ�" + year + "��Ŀǰֻ֧��1901�굽2100���ʱ�䷶Χ");
		}
		centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
		int dateNum = 0;
		/**
		 * ���� num =[Y*D+C]-L���Ǵ�˵�е�����ͨ�ù�ʽ ��ʽ����������ĺ�2λ��0.2422��C(����centuryValue)ȡ�����󣬼�������
		 */
		int y = year % 100;// ����1:ȡ��ֵĺ���λ��
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// ����
			if (ordinal == SolarTermsEnum.XIAOHAN.ordinal() || ordinal == SolarTermsEnum.DAHAN.ordinal()
					|| ordinal == SolarTermsEnum.LICHUN.ordinal() || ordinal == SolarTermsEnum.YUSHUI.ordinal()) {
				// ע�⣺������3��1��ǰ������Ҫ��һ������L=[(Y-1)/4],��ΪС�����󺮡���������ˮ������������С��3��1��,���� y = y-1
				y = y - 1;// ����2
			}
		}
		dateNum = (int) (y * D + centuryValue) - (int) (y / 4);// ����3��ʹ�ù�ʽ[Y*D+C]-L����
		dateNum += specialYearOffset(year, name);// ����4�������������ֵĽ���ƫ����
		return dateNum;
	}

	/**
	 * ����,�������ֵĽ���ƫ����,���ڹ�ʽ�������ƣ���������ĸ�������ĵڼ���������׼ȷ���ڴ˷�����ƫ����
	 * 
	 * @param year
	 *            ���
	 * @param name
	 *            ��������
	 * @return ������ƫ����
	 */
	public static int specialYearOffset(int year, String name) {
		int offset = 0;
		offset += getOffset(DECREASE_OFFSETMAP, year, name, -1);
		offset += getOffset(INCREASE_OFFSETMAP, year, name, 1);

		return offset;
	}

	public static int getOffset(Map<String, Integer[]> map, int year, String name, int offset) {
		int off = 0;
		Integer[] years = map.get(name);
		if (null != years) {
			for (int i : years) {
				if (i == year) {
					off = offset;
					break;
				}
			}
		}
		return off;
	}

	public static String getSolarTerm(Date date) {
		if (date.year >= 1901 && date.year <= 2100)
			switch (date.month) {
			case 1: {
				if (getSolarTermNum(date.year, SolarTermsEnum.XIAOHAN.name()) == date.day)
					return "С��";
				if (getSolarTermNum(date.year, SolarTermsEnum.DAHAN.name()) == date.day)
					return "��";
				break;
			}

			case 2: {
				if (getSolarTermNum(date.year, SolarTermsEnum.LICHUN.name()) == date.day)
					return "����";
				if (getSolarTermNum(date.year, SolarTermsEnum.YUSHUI.name()) == date.day)
					return "��ˮ";
				break;
			}

			case 3: {
				if (getSolarTermNum(date.year, SolarTermsEnum.JINGZHE.name()) == date.day)
					return "����";
				if (getSolarTermNum(date.year, SolarTermsEnum.CHUNFEN.name()) == date.day)
					return "����";
				break;
			}

			case 4: {
				if (getSolarTermNum(date.year, SolarTermsEnum.QINGMING.name()) == date.day)
					return "����";
				if (getSolarTermNum(date.year, SolarTermsEnum.GUYU.name()) == date.day)
					return "����";
				break;
			}

			case 5: {
				if (getSolarTermNum(date.year, SolarTermsEnum.LIXIA.name()) == date.day)
					return "����";
				if (getSolarTermNum(date.year, SolarTermsEnum.XIAOMAN.name()) == date.day)
					return "С��";
				break;
			}

			case 6: {
				if (getSolarTermNum(date.year, SolarTermsEnum.MANGZHONG.name()) == date.day)
					return "â��";
				if (getSolarTermNum(date.year, SolarTermsEnum.XIAZHI.name()) == date.day)
					return "����";
				break;
			}

			case 7: {
				if (getSolarTermNum(date.year, SolarTermsEnum.XIAOSHU.name()) == date.day)
					return "С��";
				if (getSolarTermNum(date.year, SolarTermsEnum.DASHU.name()) == date.day)
					return "����";
				break;
			}

			case 8: {
				if (getSolarTermNum(date.year, SolarTermsEnum.LIQIU.name()) == date.day)
					return "����";
				if (getSolarTermNum(date.year, SolarTermsEnum.CHUSHU.name()) == date.day)
					return "����";
				break;
			}

			case 9: {
				if (getSolarTermNum(date.year, SolarTermsEnum.BAILU.name()) == date.day)
					return "��¶";
				if (getSolarTermNum(date.year, SolarTermsEnum.QIUFEN.name()) == date.day)
					return "���";
				break;
			}

			case 10: {
				if (getSolarTermNum(date.year, SolarTermsEnum.HANLU.name()) == date.day)
					return "��¶";
				if (getSolarTermNum(date.year, SolarTermsEnum.SHUANGJIANG.name()) == date.day)
					return "˪��";
				break;
			}

			case 11: {
				if (getSolarTermNum(date.year, SolarTermsEnum.LIDONG.name()) == date.day)
					return "����";
				if (getSolarTermNum(date.year, SolarTermsEnum.XIAOXUE.name()) == date.day)
					return "Сѩ";
				break;
			}

			case 12: {
				if (getSolarTermNum(date.year, SolarTermsEnum.DAXUE.name()) == date.day)
					return "��ѩ";
				if (getSolarTermNum(date.year, SolarTermsEnum.DONGZHI.name()) == date.day)
					return "����";
			}
			}
		return null;
	}
}
