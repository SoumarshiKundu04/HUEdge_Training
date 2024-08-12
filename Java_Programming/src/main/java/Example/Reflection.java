package Example;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface  CustomAnnotation{
    String value();
}
@CustomAnnotation(value  = "Annotation")
public class Reflection {
    private int id;
    public String name;
    public int arr[];


    public Reflection() {}
    public Reflection(int x){
        System.out.println("Parametrized Constructor");
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void method(){
        System.out.println("method");
    }
    @Override
    public String toString(){
        return "New String";
    }

}
