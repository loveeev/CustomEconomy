package dev.loveeev.untitled10.config;

import com.google.common.base.CaseFormat;
import org.mineacademy.fo.annotation.AutoStaticConfig;
import org.mineacademy.fo.settings.SimpleSettings;

@AutoStaticConfig(format = CaseFormat.LOWER_UNDERSCORE)
public class config extends SimpleSettings {

    public static String DBNAME;
    public static String DBHOST;
    public static String DBUSER;
    public static String DBPASSWORD;
    @Override
    protected String getSettingsFileName() {
        return "config.yml";
    }
}
