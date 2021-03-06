package com.example.RestApp3.Controllers;

import com.example.RestApp3.Models.DatePair;
import com.example.RestApp3.Models.MyDataClass;
import com.example.RestApp3.Models.MyPair;
import com.example.RestApp3.Repos.MyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
public class MyController {

    @Autowired
    MyRepo dataRepo;


    @RequestMapping("/")
    public String func1(){

        return "from index func";
    }


    //get all data
    @RequestMapping("port/data")
    public List<MyDataClass> func2(){

        return dataRepo.findAll();
    }

    //events of out of range temperature datavalues
    @RequestMapping("port/data/outofrange")
    public List<MyDataClass> func3(@RequestBody MyPair pair){
        int lower=pair.getLower();
        int higher=pair.getHigher();

        return dataRepo.getOutOfRange(lower,higher);
    }

    //events withing temperature datavalue range
    @RequestMapping("port/data/withingrange")
    public List<MyDataClass> func8(@RequestBody MyPair pair){
        int lower=pair.getLower();
        int higher=pair.getHigher();

        System.out.println("lower :"+lower+"\thigher :"+higher);

        return dataRepo.getInRange(lower,higher);

    }

    //get datas of unique sensor
    @RequestMapping("port/data/{sensorid}")
    public List<MyDataClass> func4(@PathVariable("sensorid")int id){

        return dataRepo.getSignalsOfSensorId(id);
    }


    //post data by parameters
    @RequestMapping("port/data/postdataparas")
    public String func5(MyDataClass mydata){

        dataRepo.save(mydata);
        return "Done save";
    }

    //postdata by request body
    @RequestMapping("port/data/postdatabody")
    public String func6(@RequestBody MyDataClass data){
        dataRepo.save(data);
        return "Done save";

    }

    //get events withing time period
    @RequestMapping("port/data/getwithingdate")
    public List<MyDataClass> func7(@RequestBody DatePair pair){
        String lower=pair.getLowerdate();
        String higher=pair.getHigherdate();

        try{
            Date d1=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(lower);
            Date d2=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(higher);
            System.out.println("lower date: "+lower);
            System.out.println("higher date: "+higher);
            return dataRepo.getWithingdate(d1,d2);
        }catch(Exception e){System.out.println(e); return null;}


    }

}



