package trainerredstone7.botaniaendertransport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import trainerredstone7.botaniaendertransport.block.BlockSpecialFlower;
import trainerredstone7.botaniaendertransport.block.subtile.SubTileEnderLavender;
import trainerredstone7.botaniaendertransport.item.ItemBlockEnderLavender;
import vazkii.botania.client.render.tile.RenderTileSpecialFlower;
import vazkii.botania.common.block.BlockFloatingSpecialFlower;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("botaniaendertransport")
public class BotaniaEnderTransport
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "botaniaendertransport";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
        
    public static final RegistryObject<Block> ENDER_LAVENDER_BLOCK = BLOCKS.register("ender_lavender", () -> new BlockSpecialFlower(Effects.LEVITATION, 30, AbstractBlock.Properties.from(Blocks.POPPY), SubTileEnderLavender::new));
    public static final RegistryObject<Block> ENDER_LAVENDER_FLOATING_BLOCK = BLOCKS.register("floating_ender_lavender", () -> new BlockFloatingSpecialFlower(vazkii.botania.common.block.ModBlocks.FLOATING_PROPS, SubTileEnderLavender::new));
    public static final RegistryObject<TileEntityType<SubTileEnderLavender>> ENDER_LAVENDER_TILE = TILE_ENTITIES.register("ender_lavender_tile", () -> TileEntityType.Builder.create(SubTileEnderLavender::new, ENDER_LAVENDER_BLOCK.get(), ENDER_LAVENDER_FLOATING_BLOCK.get()).build(null));
    public static final RegistryObject<BlockItem> ENDER_LAVENDER_ITEM = ITEMS.register("ender_lavender", () -> new ItemBlockEnderLavender(ENDER_LAVENDER_BLOCK.get(), vazkii.botania.common.item.ModItems.defaultBuilder()));
    public static final RegistryObject<BlockItem> ENDER_LAVENDER_FLOATING_ITEM = ITEMS.register("floating_ender_lavender", () -> new ItemBlockEnderLavender(ENDER_LAVENDER_FLOATING_BLOCK.get(), vazkii.botania.common.item.ModItems.defaultBuilder()));
    
    public BotaniaEnderTransport() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ENDER_LAVENDER_TILE.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ENDER_LAVENDER_BLOCK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ENDER_LAVENDER_FLOATING_BLOCK.get(), RenderType.getCutout());
    }
}
