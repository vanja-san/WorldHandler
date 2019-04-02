package exopandora.worldhandler.builder.impl.abstr;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.types.Coordinate;
import net.minecraft.util.math.BlockPos;

public abstract class BuilderBlockPos extends CommandBuilderNBT
{
	public void setPosition(BlockPos pos)
	{
		this.setX(pos.getX());
		this.setY(pos.getY());
		this.setZ(pos.getZ());
	}
	
	public void setX(float x)
	{
		this.setX(new Coordinate(x));
	}
	
	public void setY(float y)
	{
		this.setY(new Coordinate(y));
	}
	
	public void setZ(float z)
	{
		this.setZ(new Coordinate(z));
	}
	
	public void setX(Coordinate x)
	{
		this.setNode(0, x);
	}
	
	public void setY(Coordinate y)
	{
		this.setNode(1, y);
	}
	
	public void setZ(Coordinate z)
	{
		this.setNode(2, z);
	}
	
	public Coordinate getXCoordinate()
	{
		return this.getNodeAsCoordinate(0);
	}
	
	public Coordinate getYCoordinate()
	{
		return this.getNodeAsCoordinate(1);
	}
	
	public Coordinate getZCoordinate()
	{
		return this.getNodeAsCoordinate(2);
	}
	
	public double getX()
	{
		return this.getXCoordinate().getValue();
	}
	
	public double getY()
	{
		return this.getYCoordinate().getValue();
	}
	
	public double getZ()
	{
		return this.getZCoordinate().getValue();
	}
	
	public BlockPos getBlockPos()
	{
		return new BlockPos(this.getX(), this.getY(), this.getZ());
	}
}
