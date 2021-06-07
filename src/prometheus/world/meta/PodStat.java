package prometheus.world.meta;

import mindustry.entities.Effect;
import mindustry.type.StatusEffect;
import prometheus.content.PrtFx;

public class PodStat {
    //pod's damage
    public float damage;
    //range of damage
    public float range = 16f;
    //how many materials we need to build this
    public int itemCap = 15;
    //attack effect
    public Effect hitEffect = null;
    //target's effect
    public StatusEffect effect;

    public PodStat(){
    }

}
