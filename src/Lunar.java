import java.text.ParseException;

/**
 *
 * @author Benzolamps
 *
 */
public class Lunar {
    public int year;
    public int month;
    public int day;
    public boolean isLeap;
    private final static String[] monthStr = {
        "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
    };

    private final static long[] lunarInfo = {
        0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
        0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
        0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
        0x14b63, 0x09370, 0x049f8, 0x04970, 0x064b0, 0x168a6, 0x0ea50, 0x06b20, 0x1a6c4, 0x0aae0,
        0x092e0, 0x0d2e3, 0x0c960, 0x0d557, 0x0d4a0, 0x0da50, 0x05d55, 0x056a0, 0x0a6d0, 0x055d4,
        0x052d0, 0x0a9b8, 0x0a950, 0x0b4a0, 0x0b6a6, 0x0ad50, 0x055a0, 0x0aba4, 0x0a5b0, 0x052b0,
        0x0b273, 0x06a30, 0x07337, 0x06aa0, 0x0ad50, 0x14b55, 0x04b60, 0x0a570, 0x05474, 0x0d160,
        0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0, 0x0d150, 0x0f252,
        0x0d520
    };

    public Lunar(Date date) throws ParseException {
        int leapMonth = 0;
        Date baseDate = null;
        baseDate = new Date(1900, 1, 31, 0, 0, 0);
        int offset = (int) (date.getOffset(baseDate));
        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // i最终结果是农历的年份
        // offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < lunarInfo.length + 1900 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        // 农历年份
        year = iYear;
        leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        isLeap = false;
        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !isLeap) {
                --iMonth;
                isLeap = true;
                daysOfMonth = leapDays(year);
            } else {
                daysOfMonth = monthDays(year, iMonth);
            }
            offset -= daysOfMonth;
            // 解除闰月
            if (isLeap && iMonth == (leapMonth + 1)) {
                isLeap = false;
            }
            if (!isLeap) {
            }
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (isLeap) {
                isLeap = false;
            } else {
                isLeap = true;
                --iMonth;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        month = iMonth;
        day = offset + 1;
    }

    private static int yearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0) {
                sum += 1;
            }
        }
        return (sum + leapDays(y));
    }

    private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0) {
                return 30;
            }
            else {
                return 29;
            }
        } else {
            return 0;
        }
    }

    private static int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0) {
            return 29;
        }
        else {
            return 30;
        }
    }

    public String getAnimal() {
        final String[] animals = new String[] {
            "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"
        };
        return animals[(year - 4) % 12];
    }

    private static String cyclicalm(int num) {
        final String[] gan = new String[] {
            "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
        };
        final String[] zhi = new String[] {
            "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
        };
        return (gan[num % 10] + zhi[num % 12]);
    }

    public String getGanZhi() {
        int num = year - 1900 + 36;
        return (cyclicalm(num));
    }

    public static String getDayStr(int day) {
        String[] chineseNumber = {
            "一", "二", "三", "四", "五", "六", "七", "八", "九"
        };

        switch (day) {
            case 10:
                return "初十";
            case 20:
                return "二十";
            case 30:
                return "三十";
            default: {
                switch (day / 10) {
                    case 0:
                        return "初" + chineseNumber[day % 10 - 1];
                    case 1:
                        return "十" + chineseNumber[day % 10 - 1];
                    case 2:
                        return "廿" + chineseNumber[day % 10 - 1];
                }
            }
        }
        return null;
    }

    public String toString() {
        return "农历" + getGanZhi() + getAnimal() + "年" + (isLeap ? "闰" : "") + monthStr[month - 1] + "月" + getDayStr(day);
    }

    public boolean isBigMonth() {
        return monthDays(year, month) == 30;
    }

    public String getMonth() {
        return (isLeap ? "闰" : "") + monthStr[month - 1];
    }

    public String getDay() {
        return getDayStr(day);
    }
}
