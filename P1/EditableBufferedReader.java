package P1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(final Reader in) {
        super(in);
    }

    public void setRaw() throws InterruptedException {
        final String[] cmd = new String[] { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw() throws InterruptedException {
        final String[] cmd = new String[] { "/bin/sh", "-c", "stty -echo sane </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public int read(){
        try{
            int i = super.read();
            if(i==27){
                i = super.read();
                if(i==91){
                    i =super.read();
                    switch(i){
                        case 72:
                        return Key.INICIO;
                        case 70:
                        return Key.FINAL;
                        case 68:
                        return Key.LEFT;
                        case 67: 
                        return Key.RIGHT;
                        case 66: 
                        return Key.DOWN;
                        case 65: 
                        return Key.UP;
                        case 50:
                            i = super.read();
                            if(i==126){
                                return Key.INSERT;  
                            }
                        break;
                        case 51:
                            i=super.read();
                            if(i==126){
                                return Key.SUPR;
                            }                
                    }
                }
            }
            return i;
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }
    
    public String readLine(){
        Line l = new Line();
        String str = new String();
        int i = 0;
        while(true){
            i = read();
            if(i==Key.ENTER){ 
                return l.getLine();
            }else if(i==Key.LEFT){
                l.moveLeft();
            }else if(i==Key.RIGHT){
                l.moveRight();
            }else if(i==Key.INICIO){
                l.moveFirst();
            }else if(i==Key.FINAL){
                l.moveEnd();
            }else if(i==Key.INSERT){
                l.toogleIns();
            }else if(i==Key.SUPR){
                l.toogleBackspace();
                str = l.delete();
                l.toogleBackspace();        
            }else if(i==Key.DELETE){
                str = l.delete();
            }else{
                str = l.add(i);
            }
            System.out.print("\u001b[1000D");
            System.out.print("\u001b[0K");
            System.out.print(str);
            System.out.print("\u001b[1000D");
            if(l.getIndex()!=0){
                System.out.print("\u001b[" + l.getIndex() + "C");
            }
        }
    }
}