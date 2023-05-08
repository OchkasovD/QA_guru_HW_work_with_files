package guru.qa.files.pojos;
import java.util.List;


public class User {

    private List<Family> children;
    private String name;
    private Integer userid;

    public List<Family> getChildren(){
        return children;
    }

    public String getName(){
        return name;
    }

    public Integer getUserID() {
        return userid;
    }
}
