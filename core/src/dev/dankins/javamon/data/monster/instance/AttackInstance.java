package dev.dankins.javamon.data.monster.instance;

import com.badlogic.gdx.assets.AssetManager;

import dev.dankins.javamon.data.monster.attack.Attack;

public class AttackInstance {

	public final Attack attack;
	public int maxUsage;
	public int currentUsage;

	public AttackInstance(final Attack attack) {
		this.attack = attack;
		maxUsage = attack.uses;
		currentUsage = attack.uses;
	}

	public AttackInstance(final AssetManager assets, final AttackSerialized attack) {
		this.attack = assets.get(attack.attack, Attack.class);
		maxUsage = attack.maxUsage;
		currentUsage = attack.currentUsage;
	}

}
