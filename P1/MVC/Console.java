package P1.MVC;

import java.util.Observer;
import java.util.Observable;

public class Console implements Observer{

    private LineM l;

    public Console(LineM l){
        this.l = l;
    }

    public void update(Observable obs, Object obj){
        System.out.print("\u001b[1000D");
        System.out.print("\u001b[0K");
        System.out.print(l.getLine());
        System.out.print("\u001b[1000D");
        if(l.getIndex()!=0){
            System.out.print("\u001b[" + l.getIndex() + "C");
        }
    }
}