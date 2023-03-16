/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_VSDDB_ARN
	STORAGE_VSDDB_NAME
	STORAGE_VSDDB_STREAMARN
Amplify Params - DO NOT EDIT */

package csc;
        
     public class ResponseClass {
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
        
        
        
        public ResponseClass(String id, String name, String category, String servtype) {
            this.id=id;
            this.name = name;
            this.category = category;
            this.servtype = servtype;
        }
        
        
        public ResponseClass() {
        }
        
        
        public String getId() {
            return id;
        }
        
        
        public void setId(String id) {
            this.id = id;
        }
        }