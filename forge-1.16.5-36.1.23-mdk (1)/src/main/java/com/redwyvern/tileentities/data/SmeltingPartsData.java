package com.redwyvern.tileentities.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class SmeltingPartsData implements IIntArray {

	private int timer, xb, timercooling, yb, dif, steptimer, cooling;

	public void putIntoNBT(CompoundNBT nbtTagCompound) {
		nbtTagCompound.putInt("timer", timer);
		nbtTagCompound.putInt("xb", xb);
		nbtTagCompound.putInt("dif", dif);
		nbtTagCompound.putInt("timercooling", timercooling);
		nbtTagCompound.putInt("yb", yb);
		nbtTagCompound.putInt("steptimer", steptimer);
		nbtTagCompound.putInt("cooling", cooling);
	}

	public void readFromNBT(CompoundNBT nbtTagCompound) {
		timer = nbtTagCompound.getInt("timer");
		xb = nbtTagCompound.getInt("xb");
		dif = nbtTagCompound.getInt("dif");
		timercooling = nbtTagCompound.getInt("timercooling");
		yb = nbtTagCompound.getInt("yb");
		steptimer = nbtTagCompound.getInt("steptimer");
		cooling = nbtTagCompound.getInt("cooling");
	}

	@Override
	public int get(int index) {
		validateIndex(index);
		switch (index) {
		case 0:
			return timer;
		case 1:
			return xb;
		case 2:
			return dif;
		case 3:
			return timercooling;
		case 4:
			return yb;
		case 5:
			return steptimer;
		case 6:
			return cooling;
		}
		return 0;
	}

	@Override
	public void set(int index, int value) {
		switch (index) {
		case 0:
			timer = value;
			break;
		case 1:
			xb = value;
			break;
		case 2:
			dif = value;
			break;
		case 3:
			timercooling = value;
			break;
		case 4:
			yb = value;
		case 5:
			steptimer = value;
			break;
		case 6:
			cooling = value;
			break;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	private void validateIndex(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= getCount()) {
			throw new IndexOutOfBoundsException("Index out of bounds:" + index);
		}
	}
}
