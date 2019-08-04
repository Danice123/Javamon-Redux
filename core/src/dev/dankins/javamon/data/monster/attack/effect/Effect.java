package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "effect")
public abstract class Effect {

	public abstract void use(MonsterInstance user, MonsterInstance target, Attack move);

}
