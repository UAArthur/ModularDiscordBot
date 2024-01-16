package net.hauntedstudio.mb.module;


import net.hauntedstudio.mb.utils.LoggerClass;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;

public class ModuleLoader {

    private final LoggerClass logger = new LoggerClass();

    public void loadModules() {
        logger.info("Loading modules...");
        File moduleDir = new File("modules");
        File[] moduleFiles = moduleDir.listFiles((dir, name) -> name.endsWith(".jar"));

        if (moduleFiles != null) {
            for (File moduleFile : moduleFiles) {
                logger.info("Loading module: " + moduleFile.getName());
                loadModule(moduleFile);
            }
        }
    }

    private void loadModule(File moduleFile) {
        try {
            logger.debug("Creating class loader for module: " + moduleFile.getName());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{moduleFile.toURI().toURL()});

            // Load module.yml from JAR
            try (InputStream input = classLoader.getResourceAsStream("module.yml")) {
                if (input != null) {
                    logger.debug("Loading module.yml for: " + moduleFile.getName());
                    Yaml yaml = new Yaml();
                    ModuleConfig config = yaml.loadAs(input, ModuleConfig.class);

                    logger.debug("Main class specified in module.yml: " + config.getMain());

                    Class<?> moduleClass = classLoader.loadClass(config.getMain());
                    Module module = (Module) moduleClass.getDeclaredConstructor().newInstance();

                    // Call onEnable method
                    Method onEnableMethod = moduleClass.getMethod("onEnable");
                    logger.info("Invoking onEnable for: " + moduleFile.getName());
                    onEnableMethod.invoke(module);

                    // You can store the loaded modules in a list for further management
                    logger.info("Module loaded successfully: " + moduleFile.getName());
                } else {
                    logger.warning("No module.yml found for: " + moduleFile.getName());
                }

            } catch (Exception e) {
                logger.severe("Error loading module: " + moduleFile.getName(), e);
            }
        } catch (Exception e) {
            logger.severe("Error creating class loader for module: " + moduleFile.getName(), e);
        }
    }
}
