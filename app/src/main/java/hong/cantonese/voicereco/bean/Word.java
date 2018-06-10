package hong.cantonese.voicereco.bean;

import java.util.ArrayList;

/**
 * Created by hongenit on 2017/1/14.
 */

public class Word {
//    }
    //    {
//        "bg": 0,
//            "cw": [
//        {
//            "sc": 0,
//                "w": "，"
//        }
//        ]

    private int bg;
    private ArrayList<ClearWord> cw;

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public ArrayList<ClearWord> getCw() {
        return cw;
    }

    public void setCw(ArrayList<ClearWord> cw) {
        this.cw = cw;
    }


    @Override
    public String toString() {
        return "Word{" +
                "bg=" + bg +
                ", cw=" + cw +
                '}';
    }
}
