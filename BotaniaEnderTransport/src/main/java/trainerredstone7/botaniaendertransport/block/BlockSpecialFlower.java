package trainerredstone7.botaniaendertransport.block;

import java.util.function.Supplier;

import net.minecraft.potion.Effect;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;

public class BlockSpecialFlower extends vazkii.botania.common.block.BlockSpecialFlower {
	public BlockSpecialFlower(Effect stewEffect, int stewDuration, Properties props, Supplier<? extends TileEntitySpecialFlower> teProvider) {
		super(stewEffect, stewDuration, props, teProvider);
	}
}
