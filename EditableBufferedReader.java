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
        final String[] cmd = new String[] { "/bin/sh", "-c", "stty -echo cooked </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public int read(){
        try{
            int i = System.in.read();
            if(i==27){
                i = System.in.read();
                if(i==91){
                    i =System.in.read();
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
                            i = System.in.read();
                            if(i==126){
                                return Key.INSERT;  
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
        int i = 0;
        while(true){
            i = read();
            if(i==Key.ENTER){ 
                return l.getLine();
            }else if(i==Key.LEFT){
                System.out.print(Key.PRINTLEFT);
                l.moveLeft();
            }else if(i==Key.RIGHT){
                System.out.print(Key.PRINTRIGHT);
                l.moveRight();
            }else if(i==Key.INICIO){
                l.moveFirst();
                System.out.print("\u001b[1000D");
            }else if(i==Key.FINAL){
                l.moveEnd();
                System.out.print("\u001b[1000D");
                System.out.print("\u001b["+ l.getIndex() +"C");
            }else if(i==Key.INSERT){
                l.toogleIns();
                
            }else if(i==Key.DELETE){
                System.out.print("\u001b[1000D");
                System.out.print("\u001b[0K");
                System.out.print(l.delete());
                System.out.print("\u001b[1000D");
                System.out.print("\u001b[" + l.getIndex() + "C");
            }else{
                System.out.print("\u001b[1000D");
                System.out.print("\u001b[0K");
                System.out.print(l.insert(i));
                System.out.print("\u001b[1000D");
                System.out.print("\u001b[" + l.getIndex() + "C");
                
            }
        }
    }
}