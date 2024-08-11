package com.cprodhomme.mischievousskull.entity;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public abstract class MischievousSkullBlockEntityModel extends SkullBlockEntityModel {
  private final ModelPart root;
    protected final ModelPart head;

    public MischievousSkullBlockEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation, int textureWidth, int textureHeight) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation), ModelTransform.NONE);
        return TexturedModelData.of(modelData, textureWidth, textureHeight);
    }

    public ModelPart getHead() {
        return this.head;
    }

    public void setHeadRotation(float animationProgress, float yaw, float pitch) {
        this.head.yaw = yaw * 0.017453292F;
        this.head.pitch = pitch * 0.017453292F;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
