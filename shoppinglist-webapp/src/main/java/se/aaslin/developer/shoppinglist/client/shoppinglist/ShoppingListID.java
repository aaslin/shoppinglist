package se.aaslin.developer.shoppinglist.client.shoppinglist;

public enum ShoppingListID {
	LOGIN("login"), SHOPPING_LIST("shopping_list");
	
	private String token; 
	
	private ShoppingListID(String token){
		this.token = token;
	}
	
	public String getToken(){
		return token;
	}
	
	public static ShoppingListID parseString(String token){
		for(ShoppingListID enumID : ShoppingListID.values()){
			if(enumID.getToken().equals(token)){
				return enumID;
			}
		}
		
		return null;
	}
}
