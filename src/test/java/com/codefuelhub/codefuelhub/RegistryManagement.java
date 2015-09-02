package com.codefuelhub.codefuelhub;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.prefs.Preferences;


	public class RegistryManagement {
	
		public static final int HKEY_CURRENT_USER = 0x80000001;
		public static final int HKEY_LOCAL_MACHINE = 0x80000002;
		public static final int REG_SUCCESS = 0;
		public static final int REG_NOTFOUND = 2;
		public static final int REG_ACCESSDENIED = 5;

		private static final int KEY_ALL_ACCESS = 0xf003f;
		private static final int KEY_READ = 0x20019;
		private static Preferences userRoot = Preferences.userRoot();
		private static Preferences systemRoot = Preferences.systemRoot();
		private static Class<? extends Preferences> userClass = userRoot.getClass();
		private static Method regOpenKey = null;
		private static Method regCloseKey = null;
		private static Method regQueryValueEx = null;
		private static Method regEnumValue = null;
		private static Method regQueryInfoKey = null;
		private static Method regEnumKeyEx = null;
		private static Method regCreateKeyEx = null;
		private static Method regSetValueEx = null;
		private static Method regDeleteKey = null;
		private static Method regDeleteValue = null;

		public static final int KEY_WOW64_32KEY = 0x0200;
		public static final int KEY_WOW64_64KEY = 0x0100;
		
		private static final String BINARY_KEY_IDENT = "hex:";

		  /**
		   * Every dword entry starts with this, also used for import
		   */
		  private static final String DWORD_KEY_IDENT = "dword:";

		  /**
		   * Every multi string entry starts with this, also used for import
		   */
		  private static final String MULTI_KEY_IDENT = "hex(7):";
		  /**
		   * Every expand string entry starts with this, also used for import
		   */
		  private static final String EXPAND_KEY_IDENT = "hex(2):";

		static {
			try {
				regOpenKey = userClass.getDeclaredMethod("WindowsRegOpenKey", new Class[] { int.class, byte[].class, int.class });
				regOpenKey.setAccessible(true);
				regCloseKey = userClass.getDeclaredMethod("WindowsRegCloseKey", new Class[] { int.class });
				regCloseKey.setAccessible(true);
				regQueryValueEx = userClass.getDeclaredMethod("WindowsRegQueryValueEx", new Class[] { int.class, byte[].class });
				regQueryValueEx.setAccessible(true);
				regEnumValue = userClass.getDeclaredMethod("WindowsRegEnumValue", new Class[] { int.class, int.class, int.class });
				regEnumValue.setAccessible(true);
				regQueryInfoKey = userClass.getDeclaredMethod("WindowsRegQueryInfoKey1", new Class[] { int.class });
				regQueryInfoKey.setAccessible(true);
				regEnumKeyEx = userClass.getDeclaredMethod("WindowsRegEnumKeyEx", new Class[] { int.class, int.class, int.class });
				regEnumKeyEx.setAccessible(true);
				regCreateKeyEx = userClass.getDeclaredMethod("WindowsRegCreateKeyEx", new Class[] { int.class, byte[].class });
				regCreateKeyEx.setAccessible(true);
				regSetValueEx = userClass.getDeclaredMethod("WindowsRegSetValueEx", new Class[] { int.class, byte[].class, byte[].class });
				regSetValueEx.setAccessible(true);
				regDeleteValue = userClass.getDeclaredMethod("WindowsRegDeleteValue", new Class[] { int.class, byte[].class });
				regDeleteValue.setAccessible(true);
				regDeleteKey = userClass.getDeclaredMethod("WindowsRegDeleteKey", new Class[] { int.class, byte[].class });
				regDeleteKey.setAccessible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private RegistryManagement() {
		}

		public static void createKey(int hkey, String key) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			int[] ret;
			if (hkey == HKEY_LOCAL_MACHINE) {
				ret = createKey(systemRoot, hkey, key);
				regCloseKey.invoke(systemRoot, new Object[] { new Integer(ret[0]) });
			} else if (hkey == HKEY_CURRENT_USER) {
				ret = createKey(userRoot, hkey, key);
				regCloseKey.invoke(userRoot, new Object[] { new Integer(ret[0]) });
			} else {
				throw new IllegalArgumentException("hkey=" + hkey);
			}
			if (ret[1] != REG_SUCCESS) {
				throw new IllegalArgumentException("rc=" + ret[1] + "  key=" + key);
			}
		}
		
		public static void writeDwordValue(String path, String valueName, String value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			saveAnyValue("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_BFCACHE", valueName, DWORD_KEY_IDENT, "0");
				
		}
		
		private static int[] createKey(Preferences root, int hkey, String key) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			return (int[]) regCreateKeyEx.invoke(root, new Object[] { new Integer(hkey), toCstr(key) });
		}
		
		private static byte[] toCstr(String str) {
			byte[] result = new byte[str.length() + 1];

			for (int i = 0; i < str.length(); i++) {
				result[i] = (byte) str.charAt(i);
			}
			result[str.length()] = 0;
			return result;
		}
		
		private static void writeStringValue(Preferences root, int hkey, String key, String valueName, String value, int wow64) throws IllegalArgumentException, IllegalAccessException,
		InvocationTargetException {
				int[] handles = (int[]) regOpenKey.invoke(root, new Object[] { new Integer(hkey), toCstr(key), new Integer(KEY_ALL_ACCESS | wow64) });

				regSetValueEx.invoke(root, new Object[] { new Integer(handles[0]), toCstr(valueName), toCstr(value) });
				regCloseKey.invoke(root, new Object[] { new Integer(handles[0]) });
		}
		
		public static void saveAnyValue(String path, String valueName, String type, String data) {
			 try {
		        if(type.equals(BINARY_KEY_IDENT))
		          type = "REG_BINARY";
		        else if(type.equals(DWORD_KEY_IDENT))
		          type = "REG_DWORD";
		        else if(type.equals(MULTI_KEY_IDENT))
		          type = "REG_MULTI_SZ";
		        else if(type.equals(EXPAND_KEY_IDENT))
		          type = "REG_EXPAND_SZ";
		        
				Runtime.getRuntime().exec("reg add \"" + path + "\" /v \"" + valueName + "\" /t " + type + " /d \"" + data + "\" /f");
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		      
		}
		
}
