package com.cscie97.store.model.test;

import java.util.List;

/**
 * Utility class to output the details of the objects
 * @author Tofik Mussa
 */
public class DetailsUtil {

    /**
     * Outputs details of the calling object including its children
     * @param desc
     * @param name
     * @param location
     * @param children
     * @return details of the calling object
     */
    public static String outputDetails(String desc, String name, String location, List<?> children) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append(desc + name + " is located in " + location);
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("Also contains ");
        stringBuffer.append(System.getProperty("line.separator"));
        if(children != null && children.size() >= 1){
            stringBuffer.append(outputChildren(children));
        }
        stringBuffer.append(System.getProperty("line.separator"));
        return stringBuffer.toString();
    }

    /**
     * Outputs children of the calling object
     * @param children
     * @return details of the children of a calling object
     */
    private static String outputChildren(List<?> children) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(System.getProperty("line.separator"));
        for(int i = 0; i < children.size(); i++){
            stringBuffer.append(children.get(0).toString());
            stringBuffer.append(System.getProperty("line.separator"));
        }
        return stringBuffer.toString();
    }

    /**
     * prints confirmation of object creation
     * @param name
     * @return confirmation of object creation
     */
    public static String outputConfirmation(String name) {
        return name + " has been created ";
    }

    /**
     * Prints confirmation of an object being updated
     * @param name
     * @param change
     * @return
     */
    public static String outputUpdateConfirmation(String name, String change) {
        return name + " has been updated. Change is " + change;
    }

    /**
     * Marks beginning of script
     * @return beginning of script
     */
    public static String beginOfScript(){
        return "=====================================Welcome====================================================";
    }


}


