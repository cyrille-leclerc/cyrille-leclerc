package cyrille.util.prefs;

import java.util.prefs.Preferences;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class TestPreferences {
    public static void main(String[] args) {
        try {
            TestPreferences testPreferences = new TestPreferences();
            testPreferences.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() throws Exception {
        Preferences preferences = Preferences.userRoot();
        // preferences.put("key", "value");
        // preferences.put("key.subKey", "subValue");
        Preferences subPreferences = preferences.node("subNode/subSubNode");
        subPreferences.put("subSubKey", "value");
        subPreferences.put("", "MainValue");
        preferences.flush();
        preferences.exportSubtree(System.out);

    }
}
