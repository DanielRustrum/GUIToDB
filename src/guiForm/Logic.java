package guiForm;

import java.util.*;

public class Logic
{

    public ArrayList<String> classes;
    private guiForm.DBWrapper wrapper;

    public Logic()
    {
        wrapper = new guiForm.DBWrapper();
        classes = new ArrayList<String>();
    }
    public String addClass(String className, String capacity)
    {

        return "Error";
    }

    public String removeClass(String item)
    {
        return "Error";
    }

    public String enrollStudent(String item, String fName, String lName, String id)
    {
        int studentId = Integer.parseInt(id);
        try
        {
            wrapper.enrollStudent(studentId,fName,lName,item);
        }
        catch (Exception exception)
        {}
        return "Error";
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