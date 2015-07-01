package musician101.controlcreativemode.common.config;

public abstract class AbstractConfig
{
	boolean blockLavaBucket;
	boolean blockTNTMinecart;
	boolean blockWaterBucket;
	boolean checkForUpdate;
	
	public boolean isLavaBucketBlocked()
	{
		return blockLavaBucket;
	}
	
	public void setLavaBucketBlocked(boolean blockLavaBucket)
	{
		this.blockLavaBucket = blockLavaBucket;
	}
	
	public boolean isTNTMinecartBlocked()
	{
		return blockTNTMinecart;
	}
	
	public void setTNTMinecartBlocked(boolean blockTNTMinecart)
	{
		this.blockTNTMinecart = blockTNTMinecart;
	}
	
	public boolean isWaterBucketBlocked()
	{
		return blockWaterBucket;
	}
	
	public void setWaterBucketBlocked(boolean blockWaterBucket)
	{
		this.blockLavaBucket = blockWaterBucket;
	}
	
	public boolean checkForUpdate()
	{
		return checkForUpdate();
	}
	
	public void setCheckForUpdate(boolean checkForUpdate)
	{
		this.checkForUpdate = checkForUpdate;
	}
}
