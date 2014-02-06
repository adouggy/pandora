package me.promenade.pandora.util;

public enum NameUtil {
	INSTANCE;
	
	public String parseName( String name ){
		if( name.contains("@") ){
			name = name.replace("@", "$");
		}
		return name;
	}
	
	public String showName(String name){
		if( name.contains("$") ){
			name = name.replace("$", "@");
		}
		return name;
	}
}
