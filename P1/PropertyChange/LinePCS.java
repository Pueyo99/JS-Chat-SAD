import java.beans.*;

public class LinePCS{

    public int index;
    public StringBuilder str;
    public Boolean insert;
    public Boolean backspace;

    private final PropertyChangeSupport pcs;

    public LinePCS(){
        str = new StringBuilder();
        index =0;
        insert = false;
        backspace = false;
        pcs = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPCS(){
        return pcs;
    }

    public void moveRight(){
        index = index < str.length() ? index + 1 : str.length();
        pcs.firePropertyChange("string",str.toString(),index);
    }
    public void moveLeft(){
        index = index > 0 ? index - 1 : 0;
        pcs.firePropertyChange("string",str.toString(),index);
    }
    public void moveEnd(){
        index = str.length();
        pcs.firePropertyChange("string",str.toString(),index);
    }
    public void moveFirst(){
        index = 0;
        pcs.firePropertyChange("string",str.toString(),index);
    }
    public void toogleIns(){
        insert = !insert;
    }
    public void toogleBackspace(){
        backspace = !backspace;
    }

    public void delete(){
        if(index ==0){
            if(str.length()>0 && backspace){
                str.deleteCharAt(0);
            }
        }else{
            if(backspace && str.length()>index){
                str.deleteCharAt(index);
            }else if(!backspace){
                str.deleteCharAt(index-1);
                index -=1;
            }
        }   
        pcs.firePropertyChange("string",str.toString(),index);
    }
    public void add(int i){
        if(insert && str.length()>index){
            str.setCharAt(index,(char)i);
        }else{
            str.insert(index,(char)i);
        }
        index +=1;
        pcs.firePropertyChange("string",str.toString(),index);
    }
    public String getLine(){
        return str.toString();
    }
    public int getIndex(){
        return index;
    }
    
}