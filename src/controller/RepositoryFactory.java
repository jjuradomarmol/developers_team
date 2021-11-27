package controller;

public class RepositoryFactory {
	
	private static RepositoryInterface repository;
	
	public static void selectRepository(String selected) {
		if(selected.equalsIgnoreCase("mySQL")) {
			repository = new MySQLRepository();
		} else if (selected.equalsIgnoreCase("mongoDB")) {
			repository = new MongoDBRepository();
		} else {
			repository = new TxtRepository();
		}
	}
	
	public static RepositoryInterface getRepository() {
		return repository;
	}
	
}
