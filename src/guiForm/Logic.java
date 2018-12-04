package guiForm;
import java.util.*;
public class Logic {

    public ArrayList<String> classes;
    private guiForm.DBWrapper wrapper;

    public Logic()
    {
        wrapper = new guiForm.DBWrapper();
        classes = new ArrayList<>();
    }
    public void addClass(String className, String capacity){}
    public void removeClass(Object item){}
    public void enrollStudent(Object item, String fName, String lName, String id){}
    public void addToClasses(String Class){}
    public void removeFromClasses(String Class){}
    public ArrayList<String> getClasses(){return classes;}
}
