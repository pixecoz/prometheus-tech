///POOOOOOWER///

const amogus = "amogus"
//No ABOBA ha ha XdXdXDXDXD

//CONTENT//

const tokamak = extendContent(ImpactReactor, "tokamak", {});

tokamak.size = 3;
tokamak.health = 650;
tokamak.ambientSound = Sounds.pulse;
tokamak.ambientSoundVolume = 0.10;

tokamak.itemDuration = 100;

tokamak.powerProduction = 145;
tokamak.consumes.power(48);
tokamak.buildVisibility = BuildVisibility.editorOnly;

tokamak.consumes.liquid(Liquids.water, 0.42);
tokamak.Category = Category.power;
//tokamak.buildVisibility = 
//requirements write down(ama baby baby write now, right now)

Events.on(ClientLoadEvent, cons(e=>{
   tokamak.consumes.item(Vars.content.getByName(ContentType.item,"prometheus-plutonium"));
   tokamak.requirements = ItemStack.with(Vars.content.getByName(ContentType.item,"prometheus-magnetite"), 250, Items.graphite, 350, Vars.content.getByName(ContentType.item,"prometheus-plutonium"), 50, Items.silicon, 300);
}));
