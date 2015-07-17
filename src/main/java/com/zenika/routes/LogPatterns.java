package com.zenika.routes;

/**
 * Created by armel on 16/07/15.
 */
public final class LogPatterns {

    public static String logAllDebugForRoute(String routeName){
        return "log:com.zenika.routes" + routeName + "?level=DEBUG&showAll=true&multiline=true";
    }

    public static String logAllWarnForRoute(String routeName){
        return "log:com.zenika.routes" + routeName + "?level=WARN&showAll=true&multiline=true";
    }

    public static String logAllInfoForRoute(String routeName){
        return "log:com.zenika.routes" + routeName + "?level=INFO&showAll=true&multiline=true";
    }
}
