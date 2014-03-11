package gaul.psychoanalytix;

import org.aeonbits.owner.Config;

/**
 *
 * @author ashish
 */
@Config.Sources({"file:${psychoanalytix.config}",
    "file:${user.home}/.psychoanalytix.config"
})
public interface AppConfig extends Config {
    @DefaultValue("http://localhost:9002")
    String cacofonixUrl();

    @DefaultValue("1800000")
    int frequency();
}
