package Example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


public class Main {

    public int maxProfit(int ind,int f,List<Integer>arr){
        if(f==2) return 0;
        if(ind==arr.size()) return  0;
        if(f==0) return Math.max(-1*(arr.get(ind))+maxProfit(ind+1,1,arr),0+maxProfit(ind+1,0,arr));
        return Math.max(arr.get(ind)+maxProfit(ind+1,2,arr),0+maxProfit(ind+1,f,arr));

    }
    public void balanced_parenthesis(String str){
        Stack<Character>s=new Stack<>();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='(' || str.charAt(i)=='[' || str.charAt(i)=='{'){
                s.push(str.charAt(i));
            }
            else if(s.size()>0 && str.charAt(i)==')'){
                if(s.peek()=='(') s.pop();
            }
            else if(s.size()>0 && str.charAt(i)==']'){
                if(s.peek()=='[') s.pop();
            }
            else if(s.size()>0 && str.charAt(i)=='}'){
                if(s.peek()=='{') s.pop();
            }
        }
        if(s.size()==0) System.out.println("True");
        else System.out.println("False");
    }
    public void getAllSubstringCount(String str){
        HashMap<String,Integer>hm=new HashMap<>();
        for(int i=0;i<str.length();i++){
            for(int j=i;j<str.length();j++){
                String sub=str.substring(i,j+1);
                if(hm.containsKey(sub))
                hm.put(sub,hm.get(sub)+1);
                else
                    hm.put(sub,1);
            }
        }
        System.out.println(hm);
    }
    public void printScenario(List<String>name,List<Integer>num){
        List<Data>d=new ArrayList<>();
        for(int i=0;i<name.size();i++){
            if(num.get(i)!=0){
                Data ele=new Data(name.get(i),num.get(i));
                d.add(ele);
            }
        }
        d.sort((d1,d2)->Integer.compare(d2.num,d1.num));
        for(Data dd:d) {
            System.out.println(dd.num+"   "+dd.str);
        }
    }
    public void printJson(JSONObject obj){
        JSONArray arr = obj.getJSONArray("data");
        JSONArray arr2 = new JSONArray();
        for(int i=0;i<arr.length();i++){
            JSONObject object = arr.getJSONObject(i);
            JSONObject object1 = new JSONObject();
            Set<String> set=object.keySet();
            for(String str:set){
                Object value=object.get(str);
                if(str.equals("email") && value.toString().contains("@deloitte.com")){
                    object1.put("email",value.toString());
                    System.out.println(value.toString());
                }
                else if(str.equals("email") && !value.toString().contains("@deloitte.com")){
                    object1.put("email","Not a valid email");

                }
                else {
                    object1.put(str,value.toString());
                }
            }
            arr2.put(object1);

        }
        obj.put("data",arr2);
        System.out.println(obj.toString());

    }
    public static void main(String[] args) throws IOException {
            JSONObject obj = new JSONObject("{ \n" +
                    "\n" +
                    "  \"page\": 1, \n" +
                    "\n" +
                    "  \"per_page\": 6, \n" +
                    "\n" +
                    "  \"total\": 12, \n" +
                    "\n" +
                    "  \"total_pages\": 2, \n" +
                    "\n" +
                    "  \"data\": [ \n" +
                    "\n" +
                    "    { \n" +
                    "\n" +
                    "      \"id\": 1, \n" +
                    "\n" +
                    "      \"email\": \"george.bluth@deloitte.com\", \n" +
                    "\n" +
                    "      \"first_name\": \"George\", \n" +
                    "\n" +
                    "      \"last_name\": \"Bluth\", \n" +
                    "\n" +
                    "      \"avatar\": \"https://reqres.in/img/faces/1-image.jpg\" \n" +
                    "\n" +
                    "    }, \n" +
                    "\n" +
                    "    { \n" +
                    "\n" +
                    "      \"id\": 2, \n" +
                    "\n" +
                    "      \"email\": \"janet.weaver@example.com\", \n" +
                    "\n" +
                    "      \"first_name\": \"Janet\", \n" +
                    "\n" +
                    "      \"last_name\": \"Weaver\", \n" +
                    "\n" +
                    "      \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\" \n" +
                    "\n" +
                    "    }, \n" +
                    "\n" +
                    "    { \n" +
                    "\n" +
                    "      \"id\": 3, \n" +
                    "\n" +
                    "      \"email\": \"emma.wong@example.com\", \n" +
                    "\n" +
                    "      \"first_name\": \"Emma\", \n" +
                    "\n" +
                    "      \"last_name\": \"Wong\", \n" +
                    "\n" +
                    "      \"avatar\": \"https://reqres.in/img/faces/3-image.jpg\" \n" +
                    "\n" +
                    "    }, \n" +
                    "\n" +
                    "    { \n" +
                    "\n" +
                    "      \"id\": 4, \n" +
                    "\n" +
                    "      \"email\": \"eve.holt@deloitte.com\", \n" +
                    "\n" +
                    "      \"first_name\": \"Eve\", \n" +
                    "\n" +
                    "      \"last_name\": \"Holt\", \n" +
                    "\n" +
                    "      \"avatar\": \"https://reqres.in/img/faces/4-image.jpg\" \n" +
                    "\n" +
                    "    }, \n" +
                    "\n" +
                    "    { \n" +
                    "\n" +
                    "      \"id\": 5, \n" +
                    "\n" +
                    "      \"email\": \"charles.morris@example.com\", \n" +
                    "\n" +
                    "      \"first_name\": \"Charles\", \n" +
                    "\n" +
                    "      \"last_name\": \"Morris\", \n" +
                    "\n" +
                    "      \"avatar\": \"https://reqres.in/img/faces/5-image.jpg\" \n" +
                    "\n" +
                    "    }, \n" +
                    "\n" +
                    "    { \n" +
                    "\n" +
                    "      \"id\": 6, \n" +
                    "\n" +
                    "      \"email\": \"tracey.ramos@deloitte.com\", \n" +
                    "\n" +
                    "      \"first_name\": \"Tracey\", \n" +
                    "\n" +
                    "      \"last_name\": \"Ramos\", \n" +
                    "\n" +
                    "      \"avatar\": \"https://reqres.in/img/faces/6-image.jpg\" \n" +
                    "\n" +
                    "    } \n" +
                    "\n" +
                    "  ], \n" +
                    "\n" +
                    "  \"support\": { \n" +
                    "\n" +
                    "    \"url\": \"https://reqres.in/#support-heading\", \n" +
                    "\n" +
                    "    \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\" \n" +
                    "\n" +
                    "  } \n" +
                    "\n" +
                    "} ");
            System.out.println(obj);
            Main m=new Main();
            m.printJson(obj);


        List<String>name=new ArrayList<>(Arrays.asList("Mahesh","Dinesh","Kamalesh","Sathish","Suresh","Ramesh"));
        List<Integer>num=new ArrayList<>(Arrays.asList(-2, 2, 1, -1, 3, 0));
        m.printScenario(name,num);


        m.getAllSubstringCount("abababa");


        m.balanced_parenthesis("({}){}");


        int profit=m.maxProfit(0,0,new ArrayList<>(Arrays.asList(7,6,4,3,1)));
        System.out.println(profit);


        Reflection r=new Reflection();
        Class rt=r.getClass();
        Constructor<?>[]constructor=rt.getConstructors();
        Method[] method = rt.getDeclaredMethods();
        Field[] field=rt.getFields();
        Annotation[]annotations=rt.getAnnotations();

        for(int i=0;i<constructor.length;i++) {
            System.out.println("Constructors are==="+constructor[i].getName());
        }

        for(int i=0;i<method.length;i++) {
            System.out.println("methods are==="+method[i].getName());
        }


        for(int i=0;i<field.length;i++) {
            System.out.println("fields are==="+field[i].getName());
        }


        for(int i=0;i<annotations.length;i++) {
            System.out.println("annotations are==="+annotations[i].annotationType());
        }


        SingletonClass sc = SingletonClass.getInstance();
        SingletonClass sc2 = SingletonClass.getInstance();
        if(sc==sc2){
            System.out.println("Same object reference");

        }
        System.out.println(sc.getString("username"));
        }
    }
