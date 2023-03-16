/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_VSDDB_ARN
	STORAGE_VSDDB_NAME
	STORAGE_VSDDB_STREAMARN
Amplify Params - DO NOT EDIT */

package csc;
        
public class RequestClass {
    String id;
    String name;
    String category;
    String servtype;


public String getName() {
    return name;
}


public void setName(String name) {
    this.name = name;
}


public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public String getServtype() {
    return servtype;
}

public void setServtype(String servtype) {
    this.servtype = servtype;
}



public RequestClass(String name, String category, String servtype) {
    this.name = name;
    this.category = category;
    this.servtype = servtype;
}


public RequestClass() {
}


public String getId() {
    return id;
}


public void setId(String id) {
    this.id = id;
}
}