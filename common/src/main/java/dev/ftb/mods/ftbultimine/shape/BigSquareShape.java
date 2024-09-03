package dev.ftb.mods.ftbultimine.shape;

public class BigSquareShape extends BigTunnelShape {
	@Override
	public String getName() {
		return "big_square";
	}

	@Override
	protected int maxDepth() {
		return 1;
	}
}