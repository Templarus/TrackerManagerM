package Db;

/**
 *
 * @author EvgenyVartavanyan
 */
//класс с набором методов для общих операций (например переобразования)
public class Methods {

    public static String dateToString(String data) {
        if (data!="") {
            String day = data.substring(0, 2);
            String month = data.substring(2, 4);
            String year = data.substring(4, 6);
            StringBuilder stringDate = new StringBuilder();
            stringDate.append("20").append(year).append(month).append(day);
            return stringDate.toString();
        } else {
            return "";
        }
    }
    public static String timeToString(String time) {
        if (time != "") {
            String h = time.substring(0, 2);
            String m = time.substring(2, 4);
            String s = time.substring(4, 6);
            StringBuilder stringTime = new StringBuilder();
            stringTime.append(h).append(":").append(m).append(":").append(s);
            return stringTime.toString();
        } else {
            return "";
        }
    }
}
