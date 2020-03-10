import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;

public class EditableBufferedReaderML extends BufferedReader {
    private LineML l;
    private int rows;

    public EditableBufferedReaderML(final Reader in, LineML l) {
        super(in);
        this.l = l;
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

    public void getSize() throws InterruptedException{
        final String[] cmd = new String[] { "bash", "-c", "tput cols </dev/tty"};
        Runtime r = Runtime.getRuntime();
        try {
            Process p = r.exec(cmd);
            Reader in = new InputStreamReader(p.getInputStream());
            rows = in.read();
            System.out.print(rows);
            } catch (final IOException e){
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
        int i = 0;
        while(true){
            /*try{
                getSize();
            }catch(Exception e){
                e.printStackTrace();
            } */
            i = read();
            if(i==Key.ENTER){
                return l.getLine();
            }else if(i==Key.LEFT){
                l.moveLeft();
            }else if(i==Key.RIGHT){
                l.moveRight();
            }else if(i==Key.UP){
                l.moveUp();
            }else if(i==Key.DOWN){
                l.moveDown();
            }else if(i==Key.INICIO){
                l.moveFirst();
            }else if(i==Key.FINAL){
                l.moveEnd();
            }else if(i==Key.INSERT){
                l.toogleIns();
            }else if(i==Key.SUPR){
                l.toogleBackspace();
                l.delete();
                l.toogleBackspace();        
            }else if(i==Key.DELETE){
                l.delete();
            }else{
                l.add(i);
            }
        }
    }
}
