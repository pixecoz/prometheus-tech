///CONSTANTS///

Events.on(ClientLoadEvent, cons(e=>{
    const Koluro = Vars.content.getByName(ContentType.planet,"prometheus-koluro");
    const CarputoPlanet = Vars.content.getByName(ContentType.planet,"prometheus-CarputoPlanet");
    
    const testSector = Vars.content.getByName(ContentType.sector,"prometheus-testSector");
}));

///FUNCTIONS///

function changeParent(child, newParent){
	var childNode = TechTree.get(child);
	var newParentNode = TechTree.get(newParent);
	childNode.parent.children.remove(childNode);
	newParentNode.children.add(childNode);	
};

function TechNode(Parent, block, requirements) {
var parent = TechTree.all.find(node => node.content == Parent);
var node = new TechTree.TechNode(parent, block, requirements);
return node;
};

function createPhantomBlock(name, TexturePixelSize) {
const PB = extendContent(Wall, name, {});
PB.size = TexturePixelSize/32;
//not change
PB.health = 0.1;
PB.buildVisibility = BuildVisibility.hidden;
return PB;
};

///TECH TREE///

//MOD TECH TREE//

const SunStarIcon = createPhantomBlock("SunStarIcon", 64);
SunStarIcon.alwaysUnlocked = true;//SUN TO TECH TREE

const SerpuloPlanetIcon = createPhantomBlock("SerpuloPlanetIcon", 64);
SerpuloPlanetIcon.alwaysUnlocked = true;

const KoluroStarIcon = createPhantomBlock("KoluroStarIcon", 64);

const CarputoPlanetIcon = createPhantomBlock("CarputoPlanetIcon", 64);

Events.run(Trigger.acceleratorUse, run(() => {
KoluroStarIcon.unlocked = true;
CarputoPlanetIcon.unlocked = true;
	
testSector.unlocked = true;

CarputoPlanet.accessible = true;
}));


TechNode(Blocks.coreShard, SunStarIcon, ItemStack.empty);//временно
TechNode(SunStarIcon, SerpuloPlanetIcon, ItemStack.empty);
TechNode(SunStarIcon, KoluroStarIcon, ItemStack.empty);
TechNode(KoluroStarIcon, CarputoPlanetIcon, ItemStack.empty);


//VANILLA TECH TREE//

changeParent(SectorPresets.groundZero, SerpuloPlanetIcon);
changeParent(SectorPresets.frozenForest, SectorPresets.groundZero);
changeParent(SectorPresets.craters, SectorPresets.frozenForest);
changeParent(SectorPresets.ruinousShores, SectorPresets.craters);
changeParent(SectorPresets.windsweptIslands, SectorPresets.ruinousShores);
changeParent(SectorPresets.tarFields, SectorPresets.windsweptIslands);
changeParent(SectorPresets.impact0078, SectorPresets.tarFields);
changeParent(SectorPresets.desolateRift, SectorPresets.impact0078);
changeParent(SectorPresets.planetaryTerminal, SectorPresets.desolateRift);

//ВСЁ парам-парам-пам!
