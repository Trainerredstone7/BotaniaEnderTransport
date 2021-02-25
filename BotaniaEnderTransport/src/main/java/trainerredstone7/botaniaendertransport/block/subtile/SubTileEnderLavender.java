package trainerredstone7.botaniaendertransport.block.subtile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import trainerredstone7.botaniaendertransport.BotaniaEnderTransport;
import trainerredstone7.botaniaendertransport.EnderLavenderTeleporter;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;
import vazkii.botania.common.network.PacketBotaniaEffect;
import vazkii.botania.common.network.PacketHandler;
import vazkii.botania.mixin.AccessorItemEntity;


public class SubTileEnderLavender extends TileEntityFunctionalFlower {
	private static final int COST = 24;
	private static final int RANGE = 2;

	public SubTileEnderLavender() {
		super(BotaniaEnderTransport.ENDER_LAVENDER_TILE.get());
	}

	@Override
	public void tickFlower() {
		super.tickFlower();

		if (!getWorld().isRemote) {
			ServerWorld destination;
			RegistryKey<World> worldKey = getWorld().getDimensionKey();
			if (worldKey == World.THE_END) destination = getWorld().getServer().getWorld(World.OVERWORLD);
			else if (worldKey == World.OVERWORLD) destination = getWorld().getServer().getWorld(World.THE_END);
			else return;
			
			if (redstoneSignal == 0 && destination.isBlockLoaded(getEffectivePos())) {
				BlockPos pos = getEffectivePos();

				boolean did = false;

				List<ItemEntity> items = getWorld().getEntitiesWithinAABB(
						ItemEntity.class,
						new AxisAlignedBB(pos.add(-RANGE, -RANGE, -RANGE),
								pos.add(RANGE + 1, RANGE + 1, RANGE + 1)));
				int slowdown = getSlowdownFactor();

				for (ItemEntity item : items) {
					int age = ((AccessorItemEntity) item).getAge();
					if (age < 60 + slowdown || !item.isAlive()) {
						continue;
					}

					ItemStack stack = item.getItem();
					if (!stack.isEmpty()) {
						Item sitem = stack.getItem();
						if (sitem instanceof IManaItem) {
							continue;
						}
						int cost = stack.getCount() * COST;
						if (getMana() >= cost) {
							spawnExplosionParticles(item, 10);
							item.changeDimension(destination, EnderLavenderTeleporter.getInstance());
							item.setMotion(Vector3d.ZERO);
							spawnExplosionParticles(item, 10);
							addMana(-cost);
							did = true;
						}
					}
				}

				if (did) {
					sync();
				}
			}
		}
	}

	static void spawnExplosionParticles(Entity item, int p) {
		PacketHandler.sendToNearby(item.world, item.getPosition(), new PacketBotaniaEffect(PacketBotaniaEffect.EffectType.ITEM_SMOKE, item.getPosX(), item.getPosY(), item.getPosZ(), item.getEntityId(), p));
	}
	
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
	}
	
	@Override
	public boolean acceptsRedstone() {
		return true;
	}
	
	@Override
	public int getColor() {
		return 0xB544E6;
	}
	
	@Override
	public int getMaxMana() {
		return 16000;
	}
}
