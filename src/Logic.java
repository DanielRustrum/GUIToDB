package guiForm;
import java.util.*;
public class Logic {

    public List<String> classes;
    private guiForm.DBWrapper wrapper;

    public Logic()
    {
        wrapper = new guiForm.DBWrapper();
        classes = new List<String>();
    }
    public void addClass(String className, String capacity){}
    public void removeClass(Object item){}
    public void enrollStudent(Object item, String fName, String lName, String id){}
    public void addToClasses(String Class){}
    public void removeFromClasses(String Class){}
    public List<String> getClasses(){return classes;}
}
