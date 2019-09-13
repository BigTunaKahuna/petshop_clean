package exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7614005188495447182L;
	private final String name;
	private final String id;
	private final Object obj;
	
	public ResourceNotFoundException(String name, String id, Object obj) {
		super(String.format("%s %s with id %s", name, id, obj));
		this.name = name;
		this.id = id;
		this.obj = obj;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public Object getObj() {
		return obj;
	}
	
	
}
