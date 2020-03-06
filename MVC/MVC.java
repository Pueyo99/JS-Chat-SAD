import java.io.InputStreamReader;

public class MVC {
    private LineM l;
    private Console c;
    public MVC(){
        l = new LineM();
        c = new Console(l);
        l.addObserver(c);
    }
    public LineM getLineM(){
        return l;
    }

    public static void main(String [] args){
        MVC m = new MVC();
        EditableBufferedReaderC in = new EditableBufferedReaderC(
                new InputStreamReader(System.in),m.getLineM());
        String str = null;
        try {
            in.setRaw();
            str = in.readLine();
            in.unsetRaw();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nline is: " + str);
        
    }
}