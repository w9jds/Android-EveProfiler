package com.w9jds.eveprofiler.Objects;

import java.text.DecimalFormat;

public class SkillInfo {
	private String typeID; 
	private String skillpoints;
	private String level;
	private String published;
	
	public String getTypeID() { return typeID; }
	public void setTypeID(String typeID) { this.typeID = typeID; }
	
	public String getSkillPoints() { return skillpoints; }
	public void setSkillPoints(String skillpoints) 
	{ 
		DecimalFormat formatter = new DecimalFormat("#,###");
		this.skillpoints = formatter.format(Double.parseDouble(skillpoints)); 
	}
	
	public String getLevel() { return level; }
	public void setLevel(String level) { this.level = level; }
	
	public String getPublished() {return published; }
	public void setPublished(String published) {this.published = published; }
	
}
