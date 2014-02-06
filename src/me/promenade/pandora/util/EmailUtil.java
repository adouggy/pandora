package me.promenade.pandora.util;


public enum EmailUtil {
	INSTANCE;

	// private final String regExpn =
	// "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
	// + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	// + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
	// + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	// + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
	// + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
	//
	// private Pattern pattern = Pattern
	// .compile(regExpn, Pattern.CASE_INSENSITIVE);
	//
	// public boolean isEmailValid(String email) {
	//
	// CharSequence inputStr = email;
	//
	// Matcher matcher = pattern.matcher(inputStr);
	//
	// if (matcher.matches())
	// return true;
	// else
	// return false;
	// }

	public static boolean isEmailValid(String email) {
		if (email == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
					.matches();
		}
	}
}
