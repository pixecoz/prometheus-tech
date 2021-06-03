///CONSTANTS///

const ModTurrets = require("blocks/turrets");

///FUNCTIONS///

function TechNode(Parent, block, requirements) {
var parent = TechTree.all.find(node => node.content == Parent);
var node = new TechTree.TechNode(parent, block, requirements);
return node;
};

function createPhantomBlock(name, TexturePixelSize) {
const PB = extendContent(Block, name, {});
PB.size = TexturePixelSize/32;
//not change
PB.health = 0.1;
PB.buildVisibility = BuildVisibility.editorOnly;
return PB;
};

///TECH TREE///

const SerpuloPlanetIcon = createPhantomBlock("Planet Serpulo", 64);
SerpuloPlanetIcon.alwaysUnlocked = true;

TechNode(Blocks.coreShard, SerpuloPlanetIcon, ItemStack.empty);


