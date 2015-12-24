package trekermanager;

import UI.Start;
import java.util.HashMap;

// класс расширяющий абстрактный Pack до пакета с данными. Сделано на всякий случай - если будут ещё другие пакеты и их тоже нужно будет обрабатывать аналогичными способами
// так же, при инициализации через конструктор, происходит запись пакета в базу данных при помощи переопределённого метода
public class PackageData extends Pack {

    private String id;
    private String date;
    private String time;
    private String lat ;
    private String lon ;
    private int speed;
    private int course;
    private int height;
    private int sats;
    private int idx;
    private int mcc;
    private int mnc;
    private int lac;
    private int cid;
    private int vext;
    private  float hdop;
    private int digitinput;
    private int digitouput;
    private String ads;
    private String ibutton;
    private String params;
    private boolean input1;
    private boolean input2;
//D#020100;030326;NA;NA;NA;NA;NA;NA;NA;NA;NA;NA;NA;;000000000000;IDX:1:107,MCC:1:250,MNC:1:1,LAC:1:407,CID:1:56625,Vext:1:8376,IN1:1:0,IN2:1:0

    public PackageData(String id, String date, String time, String lat, String lon, int speed, int course, int height, int sats, float hdop, int digitinput, int digitoutput, String ads, String ibutton, String params) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.lat = lat;
        this.lon = lon;
        this.speed = speed;
        this.course = course;
        this.sats = sats;
        this.hdop = hdop;
        this.digitinput = digitinput;
        this.digitouput = digitoutput;
        this.ads = ads;
        this.ibutton = ibutton;
        this.params = params;
        String paramsbody[] = params.split(",");
        HashMap<String, String> parameters = new HashMap<String, String>();
        for (String paramsbody1 : paramsbody) {
            String[] value = paramsbody1.split(":");
            if (paramsbody1.length() > 2) {
                parameters.put(value[0], value[2]);
            }
        }

        for (String key : parameters.keySet()) {
            switch (key) {

                case "IDX":
                    this.idx = Integer.parseInt(parameters.get(key));
                    break;
                case "MCC":
                    this.mcc = Integer.parseInt(parameters.get(key));
                    break;
                case "MNC":
                    this.mnc = Integer.parseInt(parameters.get(key));
                    break;
                case "LAC":
                    this.lac = Integer.parseInt(parameters.get(key));
                    break;
                case "CID":
                    this.cid = Integer.parseInt(parameters.get(key));
                    break;
                case "Vext":
                    this.vext = Integer.parseInt(parameters.get(key));
                    break;
                case "IN1":
                    this.input1 = Integer.parseInt(parameters.get(key)) == 1;
                    break;
                case "IN2":
                    this.input2 = Integer.parseInt(parameters.get(key)) == 1;
                    break;

            }

        }

        System.out.println("this.idx="+this.idx+ " this.mcc="+this.mcc+ " this.mnc="+this.mnc+" this.lac="+this.lac+" this.cid="+this.cid+" this.vext="+this.vext+" this.input1="+this.input1+" this.input2="+this.input1);
        SaveToDB();
    }

    @Override
    public void Print() {
        System.out.println("Параметры:" + this.params);
        System.out.println("Состояние устройства:" + this.input1);
    }
// input2 - boolean отображение дискретного сигнала(0,1) с устройства.

    @Override
    public final void SaveToDB() {
        System.out.println("PackageData: SaveToDB executed");
        if (this.input2) {
            Start.mf.deviceStatus(this.id, true);
        } else {
            Start.mf.deviceStatus(this.id, false);
        }
        Start.mf.sdb.setPackageData(this);
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCourse() {
        return course;
    }

    public int getHeight() {
        return height;
    }

    public int getSats() {
        return sats;
    }

    public int getIdx() {
        return idx;
    }

    public int getMcc() {
        return mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public int getLac() {
        return lac;
    }

    public int getCid() {
        return cid;
    }

    public int getVext() {
        return vext;
    }

    public float getHdop() {
        return hdop;
    }

    public int getDigitinput() {
        return digitinput;
    }

    public int getDigitouput() {
        return digitouput;
    }

    public String getAds() {
        return ads;
    }

    public String getIbutton() {
        return ibutton;
    }

    public String getParams() {
        return params;
    }

    public boolean isInput1() {
        return input1;
    }

    public boolean isInput2() {
        return input2;
    }
    
}
