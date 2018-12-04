package guiForm;
import java.util.*;
public class Logic
{

    public ArrayList<String> classes;
    private guiForm.DBWrapper wrapper;

    public Logic()
    {
        wrapper = new guiForm.DBWrapper();
        classes = new ArrayList<>();
    }
    public void addClass(String className, String capacity)
    {

    }

    public void removeClass(String item)
    {

    }

    public void enrollStudent(String item, String fName, String lName, String id)
    {

    }

    public void addToClasses(String Class)
    {
        classes.add(Class);
    }

    public void removeFromClasses(String Class)
    {
        classes.remove(Class);
    }

    public ArrayList<String> getClasses()
    {
        return classes;
    }
}
