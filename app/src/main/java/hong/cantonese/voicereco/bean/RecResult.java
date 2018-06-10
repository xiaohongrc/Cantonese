package hong.cantonese.voicereco.bean;

import java.util.ArrayList;

/**
 * Created by hongenit on 2017/1/14.
 */

public class RecResult {
    /*    {
            "sn": 2,
                "ls": false,
                "bg": 0,
                "ed": 0,
                "ws": [
            {
                "bg": 0,
                    "cw": [
                {
                    "sc": 0,
                        "w": "，"
                }
                ]
            },
            ]
        }*/

    private ArrayList<Word> ws;
    private int sn;
    private boolean ls;
    private int bg;
    private int ed;

    public ArrayList<Word> getWs() {
        return ws;
    }

    public void setWs(ArrayList<Word> ws) {
        this.ws = ws;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public boolean isLs() {
        return ls;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getEd() {
        return ed;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }

    @Override
    public String toString() {
        return "RecResult{" +
                "ws=" + ws.toString() +
                ", sn=" + sn +
                ", ls=" + ls +
                ", bg=" + bg +
                ", ed=" + ed +
                '}';
    }
}
