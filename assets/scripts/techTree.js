///CONSTANTS///

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
PB.buildVisibility = BuildVisibility.editorOnly;
return PB;
};

///TECH TREE///

const SunStarIcon = createPhantomBlock("SunStarIcon", 64);
SerpuloStarIcon.alwaysUnlocked = true;

const SerpuloPlanetIcon = createPhantomBlock("SerpuloPlanetIcon", 64);
SerpuloPlanetIcon.alwaysUnlocked = true;

TechNode(Blocks.coreShard, SunStarIcon, ItemStack.empty);//временно
TechNode(SunStarIcon, SerpuloPlanetIcon, ItemStack.empty);
//TechTree.TechNode(SerpuloPlanetIcon, TechTree.get(Blocks.coreShard), ItemStack.empty);

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
