package trainerredstone7.botaniaendertransport;

import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class EnderLavenderTeleporter implements ITeleporter {
	
	private static EnderLavenderTeleporter instance = new EnderLavenderTeleporter();
	
	private EnderLavenderTeleporter() {}
	
	public static EnderLavenderTeleporter getInstance() {
		return instance;
	}

    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
    {
        return repositionEntity.apply(false);
    }
    
    @Nullable
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo)
    {
        return new PortalInfo(entity.getPositionVec(), Vector3d.ZERO, entity.rotationYaw, entity.rotationPitch);
    }
}
