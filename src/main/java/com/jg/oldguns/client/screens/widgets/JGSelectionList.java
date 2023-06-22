package com.jg.oldguns.client.screens.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;

public class JGSelectionList extends GuiComponent implements Widget {

	public static final Key EMPTYKEY = new Key(Minecraft.getInstance().font, "empty");

	protected Screen screen;
	protected Font font;
	protected boolean visible;
	protected List<Key> keys;
	protected int colHeight;
	protected int colWidth;
	protected int cols;
	// protected int selected;
	protected List<Integer> selected;
	protected int x;
	protected int y;
	protected int from;
	protected int to;
	protected int hideItems;
	protected int scrollHeight;
	protected int scrollWidth;
	protected int scrollY;
	protected int offset;
	protected onSelectKey onSelectKey;

	public JGSelectionList(Key[] keys, Screen screen, Font font, int x, int y, int colWidth, int colHeight, int cols,
			onSelectKey onSelectKey) {
		this.keys = new ArrayList<Key>(Arrays.asList(keys));
		this.screen = screen;
		this.font = font;
		this.visible = true;
		this.x = x;
		this.y = y;
		this.colWidth = colWidth;
		this.colHeight = colHeight;
		this.cols = cols;
		// this.selected = -1;
		this.selected = new ArrayList<>();
		this.from = 0;
		this.to = this.cols > this.keys.size() ? this.keys.size() : this.cols;
		this.hideItems = (this.keys.size() - this.cols) < 0 ? 0 : this.keys.size() - this.cols;
		if (this.hideItems != 0) {
			LogUtils.getLogger()
					.info("HideItems: " + this.hideItems + " keys size: " + this.keys.size() + " hideItems/keys size: "
							+ ((float) this.hideItems / this.keys.size()) + " other: "
							+ (1 - (((float) this.hideItems / this.keys.size()) == 0 ? 1
									: (float) this.hideItems / this.keys.size())));
			this.scrollHeight = (int) Mth.lerp(1 - (((float) this.hideItems / this.keys.size()) == 0 ? 1
					: (float) this.hideItems / this.keys.size()), 0, this.colHeight * this.cols);
		}
		this.scrollWidth = 7;
		this.scrollY = this.y;
		this.offset = 0;
		for (Key key : keys) {
			key.cols = this.cols;
			key.colWidth = this.colWidth;
		}
		this.onSelectKey = onSelectKey;
	}

	@Override
	public void render(PoseStack matrix, int p_94670_, int p_94671_, float p_94672_) {
		if (this.visible) {
			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder bufferbuilder = tesselator.getBuilder();
			// Background
			renderBackground(bufferbuilder, tesselator);

			for (int i = this.from; i < this.to; i++) {
				renderKey(i, matrix, bufferbuilder, tesselator);
			}

			if (this.cols < this.keys.size()) {
				renderScrollbar(bufferbuilder, tesselator);
			}
		}
	}

	protected void renderBackground(BufferBuilder bufferbuilder, Tesselator tesselator) {
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, GuiComponent.BACKGROUND_LOCATION);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferbuilder.vertex((float) this.x, (double) (this.y + (this.colHeight * this.cols)), 0.0D)
				.uv((float) this.x / 32.0F, (float) (this.y + (this.colHeight * this.cols)) / 32.0F)
				.color(32, 32, 32, 255).endVertex();
		bufferbuilder
				.vertex((double) (this.x + (this.colWidth)), (double) (this.y + (this.colHeight * this.cols)), 0.0D)
				.uv((float) (this.x + (this.colWidth)) / 32.0F, (float) (this.y + (this.colHeight * this.cols)) / 32.0F)
				.color(32, 32, 32, 255).endVertex();
		bufferbuilder.vertex((double) (this.x + (this.colWidth)), (double) this.y, 0.0D)
				.uv((float) (this.x + (this.colWidth)) / 32.0F, (float) (this.y) / 32.0F).color(32, 32, 32, 255)
				.endVertex();
		bufferbuilder.vertex((double) this.x, (double) this.y, 0.0D)
				.uv((float) this.x / 32.0F, (float) (this.y) / 32.0F).color(32, 32, 32, 255).endVertex();
		tesselator.end();
	}

	protected void renderScrollbar(BufferBuilder bufferbuilder, Tesselator tesselator) {
		float nw = 7;
		float nh = this.colHeight * this.cols;
		float nx = this.x + this.colWidth;
		float ny = this.y;
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0F, 0F, 0F, 0.1F);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferbuilder.vertex(nx, nh + ny, 0).endVertex();
		bufferbuilder.vertex(nw + nx, nh + ny, 0).endVertex();
		bufferbuilder.vertex(nw + nx, ny, 0).endVertex();
		bufferbuilder.vertex(nx, ny, 0).endVertex();
		tesselator.end();

		float nw2 = 7;
		float nh2 = this.scrollHeight;
		float nx2 = this.x + this.colWidth;
		float ny2 = this.scrollY;

		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.7706F, 0.7706F, 0.7706F, 0.1F);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferbuilder.vertex(nx2, nh2 + ny2, 0).endVertex();
		bufferbuilder.vertex(nw2 + nx2, nh2 + ny2, 0).endVertex();
		bufferbuilder.vertex(nw2 + nx2, ny2, 0).endVertex();
		bufferbuilder.vertex(nx2, ny2, 0).endVertex();
		tesselator.end();
		RenderSystem.enableTexture();
	}

	protected void renderKey(int i, PoseStack matrix, BufferBuilder bufferbuilder, Tesselator tesselator) {
		if (keys.size() > 0) {
			float f = 0.5f;
			float nw = this.colWidth;
			float nh = this.colHeight;
			float nx = this.x;
			float ny = (this.y) + ((i - this.from) * this.colHeight);

			if (selected.contains(i)) {
				RenderSystem.disableTexture();
				RenderSystem.setShader(GameRenderer::getPositionShader);
				RenderSystem.setShaderColor(f, f, f, 1F);
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
				bufferbuilder.vertex(nx - 1, (nh + 2) + ny - 1, 0).endVertex();
				bufferbuilder.vertex((nw + 2) + nx - 1, (nh + 2) + ny - 1, 0).endVertex();
				bufferbuilder.vertex((nw + 2) + nx - 1, ny - 1, 0).endVertex();
				bufferbuilder.vertex(nx - 1, ny - 1, 0).endVertex();
				tesselator.end();

				RenderSystem.setShader(GameRenderer::getPositionShader);
				RenderSystem.setShaderColor(0F, 0F, 0F, 1F);
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
				bufferbuilder.vertex(nx, nh + ny, 0).endVertex();
				bufferbuilder.vertex(nw + nx, nh + ny, 0).endVertex();
				bufferbuilder.vertex(nw + nx, ny, 0).endVertex();
				bufferbuilder.vertex(nx, ny, 0).endVertex();
				tesselator.end();
				RenderSystem.enableTexture();
			}
			font.drawShadow(matrix, keys.get(i).key, nx + (30 - (font.width(keys.get(i).key) / 2)), ny + 4, 16777215);
		}
	}

	public void check(int mouseX, int mouseY) {
		if (this.cols < this.keys.size()) {
			if (mouseX > this.x + (this.colWidth) && mouseX < (this.x + (this.colWidth)) + this.scrollWidth
					&& mouseY > this.y && mouseY < this.y + (this.cols * this.colHeight)) {
				this.scrollY = mouseY - (this.scrollHeight / 2);
				this.wrapScrollY();
				float part1 = (((float) this.y) - ((float) this.scrollY));
				float part2 = (((float) (this.cols * this.colHeight) - (float) this.scrollHeight));
				this.offset = Mth.floor(Mth.abs(Mth.lerp(part1 / part2, 0, this.keys.size() - this.cols)));
				this.from = 0 + this.offset;
				if (this.cols > this.keys.size()) {
					this.to = this.keys.size();
				} else {
					this.to = this.cols + offset;
				}
			}
		}
	}

	public void tick() {

	}

	public void setKeys(Key[] keys) {
		this.keys = new ArrayList<Key>(Arrays.asList(keys));
		update();
		LogUtils.getLogger().info("Keys length: " + keys.length);
	}

	public void addKey(Key key) {
		if (key != null) {
			key.cols = this.cols;
			key.colWidth = this.colWidth;
			try {
				if (key != null) {
					LogUtils.getLogger().info("Key not null");
				}
				keys.add(key);
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
			update();
		} else {
			LogUtils.getLogger().info("Key == null");
		}
	}

	public void removeKey(Key key) {
		keys.remove(key);
		update();
	}

	public void removeKey(int index) {
		keys.remove(index);
		update();
	}

	public void update() {
		if(keys.isEmpty())return;
		try {
		this.from = 0;
		this.to = this.cols > this.keys.size() ? this.keys.size() : this.cols;
		this.hideItems = (this.keys.size() - this.cols) < 0 ? 0 : 
			this.keys.size() - this.cols;
		LogUtils.getLogger().info("hideItems: " + hideItems + " keys size: " + keys.size());
		LogUtils.getLogger().info(" div: " + (this.hideItems / this.keys.size()));
		this.scrollHeight = (int) Mth.lerp(
				1 - (((float) this.hideItems / this.keys.size()) == 0 ? 1 : 
					(float) this.hideItems / this.keys.size()),
				0, this.colHeight * this.cols);
		} catch(ArithmeticException e) {
			e.printStackTrace();
		}
	}

	public void onClick(int mouseX, int mouseY, boolean ctrlDown) {
		for (int i = this.from; i < this.to; i++) {
			int rcoly = (this.y) + ((i - this.from) * this.colHeight);
			if (mouseX > this.x && mouseX < (this.x + this.colWidth - this.scrollWidth)) {
				if (mouseY > rcoly && mouseY < rcoly + this.colHeight) {
					if (selected.contains(i)) {
						if (ctrlDown) {
							if (selected.contains(i)) {
								selected.remove(i);
							}
						} else {
							selected.clear();
						}
					} else {
						if (ctrlDown) {
							selected.add(i);
							onSelectKey.onSelectKey(getSelectedKey(), i);
						} else {
							selected.clear();
							selected.add(i);
							onSelectKey.onSelectKey(getSelectedKey(), i);
						}
					}
				}
			}
		}
	}

	public void onScroll(float delta) {
		if (this.cols < this.keys.size() && this.hideItems != 0) {
			this.scrollY += delta * 0.1f;
			this.wrapScrollY();
			float part1 = (((float) this.y) - ((float) this.scrollY));
			float part2 = (((float) (this.cols * this.colHeight) - (float) this.scrollHeight));
			this.offset = Mth.floor(Mth.abs(Mth.lerp(part1 / part2, 0, this.keys.size() - this.cols)));
			this.from = 0 + this.offset;
			if (this.cols > this.keys.size()) {
				this.to = this.keys.size();
			} else {
				this.to = this.cols + offset;
			}
		}
	}

	public void wrapScrollY() {
		if (this.scrollY <= this.y) {
			this.scrollY = this.y;
			LogUtils.getLogger().info("SAds");
		}
		if (this.scrollY + this.scrollHeight > (this.y + (this.cols * this.colHeight))) {
			this.scrollY = (this.y + (this.cols * this.colHeight)) - this.scrollHeight;
			LogUtils.getLogger().info("SAds2");
		}
		LogUtils.getLogger().info("x: " + this.x + " scrollY: " + this.scrollY + " scrollHeight: " + scrollHeight
				+ " su: " + (this.y + (this.cols * this.colHeight)));
	}

	public int getKeysSize() {
		return keys.size();
	}

	/*
	 * public int getSelected() { return selected; }
	 */

	public List<Integer> getSelectedIndexes() {
		return selected;
	}

	public void setSelectedIndexes(List<Integer> indexes) {
		this.selected = indexes;
	}

	public Key getSelectedKey() {
		if (keys.size() > 0 && !selected.isEmpty()) {
			try {
				return keys.get(this.selected.get(0));
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			return EMPTYKEY;
		} else {
			return EMPTYKEY;
		}
	}

	public Key[] getSelectedKeys() {
		LogUtils.getLogger().info("Selected size: " + selected.size());
		for(int i : selected) {
			LogUtils.getLogger().info("Selected: " + i);
		}
		if (keys.size() > 0 && !selected.isEmpty()) {
			Key[] newKeys = new Key[selected.size()];
			for (int i = 0; i < selected.size(); i++) {
				try {
				newKeys[i] = keys.get(selected.get(i));
				} catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				} catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			try {
				return newKeys;
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			return new Key[0];
		} else {
			return new Key[0];
		}
	}

	public List<Key> getKeys() {
		return keys;
	}

	public void cleanKeys() {
		keys.clear();
	}

	public static interface onSelectKey {
		public void onSelectKey(Key key, int index);
	}

	public static class Key {

		protected String key;
		protected Font font;
		protected int cols;
		protected int colWidth;

		public Key(Font font, String key) {
			this.font = font;
			this.key = key;
		}

		public void render(PoseStack matrix) {
			this.font.drawShadow(matrix, this.key, cols, colWidth, 16777215);
		}

		public String getKey() {
			return key;
		}

	}

}
