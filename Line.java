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
        String str1;
        String str2;
        if(insert){
            str1 = str.substring(0, index-1);
            str2 = str.substring(index);
            index -=1;
        }else{
            str1 = str.substring(0,index);
            str2 = str.substring(index+1);
        }    
        str = str1 + str2;
        return str;
    }
    public String add(int i){
        String str1;
        String str2;
        if(insert){
            str1 = str.substring(0, index);
            str2 = str.substring(index);
            index +=1;
        }else{
            str1 = str.substring(0,index);
            str2 = str.substring(index+1);
        }
        str = str1 + (char) i + str2;
        return str;
    }
    public String getLine(){
        return str;
    }
    public int getIndex(){
        return index;
    }
}