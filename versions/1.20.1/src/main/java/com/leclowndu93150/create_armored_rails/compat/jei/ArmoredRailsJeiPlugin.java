package com.leclowndu93150.create_armored_rails.compat.jei;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.client.HullScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@JeiPlugin
public class ArmoredRailsJeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CreateArmoredRails.MODID, "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(HullScreen.class, new HullScreenGuiHandler());
    }

    private static class HullScreenGuiHandler implements IGuiContainerHandler<HullScreen> {
        @Override
        public List<Rect2i> getGuiExtraAreas(HullScreen screen) {
            return List.of(new Rect2i(
                    screen.getGuiLeft() + 178 + 3,
                    screen.getGuiTop(),
                    76,
                    56
            ));
        }
    }
}
