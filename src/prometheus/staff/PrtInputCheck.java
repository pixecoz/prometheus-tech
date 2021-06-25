package prometheus.staff;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import prometheus.type.PrtUnitType;

import static mindustry.Vars.mobile;
import static mindustry.Vars.player;

public class PrtInputCheck {

    public static float pressTime;
    //время нажатия на телефоне для активации абилки, в тиках
    public static final float longPressTime = 30f;
    // чтобы при длительном нажатии не использовалась абилка несколько раз
    public static boolean pressed;
    public static KeyCode unitAbilityKey = KeyCode.h;

    public static void init(){

        Events.run(EventType.Trigger.update,() ->{

            //TODO должна ли работать абилка в паузе?
            if(!Vars.state.is(GameState.State.menu)){
                if(player.unit().type instanceof PrtUnitType && ((PrtUnitType)player.unit().type).hasSpecialAbility){

                    if(mobile){

                        if (Core.input.isTouched()){
                            pressTime += Time.delta;
                            if(pressTime > longPressTime && !pressed){
                                ((PrtUnitType)player.unit().type).specialAbility(player.unit());
                                pressTime = 0f;
                                pressed = true;
                            }
                        } else {
                            pressed = false;
                            pressTime = 0f;
                        }

                    } else {
                        if(Core.input.keyTap(unitAbilityKey)){
                            ((PrtUnitType)player.unit().type).specialAbility(player.unit());
                        }
                    }
                }
            }

        });



    }
}
