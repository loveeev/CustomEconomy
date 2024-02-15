package dev.loveeev.untitled10.layer;


import dev.loveeev.untitled10.Main;
import dev.loveeev.untitled10.exception.ModuleStateException;
import dev.loveeev.untitled10.module.Service;
import dev.loveeev.untitled10.util.semantics.Layer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;

/**
 * @author Zimoxy DEV: loveeev
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
@Getter
public class SpigotServiceLayer extends Layer {

    HashMap<Class<? extends Service>, Service> loadedServices = new HashMap<>();

    public SpigotServiceLayer(Main plugin) {
        super(plugin);
    }


    @SneakyThrows(ModuleStateException.class)
    public void load(Service service) {
        if (loadedServices.containsKey(service.getClass()))
            throw new ModuleStateException("Service is already loaded to loader");
        loadedServices.put(service.getClass(), service);
        loadedServices.get(service.getClass()).enable();
    }

    @SneakyThrows(ModuleStateException.class)
    public <T extends Service> T get(Class<T> serviceClass) {
        if (!loadedServices.containsKey(serviceClass))
            throw new ModuleStateException("Service is not loaded to loader");
        return serviceClass.cast(loadedServices.get(serviceClass));
    }

    @Override
    public void disable() {
        super.disable();
        loadedServices.values().forEach(Service::disable);
    }
}
