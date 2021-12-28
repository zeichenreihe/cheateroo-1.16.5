package local.pixy.cheateroo.util;

import net.minecraft.util.math.BlockPos;

public class DataStorage {
	private static final DataStorage INSTANCE = new DataStorage();
	private BlockPos highlightBlockPos = new BlockPos(0, 0, 0);

	public static DataStorage getInstance() {
		return INSTANCE;
	}

	public void reset() {
		this.highlightBlockPos = new BlockPos(0, 0, 0);
	}

	public BlockPos getHighlightBlockPos() {
		return this.highlightBlockPos;
	}

	public void setHighlightBlockPos(BlockPos highlightBlockPos) {
		this.highlightBlockPos = highlightBlockPos;
	}
}
