package Models;

import java.util.List;
import Models.Tables.TablesId;

public class Model {
    
    /**
     ** Serve a creare un nuovo id senza conflitti
     * @param path: file da controllare
     */
    public static <T extends TablesId> int autoSetId(List<T> list){

        int maxId = 0;

        for(T t : list){
            if( t.getId() > maxId)
                maxId = t.getId();
        }

        for(int i = 1; i <= maxId; i++) {
            boolean idExists = false;
            for(T t : list){
                if(t.getId() == i) {
                    idExists = true;
                    break;
                }
            }
    
            if (!idExists) {System.out.println("Nuovo id: " + i);
                return i;
                
            }
        }
        System.out.println("Nuovo id: " + (maxId + 1));
        return maxId + 1;

    }
}
