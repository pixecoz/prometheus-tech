package prometheus.world.meta;

import mindustry.entities.Effect;
import mindustry.type.StatusEffect;
import prometheus.content.PrtFx;

public class PodStat {
    //pod's damage
    public float damage = 1000f;
    //range of damage
    public float range = 16f;
    //how many materials we need to build this
    public int itemCap = 15;
    //attack effect
    public Effect hitEffect = null;
    //target's effect
    public StatusEffect effect;
    //amount of shots
    public int maxShots = 3;
    //firing rate
    public float speedScale = 1f;

    public PodStat(){
    }

}
