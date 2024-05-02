package org.tyaa.training.server.utils;

public class ErrorsGetter {
    public static String getException(Exception _ex) {
        String errorString = "";
        if (_ex.getMessage() == null) {
            StringBuilder errorTrace = new StringBuilder();
            for (StackTraceElement el : _ex.getStackTrace()) {
                errorTrace.append(el.toString());
            }
            if (!errorTrace.toString().equals("")) {
                errorString = errorTrace.toString();
            }
        } else {
            int lineNumber = 0;
            String className = "";
            try {
                lineNumber = _ex.getStackTrace()[0].getLineNumber();
                className = _ex.getStackTrace()[0].getClassName();
            } catch (Exception ex) {
                try {
                    lineNumber = _ex.getCause().getStackTrace()[0].getLineNumber();
                    className = _ex.getCause().getStackTrace()[0].getClassName();
                } catch (Exception ignored) {
                }
            }
            errorString = _ex.getMessage() + "line: " + lineNumber + "; class: " + className;
        }
        return errorString;
    }
}
