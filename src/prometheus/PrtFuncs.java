package prometheus;

import arc.util.Log;

public class PrtFuncs {
    // работает как print в питоне - сколько угодно аргументов выводит и само ставит пробел между ними
    public static void print(Object... args){
        //                                    ну так, примерное число
        StringBuilder out = new StringBuilder(args.length*6);
        for(Object obj : args){
            out.append(String.valueOf(obj));
            out.append(" ");
        }
        Log.info(out.toString());
    }
}
