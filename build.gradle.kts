plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "1.2.3"

prism {
    curseMaven()
    modrinthMaven()
    maven("Create", "https://maven.createmod.net")
    maven("Registrate", "https://maven.ithundxr.dev/mirror")
    maven("ForgeConfigAPIPort", "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
    maven("ValkirienSkies", "https://maven.valkyrienskies.org")

    metadata {
        modId = "create_armored_rails"
        name = "Create: Armored Rails"
        description = ""
        license = "MIT"
        author("Leclowndu93150")
    }

    version("1.20.1") {
        forge {
            loaderVersion = "47.4.16"
            loaderVersionRange = "[47,)"

            dependencies {
                modImplementation("com.simibubi.create:create-1.20.1:6.0.8-289:slim")
                modImplementation("net.createmod.ponder:Ponder-Forge-1.20.1:1.0.91")
                modCompileOnly("dev.engine-room.flywheel:flywheel-forge-api-1.20.1:1.0.5")
                modRuntimeOnly("dev.engine-room.flywheel:flywheel-forge-1.20.1:1.0.5")
                modImplementation("com.tterrag.registrate:Registrate:MC1.20-1.3.3")
                compileOnly("io.github.llamalad7:mixinextras-common:0.4.1")
                implementation("io.github.llamalad7:mixinextras-forge:0.4.1")
                modImplementation("curse.maven:yacl-667299:6336646")
                modImplementation("curse.maven:jade-324717:6855440")
                modImplementation("curse.maven:jei-238222:7391695")
                modImplementation("curse.maven:explosive-enhancement-reforged-1036246:5960819")
                modCompileOnly("curse.maven:valkyrien-skies-258371:7459669")
                compileOnly("org.valkyrienskies.core:api:1.1.0+70906bca16")
                compileOnly("org.valkyrienskies.core:util:1.1.0+70906bca16")
                modCompileOnly("curse.maven:create-interactive-925913:7478572")
            }
        }
    }

    version("1.21.1") {
        neoforge {
            loaderVersion = "21.1.219"

            dependencies {
                implementation("curse.maven:create-328085:7963363")
                implementation("net.createmod.ponder:ponder-neoforge:1.0.82+mc1.21.1")
                compileOnly("dev.engine-room.flywheel:flywheel-neoforge-api-1.21.1:1.0.6")
                runtimeOnly("dev.engine-room.flywheel:flywheel-neoforge-1.21.1:1.0.6")
                implementation("com.tterrag.registrate:Registrate:MC1.21-1.3.0+67")
                implementation("curse.maven:yacl-667299:7437845")
                implementation("curse.maven:jade-324717:7545219")
                implementation("curse.maven:jei-238222:7391682")
                implementation("curse.maven:explosive-enhancement-reforged-1036246:6520579")
            }
        }
    }

    publishing {
        changelog = "Fixes broken recipes"
        type = STABLE

        curseforge {
            accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
            projectId = "1502380"
        }

        modrinth {
            accessToken = providers.environmentVariable("MODRINTH_TOKEN")
            projectId = ""
        }

        dependencies {
            requires("create")
            requires("yacl")
            optional("jade")
            optional("jei")
        }
    }
}
