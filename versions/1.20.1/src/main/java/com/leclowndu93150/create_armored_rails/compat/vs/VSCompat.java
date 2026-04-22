package com.leclowndu93150.create_armored_rails.compat.vs;

import net.minecraftforge.fml.ModList;

public class VSCompat {
    private static Boolean active;

    public static boolean isActive() {
        if (active == null) {
            active = ModList.get().isLoaded("valkyrienskies")
                    && ModList.get().isLoaded("create_interactive");
        }
        return active;
    }
}
