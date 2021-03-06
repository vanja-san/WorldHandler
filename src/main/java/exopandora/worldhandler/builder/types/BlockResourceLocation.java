package exopandora.worldhandler.builder.types;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class BlockResourceLocation extends ItemResourceLocation
{
	private BlockState state;
	
	public BlockResourceLocation()
	{
		this(null);
	}
	
	public BlockResourceLocation(ResourceLocation resource)
	{
		this(resource, null, null);
	}
	
	public BlockResourceLocation(ResourceLocation resource, BlockState state, CompoundNBT nbt)
	{
		super(resource, nbt);
		this.state = this.findState(state, resource);
	}
	
	private BlockState findState(BlockState state, ResourceLocation resource)
	{
		boolean matchOld = this.state != null && this.state.getBlock().getRegistryName().equals(resource);
		boolean matchNew = state != null && state.getBlock().getRegistryName().equals(resource);
		
		if(matchNew)
		{
			return state;
		}
		
		if(matchOld)
		{
			return this.state;
		}
		
		if(resource != null && ForgeRegistries.BLOCKS.containsKey(resource))
		{
			return ForgeRegistries.BLOCKS.getValue(resource).getDefaultState();
		}

		return null;
	}
	
	@Override
	public void setResourceLocation(ResourceLocation resource)
	{
		super.setResourceLocation(resource);
		this.state = this.findState(null, resource);
	}
	
	public BlockState getState()
	{
		return this.state;
	}
	
	public <T extends Comparable<T>> void withState(IProperty<T> property, T value)
	{
		if(this.state != null && this.state.has(property))
		{
			this.state = this.state.with(property, value);
		}
	}
	
	@Nullable
	public static BlockResourceLocation valueOf(String input)
	{
		if(input != null)
		{
			BlockStateParser parser = new BlockStateParser(new StringReader(input), false);
			
			try
			{
				parser.parse(true);
			}
			catch(CommandSyntaxException e)
			{
				return null;
			}
			
			BlockState state = parser.getState();
			
			if(state != null)
			{
				return new BlockResourceLocation(state.getBlock().getRegistryName(), state, parser.getNbt());
			}
		}
		
		return null;
	}
	
	@Override
	public BlockResourceLocation get()
	{
		return (BlockResourceLocation) super.get();
	}
	
	@Override
	public String toString()
	{
		if(this.resource != null && this.state != null)
		{
			StringBuilder builder = new StringBuilder(this.state.toString());
			String block = this.state.getBlock().toString();
			builder.replace(0, block.length(), this.resource.toString());
			
			if(this.nbt != null)
			{
				builder.append(this.nbt.toString());
			}
			
			return builder.toString();
		}
		
		return null;
	}
}
