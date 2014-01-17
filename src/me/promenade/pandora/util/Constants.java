package me.promenade.pandora.util;

public class Constants {
	public static final String XMPP_HOST = "192.168.0.110";
	public static final String PUSH_URL = "http://" + XMPP_HOST + ":28080/MyPush/resources/xmpp/push";
	public static final String CALLBACK_URL = "http://" + XMPP_HOST + ":28080/MyPush/resources/xmpp/callback";
	public static final String LOGIN_URL = "http://" + XMPP_HOST + ":28080/MyPush/resources/xmpp/confirm";
	public static final String UPDATE_URL = "http://" + XMPP_HOST + ":28080/MyPush/resources/xmpp/update";
	public static final String REGISTER_URL = "http://" + XMPP_HOST + ":28080/MyPush/resources/xmpp/register";
	public static final String UPLOAD_PHOTO_URL = "http://" + XMPP_HOST + ":28080/MyPush/resources/xmpp/addPhoto";
	public static final String GET_PHOTO_URL = "http://" + XMPP_HOST + ":28080/MyPush/resources/xmpp/getPhoto";
	
	public static final String SP_IS_LOGIN = "isLogin";
	public static final String SP_USER_ID = "userId";
	public static final String SP_USER_NAME = "userName";
	public static final String SP_USER_PASSWORD = "userPassword";
	public static final String SP_FRIEND = "friend";
}
