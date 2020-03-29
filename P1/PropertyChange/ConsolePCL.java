import java.beans.*;

public class ConsolePCL implements PropertyChangeListener{

    public ConsolePCL(){

    }
    public void propertyChange(PropertyChangeEvent e){
        System.out.print("\u001b[1000D");
        System.out.print("\u001b[0K");
        System.out.print(e.getOldValue());
        System.out.print("\u001b[1000D");
        if(!e.getNewValue().equals(0)){
            System.out.print("\u001b[" + e.getNewValue() + "C");
        }
    }

}