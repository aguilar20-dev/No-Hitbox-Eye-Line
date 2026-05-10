package dev.aguilar20dev.nohitboxeyeline.mixin;

import net.minecraft.gizmos.LineGizmo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LineGizmo.class)
public abstract class LineGizmoMixin {
    @Unique
    private static final int LOOK_DIRECTION_LINE_COLOR = 0xFF0000FF;

    @Shadow
    public abstract int color();

    @Inject(method = "emit", at = @At("HEAD"), cancellable = true)
    private void nohitboxeyeline$hideBlueLookDirectionLine(CallbackInfo ci) {
        if (this.color() == LOOK_DIRECTION_LINE_COLOR) {
            ci.cancel();
        }
    }
}
