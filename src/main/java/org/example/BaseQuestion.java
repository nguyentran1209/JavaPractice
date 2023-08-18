package org.example;


import org.json.simple.JSONObject;

public class BaseQuestion {
    String id;
    String name;
    String type;
    String question;

    BaseQuestion(String id, String name,String question){
        this.id = "REQ_00"+ id;
        this.name ="REQ_00"+name;
        this.type = "REQUEST";
        this.question = "[  "+ question +"  ]";
    }


}



