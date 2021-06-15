package com.redwyvern.tileentities.data;

import net.minecraft.util.IIntArray;

public class MoldingPartsData implements IIntArray {

	private int metal, amountToRemove;

	@Override
	public int get(int index) {
		switch (index) {
		case 0:
			return metal;
		case 1:
			return amountToRemove;
		}
		return 0;
	}

	@Override
	public void set(int index, int value) {
		switch (index) {
		case 0:
			metal = value;
			break;
		case 1:
			amountToRemove = value;
			break;
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
