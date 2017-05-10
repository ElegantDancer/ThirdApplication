package share.iqiyi.com.eventbusdemo.build;

/**
 * @author zhenzhen
 * Created by zhenzhen on 2017/5/9.
 */

public class Person {

    private String name;
    private String age;
    private String height;

    private Person(Builder builder){
        this.name = builder.name;
        this.age = builder.age;
        this.height = builder.height;
    }


    public static class Builder{

        private String name;
        private String age;
        private String height;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder age(String age){
            this.age = age;
            return this;
        }

        public Builder height(String height){
            this.height = height;
            return this;
        }

        public Person build(){

            return new Person(this);
        }
    }
}
