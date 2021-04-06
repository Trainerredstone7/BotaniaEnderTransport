package trainerredstone7.botaniaendertransport.item;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class ItemBlockEnderLavender extends ItemBlockSpecialFlower {
	public ItemBlockEnderLavender(Block block1, Properties props) {
		super(block1, props);
	}
	
	//Can't figure out how to hook into Botania's code to add the functional flower text so adding it this way
	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
		tooltip.add(new TranslationTextComponent("botania.flowerType.functional").mergeStyle(TextFormatting.ITALIC, TextFormatting.BLUE));
		if (ConfigHandler.CLIENT.referencesEnabled.get()) {
			String key = "block.botaniaendertransport.ender_lavender.reference";
			IFormattableTextComponent lore = new TranslationTextComponent(key);
			if (!lore.getString().equals(key)) {
				tooltip.add(lore.mergeStyle(TextFormatting.ITALIC, TextFormatting.GRAY));
			}
		}
	}
}
