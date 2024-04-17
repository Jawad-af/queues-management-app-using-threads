package org.queuemgmtsys.global;

import java.io.File;

public class Globals {
    public volatile static boolean exit = false;
    public static int currentTime;
    public static File file = new File("Output.txt");
}
