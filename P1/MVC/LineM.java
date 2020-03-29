import java.util.Observable;

public class LineM extends Observable {

    public int index;
    public StringBuilder str;
    public Boolean insert;
    public Boolean backspace;

    public LineM(){
        str = new StringBuilder();
        index =0;
        insert = false;
        backspace = false;
    }

    public void moveRight(){
        index = index < str.length() ? index + 1 : str.length();
        setChanged();
        notifyObservers();
    }
    public void moveLeft(){
        index = index > 0 ? index - 1 : 0;
        setChanged();
        notifyObservers();
    }
    public void moveEnd(){
        index = str.length();
        setChanged();
        notifyObservers();
    }
    public void moveFirst(){
        index = 0;
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
    }
    public void add(int i){
        if(insert && str.length()>index){
            str.setCharAt(index,(char)i);
        }else{
            str.insert(index,(char)i);
        }
        index +=1;
        setChanged();
        notifyObservers();
    }
    public String getLine(){
        return str.toString();
    }
    public int getIndex(){
        return index;
    }
}