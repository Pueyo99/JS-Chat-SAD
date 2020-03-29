import java.io.*;
import java.awt.*;

public class Mouse{
    public static Point p;
    public Mouse(){

    }
    public static void setTracking(){
        try{
            final String[] cmd = {"/bin/sh", "-c", "echo -e "+"\e[?9h"+" </dev/tty"};
            Process child = Runtime.getRuntime().exec(cmd);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static double getPostion(){
        p = MouseInfo.getPointerInfo().getLocation();
        return p.getX();
    }
}