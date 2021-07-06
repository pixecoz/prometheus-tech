package prometheus.core;

import arc.Core;
import arc.audio.Sound;

public class PrtSounds {
    public static Sound timeBreak = new Sound();
    public static void load() {
        Core.assets.load("sounds/timebreak.ogg", Sound.class).loaded = a -> timeBreak = (Sound) a;
    }
}
