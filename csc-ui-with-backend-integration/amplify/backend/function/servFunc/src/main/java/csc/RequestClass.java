/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_SERVDB_ARN
	STORAGE_SERVDB_NAME
	STORAGE_SERVDB_STREAMARN
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



    public RequestClass(String id, String name, String category, String servtype) {
        this.id=id;
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