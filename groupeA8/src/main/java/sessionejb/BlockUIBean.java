package sessionejb;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.apache.poi.EncryptedDocumentException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ManagedBean
@ViewScoped
public class BlockUIBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public void waitFiveSeconds() {

	  try {
      Thread.sleep(7500);
      
    } catch (InterruptedException e) {
  	
    }
	  
	 
  }

 
}

