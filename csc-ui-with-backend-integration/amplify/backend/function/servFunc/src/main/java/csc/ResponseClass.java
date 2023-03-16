/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_SERVDB_ARN
	STORAGE_SERVDB_NAME
	STORAGE_SERVDB_STREAMARN
Amplify Params - DO NOT EDIT */

package csc;

public class ResponseClass {
    String id;
    String name;

    public ResponseClass() {
    }
    public ResponseClass(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
