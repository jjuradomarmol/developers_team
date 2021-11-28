package controller;

import model.Florist;
import model.Flower;
import model.Ornament;
import model.Product;
import model.RepositoryException;
import model.Ticket;
import model.Tree;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


@SuppressWarnings({"unchecked", "rawtypes"})
public class MongoDBRepository implements RepositoryInterface {
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection floristName;
	private MongoCollection trees;
	private MongoCollection flowers;
	private MongoCollection ornaments;
	private MongoCollection<Ticket> tickets;
	
	public MongoDBRepository() {
		setMongoClient();
		this.database = mongoClient.getDatabase("Florist");
		this.floristName = database.getCollection("floristName");
		this.trees = database.getCollection("trees");
		this.flowers = database.getCollection("flowers");
		this.ornaments = database.getCollection("ornaments");
		this.tickets = database.getCollection("tickets", Ticket.class);
	}
	
	private void setMongoClient() {
		ConnectionString connectionString =
				new ConnectionString("mongodb://localhost:27017");
		CodecRegistry pojoCodecRegistry = CodecRegistries
			.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = CodecRegistries
				.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(), 
					pojoCodecRegistry
				);
		MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
		this.mongoClient = MongoClients.create(clientSettings);
	}

	public Florist findFlorist() throws RepositoryException {
		Florist florist = Florist.getInstance();
		MongoCursor<Document> it = this.floristName.find().iterator();
		if (!it.hasNext()) {
			throw new RepositoryException("No hay ninguna floristería creada.");
		} else {
			florist.emptyFlorist();
			
			Document document = it.next();
			florist.setName((String)document.get("name"));
			
			it = this.trees.find().iterator();
			while (it.hasNext()) {
				document = it.next();
				String id = (String)document.get("_id");
				String name = (String)document.get("name");
				double price = (Double)document.get("price");
				int quantity = (Integer)document.get("quantity");
				Double height = (Double)document.get("height");
				Tree tree = new Tree(id, name, price, height, quantity);
				florist.getStock().addTree(tree);
			}
			
			it = this.flowers.find().iterator();
			while (it.hasNext()) {
				document = it.next();
				String id = (String)document.get("_id");
				String name = (String)document.get("name");
				double price = (Double)document.get("price");
				int quantity = (Integer)document.get("quantity");
				String color = (String)document.get("color");
				Flower flower = new Flower(id, name, price, color, quantity);
				florist.getStock().addFlower(flower);
			}
			
			it = this.ornaments.find().iterator();
			while (it.hasNext()) {
				document = it.next();
				String id = (String)document.get("_id");
				String name = (String)document.get("name");
				double price = (Double)document.get("price");
				int quantity = (Integer)document.get("quantity");
				String material = (String)document.get("material");
				Ornament ornament;
				ornament = new Ornament(id, name, price, material, quantity);
				florist.getStock().addOrnament(ornament);
			}
			
			MongoCursor<Ticket> itTicket = this.tickets.find().cursor();
			while (itTicket.hasNext()) {
				Ticket ticket = itTicket.next();
				florist.getTicketCollection().addToTickets(ticket);
			}			
		}
		return florist;
	}

	
	public void addFlorist(Florist florist) {
		Document floristDoc = new Document();
		floristDoc.put("name", florist.getName());
		this.floristName.insertOne(floristDoc);
	}

	public void addProduct(Product product) {
		Document productDoc = new Document();  
        productDoc.put("_id", product.getId());
        productDoc.put("name", product.getName());
        productDoc.put("price", product.getPrice());
        productDoc.put("quantity", product.getQuantity());
		if (product instanceof Tree) {
	        productDoc.put("height", ((Tree)product).getHeight());
	        this.trees.insertOne(productDoc);
		} else if (product instanceof Flower) {
	        productDoc.put("color", ((Flower)product).getColor());
	        this.flowers.insertOne(productDoc);
		} else if (product instanceof Ornament) {
	        productDoc.put("material", ((Ornament)product).getMaterial());
	        this.ornaments.insertOne(productDoc);
		}
	}

	public void updateProduct(Product product) {
		MongoCollection collection = null;
		if (product instanceof Tree) {
	        collection = this.trees;
		} else if (product instanceof Flower) {
			collection = this.flowers;
		} else if (product instanceof Ornament) {
			collection = this.ornaments;
		}
		
		Document find = new Document();
		find.put("_id", product.getId());
		
		Document updatedProduct = new Document();
		updatedProduct.put("quantity", product.getQuantity());

		collection.updateOne(find, new Document("$set", updatedProduct));

	}

	public void deleteProduct(Product product) {
		MongoCollection collection = null;
		if (product instanceof Tree) {
	        collection = this.trees;
		} else if (product instanceof Flower) {
			collection = this.flowers;
		} else if (product instanceof Ornament) {
			collection = this.ornaments;
		}
		collection.deleteOne(new Document("_id", product.getId()));
	}

	public void addTicket(Ticket ticket) {
        this.tickets.insertOne(ticket);
	}

	public void deleteFlorist() {
		this.floristName.deleteMany(new Document());
		this.trees.deleteMany(new Document());
		this.flowers.deleteMany(new Document());
		this.ornaments.deleteMany(new Document());
		this.tickets.deleteMany(new Document());
	}

}
