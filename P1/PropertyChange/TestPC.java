package P1.PropertyChange;

import java.io.InputStreamReader;

public class TestPC {
    private LinePCS l;
    private ConsolePCL c;
    public TestPC(){
        l = new LinePCS();
        c = new ConsolePCL();
        l.getPCS().addPropertyChangeListener(c);
    }
    public LinePCS getLinePCS(){
        return l;
    }

    public static void main(String [] args){
        TestPC t = new TestPC();
        EditableBufferedReaderPC in = new EditableBufferedReaderPC(
                new InputStreamReader(System.in),t.getLinePCS());
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