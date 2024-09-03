package dev.ftb.mods.ftbultimine.shape;

public class CustomRectangleShape extends CustomTunnelShape {
	@Override
	public String getName() {
		return "custom_rectangle";
	}

	@Override
	protected int maxDepth() {
		return 1;
	}
}