package local.pixy.cheateroo.util;

import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.util.math.Vec3i;

public class ParseUtils {
	/**
	 * Converts a String like "10 20 30" into a Vec3i that will then contain 10 20
	 * 30. If a parsing error occurs it will return a Vec3i with 0 0 0.
	 * 
	 * @param input
	 * @return
	 */
	public static final Vec3i stringToVec3i(final String input) {
		try {
			String[] parts = input.split(" ");
			if (parts.length != 3)
				throw new Throwable("Wrong input!!!");
			final int x = NumberUtils.toInt(parts[0]);
			final int y = NumberUtils.toInt(parts[1]);
			final int z = NumberUtils.toInt(parts[2]);
			return new Vec3i(x, y, z);
		} catch (final Throwable e) {
			System.out.println("you called ParseUtils.stringToVec3i with \"" + input + "\", thats a wrong input!");
			return new Vec3i(0, 0, 0);
		}
	}
}
