package dev.aguilar20dev.nohitboxeyeline.mixin;

import net.minecraft.client.render.debug.EntityHitboxDebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.debug.gizmo.GizmoDrawing;
import net.minecraft.world.debug.gizmo.VisibilityConfigurable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityHitboxDebugRenderer.class)
public abstract class EntityHitboxDebugRendererMixin {
    private static final int LOOK_DIRECTION_ARROW_COLOR = 0xFF0000FF;

    private static final VisibilityConfigurable NO_OP_VISIBILITY = new VisibilityConfigurable() {
        @Override
        public VisibilityConfigurable ignoreOcclusion() {
            return this;
        }

        @Override
        public VisibilityConfigurable withLifespan(int lifespan) {
            return this;
        }

        @Override
        public VisibilityConfigurable fadeOut() {
            return this;
        }
    };

    @Redirect(
            method = "drawHitbox",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/debug/gizmo/GizmoDrawing;arrow(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;I)Lnet/minecraft/world/debug/gizmo/VisibilityConfigurable;"
            )
    )
    private VisibilityConfigurable nohitboxeyeline$skipLookDirectionArrow(Vec3d from, Vec3d to, int color) {
        if (color == LOOK_DIRECTION_ARROW_COLOR) {
            return NO_OP_VISIBILITY;
        }

        return GizmoDrawing.arrow(from, to, color);
    }

    @Redirect(
            method = "drawHitbox",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;"
            )
    )
    private Vec3d nohitboxeyeline$zeroLookDirection(Entity entity, float tickProgress) {
        return Vec3d.ZERO;
    }
}
