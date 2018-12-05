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
        String result = "";
        try
        {
            int cap = Integer.parseInt(capacity);
            //result = wrapper.addClassAdmin(className, cap);
            addToClasses(className);
        }
        catch (Exception exception)
        {
            return "Error: Capacity needs to be a number";
        }
        return result;
    }

    public String removeClass(String item)
    {
        String result = "";
        try
        {
            //result = wrapper.deleteClassAdmin(item);
            removeFromClasses(item);
        }
        catch (Exception exception)
        {
            return "Error";
        }
        return result;
    }

    public String enrollStudent(String item, String fName, String lName, String id)
    {
        String result = "";
        try
        {
            int studentId = Integer.parseInt(id);
            //result = wrapper.enrollStudent(studentId,fName,lName,item);
        }
        catch (Exception exception)
        {
            return "Error";
        }
        return result;
    }

    public void addToClasses(String Class)
    {
        classes.add(Class);
    }

    public void removeFromClasses(String Class)
    {
        if(classes.size() <= 1)
        {
            classes.clear();
        }
        else
        {
            classes.remove(Class);
        }
    }

    public ArrayList<String> getClasses()
    {
        return classes;
    }
}