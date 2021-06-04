package prometheus.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

public class PrtItems implements ContentList {
    static {
        Items.class.isArray();
    }

    public static Item  platinum, magnetite, plutonium;

    public void load(){
        platinum = new Item("platinum") {
            {
                this.localizedName = "Platinum";
                this.description = "Shiny and heavy resource.";
                this.hardness = 3;
                this.cost = 1;
                this.radioactivity = 0.0f;
                this.color = Color.valueOf("d0e3e5");
            }
        };
                plutonium= new Item("plutonium") {
                    {
                        this.localizedName = "Plutonium";
                        this.description = "Shiny and radioactive resource.";
                        this.hardness = 3;
                        //this.cost = 1;
                        this.radioactivity = 0.9f;
                        this.color = Color.valueOf("b1ddc6");
                    }
                };
                magnetite = new Item("magnetite") {
                    {
                        this.localizedName = "Magnetite";
                        this.description = "Shiny and magnite resource.";
                        this.hardness = 4;
                        this.cost = 2;
                        this.radioactivity = 0.0f;
                        this.color = Color.valueOf("d38989");
                    }
                };

}} 
