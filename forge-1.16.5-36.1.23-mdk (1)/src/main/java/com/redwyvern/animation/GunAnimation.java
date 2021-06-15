package com.redwyvern.animation;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.ConsumeStackMessage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;

public abstract class GunAnimation {
	public int animsize, bulletsleft;
	public float MAXRECOILTICKS;

	public GunAnimation() {

	}

	public void animGun(MatrixStack stack, int stage, float ticks) {
		if (ticks == 0)
			return;
	};

	public void animLeftArm(MatrixStack stack, int stage, float ticks) {
		if (ticks == 0)
			return;
	};

	public void animRightArm(MatrixStack stack, int stage, float ticks) {
		if (ticks == 0)
			return;
	};

	public void animHammer(MatrixStack stack, int stage, float ticks) {
		if (ticks == 0)
			return;
	};

	public void animScope(MatrixStack stack, int stage, float ticks) {
		if (ticks == 0)
			return;
	};

	public void animShootGun(MatrixStack stack, int shootticks) {
		if (shootticks == 0)
			return;
	};

	public void animShootLeftArm(MatrixStack stack, int shootticks) {
		if (shootticks == 0)
			return;
	};

	public void animShootRightArm(MatrixStack stack, int shootticks) {
		if (shootticks == 0)
			return;
	};

	public void animShootHammer(MatrixStack stack, int shootticks) {
		if (shootticks == 0)
			return;
	};

	public void animRecoilHammer(MatrixStack stack, float recoilticks) {
		if (recoilticks == MAXRECOILTICKS)
			return;
		float mitad = MAXRECOILTICKS / 2.0f;

		if (recoilticks > mitad) {
			float stc = (recoilticks - mitad) / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 2, 0), 0, 0, true));

		} else {
			float stc = recoilticks / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 0, 2), 0, 0, true));

		}
	};

	public void animRecoilLeftArm(MatrixStack stack, float recoilticks) {
		if (recoilticks == 0)
			return;
		float mitad = MAXRECOILTICKS / 2.0f;

		if (recoilticks > mitad) {
			float stc = (recoilticks - mitad) / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 2, 0), 0, 0, true));

		} else {
			float stc = recoilticks / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 0, 2), 0, 0, true));

		}
	};

	public void animRecoilRightArm(MatrixStack stack, float recoilticks) {
		if (recoilticks == 0)
			return;
		float mitad = MAXRECOILTICKS / 2.0f;

		if (recoilticks > mitad) {
			float stc = (recoilticks - mitad) / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 2, 0), 0, 0, true));

		} else {
			float stc = recoilticks / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 0, 2), 0, 0, true));
		}
	};

	public void animRecoilScope(MatrixStack stack, float recoilticks) {
		if (recoilticks == 0)
			return;
		float mitad = MAXRECOILTICKS / 2.0f;

		if (recoilticks > mitad) {
			float stc = (recoilticks - mitad) / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 2, 0), 0, 0, true));

		} else {
			float stc = recoilticks / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 0, 2), 0, 0, true));
		}
	};

	public void animRecoilGun(MatrixStack stack, float recoilticks) {
		if (recoilticks == 0)
			return;

		float mitad = MAXRECOILTICKS / 2.0f;

		if (recoilticks > mitad) {
			float stc = (recoilticks - mitad) / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 4, 0), 0, 0, true));

		} else {
			float stc = recoilticks / mitad;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 0, 4), 0, 0, true));
		}
	};

	public void animLeftHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		if (aimprogress == 0)
			return;
	};

	public void animRightHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		if (aimprogress == 0)
			return;
	};

	public void animGunAim(MatrixStack stack, float aimprogress, boolean scope) {
		if (aimprogress == 0)
			return;
	};

	public void animHammerAim(MatrixStack stack, float aimprogress, boolean scope) {
		if (aimprogress == 0)
			return;
	};

	public void animScopeAim(MatrixStack stack, float aimprogress, boolean scope) {
		if (aimprogress == 0)
			return;
	};

	public void animLeftHandSprint(MatrixStack stack, float sprfloatprogress) {
		if (sprfloatprogress == 0)
			return;
	};

	public void animRightHandSprint(MatrixStack stack, float sprfloatprogress) {
		if (sprfloatprogress == 0)
			return;
	};

	public void animGunSprint(MatrixStack stack, float sprfloatprogress) {
		if (sprfloatprogress == 0)
			return;
	};

	public void animHammerSprint(MatrixStack stack, float sprfloatprogress) {
		if (sprfloatprogress == 0)
			return;
	};

	public void animScopeSprint(MatrixStack stack, float sprfloatprogress) {
		if (sprfloatprogress == 0)
			return;
	};

	public void animAditional(MatrixStack stack, int stage, float ticks) {
		if (ticks == 0)
			return;
	};

	protected void consumeifCreative(PlayerEntity player, int index) {
		if (!player.isCreative()) {
			player.inventory.removeItem(index, 1);
			OldGuns.channel.sendToServer(new ConsumeStackMessage(index, 1));
		}
	}

	public abstract int getReloadTimeForStage(int stage);

	public abstract void specialReload(int stage);

	public abstract int getAnimSize();

	public abstract void setBullets(int bullets, int bulletsleftn, int maxbullets);

	public abstract boolean hasCustomReload();

}

/*
 * @Override public void animGun(MatrixStack stack, int stage, float ticks) {
 * super.animGun(stack, stage, ticks); }
 * 
 * @Override public void animHammer(MatrixStack stack, int stage, float ticks) {
 * super.animHammer(stack, stage, ticks); }
 * 
 * @Override public void animLeftArm(MatrixStack stack, int stage, float ticks)
 * { super.animLeftArm(stack, stage, ticks); }
 * 
 * @Override public void animRightArm(MatrixStack stack, int stage, float ticks)
 * { super.animRightArm(stack, stage, ticks); }
 * 
 * @Override public void animGunAim(MatrixStack stack, float aimprogress) {
 * super.animGunAim(stack, aimprogress); }
 * 
 * @Override public void animLeftHandAim(MatrixStack stack, float aimprogress) {
 * super.animLeftHandAim(stack, aimprogress); }
 * 
 * @Override public void animRightHandAim(MatrixStack stack, float aimprogress)
 * { super.animRightHandAim(stack, aimprogress); }
 * 
 * @Override public void animHammerAim(MatrixStack stack, float aimprogress) {
 * super.animHammerAim(stack, aimprogress); }
 * 
 * @Override public void animGunSprint(MatrixStack stack, float
 * sprfloatprogress) { super.animGunSprint(stack, sprfloatprogress); }
 * 
 * @Override public void animLeftHandSprint(MatrixStack stack, float
 * sprfloatprogress) { super.animLeftHandSprint(stack, sprfloatprogress); }
 * 
 * @Override public void animRightHandSprint(MatrixStack stack, float
 * sprfloatprogress) { super.animRightHandSprint(stack, sprfloatprogress); }
 * 
 * @Override public void animHammerSprint(MatrixStack stack, float
 * sprfloatprogress) { super.animHammerSprint(stack, sprfloatprogress); }
 */
