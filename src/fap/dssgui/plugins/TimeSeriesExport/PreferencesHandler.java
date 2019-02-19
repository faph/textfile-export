/*
 * PreferencesHandler.java
 *
 * Created on 29 November 2007, 12:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package fap.dssgui.plugins.TimeSeriesExport;

import java.util.prefs.Preferences;

public class PreferencesHandler {

   public static void setPreference(String keyName, String keyValue) {
      Preferences prefs = Preferences.userNodeForPackage(fap.dssgui.plugins.TimeSeriesExport.ExportDialog.class);

      final String PREF_NAME = keyName;
      String newValue = keyValue;

      prefs.put(PREF_NAME, newValue);
   }

   public static void setPreference(String keyName, int keyValue) {
      Preferences prefs = Preferences.userNodeForPackage(fap.dssgui.plugins.TimeSeriesExport.ExportDialog.class);

      final String PREF_NAME = keyName;
      int newValue = keyValue;

      prefs.putInt(PREF_NAME, newValue);
   }

   public static String getPreference(String keyName) {
      Preferences prefs = Preferences.userNodeForPackage(fap.dssgui.plugins.TimeSeriesExport.ExportDialog.class);

      final String PREF_NAME = keyName;
      String defaultValue = "";

      String propertyValue = prefs.get(PREF_NAME, defaultValue);
      return propertyValue;
   }
    
   public static int getPreferenceInt(String keyName) {
      Preferences prefs = Preferences.userNodeForPackage(fap.dssgui.plugins.TimeSeriesExport.ExportDialog.class);

      final String PREF_NAME = keyName;
      int defaultValue = 0;

      int propertyValue = prefs.getInt(PREF_NAME, defaultValue);
      return propertyValue;
   }
}

