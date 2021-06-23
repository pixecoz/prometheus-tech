package prometheus;

import arc.Core;
import arc.Events;
import arc.struct.Seq;
import arc.util.*;

import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;

import mindustry.world.Block;
import prometheus.content.*;
import prometheus.staff.PrtInputCheck;

import java.lang.reflect.Field;

public class PrometheusMod extends Mod{

    private final ContentList[] prometheusContent = {
            new PrtItems(),
            new PrtBlocks(),
            new PrtLiquids(),
            new PrtUnitTypes()
    };

    public PrometheusMod(){

        //да да, каждый уважающий себя жава мод должен иметь такую всплывающую картинку, ведь так?
        //listen for game load event
        Events.on(EventType.ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("Hello");
                dialog.cont.add("aboba").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("prometheus-icon")).pad(20f).size(700,420f).row();
                dialog.cont.button("Ok", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });

    }

    @Override
    public void init(){
        PrtInputCheck.init();
    }

    @Override
    public void loadContent(){
        for(ContentList list : prometheusContent){
            list.load();

            Log.info("@: Loaded content list: @", getClass().getSimpleName(), list.getClass().getSimpleName());
        }
    }
}
