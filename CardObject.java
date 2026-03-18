import java.util.ArrayList;
import java.util.TreeSet;

public  class CardObject {
	private int id;
	private String name;
	private ArrayList<String> atributes;
	
	
	
	public CardObject(int id, String name, ArrayList<String> atributes) {
		this.id = id;
		this.name = name;
		this.atributes = atributes;
		
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
		msg += "\n\nAtributes:";
		for (String string : atributes) {
			msg +="\n" + string;
		}
		return msg;
	}
}
