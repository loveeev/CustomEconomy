package dev.loveeev.untitled10.layer;


import dev.loveeev.untitled10.Main;
import dev.loveeev.untitled10.util.ChatUtility;
import dev.loveeev.untitled10.util.semantics.Layer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author Zimoxy DEV: loveeev
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilityLayer extends Layer {

    ChatUtility chatUtility;
    public UtilityLayer(Main plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        super.enable();
        chatUtility = new ChatUtility("i huilan");
        registerCommands();
    }

    private void registerCommands() {

    }
}
