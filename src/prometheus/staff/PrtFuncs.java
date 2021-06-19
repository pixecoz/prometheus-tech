package prometheus.staff;

import arc.util.Log;

public class PrtFuncs {
    // works as print() in python - outputs your arguments and puts Space between them
    public static void print(Object... args){
        //                                    rough number
        StringBuilder out = new StringBuilder(args.length*6);
        for(Object obj : args){
            out.append(String.valueOf(obj));
            out.append(" ");
        }
        Log.info(out.toString());
    }
}
