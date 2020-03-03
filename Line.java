public class Line{

    public int index;
    public String str;
    public Boolean insert;

    public Line(){
        str = new String();
        index =0;
        insert = true;
    }

    public void moveRight(){
        index +=1;
    }
    public void moveLeft(){
        index -=1;
    }
    public void moveEnd(){
        index = str.length();
    }
    public void moveFirst(){
        index = 0;
    }
    public void toogleIns(){
        insert = !insert;
    }

    public String delete(){
        //Creamos nuevo String sin el caracter borrado
        String str1 = str.substring(0, index-1);
        String str2 = str.substring(index);
        str = str1 + str2;
        index -=1;
        return str;
    }
    public String insert(int i){
        //Insertamos el carácter donde toque
        int cursor = 0;
        cursor = insert==true ? index : index-1;
        String str1 = str.substring(0, cursor);
        String str2 = str.substring(cursor+1);
        str = str1 + (char) i + str2;
        index +=1;
        return str;
    }
    public String getLine(){
        return str;
    }
    public int getIndex(){
        return index;
    }
}