package dev.aguilar20dev.nohitboxeyeline.mixin;

import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.gizmos.GizmoProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityHitboxDebugRenderer.class)
public abstract class EntityHitboxDebugRendererMixin {
    @Unique
    private static final int LOOK_DIRECTION_ARROW_COLOR = 0xFF0000FF;

    private static final GizmoProperties NO_OP_VISIBILITY = new GizmoProperties() {
        @Override
        public GizmoProperties setAlwaysOnTop() {
            return this;
        }

        @Override
        public GizmoProperties persistForMillis(int millis) {
            return this;
        }

        @Override
        public GizmoProperties fadeOut() {
            return this;
        }
    };

    @Redirect(
            method = "showHitboxes(Lnet/minecraft/world/entity/Entity;FZ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/gizmos/Gizmos;arrow(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;I)Lnet/minecraft/gizmos/GizmoProperties;"
            )
    )
    private GizmoProperties nohitboxeyeline$skipLookDirectionArrow(Vec3 from, Vec3 to, int color) {
        if (color == LOOK_DIRECTION_ARROW_COLOR) {
            return NO_OP_VISIBILITY;
        }

        return Gizmos.arrow(from, to, color);
    }

    @Redirect(
            method = "showHitboxes(Lnet/minecraft/world/entity/Entity;FZ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"
            )
    )
    private Vec3 nohitboxeyeline$zeroLookDirection(Entity entity, float tickProgress) {
        return Vec3.ZERO;
    }
}
