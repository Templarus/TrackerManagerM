
package UI;


public class TESTCLASS {


    //Привет Ден
    //Превед
    
    public static void main(String args[]) {
        TESTCLASS t = new TESTCLASS();

    }

    public TESTCLASS() {
        run();

    }

    public void run() {
        System.out.println(dateToString(271015));
    }

    public static String dateToString(int data) {
        if (data != 0) {
            String day = Integer.toString(data).substring(0, 2);
            String month = Integer.toString(data).substring(2, 4);
            String year = Integer.toString(data).substring(4, 6);
            StringBuilder stringDate = new StringBuilder();
            stringDate.append(year).append(month).append(day);
            return stringDate.toString();
        } else {
            return "";
        }
    }

    public static String timeToString(int time) {
        if (time != 0) {
            String h = Integer.toString(time).substring(0, 2);
            String m = Integer.toString(time).substring(2, 2);
            String s = Integer.toString(time).substring(4, 2);
            StringBuilder stringTime = new StringBuilder();
            stringTime.append(h).append(":").append(m).append(":").append(s);
            return stringTime.toString();
        } else {
            return "";
        }
    }
}
