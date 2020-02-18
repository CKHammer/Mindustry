package mindustry.maps.zonegen;

import arc.math.*;
import mindustry.content.*;
import mindustry.maps.generators.*;
import mindustry.world.*;

import static mindustry.Vars.schematics;

//TODO remove
public class OvergrowthGenerator extends BasicGenerator{

    public OvergrowthGenerator(int width, int height){
        super(width, height, Blocks.oreCopper, Blocks.oreLead, Blocks.oreCoal, Blocks.oreCopper);
    }

    @Override
    public void generate(int x, int y){
    }

    @Override
    public void decorate(Tiles tiles){
        ores(tiles);
        //terrain(tiles, Blocks.sporePine, 70f, 1.4f, 1f);

        int rand = 40;
        int border = 25;
        int spawnX = Mathf.clamp(30 + Mathf.range(rand), border, width - border), spawnY = Mathf.clamp(30 + Mathf.range(rand), border, height - border);
        int endX = Mathf.clamp(width - 30 + Mathf.range(rand), border, width - border), endY = Mathf.clamp(height - 30 + Mathf.range(rand), border, height - border);

        brush(tiles, pathfind(tiles, spawnX, spawnY, endX, endY, tile -> (tile.solid() ? 5f : 0f) + (float)sim.octaveNoise2D(1, 1, 1f / 50f, tile.x, tile.y) * 50, manhattan), 6);
        brush(tiles, pathfind(tiles, spawnX, spawnY, endX, endY, tile -> (tile.solid() ? 4f : 0f) + (float)sim.octaveNoise2D(1, 1, 1f / 90f, tile.x+999, tile.y) * 70, manhattan), 5);

        erase(tiles, endX, endY, 10);
        erase(tiles, spawnX, spawnY, 20);
        distort(tiles, 20f, 4f);
        inverseFloodFill(tiles, tiles.getn(spawnX, spawnY), Blocks.sporerocks);

        //noise(tiles, Blocks.darksandTaintedWater, Blocks.duneRocks, 4, 0.7f, 120f, 0.64f);
        //scatter(tiles, Blocks.sporePine, Blocks.whiteTreeDead, 1f);

        tiles.getn(endX, endY).setOverlay(Blocks.spawn);
        schematics.placeLoadout(loadout, spawnX, spawnY);
    }
}
