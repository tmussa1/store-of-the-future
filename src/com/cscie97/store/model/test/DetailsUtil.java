package com.cscie97.store.model.test;

import java.util.List;

/**
 * @author Tofik Mussa
 */
public class DetailsUtil {

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

    private static String outputChildren(List<?> children) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(System.getProperty("line.separator"));
        for(int i = 0; i < children.size(); i++){
            stringBuffer.append(children.get(0).toString());
            stringBuffer.append(System.getProperty("line.separator"));
        }
        return stringBuffer.toString();
    }

    public static String outputConfirmation(String name) {
        return name + " has been created ";
    }
    public static String outputUpdateConfirmation(String name, String change) {
        return name + " has been updated. Change is " + change;
    }

    public static String beginOfScript(){
        return "=====================================Welcome====================================================";
    }


}


