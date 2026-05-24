package com.codex.guardillagersronintegration.mixin;

import com.codex.guardillagersronintegration.GuardillagersRonIntegrationValues;
import com.min01.guardillagers.entity.GuardIllager;
import com.solegendary.reignofnether.ability.Abilities;
import com.solegendary.reignofnether.ability.Ability;
import com.solegendary.reignofnether.faction.Faction;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.unit.Checkpoint;
import com.solegendary.reignofnether.unit.goals.*;
import com.solegendary.reignofnether.unit.interfaces.Unit;
import com.solegendary.reignofnether.unit.interfaces.UnitMoveGoal;
import com.solegendary.reignofnether.unit.interfaces.UnitTargetGoal;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(GuardIllager.class)
public abstract class GuardIllagerEntityUnitMixin implements Unit {
    
    @Unique
    private static final EntityDataAccessor<String> RON_OWNER = SynchedEntityData.defineId(GuardIllager.class, EntityDataSerializers.STRING);
    
    @Unique
    private static final EntityDataAccessor<Integer> RON_SCENARIO_ROLE = SynchedEntityData.defineId(GuardIllager.class, EntityDataSerializers.INT);
    
    @Unique
    private Object2ObjectArrayMap<Ability, Float> ronCooldowns = new Object2ObjectArrayMap<>();
    
    @Unique
    private Object2ObjectArrayMap<Ability, Integer> ronCharges = new Object2ObjectArrayMap<>();
    
    @Unique
    private ArrayList<Checkpoint> ronCheckpoints = new ArrayList<>();
    
    @Unique
    private Abilities ronAbilities = new Abilities();
    
    @Unique
    private List<ItemStack> ronItems = new ArrayList<>();
    
    @Unique
    private Set<BlockPos> ronHighlightBps = new HashSet<>();
    
    @Unique
    private Ability ronAutocast = null;
    
    @Unique
    private int ronEatingTicksLeft = 0;
    
    @Unique
    private BlockPos ronAnchor = null;
    
    @Unique
    private GarrisonGoal ronGarrisonGoal = new GarrisonGoal();
    
    @Unique
    private UsePortalGoal ronUsePortalGoal = new UsePortalGoal();
    
    @Unique
    private MoveToTargetBlockGoal ronMoveGoal = new MoveToTargetBlockGoal();
    
    @Unique
    private SelectedTargetGoal<LivingEntity> ronTargetGoal = new SelectedTargetGoal<>();
    
    @Unique
    private ReturnResourcesGoal ronReturnResourcesGoal = new ReturnResourcesGoal();
    
    @Unique
    private LivingEntity ronFollowTarget = null;
    
    @Unique
    private boolean ronHoldPosition = false;
    
    @Override
    public void createCooldownMap() {
        this.ronCooldowns = new Object2ObjectArrayMap<>();
    }
    
    @Override
    public void guardillagersIntegration$defineSynchedData(CallbackInfo ci) {
        ((SynchedEntityData.Accessor) this).define(RON_OWNER, "");
        ((SynchedEntityData.Accessor) this).define(RON_SCENARIO_ROLE, 0);
    }
    
    @Override
    public void guardillagersIntegration$saveUnitData(net.minecraft.nbt.CompoundTag tag, CallbackInfo ci) {
        // Save unit data to NBT
        this.addUnitSaveData(tag);
    }
    
    @Override
    public void guardillagersIntegration$loadUnitData(net.minecraft.nbt.CompoundTag tag, CallbackInfo ci) {
        // Load unit data from NBT
        this.readUnitSaveData(tag);
    }
    
    @Override
    public void guardillagersIntegration$registerRtsGoals(CallbackInfo ci) {
        GoalSelector goalSelector = ((net.minecraft.world.entity.Mob) (Object) this).goalSelector;
        GoalSelector targetSelector = ((net.minecraft.world.entity.Mob) (Object) this).targetSelector;
        
        // Register RTS goals
        goalSelector.addGoal(8, (Goal) ronMoveGoal);
        targetSelector.addGoal(3, (Goal) ronTargetGoal);
    }
    
    @Override
    public void guardillagersIntegration$tickAsUnit(CallbackInfo ci) {
        // Tick as unit
        this.updateAbilityButtons();
    }
    
    @Override
    public Object2ObjectArrayMap<Ability, Float> getCooldowns() {
        return ronCooldowns;
    }
    
    @Override
    public boolean hasAutocast(Ability ability) {
        return ronAutocast == ability;
    }
    
    @Override
    public void setAutocast(Ability ability) {
        this.ronAutocast = ability;
    }
    
    @Override
    public Object2ObjectArrayMap<Ability, Integer> getCharges() {
        return ronCharges;
    }
    
    @Override
    public void setEatingTicksLeft(int amount) {
        this.ronEatingTicksLeft = amount;
    }
    
    @Override
    public int getEatingTicksLeft() {
        return ronEatingTicksLeft;
    }
    
    @Override
    public void setAnchor(BlockPos pos) {
        this.ronAnchor = pos;
    }
    
    @Override
    public BlockPos getAnchor() {
        return ronAnchor;
    }
    
    @Override
    public ArrayList<Checkpoint> getCheckpoints() {
        return ronCheckpoints;
    }
    
    @Override
    public GarrisonGoal getGarrisonGoal() {
        return ronGarrisonGoal;
    }
    
    @Override
    public boolean canGarrison() {
        return true;
    }
    
    @Override
    public UsePortalGoal getUsePortalGoal() {
        return ronUsePortalGoal;
    }
    
    @Override
    public boolean canUsePortal() {
        return false;
    }
    
    @Override
    public Faction getFaction() {
        return Faction.VILLAGERS;
    }
    
    @Override
    public Abilities getAbilities() {
        return ronAbilities;
    }
    
    @Override
    public List<ItemStack> getItems() {
        return ronItems;
    }
    
    @Override
    public UnitMoveGoal getMoveGoal() {
        return (UnitMoveGoal) ronMoveGoal;
    }
    
    @Override
    public UnitTargetGoal<LivingEntity> getTargetGoal() {
        return (UnitTargetGoal<LivingEntity>) ronTargetGoal;
    }
    
    @Override
    public ReturnResourcesGoal getReturnResourcesGoal() {
        return ronReturnResourcesGoal;
    }
    
    @Override
    public float getMovementSpeed() {
        return GuardillagersRonIntegrationValues.MOVEMENT_SPEED;
    }
    
    @Override
    public float getUnitMaxHealth() {
        return GuardillagersRonIntegrationValues.MAX_HEALTH;
    }
    
    @Override
    public ResourceCost getCost() {
        return GuardillagersRonIntegrationValues.RESOURCE_COST;
    }
    
    @Override
    public int getMaxResources() {
        return 0;
    }
    
    @Override
    public LivingEntity getFollowTarget() {
        return ronFollowTarget;
    }
    
    @Override
    public void setHoldPosition(boolean holdPosition) {
        this.ronHoldPosition = holdPosition;
    }
    
    @Override
    public boolean getHoldPosition() {
        return ronHoldPosition;
    }
    
    @Override
    public String getOwnerName() {
        return ((SynchedEntityData.Accessor) this).get(RON_OWNER);
    }
    
    @Override
    public void setOwnerName(String name) {
        ((SynchedEntityData.Accessor) this).set(RON_OWNER, name);
    }
    
    @Override
    public int getScenarioRoleIndex() {
        return ((SynchedEntityData.Accessor) this).get(RON_SCENARIO_ROLE);
    }
    
    @Override
    public void setScenarioRoleIndex(int index) {
        ((SynchedEntityData.Accessor) this).set(RON_SCENARIO_ROLE, index);
    }
    
    @Override
    public void setFollowTarget(LivingEntity target) {
        this.ronFollowTarget = target;
    }
}