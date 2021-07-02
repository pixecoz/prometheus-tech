package prometheus.core;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import arc.math.geom.Point2;
import arc.struct.IntSet;
import arc.util.Interval;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.graphics.FloorRenderer;
import mindustry.world.Tile;
import prometheus.type.PrtUnitType;

import java.lang.reflect.Field;

import static mindustry.Vars.*;

public class PrtInputCheck {

    public static float pressTime;
    //время нажатия на телефоне для активации абилки, в тиках
    public static final float longPressTime = 30f;
    // чтобы при длительном нажатии не использовалась абилка несколько раз
    public static boolean pressed;
    public static KeyCode unitAbilityKey = KeyCode.h;

    static float x,y;
    static float time;
    static Interval timer = new Interval();

    public static void init() {

        Events.run(EventType.Trigger.update, () -> {

            //TODO должна ли работать абилка в паузе?
            if (!Vars.state.is(GameState.State.menu)) {
                if (player.unit().type instanceof PrtUnitType && ((PrtUnitType) player.unit().type).hasActiveAbility()) {

                    if (mobile) {

                        if (Core.input.isTouched()) {
                            pressTime += Time.delta;
                            if (pressTime > longPressTime && !pressed) {
                                ((PrtUnitType) player.unit().type).useActiveAbility(player.unit());
                                pressTime = 0f;
                                pressed = true;
                            }
                        } else {
                            pressed = false;
                            pressTime = 0f;
                        }

                    } else {
                        if (Core.input.keyTap(unitAbilityKey)) {
                            ((PrtUnitType) player.unit().type).useActiveAbility(player.unit());
                        }
                    }
                }
            }

//            print(Vars.state.getState());
//            if (Vars.state.is(GameState.State.paused)){
//                Vars.state.set(GameState.State.playing);
//            }

//            time = Mathf.clamp(time-Time.delta,0f,180f);



//            if (state.isGame()){
//                if(timer.get(200f)){
//
//                    Tile tile = world.tileWorld(player.x,player.y);
//
//                    Call.setFloor(tile, Blocks.water, Blocks.air);
////                    renderer.blocks.floor.clearTiles();
//
//                    try {
////                        Log.info(Arrays.toString(FloorRenderer.class.getDeclaredFields()));
//                        Field set = FloorRenderer.class.getDeclaredField("recacheSet");
//                        set.setAccessible(true);
//                        ((IntSet)set.get(renderer.blocks.floor)).add(Point2.pack(tile.x / 32 / 8, tile.y / 32 / 8));
//                        Log.info("recahced");
//                    } catch (Exception e){
//                        Log.info(e);
//                    }
//
//                }
//            }

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
