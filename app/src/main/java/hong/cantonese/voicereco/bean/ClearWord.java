package hong.cantonese.voicereco.bean;

/**
 * Created by hongenit on 2017/1/14.
 */
public class ClearWord {
    //    {
//        "sc": 0,
//            "w": "，"
//    }
    private String w;
    private int sc;

    public int getSc() {
        return sc;
    }

    public void setSc(int sc) {
        this.sc = sc;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    @Override
    public String toString() {
        return "ClearWord{" +
                "w='" + w + '\'' +
                ", sc=" + sc +
                '}';
    }
}
