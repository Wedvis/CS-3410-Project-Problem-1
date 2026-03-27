package group_project;

import java.util.ArrayList;
import java.util.TreeSet;

public  class CardObject {
	private int id;
	private String name;
	private String type;
	private String stage;
	private String special;
	private ArrayList<String> atributes;
	private String imageURL;
	
	
	
	public CardObject(int id, String name, String type, String stage, String special, ArrayList<String> atributes, String imageURL) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.stage = stage;
		this.special = special;
		this.atributes = atributes;
		this.imageURL = imageURL;
		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getStage() {
		return stage;
	}
	
	public String getSpecial() {
		return special;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	public TreeSet<String> getAtributes() {
		TreeSet<String> tree = new TreeSet<>(atributes);
		return tree;
	}
	
	public void addAttribute(String atribute) {
		this.atributes.add(atribute);
	}
	
	public void addManyAttributes(ArrayList<String> atributes) {
		this.atributes.addAll(atributes);
	}

  public boolean removeAttribute(String atribute) {
		if (atributes.contains(atribute)) {
			return atributes.remove(atribute);
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String msg = String.format("id=%d, name=%s", id, name);
		msg += "\nAtributes:";
		for (String string : atributes) {
			msg +="\n" + string;
		}
		msg += "\n" + imageURL;
		return msg;
	}
}
