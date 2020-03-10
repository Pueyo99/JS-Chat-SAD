import java.beans.*;

public class ConsoleML implements PropertyChangeListener{
    public int nCols;
    public int index;

    public ConsoleML(int c){
        nCols = c;

    }
    public void propertyChange(PropertyChangeEvent e){
        System.out.print(Key.orderRESET);
        System.out.print(Key.orderDELETE);
        System.out.print(e.getOldValue());
        System.out.print(Key.orderRESET);
        index = (int) e.getNewValue();
        if(index!=0){
            System.out.print("\u001b[" + index%nCols + "C");
        }
        if(index/nCols!=0){
            System.out.print("\u001b[" + index/nCols + "B");
        }
    }

}