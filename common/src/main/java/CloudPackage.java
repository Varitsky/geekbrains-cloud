import java.io.Serializable;

public class CloudPackage implements Serializable {

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    private String text;


    CloudPackage(String text){
        this.text = text;
    }


    public static void main(String[] args) {

    }
}
