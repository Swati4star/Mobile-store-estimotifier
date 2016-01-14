package functs;

/**
 * Created by swati on 21/12/15.
 */
public class MyCalls {
    Long id,score;
    String number,name;
    public void setid(Long x){
        id=x;
    }
    public void setnumber(String x){
        number=x;
    }
    public void setname(String x){
        name=x;
    }
    public void setscore(Long x){
        score=x;
    }
    public Long getId(){
        return id;
    }
    public String getNumber(){
        return number;
    }
    public String getName(){
        return name;
    }
    public Long getScore(){
        return score;
    }
}
