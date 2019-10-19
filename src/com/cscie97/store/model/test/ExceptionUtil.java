package com.cscie97.store.model.test;


/**
 * A utility class to output exceptions
 * @author Tofik Mussa
 */
public class ExceptionUtil {

    /**
     * Outputs details of an exception
     * @param lineNumber
     * @param failure
     * @param exception
     * @return details of an exception
     */
    public static String outputException(int lineNumber, String failure, Exception exception) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("Command failed at " + lineNumber);
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append(failure);
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("Root case of the issue is ");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append(exception.getCause());
        stringBuffer.append(System.getProperty("line.separator"));
        return stringBuffer.toString();
    }
}
