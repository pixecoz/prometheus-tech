package prometheus.staff;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.actions.Actions;
import arc.scene.event.Touchable;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.ui.Styles;
import org.graalvm.compiler.virtual.phases.ea.EffectList;
import prometheus.type.PrtUnitType;

import static mindustry.Vars.*;
import static prometheus.staff.PrtFuncs.print;

public class PrtInputCheck {

    public static float pressTime;
    //время нажатия на телефоне для активации абилки, в тиках
    public static final float longPressTime = 30f;
    // чтобы при длительном нажатии не использовалась абилка несколько раз
    public static boolean pressed;
    public static KeyCode unitAbilityKey = KeyCode.h;

    static float x,y;
    static float time;

    public static void init() {

        Events.run(EventType.Trigger.update, () -> {

            //TODO должна ли работать абилка в паузе?
            if (!Vars.state.is(GameState.State.menu)) {
                if (player.unit().type instanceof PrtUnitType && ((PrtUnitType) player.unit().type).hasSpecialAbility) {

                    if (mobile) {

                        if (Core.input.isTouched()) {
                            pressTime += Time.delta;
                            if (pressTime > longPressTime && !pressed) {
                                ((PrtUnitType) player.unit().type).specialAbility(player.unit());
                                pressTime = 0f;
                                pressed = true;
                            }
                        } else {
                            pressed = false;
                            pressTime = 0f;
                        }

                    } else {
                        if (Core.input.keyTap(unitAbilityKey)) {
                            ((PrtUnitType) player.unit().type).specialAbility(player.unit());
                        }
                    }
                }
            }

//            print(Vars.state.getState());
//            if (Vars.state.is(GameState.State.paused)){
//                Vars.state.set(GameState.State.playing);
//            }

            time = Mathf.clamp(time-Time.delta,0f,180f);

        });

        /*
        Events.on(EventType.StateChangeEvent.class, e -> {
//            print("from:", e.from, "to:", e.to);
            if (e.from == GameState.State.playing && e.to == GameState.State.paused) {
                Time.runTask(1,()->{
                    Vars.state.set(GameState.State.playing);
//                    Vars.ui.showInfoToast("time flows differently", 3f);

                    if(time>0) return;
                    time = 180f;

                    Table table = new Table();
                    table.setFillParent(true);
                    table.touchable = Touchable.disabled;
                    table.update(() -> {
                        if(state.isMenu()) table.remove();
                        table.x = x + Mathf.range(2f);
                        table.y = x + Mathf.range(2f);
                    });
                    table.actions(Actions.delay(3f * 0.9f), Actions.fadeOut(3f * 0.1f, Interp.fade), Actions.remove());
                    table.top().table(Styles.black3, t -> t.margin(4).add("time goes by differently", Scl.scl(1.5f)).style(Styles.outlineLabel)).padTop(30);
                    Core.scene.add(table);

                    x = table.x;
                    y = table.y;

                    PrtSounds.timeBreak.at(player,1f);

//                    print("state changed");
                });
            }
//            print("after:", Vars.state.getState());
        });
         */

    }
}
