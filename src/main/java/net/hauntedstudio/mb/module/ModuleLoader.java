package net.hauntedstudio.mb.module;

import net.hauntedstudio.mb.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleLoader {
    private Main main;
    public ModuleLoader(Main main) {
        this.main = main;
    }


    public void loadModules() {
        this.main.logger.info("Loading modules...");
        File moduleDir = new File("modules");
        File[] moduleFiles = moduleDir.listFiles((dir, name) -> name.endsWith(".jar"));

        if (moduleFiles != null) {
            for (File moduleFile : moduleFiles) {
                this.main.logger.info("Loading module: " + moduleFile.getName());
                loadModule(moduleFile);
            }
        }
        this.main.logger.info("Modules loaded!");

        this.main.dcBotMain.getCommandLoader().loadSlashCommandsForAllBots();
    }


    private void loadModule(File moduleFile) {
        try {
            this.main.logger.debug("Creating class loader for module: " + moduleFile.getName());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{moduleFile.toURI().toURL()});

            // Load module.yml from JAR
            try (InputStream input = classLoader.getResourceAsStream("module.yml")) {
                if (input != null) {
                    this.main.logger.debug("Loading module.yml for: " + moduleFile.getName());
                    Yaml yaml = new Yaml();
                    ModuleConfig config = yaml.loadAs(input, ModuleConfig.class);

                    this.main.logger.debug("Main class specified in module.yml: " + config.getMain());

                    Class<?> moduleClass = classLoader.loadClass(config.getMain());
                    Module module = (Module) moduleClass.getDeclaredConstructor().newInstance();

                    // Call onEnable method
                    Method onEnableMethod = moduleClass.getMethod("onEnable");
                    this.main.logger.debug("Invoking onEnable for: " + moduleFile.getName());
                    onEnableMethod.invoke(module);

                    // You can store the loaded modules in a list for further management
                    this.main.logger.info("Module loaded successfully: " + moduleFile.getName());
                } else {
                    this.main.logger.warning("No module.yml found for: " + moduleFile.getName());
                }

            } catch (Exception e) {
                this.main.logger.severe("Error loading module: " + moduleFile.getName(), e.getMessage());
            }
        } catch (Exception e) {
            this.main.logger.severe("Error creating class loader for module: " + moduleFile.getName(), e.getMessage());
        }
    }

}
